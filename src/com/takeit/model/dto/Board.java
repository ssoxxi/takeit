/**
 * 
 */
package com.takeit.model.dto;

import java.io.Serializable;

/**
 * 게시글
 * @author 	한소희
 * @since	jdk1.8
 * @version v2.0
 */
public class Board implements Serializable{
	/** 게시글 번호, 시스템 자동부여, 필수 */
	private String boardNo;
	
	/** 게시글 작성자, session에 저장된 아이디, 필수 */
	private String boardWriter;
	
	/** 게시글 제목, 사용자 입력, 필수 */
	private String boardTitle;
	
	/** 게시글 내용, 사용자입력, 필수 */
	private String boardContents;
	
	/** 게시글 조회수, 시스템 자동부여, 기본값 0 */
	private int boardViews;
	
	/** 게시글 작성일자, 시스템 자동부여, 기본값  0 */
	private String boardDate;
	
	/** 게시글 카테고리 번호, 사용자입력(카테고리명 선택시 번호로 전달), 필수 */
	private String boardCategory;
	
	/** 게시글 카테고리명, 카테고리 번호에 따라 자동 호출, 필수 */
	private String boardCategoryName;
	
	/** 게시글(상품문의 전용) 상품번호, 상품문의에서만 사용자 입력 */
	private String boardItem;

	
	/** 기본 생성자 */
	public Board() {}
	
	/***
	 * 공지사항,자주하는 질문 사용자(관리자) 입력데이터
	 * @param boardWriter 	게시글 작성자
	 * @param boardTitle	게시글 제목
	 * @param boardContents	게시글 내용
	 * @param boardCategory	게시글 카테고리
	 */
	public Board(String boardWriter, String boardTitle, String boardContents, String boardCategory) {
		this.boardWriter = boardWriter;
		this.boardTitle = boardTitle;
		this.boardContents = boardContents;
		this.boardCategory = boardCategory;
	}
	
	
	/***
	 * 상품문의 사용자 입력데이터
	 * @param boardWriter	게시글 작성자
	 * @param boardTitle	게시글 제목
	 * @param boardContents	게시글 내용
	 * @param boardCategory	게시글 카테고리
	 * @param boardItem		게시글 상품번호
	 */
	public Board(String boardWriter, String boardTitle, String boardContents, String boardCategory, String boardItem) {
		this(boardWriter, boardTitle, boardContents, boardCategory);
		this.boardItem = boardItem;
	}
	
	public Board(String boardWriter, String boardTitle, String boardContents, String boardCategory, String boardItem, String boardCategoryName) {
		this(boardWriter, boardTitle, boardContents, boardCategory, boardItem);
		this.boardCategoryName = boardCategoryName;
	}
	
	/***
	 * 전체입력데이터
	 * @param boardNo		게시글 번호
	 * @param boardWriter	게시글 작성자
	 * @param boardTitle	게시글 제목
	 * @param boardContents	게시글 내용
	 * @param boardViews	게시글 조회수
	 * @param boardDate		게시글 작성일자
	 * @param boardCategory	게시글 카테고리
	 * @param boardItem		게시글 상품번호
	 */
	public Board(String boardNo, String boardWriter, String boardTitle, String boardContents, int boardViews,
			String boardDate, String boardCategory, String boardItem) {
		this(boardWriter, boardTitle, boardContents, boardCategory, boardItem);
		this.boardViews = boardViews;
		this.boardDate = boardDate;
	}

	
	public String getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}

	public String getBoardWriter() {
		return boardWriter;
	}

	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContents() {
		return boardContents;
	}

	public void setBoardContents(String boardContents) {
		this.boardContents = boardContents;
	}

	public int getBoardViews() {
		return boardViews;
	}

	public void setBoardViews(int boardViews) {
		this.boardViews = boardViews;
	}

	public String getBoardDate() {
		return boardDate;
	}

	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}

	public String getBoardCategory() {
		return boardCategory;
	}

	public void setBoardCategory(String boardCategory) {
		this.boardCategory = boardCategory;
	}

	public String getBoardItem() {
		return boardItem;
	}

	public void setBoardItem(String boardItem) {
		this.boardItem = boardItem;
	}

	public String getBoardCategoryName() {
		return boardCategoryName;
	}
	public void setBoardCategoryName(String boardCategoryName) {
		this.boardCategoryName = boardCategoryName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(boardNo);
		builder.append(", ");
		builder.append(boardWriter);
		builder.append(", ");
		builder.append(boardTitle);
		builder.append(", ");
		builder.append(boardContents);
		builder.append(", ");
		builder.append(boardViews);
		builder.append(", ");
		builder.append(boardDate);
		builder.append(", ");
		builder.append(boardCategory);
		builder.append(", ");
		builder.append(boardItem);
		return builder.toString();
	}
}
