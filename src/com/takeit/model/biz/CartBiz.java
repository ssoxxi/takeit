package com.takeit.model.biz;

import java.sql.Connection;
import java.util.ArrayList;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dao.CartDao;
import com.takeit.model.dto.Cart;

/**
 * 장바구니 서비스
 * @author 한소희
 */
public class CartBiz {
	private CartDao dao = CartDao.getInstance();

	/**장바구니 리스트*/
	public void getCartList(String memberId, int cartTotalPrice, ArrayList<Cart> cart) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.getCartList(con, memberId, cartTotalPrice, cart);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
		
	}

	/**장바구니 등록*/
	public void addCart(Cart cart) throws CommonException{
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.addCart(con, cart);
			JdbcTemplate.commit(con);
		} catch (CommonException e) {
			JdbcTemplate.rollback(con);
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
		
	}

	/**장바구니 삭제*/
	public void removeCart(String memberId, String itemNo) throws CommonException{
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.removeCart(con, memberId, itemNo);
			JdbcTemplate.commit(con);
		} catch (CommonException e) {
			JdbcTemplate.rollback(con);
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}

	/**장바구니 전체 삭제*/
	public void removeAllCart(String memberId) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.removeAllCart(con, memberId);
			JdbcTemplate.commit(con);
		} catch (CommonException e) {
			JdbcTemplate.rollback(con);
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
		
	}

	
	/**장바구니 변경*/
	public void changeCartQty(Cart cart) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.changeCartQty(con, cart);
			JdbcTemplate.commit(con);
			System.out.println("[debug] 장바구니 수량변경 dao 요청 완료");
		} catch (CommonException e) {
			JdbcTemplate.rollback(con);
			System.out.println("[debug] 장바구니 수량변경 dao 요청 실패");
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
		
	}

	/**장바구니 아이템 중복 검사*/
	public int searchCartItem(String itemNo, String memberId) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			return dao.searchCartItem(con, itemNo, memberId);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}

	/**장바구니 수량 변경 추가*/
	public void cartUpdate(Cart cart) throws CommonException{
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.cartUpdate(con, cart);
			JdbcTemplate.commit(con);
			System.out.println("[debug] 장바구니 수량변경 추가 dao 요청 완료");
		} catch (CommonException e) {
			JdbcTemplate.rollback(con);
			System.out.println("[debug] 장바구니 수량변경 추가 dao 요청 실패");
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
		
	}

}
