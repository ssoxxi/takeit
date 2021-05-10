/**
 * 아이디, 비밀번호찾기 : 일반, 판매자
 * @author 임우진
 * 
 */
$(document).ready(function() {
	
	/* 인증번호 비활성화 */
	$("#email_button").prop('disabled', true);
	/* 이메일 키다운 */
	$("#email").on('keyup', function() {
		var email = $("#email").val();
		var email_Valid = email_data(email)
		if(email_Valid) {
			$('#emailNumResult1').show().html("o 예:takeit@take.com<br>");
			$('#emailNumResult1').css('color','#08A600')
			$("#email_button").prop('disabled', false);
		}else {
			$('#emailNumResult1').show().html("x 예:takeit@take.com<br>");
			$('#emailNumResult1').css('color','#FF0000')
			$("#email_button").prop('disabled', true);
		} 
	});
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
	/* 이름 포커스 */
	$("#name").on('focus', function() {
		$("#name").css({
			'background' : "#E8F0FE"
		});
	});
	/* 이름 포커스 아웃*/
	$("#name").on('focusout', function() {
		$("#name").css({
			'background' : "white"
		});
	});
	/* 이메일 포커스 */
	$("#email").on('focus', function() {
		$("#email").css({
			'background' : "#E8F0FE"
		});
	});
	/* 이메일 포커스 아웃*/
	$("#email").on('focusout', function() {
		$("#email").css({
			'background' : "white"
		});
	});
	/* 인증번호 포커스 */
	$("#mobileNum").on('focus', function() {
		$("#mobileNum").css({
			'background' : "#E8F0FE"
		});
	});	
	
	/* 인증번호 포커스 아웃 */
	$("#mobileNum").on('focusout', function() {
		$("#mobileNum").css({
			'background' : "white"
		});
	});
	
}); // ready 끝

/* 인증번호 */
function mobilePopup() {
    // window.name = "부모창 이름"; 
    window.name = "parentForm";
    // window.open("open할 window", "자식창 이름", "팝업창 옵션");
    window.open("/takeit/member/mobilePopup.jsp",
            "childForm", "width=570, height=350, resizable = no, scrollbars = no");    
}

/* 이메일 : takeit@takeit.com 형식에 맞게 입력 */
 function email_data(take) {
 	var pattern = new RegExp(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{3}$/i)
 	return pattern.test(take);
}
 
/* 찾기 폼 검증 메서드 */
function inputCheck() {
	/* 아이디 null일때 포커스 */
	var memberId = $("#memberId").val();
	if (memberId == "") {
		$("#memberId").focus();
		return false;
	}
	/* 판매자 아이디 null일때 포커스 */
	var sellerId = $("#sellerId").val();
	if (sellerId == "") {
		$("#sellerId").focus();
		return false;
	}
	/* 이름 null일때 포커스 */
	var name = $("#name").val();
	if (name == "") {
		$("#name").focus();
		return false;
	}
	/* 이메일 null일때 포커스 */
	var email = $("#email").val();
	if (email == "") {
		$("#email").focus();
		return false;
	}
	/* 인증번호 null일때 포커스 */
	var mobileNum = $("#mobileNum").val();
	if (mobileNum == "") {
		$("#mobileNum").focus();
		return false;
	} 
	return true;
}