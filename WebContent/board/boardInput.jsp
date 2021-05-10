<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.takeit.model.dto.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>takeit::공지사항 작성 페이지</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/board.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<style type="text/css">
	table, tr, th,td {
		border: solid 1px #8D959C;
		margin: 0 auto;
		border-collapse: collapse;
		padding: 5px;
		width: 1050px;
		margin-top: 30px;
		margin-bottom: 20px;
	}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		
		if ($("#itemNo").val() == "null") {
			$("#itemNo").attr("readonly", false).val("");
		} else {
			$("#itemNo").attr("readonly", true);
		}
		
		
		$("#boardCategory").on('change',function(){
			var itemNo = this.value;
			if(itemNo === '3'){
				$("#itemNo").attr("readonly",false);
				$("#itemNo").focus();
			} else {
				$("#itemNo").attr("readonly",true);
			}
		});
		
		$("#cancle").on("click", function(){
			history.back();
		});
		
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
<br>
<h1 style="width:fit-content; margin: 0 auto;">게시글 입력</h1>
<br>
<form action="/takeit/boardController?action=boardInput" method="post">
<table>
	<tr>
		<th>글제목</th>
		<td><input type="text" name="boardTitle" class="boardInput" placeholder="제목을 입력하세요.."> </td>
		<th>카테고리</th>
		<td>
			<select id="boardCategory" name="boardCategory" class="boardInput">
		<%
			ArrayList<Category> categoryList =(ArrayList<Category>)request.getAttribute("category");
			for(Category cate : categoryList){
				if(cate.getCategoryNo().equals("1")&&!(session.getAttribute("memberId").equals("admin"))||
						cate.getCategoryNo().equals("2")&&!(session.getAttribute("memberId").equals("admin"))){
					continue;
				}
		%>

			<option value="<%= cate.getCategoryNo() %>"><%= cate.getCategoryName() %></option>

		<% } %>
		</select>
	</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><input type="text" name="boardWriter" id="boardWriter" class="boardInput" value="${memberId}" readonly="readonly"></td>
		<%
			String itemNo = request.getParameter("itemNo");
		%>
		<th >상품번호</th>
		<td><input type="text" id="itemNo" class="boardInput" name="itemNo" placeholder="상품번호(예:FR000709)" value="<%=itemNo%>" readonly="readonly"></td>
	</tr>
	<tr>
		<th colspan="4">내용</th>
	</tr>
	<tr>
		<td colspan="4">
			<textarea id="boardInput" name="boardContents" placeholder="내용을 입력하세요.."></textarea>
		</td>
	</tr>
</table>
<input type="submit" value="등록" class="link">
</form>
<div id="cancle" class="link">취소</div>
<!-- floating Banner -->
<jsp:include page="/common/floatingBanner.jsp"></jsp:include>
<!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
</body>
</html>