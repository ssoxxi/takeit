<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매자등록상품</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/item.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
</head>
<body>
<!-- 상단 메뉴 -->
<c:if test="${empty memberId and empty sellerId}">
	<!-- 로그인 전 메뉴 -->
	<jsp:include page="/common/before_login_menu.jsp"></jsp:include>
</c:if>
<c:if test="${not empty memberId or not empty sellerId}">
	<!-- 로그인 후 메뉴 -->
	<jsp:include page="/common/after_login_menu.jsp"></jsp:include>	
</c:if>
<!-- logo.jsp 삽입 -->
<jsp:include page="/common/logo.jsp"></jsp:include>
<!-- 네비게이션 -->
<jsp:include page="/common/navigation.jsp"></jsp:include>
<div id="mySell-wrap">
	<div id="mySell" align='center'>
		<h1 style="margin: 10px; font-size: 30px;">내 등록상품 수정/삭제</h1>
		<form
			action="/takeit/item/itemController?action=setSellItem&itemNo=${item.itemNo}"
			method="post">
			<table id="updateSEll_table" id="updateSEll_table">
				<tr>
					<th>포장타입번호</th>
					<td><input type="text" name="packTypeNo" id="packTypeNo"
						value="${item.packTypeNo }"></td>

					<th>포장타입</th>
					<td><input type="text" name="packTypeName" id="packTypeName"
						value="${item.packTypeName }"></td>

				</tr>

				<tr>
					<th>상품카테고리번호</th>
					<td><input type="text" name="itemCategoryNo"

						id="itemCategoryNo" value="${item.itemCategoryNo}"
						readonly="readonly"></td>

					<th>상품카테고리이름</th>
					<td><input type="text" name="itemCategoryName"
						id="itemCategoryName" value="${item.itemCategoryName }"
						readonly="readonly"></td>
				</tr>

				<tr>
					<th>판매자</th>
					<td><input type="text" name="sellerName" id="sellerName"
						value="${item.sellerName }" readonly="readonly"></td>


					<th>상품번호</th>
					<td><input type="text" name="itemNo" id="itemNo"
						value="${item.itemNo }" readonly="readonly"></td>
				</tr>


				<tr>

					<th>판매가(원)</th>
					<td><input type="text" name="itemPrice" id="itemPrice"
						value="${item.itemPrice }" ></td>


					<th>상품명</th>
					<td><input type="text" name="itemName" id="itemUpdate-itemName"
						value="${item.itemName }"></td>
				</tr>

				<tr>
					<th>원산지</th>
					<td><input type="text" name="itemOrigin" id="itemOrigin"
						value="${item.itemOrigin }"></td>

					<th>재고량</th>
					<td><input type="text" name="itemStock" id="itemStock"
						value="${item.itemStock }"></td>
				</tr>

				<tr>
					<th>고객평점</th>
					<td><input type="text" name="itemCustScore" id="itemCustScore"
						value="${item.itemCustScore }" readonly="readonly"></td>

					<th>할인율(%)</th>
					<td><input type="text" name="discRate" id="itemUpdate-discRate"
						value="${item.discRate }"></td>
				</tr>

				<tr>
					<th>판매단위</th>
					<td><input type="text" name="salesUnit" id="salesUnit"
						value="${item.salesUnit }"></td>

					<th>등록일자</th>
					<td><input type="text" name="itemInputDate" id="itemInputDate"
						value="${item.itemInputDate }" readonly="readonly"></td>
				</tr>

				<tr>

					<th>상품이미지</th>
					<td> <img src="/takeit/img/item/${item.itemImg }" id="itemUpdate-img"></td>
					<th>안내사항</th>
					<td><input type="text" name="notice" id="itemUpdate-notice" value="${item.notice }"></td>
			</tr>

				<tr>
					<th>유통기한(일)</th>

					<td><input type="text" name="expirationDate"
						id="expirationDate" value="${item.expirationDate }">
					</td>

					<th>잇거래여부</th>
					<td><input type="text" name="itemTakeit"
						id="itemTakeit" value="${item.itemTakeit}">
					</td>
					
				</tr>

				<tr>
					<th>신선도(%)</th>
					<td><input type="text" name="freshPercent"
						placeholder="freshPercent" value="${item.freshPercent }">
					</td>

					<th>판매상점</th>
					<td><input type="text" name="shopName" id="shopName"
						value="${item.shopName }"></td>
				</tr>

			</table>
			<div style="display: inline-block; margin-top: 10px;">
				<input class="link" type="submit" value="상품수정">
			</div>
			</form>
			<form action="/takeit/item/itemController?action=deleteItem&itemNo=${item.itemNo}" method="post">
				<input type="submit" class="link" value="상품삭제" style="margin-top: 10px;">
			</form>
	</div>
</div>
<!-- floating Banner -->
<jsp:include page="/common/floatingBanner.jsp"></jsp:include>
<!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
<!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>

</html>

