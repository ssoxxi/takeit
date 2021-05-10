package com.takeit.model.dto;

import java.io.Serializable;

/**
 * <pre>
 * 상점구역 도메인 클래스
 * </pre>
 * @author 김태경
 * @since jdk1.8
 * @version v2.0
 */
public class ShopLoc implements Serializable{
	
	/** 상점구역코드 : 필수데이터, 중복불가 */
	private String shopLocCode;
	
	/** 상점구역이름 : 필수데이터 */
	private String shopLocName;
	
	/** 위도 : 필수데이터 */
	private String shopLocLat;
	
	/** 경도 : 필수데이터 */
	private String shopLocLng;
	
	
	/** 기본 생성자 */
	public ShopLoc() {	}
	
	/**
	 * 전체 데이터 초기화 생성자
	 * @param shopLocCode 상점구역코드
	 * @param shopLocName 상점구역이름
	 * @param shopLocLat 경도
	 * @param shopLocLng 위도
	 */
	public ShopLoc(String shopLocCode, String shopLocName, String shopLocLat, String shopLocLng) {
		super();
		this.shopLocCode = shopLocCode;
		this.shopLocName = shopLocName;
		this.shopLocLat = shopLocLat;
		this.shopLocLng = shopLocLng;
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

	public String getShopLocLat() {
		return shopLocLat;
	}

	public void setShopLocLat(String shopLocLat) {
		this.shopLocLat = shopLocLat;
	}

	public String getShopLocLng() {
		return shopLocLng;
	}

	public void setShopLocLng(String shopLocLng) {
		this.shopLocLng = shopLocLng;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(shopLocCode);
		builder.append(", ");
		builder.append(shopLocName);
		builder.append(", ");
		builder.append(shopLocLat);
		builder.append(", ");
		builder.append(shopLocLng);
		return builder.toString();
	}
}
