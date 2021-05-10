<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지역상점 관리</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/mypage/myPage.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/takeit.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">

function shopLocDelete(){
	if(confirm("[ "+$("#shopLocCode option:checked").text()+" ]"+"을 삭제 하시겠습니까?") == true){
		document.getElementById('shopLocDelete').submit();
		return true;
	}else{
		return false;
	}
}

</script>
</head>
<body>
<c:if test="${empty dto || empty dto.grade != 'A'}">
	<jsp:useBean id="message" class="com.takeit.model.dto.MessageEntity" scope="request" />
	<jsp:setProperty property="type" name="message" value="message"/>
	<jsp:setProperty property="index" name="message" value="2"/>
	<jsp:setProperty property="url" name="message" value="${CONTEXT_PATH}/index"/>
	<jsp:setProperty property="linkTitle" name="message" value="처음으로"/>
	<jsp:forward page="/message.jsp"/>
</c:if>

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

<div id="container"  class="view-width">
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
	<div class="takeitInput-wrap view-width">
		<h1>지역상점 관리</h1>
		<form action="${CONTEXT_PATH}/takeit/takeitController?action=shopLocDelete" method="post" id="shopLocDelete" style="width: fit-content; margin: 0 auto;">
		<table class="takeitInput-tbl">
			<tr>
				<th>상점구역선택</th>
				<td>
				<select id="shopLocCode" name="shopLocCode" id="shopLocCode">
					<c:forEach items="${shopLocList}" var="shopLoc">
						<option value="${shopLoc.shopLocCode}">${shopLoc.shopLocName}</option>
					</c:forEach>
				</select>
				</td>
			</tr>	
		</table>
		<div id="shopLoc-btn-area">
			<input type="button" class="shopLocDelete_btn" value="삭제" onclick="shopLocDelete()"/>
		</div>
		</form>
	</div>
</div>

 <!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
 <!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>