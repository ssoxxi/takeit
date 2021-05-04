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
		} catch(CommonException e) {
			throw e; 
		} finally {
			JdbcTemplate.close(conn);
		}
	}

	/**
	 * 내가 작성한 후기보기
	 * @param dto review
	 */
	public void getReview(Review dto){
		Connection conn = JdbcTemplate.getConnection();

		try {
			dao.ReviewDetail(conn, dto);
		}catch (Exception e) {
			e.printStackTrace();
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
	/**후기삭제*/
	public void reviewDelete(String memberId, String reviewTitle) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.deleteReview(con, memberId, reviewTitle);
			JdbcTemplate.commit(con);
		} catch (CommonException e) {
			JdbcTemplate.rollback(con);
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
		
	}
}

