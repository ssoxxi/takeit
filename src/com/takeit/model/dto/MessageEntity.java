package com.takeit.model.dto;

import java.util.ArrayList;
import java.util.HashMap;

/** 메세지 도메인 클래스 */
public class MessageEntity {
	private HashMap<String, ArrayList<String>> messageList = new HashMap<String, ArrayList<String>>();
	private String url;
	private String linkTitle;
	private String type;
	private int index;
	
	public MessageEntity() {

		ArrayList<String> error = new ArrayList<String>();
		error.add("[회원 등록 오류]");
		error.add("[회원 조회 오류]");
		error.add("[회원 수정 오류]");
		error.add("[회원 검색 오류]");
		error.add("[회원 탈퇴 오류]");
		error.add("[아이디 또는 비밀번호를 확인해주세요.]"); //5
		error.add("[이름 또는 이메일을 확인해주세요.]"); //6
		
		error.add("[상품 등록 오류]");
		error.add("[상품 조회 오류]");
		error.add("[상품 검색 오류]");
		error.add("[상품 삭제 오류]"); //10
		
		error.add("[잇거래 등록 오류]");
		error.add("[잇거래 조회 오류]");
		error.add("[잇거래 검색 오류]");
		error.add("[잇거래 삭제 오류]"); //14
		
		error.add("[게시글 등록 오류]"); 
		error.add("[게시글 조회 오류]");
		error.add("[게시글 검색 오류]");
		error.add("[게시글 삭제 오류]");		
		error.add("[게시글 변경 오류]"); //19
		
		error.add("[장바구니 등록/변경 오류]");
		error.add("[장바구니 조회 오류]");
		error.add("[장바구니 검색 오류]");
		error.add("[장바구니 삭제 오류]"); //23
		
		error.add("[상품전체조회 오류]"); 
		error.add("[후기전체조회 오류]"); 
		error.add("[후기정보 변경오류]"); 
		error.add("[후기 삭제오류]"); 
		error.add("[상품 조회오류]"); 
		
		error.add("[주문 등록 오류]"); //29
		error.add("[주문 조회 오류]");
		error.add("[주문 삭제 오류]");
		error.add("[후기 상세조회 오류]");
	
		error.add("[회원가입 오류]"); //33
		error.add("[로그인 오류]"); 
		error.add("[아이디찾기 오류]"); 
		error.add("[비밀번호찾기 오류]"); 
		error.add("[아이디 중복체크 오류]"); 
		error.add("[이메일 중복체크 오류]"); 

		error.add("[배송상태 조회 오류]"); //39
    
		error.add("[등록상품 변경오류]"); 
		error.add("[전체조회 오류]");

		error.add("[상점명 중복체크 오류]"); //42

		error.add("[지역시장 중복 등록]"); //43
		error.add("[등록상품 변경오류] "); 
		error.add("[지역시장 삭제 오류]");
		error.add("[주문 내역 없음]");

		
		

		
		
		
		
		
		
		ArrayList<String> validation = new ArrayList<String>();
		validation.add("[아이디 정보 오류]");	
		validation.add("[비밀번호 정보 오류]");	
		validation.add("[이름 정보 오류]");	
		validation.add("[주소 정보 오류]");		
		validation.add("[필수 정보 미기입 오류]"); //4
		validation.add("[상품등록 데이터 미입력]"); 
		validation.add("[후기등록 데이터 미입력]"); 
		
		ArrayList<String> success = new ArrayList<String>();
		success.add("[회원가입 성공]");	
		success.add("[로그인 성공]");	
		success.add("[회원 로그아웃 성공]");		
		success.add("[회원정보 수정 성공]");
		success.add("[회원 탈퇴 성공]");
		success.add("[상품정보 수정 성공]");		
		success.add("[상품삭제 성공]");		
		success.add("[잇거래 등록 성공]");
		success.add("[잇거래 갱신 성공]");
		success.add("[공지사항/자주하는질문 등록 성공]");
		success.add("[공지사항/자주하는질문 갱신 성공]");//10
		success.add("[상품 삭제완료]");
		success.add("[후기 등록완료]");
		success.add("[후기 변경완료]");
		success.add("[상품 등록 성공]");
		success.add("[후기 삭제 성공]");
		success.add("[지역상점 삭제 성공]");
		ArrayList<String> message = new ArrayList<String>();
		message.add("[이 페이지는 로그인이 필요합니다.]");
		message.add("[일반회원 전용]");
		message.add("[관리자 전용]"); //2
		
		
		messageList.put("error", error);
		messageList.put("validation", validation);
		messageList.put("success", success);
		messageList.put("message", message);
	}
	
	public MessageEntity(String type, int index) {
		this();
		
		this.type = type;
		this.index = index;
	}

	public String getMessage() {
		return messageList.get(type).get(index);
	}

	public String getUrl() {
		return url;
	}

	public String getLinkTitle() {
		return linkTitle;
	}

	public String getType() {
		return type;
	}

	public int getIndex() {
		return index;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

}
