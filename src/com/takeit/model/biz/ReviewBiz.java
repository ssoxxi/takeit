/**
 * 
 */
package com.takeit.model.biz;

import java.sql.Connection;
import java.util.ArrayList;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dao.ReviewDao;
import com.takeit.model.dto.Review;

/**
 * 후기로직 담당클래스 
 * @author 김효원
 */

/**
 * 후기전체조회

 * @param dto review
 * @return 성공시 등록 미입력시 오류처리
 */
public class ReviewBiz {
	private ReviewDao dao = ReviewDao.getInstance(); 

	public void getReviewList(ArrayList<Review> ReviewList) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.getReviewList(con, ReviewList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}
	/**
	 * 내 후기 전체조회

	 * @param dto review
	 * @return 성공시 등록 미입력시 오류처리
	 */
	public void getMyReviewList(ArrayList<Review> ReviewList , String memberId) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.getMyReviewList(con, ReviewList, memberId);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}	
	/**
	 * <pre>
	 * 후기등록
	 * -- 상품등록 입력 데이터  
	 * 시스템 추가 데이터 : 후기 등록일,작성자,후기번호
	 * </pre>
	 * @param dto review
	 * @return 성공시 등록 미입력시 오류처리
	 */

	public void enrollReview(Review dto) throws CommonException{
		Connection conn = JdbcTemplate.getConnection();
		try {
			dao.enrollReview(conn, dto);
			JdbcTemplate.commit(conn);

		} catch(CommonException e) {
			JdbcTemplate.rollback(conn);
			throw e; 
		} finally {
			JdbcTemplate.close(conn);
		}
	}

	/**
	 * 내가 작성한 후기보기
	 * @param dto review
	 */
	public void getReview(Review dto)throws CommonException{
		Connection conn = JdbcTemplate.getConnection();

		try {
			dao.ReviewDetail(conn, dto);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			JdbcTemplate.close(conn);
		}
	}
	/**
	 * 작성후기내용 수정
	 * @param dto review
	 */
	public void setReview(Review dto) throws CommonException{
		Connection conn = JdbcTemplate.getConnection();
		try {
			dao.updateReview(conn,dto);
			JdbcTemplate.commit(conn);

		} catch (Exception e) {
			e.printStackTrace();
			JdbcTemplate.rollback(conn);
			throw e;
		}finally {
			JdbcTemplate.close(conn);

		}
	}

	/**후기상세조회*/
	public void searchReview(Review dto,String reviewNo) throws CommonException {

		Connection conn = JdbcTemplate.getConnection();
		System.out.println("dto = "+ reviewNo);
		try {
			
			int hits = dao.hits(conn, reviewNo);
			JdbcTemplate.commit(conn);
			if(hits != 0) {
			dao.lookReview(conn, dto,reviewNo);
			}
		} catch (CommonException e) {
			JdbcTemplate.rollback(conn);
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
		
	}

	/**후기삭제*/
	public void reviewDelete(String reviewNo,String memberId) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.deleteReview(con,reviewNo, memberId);
			JdbcTemplate.commit(con);
		} catch (CommonException e) {
			JdbcTemplate.rollback(con);
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}

	}
}

