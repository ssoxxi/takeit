<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반회원 목록</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/mypage/myPage.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
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
<br>
<div id="list-container" class="list-view-width">
<div id="memberList">
	<h1 style="width:fit-content; margin: 0 auto;">일반회원 목록</h1>
	<br>
	<table id="memberList_table">
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>이메일</th>
			<th>가입일자</th>
			<th>우편번호</th>
			<th>주소</th>
			<th>생일</th>
			<th>구역번호</th>
			<th>등급</th>
			<th>탈퇴</th>
		</tr>
		<c:forEach var="dto" items="${ memberList}">
		<tr>
		
			<td>
				${ dto.getMemberId()}
			</td>
			<td>
				${ dto.getName()}
			</td>
			<td>
				${ dto.getEmail()}
			</td>
			<td>
				${ dto.getEntryDate()}
			</td>
			<td>
				${ dto.getPostNo()}
			</td>
			<td>
				${ dto.getAddress()} ${dto.getAddressDetail()}
			</td>
			<td>
				${ dto.getBirth()}
			</td>
			<td>
				${ dto.getMemberLocNo()}
			</td>
			<td>
				${ dto.getGrade()}
			</td>
			<td>
				<input type="button" value="탈퇴" id="deleteBtn" onclick="location.href='${CONTEXT_PATH}/member/mypageController?action=AceRemoveMember&memberId=${ dto.getMemberId()}&memberPw=${ dto.getMemberPw()}'">
			</td>
		</tr>
		</c:forEach>
	</table>

<a href="${CONTEXT_PATH}/member/myPage.jsp" id="mypage_Btn">마이페이지로 이동</a>
</div>
</div>
 <!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>