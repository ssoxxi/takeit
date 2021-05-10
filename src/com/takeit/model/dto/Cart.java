package com.takeit.model.dto;

/** 장바구니
 * @author 	한소희
 * @since	jdk1.8
 * @version v2.0
 *  
 */
public class Cart {
	/** 회원아이디, session에 저장된 memberId, 필수 */
	private String memberId;
	/** 장바구니 상품번호, request에 저장된 itemNo, 필수 */
	private String itemNo;
	/** 장바구니 상품명, itemNo로 자동 호출, 필수 */
	private String itemName;
	/** 장바구니 상품 이미지, itemNo로 자동호출, 필수 */
	private String itemImg;
	/** 장바구니 상품 판매자 아이디, itemNo로 자동호출, 필수 */
	private String sellerId;
	/** 장바구니 상품 판매자 이름, sellerId로 자동호출, 필수 */
	private String sellerName;
	/** 장바구니 상품 수량, 사용자 입력, 기본값 1, 필수 */
	private int cartItemQty;
	/** 장바구니 상품 판매가, itemNo로 자동호출, 필수  */
	private int itemPrice;
	/** 장바구니 할인율, itemPrice로 연산, 필수*/
	private int discRate;
	/** 장바구니 전체 가격, itemPrice로 연산, 필수*/
	private int totalPrice;
	/** 장바구니 전체 결제 금액, itemPrice*cartitemQty+배달료, 필수 */
	private int cartTotalPrice; 
	/** 장바구니 할인가, itemPrice * discRate로 연산, 필수*/
	private int discPrice;
	/** 잇거래 상품여부, 필수 */
	private String itemTakeit;
	
	
	
	public Cart() {}

	/***
	 * 장바구니 등록 
	 * @param memberId 		회원 아이디
	 * @param itemNo		상품 번호
	 * @param cartItemQty	장바구니 상품 수량
	 */
	public Cart(String memberId, String itemNo, int cartItemQty) {
		this.memberId = memberId;
		this.itemNo = itemNo;
		this.cartItemQty = cartItemQty;
	}

	/***
	 * @param memberId 		회원 아이디
	 * @param itemNo		상품번호
	 * @param itemImg		상품 이미지
	 * @param cartItemQty	장바구니 상품 수량
	 */
	public Cart(String memberId, String itemNo, String itemImg, int cartItemQty) {
		super();
		this.memberId = memberId;
		this.itemNo = itemNo;
		this.itemImg = itemImg;
		this.cartItemQty = cartItemQty;
	}

	/***
	 * @param itemName		상품명
	 * @param sellerName	판매자이름
	 * @param cartItemQty	장바구니 상품 수량
	 * @param totalPrice	장바구니 총 결제금액
	 */
	public Cart(String itemName, String sellerName, int cartItemQty, int totalPrice) {
		super();
		this.itemName = itemName;
		this.sellerName = sellerName;
		this.cartItemQty = cartItemQty;
		this.totalPrice = totalPrice;
	}

	
	public String getItemTakeit() {
		return itemTakeit;
	}

	public void setItemTakeit(String itemTakeit) {
		this.itemTakeit = itemTakeit;
	}

	public int getDiscPrice() {
		return discPrice;
	}

	public void setDiscPrice(int discPrice) {
		this.discPrice = discPrice;
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public int getCartItemQty() {
		return cartItemQty;
	}

	public void setCartItemQty(int cartItemQty) {
		this.cartItemQty = cartItemQty;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getItemImg() {
		return itemImg;
	}

	public void setItemImg(String itemImg) {
		this.itemImg = itemImg;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getCartTotalPrice() {
		return cartTotalPrice;
	}

	public void setCartTotalPrice(int cartTotalPrice) {
		this.cartTotalPrice = cartTotalPrice;
	}
	
	public int getDiscRate() {
		return discRate;
	}

	public void setDiscRate(int discRate) {
		this.discRate = discRate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(memberId);
		builder.append(", ");
		builder.append(itemNo);
		builder.append(", ");
		builder.append(itemName);
		builder.append(", ");
		builder.append(itemImg);
		builder.append(", ");
		builder.append(sellerId);
		builder.append(", ");
		builder.append(sellerName);
		builder.append(", ");
		builder.append(cartItemQty);
		builder.append(", ");
		builder.append(itemPrice);
		builder.append(", ");
		builder.append(discRate);
		builder.append(", ");
		builder.append(totalPrice);
		builder.append(", ");
		builder.append(cartTotalPrice);
		return builder.toString();
	}

	 
	
	
	
	
	
}
