<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상점구역 등록</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/mypage/myPage.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/takeit.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
var goPopup = function() {
	 var pop = window.open("${CONTEXT_PATH}/member/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes");
 } 
var jusoCallBack = function(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo){
	 document.getElementById("postNo").value = zipNo; 
	 document.getElementById("address").value = roadAddrPart1; 
	 document.getElementById("addressDetail").value = addrDetail; 
	 if(addressDetail.length>30){ 
		alert('상세주소를 30자 이내로 입력하세요.'); 
		return; 
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
	<div class="shopLocInput-wrap view-width">
		<h1>상점구역 등록</h1>
		<form action="${CONTEXT_PATH}/takeit/takeitController?action=shopLocInput" method="post" style="width: fit-content; margin: 0 auto;">
		<table class="shopLocInput-tbl">
			<tr>
				<th>우편번호</th>
				<td>
					<input type="text" placeholder="우편번호 선택" id="postNo" class="shopLoc-input" name="postNo" readonly="readonly"/>
					<input type="button" id="postNoBtn" name="postNoBtn" class="small-btn" onclick="goPopup();" value="주소검색"/>
				</td>
			</tr>
			<tr>
				<th>도로명주소</th>
				<td>
					<input type="text" placeholder="도로명주소" id="address" class="shopLoc-input" name="address" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<th>상세주소</th>
				<td>
					<input type="text" required="required" placeholder="상세주소" id="addressDetail" class="shopLoc-input" name="addressDetail"/>
				</td>
			</tr>
			<tr>
				<th>상점구역코드</th>
				<td>
					<input type="text" required="required" placeholder="상점구역코드" id="shopLocCode" class="shopLoc-input" name="shopLocCode"/>
				</td>
			</tr>
			<tr>
				<th>상점구역이름</th>
				<td>
					<input type="text" required="required" placeholder="상점구역이름" id="shopLocName" class="shopLoc-input" name="shopLocName"/>
				</td>
			</tr>
		</table>
		<div id="shopLoc-btn-area">
			<input type="submit" class="shopLoc-btn" value="등록"/>
			<input type="reset" class="shopLoc-btn" value="초기화"/>
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