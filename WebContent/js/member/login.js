/**
 * 로그인 : 일반, 판매
 * @author 임우진
 * 
 */
$(document).ready(function() {
	
	/* 아이디 포커스 */
	$("#memberId").focus();
	$("#memberId").on('focus', function() {
		$("#memberId").css({
			'background' : "#E8F0FE"
		});
	});
	/* 아이디 포커스 아웃*/
	$("#memberId").on('focusout', function() {
		$("#memberId").css({
			'background' : "white"
		});
	});
	/* 비밀번호 포커스 */
	$("#memberPw").on('focus', function() {
		$("#memberPw").css({
			'background' : "#E8F0FE"
		});
	});
	/* 비밀번호 포커스 아웃*/
	$("#memberPw").on('focusout', function() {
		$("#memberPw").css({
			'background' : "white"
		});
	});

	/* 판매자 아이디 포커스 */
	$("#sellerId").focus();
	$("#sellerId").on('focus', function() {
		$("#sellerId").css({
			'background' : "#E8F0FE"
		});
	});
	/* 판매자 아이디 포커스 아웃*/
	$("#sellerId").on('focusout', function() {
		$("#sellerId").css({
			'background' : "white"
		});
	});
	
	/* 판매자 비밀번호 포커스 */
	$("#sellerPw").on('focus', function() {
		$("#sellerPw").css({
			'background' : "#E8F0FE"
		});
	});
	/* 판매자 비밀번호 포커스 아웃*/
	$("#sellerPw").on('focusout', function() {
		$("#sellerPw").css({
			'background' : "white"
		});
	});

	/* 일반 회원가입 이동 이벤트 */
	$("#inputNormal").click(function() {
		 location.href = "/takeit/member/memberInput.jsp"
	});
	/* 판매자 회원가입 이동 이벤트 */
	$("#inputSeller").click(function() {
		location.href = "/takeit/seller/controller?action=sellerInputForm"
	});
	
	/* 일반 로그인 이벤트 */
	$("#normalLoginFrom").click(function() {
		 location.href = "/takeit/member/memberLogin.jsp"
	});
	/* 판매자 로그인 이벤트 */
	$("#sellerLoginFrom").click(function() {
		location.href = "/takeit/seller/sellerLogin.jsp"
	});
});

/* 비밀번호 표시 */
function pwCheckbox_onclick() {
	
	var pwCheckboxElement = document.getElementById("pwCheckbox")
	var memberPwElement = document.getElementById("memberPw")
	
	if(pwCheckboxElement.checked) {
		memberPwElement.type = "text"
	}else {
		memberPwElement.type = "password"
	}
}

/* 판매자 비밀번호 표시*/
function pwCheckbox_onclick_seller() {
	
	var pwCheckboxElement = document.getElementById("pwCheckbox")
	var sellerPwElement = document.getElementById("sellerPw")
	
	if(pwCheckboxElement.checked) {
		sellerPwElement.type = "text"
	}else {
		sellerPwElement.type = "password"
	}
}