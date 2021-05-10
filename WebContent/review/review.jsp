<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Take it</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/review.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#itemNo").attr("readonly",true);
	

	if ($("#itemNo").val() == "null") {
		$("#itemNo").attr("readonly", false).val("");
	} else {
		$("#itemNo").attr("readonly", true);
	}
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

<div id="review-input">
	<div id="reviewInput-wrap" class="view-width">
		<div id="reviewInput-title">
		<h3>REVIEW</h3>
		</div>
		<form action="/takeit/item/reviewController?action=enrollReview" method="post" enctype="multipart/form-data">
			<table id="reviewInput-table">
				<tr>
					<th>작성자</th>
					<td><input type="text" name="memberId" class="reviewInput" placeholder="작성자" value="${memberId }" readonly="readonly"></td>
				</tr>
				<%
					String itemNo = request.getParameter("itemNo");
				%>
				<tr>
					<th>상품번호</th>
					<td><input type="text" name="itemNo" id="itemNo" class="reviewInput" placeholder="상품번호" value="<%=itemNo%>" readonly="readonly"></td>
				</tr>
				<tr>
					<th>글제목</th> 
					<td><input type="text" name="reviewTitle" class="reviewInput" placeholder="제목을 입력해주세요"></td>
				</tr>
				<tr>
					<th colspan="2">내용</th>
				</tr>
				<tr>
					<td colspan="2"><textarea name="reviewContents" class="reviewInput" id="reviewInput-contents"
							placeholder="자세한 후기작성은 저희에게 큰 도움이 됩니다.반품/환불문의는 고객센터에 남겨주세요."></textarea>
					</td>
				</tr>
				<tr>
					<th>상품평점</th>
					<td>
					<select onchange="reviewChange(value)" class="reviewInput"	name="reviewScore">
						<option value="">==평점선택==</option>
						<option value="1">1점</option>
						<option value="2">2점</option>
						<option value="3">3점</option>
						<option value="4">4점</option>
						<option value="5">5점</option>
						<option value="6">6점</option>
						<option value="7">7점</option>
						<option value="8">8점</option>
						<option value="9">9점</option>
						<option value="10">10점</option>
					</select>
					</td>
				</tr>
				<tr>
					<th>상품이미지</th>
					<td><input type="file" name="reviewImg" placeholder="상품이미지"></td>
				</tr>
			</table>
			<span id="img-notice">구매한 상품이 아니거나 캡쳐사진은 첨부가 불가능합니다</span>
			<br>
			<div id="reviewInput-btn-area">
				<input class="link" type="submit" value="후기등록"> 
				<input class="link" type="reset" value="취소">
			</div>
		</form>
	</div>
</div>
<!--	</div> -->
<!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
<!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>