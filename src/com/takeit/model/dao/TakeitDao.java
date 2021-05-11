package com.takeit.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import com.oreilly.servlet.multipart.ExceededSizeException;
import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dto.Member;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Order;
import com.takeit.model.dto.ShopLoc;
import com.takeit.model.dto.Takeit;
import com.takeit.model.dto.TakeitItem;
import com.takeit.util.Utility;

/**
 * 잇거래 테이블에 대한 TakeitDao 클래스
 * @author 김태경
 * @since jdk1.8
 * @version v2.0 2021/05/10
 */
public class TakeitDao {
	private static TakeitDao instance = new TakeitDao();

	public static TakeitDao getInstance() {
		return instance;
	}

	/**
	 * 판매자회원 잇거래 상품 목록조회
	 * 
	 * @param shopLocCode    상점구역코드
	 * @param takeitItemList 잇거래상품목록
	 */
	public void searchTakeitItemList(Connection conn, String shopLocCode, ArrayList<TakeitItem> takeitItemList)
			throws CommonException {
		String sql = "SELECT * "
				+ "FROM ITEM JOIN ITEM_CATEGORY USING (ITEM_CATEGORY_NO) JOIN PACKING USING (PACK_TYPE_NO) JOIN SELLER USING (SELLER_ID) JOIN TAKEIT USING(SHOP_LOC_CODE) "
				+ "WHERE SELLER_ID IN ( " 
				+ "		SELECT SELLER_ID " 
				+ "		FROM SELLER "
				+ "		WHERE SHOP_LOC_CODE IN ( " 
				+ "			SELECT SHOP_LOC_CODE " 
				+ "			FROM TAKEIT "
				+ "			WHERE TAKEIT_ALIVE = 'T' AND SHOP_LOC_CODE = ? " 
				+ "			) " 
				+ "		) "
				+ "AND ITEM_TAKEIT = 'T' AND MEMBER_LOC_NO = '0' " 
				+ "ORDER BY SUBSTR(ITEM.ITEM_NO, 3) DESC ";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, shopLocCode);
			rs = stmt.executeQuery();

			TakeitItem takeitItem = null;
			while (rs.next()) {
				takeitItem = new TakeitItem();

				takeitItem.setItemNo(rs.getString("item_No"));
				takeitItem.setSellerId(rs.getString("seller_Id"));
				takeitItem.setItemName(rs.getString("item_Name"));
				takeitItem.setItemPrice(rs.getInt("item_Price"));
				takeitItem.setItemImg(rs.getString("item_Img"));
				takeitItem.setItemCustScore(rs.getDouble("item_Cust_Score"));
				takeitItem.setItemInputDate(rs.getString("item_Input_Date"));

				Date firstDate = Utility.convertStringToDate(Utility.getCurrentDate(), "yyyy-MM-dd HH:mm:ss");
				Date secondDate = Utility.convertStringToDate(rs.getString("item_input_date"), "yyyy-MM-dd HH:mm:ss");
				int a = Utility.getDayBetweenAandB(firstDate, secondDate);
				int b = Integer.valueOf(rs.getString("expiration_date"));
				int c = 100 - (int) (((double) (b - a) / b) * 100);
				if (c > 100) {
					c = 100;
				}
				takeitItem.setDiscRate(c);
				takeitItem.setItemTakeit(rs.getString("item_TakeIt"));

				takeitItem.setTakeitNo(rs.getString("takeit_No"));
				takeitItem.setTakeitPrice(rs.getInt("takeit_Price"));
				takeitItem.setTakeitCurrPrice(rs.getInt("takeit_Curr_Price"));
				takeitItem.setTakeitDate(rs.getString("takeit_date"));
				takeitItem.setTakeitCustScore(rs.getDouble("takeit_Cust_Score"));
				takeitItem.setTakeitAlive(rs.getString("takeit_Alive"));
				takeitItem.setMemberLocNo(rs.getString("member_Loc_No"));
				takeitItem.setShopLocCode(rs.getString("shop_Loc_Code"));

				takeitItem.setSellerName(rs.getString("name"));
				takeitItem.setShopName(rs.getString("Shop_name"));

				takeitItemList.add(takeitItem);
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}

