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
	/* 아이디 포커스 */
	$("#memberId").on('focus', function() {
		$("#memberId").css({
			'background' : "#E8F0FE"
		});
			$('#memberIdResult1').show().html("* 6~20자 영문 혹은 영문+숫자를 조합<br>");
			$('#memberIdResult2').show().html("* 아이디 중복확인");
	});	
	/* 아이디 포커스 아웃 */
	$("#memberId").on('focusout', function() {
		$("#memberId").css({
			'background' : "white"
		});
	});
	/* 아이디 키다운 */
	$("#memberId").on('keyup', function() {
		var memberId = $("#memberId").val();
		var memberId_Valid = memberId_data(memberId)
		if(memberId_Valid) {
			$('#memberIdResult1').show().html("o 6~20자 영문 혹은 영문+숫자를 조합<br>");
			$('#memberIdResult1').css('color','#08A600')
			$("#id_button" ).prop('disabled', false);
		}else {
			$('#memberIdResult1').show().html("x 6~20자 영문 혹은 영문+숫자를 조합<br>");
			$('#memberIdResult1').css('color','#FF0000')
			$("#id_button" ).prop('disabled', true);
		}
	});
	/* 비밀번호 포커스 */
	$("#memberPw").on('focus', function() {
		$("#memberPw").css({
			'background' : "#E8F0FE"
		});
		$('#memberPwResult1').show().html("* 8~20자 영문대소문자+숫자 조합");
	});	
	/* 비밀번호 포커스 아웃 */
	$("#memberPw").on('focusout', function() {
		$("#memberPw").css({
			'background' : "white"
		});
	});
	/* 비밀번호 키다운 */
	$("#memberPw").on('keyup', function() {
		var memberPw = $("#memberPw").val();
		var memberPw_Valid = memberPw_data(memberPw)
		if(memberPw_Valid) {
			$('#memberPwResult1').show().html("o 8~20자 영문대소문자+숫자 조합");
			$('#memberPwResult1').css('color','#08A600')
		}else {
			$('#memberPwResult1').show().html("x 8~20자 영문대소문자+숫자 조합");
			$('#memberPwResult1').css('color','#FF0000')
		} 
	});
	/* 비밀번호 확인 포커스 */
	$("#pwChk").on('focus', function() {
		$("#pwChk").css({
			'background' : "#E8F0FE"
		});
		$('#pwChkResult1').show().html("* 동일한 비밀번호를 입력해주세요.");
	});	
	/* 비밀번호 확인 포커스 아웃 */
	$("#pwChk").on('focusout', function() {
		$("#pwChk").css({
			'background' : "white"
		});
	});
	/* 비밀번호확인 키다운 */
	$("#pwChk").on('keyup', function() {
		 var memberPw = $("#memberPw").val();
		 var pwChk = $("#pwChk").val();
		 
		 if(memberPw == pwChk) {
				$('#pwChkResult1').show().html("o 동일한 비밀번호를 입력해주세요.");
				$('#pwChkResult1').css('color','#08A600')
		 }else {
				$('#pwChkResult1').show().html("x 동일한 비밀번호를 입력해주세요.");
				$('#pwChkResult1').css('color','#FF0000')
		 }
	});
	
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
	/* === 판매자회원 ===*/
	/* 아이디 포커스 */
	$("#sellerId").on('focus', function() {
		$("#sellerId").css({
			'background' : "#E8F0FE"
		});
		$('#sellerIdResult1').show().html("* 6~20자 영문 혹은  영문+숫자를 조합<br>");
		$('#sellerIdResult2').show().html("* 아이디 중복확인");
	});
	/* 아이디 포커스 아웃*/
	$("#sellerId").on('focusout', function() {
		$("#sellerId").css({
			'background' : "white"
		});
	});
	/* 아이디 키다운 */
	$("#sellerId").on('keyup', function() {
		var sellerId = $("#sellerId").val();
		var sellerId_Valid = sellerId_data(sellerId)
		if(sellerId_Valid) {
			$('#sellerIdResult1').show().html("o 6~20자 영문 혹은 영문+숫자를 조합<br>");
			$('#sellerIdResult1').css('color','#08A600')
			$("#id_button").prop('disabled', false);
		}else {
			$('#sellerIdResult1').show().html("x 6~20자 영문 혹은 영문+숫자를 조합<br>");
			$('#sellerIdResult1').css('color','#FF0000')
			$("#id_button").prop('disabled', true);
		} 
	});
	/* 비밀번호 포커스 */
	$("#sellerPw").on('focus', function() {
		$("#sellerPw").css({
			'background' : "#E8F0FE"
		});
		$('#sellerPwResult1').show().html("* 8~20자 영문대소문자+숫자 조합");
	});
	/* 비밀번호 포커스 아웃*/
	$("#sellerPw").on('focusout', function() {
		$("#sellerPw").css({
			'background' : "white"
		});
	});
	/* 비밀번호 키다운 */
	$("#sellerPw").on('keyup', function() {
		var sellerPw = $("#sellerPw").val();
		var sellerPw_Valid = sellerPw_data(sellerPw)
		if(sellerPw_Valid) {
			$('#sellerPwResult1').show().html("o 8~20자 영문대소문자+숫자 조합");
			$('#sellerPwResult1').css('color','#08A600')
		}else {
			$('#sellerPwResult1').show().html("x 8~20자 영문대소문자+숫자 조합");
			$('#sellerPwResult1').css('color','#FF0000')
		} 
	});
	/* 비밀번호확인 포커스 */
	$("#pwChkSeller").on('focus', function() {
		$("#pwChkSeller").css({
			'background' : "#E8F0FE"
		});
		$('#pwChkResult1').show().html("* 동일한 비밀번호를 입력해주세요.");
	});
	/* 비밀번호확인 포커스 아웃*/
	$("#pwChkSeller").on('focusout', function() {
		$("#pwChkSeller").css({
			'background' : "white"
		});
	});
	/* 비밀번호확인 키다운 */
	$("#pwChkSeller").on('keyup', function() {
		 var sellerPw = $("#sellerPw").val();
		 var pwChkSeller = $("#pwChkSeller").val();
		 
		 if(sellerPw == pwChkSeller) {
				$('#pwChkResult1').show().html("o 동일한 비밀번호를 입력해주세요.");
				$('#pwChkResult1').css('color','#08A600')
		 }else {
				$('#pwChkResult1').show().html("x 동일한 비밀번호를 입력해주세요.");
				$('#pwChkResult1').css('color','#FF0000')
		 }
	});
	/* 상점구역 포커스 */
	$("#shopLocCode").on('focus', function() {
		$("#shopLocCode").css({
			'background' : "#E8F0FE"
		});
	});
	/* 상점구역 포커스 */
	$("#shopLocCode").on('focusout', function() {
		$("#shopLocCode").css({
			'background' : "white"
		});
	});
	/* 사업자등록번호 포커스 */
	$("#sellerNo").on('focus', function() {
		$("#sellerNo").css({
			'background' : "#E8F0FE"
		});
		$('#sellerNoResult1').show().html("* -포함 12자리를 입력하세요. ex) 000-00-00000<br>");
		$('#sellerNoResult2').show().html("* 사업자등록번호 중복확인");
	});
	/* 사업자등록번호 포커스 아웃*/
	$("#sellerNo").on('focusout', function() {
		$("#sellerNo").css({
			'background' : "white"
		});
	});
	/* 사업자등록번호 키다운 */
	$("#sellerNo").on('keyup', function() {
		var sellerNo = $("#sellerNo").val();
		var sellerNo_Valid = sellerNo_data(sellerNo)
		if(sellerNo_Valid) {
			$('#sellerNoResult1').show().html("o -포함 12자리를 입력하세요. ex) 000-00-00000<br>");
			$('#sellerNoResult1').css('color','#08A600')
			$("#sellerNoBtn").prop('disabled', false);
		}else {
			$('#sellerNoResult1').show().html("x -포함 12자리를 입력하세요. ex) 000-00-00000<br>");
			$('#sellerNoResult1').css('color','#FF0000')
			$("#sellerNoBtn").prop('disabled', true);
		} 
	}); 
	/* 상점연락처 포커스 */
	$("#shopMobile").on('focus', function() {
		$("#shopMobile").css({
			'background' : "#E8F0FE"
		});
		$('#shopMobileResult1').show().html("* - 포함 13자리를 입력하세요. ex) 000-0000-0000");
	});
	/* 상점연락처 포커스 아웃*/
	$("#shopMobile").on('focusout', function() {
		$("#shopMobile").css({
			'background' : "white"
		});
	});
	/* 상점연락처 키다운 */
	$("#shopMobile").on('keyup', function() {
		var shopMobile = $("#shopMobile").val();
		var shopMobile_Valid = shopMobile_data(shopMobile)
		if(shopMobile_Valid) {
			$('#shopMobileResult1').show().html("o - 포함 13자리를 입력하세요. ex) 000-0000-0000");
			$('#shopMobileResult1').css('color','#08A600')
		}else {
			$('#shopMobileResult1').show().html("x - 포함 13자리를 입력하세요. ex) 000-0000-0000");
			$('#shopMobileResult1').css('color','#FF0000')
		} 
	});
	/* 상점명 포커스 */
	$("#shopName").on('focus', function() {
		$("#shopName").css({
			'background' : "#E8F0FE"
		});
		$('#shopNameResult1').show().html("* 한글, 영문, 숫자 입력<br>");
		$('#shopNameResult2').show().html("* 상점명 중복확인");
	});
	/* 상점명 포커스 아웃*/
	$("#shopName").on('focusout', function() {
		$("#shopName").css({
			'background' : "white"
		});
	});
	/* 상점명 키다운 */
	$("#shopName").on('keyup', function() {
		var shopName = $("#shopName").val();
		var shopName_Valid = shopName_data(shopName)
		if(shopName_Valid) {
			$('#shopNameResult1').show().html("o 한글, 영문, 숫자 입력<br>");
			$('#shopNameResult1').css('color','#08A600')
			$("#shopNameBtn").prop('disabled', false);
		}else {
			$('#shopNameResult1').show().html("x 한글, 영문, 숫자 입력<br>");
			$('#shopNameResult1').css('color','#FF0000')
			$("#shopNameBtn").prop('disabled', true);
		} 
	});
	/* 카카오톡아이디 포커스 */
	$("#shopKakaoId").on('focus', function() {
		$("#shopKakaoId").css({
			'background' : "#E8F0FE"
		});
	});
	/* 카카오톡아이디 포커스 아웃*/
	$("#shopKakaoId").on('focusout', function() {
		$("#shopKakaoId").css({
			'background' : "white"
		});
	});
	/* 상점이미지 포커스 */
	$("#shopImg").on('focus', function() {
		$("#shopImg").css({
			'background' : "#E8F0FE"
		});
	});
	/* 상점이미지 포커스 아웃*/
	$("#shopImg").on('focusout', function() {
		$("#shopImg").css({
			'background' : "white"
		});
	});
	/* 상점카테고리번호 포커스 */
	$("#shopCategoryNo").on('focus', function() {
		$("#shopCategoryNo").css({
			'background' : "#E8F0FE"
		});
	});
	/* 상점카테고리번호 포커스 아웃*/
	$("#shopCategoryNo").on('focusout', function() {
		$("#shopCategoryNo").css({
			'background' : "white"
		});
	});
	}); // ready 끝괄호
	
	/* 중복확인 포커스를 주기위한 변수명 생성 */
	var successId = "";
	var successEmail = "";
	var successSellerNo = "";
	var successShopName = "";
	
	/* 아이디 : 6~20자 영문+숫자를 조합, 중복확인*/
	function memberId_data(take) {
	 	var pattern = new RegExp(/^[a-zA-Z][a-zA-Z0-9]{5,19}$/);
	  	return pattern.test(take);
	}
	/* 비밀번호 : 8~20자 영대소문자+숫자 조합 */
	 function memberPw_data(take) {
	 	var pattern = new RegExp(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$/);
	
	 	return pattern.test(take);
	}
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
	/* 판매자 아이디 : 6~20자 영문+숫자를 조합, 중복확인*/
	function sellerId_data(take) {
	 	var pattern = new RegExp(/^[a-zA-Z0-9]{6,20}$/g);
	 	return pattern.test(take);
	}
	/* 판매자 비밀번호 : 8~20자 영대소문자+숫자 조합 */
	 function sellerPw_data(take) {
		var pattern = new RegExp(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$/);
	 	return pattern.test(take);
	}
	/* 사업자번호 : 000-00-00000 -포함 12자리 */
	 function sellerNo_data(take) {
		 var pattern = new RegExp(/^\d{3}-\d{2}-\d{5}$/); 
	 	return pattern.test(take);
	}
	/* 상점연락처 : 휴대폰 동일 */
	 function shopMobile_data(take) {
		 var pattern = new RegExp(/^\d{3}-\d{4}-\d{4}$/); 
	 	return pattern.test(take);
	}
	/* 상점명 : 한글,영어,숫자 조합 */
	 function shopName_data(take) {
	 	var pattern = new RegExp(/^[가-힣|a-z|A-Z|0-9|]+$/);
	 	return pattern.test(take);
	}
	 
