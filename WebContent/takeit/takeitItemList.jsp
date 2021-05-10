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
			
			takeitTimeElement.innerHTML = "남은시간 : " +d1 + "일 "+ h1+"시간 "+m1+"분 "+s1 + "초"
			
			if (d1 < 3) {
				console.log("d1="+d1);
				$($(".takeitTime").get(i)).css("color", "red");
			}
		}
	}
	$(document).ready(function (){
		getTakeitTime();
		setInterval(getTakeitTime, 1000);
		
		$("#selectScope").change(function() {
			if ($("#selectScope").val() == "my") {
				location.href="/takeit/takeit/takeitController?action=takeitItemList&scope=my";
			} else {
				location.href="/takeit/takeit/takeitController?action=takeitItemList&scope=all";
			}
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
<div style="width: 950px; margin: 0 auto;">
	<div id="title">
		<h3 >잇거래</h3>
	</div>
	<div>
		<select id="selectScope" name="scope">
		<c:choose >
			<c:when test="${not empty applicationScope.takeitScope and applicationScope.takeitScope == 'all'}">
				<option value="all" selected="selected">전체</option>
				<option value="my">내 지역</option>
				
			</c:when>
			<c:when test="${not empty applicationScope.takeitScope and applicationScope.takeitScope == 'my'}">
				<option value="all">전체</option>
				<option value="my" selected="selected">내 지역</option>
			</c:when>
		</c:choose>
		</select>	
	</div>
</div>

 <div class="item_wrap" style="display: flex;">
	<div class="takeit_item_wrap">
	<c:forEach items="${takeitItemList}" var="takeitItem" begin="${startRow }" end="${endRow}" > 
		<ul class="takeit_item" style="display: inline-block;">

			<li style="width: 250px;">
				<span class="takeitTime takeit-listTime blink" data-takeittime="${takeitItem.takeitDate}"></span><br>
				<a href="/takeit/takeit/takeitController?action=takeitItemDetail&itemNo=${takeitItem.itemNo}&shopLocCode=${takeitItem.shopLocCode}">
					<img id="takeitImg" alt="${takeitItem.itemImg}" src="/takeit/img/item/${takeitItem.itemImg}">
				</a>
				<span class="item-fresh">신선도${100-(takeitItem.discRate)}%</span>
			</li>
			<fmt:formatNumber var="itemPrice" value="${takeitItem.itemPrice}" type="number"/>
			<fmt:formatNumber var="discPrice" value="${takeitItem.itemPrice * (100-takeitItem.discRate) / 100}" type="number"/>
			<fmt:parseNumber  var="intPrice" value="${(takeitItem.itemPrice * (100-takeitItem.discRate) / 100)/1000}" integerOnly="true"/>
			<fmt:formatNumber var="takeitItemPrice" value="${intPrice*1000}" type="number"/>
			<fmt:formatNumber var="itemDiscRate" value="${takeitItem.discRate / 100}" type="percent"/>
			<fmt:formatNumber var="takeitDisc" value="${(takeitItem.itemPrice * (100-takeitItem.discRate) / 100) - intPrice*1000 }" type="number"/>
			<li id="itemTitle">${takeitItem.itemName}</li>
			<li id="discRate">(할인 ${itemDiscRate}+${takeitDisc}원)</li>
			<li id="salePrice">${takeitItemPrice}원</li>
			<li id="price">${takeitItem.itemPrice}원</li>
		</ul>
	</c:forEach>
	</div>
</div>

<!-- 페이징 -->
<div class="view-width">
	<div id="paging">
		<c:choose>
			<c:when test="${whereGroup > 1 }">
				<span><a href="/takeit/takeit/takeitController?action=takeitItemList&goGroup=1">[처음]</a></span>
				<span><a href="/takeit/takeit/takeitController?action=takeitItemList&goGroup=${priorGroup}">[이전]</a></span>
			</c:when>
			<c:otherwise>
					<span>[처음]</span>
					<span>[이전]</span>
				</c:otherwise>
		</c:choose>
		<c:forEach var="i" begin="${startPageNo}" end="${endPageNo}" step="1">
			<a href="/takeit/takeit/takeitController?action=takeitItemList&go=${i}">${i}</a>
		</c:forEach>
		<c:choose>
			<c:when test="${whereGroup < totalGroup}">
				<span><a href="/takeit/takeit/takeitController?action=takeitItemList&goGroup=${nextGroup}">[다음]</a></span>
				<span><a href="/takeit/takeit/takeitController?action=takeitItemList&goGroup=${totalGroup}">[마지막]</a></span>
			</c:when>
			<c:otherwise>
					<span>[다음]</span>
					<span>[마지막]</span>
				</c:otherwise>
		</c:choose>
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
