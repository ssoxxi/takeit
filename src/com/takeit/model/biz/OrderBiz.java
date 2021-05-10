package com.takeit.model.biz;

import java.sql.Connection;
import java.util.ArrayList;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dao.CartDao;
import com.takeit.model.dao.OrderDao;
import com.takeit.model.dao.TakeitDao;
import com.takeit.model.dto.Order;
import com.takeit.model.dto.OrderDetail;
import com.takeit.model.dto.Shipping;
import com.takeit.model.dto.Takeit;

/**
 * 주문 업무처리 위한 OrderBiz 클래스
 * @author 김태경
 * @since jdk1.8
 * @version v2.0 2021/05/10
 */
public class OrderBiz {
	/**
	 * 주문 등록
	 * @param orderList 주문목록
	 */
	public void addOrder(ArrayList<Order> orderList) throws CommonException {
		OrderDao dao = OrderDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try { 
			for (Order order : orderList) {
				for (OrderDetail orderDetail : order.getOrderDetails()) {
					order.setItemTakeit(orderDetail.getItemTakeit());
					if (order.getItemTakeit().equals("F")) {
						break;
					}
				} 
				dao.insertOrder(conn, order);
				dao.selectOrderNo(conn, order);
				dao.insertOrderDetail(conn, order);
				if (order.getItemTakeit().equals("T")) {
					TakeitDao takeitDao = TakeitDao.getInstance();
					Takeit takeit = new Takeit();
					takeitDao.selectLoc(conn, order.getMemberId(), takeit);
					takeitDao.selectTakeitNo(conn, takeit);
					takeitDao.insertTakeitDetail(conn, takeit, order);
					takeitDao.updateTakeitCurrPrice(conn, takeit, order);
				}
				for (OrderDetail orderDetail : order.getOrderDetails()) {
					CartDao.getInstance().removeCart(conn, order.getMemberId(), orderDetail.getItemNo());
				}
			}
			JdbcTemplate.commit(conn);
		} catch (CommonException e) {
			JdbcTemplate.rollback(conn);
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}

	/**
	 * 판매자 주문목록 조회
	 * @param sellerId 판매자아이디
	 * @param orderList 주문목록
	 */
	public void getSellerOrderList(String sellerId, ArrayList<Order> orderList) throws CommonException {
		OrderDao dao = OrderDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.selectSellerOrderList(conn, sellerId, orderList);
		} catch (CommonException e) {
			throw e;
		}
		finally {
			JdbcTemplate.close(conn);
		}
	}

	/**
	 * 일반회원 주문목록 조회
	 * @param memberId 회원아이디
	 * @param orderList 주문목록
	 */
	public void getMemberOrderList(String memberId, ArrayList<Order> orderList) throws CommonException {
		OrderDao dao = OrderDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.selectMemberOrderList(conn, memberId, orderList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}

	/**
	 * 주문취소요청
	 * @param orderNo 주문번호
	 */
	public void orderCancelRequest(String orderNo) throws CommonException {
		OrderDao dao = OrderDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.updateOrderCancelReq(conn, orderNo);
			JdbcTemplate.commit(conn);
		} catch (CommonException e) {
			JdbcTemplate.rollback(conn);
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}

	/**
	 * 주문 취소 
	 * @param orderNo 주문번호
	 */
	public void orderCancel(String orderNo) throws CommonException {
		OrderDao dao = OrderDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.updateOrderCancel(conn, orderNo);
			JdbcTemplate.commit(conn);
		} catch (CommonException e) {
			JdbcTemplate.rollback(conn);
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
		
	}

	/**
	 * 배송상태 목록 조회
	 * @param shippingList 배송상태리스트
	 */
	public void getShippingList(ArrayList<Shipping> shippingList) throws CommonException {
		OrderDao dao = OrderDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.selectShippingList(conn, shippingList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}

	/** 주문 정보 조회 */
	public void getOrderItem(Order order) throws CommonException {
  	OrderDao dao = OrderDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
  			dao.selectOrderItem(conn, order);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}

	/** 배송상태 변경 */
	public void updateShipStatusCode(String orderNo, String shipStatusCode) throws CommonException {

		OrderDao dao = OrderDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.updateShopStatusCode(conn, orderNo , shipStatusCode);
			JdbcTemplate.commit(conn);
			
		}catch (CommonException e) {
			JdbcTemplate.rollback(conn);
			throw e;
		}finally {
			JdbcTemplate.close(conn);
		}
	}
}
