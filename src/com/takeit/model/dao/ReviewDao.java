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
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Review;

/**
 * 후기
 * @author 김효원
 * @since jdk1.8
 * @version v2.0
 */

public class ReviewDao {
	private static ReviewDao instance = new ReviewDao();
	private ReviewDao() {}
	public static ReviewDao getInstance() {
		return instance;
	}

	/**
	 * 후기 전체목록
	 * @param con
	 * @param ReviewList
	 * @throws CommonException
	 */
	public void getReviewList(Connection con, ArrayList<Review> reviewList) throws CommonException {
		String sql = "SELECT * FROM Review WHERE 1 = 1 ORDER BY REVIEW_NO ASC";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(sql);
			rs= stmt.executeQuery();

			while(rs.next()) {
				Review dto = new Review();
				
				dto.setReviewNo(rs.getString("REVIEW_NO"));
				dto.setItemNo(rs.getString("ITEM_NO"));
				dto.setReviewTitle(rs.getString("REVIEW_TITLE"));
				dto.setMemberId(rs.getString("MEMBER_ID"));
				dto.setReviewScore(rs.getInt("REVIEW_SCORE"));
				dto.setReviewDate(rs.getString("REVIEW_DATE"));
				dto.setReviewViews(rs.getInt("REVIEW_VIEWS"));
				dto.setReviewContents(rs.getString("REVIEW_CONTENTS"));
				dto.setReviewImg(rs.getString("REVIEW_IMG"));
				System.out.println(dto);

				reviewList.add(dto);
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
	 * 회원작성후기 전체목록
	 * @param con
	 * @param ReviewList
	 * @throws CommonException
	 */
	public void getMyReviewList(Connection con, ArrayList<Review> reviewList, String memberId) throws CommonException {
		String sql = "SELECT * FROM Review WHERE MEMBER_ID = ? ORDER BY REVIEW_NO ASC";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(sql);			
			stmt.setString(1, memberId);
			rs= stmt.executeQuery();
			
			while(rs.next()) {
				Review dto = new Review();
				
				dto.setReviewNo(rs.getString("REVIEW_NO"));
				dto.setItemNo(rs.getString("ITEM_NO"));
				dto.setReviewTitle(rs.getString("REVIEW_TITLE"));
				dto.setMemberId(rs.getString("MEMBER_ID"));
				dto.setReviewScore(rs.getInt("REVIEW_SCORE"));
				dto.setReviewDate(rs.getString("REVIEW_DATE"));
				dto.setReviewViews(rs.getInt("REVIEW_VIEWS"));
				dto.setReviewContents(rs.getString("REVIEW_CONTENTS"));
				dto.setReviewImg(rs.getString("REVIEW_IMG"));
				System.out.println(dto);

				reviewList.add(dto);
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
	 * 후기등록
	 * @param con
	 * @param EnrollReview
	 * @throws CommonException
	 */

	public void enrollReview(Connection conn, Review dto) throws CommonException {
		String sql = "insert into review values(REVIEW_SEQ.NEXTVAL,?,?,sysdate,?,?,0,?,?)";

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, dto.getMemberId());
			stmt.setString(2, dto.getItemNo());
			stmt.setString(3, dto.getReviewTitle());
			stmt.setString(4, dto.getReviewContents());
			stmt.setInt(5, dto.getReviewScore());
			stmt.setString(6, dto.getReviewImg());
			stmt.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error", 7);
			//	message.setUrl("/takeit/reviewController?action=reviewEnrollForm");
			//	message.setLinkTitle("후기등록");

			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}
	}

	/**
	 * 회원후기 상세조회
	 * @param conn
	 * @param dto 후기
	 */
	public void ReviewDetail(Connection conn, Review dto) throws CommonException{
		String sql = "select * from review where member_id=?";

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, dto.getMemberId());
			rs = stmt.executeQuery();

			if(rs.next()) {

				dto.setReviewNo(rs.getString("REVIEW_NO"));
				dto.setMemberId(rs.getString("MEMBER_ID"));
				dto.setItemNo(rs.getString("ITEM_NO"));
				dto.setReviewDate(rs.getString("REVIEW_DATE"));
				dto.setReviewTitle(rs.getString("REVIEW_TITLE"));
				dto.setReviewContents(rs.getString("REVIEW_CONTENTS"));
				dto.setReviewScore(rs.getInt("REVIEW_SCORE"));
				dto.setReviewImg(rs.getString("REVIEW_IMG"));			
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
	 * 후기내용 변경
	 * @param conn
	 * @param dto 후기
	 * @throws CommonException 
	 */
	public void updateReview (Connection conn, Review dto) throws CommonException{
		String sql = "update review set  review_title=? ,review_contents=? ,review_score=? where member_id=? and review_no=?";

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, dto.getReviewTitle());
			stmt.setString(2, dto.getReviewContents());
			stmt.setInt(3, dto.getReviewScore());
			stmt.setString(4, dto.getMemberId());
			stmt.setString(5, dto.getReviewNo());


			int result =stmt.executeUpdate();

			if(result == 0) {
				throw new Exception();
			}

		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

			MessageEntity message = new MessageEntity("error",26);
			message.setLinkTitle("내가 작성한 후기보기");
			message.setUrl("/takeit/review/reviewInfo.jsp");
			throw new CommonException(message);
		}finally {
			JdbcTemplate.close(stmt);
		}

	}


	/**
	 * 후기삭제
	 * @param conn
	 * @param dto
	 * @throws CommonException 
	 */
	public void deleteReview(Connection conn,String reviewNo,String memberId) throws CommonException {
		String sql = "delete from review where member_id=? and review_No=?";

		PreparedStatement stmt = null;
				
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, reviewNo);

			int rows = stmt.executeUpdate();
			
			if(rows ==0) {
				throw new Exception();
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",27);
			message.setLinkTitle("후기목록");
			message.setUrl("/takeit/item/reviewController?action=myReviewList");
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(stmt);
		}

	}
	/**
	 * 후기상세조회	
	 */
	public void lookReview(Connection conn, Review dto, String reviewNo) throws CommonException {
		String sql = "select * from review where review_no=?";


		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, reviewNo);
			
			rs = stmt.executeQuery();
			
			while (rs.next()) {

				dto.setReviewNo(rs.getString("REVIEW_NO"));
				dto.setItemNo(rs.getString("ITEM_NO"));
				dto.setReviewTitle(rs.getString("REVIEW_TITLE"));
				dto.setMemberId(rs.getString("MEMBER_ID"));
				dto.setReviewScore(rs.getInt("REVIEW_SCORE"));
				dto.setReviewDate(rs.getString("REVIEW_DATE"));
				dto.setReviewViews(rs.getInt("REVIEW_VIEWS"));
				dto.setReviewContents(rs.getString("REVIEW_CONTENTS"));
				dto.setReviewImg(rs.getString("REVIEW_IMG"));

			
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageEntity message = new MessageEntity("error", 31);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}	
	}
	/**후기조회수 증가*/
	public int hits(Connection conn, String reviewNo) {
		String sql = "UPDATE REVIEW SET REVIEW_VIEWS = REVIEW_VIEWS+1 WHERE REVIEW_NO=?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, reviewNo);
			int hits = stmt.executeUpdate();
			
			return hits;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",25);
			message.setLinkTitle("후기 목록");
			message.setUrl("/takeit/item/reviewController?action=reviewList");
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(stmt);
		}
		return 0;
	}
}