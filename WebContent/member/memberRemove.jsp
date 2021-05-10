<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴 </title>
<link type="text/css" rel="stylesheet" href="/takeit/css/mypage/memberUpdate.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
function removeCheck(){
	
	if(confirm("탈퇴를 하시겠습니까?") == true){
		var memberPwElement = document.getElementById("memberPw");
		var memberPwElement2 = document.getElementById("memberPw2");
		
		var memberPw = memberPwElement.value;
		var memberPw2= memberPwElement2.value;
		
		var memberPwMessageElement = document.getElementById("memberPwMessage");
		
		if(memberPw != memberPw2){
			memberPwMessageElement.innerHTML = "비밀번호가 일치하지 않습니다.";
			memberPwElement2.value="";
			memberPwElement2.focus();
			return false;
		}
		
		document.getElementById('memberRemove').submit();
		return true;
	}else{
		return false;
	}
	
}

function removeSellerCheck(){
	
	if(confirm("탈퇴를 하시겠습니까?") == true){
		var sellerElement = document.getElementById("sellerPw");
		var sellerElement2 = document.getElementById("sellerPw2");
		
		var sellerPw = sellerElement.value;
		var sellerPw2= sellerElement2.value;
		
		var memberPwMessageElement = document.getElementById("memberPwMessage");
		
		if(sellerPw != sellerPw2){
			memberPwMessageElement.innerHTML = "비밀번호가 일치하지 않습니다.";
			sellerElement2.value="";
			sellerElement2.focus();
			return false;
		}
		
		document.getElementById('memberRemove').submit();
		return true;
	}else{
		return false;
	}
	
}
</script>
<style type="text/css">

.error {
	color: red;
	font-weigth: bold;
	font-size: 7px;
}
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
	<div id="memberRemove-wrap">
		<h1 style="width:fit-content; margin: 0 auto;">판매자회원 탈퇴</h1>
		<br>
		<form action = "${CONTEXT_PATH}/member/mypageController?action=removeSeller" method="post" id="memberRemove">
			<table id="myInfo_table">
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" id="sellerId" name="sellerId" value="${sellerId }" readonly="readonly">
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="password" id="sellerPw" name="sellerPw">
					</td>		
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td>
						<input type="password" id="sellerPw2" name="sellerPw2">
						<div id="memberPwMessage" class="error"></div>
					</td>		
				</tr>
				<tr>
					<td colspan="2">
					<input type="button" class="removeBtn" value="탈퇴하기" onclick="return removeSellerCheck()">
					</td>
				</tr>
			</table>
		</form>
	</div>
	</c:when>
	<c:otherwise>
	<!-- 일반회원 마이페이지 메뉴 -->
	<div id="memberRemove-wrap">
		<h1 style="width:fit-content; margin: 0 auto;">일반회원 탈퇴</h1>
		<br>
		<form action="${CONTEXT_PATH}/member/mypageController?action=removeMember" method="post" id="memberRemove">
			<table id="myInfo_table">
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" id="memberId" name="memberId" value="${memberId }" readonly="readonly">
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="password" id="memberPw" name="memberPw">
					</td>		
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td>
						<input type="password" id="memberPw2" name="memberPw2">
						<div id="memberPwMessage" class="error"></div>
					</td>		
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" class="removeBtn" value="탈퇴하기" onclick="return removeCheck()">
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