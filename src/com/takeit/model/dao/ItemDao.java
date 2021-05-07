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



/**
 * @author 김효원
 */
public class ItemDao {
	
	private static ItemDao instance = new ItemDao();
	
	/**
	 * 상품등록
	 * @param dto 상품
	 * @return 상품목록
	 */
	private ItemDao() {}
	public static ItemDao getInstance() {
		return instance;
	}
	//상품등록
	public void addItem(Connection conn, Item dto) throws CommonException {
		String sql = "insert into member values(?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = JdbcTemplate.getConnection();
		
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			
			if(rs.next()) {	
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, dto.getItemCategoryName());
			stmt.setString(2, dto.getSellerId());
			stmt.setString(3, dto.getItemName());
			stmt.setInt(4, dto.getItemPrice ());
			stmt.setString(5, dto.getItemOrigin());
			stmt.setInt(6, dto.getItemStock());
			stmt.setString(7, dto.getItemImg());
			stmt.setString(8, dto.getItemTakeit());
			stmt.setString(9, dto.getPackTypeName());
			stmt.setString(10, dto.getExpirationDate());
			stmt.setString(11, dto.getNotice());
			stmt.setInt(12, dto.getFreshPercent());
			
			}
		} catch (SQLException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
		
			MessageEntity message = new MessageEntity("error", 2);
			message.setUrl("/takeit/item/login?action=itemEnrollForm");
			message.setLinkTitle("상품등록");

			throw new CommonException(message);
		}
		JdbcTemplate.close(rs);
		JdbcTemplate.close(stmt);
	}
	/**
	 * 전체상품조회
	 * @return ArrayList<Item>
	 */
	public void ItemList(Connection conn, ArrayList<Item> itemList) throws CommonException {
	
		System.out.println("ItemList==========================================");
		String sql = "select a.item_no , a.seller_id , a.item_name , a.item_price"+
			          ", a.sales_unit, a.item_origin , a.item_stock	, a.item_img , a.item_cust_score" +
			          ", a.item_input_date , a.disc_rate , a.item_takeit , a.item_category_no , b.item_category_name"+
			          ", b.expiration_date, b.fresh_percent,b.notice, b.pack_type_no , c.pack_type_name"+
			          ", d.shop_name as shop_name"+
			          " from item a, item_category b , packing c, seller d"+
			          " where a.item_category_no =b.item_category_no and b.pack_type_no =c.pack_type_no"+
			          " and a.seller_id = d.seller_id"+
			          " order by a.item_input_date desc";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			Item dto = null;
			
			while(rs.next()) {
				dto = new Item();
				dto.setPackTypeNo(rs.getString("PACK_TYPE_NO"));
				dto.setPackTypeName(rs.getString("PACK_TYPE_NAME"));
				
				dto.setItemCategoryNo(rs.getString("ITEM_CATEGORY_NO"));
				dto.setItemCategoryName(rs.getString("ITEM_CATEGORY_NAME"));
				dto.setExpirationDate(rs.getString("EXPIRATION_DATE"));
				dto.setNotice(rs.getString("NOTICE"));
				dto.setFreshPercent(rs.getInt("FRESH_PERCENT"));
				dto.setItemNo(rs.getString("ITEM_NO"));
				
				dto.setShopName(rs.getString("SHOP_NAME"));
				dto.setSellerId(rs.getString("SELLER_ID"));
				dto.setItemName(rs.getString("ITEM_NAME"));
				dto.setItemPrice(rs.getInt("ITEM_PRICE"));
				dto.setSalesUnit(rs.getString("SALES_UNIT"));
				dto.setItemOrigin(rs.getString("ITEM_ORIGIN"));
				dto.setItemStock(rs.getInt("ITEM_STOCK"));
				dto.setItemImg(rs.getString("ITEM_IMG"));
				dto.setItemCustScore(rs.getDouble("ITEM_CUST_SCORE"));
				dto.setItemInputDate(rs.getString("ITEM_INPUT_DATE"));
				dto.setDiscRate(rs.getInt("DISC_RATE"));
				dto.setItemTakeit(rs.getString("ITEM_TAKEIT"));
		
                
				itemList.add(dto);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",7);
			message.setLinkTitle("메인으로");
			message.setUrl("/takeit/index");
			throw new CommonException(message);
		}
		JdbcTemplate.close(rs);
		JdbcTemplate.close(stmt);
	}
	
	
	/**
	 * 판매자 등록상품전체조회
	 * @return ArrayList<Item>
	 */
	public void getMyReviewList(Connection conn, ArrayList<Item> itemList,String sellerId) throws CommonException {
		
		String sql = "select a.item_no , a.seller_id , a.item_name , a.item_price"+
			          ", a.sales_unit, a.item_origin , a.item_stock	, a.item_img , a.item_cust_score" +
			          ", a.item_input_date , a.disc_rate , a.item_takeit , a.item_category_no , b.item_category_name"+
			          ", b.expiration_date, b.fresh_percent,b.notice, b.pack_type_no , c.pack_type_name"+
			          ", d.shop_name as shop_name"+
			          " from item a, item_category b , packing c, seller d"+
			          " where a.item_category_no =b.item_category_no and b.pack_type_no =c.pack_type_no"+
			          " and a.seller_id = d.seller_id"+
			          " and a.seller_Id =?" +
			          " order by a.item_input_date desc";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, sellerId);
			rs = stmt.executeQuery();
			
			System.out.println("try");
			while(rs.next()) {
				System.out.println("while");
				 Item dto = new Item();
				
				dto.setPackTypeNo(rs.getString("PACK_TYPE_NO"));
				dto.setPackTypeName(rs.getString("PACK_TYPE_NAME"));
				
				dto.setItemCategoryNo(rs.getString("ITEM_CATEGORY_NO"));
				dto.setItemCategoryName(rs.getString("ITEM_CATEGORY_NAME"));
				dto.setExpirationDate(rs.getString("EXPIRATION_DATE"));
				dto.setNotice(rs.getString("NOTICE"));
				dto.setFreshPercent(rs.getInt("FRESH_PERCENT"));
				dto.setItemNo(rs.getString("ITEM_NO"));
				
				dto.setShopName(rs.getString("SHOP_NAME"));
				dto.setSellerId(rs.getString("SELLER_ID"));
				dto.setItemName(rs.getString("ITEM_NAME"));
				dto.setItemPrice(rs.getInt("ITEM_PRICE"));
				dto.setSalesUnit(rs.getString("SALES_UNIT"));
				dto.setItemOrigin(rs.getString("ITEM_ORIGIN"));
				dto.setItemStock(rs.getInt("ITEM_STOCK"));
				dto.setItemImg(rs.getString("ITEM_IMG"));
				dto.setItemCustScore(rs.getDouble("ITEM_CUST_SCORE"));
				dto.setItemInputDate(rs.getString("ITEM_INPUT_DATE"));
				dto.setDiscRate(rs.getInt("DISC_RATE"));
				dto.setItemTakeit(rs.getString("ITEM_TAKEIT"));
		
                
				itemList.add(dto);
				System.out.println("리스트");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",7);
			message.setLinkTitle("메인으로");
			message.setUrl("/takeit/index");
			throw new CommonException(message);
		}
		JdbcTemplate.close(rs);
		JdbcTemplate.close(stmt);
	}
