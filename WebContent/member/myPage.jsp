<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<style type="text/css">

.myPage_menu_aside ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    width: 200px;
    background-color: #f1f1f1;
}
.myPage_menu_aside li a {
    display: block;
    color: #001;
    padding: 8px 16px;
    text-decoration: none;
}
.myPage_menu_aside h3.active {
    background-color: #4CAF50;
    color: white;
}
.myPage_menu_aside li a:hover:not(.active) {
    background-color: #555;
    color: white;
}

#container{
	height: 700px;

}
.myPage_menu_aside {
	width: 200px;
	height: 500px;
	float: left;
	margin-left: 200px;
	margin-top: 100px;
}

#mypage_order{
		display: inline-block;
    padding: 0;
    width: 800px;
    height: 500px;
    float: left;
    margin-left: 170px;
}

.order_img{
	width: 149px;
	height: 240px;
	float:left;
}

#itemInfo{
	width:300px;
	height: 200px;
	float:left;
	margin-left: 50px;
	margin-top: auto;
}

#order_Info{
	width:700px;
	height:350px;
	margin-left: 30px;

}

#order_item{
	float:left;
	width:70px;
	margin-left: 100px;
	margin-top: 50px;

}

#item_review_btn{
	margin-bottom: 20px;
}
#shipping_ask_btn{
margin-bottom: 20px;
}
</style>
</head>
<body>

	
<!-- 상단 메뉴 -->
<c:choose>
	<c:when test="${empty memberId or empty grade}">
		<!-- 로그인 전 메뉴 -->
		<jsp:include page="/common/before_login_menu.jsp"></jsp:include>
	</c:when>
	<c:otherwise>
		<!-- 로그인 후 메뉴 -->
		<jsp:include page="/common/after_login_menu.jsp"></jsp:include>	
	</c:otherwise>
</c:choose>
<!-- logo.jsp 삽입 -->
<jsp:include page="/common/logo.jsp"></jsp:include>
<!-- 네비게이션 -->
<jsp:include page="/common/navigation.jsp"></jsp:include>
	
	
<h3 align="center">마이페이지</h3>

<div id="container">
	<c:choose>
		<c:when test ="${grade == 'S' }">
	 		<!-- 판매자 마이페이지 메뉴 -->
	 		<jsp:include page="/common/mypage_seller_menu.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<!-- 일반회원 마이페이지 메뉴 -->
			<jsp:include page="/common/mypage_member_menu.jsp"></jsp:include>
		</c:otherwise>
	</c:choose>
	
	
	<div id="mypage_order">
	<h3>주문내역</h3>
	<hr>
		<div id="order_Info">
		<h4>주문번호 : xxxxxxxxx </h4>
		
			<div >
				<img class="order_img" src="/takeit/img/item/item1.jpg">	
					<div id="itemInfo">
						<h6>상품명 : 1등급 마블링 한우</h6>
						<h6>상품 수량 : 1개</h6>
						<h6>결제금액 : 4000원</h6>
						<h6>수령 방법 : 배송</h6>
						<h6>주문 상태 : 배송중</h6>
					</div>
						<div id="order_item">
							<input id="shipping_ask_btn" type="button" value="배송문의">
							<input id="item_review_btn" type="button" value="상품 후기">
							<input type="button" value="상품 문의">
						</div>
			</div>
			
		</div>
		<hr>
	</div>

 </div>
 
 <!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
 
 <!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
 
</body>
</html>