	/** 비회원 잇거래상품목록 조회 */
	public void searchTakeitItemList(Connection conn, ArrayList<TakeitItem> takeitItemList) throws CommonException {
		String sql = "SELECT * "
				+ "FROM ITEM JOIN ITEM_CATEGORY USING (ITEM_CATEGORY_NO) JOIN PACKING USING (PACK_TYPE_NO) JOIN SELLER USING (SELLER_ID) JOIN TAKEIT USING(SHOP_LOC_CODE) "
				+ "WHERE SELLER_ID IN ( " 
				+ "		SELECT SELLER_ID " 
				+ "		FROM SELLER "
				+ "		WHERE SHOP_LOC_CODE IN ( " 
				+ "			SELECT SHOP_LOC_CODE " 
				+ "			FROM TAKEIT "
				+ "			WHERE TAKEIT_ALIVE = 'T'" 
				+ "			) " 
				+ "		) "
				+ "AND ITEM_TAKEIT = 'T' AND MEMBER_LOC_NO = '0' " + "ORDER BY SUBSTR(ITEM.ITEM_NO, 3) DESC ";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			TakeitItem takeitItem = null;
			while (rs.next()) {
				takeitItem = new TakeitItem();

				takeitItem.setItemNo(rs.getString("item_No"));
				takeitItem.setSellerId(rs.getString("seller_Id"));
				takeitItem.setItemName(rs.getString("item_Name"));
				takeitItem.setItemPrice(rs.getInt("item_Price"));
				takeitItem.setItemImg(rs.getString("item_Img"));
				takeitItem.setItemCustScore(rs.getDouble("item_Cust_Score"));
				takeitItem.setItemInputDate(rs.getString("item_Input_Date"));

				Date firstDate = Utility.convertStringToDate(Utility.getCurrentDate(), "yyyy-MM-dd HH:mm:ss");
				Date secondDate = Utility.convertStringToDate(rs.getString("item_input_date"), "yyyy-MM-dd HH:mm:ss");
				int a = Utility.getDayBetweenAandB(firstDate, secondDate);
				int b = Integer.valueOf(rs.getString("expiration_date"));
				int c = 100 - (int) (((double) (b - a) / b) * 100);
				if (c > 100) {
					c = 100;
				}
				takeitItem.setDiscRate(c);
				takeitItem.setItemTakeit(rs.getString("item_TakeIt"));

				takeitItem.setTakeitNo(rs.getString("takeit_No"));
				takeitItem.setTakeitPrice(rs.getInt("takeit_Price"));
				takeitItem.setTakeitCurrPrice(rs.getInt("takeit_Curr_Price"));
				takeitItem.setTakeitDate(rs.getString("takeit_date"));
				takeitItem.setTakeitCustScore(rs.getDouble("takeit_Cust_Score"));
				takeitItem.setTakeitAlive(rs.getString("takeit_Alive"));
				takeitItem.setMemberLocNo(rs.getString("member_Loc_No"));
				takeitItem.setShopLocCode(rs.getString("shop_Loc_Code"));

				takeitItem.setSellerName(rs.getString("name"));
				takeitItem.setShopName(rs.getString("Shop_name"));

				takeitItemList.add(takeitItem);
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}

	/**
	 * 일반회원 잇거래상품목록 조회
	 * @param member         회원객체
	 * @param takeitItemList 잇거래상품목록
	 */
	public void searchTakeitItemList(Connection conn, Member member, ArrayList<TakeitItem> takeitItemList)
			throws CommonException {
		String sql = "SELECT * "
				+ "FROM ITEM JOIN ITEM_CATEGORY USING (ITEM_CATEGORY_NO) JOIN PACKING USING (PACK_TYPE_NO) JOIN SELLER USING (SELLER_ID) JOIN TAKEIT USING(SHOP_LOC_CODE) "
				+ "WHERE SELLER_ID IN ( " 
				+ "		SELECT SELLER_ID " 
				+ "		FROM SELLER "
				+ "		WHERE SHOP_LOC_CODE IN ( " 
				+ "			SELECT SHOP_LOC_CODE " 
				+ "			FROM TAKEIT "
				+ "			WHERE TAKEIT_ALIVE = 'T' AND SHOP_LOC_CODE = ? " 
				+ "			) " 
				+ "		) "
				+ "AND ITEM_TAKEIT = 'T' AND MEMBER_LOC_NO = ? " 
				+ "ORDER BY SUBSTR(ITEM.ITEM_NO, 3) DESC ";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getShopLocCode());
			stmt.setString(2, member.getMemberLocNo());
			rs = stmt.executeQuery();

			TakeitItem takeitItem = null;
			while (rs.next()) {
				takeitItem = new TakeitItem();

				takeitItem.setItemNo(rs.getString("item_No"));
				takeitItem.setSellerId(rs.getString("seller_Id"));
				takeitItem.setItemName(rs.getString("item_Name"));
				takeitItem.setItemPrice(rs.getInt("item_Price"));
				takeitItem.setItemImg(rs.getString("item_Img"));
				takeitItem.setItemCustScore(rs.getDouble("item_Cust_Score"));
				takeitItem.setItemInputDate(rs.getString("item_Input_Date"));

				Date firstDate = Utility.convertStringToDate(Utility.getCurrentDate(), "yyyy-MM-dd HH:mm:ss");
				Date secondDate = Utility.convertStringToDate(rs.getString("item_input_date"), "yyyy-MM-dd HH:mm:ss");
				int a = Utility.getDayBetweenAandB(firstDate, secondDate);
				int b = Integer.valueOf(rs.getString("expiration_date"));
				int c = 100 - (int) (((double) (b - a) / b) * 100);
				if (c > 100) {
					c = 100;
				}
				takeitItem.setDiscRate(c);
				takeitItem.setItemTakeit(rs.getString("item_TakeIt"));

				takeitItem.setTakeitNo(rs.getString("takeit_No"));
				takeitItem.setTakeitPrice(rs.getInt("takeit_Price"));
				takeitItem.setTakeitCurrPrice(rs.getInt("takeit_Curr_Price"));
				takeitItem.setTakeitDate(rs.getString("takeit_date"));
				takeitItem.setTakeitCustScore(rs.getDouble("takeit_Cust_Score"));
				takeitItem.setTakeitAlive(rs.getString("takeit_Alive"));
				takeitItem.setMemberLocNo(rs.getString("member_Loc_No"));
				takeitItem.setShopLocCode(rs.getString("shop_Loc_Code"));

				takeitItem.setSellerName(rs.getString("name"));
				takeitItem.setShopName(rs.getString("Shop_name"));

				takeitItemList.add(takeitItem);
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}

	/**
	 * 잇거래상품 상세 조회
	 * @param takeitItem 잇거래 상품
	 */
	public void searchTakeitItem(Connection conn, TakeitItem takeitItem) throws CommonException {
		String sql = "SELECT * "
				+ "FROM ITEM JOIN ITEM_CATEGORY USING (ITEM_CATEGORY_NO) JOIN PACKING USING (PACK_TYPE_NO) JOIN SELLER USING (SELLER_ID) JOIN TAKEIT USING(SHOP_LOC_CODE) "
				+ "WHERE ITEM_NO = ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, takeitItem.getItemNo());
			rs = stmt.executeQuery();

			if (rs.next()) {

				takeitItem.setPackTypeNo(rs.getString("pack_Type_No"));
				takeitItem.setPackTypeName(rs.getString("pack_Type_Name"));

				takeitItem.setItemCategoryNo(rs.getString("item_Category_No"));
				takeitItem.setItemCategoryName(rs.getString("item_Category_Name"));
				takeitItem.setExpirationDate(rs.getString("expiration_Date"));
				takeitItem.setNotice(rs.getString("notice"));
				takeitItem.setFreshPercent(rs.getInt("fresh_Percent"));

				takeitItem.setItemNo(rs.getString("item_No"));
				takeitItem.setSellerId(rs.getString("seller_Id"));
				takeitItem.setItemName(rs.getString("item_Name"));
				takeitItem.setItemPrice(rs.getInt("item_Price"));
				takeitItem.setSalesUnit(rs.getString("sales_Unit"));
				takeitItem.setItemOrigin(rs.getString("item_Origin"));
				takeitItem.setItemStock(rs.getInt("item_Stock"));
				takeitItem.setItemImg(rs.getString("item_Img"));
				takeitItem.setItemCustScore(rs.getDouble("item_Cust_Score"));
				takeitItem.setItemInputDate(rs.getString("item_Input_Date"));

				Date secondDate = Utility.convertStringToDate(rs.getString("item_input_date"), "yyyy-MM-dd HH:mm:ss");
				Date firstDate = Utility.convertStringToDate(Utility.getCurrentDate(), "yyyy-MM-dd HH:mm:ss");
				int a = Utility.getDayBetweenAandB(firstDate, secondDate);
				int b = Integer.valueOf(rs.getString("expiration_date"));
				int c = 100 - (int) (((double) (b - a) / b) * 100);
				if (c > 100) {
					c = 100;
				}
				takeitItem.setDiscRate(c);
				takeitItem.setItemTakeit(rs.getString("item_TakeIt"));

				takeitItem.setTakeitNo(rs.getString("takeit_No"));
				takeitItem.setTakeitPrice(rs.getInt("takeit_Price"));
				takeitItem.setTakeitCurrPrice(rs.getInt("takeit_Curr_Price"));
				takeitItem.setTakeitDate(rs.getString("takeit_date"));
				takeitItem.setTakeitCustScore(rs.getDouble("takeit_Cust_Score"));
				takeitItem.setTakeitAlive(rs.getString("takeit_Alive"));
				takeitItem.setShopLocCode(rs.getString("shop_Loc_Code"));

				takeitItem.setSellerName(rs.getString("name"));
				takeitItem.setShopName(rs.getString("Shop_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}

	/** 상점구역목록 전체 조회 */
	public void searchShopLocList(Connection conn, ArrayList<ShopLoc> shopLocList) throws CommonException {
		String sql = "SELECT * FROM SHOP_LOC";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			ShopLoc shopLoc = null;
			while (rs.next()) {
				shopLoc = new ShopLoc();
				shopLoc.setShopLocCode(rs.getString("shop_Loc_Code"));
				shopLoc.setShopLocName(rs.getString("shop_Loc_Name"));
				shopLoc.setShopLocLat(rs.getString("shop_Loc_Lat"));
				shopLoc.setShopLocLng(rs.getString("shop_Loc_Lng"));
				shopLocList.add(shopLoc);
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}

	/**
	 * 회원구역 존재여부
	 * 
	 * @param member 회원객체
	 * @return 존재시 true, 미존재시 false
	 * @throws CommonException
	 */
	public boolean isValidMemberLocNo(Connection conn, Member member) throws CommonException {
		String sql = "SELECT 1 " + "FROM MEMBER_LOC " + "WHERE MEMBER_LOC_NO = ? AND SHOP_LOC_CODE = ? ";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberLocNo());
			stmt.setString(2, member.getShopLocCode());

			rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error", 13);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
		return false;
	}

	/**
	 * 회원구역 등록
	 * 
	 * @param member 회원객체
	 */
	public void addMemberLocNo(Connection conn, Member member) throws CommonException {
		String sql = "INSERT INTO MEMBER_LOC VALUES(?,?,?) ";

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberLocNo());
			stmt.setString(2, member.getShopLocCode());
			stmt.setString(3, member.getShopLocCode() + member.getMemberLocNo());

			int row = stmt.executeUpdate();
			if (row == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 11);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}
	}

	/**
	 * 회원구역 초기화 메서드 상점구역등록에 따른 회원구역 초기화 등록
	 * @param shopLoc 상점구역
	 */
	public void addMemberLoc(Connection conn, ShopLoc shopLoc) throws CommonException {
		String sql = "BEGIN " + "FOR i IN 0 .. 99 LOOP " + "INSERT INTO MEMBER_LOC VALUES (i, ?, ?||'-'||i); "
				+ "END LOOP; " + "END; ";

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, shopLoc.getShopLocCode());
			stmt.setString(2, shopLoc.getShopLocCode());

			int row = stmt.executeUpdate();
			if (row == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 11);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}
	}

	/** 상점구역 등록 */
	public void addShopLoc(Connection conn, ShopLoc shopLoc) throws CommonException {
		String sql = "INSERT INTO SHOP_LOC VALUES(?,?,?,?) ";

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, shopLoc.getShopLocCode());
			stmt.setString(2, shopLoc.getShopLocName());
			stmt.setString(3, shopLoc.getShopLocLat());
			stmt.setString(4, shopLoc.getShopLocLng());

			int row = stmt.executeUpdate();
			if (row == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error", 11);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}
	}

	/**
	 * 잇거래 존재여부 확인 메서드
	 * @param shopLocCode 상점구역코드
	 * @return 존재하면 1, 미존재시 0
	 */
	public int searchExistTakeit(Connection conn, String shopLocCode) throws CommonException {
		String sql = "SELECT 1 " + "FROM TAKEIT " + "WHERE TAKEIT_ALIVE = 'T' AND SHOP_LOC_CODE = ? ";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, shopLocCode);

			rs = stmt.executeQuery();
			if (rs.next()) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 13);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
		return 0;
	}

	/**
	 * 잇거래 등록
	 * 
	 * @param takeit 잇거래 객체
	 */
	public void insertTakeit(Connection conn, Takeit takeit) throws CommonException {
		String sql = "BEGIN " + "FOR i IN 0 .. 99 LOOP " + "INSERT INTO TAKEIT "
				+ "VALUES ('TAKE' || TO_CHAR(SYSDATE,'YYYYMMDD') || LPAD(TAKEIT_SEQ.NEXTVAL, 6, '0') "
				+ ", ?, 0, SYSDATE, 0.0, 'T', i, ?); " + "END LOOP; " + "END; ";

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, takeit.getTakeitPrice());
			stmt.setString(2, takeit.getShopLocCode());

			int rows = stmt.executeUpdate();
			if (rows == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 11);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}
	}

	/** 일반회원의 회원구역 조회 */
	public void selectLoc(Connection conn, String memberId, Takeit takeit) throws CommonException {
		String sql = "SELECT MEMBER_LOC_NO, SHOP_LOC_CODE " + "FROM MEMBER " + "WHERE MEMBER_ID = ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);

			rs = stmt.executeQuery();
			if (rs.next()) {
				takeit.setMemberLocNo(rs.getString("MEMBER_LOC_NO"));
				takeit.setShopLocCode(rs.getString("SHOP_LOC_CODE"));
			}

		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 11);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}

	/** 회원구역번호와 일치하는 잇거래번호 반환 */
	public void selectTakeitNo(Connection conn, Takeit takeit) throws CommonException {
		String sql = "SELECT TAKEIT_NO " + "FROM TAKEIT " + "WHERE MEMBER_LOC_NO = ? AND SHOP_LOC_CODE = ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, takeit.getMemberLocNo());
			stmt.setString(2, takeit.getShopLocCode());

			rs = stmt.executeQuery();
			if (rs.next()) {
				takeit.setTakeitNo(rs.getString("TAKEIT_NO"));
			}

		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 11);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}

	/** 회원구역번호와 일치하는 잇거래번호 반환 */
	public void selectTakeitNo(Connection conn, TakeitItem takeitItem) throws CommonException {
		String sql = "SELECT TAKEIT_NO " + "FROM TAKEIT " + "WHERE MEMBER_LOC_NO = ? AND SHOP_LOC_CODE = ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, takeitItem.getMemberLocNo());
			stmt.setString(2, takeitItem.getShopLocCode());

			rs = stmt.executeQuery();
			if (rs.next()) {
				takeitItem.setTakeitNo(rs.getString("TAKEIT_NO"));
			}

		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 11);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}

	/** 잇거래 상세 등록 */
	public void insertTakeitDetail(Connection conn, Takeit takeit, Order order) throws CommonException {
		String sql = "INSERT INTO TAKEIT_DETAIL VALUES(?,?) ";

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, takeit.getTakeitNo());
			stmt.setString(2, order.getOrderNo());

			int row = stmt.executeUpdate();
			if (row == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 11);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}
	}

	/** 회원구역 변경 */
	public void updateMemberLoc(Connection conn, Member member) throws CommonException {
		String sql = "UPDATE MEMBER SET MEMBER_LOC_NO = ? , SHOP_LOC_CODE = ? where member_ID = ?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberLocNo());
			stmt.setString(2, member.getShopLocCode());
			stmt.setString(3, member.getMemberId());
			int row = stmt.executeUpdate();
			if (row == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 2);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}
	}

	/** 잇거래 현재금액 변경 */
	public void updateTakeitCurrPrice(Connection conn, Takeit takeit, Order order) throws CommonException {
		String sql = "UPDATE TAKEIT SET TAKEIT_CURR_PRICE = TAKEIT_CURR_PRICE + ? " + " WHERE TAKEIT_NO = ? ";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, order.getOrderPrice());
			stmt.setString(2, takeit.getTakeitNo());
			int row = stmt.executeUpdate();
			if (row == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 2);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}
	}

	/** 잇거래 현재금액 조회 */
	public void searchTakeitCurrPrice(Connection conn, TakeitItem takeitItem) throws CommonException {
		String sql = "SELECT TAKEIT_CURR_PRICE, TAKEIT_PRICE " + "FROM TAKEIT " + "WHERE TAKEIT_NO = ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, takeitItem.getTakeitNo());

			rs = stmt.executeQuery();
			if (rs.next()) {
				takeitItem.setTakeitCurrPrice(rs.getInt("TAKEIT_CURR_PRICE"));
				takeitItem.setTakeitPrice(rs.getInt("TAKEIT_PRICE"));
			}

		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}

	/** 상점구역 조회 */
	public void searchshopLocName(Connection conn, TakeitItem takeitItem) throws CommonException {
		String sql = "SELECT SHOP_LOC_NAME FROM SHOP_LOC WHERE SHOP_LOC_CODE = ? ";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, takeitItem.getShopLocCode());

			rs = stmt.executeQuery();
			if (rs.next()) {
				takeitItem.setShopLocName(rs.getString("shop_Loc_Name"));
			}

		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}

	/**
	 * 지역상점 삭제
	 * @param takeit 잇거래 객체
	 */
	public void deleteShopLoc(Connection conn, Takeit takeit) throws CommonException {
		String sql = "DELETE FROM SHOP_LOC WHERE SHOP_LOC_CODE = ? ";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, takeit.getShopLocCode());
			int rows = stmt.executeUpdate();
			if (rows == 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 45);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}
	}

	
	/**
	 * 삭제 지역상점 해당 회원 상점코드 변경
	 * @param takeit 잇거래 객체
	 */
	public void updateMemberLocNull(Connection conn, Takeit takeit) throws CommonException {
		String sql = "UPDATE MEMBER SET SHOP_LOC_CODE = NULL WHERE SHOP_LOC_CODE = ?";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, takeit.getShopLocCode());
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 2);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}
	}
	
	/** 만료된 잇거래 목록 조회 */
	public void searchTakeitExpiredList(Connection conn, ArrayList<Takeit> takeitList) throws CommonException {
		String sql = "SELECT SHOP_LOC_CODE, TAKEIT_NO, TAKEIT_PRICE, TAKEIT_CURR_PRICE, TO_CHAR(TAKEIT_DATE, 'yyyy-mm-dd') TAKEIT_DATE, TAKEIT_CUST_SCORE, TAKEIT_ALIVE, MEMBER_LOC_NO, SHOP_LOC_NAME, TO_CHAR(TAKEIT_DATE+7, 'yyyy-mm-dd') TAKEIT_END_DATE"
				+ " FROM TAKEIT JOIN SHOP_LOC USING(SHOP_LOC_CODE) "
				+ " WHERE TAKEIT_ALIVE = 'T' AND (TAKEIT_PRICE <= TAKEIT_CURR_PRICE "
				+ " OR TAKEIT_DATE + 7 <= SYSDATE)";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);

			rs = stmt.executeQuery();
			Takeit dto = null;
			while (rs.next()) {
				dto = new Takeit();
				dto.setMemberLocNo(rs.getString("member_Loc_No"));
				dto.setShopLocCode(rs.getString("shop_Loc_Code"));
				dto.setShopLocName(rs.getString("shop_Loc_Name"));
				dto.setTakeitAlive(rs.getString("takeit_alive"));
				dto.setTakeitCurrPrice(rs.getInt("takeit_Curr_Price"));
				dto.setTakeitDate(rs.getString("takeit_Date"));
				dto.setTakeitEndDate(rs.getString("takeit_End_Date"));
				dto.setTakeitNo(rs.getString("takeit_No"));
				dto.setTakeitPrice(rs.getInt("takeit_Price"));
				takeitList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}

	/** 진행중 잇거래 목록 조회 */
	public void searchTakeitLiveList(Connection conn, ArrayList<Takeit> takeitList) throws CommonException {
		String sql = "SELECT SHOP_LOC_CODE, TAKEIT_NO, TAKEIT_PRICE, TAKEIT_CURR_PRICE, TO_CHAR(TAKEIT_DATE, 'yyyy-mm-dd') TAKEIT_DATE, TAKEIT_CUST_SCORE, TAKEIT_ALIVE, MEMBER_LOC_NO, SHOP_LOC_NAME, TO_CHAR(TAKEIT_DATE+7, 'yyyy-mm-dd') TAKEIT_END_DATE"
				+ " FROM TAKEIT JOIN SHOP_LOC USING(SHOP_LOC_CODE) "
				+ " WHERE TAKEIT_ALIVE = 'T' AND (TAKEIT_PRICE >= TAKEIT_CURR_PRICE "
				+ " AND TAKEIT_DATE + 7 >= SYSDATE)";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);

			rs = stmt.executeQuery();
			Takeit dto = null;
			while (rs.next()) {
				dto = new Takeit();
				dto.setMemberLocNo(rs.getString("member_Loc_No"));
				dto.setShopLocCode(rs.getString("shop_Loc_Code"));
				dto.setShopLocName(rs.getString("shop_Loc_Name"));
				dto.setTakeitAlive(rs.getString("takeit_alive"));
				dto.setTakeitCurrPrice(rs.getInt("takeit_Curr_Price"));
				dto.setTakeitDate(rs.getString("takeit_Date"));
				dto.setTakeitEndDate(rs.getString("takeit_End_Date"));
				dto.setTakeitNo(rs.getString("takeit_No"));
				dto.setTakeitPrice(rs.getInt("takeit_Price"));
				takeitList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}
	
	/** 종료된 잇거래 목록 조회 */
	public void searchTakeitDeadList(Connection conn, ArrayList<Takeit> takeitList) throws CommonException {
		String sql = "SELECT SHOP_LOC_CODE, TAKEIT_NO, TAKEIT_PRICE, TAKEIT_CURR_PRICE, TO_CHAR(TAKEIT_DATE, 'yyyy-mm-dd') TAKEIT_DATE, TAKEIT_CUST_SCORE, TAKEIT_ALIVE, MEMBER_LOC_NO, SHOP_LOC_NAME, TO_CHAR(TAKEIT_DATE+7, 'yyyy-mm-dd') TAKEIT_END_DATE"
				+ " FROM TAKEIT JOIN SHOP_LOC USING(SHOP_LOC_CODE) " + " WHERE TAKEIT_ALIVE = 'F' ";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);

			rs = stmt.executeQuery();
			Takeit dto = null;
			while (rs.next()) {
				dto = new Takeit();
				dto.setMemberLocNo(rs.getString("member_Loc_No"));
				dto.setShopLocCode(rs.getString("shop_Loc_Code"));
				dto.setShopLocName(rs.getString("shop_Loc_Name"));
				dto.setTakeitAlive(rs.getString("takeit_alive"));
				dto.setTakeitCurrPrice(rs.getInt("takeit_Curr_Price"));
				dto.setTakeitDate(rs.getString("takeit_Date"));
				dto.setTakeitEndDate(rs.getString("takeit_End_Date"));
				dto.setTakeitNo(rs.getString("takeit_No"));
				dto.setTakeitPrice(rs.getInt("takeit_Price"));
				takeitList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}

	}

	/** 전체 잇거래 목록 조회 */
	public void searchTakeitAllList(Connection conn, ArrayList<Takeit> takeitList) throws CommonException {
		String sql = "SELECT SHOP_LOC_CODE, TAKEIT_NO, TAKEIT_PRICE, TAKEIT_CURR_PRICE, TO_CHAR(TAKEIT_DATE, 'yyyy-mm-dd') TAKEIT_DATE, TAKEIT_CUST_SCORE, TAKEIT_ALIVE, MEMBER_LOC_NO, SHOP_LOC_NAME, TO_CHAR(TAKEIT_DATE+7, 'yyyy-mm-dd') TAKEIT_END_DATE"
				+ " FROM TAKEIT JOIN SHOP_LOC USING(SHOP_LOC_CODE) ";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);

			rs = stmt.executeQuery();
			Takeit dto = null;
			while (rs.next()) {
				dto = new Takeit();
				dto.setMemberLocNo(rs.getString("member_Loc_No"));
				dto.setShopLocCode(rs.getString("shop_Loc_Code"));
				dto.setShopLocName(rs.getString("shop_Loc_Name"));
				dto.setTakeitAlive(rs.getString("takeit_alive"));
				dto.setTakeitCurrPrice(rs.getInt("takeit_Curr_Price"));
				dto.setTakeitDate(rs.getString("takeit_Date"));
				dto.setTakeitEndDate(rs.getString("takeit_End_Date"));
				dto.setTakeitNo(rs.getString("takeit_No"));
				dto.setTakeitPrice(rs.getInt("takeit_Price"));
				takeitList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}

	}

	/** 잇거래 삭제 */
	public void deleteTakeit(Connection conn, String takeitNo) throws CommonException {
		String sql = "UPDATE TAKEIT SET TAKEIT_ALIVE = 'F' WHERE TAKEIT_NO = ? "; 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, takeitNo);
			int rows = stmt.executeUpdate();
			
			if(rows == 0) {
				throw new ExceededSizeException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 14);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}		
	}

	/** 비회원 잇거래 상품 개수 조회 */
	public int selectTakeitItemListCount(Connection conn) throws CommonException {
		String sql = "SELECT COUNT(*) count "
				+ "FROM ITEM JOIN ITEM_CATEGORY USING (ITEM_CATEGORY_NO) JOIN PACKING USING (PACK_TYPE_NO) JOIN SELLER USING (SELLER_ID) JOIN TAKEIT USING(SHOP_LOC_CODE) "
				+ "WHERE SELLER_ID IN ( "
				+ "		SELECT SELLER_ID "
				+ "		FROM SELLER "
				+ "		WHERE SHOP_LOC_CODE IN ( "
				+ "			SELECT SHOP_LOC_CODE "
				+ "			FROM TAKEIT "
				+ "			WHERE TAKEIT_ALIVE = 'T'"
				+ "			) "
				+ "		) "
				+ "AND ITEM_TAKEIT = 'T' AND MEMBER_LOC_NO = '0' "
				+ "ORDER BY SUBSTR(ITEM.ITEM_NO, 3) DESC ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
		return 0;
	}
	
	
	/** 일반회원 잇거래 상품 개수 조회 */
	public int selectTakeitItemListCount(Connection conn, Member member) throws CommonException {
		String sql = "SELECT COUNT(*) count "
				+ "FROM ITEM JOIN ITEM_CATEGORY USING (ITEM_CATEGORY_NO) JOIN PACKING USING (PACK_TYPE_NO) JOIN SELLER USING (SELLER_ID) JOIN TAKEIT USING(SHOP_LOC_CODE) "
				+ "WHERE SELLER_ID IN ( "
				+ "		SELECT SELLER_ID "
				+ "		FROM SELLER "
				+ "		WHERE SHOP_LOC_CODE IN ( "
				+ "			SELECT SHOP_LOC_CODE "
				+ "			FROM TAKEIT "
				+ "			WHERE TAKEIT_ALIVE = 'T' AND SHOP_LOC_CODE = ? "
				+ "			) "
				+ "		) "
				+ "AND ITEM_TAKEIT = 'T' AND MEMBER_LOC_NO = ? "
				+ "ORDER BY SUBSTR(ITEM.ITEM_NO, 3) DESC ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getShopLocCode());
			stmt.setString(2, member.getMemberLocNo());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
		return 0;
	}
	
	/** 판매자회원 잇거래상품개수 조회 */
	public int selectTakeitItemListCount(Connection conn, String shopLocCode) throws CommonException {
		String sql = "SELECT COUNT(*) count "
				+ "FROM ITEM JOIN ITEM_CATEGORY USING (ITEM_CATEGORY_NO) JOIN PACKING USING (PACK_TYPE_NO) JOIN SELLER USING (SELLER_ID) JOIN TAKEIT USING(SHOP_LOC_CODE) "
				+ "WHERE SELLER_ID IN ( "
				+ "		SELECT SELLER_ID "
				+ "		FROM SELLER "
				+ "		WHERE SHOP_LOC_CODE IN ( "
				+ "			SELECT SHOP_LOC_CODE "
				+ "			FROM TAKEIT "
				+ "			WHERE TAKEIT_ALIVE = 'T' AND SHOP_LOC_CODE = ? "
				+ "			) "
				+ "		) "
				+ "AND ITEM_TAKEIT = 'T' AND MEMBER_LOC_NO = '0' "
				+ "ORDER BY SUBSTR(ITEM.ITEM_NO, 3) DESC ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, shopLocCode);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 12);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
		return 0;
	}
}
