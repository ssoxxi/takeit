<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매자 판매목록 조회</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/mypage/myPage.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/order.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
function orderCancel(orderNo) {
	var result = confirm("주문을 취소하시겠습니까?");
	if (!result) {
		return;
	}
	$.ajax({
		url:"/takeit/order/orderController?action=orderCancel",
		type:"post",
		data:{
			"orderNo" : orderNo
		},
		success : function(data) {
			if (data == "success") {
				$("#"+orderNo+"btn").hide();
				$("#"+orderNo+"message").html("주문취소완료");
				$("#"+orderNo+"stat").html("주문취소");
				$("#"+orderNo+"btn2").hide();
			} else {
				alert("에러 실패");
			}
		}
	});
} 

function updateShipStatus(orderNo, shipStatusCode) {
	var popupX = ((document.body.offsetWidth) / 2);
		popupX = popupX - ( 500 / 2);
	var popupY = ((window.screen.height) / 2);
		popupY = popupY - (300 / 2);
	var url = "/takeit/order/orderController?action=updateShipStatusForm&orderNo=" + orderNo +"&shipStatusCode=" + shipStatusCode;
	var name = "updateShipStatus";
	var specs = "width=500px, height=300px, resizable=1, scrollbars=1, status=1, titlebar=1, left="+popupX +", top="+popupY;
	open(url, name, specs);
}
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

<div id="sellerOrder-wrap" class="view-width">
	<div class="side-menu" style="margin-right: 50px;">
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
		<h1 style="margin-bottom: 30px;">판매내역</h1>
		<hr style="width: 800px;">
		<c:forEach var="order" items="${orderList}">
		<div>
			<div class="order-info">
				<span><b>주문번호 :</b> ${order.orderNo}</span>
				<c:if test="${order.orderCancel == 'T'}"> 주문취소완료</c:if>
				 <span id="${order.orderNo}message"></span>
				<c:if test="${order.orderCancelReq == 'T' and order.orderCancel == 'F'}">
					<input type="button" class="small-btn" style="margin-left: 10px;" value="주문취소승인" id="${order.orderNo}btn" onclick="orderCancel('${order.orderNo}')">
				</c:if>
				<br>
				<span><b>주문자 :</b> ${order.memberId}</span><br>
				<span><b>배송상태 :</b>
					<c:if test="${order.orderCancel == 'T'}">주문취소</c:if>
					<c:if test="${order.orderCancel == 'F'}">
						<span id="${order.orderNo}stat">${order.shipStatus}</span>
						<span><input id="${order.orderNo}btn2" type="button" class="small-btn" value="배송상태변경" onclick="updateShipStatus('${order.orderNo}','${order.shipStatusCode}')"/></span>
					</c:if>
				</span>
				<br>
				<span><b>주소 : </b>${order.recipientAddr }</span><br>
				<span><b>요청사항 :</b><span style="text-decoration: underline;"> ${order.shipRequest}</span></span><br>
			</div>
			<c:forEach var="orderDetail" items="${order.orderDetails}">
			<div class="order-detail-wrap">
				<div id="orderList-img">
				<img src="/takeit/img/item/${orderDetail.itemImg}">
				</div>
				<div class="order-detail">
					<span><b>상품명(${orderDetail.itemTakeit == "F" },${(orderDetail.itemPayPrice * orderDetail.itemQty >= 50000) }) :</b>&emsp;&emsp;&emsp;&emsp; ${orderDetail.itemName}</span> <br>
					<span><b>상품개수 :</b>&emsp;&emsp;&emsp; ${orderDetail.itemQty}개</span> <br>
					<c:choose>
						<c:when test='${orderDetail.itemTakeit == "T" }'>
							<span><b>결제금액 :</b> &emsp;<fmt:formatNumber type="number" value="${orderDetail.itemPayPrice * orderDetail.itemQty}"/>원</span><br>
						</c:when>
						<c:when test='${orderDetail.itemTakeit == "F" && (orderDetail.itemPayPrice * orderDetail.itemQty >= 50000) }'>
							<span><b>결제금액 :</b> &emsp;<fmt:formatNumber type="number" value="${orderDetail.itemPayPrice * orderDetail.itemQty}"/>원</span><br>
						</c:when>
						<c:otherwise>
							<span><b>결제금액 :</b> &emsp;<fmt:formatNumber type="number" value="${orderDetail.itemPayPrice * orderDetail.itemQty + 3500}"/>원</span><br>
						</c:otherwise>
					</c:choose>
				</div><br>
			</div>
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