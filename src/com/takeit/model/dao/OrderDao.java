package com.takeit.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Order;
import com.takeit.model.dto.OrderDetail;
import com.takeit.model.dto.Shipping;

/**
 * 주문 테이블에 대한 OrderDao 클래스
 * @author 김태경
 * @since jdk1.8
 * @version v2.0 2021/05/10
 */
public class OrderDao {
	private static OrderDao instance = new OrderDao();
	
	public static OrderDao getInstance() {
		return instance;
	}
	
	/**
	 * 주문 등록
	 * @param order 주문 객체
	 */
	public void insertOrder(Connection conn, Order order) throws CommonException {
		String sql = "INSERT INTO Orders VALUES(? || TO_CHAR(SYSDATE,'yymmdd') || LPAD(ORDER_SEQ.NEXTVAL, 6, '0') "
				+ ", ?, ?, ?, ?||?, ?, ?, ?, 'F', 'F', ?, ?) ";
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, order.getItemTakeit()); 
			stmt.setString(2, order.getReceiveMethod());
			stmt.setString(3, order.getRecipientName());
			stmt.setString(4, order.getRecipientPostNo());
			stmt.setString(5, order.getRecipientAddr());
			stmt.setString(6, order.getRecipientAddrDetail());
			stmt.setString(7, order.getRecipientMobile());
			stmt.setString(8, order.getShipRequest());
			stmt.setInt(9, order.getOrderPrice());
			stmt.setString(10, order.getShipStatusCode());
			stmt.setString(11, order.getMemberId());
			
