/**
 * 
 */
package com.takeit.model.dto;

/**
 * 게시글 카테고리
 * @author 	한소희
 * @since	jdk1.8
 * @version v2.0
 */
public class Category {
	/** 게시글 카테고리 번호(1|공지사항. 2|자주하는질문, 3|상품문의) */
	private String categoryNo;
	
	/** 게시글 카테고리명 */
	private String categoryName;
	
	
	/** 기본 생성자 */
	public Category() {}

	/***
	 * @param categoryNo	카테고리번호
	 * @param categoryName	카테고리명
	 */
	public Category(String categoryNo, String categoryName) {
		this.categoryNo = categoryNo;
		this.categoryName = categoryName;
	}


	/**
	 * @return the categoryNo
	 */
	public String getCategoryNo() {
		return categoryNo;
	}

	/**
	 * @param categoryNo the categoryNo to set
	 */
	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(categoryNo);
		builder.append(", ");
		builder.append(categoryName);
		return builder.toString();
	}
}
