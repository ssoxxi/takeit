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
import com.takeit.model.dto.Board;
import com.takeit.model.dto.Category;
import com.takeit.model.dto.MessageEntity;

/**
 * 게시글 dao
 * @author 	한소희
 * @since	jdk1.8
 * @version v2.0
 */
public class BoardDao {
	private static BoardDao instance = new BoardDao();
	private BoardDao() {}
	public static BoardDao getInstance() {
		return instance;
	}
	
	/***
	 * 게시글 전체목록
	 * @param con
	 * @param boardList
	 * @throws CommonException
	 */
	public void getBoardList(Connection con, String categoryNo, ArrayList<Board> boardList) throws CommonException {
		System.out.println("[debug] 게시판 dao 목록 요청");
		String sql = "SELECT * FROM BOARD B, BOARD_CATEGORY BC "
				+ "WHERE B.BOARD_CATEGORY_NO=BC.BOARD_CATEGORY_NO "
				+ "AND B.BOARD_CATEGORY_NO = ? "
				+ "ORDER BY B.BOARD_DATE DESC";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, categoryNo);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				Board dto = new Board();
				dto.setBoardNo(rs.getString("BOARD_NO"));
				dto.setBoardTitle(rs.getString("BOARD_TITLE"));
				dto.setBoardWriter(rs.getString("BOARD_WRITER"));
				dto.setBoardCategory(rs.getString("BOARD_CATEGORY_NO"));
				dto.setBoardCategoryName(rs.getString("BOARD_CATEGORY"));
				dto.setBoardViews(rs.getInt("BOARD_VIEWS"));
				dto.setBoardDate(rs.getString("BOARD_DATE"));
				
