<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.takeit.model.dto.*" %>
<%@ include file="/common/taglib_menu.jsp" %> 
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>takeit::카테고리상품</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/item.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
</head>
<body>
<%
	if(((ArrayList<Item>)request.getAttribute("categoryItemList")).isEmpty()){
		response.sendRedirect("/takeit/item/noItemList.jsp");
		return;
	}
%>
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

<!-- contents menu -->
<%
ArrayList<Item> categoryItemList = (ArrayList<Item>)request.getAttribute("categoryItemList");
String categoryName = categoryItemList.get(0).getItemCategoryName();
%>
<div id="title" style="width: fit-content; margin: 0 auto;">
	<h3>[<%= categoryName %>] 상품 목록</h3>
</div>

<div class="item_wrap">
<c:forEach var="dto" items="${categoryItemList}">
	<div class="item_list" style="white-space: nowrap;">
		<div>
		<a href="/takeit/item/itemController?action=itemDetail&itemNo=${dto.itemNo}">
			<img id="itemListImg" src="/takeit/img/item/${dto.itemImg}">
		</a>
		</div>
		<a href="/takeit/item/itemController?action=itemDetail&itemNo=%{dto.itemNo}">
		<span id="shop-name">[${dto.shopName}]</span><br>
		<span id="itemLI">${dto.itemName}</span><br>
		</a>
		<span id="itemFr">신선도 :${dto.freshPercent}%</span><br>
		<span id="itemPr">&#8361;<fmt:formatNumber value="${dto.itemPrice}" pattern="###,###"/></span>
		<span id="itemDc" style="color: red">(${dto.discRate}%할인)</span>
		<span id="itemDiscPrice"><fmt:formatNumber value="${dto.itemPrice * (100 - dto.discRate)/100 }" pattern="###,###"/>원</span>
	</div>
</c:forEach>
</div>

<!-- floating Banner -->
<jsp:include page="/common/floatingBanner.jsp"></jsp:include>
<!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
<!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>