package com.takeit.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dto.Item;
import com.takeit.model.dto.Member;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Seller;

/**
 * 회원 괸련 테이블에 관한 DAO
 * @author 심선경
 * @since jdk1.8
 * @version v2.0
 */
public class MypageDao {
	
	private static MypageDao instance = new MypageDao();
	
	public static MypageDao getInstance() {
		return instance;
	}
	
	/**
	 * 판매자 회원 전체 조회
	 * @param conn
	 * @param sellerList 판매자 회원 목록
	 * @throws CommonException
	 */
	public void selectSellerList(Connection conn , ArrayList<Seller> sellerList) throws CommonException{
		String sql = "SELECT * FROM SELLER";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Seller dto = new Seller();
				dto.setSellerId(rs.getString("SELLER_ID"));
				dto.setSellerPw(rs.getString("SELLER_PW"));
				dto.setName(rs.getString("NAME"));
				dto.setMobile(rs.getString("MOBILE"));
				dto.setEmail(rs.getString("EMAIL"));
				dto.setEntryDate(rs.getString("ENTRY_DATE"));
				dto.setSellerNo(rs.getString("SELLER_NO"));
				dto.setPostNo(rs.getString("POSTNO"));
				dto.setAddress(rs.getString("ADDRESS"));
				dto.setAddressDetail(rs.getString("ADDRESS_DETAIL"));
				
				dto.setShopMobile(rs.getString("SHOP_MOBILE"));
				dto.setShopName(rs.getString("SHOP_NAME"));
				dto.setCustScore(rs.getDouble("CUST_SCORE"));
				dto.setShopKakaoId(rs.getString("SHOP_KAKAO_ID"));
				dto.setShopLocCode(rs.getString("SHOP_LOC_CODE"));
				dto.setGrade(rs.getString("GRADE"));
				
				sellerList.add(dto);
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 41);
			message.setUrl("/takeit/member/myPage.jsp");
			message.setLinkTitle("마이페이지로 이동");

