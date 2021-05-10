/**
 * 
 */
package com.takeit.model.dto;

import java.io.Serializable;

/**
 * 후기
 * @author 김효원
 * @since jdk1.8
 * @version v2.0
 */
public class Review implements Serializable{
	
	/**후기 번호:시스템 자동부여*/
	private String reviewNo;
	
	/**일반회원아이디:필수입력*/
	private String memberId;
	
	/**상품번호:필수입력*/
	private String itemNo;
	
	/**후기 등록일자:시스템 자동부여 */
	private String reviewDate;
	
	/**후기제목:필수입력*/
	private String reviewTitle;
	
	/**후기내용:필수입력*/
	private String reviewContents;
	
	/**후기 조회수 default=0*/
	private int reviewViews;
	
	/**후기 평점:필수입력*/
	private int reviewScore;
	
	/** 후기사진*/
	private String reviewImg;



	public Review() {}
	/**후기 작성 필수 입력데이터*/
	public Review(String itemNo,String memberId,String reviewTitle, String reviewContents, int reviewScore,String reviewImg) {


		this.itemNo =itemNo;
		this.memberId = memberId;
		this.reviewTitle = reviewTitle;
		this.reviewContents = reviewContents;
		this.reviewScore = reviewScore;
		this.reviewImg = reviewImg;

	}


	/**후기 전체데이터*/
	public Review(String itemNo,String memberId,String reviewTitle, String reviewContents, int reviewScore,
			String reviewImg,String reviewNo,int reviewViews,String reviewDate) {
		this(itemNo, memberId, reviewTitle, reviewContents, reviewScore,reviewImg);
	
		this.reviewNo = reviewNo;
		this.reviewViews = reviewViews;
		this.reviewDate = reviewDate;


	}
	public String getReviewNo() {
		return reviewNo;
	}
	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	public String getReviewTitle() {
		return reviewTitle;
	}
	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}
	public String getReviewContents() {
		return reviewContents;
	}
	public void setReviewContents(String reviewContents) {
		this.reviewContents = reviewContents;
	}
	public int getReviewViews() {
		return reviewViews;
	}
	public void setReviewViews(int reviewViews) {
		this.reviewViews = reviewViews;
	}
	public int getReviewScore() {
		return reviewScore;
	}
	public void setReviewScore(int reviewScore) {
		this.reviewScore = reviewScore;
	}
	public String getReviewImg() {
		return reviewImg;
	}
	public void setReviewImg(String reviewImg) {
		this.reviewImg = reviewImg;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(reviewNo);
		builder.append(", ");
		builder.append(memberId);
		builder.append(", ");
		builder.append(itemNo);
		builder.append(", ");
		builder.append(reviewDate);
		builder.append(", ");
		builder.append(reviewTitle);
		builder.append(", ");
		builder.append(reviewContents);
		builder.append(", ");
		builder.append(reviewViews);
		builder.append(", ");
		builder.append(reviewScore);
		builder.append(", ");
		builder.append(reviewImg);
		builder.append("]");
		return builder.toString();
	}




}