package com.takeit.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Seller;
import com.takeit.util.Utility;

/**
 * 일반 회원관리 컨트롤러
 * @author  임우진
 * @since   jdk1.8
 * @version v2.0
 */
public class SellerDao {

	/**
	 * 회원가입
	 */
	public void addSeller(Connection con, Seller seller) throws CommonException {
		
	String sql = "insert into seller values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	PreparedStatement stmt = null;
	
	String date = Utility.getCurrentDate("yyyy.MM.dd HH:mm:ss");
	
	try {
		stmt = con.prepareStatement(sql);
		
		stmt.setString(1, seller.getSellerId());
		stmt.setString(2, seller.getSellerPw());
		stmt.setString(3, seller.getName());
		stmt.setString(4, seller.getMobile());
		stmt.setString(5, seller.getEmail());
		stmt.setString(6, date);
		stmt.setString(7, seller.getPostNo());
		stmt.setString(8, seller.getAddress());
		stmt.setString(9, seller.getAddressDetail());
		stmt.setString(10, "S");
		stmt.setString(11, seller.getSellerNo());
		stmt.setString(12, seller.getShopMobile());
		stmt.setString(13, seller.getShopName());
		stmt.setDouble(14, 0.0);
		stmt.setString(15, seller.getShopKakaoId());
		stmt.setString(16, seller.getShopImg());
		stmt.setString(17, seller.getShopCategoryNo());
		stmt.setString(18, seller.getShopLocCode());
		int rows = stmt.executeUpdate();
		
		if(rows == 0) {
			throw new Exception();
		}
		
	} catch (Exception e) {
		e.printStackTrace();		
		MessageEntity message = new MessageEntity("error",33);
		message.setUrl("/takeit/seller/sellerInput.jsp");
		message.setLinkTitle("뒤로가기");
		throw new CommonException(message);
	}finally {
		JdbcTemplate.close(stmt);
	}
  }
	
	/**
	 * 로그인
	 */
	public void login(Connection con, Seller seller) throws CommonException {
		
		String sql = "select * from seller where seller_Id = ? and seller_Pw = ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, seller.getSellerId());
			stmt.setString(2, seller.getSellerPw());
			
			rs = stmt.executeQuery();

			if(rs.next()) {
				seller.setSellerId(rs.getString("seller_id"));
				seller.setSellerPw(rs.getString("seller_pw"));
				seller.setName(rs.getString("mobile"));
				seller.setEmail(rs.getString("email"));
				seller.setEntryDate(rs.getString("entry_date"));
				seller.setPostNo(rs.getString("postno"));
				seller.setAddress(rs.getString("address"));
				seller.setAddressDetail(rs.getString("address_detail"));
				seller.setGrade(rs.getString("grade"));
				seller.setSellerNo(rs.getString("seller_no"));
				seller.setShopMobile(rs.getString("shop_mobile"));
				seller.setShopName(rs.getString("shop_name"));
				seller.setCustScore(rs.getDouble("cust_score"));
				seller.setShopKakaoId(rs.getString("shop_kakao_id"));
				seller.setShopImg(rs.getString("shop_img"));
				seller.setShopCategoryNo(rs.getString("shop_category_no"));
				seller.setShopLocCode(rs.getString("shop_loc_code"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error",34);
			message.setUrl("/takeit/seller/sellerLogin.jsp");
			message.setLinkTitle("뒤로가기");
			throw new CommonException(message);
		}finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}
	
	/**
	 * 아이디 찾기
	 */
	public void idFind(Connection con, Seller seller) throws CommonException {
		
		String sql = "select seller_id, entry_date from seller where name = ? and email = ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, seller.getName());
			stmt.setString(2, seller.getEmail());
			
			rs = stmt.executeQuery();

			if(rs.next()) {
				seller.setSellerId(rs.getString("seller_id"));
				seller.setEntryDate(rs.getString("entry_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error",35);
			message.setUrl("/takeit/seller/sellerFindId.jsp");
			message.setLinkTitle("뒤로가기");
			throw new CommonException(message);
		}finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}
	
	/**
	 * 비밀번호 찾기
	 */
	public void pwFind(Connection con, String temporaryPw, Seller seller) throws CommonException {
		
		String sql = "update seller set seller_pw = ? where seller_id = ? and name = ? and email = ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String randomPw = Utility.getSecureString(10, false);
		
		//임시비밀번호
		temporaryPw = randomPw;
				
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, temporaryPw);
			stmt.setString(2, seller.getSellerId());
			stmt.setString(3, seller.getName());
			stmt.setString(4, seller.getEmail());
			
			int rows = stmt.executeUpdate();
			
			if(rows == 1) {
				seller.setSellerPw(temporaryPw);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error",36);
			message.setUrl("/takeit/seller/sellerFindPw.jsp");
			message.setLinkTitle("뒤로가기");
			throw new CommonException(message);
		}finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}
	
	/**
	 * 아이디 중복체크
	 */
	public boolean sellerIdChk(Connection con, String seller) throws CommonException {

		String sql = "select seller_Id from seller where seller_Id= ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, seller);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error",37);
			message.setUrl("/takeit/seller/sellerInput.jsp");
			message.setLinkTitle("뒤로가기");
			throw new CommonException(message);
		}finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
		return true;
	}

	/**
	 * 이메일 중복체크
	 */
	public boolean sellerEmailChk(Connection con, String email) throws CommonException {
		
		String sql = "select email from seller where email= ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, email);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error",38);
			message.setUrl("/takeit/seller/sellerInput.jsp");
			message.setLinkTitle("뒤로가기");
			throw new CommonException(message);
		}finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
		return true;
	}

	/**
	 * 사업자등록번호 중복체크
	 */
	public boolean sellerNoChk(Connection con, String sellerNo) throws CommonException {

		String sql = "select seller_no from seller where seller_no= ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, sellerNo);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error",37);
			message.setUrl("/takeit/member/memberInput.jsp");
			message.setLinkTitle("뒤로가기");
			throw new CommonException(message);
		}finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
		return true;
	}
	
	/**
	 * 상점명 중복체크
	 */
	public boolean shopNameChk(Connection con, String shopName) throws CommonException {
		
		String sql = "select shop_name from seller where shop_name= ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, shopName);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error",41);
			message.setUrl("/takeit/seller/sellerInput.jsp");
			message.setLinkTitle("뒤로가기");
			throw new CommonException(message);
		}finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
		return true;
	}
	
	/**
	 * 상점 카테고리 전체조회
	 */
	public void shopCategoryList(Connection conn, ArrayList<Seller> shopCategoryList) throws CommonException {
	
		String sql ="select * from seller s, shop_category c where s.shop_category_no = c.shop_category_no";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			Seller shopCate = null;
			while (rs.next()) {
				shopCate = new Seller();
				shopCate.setShopCategoryNo(rs.getString("shop_category_no"));  
				shopCate.setShopCategory(rs.getString("shop_category"));
				shopCategoryList.add(shopCate);
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error", 0);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}	
	}
}