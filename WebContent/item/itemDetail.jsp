<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<%@page import="com.takeit.model.dto.Item"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세조회</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/item.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function (){
	$("#addCart-area").hide();
	
	$("#addCart").on("click",function(){
		if(${sellerId != null}) {
			alert('판매자는 구매할 수 없습니다'); 
			return;
		}
		$("#addCart-area").slideToggle(300);
	});
});



</script>
<script type="text/javascript">
function gradeCheck(){
	
/* 	
	if(${sellerId != null} && {memberId != null}){
		alert("로그인 후 가능한 서비스 입니다.");
		return;
	};
	alert("창ㅊ아");
	
}

if(${sellerId != null}){
	alert('판매자는 구매할 수 없습니다'); return;};
	$('#buyItemForm').submit();"
 */

/* if(dto.grade == "G" || dto.grade== "A" || dto.grade =="S"){
	location.href="/takeit/boardController?action=boardInputForm&itemNo="+$(item.itemNo);
	return true;
} */
	
if(${memberId == null}){
	alert('로그인 후 이용이 가능합니다.'); 
	location.href="/takeit/member/memberLogin.jsp";
	return;
} else if(${memberId != null}){
	location.href='/takeit/boardController?action=boardInputForm&itemNo=${item.itemNo}';
return;
	};

}
</script>
</head>
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
<div class="view-width">
	<div id="item_detail_wrap">
		<div class="item_detail">
			<div class="item_img_wrap">
				<img src="/takeit/img/item/${item.itemImg}">
			</div>
			<div id="detail_info">
				<span id="itemName">${item.itemName}</span>
				<fmt:formatNumber var="itemPrice" value="${item.itemPrice}" type="number"/>
				<fmt:formatNumber var="discPrice" value="${item.itemPrice * (100-item.discRate) / 100}" type="number"/>
				<fmt:parseNumber  var="realPrice" value="${(item.itemPrice * (100-item.discRate) / 100)}" integerOnly="true"/>
				<fmt:formatNumber var="itemDiscRate" value="${item.discRate / 100}" type="percent"/>
				<span style="color: red; font-weight: 700; font-size: 20px;">&#8361;<fmt:formatNumber value="${realPrice}" pattern="###,###"/></span> 
				<span id="item_disc_Rate"> (${itemDiscRate}할인) </span>&emsp;&emsp;&emsp; 
				<br>
				<span id="realPrice"><b>소비자 권장소매가:</b><fmt:formatNumber value="${item.itemPrice}" pattern="###,###"/>원</span> 
				<hr class="line1">
				<span class="it_info"><b>카테고리</b>&emsp;${item.itemCategoryName}</span><br>
				<span class="it_info"><b>상품번호</b>&emsp;${item.itemNo}</span><br>
				<span class="it_info"><b>판매단위</b>&emsp;${item.salesUnit}</span><br>
				<span class="it_info"><b>재고량</b>&emsp;&emsp;${item.itemStock}</span><br>
				<span class="it_info"><b>원산지</b>&emsp;&emsp;${item.itemOrigin}</span><br>
				<span class="it_info"><b>포장타입</b>&emsp;${item.packTypeName}</span><br>
		        <span class="it_info"><b>고객평점</b>&emsp;${item.itemCustScore}</span><br>
				<span class="it_info"><b>유통기한</b>&emsp;${item.expirationDate}</span><br>
				<span class="it_info"><b>등록일자</b>&emsp;${item.itemInputDate}</span><br>
				<span class="it_info"><b>판매상점</b>&emsp;${item.shopName}</span><br>
				<span class="it_info"><b>판매자</b>&emsp;&emsp;${item.sellerName}</span><br>
				<span class="it_info"><b>안내사항</b>&emsp;${item.notice}</span><br>					
			</div>
		</div>
	</div>
	<div class="btn-area">
		<input type="button" class="link" id="itemAsk" value="상품문의" onclick=" return gradeCheck()"/>
		<input type="button" class="link" id="addCart"  style="display: inline-block;" value="장바구니"/>
		<form action="${CONTEXT_PATH}/order/orderController?action=orderForm"  method="post" style="display: inline-block;" id="buyItemForm">
		<input type="hidden" value="${item.itemNo}" name="itemNo"> 
		<input type="hidden" value="1" name="itemQty"> 
		<input type="hidden" value="${realPrice}" name="itemPrice" > 
		<input type="hidden" value="${realPrice}" name="totalPrice" > 
		<input type="hidden" value="${realPrice}" name="cartTotalPrice" > 
		<input type="button" class="link" style="display: inline-block;" value="구매" onclick="if(${sellerId != null}){alert('판매자는 구매할 수 없습니다'); return;};$('#buyItemForm').submit();"/>
	</form>
	</div>
<!-- 장바구니 등록 -->
<div id="addCart-area">
<form action="/takeit/cartController?action=addCart&itemNo=${item.itemNo}" method="post">
<br><hr>
	<div id="addCart-info">
		<div id="cart-info">
			<b>상품명</b>&emsp;
			<span id="addcart-itemName">${item.itemName}</span><br>
			<b>판매자</b>&emsp;
			<span id="addcart-itemSeller">${item.sellerName}</span><br>
			<b>배송비</b>&emsp;
			<span id="addcart-shippingFee">3,500원</span><br>
			<b>수량</b>&emsp;&emsp;
			<select id="addcart-itemQty" name="cart-itemQty">
			<%
				for(int i=1; i<10; i++){
			%>
				<option value="<%= i %>"><%= i %></option>
			<%
				}
			%>
			</select>
		</div>
	</div>
	<div id="addCart-btn-area">
		<input type="submit" class="link" value="장바구니 추가"/>
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