			throw new CommonException(message);
		}
		JdbcTemplate.close(rs);
		JdbcTemplate.close(stmt);
		
	}
	
	
	/**
	 * 일반회원 전체 조회
	 * @param conn
	 * @param memberList 일반회원 목록
	 * @throws CommonException
	 */
	public void selectMemberList(Connection conn , ArrayList<Member> memberList) throws CommonException{
		String sql = "SELECT * FROM MEMBER WHERE GRADE='G'";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Member dto = new Member();
				dto.setMemberId(rs.getString("MEMBER_ID"));
				dto.setMemberPw(rs.getString("MEMBER_PW"));
				dto.setName(rs.getString("NAME"));
				dto.setMobile(rs.getString("MOBILE"));
				dto.setEmail(rs.getString("EMAIL"));
				dto.setEntryDate(rs.getString("ENTRY_DATE"));
				dto.setPoint(rs.getInt("POINT"));
				dto.setPostNo(rs.getString("POSTNO"));
				dto.setAddress(rs.getString("ADDRESS"));
				dto.setAddressDetail(rs.getString("ADDRESS_DETAIL"));
				dto.setBirth(rs.getString("BIRTH"));
				dto.setMemberLocNo(rs.getString("MEMBER_LOC_NO"));
				dto.setGrade(rs.getString("GRADE"));
				dto.setShopLocCode(rs.getString("SHOP_LOC_CODE"));
				
				memberList.add(dto);
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error", 41);
			message.setUrl("/takeit/member/myPage.jsp");
			message.setLinkTitle("마이페이지로 이동");

			throw new CommonException(message);
		}
		JdbcTemplate.close(rs);
		JdbcTemplate.close(stmt);
		
	}
	
	
	/**
	 * 일반 회원 내 정보 상세 조회
	 * @param conn
	 * @param dto 회원 객체
	 */
	public void selectMemberDetail(Connection conn, Member dto) throws CommonException{
		String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID=?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, dto.getMemberId());
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				dto.setMemberId(rs.getString("MEMBER_ID"));
				dto.setMemberPw(rs.getString("MEMBER_PW"));
				dto.setName(rs.getString("NAME"));
				dto.setMobile(rs.getString("MOBILE"));
				dto.setEmail(rs.getString("EMAIL"));
				dto.setEntryDate(rs.getString("ENTRY_DATE"));
				dto.setPoint(rs.getInt("POINT"));
				dto.setAddress(rs.getString("ADDRESS"));
				dto.setAddressDetail(rs.getString("ADDRESS_DETAIL"));
				dto.setPostNo(rs.getString("POSTNO"));
				dto.setBirth(rs.getString("BIRTH"));
				dto.setMemberLocNo(rs.getString("MEMBER_LOC_NO"));
				dto.setGrade(rs.getString("GRADE"));
				dto.setShopLocCode(rs.getString("SHOP_LOC_CODE"));
				System.out.println(dto);
			}
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",1);
			message.setLinkTitle("마이페이지로 이동");
			message.setUrl("/takeit/member/myPage.jsp");
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
	}
	
	/**
	 * 일반회원 내 정보 수정 
	 * @param conn
	 * @param dto 일반회원 객체
	 */
	public void memberInfoUpdate (Connection conn, Member dto) throws CommonException{
		String sql = "UPDATE MEMBER SET MEMBER_PW=?, NAME=?, MOBILE=?,"
					 + "EMAIL=?, POSTNO=?, ADDRESS=?, ADDRESS_DETAIL=?,"
					 + " BIRTH=? , MEMBER_LOC_NO=? , SHOP_LOC_CODE=? WHERE MEMBER_ID=?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, dto.getMemberPw());
			stmt.setString(2, dto.getName());
			stmt.setString(3, dto.getMobile());
			stmt.setString(4, dto.getEmail());
			stmt.setString(5, dto.getPostNo());
			stmt.setString(6, dto.getAddress());
			stmt.setString(7, dto.getAddressDetail());
			stmt.setString(8, dto.getBirth());
			
			stmt.setString(9, dto.getMemberLocNo());
			stmt.setString(10, dto.getShopLocCode());
			stmt.setString(11, dto.getMemberId());
			int result = stmt.executeUpdate();
			
			if(result == 0) {
				throw new Exception();
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",2);
			message.setLinkTitle("내 정보 페이지 이동");
			message.setUrl("/takeit/member/myPage.jsp");
			throw new CommonException(message);
		}finally {
			JdbcTemplate.close(stmt);
		}
		
	}
	
	/**
	 * 판매자 정보 상세 조회
	 * @param conn
	 * @param dto	판매자 객체
	 * @throws CommonException
	 */
	public void searchSellerDetail(Connection conn, Seller dto) throws CommonException {
		
		String sql = "SELECT * FROM SELLER WHERE SELLER_ID=?";
		
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, dto.getSellerId());
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				
				dto.setSellerId(rs.getString("SELLER_ID"));
				dto.setSellerPw(rs.getString("SELLER_PW"));
				dto.setName(rs.getString("NAME"));
				dto.setMobile(rs.getString("MOBILE"));
				dto.setEmail(rs.getString("EMAIL"));
				dto.setEntryDate(rs.getString("ENTRY_DATE"));
				dto.setPostNo(rs.getString("POSTNO"));
				dto.setAddress(rs.getString("ADDRESS"));
				dto.setAddressDetail(rs.getString("ADDRESS_DETAIL"));
				dto.setGrade(rs.getString("GRADE"));
				dto.setSellerNo(rs.getString("SELLER_NO"));
				dto.setShopMobile(rs.getString("SHOP_MOBILE"));
				dto.setShopName(rs.getString("SHOP_NAME"));
				dto.setCustScore(rs.getDouble("CUST_SCORE"));
				dto.setShopKakaoId(rs.getString("SHOP_KAKAO_ID"));
				dto.setShopImg(rs.getString("SHOP_IMG"));
				dto.setShopCategoryNo(rs.getString("SHOP_CATEGORY_NO"));
				dto.setShopLocCode(rs.getString("SHOP_LOC_CODE"));
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",1);
			message.setUrl("/takeit/member/myPage.jsp");
			message.setLinkTitle("내 정보 보기");
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
		
	}
	
	/**
	 * 내 정보 수정 > 판매자 
	 * @param conn
	 * @param dto 판매자 객체
	 * @throws CommonException
	 */
	public void sellerInfoUpdate(Connection conn, Seller dto) throws CommonException{
		String sql = "UPDATE SELLER SET SELLER_PW=? ,NAME=? ,MOBILE=?,"
				 + "EMAIL=?, POSTNO=?, ADDRESS=?, ADDRESS_DETAIL=?, SELLER_NO=? , "
				 + "SHOP_MOBILE=? , SHOP_NAME=?, SHOP_KAKAO_ID=?, "
				 + "SHOP_CATEGORY_NO=? WHERE SELLER_ID=? ";
	
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, dto.getSellerPw());
			stmt.setString(2, dto.getName());
			stmt.setString(3, dto.getMobile());
			stmt.setString(4, dto.getEmail());
			stmt.setString(5, dto.getPostNo());
			stmt.setString(6, dto.getAddress());
			stmt.setString(7, dto.getAddressDetail());
			stmt.setString(8, dto.getSellerNo());
			stmt.setString(9, dto.getShopMobile());
			stmt.setString(10, dto.getShopName());
			stmt.setString(11, dto.getShopKakaoId());
			stmt.setString(12, dto.getShopCategoryNo());
			stmt.setString(13, dto.getSellerId());
			
			
			int result = stmt.executeUpdate();
			if(result == 0) {
				throw new Exception();
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",2);
			message.setLinkTitle("내 정보페이지로  이동");
			message.setUrl("/takeit/member/myPage.jsp");
			throw new CommonException(message);
		}finally {
			JdbcTemplate.close(stmt);
		}
		
	}
	
	/**
	 * 비밀번호 변경 > 일반회원
	 * @param conn 
	 * @param memberPw2	변경할 비밀번호
	 * @param dto 		일반회원 객체
	 * @throws CommonException
	 */
	public void memberPwUpdate(Connection conn , String memberPw2, Member dto) throws CommonException{
		String sql = "UPDATE MEMBER SET MEMBER_PW=? WHERE MEMBER_ID=? AND MEMBER_PW=?";
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, memberPw2);
			stmt.setString(2, dto.getMemberId());
			stmt.setString(3, dto.getMemberPw());
			
			int result = stmt.executeUpdate();
			if(result == 0) {
				throw new Exception();
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",2);
			message.setLinkTitle("내 정보페이지 이동");
			message.setUrl("/takeit/member/myPage.jsp");
			throw new CommonException(message);
		}finally {
			JdbcTemplate.close(stmt);
		}
		
	}
	
	/**
	 * 비밀번호 변경 > 판매자
	 * @param conn
	 * @param sellerPw2	변경할 비밀번호
	 * @param dto		판매자 객체
	 * @throws CommonException
	 */
	public void sellerPwUpdate(Connection conn , String sellerPw2, Seller dto) throws CommonException{
		String sql = "UPDATE SELLER SET SELLER_PW=? WHERE SELLER_ID=? AND SELLER_PW=?";
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, sellerPw2);
			stmt.setString(2, dto.getSellerId());
			stmt.setString(3, dto.getSellerPw());
			
			int result = stmt.executeUpdate();
			
			if(result == 0) {
				throw new Exception();
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",2);
			message.setLinkTitle("내 정보페이지 이동");
			message.setUrl("/takeit/member/myPage.jsp");
			throw new CommonException(message);
		}finally {
			JdbcTemplate.close(stmt);
		}
		
	}
	
	/**
	 * 회원 탈퇴 일반회원
	 * @param conn
	 * @param memberId 탈퇴할 아이디
	 * @param memberPw 탈퇴할 비밀번호
	 * @throws CommonException
	 */
	public void memberDelete(Connection conn, String memberId, String memberPw) throws CommonException {
		String sql = "DELETE FROM MEMBER WHERE MEMBER_ID=? AND MEMBER_PW=?";
		
		PreparedStatement stmt = null;
		
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, memberPw);
			
			int result = stmt.executeUpdate();
			
			
			if(result == 0) {
				throw new Exception();
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",4);
			message.setLinkTitle("마이페이지로 이동");
			message.setUrl("/takeit/member/myPage.jsp");
			throw new CommonException(message);
			
			
		}finally {
			JdbcTemplate.close(stmt);
		}
		
	}
	
	
	/**
	 * 회원 탈퇴 판매자
	 * @param conn
	 * @param sellerId 탈퇴할 아이디
	 * @param sellerPw	탈퇴할 비밀번호
	 * @throws CommonException
	 */
	public void sellerDelete(Connection conn, String sellerId, String sellerPw) throws CommonException{
		
		String sql = "DELETE FROM SELLER WHERE SELLER_ID=? AND SELLER_PW=?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, sellerId);
			stmt.setString(2, sellerPw);
			
			int result = stmt.executeUpdate();
			
			
			if(result == 0) {
				throw new Exception();
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",4);
			message.setLinkTitle("마이페이지로 이동");
			message.setUrl("/takeit/member/myPage.jsp");
			throw new CommonException(message);
		}finally {
			JdbcTemplate.close(stmt);
		}
	}
	
		
}
