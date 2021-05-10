<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="com.takeit.model.dto.Review"%>
<%@page import="com.takeit.model.dto.Item"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>takeit::후기상세조회</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/review.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#cancle").on("click", function() {
			history.back();
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
<div id="reviews">
	<div id="title">
		<h3>${review.reviewTitle}</h3>
	</div>

	<table class="review-table" >
		<tr style="line-height: 2;">
			<th>후기번호</th>
			<td>${review.reviewNo}</td>
			<th>상품번호</th>
			<td>${review.itemNo}</td>
		</tr>
		<tr style="line-height: 2;">
			<th>조회수</th>
			<td>${review.reviewViews}</td>
			<th>상품평점</th>
			<td>${review.reviewScore}점</td>
		</tr>
		<tr style="line-height: 2;">
			<th>작성자</th>
			<td>${review.memberId}</td>
			<th>작성일자</th>
			<td>${review.reviewDate}</td>
		</tr>
		<tr style="line-height: 2;">
			<th>후기제목</th>
			<td colspan="3">${review.reviewTitle}</td>
		</tr>
		<tr>
			<th colspan="3">후기이미지</th>
			<th colspan="2">내용</th>
		</tr>

		<tr>
			<td colspan="3" style="text-align: center;"><img id="review-img" alt='${review.reviewImg}'src="/takeit/img/review/${review.reviewImg}"></td>
			<td colspan="2">${review.reviewContents}</td>
		</tr>
	</table>
	<div>
		<div class="btn-area" align='center'>
			<input type="button" class="link" style="display: inline-block;"
				value="목록" onclick="location.href='/takeit/item/reviewController?action=reviewList'"/> 
		   <form action="/takeit/item/itemController?action=itemDetail&itemNo=${review.itemNo}" method="post"  style="display: inline-block;">
	         <input type="submit" class="link" value="상품보러가기"/>
			</form>
	   </div>
		
				

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