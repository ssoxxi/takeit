<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %> 
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.takeit.model.dto.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>takeit::공지사항</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/board.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#cancle").on("click", function(){
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
<div id="notice">
<%
	Board dto = (Board)request.getAttribute("board");
	String memberId = (String)session.getAttribute("memberId");
%>
<div id="title">
	<h3><%= dto.getBoardCategoryName() %> </h3>
</div>
<%
	if(memberId != null && memberId.equals(dto.getBoardWriter())){ 
%>
<form action="/takeit/boardController?action=boardUpdateForm&boardNo=<%= dto.getBoardNo() %>&boardCategory=<%= dto.getBoardCategory() %>" method="post">
	<input type="submit" value="수정" class="btn">
</form>
<form action="/takeit/boardController?action=boardDelete&boardNo=<%= dto.getBoardNo()%>&boardCategory=<%= dto.getBoardCategory() %>" method="post">
	<input type="submit" value="삭제" class="btn">
</form>
<%
	}
%>
<table class="notice-table">
	<tr>
		<th>글번호</th>
		<td><%= dto.getBoardNo() %></td>
		<th>조회수</th>
		<td><%= dto.getBoardViews()%></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><%= dto.getBoardWriter() %></td>
		<th>작성일자</th>
		<td><%= dto.getBoardDate() %></td>
	</tr>
	<tr>
		<th>글제목</th>
		<td colspan="3"><%= dto.getBoardTitle() %> </td>
	</tr>
	<tr>
		<th colspan="4">내용</th>
	</tr>
	<tr>
		<td colspan="4">
			<%= dto.getBoardContents() %>
		</td>
	</tr>
</table>
<div>
<div id="cancle" class="link">돌아가기</div>
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