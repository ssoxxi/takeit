/**
 * 회원가입 : 일반, 판매자
 * @author 임우진
 * 
 */
$(document).ready(function() {

	/*  비활성화 */
	$("#id_button" ).prop('disabled', true)	
	$("#mobile_button" ).prop('disabled', true)	
	$("#email_button" ).prop('disabled', true)	
	$("#sellerNoBtn" ).prop('disabled', true)	
	$("#shopNameBtn" ).prop('disabled', true)	
	
	/* 회원가입 폼 : 라디오 버튼 */
	$("#normalInputFrom").click(function() {
		 location.href = "/takeit/member/memberInput.jsp"
	});
	
	/* 회원가입 폼 : 라디오 버튼 */
	$("#sellerInputForm").click(function() {
		location.href = "/takeit/seller/controller?action=sellerInputForm"
	});
	
	/* === 일반회원 === */

	/* 이름 포커스 */
	$("#name").on('focus', function() {
		$("#name").css({
			'background' : "#E8F0FE"
		});
		$('#nameResult1').show().html("* 2~6자 한글입력");
	});	
	/* 이름 포커스 아웃 */
	$("#name").on('focusout', function() {
		$("#name").css({
			'background' : "white"
		});
	});
	/* 이름 키다운 */
	$("#name").on('keyup', function() {
		var name = $("#name").val();
		var name_Valid = name_data(name)
		if(name_Valid) {
			$('#nameResult1').show().html("o 2~6자 한글입력");
			$('#nameResult1').css('color','#08A600')
		}else {
			$('#nameResult1').show().html("x 2~6자 한글입력");
			$('#nameResult1').css('color','#FF0000')
		} 
	});
	/* 휴대폰 포커스 */
	$("#mobile").on('focus', function() {
		$("#mobile").css({
			'background' : "#E8F0FE"
		});
		$('#mobileResult1').show().html("* - 포함 13자리를 입력하세요. ex) 000-0000-0000");
	});	
	/* 휴대폰 포커스 아웃 */
	$("#mobile").on('focusout', function() {
		$("#mobile").css({
			'background' : "white"
		});
	});
	/* 휴대폰 키다운 */
	$("#mobile").on('keyup', function() {
		var mobile = $("#mobile").val();
		var mobile_Valid = mobile_data(mobile)
		if(mobile_Valid) {
			$('#mobileResult1').show().html("o - 포함 13자리를 입력하세요. ex) 000-0000-0000");
			$('#mobileResult1').css('color','#08A600')
			$("#mobile_button" ).prop('disabled', false);
		}else {
			$('#mobileResult1').show().html("x - 포함 13자리를 입력하세요. ex) 000-0000-0000");
			$('#mobileResult1').css('color','#FF0000')
			$("#mobile_button" ).prop('disabled', true);
		} 
	});
	/* 인증번호 포커스 */
	$("#mobileNum").on('focus', function() {
		$("#mobileNum").css({
			'background' : "#E8F0FE"
		});
		//$('#mobileNumResult1').show().html("* 인증번호를 받아주세요.");
	});	
	/* 인증번호 포커스 아웃 */
	$("#mobileNum").on('focusout', function() {
		$("#mobileNum").css({
			'background' : "white"
		});
	});
	/* 이메일 포커스 */
	$("#email").on('focus', function() {
		$("#email").css({
			'background' : "#E8F0FE"
		});
		$('#emailNumResult1').show().html("* 예:takeit@take.com<br>");
		$('#emailNumResult2').show().html("* 이메일 중복확인");
	});	
	/* 이메일 포커스 아웃 */
	$("#email").on('focusout', function() {
		$("#email").css({
			'background' : "white"
		});
	});
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
	/* 우편번호 포커스 */
	$("#postNo").on('focus', function() {
		$("#postNo").css({
			'background' : "#E8F0FE"
		});
	});
	/* 우편번호 포커스 아웃 */
	$("#postNo").on('focusout', function() {
		$("#postNo").css({
			'background' : "white"
		});
	});
	/* 주소 포커스 */
	$("#address").on('focus', function() {
		$("#address").css({
			'background' : "#E8F0FE"
		});
	});
	/* 주소 포커스 아웃 */
	$("#address").on('focusout', function() {
		$("#address").css({
			'background' : "white"
		});
	});
	/* 상세주소 포커스 */
	$("#addressDetail").on('focus', function() {
		$("#addressDetail").css({
			'background' : "#E8F0FE"
		});
	});
	/* 상세주소 포커스 아웃 */
	$("#addressDetail").on('focusout', function() {
		$("#addressDetail").css({
			'background' : "white"
		});
	});
	/* 생년월일 포커스 */
	$("#brith").on('focus', function() {
		$("#brith").css({
			'background' : "#E8F0FE"
		});
	});
	/* 생년월일 포커스 아웃 */
	$("#brith").on('focusout', function() {
		$("#brith").css({
			'background' : "white"
		});
	});

}); // ready 끝괄호
	
	/* 중복확인 포커스를 주기위한 변수명 생성 */

	var successEmail = "";
	var successSellerNo = "";
	var successShopName = "";
	

	/* 이름 : 최소 2 ~ 최대 6자리 */	
	 function name_data(take) {
	 	var pattern = new RegExp(/^[가-힣]{2,6}$/); 
	 	return pattern.test(take);
	}
	/* 휴대폰  : 010-0000-0000 형식에 맞춰 입력 */
	 function mobile_data(take) {
	 	var pattern = new RegExp(/^\d{3}-\d{4}-\d{4}$/); 
	 	return pattern.test(take);
	}
	/* 이메일 : takeit@takeit.com 형식에 맞게 입력 */
	 function email_data(take) {
	 	var pattern = new RegExp(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{3}$/i)
	 	return pattern.test(take);
	}
	 
