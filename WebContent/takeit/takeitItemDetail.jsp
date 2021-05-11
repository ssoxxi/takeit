<%@page import="com.takeit.model.dto.TakeitItem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>잇거래 상품 상세조회</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/takeit.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/cart.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script>
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
		
		takeitTimeElement.innerHTML = d1 + "일 "+ h1+"시간 "+m1+"분 "+s1 + "초"
	}
}

function getTakeitExpDate() {
	var inputDate = new Date("${takeitItem.itemInputDate}");
	console.log("inputDate = " + inputDate);
	var expDate = document.getElementById("takeitExpDate").dataset.takeitexpdate;
	
	console.log("expDate = " +expDate);
	var experDate = new Date(inputDate.setDate(inputDate.getDate() + parseInt(expDate)));
	
	var year = experDate.getFullYear();
	var month = experDate.getMonth() + 1;
	month = month < 10 ? "0" + month : month;
	var day =  experDate.getDate();
	day = day < 10 ? "0" + day : day;
	
	var html = year +"-" + month + "-" + day
	document.getElementById("takeitExpDate").innerHTML = html;
}

function getTakeitFresh() {
	var expDateElement = document.getElementById("takeitExpDate");
	var expDate = expDateElement.dataset.takeitexpdate;
	var inputDate = new Date("${takeitItem.itemInputDate}");
	var date = new Date();
	
	console.log(date);
	console.log(inputDate);
	var result = date - inputDate;
	var day = parseInt(result / 86400000);
	
	
	
	var freshPercent = parseInt((expDate - day) / expDate * 100);
	if (freshPercent > 80) {
		$("#takeitFresh").css("color","green");
	} else if (freshPercent > 50) {
		$("#takeitFresh").css("color","blue");
	} else {
		$("#takeitFresh").css("color","red");
	}
	if (freshPercent < 0) {
		freshPercent = 0;
	}
	
	document.getElementById("takeitFresh").innerHTML = "신선도 " + freshPercent + "%";
}

