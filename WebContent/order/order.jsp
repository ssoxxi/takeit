<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품주문</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/order.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="/takeit/js/takeit/order.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#receiveMethod1").click(function() {
			$("#recipientName").val("${order.recipientName}");
			$("#recipientMobile").val("${order.recipientMobile}");
			$("#recipientPostNo").val("${order.recipientPostNo}").attr("disabled", false);
			$("#recipientAddr").val("${order.recipientAddr}").attr("disabled", false);
			$("#recipientAddrDetail").val("${order.recipientAddrDetail}").attr("disabled", false);
			$("#shipRequest").val("${order.shipRequest}").attr("disabled", false);
		});
	});
</script>

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
	<jsp:include page="/common/before_login_menu.jsp"/>
</c:if>
<c:if test="${not empty memberId or not empty sellerId}">
	<!-- 로그인 후 메뉴 -->
	<jsp:include page="/common/after_login_menu.jsp"/>	
</c:if>
<!-- logo.jsp 삽입 -->
<jsp:include page="/common/logo.jsp"></jsp:include>
<!-- 네비게이션 -->
<jsp:include page="/common/navigation.jsp"></jsp:include>

<div id="order-check-wrap" class="view-width">
	<hr style="border-top-width: 3px;">	
	<div id="personal-info">
		<h1>주문자 정보</h1><hr>
		<span><b>이름</b>&emsp;&emsp;${order.recipientName}</span><br>
		<span><b>연락처</b>&emsp;${order.recipientMobile}</span><br>
		<span><b>주소</b>&emsp;&emsp;${order.recipientAddr}, ${order.recipientAddrDetail} (${order.recipientPostNo})</span>
	</div>	
	<hr style="border-top-width: 3px;">

	
	<form action="${CONTEXT_PATH}/order/orderController?action=order" method="post" id="orderForm">
		<div id="recipient-info">	
			<h1>배송지 정보</h1><hr>
			<span><b>배송지 선택 :</b> </span>
			<span id="receiveMethod">
				<input type="radio" name="receiveMethod" id="receiveMethod1" class="recipient-receiveMethod" value="배송" checked="checked">기본 배송지
				<input type="radio" name="receiveMethod" id="receiveMethod2" class="recipient-receiveMethod" value="배송">신규 배송지
				<input type="radio" name="receiveMethod" id="receiveMethod3" class="recipient-receiveMethod" value="직접수령">직접 수령
			</span><br>
			<div id="recipient" style="display: flex;">
			<div class="recipient-info">
				<span><b>수령인</b></span><br>
				<span><b>연락처</b></span><br>
				<span><b style="margin-top: 5px;">배송지주소</b></span><br>
				<br><br><br>
				<span><b>배송요청사항</b></span>
			</div>
			<div class="recipient-info" style="margin-left: 10px;">

				<span><input type="text" id="recipientName" name="recipientName"  class="recipient-receiveMethod" value="${order.recipientName}" ></span><br>
				<span><input type="text" id="recipientMobile" name="recipientMobile"  class="recipient-receiveMethod" value="${order.recipientMobile}" ><span id="mobileResult1" class="orderResult"></span></span><br>
				<span><input type="text" id="recipientPostNo" readonly="readonly"  class="recipient-receiveMethod" name="recipientPostNo" value="${order.recipientPostNo}"></span><input type="button" id="postNoBtn" name="postNoBtn" class="small-btn" onclick="goPopup();" value="주소검색" /> <br>
				<span><input type="text" id="recipientAddr" readonly="readonly"  name="recipientAddr"  class="recipient-receiveMethod" value="${order.recipientAddr}"><span id="postNoResult1" class="orderResult"></span></span><br>
				<span><input type="text" id="recipientAddrDetail" required="required" name="recipientAddrDetail"  class="recipient-receiveMethod" value="${order.recipientAddrDetail}"><span id="addressDetailResult1" class="orderResult"></span></span><br>

				<span><input type="text" id="shipRequest" name="shipRequest"  class="recipient-receiveMethod" value="${order.shipRequest}"></span>
			</div>
			</div>
		</div>
	<hr style="border-top-width: 3px;">	
<c:set var="shipPay" value="0" />	
	<c:set var="totalPrice" value="0" scope="page"/>
		<div class="orderList-wrap">
		<h3>주문상품</h3><hr>
			<br>
			<c:forEach var="orderDetail" items="${order.orderDetails}">
			<div class="orderDetail">
				<div id="orderDetail-img-area">
				<img src="/takeit/img/item/${orderDetail.itemImg}" id="orderDetail-img" >
				</div>
				<div id="orderDetail-info-area">
					<span><b>상품명 :</b> ${orderDetail.itemName} </span><br>
					<span><b>상품개수 :</b> ${orderDetail.itemQty}개 </span><br>
					<span><b>배송비 :</b>
					
					<c:choose>
						<c:when test="${orderDetail.itemTakeit == 'T' }">무료(잇거래)<c:set var="totalPrice" value="${totalPrice + (orderDetail.itemQty*orderDetail.itemPayPrice) }"/></c:when>
						<c:when test="${orderDetail.itemPayPrice * orderDetail.itemQty >= 50000}">무료<c:set var="totalPrice" value="${totalPrice + (orderDetail.itemQty*orderDetail.itemPayPrice) }"/></c:when>
						<c:when test="${orderDetail.itemTakeit == 'F' and orderDetail.itemPayPrice * orderDetail.itemQty < 50000 }">3500원<c:set var="totalPrice" value="${totalPrice + (orderDetail.itemQty*orderDetail.itemPayPrice)}"/>
						<c:set var="shipPay" value="3500" />
						</c:when>
						
					</c:choose></span><br>
					<span><b>상품결제금액 :</b> ${orderDetail.itemPayPrice * orderDetail.itemQty}원</span> <br> 
				</div>
				<div></div>
			</div>
<input type="hidden" value="${orderDetail.itemNo}" name="itemNo"> 
<input type="hidden" value="${orderDetail.itemQty}" name="itemQty"> 
<input type="hidden" value="${orderDetail.itemPayPrice}" name="itemPayPrice" > 
<input type="hidden" value="${orderDetail.itemTakeit}" name="itemTakeit" > 
<input type="hidden" value="${orderDetail.sellerId}" name="sellerId" >        
        
			</c:forEach>
		</div>

		<hr style="border-top-width: 3px;">	
		<div id="order-price-area">
			<c:if test=""></c:if>
			<span id="order-totPrice">
				<b>총 주문금액 :</b> 
				<span style="font-weight: 700; font-size:35px;  color: red;">&#8361;<fmt:formatNumber value="${order.orderPrice}"  pattern="###,###"/></span> 
			</span>
			<input type="submit" class="link" value="결제하기" onclick="return orderCheck()"/>
			</div>
<input type="hidden" value="${order.orderPrice}" name="orderPrice">	
	</form>
</div>

<!-- floating Banner -->
<jsp:include page="/common/floatingBanner.jsp"></jsp:include>
 <!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
 <!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>

</body>
</html>