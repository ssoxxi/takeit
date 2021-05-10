/**
 * 
 */
package com.takeit.model.biz;

import java.sql.Connection;
import java.util.ArrayList;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dao.BoardDao;
import com.takeit.model.dto.Board;
import com.takeit.model.dto.Category;

/**
 * 게시판 서비스
 * @author 한소희
 */
public class BoardBiz {
	private BoardDao dao = BoardDao.getInstance(); 
	
	/**게시글 전체조회
	 * @param categoryNo		카테고리번호
	 * @param boardList		게시글목록
	 */
	public void getBoardList(String categoryNo, ArrayList<Board> boardList) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.getBoardList(con, categoryNo, boardList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}
	
	/**게시글 전체갯수조회
	 * @param categoryNo	게시글 카테고리번호
	 * @return				게시글 갯수
	 */
	public int boardCount(String categoryNo) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			return dao.boardCount(con, categoryNo);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}

	/**게시글상세조회
	 * @param boardNo		게시글 번호
	 * @param boardCategory	게시글 카테고리
	 * @param board			게시글정보
	 */
	public void boardDetail(String boardNo, String boardCategory, Board board) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			int view = dao.views(con, boardNo);
			JdbcTemplate.commit(con);
			if(view != 0) {
				dao.boardDetail(con, boardNo, boardCategory, board);
			}
		} catch (CommonException e) {
			JdbcTemplate.rollback(con);
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
		
	}
	
	/**게시글 등록
	 * @param board	게시글
	 */
	public void boardInput(Board board) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.boardInput(con, board);
			JdbcTemplate.commit(con);
		} catch (CommonException e) {
			JdbcTemplate.rollback(con);
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}
	
	/**카테고리 리스트
	 * @param category	게시글 카테고리 리스트
	 */
	public void getCategoryList(ArrayList<Category> category) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.getCategoryList(con, category);
		} catch (CommonException e) {
			JdbcTemplate.rollback(con);
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
		
	}

	/**게시글 수정 화면
	 * @param boardNo		게시글 번호
	 * @param boardCategory	게시글 카테고리
	 * @param boardWriter	게시글 작성자
	 * @param board			게시글 정보
	 */
	public void boardDetail(String boardNo, String boardCategory, String boardWriter, Board board) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.boardDetail(con, boardNo, boardCategory, boardWriter, board);
			
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
		
	}

	/**게시글 수정
	 * @param boardNo	게시글 번호
	 * @param board		게시글 정보
	 */
	public void boardUpdate(String boardNo, Board board) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.boardUpdate(con, boardNo, board);
			JdbcTemplate.commit(con);
			System.out.println("[debug] 게시글 수정 완료");
		} catch (CommonException e) {
			JdbcTemplate.rollback(con);
			System.out.println("[debug] 게시글 수정 실패");
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
		
	}

	/**게시글 삭제
	 * @param boardNo			게시글 번호
	 * @param boardWriter		게시글 작성자 아이디
	 * @param boardCategory		게시글 카테고리
	 */
	public void boardDelete(String boardNo, String boardWriter, String boardCategory) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.boardDelete(con, boardNo, boardWriter, boardCategory);
			JdbcTemplate.commit(con);
			System.out.println("[debug] 게시판 삭제 완료");
		} catch (CommonException e) {
			JdbcTemplate.rollback(con);
			System.out.println("[debug] 게시판 삭제 실패");
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
		
	}
	/**게시글 검색결과 조회
	 * @param boardCategory	게시글 카테고리
	 * @param boardSearch	게시글 검색어 주제
	 * @param searchInput	게시글 검색어
	 * @param boardList		게시글 리스트
	 */
	public void getBoardSearchList(String boardCategory, String boardSearch, String searchInput,
			ArrayList<Board> boardList) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.getBoardSearchList(con, boardCategory, boardSearch, searchInput, boardList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
		
	}
	

}
