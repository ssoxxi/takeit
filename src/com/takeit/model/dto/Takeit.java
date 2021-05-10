package com.takeit.model.dto;

/**
 * <pre>
 * 잇거래 도메인 클래스
 * </pre>
 * @author 김태경
 * @since jdk1.8
 * @version v2.0
 */
public class Takeit {
	/** 잇거래번호 : 필수, 시스템 자동 입력 */
	private String takeitNo;
	
	/** 상점구역코드 : 필수 */
	private String shopLocCode;
	
	/** 상점명 : 필수 */
	private String shopLocName;
	
	/** 회원구역번호 : 필수 */
	private String memberLocNo;
	
	/** 모집금액 : 필수 */
	private int takeitPrice;
	
	/** 현재 모집된 금액 : 필수 */
	private int takeitCurrPrice;
	
	/** 모집시작일자 : 필수 */
	private String takeitDate;
	
	/** 모집마감일자: 모집시작일자+7 */
	private String takeitEndDate;
	
	/** 잇거래진행여부 : 필수 */
	private String takeitAlive;

	
	/** 기본 생성자 */
	public Takeit() { }

	/**
	 * 전체 데이터 초기화 생성자
	 * @param takeitNo 잇거래번호
	 * @param shopLocCode 상점구역코드
	 * @param shopLocName 상점구역이름
	 * @param memberLocNo 회원구역번호
	 * @param takeitPrice 모집금액
	 * @param takeitCurrPrice 현재금액
	 * @param takeitDate 모집일자
	 * @param takeitEndDate 종료일자
	 * @param takeitAlive 진행여부
	 */
	public Takeit(String takeitNo, String shopLocCode, String shopLocName, String memberLocNo, int takeitPrice,
			int takeitCurrPrice, String takeitDate, String takeitEndDate, String takeitAlive) {
		super();
		this.takeitNo = takeitNo;
		this.shopLocCode = shopLocCode;
		this.shopLocName = shopLocName;
		this.memberLocNo = memberLocNo;
		this.takeitPrice = takeitPrice;
		this.takeitCurrPrice = takeitCurrPrice;
		this.takeitDate = takeitDate;
		this.takeitEndDate = takeitEndDate;
		this.takeitAlive = takeitAlive;
	}


	public String getTakeitNo() {
		return takeitNo;
	}

	public void setTakeitNo(String takeitNo) {
		this.takeitNo = takeitNo;
	}

	public String getShopLocCode() {
		return shopLocCode;
	}

	public void setShopLocCode(String shopLocCode) {
		this.shopLocCode = shopLocCode;
	}

	public String getShopLocName() {
		return shopLocName;
	}

	public void setShopLocName(String shopLocName) {
		this.shopLocName = shopLocName;
	}

	public String getMemberLocNo() {
		return memberLocNo;
	}

	public void setMemberLocNo(String memberLocNo) {
		this.memberLocNo = memberLocNo;
	}

	public int getTakeitPrice() {
		return takeitPrice;
	}

	public void setTakeitPrice(int takeitPrice) {
		this.takeitPrice = takeitPrice;
	}

	public int getTakeitCurrPrice() {
		return takeitCurrPrice;
	}

	public void setTakeitCurrPrice(int takeitCurrPrice) {
		this.takeitCurrPrice = takeitCurrPrice;
	}

	public String getTakeitDate() {
		return takeitDate;
	}

	public void setTakeitDate(String takeitDate) {
		this.takeitDate = takeitDate;
	}

	public String getTakeitEndDate() {
		return takeitEndDate;
	}

	public void setTakeitEndDate(String takeitEndDate) {
		this.takeitEndDate = takeitEndDate;
	}

	public String getTakeitAlive() {
		return takeitAlive;
	}

	public void setTakeitAlive(String takeitAlive) {
		this.takeitAlive = takeitAlive;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(takeitNo);
		builder.append(", ");
		builder.append(shopLocCode);
		builder.append(", ");
		builder.append(shopLocName);
		builder.append(", ");
		builder.append(memberLocNo);
		builder.append(", ");
		builder.append(takeitPrice);
		builder.append(", ");
		builder.append(takeitCurrPrice);
		builder.append(", ");
		builder.append(takeitDate);
		builder.append(", ");
		builder.append(takeitEndDate);
		builder.append(", ");
		builder.append(takeitAlive);
		return builder.toString();
	}
}
