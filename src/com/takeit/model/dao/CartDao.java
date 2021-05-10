package com.takeit.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dto.Cart;
import com.takeit.model.dto.MessageEntity;
import com.takeit.util.Utility;

/**
 * 장바구니 dao
 * @author 	한소희
 * @since	jdk1.8
 * @version v2.0
 *
 */
public class CartDao {
	private static CartDao instance = new CartDao();
	private CartDao() {}
	public static CartDao getInstance() {
		return instance;
	}
	
	/**장바구니 전체 목록 조회*/
	public void getCartList(Connection con, String memberId, int cartTotalPrice, ArrayList<Cart> cart) throws CommonException {
		System.out.println("[debug] 장바구니 전체 목록 dao 요청");
		String sql = "SELECT I.ITEM_NO AS ITEM_NO, I.ITEM_NAME AS ITEM_NAME, S.NAME AS SELLER_NAME, "
				+ "I.ITEM_IMG AS ITEM_IMG, I.DISC_RATE AS DISC_RATE, C.CART_ITEM_QTY AS CART_ITEM_QTY, I.ITEM_TAKEIT ITEM_TAKEIT, I.ITEM_INPUT_DATE ITEM_INPUT_DATE, G.EXPIRATION_DATE, "
				+ "I.ITEM_PRICE AS ITEM_PRICE, (100-I.DISC_RATE)/100*I.ITEM_PRICE * C.CART_ITEM_QTY AS TOTAL_PRICE "
				+ "FROM CART C, ITEM I JOIN ITEM_CATEGORY G ON(I.ITEM_CATEGORY_NO = G.ITEM_CATEGORY_NO), SELLER S "
				+ "WHERE C.ITEM_NO = I.ITEM_NO "
				+ "AND I.SELLER_ID = S.SELLER_ID "
				+ "AND C.MEMBER_ID=?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("[debug] 총 결제 금액= " + cartTotalPrice);
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				Cart dto = new Cart();
				dto.setItemNo(rs.getString("ITEM_NO"));
				dto.setItemName(rs.getString("ITEM_NAME"));
				dto.setSellerName(rs.getString("SELLER_NAME"));
				dto.setCartItemQty(rs.getInt("CART_ITEM_QTY"));
				
				dto.setItemPrice(rs.getInt("ITEM_PRICE"));
				dto.setTotalPrice(rs.getInt("TOTAL_PRICE"));
				dto.setItemImg(rs.getString("ITEM_IMG"));
				
				//cartTotalPrice += dto.getTotalPrice();
				System.out.println("[debug] 누적 총 결제 금액= " + cartTotalPrice);
				dto.setItemTakeit(rs.getString("item_Takeit"));
				if (rs.getString("ITEM_TAKEIT").equals("T")) {
					Date firstDate = Utility.convertStringToDate(Utility.getCurrentDate(), "yyyy-MM-dd HH:mm:ss");
					Date secondDate = Utility.convertStringToDate(rs.getString("ITEM_INPUT_DATE"), "yyyy-MM-dd HH:mm:ss");
					int a = Utility.getDayBetweenAandB(firstDate, secondDate);
					int b = Integer.valueOf(rs.getString("expiration_date"));
					int c = 100 - (int) (((double) (b - a) / b) * 100);
					if (c > 100) {
						c = 100;
					}
					
					int itemPrice = rs.getInt("item_price");
					int discPrice = (int)(itemPrice * (100-c) /100);
					int takeitPrice = ((int)((double)discPrice / 1000)) * 1000;
					
					dto.setDiscRate(100 - (int)((double)takeitPrice / itemPrice * 100));
					dto.setDiscPrice(takeitPrice);
					
					dto.setTotalPrice(takeitPrice * rs.getInt("CART_ITEM_QTY"));
					
				} else {
					dto.setDiscRate(rs.getInt("DISC_RATE"));
					int discRate = rs.getInt("disc_rate");
					int discPrice = (int)(rs.getInt("ITEM_PRICE") * (100-discRate) / 100);
					dto.setDiscPrice(discPrice);
					
					dto.setTotalPrice(discPrice * rs.getInt("CART_ITEM_QTY"));
				}
				
				cart.add(dto);
			}
			System.out.println("[debug] 장바구니 전체 목록 dao 요청 완료");
			System.out.println("[debug] 누적 총 결제 금액= " + cartTotalPrice);
		} catch (Exception e) {
			System.out.println("[debug] 장바구니 전체 목록 dao 요청 실패");
			System.out.println(e.getMessage());
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error",21);
			message.setLinkTitle("메인으로");
			message.setUrl("/takeit/index");
			throw new CommonException(message);
		} finally{
			JdbcTemplate.close(rs);
			JdbcTemplate.close(pstmt);
		}
		
	}
	
	/**장바구니 등록*/
	public void addCart(Connection con, Cart cart) throws CommonException {
		System.out.println("[debug] 장바구니 등록 dao 요청");
		String sql = "INSERT INTO CART VALUES(?,?,?)";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cart.getMemberId());
			pstmt.setString(2, cart.getItemNo());
			pstmt.setInt(3, cart.getCartItemQty());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",20);
			message.setLinkTitle("장바구니");
			message.setUrl("/takeit/item/cartList.jsp");
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(pstmt);
		}
		
	}
	
	/**장바구니 삭제*/
	public void removeCart(Connection con, String memberId, String itemNo) throws CommonException{
		System.out.println("[debug] 장바구니 삭제 dao 요청");
		String sql = "DELETE FROM CART WHERE MEMBER_ID=? AND ITEM_NO=?";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, itemNo);
			pstmt.executeUpdate();
			System.out.println("[debug] 장바구니 삭제 dao 요청 완료");
		} catch (SQLException e) {
			System.out.println("[debug] 장바구니 삭제 dao 요청 실패");
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",23);
			message.setLinkTitle("장바구니");
			message.setUrl("/takeit/item/cartList.jsp");
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(pstmt);
		}
		
	}
	
	/**장바구니 전체 삭제*/
	public void removeAllCart(Connection con, String memberId) throws CommonException {
		System.out.println("[debug] 장바구니 전체 삭제 dao 요청");
		String sql = "DELETE FROM CART WHERE MEMBER_ID=?";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.executeUpdate();
			System.out.println("[debug] 장바구니 전체 삭제 dao 요청 완료");
		} catch (SQLException e) {
			System.out.println("[debug] 장바구니 전체 삭제 dao 요청 실패");
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",23);
			message.setLinkTitle("장바구니");
			message.setUrl("/takeit/item/cartList.jsp");
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(pstmt);
		}
		
	}
	
	/**장바구니 변경*/
	public void changeCartQty(Connection con, Cart cart) throws CommonException {
		System.out.println("[debug] 장바구니 수량변경 dao 요청");
		String sql = "UPDATE CART SET CART_ITEM_QTY=? WHERE MEMBER_ID=? AND ITEM_NO=?";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cart.getCartItemQty());
			pstmt.setString(2, cart.getMemberId());
			pstmt.setString(3, cart.getItemNo());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",20);
			message.setLinkTitle("장바구니");
			message.setUrl("/takeit/item/cartList.jsp");
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(pstmt);
		}
		
	}
	
	/** 장바구니 중복 여부 검사*/
	public int searchCartItem(Connection con, String itemNo, String memberId) throws CommonException {
		System.out.println("[debug] 장바구니 아이템 중복 검사 dao 요청");
		String sql = "SELECT * FROM CART WHERE MEMBER_ID=? AND ITEM_NO=?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, itemNo);
			rs= pstmt.executeQuery();
			
			if(rs.next()) {
				return 1;
			}
			System.out.println("[debug] 장바구니 아이템 중복 검사 dao 요청 완료");
			
		} catch (SQLException e) {
			System.out.println("[debug] 장바구니 아이템 중복 검사 dao 요청 실패");
			System.out.println(e.getMessage());
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error",21);
			message.setLinkTitle("메인으로");
			message.setUrl("/takeit/index");
			throw new CommonException(message);
		} finally{
			JdbcTemplate.close(rs);
			JdbcTemplate.close(pstmt);
		}
		return 0;
	}
	
	/**장바구니 수량 변경 추가*/
	public void cartUpdate(Connection con, Cart cart) throws CommonException {
		System.out.println("[debug] 장바구니 수량변경 dao 요청");
		String sql = "UPDATE CART SET CART_ITEM_QTY=CART_ITEM_QTY+? WHERE MEMBER_ID=? AND ITEM_NO=?";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cart.getCartItemQty());
			pstmt.setString(2, cart.getMemberId());
			pstmt.setString(3, cart.getItemNo());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",20);
			message.setLinkTitle("장바구니");
			message.setUrl("/takeit/item/cartList.jsp");
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(pstmt);
		}
		
	}

	
}
