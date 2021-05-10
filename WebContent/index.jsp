<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<%@ page import="com.takeit.model.dto.*" %>
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>Take it::즐거움의 시작</title>
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
<!-- 메인배너 -->
<section id="main-visual" class="view-width">
	<div class="container" class="view-width">
		<div class="inner">
		<img alt="메인배너" src="/takeit/img/main/main1.jpg">
		</div>
		<div class="inner">
		<img alt="메인배너" src="/takeit/img/main/main2.jpg">
		</div>
		<div class="inner">
		<img alt="메인배너" src="/takeit/img/main/main3.jpg">
		</div>
		<div class="inner">
		<img alt="메인배너" src="/takeit/img/main/main4.jpg">
		</div>
		<div class="inner">
		<img alt="메인배너" src="/takeit/img/main/main5.jpg">
		</div>
	</div>
<div id="btn-wrap">
<button class="btn1">1</button>
<button class="btn2">2</button>
<button class="btn3">3</button>
<button class="btn4">4</button>
<button class="btn5">5</button>
</div>
</secion>
<script type="text/javascript">
document.querySelector(".btn2").addEventListener('click', function(){
	document.querySelector('.container').style.transform = 'translate(-100vw)';
});
document.querySelector(".btn3").addEventListener('click', function(){
	document.querySelector('.container').style.transform = 'translate(-200vw)';
});
document.querySelector(".btn4").addEventListener('click', function(){
	document.querySelector('.container').style.transform = 'translate(-300vw)';
});
document.querySelector(".btn5").addEventListener('click', function(){
	document.querySelector('.container').style.transform = 'translate(-400vw)';
});
document.querySelector(".btn1").addEventListener('click', function(){
	document.querySelector('.container').style.transform = 'translate(0vw)';
});
</script>
</section>
<!-- 상품추천 구역-->
<h3 style="width:fit-content; margin: 20px auto; font-size: 30px;">이 상품 어때요?</h3>
<div id="shortcut" class="view-width">
<a href="/takeit/item/itemController?action=itemList">바로가기>></a>
</div>
<div id="item-recomm" class="view-width">
<%
	ArrayList<Item> itemList = (ArrayList<Item>)request.getAttribute("itemList");
	for(int i=0; i <4; i++){
%>
<ul>
	<li>
		<a href="/takeit/item/itemController?action=itemDetail&itemNo=<%= itemList.get(i).getItemNo() %>">
		<img id="itemImg" title="<%= itemList.get(i).getItemImg() %>" src="/takeit/img/item/<%= itemList.get(i).getItemImg() %>">
		</a>
	</li>
	<li id="itemTitle">[<%= itemList.get(i).getShopName() %>]<%= itemList.get(i).getItemName() %></li>
	<li id="discRate"><%= itemList.get(i).getDiscRate() %>%</li>
	<li id="salePrice"><fmt:formatNumber value="<%= ((100-itemList.get(i).getDiscRate())*0.01)*itemList.get(i).getItemPrice() %>" pattern="###,###"/>원</li>
	<li id="price">&#8361;<fmt:formatNumber value="<%= itemList.get(i).getItemPrice() %>" pattern="###,###"/></li>
</ul>
<%
	}
%>
</div>
<!-- 후기 구역 -->
<h3 style="width:fit-content; margin: 20px auto; font-size: 30px;">베스트 후기</h3>
<div id="shortcut" class="view-width">
<a href="/takeit/item/reviewController?action=reviewList">바로가기>></a>
</div>
<div id="best-review" class="view-width">
<%
	ArrayList<Review> reviewList = (ArrayList<Review>)request.getAttribute("reviewList");
	for(int i=0; i<4; i++){
%>
<ul>
	<li>
		<a href="/takeit/item/reviewController?action=reviewDetail&reviewNo=<%= reviewList.get(i).getReviewNo()%>">
		<img id="review-img" src="/takeit/img/review/<%= reviewList.get(i).getReviewImg() %>">
		</a>
	</li>
	<li id="reviewTitle"><%= reviewList.get(i).getReviewTitle() %></li>
	<li id="reviewWriter"><%= reviewList.get(i).getMemberId() %></li>
	<li id="review"> 
		<a href="/takeit/item/reviewController?action=reviewDetail&reviewNo=<%= reviewList.get(i).getReviewNo() %>">

		<% 
			if((reviewList.get(i).getReviewContents()).length() > 10){
		%>
			<%= (reviewList.get(i).getReviewContents()).substring(0,10) %>. . . &nbsp;&nbsp;<span style="font-size: 10px; color: grey;">더보기>></span>
		<%
			} else {
		%>
			<%=	reviewList.get(i).getReviewContents() %>
		<%
			} 
		%>
		</a>
	</li>
</ul>
<%
	}
%>
</div>
<!-- floating Banner -->
<jsp:include page="/common/floatingBanner.jsp"></jsp:include>
<!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
<!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>