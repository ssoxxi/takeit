package com.takeit.model.dto;

import java.io.Serializable;

/**
 * <pre>
 * 배송상태 도메인 클래스
 * </pre>
 * @author 김태경
 * @since jdk1.8
 * @version v2.0
 */
public class Shipping implements Serializable {
	/** 배송상태코드 : 필수데이터, 중복불가 , (O-GET, I-GET, I-MOVE, S-ARR, S-GO, DONE) */
	private String shipStatusCode;
	
	/** 배송상태이름 : 필수데이터, (주문접수, 상품인수, 상품이동중, 배달지도착, 배달출발, 배달완료) */
	private String shipStatus;
	
	
	/** 기본 생성자 */
	public Shipping() {}
	
	/**
	 * 전체 데이터 초기화 생성자
	 * @param shipStatusCode 배송상태코드
	 * @param shipStatus 배송상태
	 */
	public Shipping(String shipStatusCode, String shipStatus) {
		super();
		this.shipStatusCode = shipStatusCode;
		this.shipStatus = shipStatus;
	}


	public String getShipStatusCode() {
		return shipStatusCode;
	}

	public void setShipStatusCode(String shipStatusCode) {
		this.shipStatusCode = shipStatusCode;
	}

	public String getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(String shipStatus) {
		this.shipStatus = shipStatus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(shipStatusCode);
		builder.append(", ");
		builder.append(shipStatus);
		return builder.toString();
	}
}
