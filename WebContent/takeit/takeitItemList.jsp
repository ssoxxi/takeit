<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>

<%@ page import="com.takeit.model.dto.*" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>잇거래 상품 목록</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/takeit.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/item.css">

<style>
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
	function getTakeitTime() {
		var date = new Date();
		
		for (i = 0; i < $(".takeitTime").length; i++) {
			var takeitTimeElement = $(".takeitTime").get(i);
			var takeitTime = takeitTimeElement.dataset.takeittime;
			var takeitDate =new Date(takeitTime);
			var result = takeitDate - date;
			result = result + 604800000;
			var d1 = parseInt(result / 86400000);
			var h1 = Math.floor((result % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
			var m1 = Math.floor((result % (1000 * 60 * 60)) / (1000 * 60));
			var s1 = Math.floor((result % (1000 * 60)) / 1000);
			
			takeitTimeElement.innerHTML = "남은시간 :" +d1 + "일 "+ h1+"시간 "+m1+"분 "+s1 + "초"
			
		}
	}
	$(document).ready(function (){
		getTakeitTime();
		setInterval(getTakeitTime, 1000);
	});
</script>
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

<h3 style="width:fit-content; margin: 20px auto; font-size: 30px;">잇거래</h3>

 <div class="item_wrap" style="display: flex;">
 	<%
	int i = 0;
	%>
	<div class="takeit_item_wrap">
	<c:forEach items="${takeitItemList}" var="dto"> 
	<%
		i++;
		if(i % 3 != 0){ //3의 배수가 아닐 때(flex)
	%>
		<ul class="takeit_item" style="display: inline-block;">

			<li style="width: 250px;">
				<span class="takeitTime takeit-listTime blink" data-takeittime="${dto.takeitDate}"></span><br>
				<a href="/takeit/takeit/takeitController?action=takeitItemDetail&itemNo=${dto.itemNo }">
					<img id="takeitImg" alt="${dto.itemImg}" src="/takeit/img/item/${dto.itemImg}">
				</a>
				<span class="item-fresh">신선도${100-(dto.discRate)}%</span>
			</li>
			<fmt:formatNumber var="itemPrice" value="${dto.itemPrice}" type="number"/>
			<fmt:formatNumber var="discPrice" value="${dto.itemPrice * (100-dto.discRate) / 100}" type="number"/>
			<fmt:parseNumber  var="intPrice" value="${(dto.itemPrice * (100-dto.discRate) / 100)/1000}" integerOnly="true"/>
			<fmt:formatNumber var="takeitItemPrice" value="${intPrice*1000}" type="number"/>
			<fmt:formatNumber var="itemDiscRate" value="${dto.discRate / 100}" type="percent"/>
			<fmt:formatNumber var="takeitDisc" value="${(dto.itemPrice * (100-dto.discRate) / 100) - intPrice*1000 }" type="number"/>
			<li id="itemTitle">${dto.itemName}</li>
			<li id="discRate">(할인 ${itemDiscRate}+${takeitDisc}원)</li>
			<li id="salePrice">${takeitItemPrice}원</li>
			<li id="price">${dto.itemPrice}원</li>
		</ul>

		<%
		} else if(i % 3 == 0){ //3의 배수일 때
	%>
		<ul class="takeit_item" style="display: inline-block;">
			<li style="width: 250px;">
				<span class="takeitTime takeit-listTime blink" data-takeittime="${dto.takeitDate}"></span><br>
				<a href="/takeit/takeit/takeitController?action=takeitItemDetail&itemNo=${dto.itemNo }">
					<img id="takeitImg" alt="${dto.itemImg}" src="/takeit/img/item/${dto.itemImg}">
				</a>
				<span class="item-fresh">신선도${100-(dto.discRate)}%</span>
			</li>
			<fmt:formatNumber var="itemPrice" value="${dto.itemPrice}" type="number"/>
			<fmt:formatNumber var="discPrice" value="${dto.itemPrice * (100-dto.discRate) / 100}" type="number"/>
			<fmt:parseNumber  var="intPrice" value="${(dto.itemPrice * (100-dto.discRate) / 100)/1000}" integerOnly="true"/>
			<fmt:formatNumber var="takeitItemPrice" value="${intPrice*1000}" type="number"/>
			<fmt:formatNumber var="itemDiscRate" value="${dto.discRate / 100}" type="percent"/>
			<fmt:formatNumber var="takeitDisc" value="${(dto.itemPrice * (100-dto.discRate) / 100) - intPrice*1000 }" type="number"/>
			<li id="itemTitle">${dto.itemName}</li>
			<li id="discRate">(할인 ${itemDiscRate}+${takeitDisc}원)</li>
			<li id="salePrice">${takeitItemPrice}원</li>
			<li id="price">${dto.itemPrice}원</li>
		</ul>
	<%
		i--;
		}
	%>
	</c:forEach>
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