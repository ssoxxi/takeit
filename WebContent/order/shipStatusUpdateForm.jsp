<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>배송상태 변경</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
function updateStatus() {
	$.ajax({
		url:"/takeit/order/orderController?action=updateShipStatus",
		type:"post",
		data:{
			"orderNo" : "${param.orderNo}" , 
			"shipStatusCode" : $("#shipStatusCode").val()
		},
		success : function(data) {
			if (data == "success") {
				opener.document.getElementById("${param.orderNo}stat").innerHTML = $("#shipStatusCode option:selected").text();
				close();
			} else {
				alert("변경 실패");
			}
		}
	});
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

<h2 align="center">배송상태 변경</h2>
<form action="${CONTEXT_PATH}/order/orderController?action=updateShipStatus" method="post">
<div align="center" style="width:fit-content;margin: 0 auto;margin-top:50px;">
	<div style="margin-bottom: 20px;">
		<span ><b>주문번호</b> : ${param.orderNo}</span><br><p/>
	</div>
	<select id="shipStatusCode" name="shipStatusCode" style="width:150px;height:50px; text-align: center; box-sizing: border-box;">
		<c:forEach items="${shippingList}" var="shipping">
			<c:choose>
				<c:when test="${shipping.shipStatusCode == param.shipStatusCode}">
					<option selected="selected" value="${shipping.shipStatusCode}">${shipping.shipStatus}</option>
				</c:when>
				<c:otherwise>
					<option value="${shipping.shipStatusCode}">${shipping.shipStatus}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</select>
	<input onclick="updateStatus()" type="button" value="변경하기" style="height:47px;width:100px;box-sizing: border-box;" />
</div>
</form>
</body>
</html>