			int row = stmt.executeUpdate();
			if (row == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 29);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}	
	}
	
	/**
	 * 최근 주문번호 조회
	 * @param order 주문 객체
	 */
	public void selectOrderNo(Connection conn, Order order) throws CommonException {
		String sql = "SELECT ORDER_NO FROM ORDERS ORDER BY SUBSTR(ORDER_NO, 2) DESC ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				order.setOrderNo(rs.getString("Order_no"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 29);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}	
	}
	
	/**
	 * 주문상세 등록
	 * @param order 주문객체
	 */
	public void insertOrderDetail(Connection conn, Order order) throws CommonException {
		String sql = "INSERT INTO Order_Detail VALUES(?, ?, ?, ?) ";
		PreparedStatement stmt = null;
		try {
			
			for (int index = 0; index < order.getOrderDetails().size(); index++) {
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, order.getOrderDetails().get(index).getItemNo());
				stmt.setString(2, order.getOrderNo());
				stmt.setInt(3, order.getOrderDetails().get(index).getItemQty());
				stmt.setInt(4, order.getOrderDetails().get(index).getItemPayPrice());
				
				int row = stmt.executeUpdate();
				if (row == 0) {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 29);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}	
	}

	/**
	 * 판매자 주문목록 조회
	 * @param sellerId 판매자아이디
	 * @param orderList 주문목록
	 */
	public void selectSellerOrderList(Connection conn, String sellerId, ArrayList<Order> orderList) throws CommonException {
		String sql = "SELECT * "
				+ "FROM ITEM JOIN ORDER_DETAIL USING(ITEM_NO) JOIN ORDERS USING(ORDER_NO) LEFT JOIN SHIPPING USING(SHIP_STATUS_CODE) "
				+ "WHERE ITEM.SELLER_ID = ? "
				+ "ORDER BY SUBSTR(ORDER_NO, 2) DESC ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,  sellerId);
			rs = stmt.executeQuery();
			
			Order order = null;
			ArrayList<OrderDetail> orderDetails = null;
			OrderDetail orderDetail = null;
			while (rs.next()) {
				order = new Order();
				orderDetails = new ArrayList<OrderDetail>();
				orderDetail = new OrderDetail();
				for (Order dto : orderList) {
					if (dto.getOrderNo().equals(rs.getString("order_no"))) {
						orderDetails = dto.getOrderDetails();
						
						orderDetail.setItemNo(rs.getString("item_no"));
						orderDetail.setItemName(rs.getString("item_name"));
						orderDetail.setItemQty(rs.getInt("Item_Qty"));
						orderDetail.setItemPayPrice(rs.getInt("item_pay_price"));
						orderDetail.setItemImg(rs.getString("item_img"));
						orderDetail.setItemTakeit(rs.getString("item_Takeit"));
						orderDetails.add(orderDetail);
						break;
					}
				}
				if (orderDetails.isEmpty()) {
					orderDetail.setItemNo(rs.getString("item_no"));
					orderDetail.setItemName(rs.getString("item_name"));
					orderDetail.setItemQty(rs.getInt("Item_Qty"));
					orderDetail.setItemPayPrice(rs.getInt("item_pay_price"));
					orderDetail.setItemImg(rs.getString("item_img"));
					orderDetails.add(orderDetail);
					
					order.setOrderDetails(orderDetails);
					order.setRecipientAddr(rs.getString("recipient_Addr"));
					order.setShipStatusCode(rs.getString("ship_status_code"));
					order.setOrderNo(rs.getString("order_No"));
					order.setShipStatus(rs.getString("ship_Status"));
					order.setShipRequest(rs.getString("ship_Request"));
					order.setMemberId(rs.getString("member_Id"));
					order.setOrderCancelReq(rs.getString("order_Cancel_Req"));
					order.setOrderCancel(rs.getString("order_Cancel"));
					
					orderList.add(order);
				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 29);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}	
	}

	/**
	 * 일반회원 주문목록 조회
	 * @param memberId 회원아이디
	 * @param orderList 주문목록
	 */
	public void selectMemberOrderList(Connection conn, String memberId, ArrayList<Order> orderList) throws CommonException {
		String sql = "SELECT * "
				+ "FROM ORDERS LEFT JOIN SHIPPING USING(SHIP_STATUS_CODE) JOIN ORDER_DETAIL USING(ORDER_NO) JOIN ITEM USING(ITEM_NO) JOIN SELLER USING(SELLER_ID) "
				+ "WHERE ORDERS.MEMBER_ID = ? "
				+ "ORDER BY SUBSTR(ORDER_NO, 2) DESC ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			
			Order order = null;
			ArrayList<OrderDetail> orderDetails = null;
			OrderDetail orderDetail = null;
			while (rs.next()) {
				order = new Order();
				orderDetails = new ArrayList<OrderDetail>();
				orderDetail = new OrderDetail();
				for (Order dto : orderList) {
					if (dto.getOrderNo().equals(rs.getString("order_no"))) {
						orderDetails = dto.getOrderDetails();
						
						orderDetail.setItemNo(rs.getString("item_no"));
						orderDetail.setItemName(rs.getString("item_name"));
						orderDetail.setItemQty(rs.getInt("Item_Qty"));
						orderDetail.setItemPayPrice(rs.getInt("item_pay_price"));
						orderDetail.setItemImg(rs.getString("item_img"));

						orderDetails.add(orderDetail);
						
						break;
					}
				}
				if (orderDetails.isEmpty()) {
					orderDetail.setItemNo(rs.getString("item_no"));
					orderDetail.setItemName(rs.getString("item_name"));
					orderDetail.setItemQty(rs.getInt("Item_Qty"));
					orderDetail.setItemPayPrice(rs.getInt("item_pay_price"));
					orderDetail.setItemImg(rs.getString("item_img"));
					orderDetails.add(orderDetail);
					
					order.setOrderDetails(orderDetails);
	
					order.setOrderNo(rs.getString("order_No"));
					order.setShipStatusCode(rs.getString("ship_status_code"));
					order.setShipStatus(rs.getString("ship_Status"));
					order.setSellerId(rs.getString("seller_id"));
					order.setShopName(rs.getString("shop_name"));
					order.setShipRequest(rs.getString("ship_Request"));
					order.setMemberId(rs.getString("member_Id"));
					order.setReceiveMethod(rs.getString("receive_Method"));
					order.setOrderCancelReq(rs.getString("order_Cancel_Req"));
					order.setOrderCancel(rs.getString("order_Cancel"));
					
					orderList.add(order);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 29);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}	
	}

	/**
	 * 주문취소 요청
	 * @param orderNo 주문번호
	 */
	public void updateOrderCancelReq(Connection conn, String orderNo) throws CommonException {
		String sql = "UPDATE ORDERS "
				+ "SET ORDER_CANCEL_REQ = 'T' "
				+ "WHERE ORDER_NO = ? AND ORDER_CANCEL_REQ = 'F' ";
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, orderNo);
			int row = stmt.executeUpdate();
			
			if (row == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 29);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}	
	}

	/**
	 * 주문 취소
	 * @param orderNo 주문번호
	 */
	public void updateOrderCancel(Connection conn, String orderNo) throws CommonException {
		String sql = "UPDATE ORDERS "
				+ "SET ORDER_CANCEL = 'T' "
				+ "WHERE ORDER_NO = ? AND ORDER_CANCEL_REQ = 'T' AND ORDER_CANCEL = 'F' ";
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, orderNo);
			
			int row = stmt.executeUpdate();
			if (row == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 29);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}	
	}
	
	
	/**
	 * 배송 상태 변경
	 * @param orderNo 주문번호
	 * @param shipStatusCode 배송상태코드
	 */
	public void updateShopStatusCode(Connection conn, String orderNo, String shipStatusCode) throws CommonException {
		String sql = "UPDATE ORDERS "
				+ "SET SHIP_STATUS_CODE = ? " 
				+ " WHERE ORDER_NO = ?";
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, shipStatusCode);
			stmt.setString(2, orderNo);
			
			int row = stmt.executeUpdate();
			if (row == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error", 29);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}	
	}
	
	/**
	 * 배송상태 목록 조회
	 * @param shippingList 배송상태리스트
	 */
	public void selectShippingList(Connection conn, ArrayList<Shipping> shippingList) throws CommonException {
		String sql = "SELECT * FROM SHIPPING";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			Shipping shipping = null;
			while (rs.next()) {
				shipping = new Shipping();
				shipping.setShipStatusCode(rs.getString("ship_status_code"));
				shipping.setShipStatus(rs.getString("ship_status"));
				shippingList.add(shipping);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 39);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}	
	}

	/** 주문 정보 조회 */
	public void selectOrderItem(Connection conn, Order order) throws CommonException {
		String sql = "SELECT * "
				+ "FROM ITEM "
				+ "WHERE ITEM_NO = ? ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			for (OrderDetail orderDetail : order.getOrderDetails()) {
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, orderDetail.getItemNo());
				rs = stmt.executeQuery();
				while (rs.next()) {
					orderDetail.setItemTakeit(rs.getString("item_takeit"));
					orderDetail.setItemName(rs.getString("item_name"));
					orderDetail.setItemImg(rs.getString("item_img"));
					orderDetail.setSellerId(rs.getString("seller_id"));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 29);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}	
		
		try {
			sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, order.getMemberId());
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				order.setRecipientName(rs.getString("name"));
				order.setRecipientPostNo(rs.getString("postno"));
				order.setRecipientAddr(rs.getString("Address"));
				order.setRecipientMobile(rs.getString("mobile"));
				order.setRecipientAddrDetail(rs.getString("Address_Detail"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 29);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}

}
