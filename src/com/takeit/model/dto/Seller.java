package com.takeit.model.dto;

import java.io.Serializable;

/**
 * 판매자 회원관리 도메인
 * @author  임우진
 * @since   jdk1.8
 * @version v2.0
 */
public class Seller implements Serializable{	
	
	/* 아이디 : 필수입력, 중복불가, 6~20자 영문+숫자 조합 */
	private String sellerId;
	
	/* 비밀번호 : 필수입력, 8~20자 영대소문자+숫자 조합 */
	private String sellerPw;
	
	/* 이름 : 필수입력, 2~6자 한글만 입력 */
	private String name;
	
	/* 휴대폰 : 필수입력, 000-0000-0000 형식에 맞춰 입력 */
	private String mobile;
	
	/* 이메일 : 필수입력, 중복불가, takeit@take.com 형식에 맞춰 입력 */	
	private String email;
	
	/* 가입일 : 시스템 부여, yyyy.MM.dd HH:mm:ss 형식, Timestamp */	
	private String entryDate;
	
	/* 우편번호 : 필수입력, api로 데이터를 받아옴 */
	private String postNo;
	
	/* 도로명주소 : 필수입력, api로 데이터를 받아옴 */
	private String address;
	
	/* 상세주소 : 필수입력  */
	private String addressDetail;
	
	/* 등급 : 시스템 부여, 기본값 : S */	
	private String grade;
	
	/* 사업자등록번호 : 필수입력, 중복불가, 000-00-00000 형식에 맞춰 입력 */
	private String sellerNo;
	
	/* 상점연락처 : 필수입력, 000-0000-0000 형식에 맞춰 입력 */
	private String shopMobile;
	
	/* 상점명 : 필수입력, 중복불가, 한글+영어+숫자 조합 원하는거 사용 */
	private String shopName;
	
	/* 고객평점 : 시스템 자동부여, 기본값 : 0.0  */
	private double custScore;
	
	/* 상점카카오아이디 : 사용자 입력  */
	private String shopKakaoId;
	
	/* 상점이미지 : 사용자 입력, 이미지업로드 형식  */
	private String shopImg;
	
	/* 상점카테고리번호 : 필수선택, SHOP_CATEGORY에서 받아온 데이터 */
	private String shopCategoryNo;
	
	/* 상점카테고리이름 : 필수선택, SHOP_CATEGORY에서 받아온 데이터 */
	private String shopCategory;
	
	/* 상점카테고리이름 : 필수선택, SHOP_LOC에서 받아온 데이터 */
	private String shopLocCode;
	
	/* 기본 생성자 */
	public Seller() {}

	/* 사용자 입력 데이터 */
	public Seller(String sellerId, String sellerPw, String name, String mobile, String email, String postNo,
			String address, String addressDetail, String sellerNo, String shopMobile, String shopName,
			String shopKakaoId, String shopImg, String shopCategoryNo, String shopLocCode) {
		this.sellerId = sellerId;
		this.sellerPw = sellerPw;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.postNo = postNo;
		this.address = address;
		this.addressDetail = addressDetail;
		this.sellerNo = sellerNo;
		this.shopMobile = shopMobile;
		this.shopName = shopName;
		this.shopKakaoId = shopKakaoId;
		this.shopImg = shopImg;
		this.shopCategoryNo = shopCategoryNo;
		this.shopLocCode = shopLocCode;
	}
	
	/* 전체 데이터 */
	public Seller(String sellerId, String sellerPw, String name, String mobile, String email, String entryDate,
			String postNo, String address, String addressDetail, String grade, String sellerNo, String shopMobile,
			String shopName, double custScore, String shopKakaoId, String shopImg, String shopCategoryNo,
			String shopLocCode) {
		this.sellerId = sellerId;
		this.sellerPw = sellerPw;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.entryDate = entryDate;
		this.postNo = postNo;
		this.address = address;
		this.addressDetail = addressDetail;
		this.grade = grade;
		this.sellerNo = sellerNo;
		this.shopMobile = shopMobile;
		this.shopName = shopName;
		this.custScore = custScore;
		this.shopKakaoId = shopKakaoId;
		this.shopImg = shopImg;
		this.shopCategoryNo = shopCategoryNo;
		this.shopLocCode = shopLocCode;
	}

	/* getter, setter */
	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerPw() {
		return sellerPw;
	}

	public void setSellerPw(String sellerPw) {
		this.sellerPw = sellerPw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getPostNo() {
		return postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSellerNo() {
		return sellerNo;
	}

	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
	}

	public String getShopMobile() {
		return shopMobile;
	}

	public void setShopMobile(String shopMobile) {
		this.shopMobile = shopMobile;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public double getCustScore() {
		return custScore;
	}

	public void setCustScore(double custScore) {
		this.custScore = custScore;
	}

	public String getShopKakaoId() {
		return shopKakaoId;
	}

	public void setShopKakaoId(String shopKakaoId) {
		this.shopKakaoId = shopKakaoId;
	}

	public String getShopImg() {
		return shopImg;
	}

	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}

	public String getShopCategoryNo() {
		return shopCategoryNo;
	}

	public void setShopCategoryNo(String shopCategoryNo) {
		this.shopCategoryNo = shopCategoryNo;
	}

	public String getShopLocCode() {
		return shopLocCode;
	}

	public void setShopLocCode(String shopLocCode) {
		this.shopLocCode = shopLocCode;
	}

	public String getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(String shopCategory) {
		this.shopCategory = shopCategory;
	}

	@Override
	public String toString() {
		return "Seller [sellerId=" + sellerId + ", sellerPw=" + sellerPw + ", name=" + name + ", mobile=" + mobile
				+ ", email=" + email + ", entryDate=" + entryDate + ", postNo=" + postNo + ", address=" + address
				+ ", addressDetail=" + addressDetail + ", grade=" + grade + ", sellerNo=" + sellerNo + ", shopMobile="
				+ shopMobile + ", shopName=" + shopName + ", custScore=" + custScore + ", shopKakaoId=" + shopKakaoId
				+ ", shopImg=" + shopImg + ", shopCategoryNo=" + shopCategoryNo + ", shopLocCode=" + shopLocCode
				+ ", shopCategory=" + shopCategory + "]";
	}

	
	
} // Seller 끝