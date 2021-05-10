<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경 </title>
<link type="text/css" rel="stylesheet" href="/takeit/css/mypage/memberUpdate.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<style type="text/css">
	
</style>
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
<c:choose>
	<c:when test ="${dto.grade == 'S' }">
		<div id="memberPwUpdate-wrap">
			<h1 style="width:fit-content; margin: 0 auto;">판매자 비밀번호 변경</h1>
			<br>
			<form action = "${CONTEXT_PATH}/member/mypageController?action=setSellerPw" method="post">
				<table id="myInfo_table">
					<tr>
						<th>아이디</th>
						<td>
							<input type="text" id="sellerId" name="sellerId">
						</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td>
							<input type="password" id="sellerPw" name="sellerPw">
						</td>		
					</tr>
					<tr>
						<th>새비밀번호</th>
						<td>
							<input type="password" id="sellerPw2" name="sellerPw2">
						</td>		
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" class="updateBtn" value="비밀번호 변경">
						</td>
					</tr>
				</table>
			</form>
		</div>
	</c:when>
	<c:otherwise>
		<div id="memberPwUpdate-wrap">
			<h1 style="width:fit-content; margin: 0 auto;">일반회원 비밀번호 변경</h1>
			<br>
			<form action="${CONTEXT_PATH}/member/mypageController?action=setMemberPw" method="post">
				<table id="myInfo_table">
					<tr>
						<th>아이디</th>
						<td>
							<input type="text" id="memberId" name="memberId">
						</td>
					</tr>
					
					<tr>
						<th>비밀번호</th>
						<td>
							<input type="password" id="memberPw" name="memberPw">
						</td>		
					</tr>
					<tr>
						<th>새비밀번호</th>
						<td>
							<input type="password" id="memberPw2" name="memberPw2">
						</td>		
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" class="updateBtn" value="비밀번호 변경">
						</td>
					</tr>
				</table>
			</form>
		</div>
	</c:otherwise>
</c:choose>
</div>
<!-- floating Banner -->
<jsp:include page="/common/floatingBanner.jsp"></jsp:include>		
 <!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
 <!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>