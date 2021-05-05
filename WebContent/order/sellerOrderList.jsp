<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매자 판매목록 조회</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/mypage/myPage.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
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

<div id="container">
<c:choose>
	<c:when test ="${grade == 'S'}">
 		<!-- 판매자 마이페이지 메뉴 -->
 		<jsp:include page="/common/mypage_seller_menu.jsp"></jsp:include>
	</c:when>
	<c:otherwise>
		<!-- 일반회원 마이페이지 메뉴 -->
		<jsp:include page="/common/mypage_member_menu.jsp"></jsp:include>
	</c:otherwise>
</c:choose>

<div>
	<h3>주문내역</h3>
	<c:forEach var="order" items="${orderList}">
	<div>
		주문번호 : ${order.orderNo} <br>
		주문자 : ${order.memberId}<br>
		배송상태 : ${order.shipStatus}
		<input type="button" value="배송상태변경"/>
		<br>
		요청사항 : ${order.shipRequest}<br>
		
		<c:forEach var="orderDetail" items="${order.orderDetails}">
		<br>
		<img src="/takeit/img/item/${orderDetail.itemImg}" style="width:100px; height:150px;">
		<div style="display:inline-block;">
			상품명 : ${orderDetail.itemName} <br>
			상품개수 : ${orderDetail.itemQty}개 <br>
			상품결제금액 : ${orderDetail.itemPayPrice}원 <br> 
		</div><br>
		</c:forEach>
		<hr>
	</div><br>
	</c:forEach>
</div>

</div>
 <!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
 
 <!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>

</body>
</html>