				boardList.add(dto);
				System.out.println("[debug] 게시판 dao 목록 요청 완료");
			}
			
		} catch (SQLException e) {
			System.out.println("[debug] 게시판 dao 목록 요청 실패");
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
	
	/**게시글 갯수 구하기*/
	public int boardCount(Connection con, String categoryNo) throws CommonException {
		System.out.println("[debug] 게시판 dao 목록 갯수 요청");
		String sql = "SELECT COUNT(*) FROM BOARD B, BOARD_CATEGORY BC "
				+ "WHERE B.BOARD_CATEGORY_NO=BC.BOARD_CATEGORY_NO "
				+ "AND B.BOARD_CATEGORY_NO = ? "
				+ "ORDER BY B.BOARD_DATE DESC";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, categoryNo);
			rs= pstmt.executeQuery();
			
			if(rs.next()) {
			cnt = rs.getInt(1);
				System.out.println("[debug] 게시판 dao 목록 갯수 요청 완료");
			}
			
		} catch (SQLException e) {
			System.out.println("[debug] 게시판 dao 목록 갯수 요청 실패");
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",16);
			message.setLinkTitle("메인으로");
			message.setUrl("/takeit/index");
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(pstmt);
		}
		return cnt;
	}
	
	/***
	 * 게시글 상세조회
	 * @param con
	 * @param board_no
	 * @param notice
	 * @throws CommonException
	 */
	public void boardDetail(Connection con, String boardNo, String boardCategory, Board board) throws CommonException {
		System.out.println("[debug] 게시판 dao 상세조회 요청");
		String sql = "SELECT * FROM BOARD B, BOARD_CATEGORY BC "
				+ "WHERE B.BOARD_CATEGORY_NO=BC.BOARD_CATEGORY_NO "
				+ "AND B.BOARD_CATEGORY_NO = ? "
				+ "AND B.BOARD_NO=?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boardCategory);
			pstmt.setString(2, boardNo);
			rs= pstmt.executeQuery();
	
			if(rs.next()) {
				board.setBoardNo(rs.getString("board_no"));
				board.setBoardTitle(rs.getString("board_title"));
				board.setBoardWriter(rs.getString("board_writer"));
				board.setBoardCategoryName(rs.getString("board_category"));
				board.setBoardCategory(rs.getString("board_category_no"));
				board.setBoardViews(rs.getInt("board_views"));
				board.setBoardDate(rs.getString("board_date"));
				board.setBoardContents(rs.getString("board_contents"));
				
				System.out.println("[debug] 게시판 dao 상세조회 요청 완료");
			}
			
		} catch (SQLException e) {
			System.out.println("[debug] 게시판 dao 상세조회 요청 실패");
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
	
	/**조회수 증가*/
	public int views(Connection con, String boardNo) {
		System.out.println("[debug] 게시판 dao 조회수 증가 요청");
		String sql = "UPDATE BOARD SET BOARD_VIEWS = BOARD_VIEWS+1 WHERE BOARD_NO=?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boardNo);
			int view = pstmt.executeUpdate();
			
			
			System.out.println("[debug] 게시판 조회수 dao 증가 완료");
			return view;
		} catch (SQLException e) {
			System.out.println("[debug] 게시판 조회수 dao 증가 실패");
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",16);
			message.setLinkTitle("게시판 목록");
			message.setUrl("/takeit/boardController?action=boardList");
		} finally {
			JdbcTemplate.close(rs);
			JdbcTemplate.close(pstmt);
		}
		return 0;
	}
	
	/**게시글 등록*/
	public void boardInput(Connection con, Board notice) throws CommonException {
		System.out.println("[debug] 게시판 dao 등록 요청");
		String sql = "INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL, ?, ?, ?, 0, 0, sysdate, ?, ?)";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, notice.getBoardWriter());
			pstmt.setString(2, notice.getBoardTitle());
			pstmt.setString(3, notice.getBoardContents());
			pstmt.setString(4, notice.getBoardCategory());
			pstmt.setString(5, notice.getBoardItem());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",15);
			message.setLinkTitle("게시판 목록");
			message.setUrl("/takeit/boardController?action=boardList");
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(pstmt);
		}
	}
	
	/**카테고리 리스트*/
	public void getCategoryList(Connection con, ArrayList<Category> category) throws CommonException {
		System.out.println("[debug] 게시판 dao 카테고리 리스트 요청");
		String sql = "SELECT * FROM BOARD_CATEGORY";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs= pstmt.executeQuery();
	
			while(rs.next()) {
				Category dto = new Category();
				dto.setCategoryNo(rs.getString("board_category_no"));
				dto.setCategoryName(rs.getString("board_category"));
				
				category.add(dto);
			}
			System.out.println("[debug] 게시판 dao 카테고리 리스트 요청 완료");
			
		} catch (SQLException e) {
			System.out.println("[debug] 게시판 dao 카테고리 리스트 요청 실패");
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",16);
			message.setLinkTitle("게시판 목록");
			message.setUrl("/takeit/boardController?action=boardList");
			throw new CommonException(message);
		}
		JdbcTemplate.close(rs);
		JdbcTemplate.close(pstmt);
	}
	
	/**게시글 수정 폼
	 * @throws CommonException */
	public void boardDetail(Connection con, String boardNo, String boardCategory, String boardWriter, Board board) throws CommonException {
		System.out.println("[debug] 게시판 dao 수정  폼 요청");
		String sql = "SELECT * FROM BOARD B, BOARD_CATEGORY BC "
				+ "WHERE B.BOARD_CATEGORY_NO=BC.BOARD_CATEGORY_NO "
				+ "AND B.BOARD_CATEGORY_NO = ? "
				+ "AND B.BOARD_WRITER = ?"
				+ "AND B.BOARD_NO=?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boardCategory);
			pstmt.setString(2, boardWriter);
			pstmt.setString(3, boardNo);
			rs= pstmt.executeQuery();
	
			if(rs.next()) {
				board.setBoardNo(rs.getString("board_no"));
				board.setBoardTitle(rs.getString("board_title"));
				board.setBoardWriter(rs.getString("board_writer"));
				board.setBoardCategoryName(rs.getString("board_category"));
				board.setBoardCategory(rs.getString("board_category_no"));
				board.setBoardViews(rs.getInt("board_views"));
				board.setBoardDate(rs.getString("board_date"));
				board.setBoardContents(rs.getString("board_contents"));
				
				System.out.println("[debug] 게시판 dao 수정 폼 요청 완료");
			}
			
		} catch (SQLException e) {
			System.out.println("[debug] 게시판 dao 변경 폼 요청 실패");
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",19);
			message.setLinkTitle("메인으로");
			message.setUrl("/takeit/index");
			throw new CommonException(message);
		}
		JdbcTemplate.close(rs);
		JdbcTemplate.close(pstmt);
		
	}
	
	/**게시글 수정*/
	public void boardUpdate(Connection con, String boardNo, Board board) throws CommonException {
		System.out.println("[debug] 게시판 dao 변경  요청");
		String sql = "UPDATE BOARD SET BOARD_TITLE=?, BOARD_CONTENTS=?, BOARD_CATEGORY_NO=?, ITEM_NO=? "
				+ "WHERE BOARD_NO=? AND BOARD_CATEGORY_NO=? AND BOARD_WRITER=?";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardContents());
			pstmt.setString(3, board.getBoardCategory());
			pstmt.setString(4, board.getBoardItem());
			
			pstmt.setString(5, boardNo);
			pstmt.setString(6, board.getBoardCategory());
			pstmt.setString(7, board.getBoardWriter());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",19);
			message.setLinkTitle("게시판 목록");
			message.setUrl("/takeit/boardController?action=boardList&boardCategory="+board.getBoardCategory());
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(pstmt);
		}
		
	}
	/**게시글 삭제*/
	public void boardDelete(Connection con, String boardNo, String boardWriter, String boardCategory) throws CommonException {
		System.out.println("[debug] 게시판 dao 삭제  요청");
		String sql = "DELETE FROM BOARD WHERE BOARD_NO=? AND BOARD_WRITER=?";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boardNo);
			pstmt.setString(2, boardWriter);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
			MessageEntity message = new MessageEntity("error",18);
			message.setLinkTitle("게시판 목록");
			message.setUrl("/takeit/boardController?action=boardList&boardCategory="+boardCategory);
			throw new CommonException(message);
		} finally {
			JdbcTemplate.close(pstmt);
		}
		
	}
	
	/**게시글 검색결과 조회*/
	public void getBoardSearchList(Connection con, String boardCategory, String boardSearch, String searchInput,
			ArrayList<Board> boardList) throws CommonException{
		System.out.println("[debug] 게시판 dao 검색결과 요청");
		String sql = "SELECT DISTINCT * FROM BOARD B LEFT OUTER JOIN BOARD_CATEGORY BC "
				+ "ON (B.BOARD_CATEGORY_NO=BC.BOARD_CATEGORY_NO) "
				+ "WHERE B.BOARD_CATEGORY_NO = ? "
				+ "AND B.BOARD_WRITER LIKE ? "
				+ "OR B.BOARD_TITLE LIKE ? "
				+ "OR B.BOARD_CONTENTS LIKE ? "
				+ "OR B.ITEM_NO LIKE ?"
				+ "ORDER BY B.BOARD_DATE DESC";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boardCategory);
			pstmt.setString(2, searchInput);
			pstmt.setString(3, searchInput);
			pstmt.setString(4, searchInput);
			pstmt.setString(5, searchInput);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				Board dto = new Board();
				dto.setBoardNo(rs.getString("BOARD_NO"));
				dto.setBoardTitle(rs.getString("BOARD_TITLE"));
				dto.setBoardWriter(rs.getString("BOARD_WRITER"));
				dto.setBoardCategory(rs.getString("BOARD_CATEGORY_NO"));
				dto.setBoardCategoryName(rs.getString("BOARD_CATEGORY"));
				dto.setBoardViews(rs.getInt("BOARD_VIEWS"));
				dto.setBoardDate(rs.getString("BOARD_DATE"));
				
				boardList.add(dto);
				System.out.println("[debug] 게시판 dao 검색결과 요청 완료");
			}
			
		} catch (SQLException e) {
			System.out.println("[debug] 게시판 dao 검색결과 요청 실패");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		JdbcTemplate.close(rs);
		JdbcTemplate.close(pstmt);
		
	}

}