/**
 * 상품 상세조회	
 */
	public void searchItem(Connection conn, Item dto) throws CommonException {
		String sql = "select a.item_no , a.seller_id , a.item_name , a.item_price"+
		          ", a.sales_unit, a.item_origin , a.item_stock	, a.item_img , a.item_cust_score" +
		          ", a.item_input_date , a.disc_rate , a.item_takeit , a.item_category_no , b.item_category_name"+
		          ", b.expiration_date, b.fresh_percent,b.notice, b.pack_type_no , c.pack_type_name,d.name as seller_name,d.shop_name "+
		          " from item a, item_category b , packing c, seller d"+
		          " where a.item_category_no =b.item_category_no and b.pack_type_no =c.pack_type_no and a.seller_id = d.seller_id "+
		          " and a.item_no = ? ";

		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, dto.getItemNo());
			rs = stmt.executeQuery();

			
			while (rs.next()) {
				//포장타입
				System.out.println("PACK_TYPE_NO = "+rs.getString("PACK_TYPE_NO"));
				//dto = new Item();
				dto.setPackTypeNo(rs.getString("PACK_TYPE_NO"));
				dto.setPackTypeName(rs.getString("PACK_TYPE_NAME"));
				
				dto.setItemCategoryNo(rs.getString("ITEM_CATEGORY_NO"));
				dto.setItemCategoryName(rs.getString("ITEM_CATEGORY_NAME"));
				dto.setExpirationDate(rs.getString("EXPIRATION_DATE"));
				dto.setNotice(rs.getString("NOTICE"));
				dto.setFreshPercent(rs.getInt("FRESH_PERCENT"));
				dto.setItemNo(rs.getString("ITEM_NO"));
				
				dto.setSellerId(rs.getString("SELLER_ID"));
				dto.setItemName(rs.getString("ITEM_NAME"));
				dto.setItemPrice(rs.getInt("ITEM_PRICE"));
				dto.setSalesUnit(rs.getString("SALES_UNIT"));
				dto.setItemOrigin(rs.getString("ITEM_ORIGIN"));
				dto.setItemStock(rs.getInt("ITEM_STOCK"));
				dto.setItemImg(rs.getString("ITEM_IMG"));
				dto.setItemCustScore(rs.getDouble("ITEM_CUST_SCORE"));
				dto.setItemInputDate(rs.getString("ITEM_INPUT_DATE"));
				dto.setDiscRate(rs.getInt("DISC_RATE"));
				dto.setItemTakeit(rs.getString("ITEM_TAKEIT"));
		
				//판매자
				dto.setSellerName(rs.getString("SELLER_NAME"));
				dto.setShopName(rs.getString("SHOP_NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error", 28);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}	
	}	
	

	/**
	 * 내가 등록한 상품상세보기
	 * @param conn
	 * @param dto 상품
	 */
	public void SellItem(Connection conn, Item dto) throws CommonException{
		String sql = "select a.item_no , a.seller_id , a.item_name , a.item_price"+
				", a.sales_unit, a.item_origin , a.item_stock	, a.item_img , a.item_cust_score" +
				", a.item_input_date , a.disc_rate , a.item_takeit , a.item_category_no , b.item_category_name"+
				", b.expiration_date, b.fresh_percent,b.notice, b.pack_type_no , c.pack_type_name,d.name as seller_name,d.shop_name "+
				" from item a, item_category b , packing c, seller d"+
				" where a.item_category_no =b.item_category_no and b.pack_type_no =c.pack_type_no and a.seller_id = d.seller_id "+
				" and a.seller_id = ? ";

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, dto.getSellerId());
			rs = stmt.executeQuery();

			if(rs.next()) {

				dto.setPackTypeNo(rs.getString("PACK_TYPE_NO"));
				dto.setPackTypeName(rs.getString("PACK_TYPE_NAME"));
				dto.setItemCategoryNo(rs.getString("ITEM_CATEGORY"));
				dto.setItemCategoryName(rs.getString("ITEM_CATEGORY_NAME"));
				dto.setExpirationDate(rs.getString("EXPIRATION_DATE"));
				dto.setNotice(rs.getString("NOTICE"));
				dto.setFreshPercent(rs.getInt("FRESH_PERCENT"));
				dto.setItemNo(rs.getString("ITEM_NO "));			
				dto.setSellerId(rs.getString("SELLER_ID"));			
				dto.setItemName(rs.getString("ITEM_NAME"));			
				dto.setItemPrice(rs.getInt("ITEM_PRICE"));
				dto.setSalesUnit(rs.getString("SALES_UNIT"));			
				dto.setItemOrigin(rs.getString("ITEM_ORIGIN"));			
				dto.setItemStock(rs.getInt("ITEM_STOCK"));			
				dto.setItemImg(rs.getString("ITEM_IMG"));			
				dto.setItemCustScore(rs.getInt("ITEM_CUST_SCORE"));			
				dto.setItemInputDate(rs.getString("ITEM_INPUT_DATE"));			
				dto.setDiscRate(rs.getInt("DISC_RATE"));			
				dto.setItemTakeit(rs.getString("ITEM_TAKEIT"));			

			}

		}catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error",25);
			message.setLinkTitle("마이페이지로 이동");
			message.setUrl("/takeit/member/myPage.jsp");
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}
		/**
		 * 등록상품내역변경
		 * @param conn
		 * @param dto 상품
		 */
		public void updateSellItem (Connection conn,Item dto)throws CommonException{
			String sql = "update item set  ITEM_NO=? ,ITEM_NAME=?,SALES_UNIT=?,"
						 + "ITEM_ORIGIN=? where seller_id=? ";
			
			PreparedStatement stmt = null;
			
			try {
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, dto.getPackTypeNo());
				stmt.setString(2, dto.getPackTypeName());
				
				stmt.setString(3, dto.getItemCategoryNo());
				stmt.setString(4, dto.getItemCategoryName());
				stmt.setString(5, dto.getExpirationDate());
				stmt.setString(6, dto.getNotice());
				stmt.setInt(7, dto.getFreshPercent());
				stmt.setString(8, dto.getItemNo());
				stmt.setString(9, dto.getItemName());
				stmt.setInt(10, dto.getItemPrice());
				stmt.setString(11, dto.getSalesUnit());
				stmt.setString(12, dto.getItemOrigin());
				stmt.setInt(13, dto.getItemStock());
				stmt.setString(14, dto.getItemImg());
				stmt.setDouble(15, dto.getItemCustScore());
				
				stmt.setString(16, dto.getItemInputDate());
				stmt.setInt(17, dto.getDiscRate());
				stmt.setString(18, dto.getItemTakeit());
				stmt.setString(19, dto.getSellerName());
				stmt.setString(20, dto.getShopName());
	
				
				stmt.executeUpdate();
				
				
			}catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();

				MessageEntity message = new MessageEntity("error",26);
				message.setLinkTitle("내가 등록한상품보기");
				message.setUrl("/takeit/item/mySellList.jsp");
				throw new CommonException(message);
			}finally {
				JdbcTemplate.close(stmt);
			}

		}
		/**카테고리별 상품 리스트*/
		public void getCategoryItemList(Connection con, ArrayList<Item> categoryItemList, String categoryNo, String categoryName) throws CommonException{
			System.out.println("[debug]카테고리 상품 목록 dao 요청");
			String sql = "SELECT * "
					+ "FROM ITEM I LEFT OUTER JOIN ITEM_CATEGORY IC "
					+ "ON(I.ITEM_CATEGORY_NO=IC.ITEM_CATEGORY_NO) "
					+ "LEFT OUTER JOIN SELLER S ON(I.SELLER_ID=S.SELLER_ID) "
					+ "WHERE I.ITEM_CATEGORY_NO=?";
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, categoryNo);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Item dto = new Item();
					dto.setItemCategoryNo(rs.getString("ITEM_CATEGORY_NO"));
					dto.setItemCategoryName(rs.getString("ITEM_CATEGORY_NAME"));
					dto.setFreshPercent(rs.getInt("FRESH_PERCENT"));
					dto.setItemNo(rs.getString("ITEM_NO"));
					
					dto.setShopName(rs.getString("SHOP_NAME"));
					dto.setSellerId(rs.getString("SELLER_ID"));
					dto.setItemName(rs.getString("ITEM_NAME"));
					dto.setItemPrice(rs.getInt("ITEM_PRICE"));
					dto.setItemImg(rs.getString("ITEM_IMG"));
					dto.setItemCustScore(rs.getDouble("ITEM_CUST_SCORE"));
					dto.setDiscRate(rs.getInt("DISC_RATE"));
					
					categoryItemList.add(dto);
					categoryName = rs.getString("ITEM_CATEGORY_NAME");
				}
				System.out.println("[debug]카테고리: "+categoryName);
				System.out.println("[debug]카테고리 상품 목록 dao 요청 완료");
				
			} catch (SQLException e) {
				System.out.println("[debug]카테고리 상품 목록 dao 요청 실패");
				System.out.println(e.getMessage());
				e.printStackTrace();
				
				MessageEntity message = new MessageEntity("error",8);
				message.setLinkTitle("메인으로");
				message.setUrl("/takeit/index");
				throw new CommonException(message);
			}
			JdbcTemplate.close(rs);
			JdbcTemplate.close(pstmt);
		}

		/**
		 * 상품삭제
		 */
		public void deleteItem(Connection conn, Item dto) {
			
			String sql = "delete from Item where seller_id=? and Item_no=?";
			
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			try {
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, dto.getSellerId());
				stmt.setString(2, dto.getItemNo());
				
				stmt.executeUpdate();
			}catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}finally {
				JdbcTemplate.close(rs);
				JdbcTemplate.close(stmt);
			}		
		}
}