$(document).ready(function (){
	getTakeitFresh();
	getTakeitExpDate();
	getTakeitTime();
	setInterval(getTakeitTime, 1000);
	
	$("#addCart-area").hide();
	
	$("#addCart").on("click",function(){
		var isDto = ${dto == null} ;
		var isSellerId = ${sellerId != null} ;
		
		if(isDto){
			location.href='/takeit/cartController?action=addCart';
			return;
		}
		if(isSellerId){
			alert('판매자는 구매할 수 없습니다');
			return;
		}
		$("#addCart-area").slideToggle(300);
	});
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

<div class="takeit_detail">
	<div class="takeit_item-content takeit_detail_wrap">
		<div class="takeitImg-wrap" >
			<img id="takeit-detailImg" style="width:330px; height: 400px; " src="/takeit/img/item/${takeitItem.itemImg}">
		</div>
		<div class="desc takeit_detail_wrap">
		
			<fmt:formatNumber var="itemPrice" value="${takeitItem.itemPrice}" type="number"/>
			<fmt:formatNumber var="discPrice" value="${takeitItem.itemPrice * (100-takeitItem.discRate) / 100}" type="number"/>
			<fmt:parseNumber  var="intPrice" value="${(takeitItem.itemPrice * (100-takeitItem.discRate) / 100)/1000}" integerOnly="true"/>
			<fmt:formatNumber var="takeitItemPrice" value="${intPrice*1000}" type="number"/>
			<fmt:formatNumber var="itemDiscRate" value="${takeitItem.discRate / 100}" type="percent"/>
			<fmt:formatNumber var="takeitDisc" value="${(takeitItem.itemPrice * (100-takeitItem.discRate) / 100) - intPrice*1000 }" type="number"/>
			
			<ul class="takeit_info">
				<h2>${takeitItem.itemName} (<span id="takeitFresh" data-takeitexpdate="${takeitItem.freshPercent}"></span>)</h2>
				<li style="list-style: none">
					<span style="color: grey; text-decoration: line-through;">${itemPrice}원</span>
					<span style="color: black; font-weight: 700; text-decoration: line-through;">${discPrice}원</span>
					<span>(할인 ${itemDiscRate})</span>
					<span style="color: red; font-size:20px; font-weight: 700;">${takeitItemPrice}원</span>
					<span style="color: grey;">(잔돈할인 ${takeitDisc}원)</span>
				<hr>
				<span class="it_info"><b>판매단위</b>&emsp;${takeitItem.salesUnit}</span><br>
				<span class="it_info"><b>남은시간</b>&emsp;<span class="takeitTime takeit-detailTime blink" data-takeittime="${takeitItem.takeitDate}"></span></span><br>
				<span class="it_info"><b>원산지</b>&emsp;&emsp;${takeitItem.itemOrigin}</span><br>
				<span class="it_info"><b>포장타입</b>&emsp;${takeitItem.packTypeName}</span><br>
				<span class="it_info"><b>판매자</b>&emsp;&emsp;${takeitItem.shopName}(${takeitItem.sellerName})</span><br>
				<span class="it_info"><b>고객평점</b>&emsp;${takeitItem.itemCustScore}</span><br>
				<span class="it_info"><b>유통기한</b>&emsp;<span id="takeitExpDate" data-takeitexpdate="${takeitItem.expirationDate}"></span> 이내</span><br>
				<span class="it_info"><b>등록일자</b>&emsp;${(takeitItem.itemInputDate).substring(0,10)}</span><br>
				
				<fmt:formatNumber var="takeitRate" type="percent" value="${takeitItem.takeitCurrPrice/takeitItem.takeitPrice}" />
				<fmt:formatNumber var="takeitCurrPrice" type="number" value="${takeitItem.takeitCurrPrice}" />
				<fmt:formatNumber var="takeitPrice" type="number" value="${takeitItem.takeitPrice}" />
				<c:choose>
					<c:when test="${not empty memberId and not empty dto and dto.shopLocCode == takeitItem.shopLocCode}">
						<span class="it_info"><b>구역번호</b>&emsp;<span style="font-weight: 600;">${takeitItem.shopLocCode}-${dto.memberLocNo} (${takeitItem.shopLocName})</span></span><br>
						<span class="it_info"><b>목표금액 달성률</b>&emsp; <span style="color: red; font-weight: 600;">${takeitRate}</span> (${takeitCurrPrice}원 / ${takeitPrice}원)</span><br>
					</c:when>
					<c:when test="${not empty sellerId and not empty dto}">
						<span class="it_info"><b>구역번호</b>&emsp;<span style="font-weight: 600;">${takeitItem.shopLocCode} (${takeitItem.shopLocName})</span></span><br>
						<span class="it_info"><b>목표금액 달성률</b>&emsp; <span style="color: black; font-weight: 600;">판매자는 구매 불가능</span></span><br>
					</c:when>
					<c:when test="${not empty dto}">
						<span class="it_info"><b>구역번호</b>&emsp;<span style="font-weight: 600;">${takeitItem.shopLocCode} (${takeitItem.shopLocName})</span></span><br>
						<span class="it_info"><b>목표금액 달성률</b>&emsp; <span style="color: black; font-weight: 600;">내 지역이 아닙니다</span></span><br>
					</c:when>
					<c:otherwise>
						<span class="it_info"><b>구역번호</b>&emsp;<span style="font-weight: 600;">${takeitItem.shopLocCode} (${takeitItem.shopLocName})</span></span><br>
						<span class="it_info"><b>목표금액 달성률</b>&emsp; <span style="color: black; font-weight: 600;">로그인이 필요합니다</span></span><br>
					</c:otherwise>
				</c:choose>
				
			</ul>
		</div>
	</div>
	<div class="btn-area">
	<input type="button" class="link" id="addCart"  style="display: inline-block;" value="장바구니"/>
	<form action="${CONTEXT_PATH}/order/orderController?action=orderForm"  method="post" style="display: inline-block;" id="buyItemForm">
		<input type="hidden" value="${takeitItem.itemNo}" name="itemNo"> 
		<input type="hidden" value="1" name="itemQty"> 
		<input type="hidden" value="${intPrice*1000}" name="itemPrice" > 
		<input type="hidden" value="${intPrice*1000}" name="totalPrice" > 
		<input type="hidden" value="${intPrice*1000}" name="cartTotalPrice" > 
		<input type="button" class="link" style="display: inline-block;" value="구매" onclick="if(${dto == null }){$('#buyItemForm').submit();return;};if(${sellerId != null}){alert('판매자는 구매할 수 없습니다'); return;} ;if('${takeitItem.shopLocCode}' != '${dto.shopLocCode}'){ alert('내 구역 상품만 구매할 수 있습니다');return;} $('#buyItemForm').submit(); "/>
	</form>
	</div>
	<!-- 장바구니 등록 -->
	<div id="addCart-area">
	<form action="/takeit/cartController?action=addCart&itemNo=${takeitItem.itemNo }" method="post">
	<br><hr>
		<div id="addCart-info">
			<div id="cart-info">
				<b>상품명</b>&emsp;
				<span id="addcart-itemName">${takeitItem.itemName }</span><br>
				<b>판매자</b>&emsp;
				<span id="addcart-itemSeller">${takeitItem.sellerName }</span><br>
				<b>배송비</b>&emsp;
				<span id="addcart-shippingFee">무료(잇거래)</span><br>
				<b>수량</b>&emsp;&emsp;
				<select id="addcart-itemQty" name="cart-itemQty">
				<c:forEach var="i" begin="1" end="9">
					<option>${i}</option>
				</c:forEach>
				</select>
			</div>
		</div>
		<div id="addCart-btn-area">
			<input type="submit" class="link" value="장바구니 추가" onclick="if(${dto == null}){return true;};if('${takeitItem.shopLocCode}' != '${dto.shopLocCode}'){alert('내 구역 상품만 구매할 수 있습니다'); return false}; return true; ">
		</div>
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