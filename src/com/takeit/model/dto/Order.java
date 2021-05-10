package com.takeit.model.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <pre>
 * 주문 도메인 클래스
 * </pre>
 * @author 김태경
 * @since jdk1.8
 * @version v2.0
 */
public class Order implements Serializable {
	/** 잇거래여부, 필수, ('T', 'F') */
	private String itemTakeit;
	
	/** 배송상태이름(주문접수|o-get, 상품인수|i-get, 상품이동중|i-move, 배달지도착|s-arr, 배달출발|s-go, 배달완료|done), 판매자 입력 */
	private String shipStatus;
	
	/** 주문 번호, 시스템자동부여, 필수*/
	private String orderNo;
	
	/** 수령방법(배송/직접수령), 사용자 입력, 필수 */
	private String receiveMethod;
	
	/** 수령인 이름, 사용자 입력, 필수 */
	private String recipientName;
	
	/** 수령인 우편번호, 사용자 주소api통해 입력, 배송수령 선택한 사용자 필수 */
	private String recipientPostNo;
	
	/** 수령인 도로명주소, 주소api에서 자동입력, 배송수령 선택한 사용자 필수*/
	private String recipientAddr;
	
	/** 수령인 상세주소, 사용자 입력, 배송수령 선택한 사용자 필수*/
	private String recipientAddrDetail;
	
	/** 수령인 연락처, 사용자 입력, 배송수령 선택한 사용자  필수 */
	private String recipientMobile;
	
	/** 배송요청사항, 사용자입력 */
	private String shipRequest;
	
	/** 주문금액, 시스템계산, 필수 */
	private int orderPrice;
	
	/** 배송상태코드(주문접수|o-get, 상품인수|i-get, 상품이동중|i-move, 배달지도착|s-arr, 배달출발|s-go, 배달완료|done) */
	private String shipStatusCode;
	
	/** 주문자 아이디, session에서 받아옴, 필수 */
	private String memberId;
	
	/** 주문 취소요청, 사용자 입력 */
	private String orderCancelReq;
	
	/** 주문 취소, 판매자 입력 */
	private String orderCancel;
	
	/** 판매자 아이디, itemNo로 접근, 필수*/
	private String sellerId;
	
	/** 상점명, sellerId로 접근, 필수*/
	private String shopName;

	/** 주문상세정보 */
	private ArrayList<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

	
	/** 기본 생성자 */
	public Order() { }

	/**
	 * 전체 데이터 초기화 생성자
	 * @param itemTakeit      잇거래여부
	 * @param shipStatus      배송상태
	 * @param orderNo         주문번호
	 * @param receiveMethod   수령방법
	 * @param recipientName   수령인 이름
	 * @param recipientPostNo 수령인 우편번호
	 * @param recipientAddr   수령인 주소
	 * @param recipientAddrDetail 수령인 상세주소
	 * @param recipientMobile 수령인 연락처
	 * @param shipRequest     배송요청사항
	 * @param orderPrice      총주문금액
	 * @param shipStatusCode  배송상태코드
	 * @param memberId        주문자 아이디
	 * @param orderCancelReq  주문취소요청여부
	 * @param orderCancel     주문취소여부
	 * @param sellerId		    판매자아이디
	 * @param shopName		    상점명
	 * @param orderDetails    주문상세
	 */
	public Order(String itemTakeit, String shipStatus, String orderNo, String receiveMethod, String recipientName,
			String recipientPostNo, String recipientAddr, String recipientAddrDetail, String recipientMobile,
			String shipRequest, int orderPrice, String shipStatusCode, String memberId, String orderCancelReq,
			String orderCancel, String sellerId, String shopName, ArrayList<OrderDetail> orderDetails) {
		super();
		this.itemTakeit = itemTakeit;
		this.shipStatus = shipStatus;
		this.orderNo = orderNo;
		this.receiveMethod = receiveMethod;
		this.recipientName = recipientName;
		this.recipientPostNo = recipientPostNo;
		this.recipientAddr = recipientAddr;
		this.recipientAddrDetail = recipientAddrDetail;
		this.recipientMobile = recipientMobile;
		this.shipRequest = shipRequest;
		this.orderPrice = orderPrice;
		this.shipStatusCode = shipStatusCode;
		this.memberId = memberId;
		this.orderCancelReq = orderCancelReq;
		this.orderCancel = orderCancel;
		this.sellerId = sellerId;
		this.shopName = shopName;
		this.orderDetails = orderDetails;
	}

	public String getRecipientAddrDetail() {
		return recipientAddrDetail;
	}

	public void setRecipientAddrDetail(String recipientAddrDetail) {
		this.recipientAddrDetail = recipientAddrDetail;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getOrderCancelReq() {
		return orderCancelReq;
	}

	public void setOrderCancelReq(String orderCancelReq) {
		this.orderCancelReq = orderCancelReq;
	}

	public String getOrderCancel() {
		return orderCancel;
	}

	public void setOrderCancel(String orderCancel) {
		this.orderCancel = orderCancel;
	}

	public String getItemTakeit() {
		return itemTakeit;
	}

	public String getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(String shipStatus) {
		this.shipStatus = shipStatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getRecipientPostNo() {
		return recipientPostNo;
	}

	public void setRecipientPostNo(String recipientPostNo) {
		this.recipientPostNo = recipientPostNo;
	}

	public String getRecipientAddr() {
		return recipientAddr;
	}

	public void setRecipientAddr(String recipientAddr) {
		this.recipientAddr = recipientAddr;
	}

	public String getRecipientMobile() {
		return recipientMobile;
	}

	public void setRecipientMobile(String recipientMobile) {
		this.recipientMobile = recipientMobile;
	}

	public String getShipRequest() {
		return shipRequest;
	}

	public void setShipRequest(String shipRequest) {
		this.shipRequest = shipRequest;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getShipStatusCode() {
		return shipStatusCode;
	}

	public void setShipStatusCode(String shipStatusCode) {
		this.shipStatusCode = shipStatusCode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public ArrayList<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public void setItemTakeit(String itemTakeit) {
		this.itemTakeit = itemTakeit;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(itemTakeit);
		builder.append(", ");
		builder.append(shipStatus);
		builder.append(", ");
		builder.append(orderNo);
		builder.append(", ");
		builder.append(receiveMethod);
		builder.append(", ");
		builder.append(recipientName);
		builder.append(", ");
		builder.append(recipientPostNo);
		builder.append(", ");
		builder.append(recipientAddr);
		builder.append(", ");
		builder.append(recipientAddrDetail);
		builder.append(", ");
		builder.append(recipientMobile);
		builder.append(", ");
		builder.append(shipRequest);
		builder.append(", ");
		builder.append(orderPrice);
		builder.append(", ");
		builder.append(shipStatusCode);
		builder.append(", ");
		builder.append(memberId);
		builder.append(", ");
		builder.append(orderCancelReq);
		builder.append(", ");
		builder.append(orderCancel);
		builder.append(", ");
		builder.append(sellerId);
		builder.append(", ");
		builder.append(shopName);
		builder.append(", ");
		builder.append(orderDetails);
		return builder.toString();
	}
}