/* 일반 회원가입 폼 검증 메서드 */
function inputCheck() {

	/* 이름 */
	var name = $("#name").val();
	var name_Valid = name_data(name)
	if (!name_Valid) {
		$("#name").focus();
		return false;
	} 
	/* 휴대폰*/
	var mobile = $("#mobile").val();
	var mobile_Valid = mobile_data(mobile)
	if (!mobile_Valid) {
		$("#mobile").focus();
		return false;
	} 
	/* 인증번호 */
	var mobileNum = $("#mobileNum").val();
	if (mobileNum == "") {
		$("#mobileNum").focus();
		return false;
	}
	/* 이메일 */
	var email = $("#email").val();
	var email_Valid = email_data(email)
	if (!email_Valid) {
		$("#email").focus();
		return false;
	} 
	/* 이메일 중복확인 */
	if(successEmail == "") {
		alert("이메일 중복확인을 해주세요.")
		$("#email").focus();
		return false;
	}
	return true;
}

/* 이메일 중복체크 */
function emailCheck(){
	
	/* 전역변수에 담기위한 변수 생성 */
	var check = "o";
	
	/* 중복확인 버튼 클릭시 검증이 맞지않으면 리턴 */
	var email = $("#email").val();
	var email_Valid = email_data(email)
	if(!email_Valid) {
		$("#email").focus();
		return false;
	}
	
	 $.ajax({	
		 	// 서블릿으로 보낸다
			url:"/takeit/member/controller?action=memberEmailChk",
			type:"get",	
			// name값
			data:{
				"email" : $("#email").val()  
			},	
		
			success:function(data){
				
				if(data == "1"){
					alert("사용가능한 이메일입니다.");
					$('#emailNumResult2').show().html(check + " 이메일 중복확인");
					successEmail = check;	/* 중복확인이 성공하면 전역변수에 o를 담는다. */
					$('#emailNumResult2').css('color','#08A600')
					$("#email" ).prop('readonly', true);
					
				}else {					
					alert("해당 이메일은 사용중입니다.");	
					$('#emailNumResult2').show().html("x 이메일 중복확인");
					$('#emailNumResult2').css('color','#FF0000')
				}					
			}
	});	 
};
/* 비밀번호 표시 */
function pwCheckbox_onclick() {
	
	var pwCheckboxElement = document.getElementById("pwCheckbox")
	var memberPwElement = document.getElementById("memberPw")
	var pwChk_Element = document.getElementById("pwChk")
	
	if(pwCheckboxElement.checked) {
		memberPwElement.type = "text"
		pwChk_Element.type = "text"
	}else {
		memberPwElement.type = "password"
		pwChk_Element.type = "password"
	}
}

/* 우편번호 api */
var goPopup = function() {
	 var pop = window.open("/takeit/member/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes");
} 
var jusoCallBack = function(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo){
	 document.getElementById("postNo").value = zipNo; 
	 document.getElementById("address").value = roadAddrPart1; 
	 document.getElementById("addressDetail").value = addrDetail; 
	 if(addressDetail.length>30){ 
		alert('상세주소를 30자 이내로 입력해주세요.'); 
		return; 
	} 
}

/* 모바일 인증번호 팝업 */
function mobilePopup() {
    // window.name = "부모창 이름"; 
    window.name = "parentForm";
    // window.open("open할 window", "자식창 이름", "팝업창 옵션");
    window.open("/takeit/member/mobilePopup.jsp",
            "childForm", "width=570, height=350, resizable = no, scrollbars = no");  
}