/* 일반 회원가입 폼 검증 메서드 */
function inputCheck() {
	/* 아이디 */
	var memberId = $("#memberId").val();
	var memberId_Valid = memberId_data(memberId)
	if (!memberId_Valid) {
		$("#memberId").focus();
		return false;
	} 
	/* 아이디 중복확인 */
	if(successId == "") {
		alert("아이디 중복확인을 해주세요.")
		$("#memberId").focus();
		return false;
	} 
	/* 비밀번호 */
	var memberPw = $("#memberPw").val();
	var memberPw_Valid = memberPw_data(memberPw)	
	if (!memberPw_Valid) {
		$("#memberPw").focus();
		return false;
	} 
	/* 비밀번호 확인 */
	var pwChk = $("#pwChk").val(); 
	if(memberPw != pwChk) {
		$("#pwChk").focus();
		return false;
	}
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
/* 판매자 회원가입 폼 검증 메서드 */
function inputCheckSeller() {
	/* 판매자 아이디 */
	var sellerId = $("#sellerId").val();
	var sellerId_Valid = sellerId_data(sellerId)
	if (!sellerId_Valid) {
		$("#sellerId").focus();
		return false;
	} 
	/* 아이디 중복확인 */
	if(successId == "") {
		alert("아이디 중복확인을 해주세요.")
		$("#sellerId").focus();
		return false;
	}
	/* 판매자 비밀번호 */
	var sellerPw = $("#sellerPw").val();
	var sellerPw_Valid = sellerPw_data(sellerPw)
	if (!sellerPw_Valid) {
		$("#sellerPw").focus();
		return false;
	} 
	/* 비밀번호 확인*/
	var pwChkSeller = $("#pwChkSeller").val(); 
	if(sellerPw != pwChkSeller) {
		$("#pwChkSeller").focus();
		return false;
	}
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
	/* 인증번호*/
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
	/* 상점구역 */
	var shopLocCode = $("#shopLocCode").val();
	if (shopLocCode == "none") {
		alert("상점구역을 선택해주세요.")
		//$("#shopLocCode").focus(); 사용시 폼이 넘어가버리는데 이유를 모름
		return false;
	}
	/* 사업자등록번호 */
	var sellerNo = $("#sellerNo").val();
	var sellerNo_Valid = sellerNo_data(sellerNo)
	if (!sellerNo_Valid) {
		$("#sellerNo").focus();
		return false;
	} 
	/* 사업자등록번호 중복확인 */
	if(successSellerNo == "") {
		alert("사업자등록번호 중복확인을 해주세요.")
		$("#sellerNo").focus();
		return false;
	}
	/* 상점연락처 */
	var shopMobile = $("#shopMobile").val();
	var shopMobile_Valid = shopMobile_data(shopMobile)
	if (!shopMobile_Valid) {
		$("#shopMobile").focus();
		return false;
	} 
	/* 상점명 */
	var shopName = $("#shopName").val();
	var shopName_Valid = shopName_data(shopName)
	if (!shopName_Valid) {
		$("#shopName").focus();
		return false;
	}
	/* 상점명 중복확인 */
	if(successShopName == "") {
		alert("상점명 중복확인을 해주세요.")
		$("#shopName").focus();
		return false;
	}
	/* 상점구역 */
	var shopCategoryNo = $("#shopCategoryNo").val();
	if (shopCategoryNo == "none") {
		alert("상점카테고리를 선택해주세요.")
		//$("#shopLocCode").focus(); 사용시 폼이 넘어가버리는데 이유를 모름
		return false;
	}
	return true;
}

/* 아이디 중복체크 */
function idCheck(){
	
	/* 전역변수에 담기위한 변수 생성 */
	var check = "o";
	
	/* 중복확인 버튼 클릭시 검증이 맞지않으면 리턴 */
	var memberId = $("#memberId").val();
	var memberId_Valid = memberId_data(memberId)
	if(!memberId_Valid) {
		$("#memberId").focus();
		return false;
	}
	
	 $.ajax({	
		 	// 서블릿으로 보낸다
			url:"/takeit/member/controller?action=memberIdChk",
			type:"get",	
			// name값
			data:{
				"memberId" : $("#memberId").val()  
			},	
			
			success:function(data){
				if(data == "1"){
					alert("사용가능한 아이디입니다.");
					$('#memberIdResult2').show().html(check + " 아이디 중복확인"); 
					successId = check;	/* 중복확인이 성공하면 전역변수에 o를 담는다. */
					$('#memberIdResult2').css('color','#08A600')
					$("#memberId" ).prop('readonly', true);
				}else {					
					alert("해당 아이디는 사용중입니다.");	
					$('#memberIdResult2').show().html("x 아이디 중복확인");
					$('#memberIdResult2').css('color','#FF0000')
				}
			}
	});	
	 

};
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
/* 판매자 아이디 중복체크 */
function idCheckSeller(){
	
	/* 전역변수에 담기위한 변수 생성 */
	var check = "o";
	
	/* 중복확인 버튼 클릭시 검증이 맞지않으면 리턴 */
	var sellerId = $("#sellerId").val();
	var sellerId_Valid = sellerId_data(sellerId)
	if(!sellerId_Valid) {
		$("#sellerId").focus();
		return false;
	}
	
	 $.ajax({	
		 	// 서블릿으로 보낸다
			url:"/takeit/seller/controller?action=sellerIdChk",
			type:"get",	
			// name값
			data:{
				"sellerId" : $("#sellerId").val()  
			},	
		
			success:function(data){
				
				if(data == "1"){
					alert("사용가능한 아이디입니다.");
					$('#sellerIdResult2').show().html(check + " 아이디 중복확인");
					successId = check;	/* 중복확인이 성공하면 전역변수에 o를 담는다. */
					$('#sellerIdResult2').css('color','#08A600')
					$("#sellerId").prop('readonly', true);
				}else {					
					alert("해당 아이디는 사용중입니다.");	
					$('#sellerIdResult2').show().html("x 아이디 중복확인");
					$('#sellerIdResult2').css('color','#FF0000')
				}				
			}
	});	 
};
/* 판매자 이메일 중복체크 */
function emailCheckSeller(){
	
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
			url:"/takeit/seller/controller?action=sellerEmailChk",
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
					$("#email").prop('readonly', true);
				}else {					
					alert("해당 이메일은 사용중입니다.");	
					$('#emailNumResult2').show().html("x 이메일 중복확인");
					$('#emailNumResult2').css('color','#FF0000')
				}			
			}
	});	 
};
/* 판매자 사업자등록번호 중복체크 */
function sellerNoCheck(){
	
	/* 전역변수에 담기위한 변수 생성 */
	var check = "o";
	
	/* 중복확인 버튼 클릭시 검증이 맞지않으면 리턴 */
	var sellerNo = $("#sellerNo").val();
	var sellerNo_Valid = sellerNo_data(sellerNo)
	if(!sellerNo_Valid) {
		$("#sellerNo").focus();
		return false;
	}
	
	 $.ajax({	
		 	// 서블릿으로 보낸다
			url:"/takeit/seller/controller?action=sellerNoChk",
			type:"get",	
			// name값
			data:{
				"sellerNo" : $("#sellerNo").val()  
			},	
		
			success:function(data){
				
				if(data == "1"){
					alert("사용가능한 사업자등록번호입니다.");
					$('#sellerNoResult2').show().html(check + " 사업자등록번호 중복확인");
					successSellerNo = check;	/* 중복확인이 성공하면 전역변수에 o를 담는다. */
					$('#sellerNoResult2').css('color','#08A600')
					$("#sellerNo").prop('readonly', true);
					
				}else {					
					alert("해당 사업자등록번호는 사용중입니다.");	
					$('#sellerNoResult2').show().html("x 사업자등록번호 중복확인");
					$('#sellerNoResult2').css('color','#FF0000')
				}				
			}
	});	 
};
/* 판매자 상점명 중복체크 */
function shopNameCheck(){
	
	/* 전역변수에 담기위한 변수 생성 */
	var check = "o";
	
	/* 중복확인 버튼 클릭시 검증이 맞지않으면 리턴 */
	var shopName = $("#shopName").val();
	var shopName_Valid = shopName_data(shopName)
	if(!shopName_Valid) {
		$("#shopName").focus();
		return false;
	}
	
	$.ajax({	
		// 서블릿으로 보낸다
		url:"/takeit/seller/controller?action=shopNameChk",
		type:"get",	
		// name값
		data:{
			"shopName" : $("#shopName").val()  
		},	
		
		success:function(data){
			
			if(data == "1"){
				alert("사용가능한 상점명입니다.");
				var shopNameResult2 = $('#shopNameResult2').show().html(check + " 상점명 중복확인");
				successShopName = check;	/* 중복확인이 성공하면 전역변수에 o를 담는다. */
				$('#shopNameResult2').css('color','#08A600')
				$("#shopName").prop('readonly', true);
			}else {					
				alert("해당 상점명은 사용중입니다.");	
				$('#shopNameResult2').show().html("x 상점명 중복확인");
				$('#shopNameResult2').css('color','#FF0000')
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
/* 판매자 비밀번호 표시*/
function pwCheckbox_onclick_seller() {
	
	var pwCheckboxElement = document.getElementById("pwCheckbox")
	var sellerPwElement = document.getElementById("sellerPw")
	var pwChkSeller_Element = document.getElementById("pwChkSeller")
	
	if(pwCheckboxElement.checked) {
		sellerPwElement.type = "text"
		pwChkSeller_Element.type = "text"
	}else {
		sellerPwElement.type = "password"
		pwChkSeller_Element.type = "password"
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
