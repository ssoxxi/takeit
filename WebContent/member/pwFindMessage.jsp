<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>findMessage</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/member/find.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
</head>


<body>
<!-- 상단 메뉴 -->
<c:choose>
	<c:when test="${empty memberId or empty dto}">
		<!-- 로그인 전 메뉴 -->
		<jsp:include page="/common/before_login_menu.jsp"></jsp:include>
	</c:when>
	<c:otherwise>
		<!-- 로그인 후 메뉴 -->
		<jsp:include page="/common/after_login_menu.jsp"></jsp:include>	
	</c:otherwise>
</c:choose>

<!-- logo.jsp 삽입 -->
<jsp:include page="/common/logo.jsp"></jsp:include>

<!-- 네비게이션 -->
<jsp:include page="/common/navigation.jsp"></jsp:include>


<!-- contents -->
<div class="view-width">
	<div id="title">
		<h3>임시비밀번호 발급</h3>
	</div>
	<div id="find-table">	
		<table id="table_logds" class="find-table">
			<tr class="find-table">
				<td class="find-table">회원님의 임시비밀번호 입니다.</td>
			</tr>
			<tr class="find-table">
				<td class="find-table" style="text-align: center; font-weight: 700;">${pwInfo}</td>
			</tr>	
		</table>
	</div>
</div>
<hr/>
<!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
<!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>