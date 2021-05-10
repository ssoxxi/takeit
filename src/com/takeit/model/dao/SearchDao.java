package com.takeit.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dto.Board;
import com.takeit.model.dto.Item;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Search;

/**
 * 상품검색 dao
 * @author 	한소희
 * @since	jdk1.8
 * @version v2.0
 */
public class SearchDao {
	private static SearchDao instance = new SearchDao();
	private SearchDao() {}
	public static SearchDao getInstance() {
		return instance;
	}
	/**검색결과조회*/
	public void getSearchList(Connection con, ArrayList<Search> searchList, String searchInput) throws CommonException {
		System.out.println("[debug] 검색 결과 조회 dao 요청");
		String sql ="SELECT I.ITEM_NO AS ITEM_NO, I.ITEM_NAME AS ITEM_NAME, I.ITEM_PRICE AS ITEM_PRICE, I.ITEM_IMG AS ITEM_IMG, "
				+ "IC.ITEM_CATEGORY_NAME AS ITEM_CATEGORY_NAME, "
				+ "S.NAME AS SELLER_NAME, S.SHOP_NAME AS SHOP_NAME, "
				+ "I.DISC_RATE AS DISC_RATE "
				+ "FROM ITEM I FULL OUTER JOIN SELLER S ON(I.SELLER_ID = S.SELLER_ID) "
				+ "LEFT OUTER JOIN ITEM_CATEGORY IC ON(I.ITEM_CATEGORY_NO = IC.ITEM_CATEGORY_NO) "
				+ "WHERE I.SELLER_ID LIKE ? "
				+ "OR S.NAME LIKE ? "
				+ "OR I.ITEM_NAME LIKE ? "
				+ "OR I.ITEM_NO LIKE ? "
				+ "OR S.SHOP_NAME LIKE ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchInput);
			pstmt.setString(2, searchInput);
			pstmt.setString(3, searchInput);
			pstmt.setString(4, searchInput);
			pstmt.setString(5, searchInput);
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				Search dto = new Search();
				dto.setItemNo(rs.getString("item_no"));						//상품번호
				dto.setItemName(rs.getString("item_name"));					// 상품이름
				dto.setItemCategoryName(rs.getString("item_category_name"));// 상품 카테고리이름
				dto.setName(rs.getString("seller_name"));					// 판매자 이름
				dto.setShopName(rs.getString("shop_name"));					// 상점 이름
				dto.setItemImg(rs.getString("item_img"));					// 상품 이미지
				dto.setItemPrice(rs.getInt("item_price"));					// 상품 가격	
				dto.setDiscRate(rs.getInt("disc_rate"));					// 할인율
				
				searchList.add(dto);
				System.out.println("[debug] 검색 결과 조회 dao 요청 완료");
			}
			
		} catch (SQLException e) {
			System.out.println("[debug] 검색 결과 조회 dao 요청 실패");
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",9);
			message.setLinkTitle("메인으로");
			message.setUrl("/takeit/index");
			throw new CommonException(message);
		}
		JdbcTemplate.close(rs);
		JdbcTemplate.close(pstmt);
		
	}
}
