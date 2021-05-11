<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>	
<link type="text/css" rel="stylesheet" href="${CONTEXT_PATH}/css/link.css">
<link type="text/css" rel="stylesheet" href="${CONTEXT_PATH}/css/member/input.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="/takeit/js/member/kakaoInput.js"></script>
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
<!-- 내용 -->
<div id="contents_box" align="center">
<table>
		<tr>
			<td align="center">
				일반회원 <input type="radio" id="normalInputFrom" name="normalInputFrom" checked="checked"/>
			</td>
		</tr>
</table>
<form action="${CONTEXT_PATH}/member/controller?action=memberInput" method="post">
<input type="hidden" value="${kakaoId}" name="memberId">
<input type="hidden" value="ka${kakaoPass}" name="memberPw">
<table>
		<tr>
			<td>이름<span class="star"> *</span></td>
			<td><input type="text" placeholder="이름을 입력해주세요." id="name" name="name"/></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="nameResult1" class="inputResult"></span>
			</td>
		</tr>		

		<tr>
			<td>휴대폰<span class="star"> *</span></td>
			<td>
				<input type="text" id="mobile" placeholder="휴대폰번호를 입력해주세요." maxlength="13" id="mobile" name="mobile"  />
				<input type="button" value="인증번호" id="mobile_button" name="mobile_button" onclick="mobilePopup();"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="mobileResult1" class="inputResult"></span>
			</td>
		</tr>
			
		<tr>
			<td>인증번호<span class="star"> *</span></td>
			<td>
				<input type="text" placeholder="인증번호를 선택해주세요." id="mobileNum" name="mobileNum"  readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="mobileNumResult1" class="inputResult"></span>
			</td>
		</tr>
		
		<tr>
			<td>이메일<span class="star"> *</span></td>
			<td>
				<input type="text" placeholder="이메일을 입력해주세요." id="email" name="email" value="${kakaoEmail}" />
				<input type="button" value="중복확인" id="email_button" name="email_button" onclick="emailCheck()"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="emailNumResult1" class="inputResult"></span>
				<span id="emailNumResult2" class="inputResult"></span>
			</td>
		</tr>
		
		<tr>
			<td>우편번호<span class="star"> *</span></td>
			<td>
				<input type="text" placeholder="우편번호를 선택해주세요." id="postNo" name="postNo" readonly="readonly"/>
				<input type="button" id="postNoBtn" name="zipNoBtn" onclick="goPopup();" value="우편번호"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="postNoResult1" class="inputResult"></span>
			</td>
		</tr>
		
		<tr>
			<td>도로명주소<span class="star"> *</span></td>
			<td>
				<input type="text" placeholder="우편번호를 선택해주세요." id="address" name="address" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="addressResult1" class="inputResult"></span>
			</td>
		</tr>
		
		<tr>
			<td>상세주소<span class="star"> *</span></td>
			<td>
				<input type="text" placeholder="상세주소를 입력해주세요." id="addressDetail" name="addressDetail" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="addressDetail1" class="inputResult"></span>
			</td>
		</tr>		
		
		<tr>
			<td>생년월일</td>
			<td><input type="text" placeholder="YYYY / MM / DD" id="brith" name="birth" value=""/></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="addressResult1" class="inputResult"></span>
			</td>
		</tr>	
		
		<tr>
			<td colspan="3" align="center"><input type="submit" value="가입하기" id="normalSubmit" name="normalSubmit" onclick="return inputCheck()"/></td>
		</tr>
	</table>
</form>
</div>
<!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
<!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html> 
