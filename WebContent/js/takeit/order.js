$(document).ready(function() {
	/* 수령 radio */
	$("#receiveMethod2").click(function() {
		$("#recipientName").val("");
		$("#recipientMobile").val("");
		$("#recipientPostNo").val("").attr("disabled", false);
		$("#recipientAddr").val("").attr("disabled", false);
		$("#recipientAddrDetail").val("").attr("disabled", false);
		$("#shipRequest").val("").attr("disabled", false);
	});
	$("#receiveMethod3").click(function() {
		$("#recipientPostNo").val("").attr("disabled", true);
		$("#recipientAddr").val("").attr("disabled", true);
		$("#recipientAddrDetail").val("").attr("disabled", true);
		$("#shipRequest").val("").attr("disabled", true);
	});
	
	/* 휴대폰 포커스 */
	$("#recipientMobile").on('focus', function() {
		$('#mobileResult1').show().html(" * - 포함 13자리를 입력하세요. ex) 000-0000-0000");
	});	
	/* 휴대폰 키다운 */
	$("#recipientMobile").on('keyup', function() {
		var recipientMobile = $("#recipientMobile").val();
		var recipientMobile_Valid = mobile_data(recipientMobile)
		if(recipientMobile_Valid) {
			$('#mobileResult1').show().html(" o - 포함 13자리를 입력하세요. ex) 000-0000-0000");
			$('#mobileResult1').css('color','#08A600')
		}else {
			$('#mobileResult1').show().html(" x - 포함 13자리를 입력하세요. ex) 000-0000-0000");
			$('#mobileResult1').css('color','#FF0000')
		} 
	});
});

/* 휴대폰  : 010-0000-0000 형식에 맞춰 입력 */
function mobile_data(take) {
	var pattern = new RegExp(/^\d{3}-\d{4}-\d{4}$/); 
	return pattern.test(take);
}

/* 결제하기 검증 메서드 */
function orderCheck() {
	/* 이름 */
	var recipientName = $("#recipientName").val();
	if (recipientName == "") {
		$("#recipientName").focus();
		return false;
	} 
	/* 휴대폰 */
	var recipientMobile = $("#recipientMobile").val();
	var recipientMobile_Valid = mobile_data(recipientMobile)
	if (!recipientMobile_Valid || recipientMobile == "") {
		$("#recipientMobile").focus();
		return false;
	}
	
	/* 우편번호 */
	console.log("method3:"+$("#receiveMethod3").prop("checked"));
	if (!$("#receiveMethod3").prop("checked")) {
		if($("#recipientPostNo").val() == "") {
			$("#postNoBtn").focus();
			$('#postNoResult1').show().html(" * - 주소를 검색해주세요. ");
			return false;
		}
		if($("#recipientAddrDetail").val() == "") {
			$("#recipientAddrDetail").focus();
			$('#addressDetailResult1').show().html(" * - 상세주소를 입력해주세요. ");
			return false;
		}
	}
	return true;
}

/* 우편번호 api */
var goPopup = function() {
	 var pop = window.open("/takeit/member/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes");
} 
var jusoCallBack = function(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo){
	 document.getElementById("recipientPostNo").value = zipNo; 
	 document.getElementById("recipientAddr").value = roadAddrPart1; 
	 document.getElementById("recipientAddrDetail").value = addrDetail; 
	 if(addressDetail.length>30){ 
		alert('상세주소를 30자 이내로 입력하세요.'); 
		return; 
	} 
}

