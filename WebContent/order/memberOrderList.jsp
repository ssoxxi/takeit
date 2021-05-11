<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 주문목록 조회</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/mypage/myPage.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/order.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
function orderCancelRequest(orderNo) {
	var result = confirm("주문취소 요청을 하시겠습니까?");
	if (!result) {
		return;
	}
	$.ajax({
		url:"/takeit/order/orderController?action=orderCancelRequest",
		type:"post",
		data:{
			"orderNo" : orderNo
		},
		success : function(data) {
			if (data == "success") {
				$("#"+orderNo+"can").attr("disabled", true);
				$("#"+orderNo+"message").html("(취소요청)");
			} else {
				alert("취소요청 실패");
			}
		}
	});
}
$(document).ready(function() {
	
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

<div id="container" class="view-width">
	<div class="side-menu">
	<c:choose>
		<c:when test ="${not empty sellerId}">
	 		<!-- 판매자 마이페이지 메뉴 -->
	 		<jsp:include page="/common/mypage_seller_menu.jsp"/>
		</c:when>
		<c:when test="${not empty memberId}">
			<!-- 일반회원 마이페이지 메뉴 -->
			<jsp:include page="/common/mypage_member_menu.jsp"/>
		</c:when>
		<c:otherwise>
			<jsp:include page="/member/memberLogin.jsp"/>
		</c:otherwise>
	</c:choose>
	</div>	
	<div class="orderList-wrap">
		<h1>주문내역</h1>
		<c:forEach var="order" items="${orderList}">
			<hr>
			<c:choose>
			<c:when test="${order.orderCancel == 'T' and order.orderCancelReq == 'T'}">
				<div style="opacity:0.3;">
			</c:when>
			<c:otherwise>
				<div>
			</c:otherwise>
			</c:choose>
				<div class="order-info">
					<span><b>주문번호 :</b> ${order.orderNo}</span> &emsp;
					<span><b>주문 상태 :</b></span>
					<c:choose>
						<c:when test="${order.orderCancel == 'F' and order.orderCancelReq == 'T'}">
							<span>${order.shipStatus} (취소요청)</span>
						</c:when>
						<c:when test="${order.orderCancel == 'T'}">
							<span>주문취소완료</span>
						</c:when>
						<c:otherwise>
							<span>${order.shipStatus}</span>
						</c:otherwise>
					</c:choose>
					<span id="${order.orderNo}message"></span>
					<c:choose>
						<c:when test="${order.orderCancelReq == 'T' and order.orderCancel == 'F'}">
							<input id="${order.orderNo}can" class="cancle-btn" disabled="disabled" type="button" value="취소요청" onclick="orderCancelRequest('${order.orderNo}')">		
						</c:when>
						<c:when test="${order.orderCancelReq == 'F' and order.orderCancel == 'F'}">
							<input id="${order.orderNo}can" class="cancle-btn" type="button" value="취소요청" onclick="orderCancelRequest('${order.orderNo}')">
						</c:when>
						<c:when test="${order.orderCancel == 'T' }"></c:when>
					</c:choose>
					 
				</div>
				<c:forEach var="orderDetail" items="${order.orderDetails}">
				<div class="order-detail-wrap">
					<div id="orderList-img">
					<img src="/takeit/img/item/${orderDetail.itemImg}" alt="${orderDetail.itemImg}" title="${orderDetail.itemImg}">
					</div>	
					<div class="order-detail">
						<span><b>상품명 :</b>&emsp;${orderDetail.itemName}</span><br>
						<span><b>상품 수량 :</b> &emsp;${orderDetail.itemQty}개</span><br>
						<span><b>결제금액 :</b> &emsp;<fmt:formatNumber type="number" value="${orderDetail.itemPayPrice * orderDetail.itemQty}"/>원</span><br>
						<span><b>수령 방법 :</b> &emsp;${order.receiveMethod}</span><br>
						<span>${order.shopName}(${order.sellerId})</span>
					</div>
					<div class="order-detail-btn">
						<input type="button" value="상품 후기" class="order-btn"  style="margin-bottom: 5px;" onclick="location.href='/takeit/item/reviewController?action=enrollReviewForm&itemNo=${orderDetail.itemNo}'">
						<input type="button" value="상품 문의" class="order-btn" onclick="location.href='/takeit/boardController?action=boardInputForm&itemNo=${orderDetail.itemNo}'">
					</div>
				</div>
				<hr style=" border-top: 1px dashed grey;">
				</c:forEach>
			</div>
		</c:forEach>
		<hr>
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