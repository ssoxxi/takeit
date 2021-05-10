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
<script type="text/javascript" src="/takeit/js/member/input.js"></script>	
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
				일반 <input type="radio" id="normalInputFrom" name="normalInputFrom" checked="checked"/>
				판매자 <input type="radio" id="sellerInputForm" name="sellerInputForm" />
			</td>
		</tr>
</table>
<form action="${CONTEXT_PATH}/member/controller?action=memberInput" method="post">
<table>
		<tr>
			<td>아이디<span class="star"> *</span></td>
			<td>
				<input type="text" placeholder="아이디를 입력해주세요." id="memberId"  name="memberId" />
				<input type="button" value="중복확인" id="id_button" name="id_button" onclick="idCheck()"/>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="memberIdResult1" class="inputResult"></span>
				<span id="memberIdResult2" class="inputResult"></span>
			</td>
		</tr>
		<tr>
		</tr>
		
		<tr>
			<td>비밀번호<span class="star"> *</span></td>
			<td><input type="password" placeholder="비밀번호를 입력해주세요." id="memberPw" name="memberPw" /></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="memberPwResult1" class="inputResult"></span>
			</td>
		</tr>
	
		<tr>
			<td>비밀번호 확인<span class="star"> *</span></td>
			<td>
				<input type="password" placeholder="비밀번호를 한번 더 입력해주세요." id="pwChk" name="pwChk" />
				<input type="checkbox" id="pwCheckbox" name="pwCheckbox" onclick="pwCheckbox_onclick()"/>
				<label for="pwCheckbox"></label>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="pwChkResult1" class="inputResult"></span>
			</td>
		</tr>
		
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
				<input type="text" id="mobile" placeholder="휴대폰번호를 입력해주세요." id="mobile" name="mobile"  />
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
				<input type="text" placeholder="이메일을 입력해주세요." id="email" name="email" />
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
			<td><input type="text" placeholder="YYYY / MM / DD" id="brith" name="birth" /></td>
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
