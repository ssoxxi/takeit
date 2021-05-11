/**
 * 
 */
package com.takeit.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dto.Item;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Review;

/**
 * 시작페이지 dao
 * @author 	한소희
 * @since	jdk1.8
 * @version v2.0
 */
public class IndexDao {
	private static IndexDao instance = new IndexDao();
	private IndexDao() {}
	public static IndexDao getInstance() {
		return instance;
	}
	
	/**신상품 리스트*/
	public void getNewItemList(Connection con, ArrayList<Item> itemList) throws CommonException {
		System.out.println("[debug] index 신상품 dao 리스트 요청");
		String sql = "SELECT * "
				+ "FROM ITEM I LEFT OUTER JOIN SELLER S "
				+ "ON(I.SELLER_ID = S.SELLER_ID) "
				+ "ORDER BY I.ITEM_INPUT_DATE DESC";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				Item dto = new Item();
				dto.setShopName(rs.getString("SHOP_NAME"));
				dto.setItemName(rs.getString("ITEM_NAME"));
				dto.setItemNo(rs.getString("ITEM_NO"));
				dto.setDiscRate(rs.getInt("DISC_RATE"));
				dto.setItemPrice(rs.getInt("ITEM_PRICE"));
				dto.setItemImg(rs.getString("ITEM_IMG"));
				dto.setItemCustScore(rs.getDouble("ITEM_CUST_SCORE"));
				
				itemList.add(dto);
			}
			System.out.println("[debug] index 신상품 dao 리스트 요청 완료");
			
		} catch (SQLException e) {
			System.out.println("[debug] index 신상품 dao 리스트 요청 실패");
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",16);
			message.setLinkTitle("메인으로");
			message.setUrl("/takeit/index");
			throw new CommonException(message);
		}
		JdbcTemplate.close(rs);
		JdbcTemplate.close(pstmt);
	}
	
	/**베스트후기 리스트*/
	public void getBestReviewList(Connection con, ArrayList<Review> reviewList) throws CommonException {
		System.out.println("[debug] index 베스트후기 dao 리스트 요청");
		String sql = "SELECT * FROM REVIEW ORDER BY REVIEW_VIEWS DESC";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				Review dto = new Review();
				dto.setReviewNo(rs.getString("REVIEW_NO"));
				dto.setReviewTitle(rs.getString("REVIEW_TITLE"));
				dto.setReviewContents(rs.getString("REVIEW_CONTENTS"));
				dto.setItemNo(rs.getString("ITEM_NO"));
				dto.setReviewScore(rs.getInt("REVIEW_SCORE"));
				dto.setReviewImg(rs.getString("REVIEW_IMG"));
				dto.setMemberId(rs.getString("MEMBER_ID"));
				
				reviewList.add(dto);
			}
			System.out.println("[debug] index 베스트후기 dao 리스트 요청 완료");
			
		} catch (SQLException e) {
			System.out.println("[debug] index 베스트후기 dao 리스트 요청 실패");
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",16);
			message.setLinkTitle("메인으로");
			message.setUrl("/takeit/index");
			throw new CommonException(message);
		}
		JdbcTemplate.close(rs);
		JdbcTemplate.close(pstmt);
	}

}
