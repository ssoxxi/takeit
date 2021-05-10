package com.takeit.model.dto;

import java.io.Serializable;

/**
 * <pre>
 * 잇거래 상품 도메인 클래스
 * </pre>
 * @author 김태경
 * @since jdk1.8
 * @version v2.0
 */
public class TakeitItem extends Item implements Serializable {
	/** 잇거래번호 : 필수 */
	private String takeitNo;
	
	/** 모집금액 : 필수 */
	private int takeitPrice;
	
	/**현재 모집된 금액 : 필수*/
	private int takeitCurrPrice;
	
	/** 모집시작일자 : 필수*/
	private String takeitDate;
	
	/** 고객평점 : 필수 */
	private double takeitCustScore;
	
	/** 잇거래진행여부 : 필수, ('T','F') */
	private String takeitAlive;
	
	/** 회원구역번호 : 필수 */
	private String memberLocNo;
	
	/** 상점구역코드 : 필수 */
	private String shopLocCode;
	
	/** 상점구역이름 : 필수 */
	private String shopLocName;

	
	/** 기본 생성자 */
	public TakeitItem() {}

	/**
	 * 전체 데이터 초기화 생성자
	 * @param takeitNo 잇거래번호
	 * @param takeitPrice 모집금액
	 * @param takeitCurrPrice 현재금액
	 * @param takeitDate 모집일자
	 * @param takeitCustScore 잇거래평점
	 * @param takeitAlive 진행여부
	 * @param memberLocNo 회원구역번호
	 * @param shopLocCode 상점구역코드
	 * @param shopLocName 상점구역이름
	 */
	public TakeitItem(String takeitNo, int takeitPrice, int takeitCurrPrice, String takeitDate, double takeitCustScore,
			String takeitAlive, String memberLocNo, String shopLocCode, String shopLocName) {
		super();
		this.takeitNo = takeitNo;
		this.takeitPrice = takeitPrice;
		this.takeitCurrPrice = takeitCurrPrice;
		this.takeitDate = takeitDate;
		this.takeitCustScore = takeitCustScore;
		this.takeitAlive = takeitAlive;
		this.memberLocNo = memberLocNo;
		this.shopLocCode = shopLocCode;
		this.shopLocName = shopLocName;
	}


	public String getShopLocName() {
		return shopLocName;
	}

	public void setShopLocName(String shopLocName) {
		this.shopLocName = shopLocName;
	}

	public String getTakeitDate() {
		return takeitDate;
	}

	public void setTakeitDate(String takeitDate) {
		this.takeitDate = takeitDate;
	}

	public String getTakeitNo() {
		return takeitNo;
	}

	public void setTakeitNo(String takeitNo) {
		this.takeitNo = takeitNo;
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

	public double getTakeitCustScore() {
		return takeitCustScore;
	}

	public void setTakeitCustScore(double takeitCustScore) {
		this.takeitCustScore = takeitCustScore;
	}

	public String getTakeitAlive() {
		return takeitAlive;
	}

	public void setTakeitAlive(String takeitAlive) {
		this.takeitAlive = takeitAlive;
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
		StringBuilder builder = new StringBuilder();
		builder.append(takeitNo);
		builder.append(", ");
		builder.append(takeitPrice);
		builder.append(", ");
		builder.append(takeitCurrPrice);
		builder.append(", ");
		builder.append(takeitDate);
		builder.append(", ");
		builder.append(takeitCustScore);
		builder.append(", ");
		builder.append(takeitAlive);
		builder.append(", ");
		builder.append(memberLocNo);
		builder.append(", ");
		builder.append(shopLocCode);
		builder.append(", ");
		builder.append(shopLocName);
		return super.toString() + builder.toString();
	}
}
