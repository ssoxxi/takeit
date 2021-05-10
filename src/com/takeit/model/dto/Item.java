/**
 * 
 */
package com.takeit.model.dto;

import java.io.Serializable;

/**
 * 상품
 * @author 김효원
 * @since jdk1.8
 * @version v2.0
 */
public class Item implements Serializable{

	/**포장타입번호:(re789,ic789,ro123,el456),필수입력*/
	private String packTypeNo;

	/**포장타입이름:(냉장포장,냉동포장,상온포장,기타),필수입력 */
	private String packTypeName;

	/**상품카테고리번호:필수입력*/
	private String itemCategoryNo;

	/**상품카테고리이름:(야채,과일,곡물류,음료,유제품,고기류,반찬류,아이스크림),필수입력 */
	private String itemCategoryName;

	/**유통기한:남은 일수 숫자로 계산 */
	private String expirationDate;

	/**안내사항:필수입력*/
	private String notice;

	/**신선도*/
	private int freshPercent;

	/**상품번호:필수입력*/
	private String itemNo;

	/**판매자회원아이디:필수입력*/
	private String sellerId;

	/**상품명:필수입력*/
	private String itemName;

	/**판매가:필수입력*/
	private int itemPrice;

	/**판매단위*/
	private String salesUnit;

	/**원산지:필수입력*/
	private String itemOrigin;

	/**재고량:필수입력*/
	private int itemStock;

	/**상품이미지:이미지 업로드 파일사용,필수입력*/
	private String itemImg;

	/**고갱평점:필수입력*/
	private double itemCustScore;

	/**등록일자:필수입력*/
	private String itemInputDate;

	/**할인율*/
	private int discRate;

	/**잇거래여부:T,F로 선택,필수입력*/
	private String itemTakeit;

	/**판매자이름:필수입력*/
	private String sellerName;

	/**상점명:필수입력*/
	private String shopName;
	
	
	public Item() {}

	/**판매자 입력 필수데이터*/
	public Item(String itemCategoryName,String sellerId, String itemName, int itemPrice, String itemOrigin, int itemStock, String itemImg,
			String itemTakeit,String packTypeName,String expirationDate,String notice,int freshPercent) {
		
		this.itemCategoryName =itemCategoryName;
		this.sellerId = sellerId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemOrigin = itemOrigin;
		this.itemStock = itemStock;
		this.itemImg = itemImg;
		this.itemTakeit = itemTakeit;
		this.packTypeName = packTypeName;
        this.expirationDate = expirationDate;
		this.notice = notice;
		this.freshPercent = freshPercent;
	}
	
	/**상품 전체 데이터*/
	public Item(String packTypeNo, String packTypeName, String itemCategoryNo, String itemCategoryName,
			String expirationDate, String notice, int freshPercent, String itemNo, String sellerId, String itemName,
			int itemPrice, String salesUnit, String itemOrigin, int itemStock, String itemImg, double itemCustScore,
			String itemInputDate, int discRate, String itemTakeit, String sellerName, String shopName) {
		this(itemCategoryName,sellerId, itemName, itemPrice,itemOrigin,itemStock,itemImg,itemTakeit,packTypeName,expirationDate,notice,freshPercent);
		this.salesUnit = salesUnit;
		this.itemOrigin = itemOrigin;
		this.itemStock = itemStock;
		this.itemImg = itemImg;
		this.itemCustScore = itemCustScore;
		this.itemInputDate = itemInputDate;
		this.discRate = discRate;
		this.itemTakeit = itemTakeit;
		this.sellerName = sellerName;
		this.shopName = shopName;
		this.packTypeNo=packTypeNo;
		this.itemNo=itemNo;
		this.itemCategoryNo=itemCategoryNo;
	}



	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getPackTypeNo() {
		return packTypeNo;
	}

	public void setPackTypeNo(String packTypeNo) {
		this.packTypeNo = packTypeNo;
	}

	public String getPackTypeName() {
		return packTypeName;
	}

	public void setPackTypeName(String packTypeName) {
		this.packTypeName = packTypeName;
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

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
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

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
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

	public String getSalesUnit() {
		return salesUnit;
	}

	public void setSalesUnit(String salesUnit) {
		this.salesUnit = salesUnit;
	}

	public String getItemOrigin() {
		return itemOrigin;
	}

	public void setItemOrigin(String itemOrigin) {
		this.itemOrigin = itemOrigin;
	}

	public int getItemStock() {
		return itemStock;
	}

	public void setItemStock(int itemStock) {
		this.itemStock = itemStock;
	}

	public String getItemImg() {
		return itemImg;
	}

	public void setItemImg(String itemImg) {
		this.itemImg = itemImg;
	}

	public double getItemCustScore() {
		return itemCustScore;
	}

	public void setItemCustScore(double itemCustScore) {
		this.itemCustScore = itemCustScore;
	}

	public String getItemInputDate() {
		return itemInputDate;
	}

	public void setItemInputDate(String itemInputDate) {
		this.itemInputDate = itemInputDate;
	}

	public int getDiscRate() {
		return discRate;
	}

	public void setDiscRate(int discRate) {
		this.discRate = discRate;
	}

	public String getItemTakeit() {
		return itemTakeit;
	}

	public void setItemTakeit(String itemTakeit) {

		this.itemTakeit = itemTakeit;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(packTypeNo);
		builder.append(", ");
		builder.append(packTypeName);
		builder.append(", ");
		builder.append(itemCategoryNo);
		builder.append(", ");
		builder.append(itemCategoryName);
		builder.append(", ");
		builder.append(expirationDate);
		builder.append(", ");
		builder.append(notice);
		builder.append(", ");
		builder.append(freshPercent);
		builder.append(", ");
		builder.append(itemNo);
		builder.append(", ");
		builder.append(sellerId);
		builder.append(", ");
		builder.append(itemName);
		builder.append(", ");
		builder.append(itemPrice);
		builder.append(", ");
		builder.append(salesUnit);
		builder.append(", ");
		builder.append(itemOrigin);
		builder.append(", ");
		builder.append(itemStock);
		builder.append(", ");
		builder.append(itemImg);
		builder.append(", ");
		builder.append(itemCustScore);
		builder.append(", ");
		builder.append(itemInputDate);
		builder.append(", ");
		builder.append(discRate);
		builder.append(", ");
		builder.append(itemTakeit);
		builder.append(", ");
		builder.append(sellerName);
		builder.append(", ");
		builder.append(shopName);
		builder.append("]");
		return builder.toString();
	}
	
	
}

