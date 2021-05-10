<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디찾기</title>
<link type="text/css" rel="stylesheet" href="${CONTEXT_PATH}/css/link.css">
<link type="text/css" rel="stylesheet" href="${CONTEXT_PATH}/css/member/find.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="${CONTEXT_PATH}/js/member/find.js"></script>
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
<jsp:include page="/common/navigation.jsp"></jsp:include>

<!-- 내용 -->
<div id="contents_box" align="center">
<div class="view-width">
	<h1 align="center">아이디 찾기</h1>
	<form action="${CONTEXT_PATH}/seller/controller?action=sellerFindId" method="post">
	<table id="find-account-tbl">
		<tr>
			<th>이름</th>
			<td><input type="text" placeholder="고객님의 이름을 입력해주세요" id="name" name="name" /></td>
		</tr>
		<tr>
			<th>이메일</th>
			<td>
				<input type="text" placeholder="가입 시 등록하신 이메일 주소를 입력해주세요" id="email" name="email" />
				<input type="button" value="인증번호" id="email_button" name="email_button" onclick="mobilePopup();"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="emailNumResult1" class="inputResult"></span>
			</td>
		</tr>
		<tr>
			<th>인증번호</th>
			<td>
				<input type="text" placeholder="인증번호 입력" id="mobileNum" name="mobileNum" readonly="readonly" />
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align: center;"><input type="submit" value="찾기" id="check_submit" onclick="return inputCheck()"/></td>
		</tr>
	</table>
	</form>
	</div>
</div>
<!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
<!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html> 