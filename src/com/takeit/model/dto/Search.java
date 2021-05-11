package com.takeit.model.dto;

/** 상품 검색(상품명, 상호명, 판매자)
 *	@author 	한소희
 *  @since	jdk1.8
 *  @version v2.0 
 */
public class Search {
	/** 상품 카테고리 번호, 사용자 입력(전체 카테고리에서 선택시 자동입력) */
	private String itemCategoryNo;
	
	/** 상품 카테고리명, itemCategoryNo로 자동부여, 필수 */
	private String itemCategoryName;
	
	/** 상품 신선도, %단위  */
	private int freshPercent;

	/** 상품 번호, 필수 */
	private String itemNo;
	
	/** 상품명, 필수 */
	private String itemName;
	
	/** 상품 판매가, 필수 */
	private int itemPrice;
	
	/** 상품 이미지, 판매자입력, 필수 */
	private String itemImg;
	
	/** 상품 할인율, 필수, %단위 */
	private int discRate;
	
	/** 판매자 아이디, 사용자입력  */
	private String sellerId;
	
	/** 판매자 이름, 사용자입력 */
	private String name;
	
	/** 상점명, 사용자입력 */
	private String shopName;

	public Search() {}
	
	
	/**상품검색
	 * @param itemCategoryName	상품 카테고리명
	 * @param itemNo			상품번호
	 * @param itemName			상품명
	 * @param sellerId			상품 판매자 아이디
	 * @param itemImg			상품 이미지
	 * @param name				상품 판매자 이름
	 */
	public Search(String itemCategoryName, String itemNo, String itemName, String sellerId,
			String itemImg, String name) {
		super();
		this.itemCategoryName = itemCategoryName;
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.sellerId = sellerId;
		this.name = name;
		this.itemImg = itemImg;
	}
	
	/**판매자검색
	 * @param itemNo	상품번호
	 * @param itemName	상품명
	 * @param sellerId	상품 판매자 아이디
	 * @param name		상품 판매자 이름
	 * @param shopName	상품 판매자 상호명
	 */
	public Search(String itemNo, String itemName, String sellerId, String name,  String shopName ) {
		super();
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.sellerId = sellerId;
		this.name = name;
		this.shopName = shopName;
	}


	public String getItemCategoryNo() {
		return itemCategoryNo;
	}
	public void setItemCategoryNo(String itemCategoryNo) {
		this.itemCategoryNo = itemCategoryNo;
	}
	public String getItemCategoryName() {
		return itemCategoryName;
	}
	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}
	public int getFreshPercent() {
		return freshPercent;
	}
	public void setFreshPercent(int freshPercent) {
		this.freshPercent = freshPercent;
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
	public int getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getItemImg() {
		return itemImg;
	}
	public void setItemImg(String itemImg) {
		this.itemImg = itemImg;
	}
	public int getDiscRate() {
		return discRate;
	}
	public void setDiscRate(int discRate) {
		this.discRate = discRate;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(itemCategoryNo);
		builder.append(", ");
		builder.append(itemCategoryName);
		builder.append(", ");
		builder.append(freshPercent);
		builder.append(", ");
		builder.append(itemNo);
		builder.append(", ");
		builder.append(itemName);
		builder.append(", ");
		builder.append(itemPrice);
		builder.append(", ");
		builder.append(itemImg);
		builder.append(", ");
		builder.append(discRate);
		builder.append(", ");
		builder.append(sellerId);
		builder.append(", ");
		builder.append(name);
		builder.append(", ");
		builder.append(shopName);
		builder.append(", ");
		return builder.toString();
	}
	
	
}
