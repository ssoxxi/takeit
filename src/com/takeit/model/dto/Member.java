package com.takeit.model.dto;

import java.io.Serializable;

/**
 * 일반회원관리 도메인
 * @author  임우진
 * @since   jdk1.8
 * @version v2.0
 */
public class Member implements Serializable{	
	
	/** 아이디 : 필수입력, 중복불가, 6~20자 영문+숫자 조합 */
	private String memberId;
	
	/** 비밀번호 : 필수입력, 8~20자 영대소문자+숫자 조합 */
	private String memberPw;
	
	/** 이름 : 필수입력, 2~6자 한글만 입력 */
	private String name;
	
	/** 휴대폰 : 필수입력, 000-0000-0000 형식에 맞춰 입력 */
	private String mobile;
	
	/** 이메일 : 필수입력, 중복불가, takeit@take.com 형식에 맞춰 입력 */	
	private String email;
	
	/** 가입일 : 시스템 부여, yyyy.MM.dd HH:mm:ss 형식, Timestamp */	
	private String entryDate;
	
	/** 우편번호 : 필수입력, api로 데이터를 받아옴 */
	private String postNo;
	
	/** 도로명주소 : 필수입력, api로 데이터를 받아옴 */
	private String address;
	
	/** 상세주소 : 필수입력, api로 데이터를 받아옴 */
	private String addressDetail;
	
	/** 적립금 : 시스템 부여, 기본값 : 1000 */
	private int point;
	
	/** 생일 : 사용자 입력, 10자이내 문자열 형식 */
	private String birth;
	
	/** 등급 : 시스템 부여, 기본값 : G */
	private String grade; 
	
	/** 회원구역번호 : 시스템부여*/
	private String memberLocNo;

	/** 상점구역코드 : 시스템부여*/
	private String shopLocCode;
	
	/** 기본 생성자 */
	public Member() {}

	/** 사용자 입력 데이터 */
	public Member(String memberId, String memberPw, String name, String mobile, String email, String postNo,
			String address, String addressDetail, String birth) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.postNo = postNo;
		this.address = address;
		this.addressDetail = addressDetail;
		this.birth = birth;
	}

	/** 전체 데이터 */
	public Member(String memberId, String memberPw, String name, String mobile, String email, String entryDate,
			String postNo, String address, String addressDetail, int point, String birth, String grade,
			String memberLocNo, String shopLocCode) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.entryDate = entryDate;
		this.postNo = postNo;
		this.address = address;
		this.addressDetail = addressDetail;
		this.point = point;
		this.birth = birth;
		this.grade = grade;
		this.memberLocNo = memberLocNo;
		this.shopLocCode = shopLocCode;
	}

	/** getter, setter */
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
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

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMemberLocNo() {
		return memberLocNo;
	}

	public void setMemberLocNo(String memberLocNo) {
		this.memberLocNo = memberLocNo;
	}

	public String getShopLocCode() {
		return shopLocCode;
	}

	public void setShopLocCode(String shopLocCode) {
		this.shopLocCode = shopLocCode;
	}

	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", memberPw=" + memberPw + ", name=" + name + ", mobile=" + mobile
				+ ", email=" + email + ", entryDate=" + entryDate + ", postNo=" + postNo + ", address=" + address
				+ ", addressDetail=" + addressDetail + ", point=" + point + ", birth=" + birth + ", grade=" + grade
				+ ", memberLocNo=" + memberLocNo + ", shopLocCode=" + shopLocCode + "]";
	}
} // Member 끝