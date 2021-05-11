<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/mypage/myPage.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
</head>
<body>
<c:if test="${empty dto}">
	<jsp:useBean id="message" class="com.takeit.model.dto.MessageEntity" scope="request" />
	<jsp:setProperty property="type" name="message" value="message"/>
	<jsp:setProperty property="index" name="message" value="0"/>
	<jsp:setProperty property="url" name="message" value="${CONTEXT_PATH}/index"/>
	<jsp:setProperty property="linkTitle" name="message" value="처음으로"/>
	<jsp:forward page="/message.jsp"/>
</c:if>

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
<div id="no-orderList-wrap">
	<div id="no-orderList">
		<div class="side-menu" >
		<c:choose>
			<c:when test ="${sellerId != null}">
		 		<!-- 판매자 마이페이지 메뉴 -->
		 		<jsp:include page="/common/mypage_seller_menu.jsp"></jsp:include>
			</c:when>
			<c:otherwise>
				<!-- 일반회원 마이페이지 메뉴 -->
				<jsp:include page="/common/mypage_member_menu.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>
		</div>
		<div id="no-orderList-main">
			<div id="noOrderList-title">
				<h3>주문내역 </h3>
			</div>
			<div id="noOrderList-info">
				<span>현재 고객님의 주문 상품이 존재하지 않습니다.</span><br>
				<span>상품 구매후 이용 부탁드립니다.</span><br>
			</div>
			<a href="/takeit/index" class="link" style="margin-top: 30px;">홈으로 이동</a>
		</div>
	</div>
</div>
 <!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
 <!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
 
</body>
</html>
