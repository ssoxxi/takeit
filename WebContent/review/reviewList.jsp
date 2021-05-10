<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.takeit.model.dto.Review"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.takeit.model.dto.MessageEntity"%>

<%@ include file="/common/taglib_menu.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>후기조회</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/review.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
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

<!-- contents menu -->
<div id="reviewList-wrap">
	<div class="title-wrap">
		<div id="title">
			<h3 style="margin-bottom: 0;">REVIEW</h3>
		</div>
		<div id="small-btn" style="width: 800px;">
			<a href="/takeit/item/reviewController?action=enrollReviewForm">등록</a>
		</div>
	</div>
	<table id="review_tbl" class="review">
		<!-- 제목행 -->
		<tr>
			<th>후기NO</th>
			<th>작성자</th>
            <th>상품번호</th>
            <th>후기제목</th>
		    <th>조회수</th>
			<th>후기평점</th>
			<th>작성일자</th>
		</tr>
		<%
			ArrayList<Review> reviewList = (ArrayList<Review>) request.getAttribute("reviewList");
			for (Review dto : reviewList) {
		%>
		<tr>
			<td><%=dto.getReviewNo()%></td>
			<td><%=dto.getMemberId()%></td>
	        <td><%=dto.getItemNo()%></td>
	        <td>
		<a id="reviewLink" href="/takeit/item/reviewController?action=reviewDetail&reviewNo=<%= dto.getReviewNo() %>"><%= dto.getReviewTitle()%></a>
		</td>
			<td><%=dto.getReviewViews()%></td>
		    <td><%=dto.getReviewScore()%></td>
		    <td><%=dto.getReviewDate()%></td>
	
		</tr>
		<%
			}
		%>
	
	</table>
	<br>
	<a href="/takeit/index" class="link">홈으로이동</a>
</div>
<!-- floating Banner -->
<jsp:include page="/common/floatingBanner.jsp"></jsp:include>
<!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
<!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>