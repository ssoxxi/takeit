<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>잇거래 관리</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet"
	href="/takeit/css/mypage/myPage.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/takeit.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".takeitRange").click(function() {
			$("#takeitListForm").submit();
		});
		$("#takeitRange${takeitRange}").attr("checked", true);
	});
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

	<div id="takeitMgr-view-width">
		<div class="side-menu">
			<c:choose>
				<c:when test="${not empty sellerId}">
					<!-- 판매자 마이페이지 메뉴 -->
					<jsp:include page="/common/mypage_seller_menu.jsp" />
				</c:when>
				<c:when test="${not empty memberId}">
					<!-- 일반회원 마이페이지 메뉴 -->
					<jsp:include page="/common/mypage_member_menu.jsp" />
				</c:when>
				<c:otherwise>
					<jsp:include page="/member/memberLogin.jsp" />
				</c:otherwise>
			</c:choose>
		</div>
		<div id="takeitManage-wrap">
			<div>
				<div class="takeitMgr-title">
					<h1>잇거래 목록</h1>
				</div>

				<form action="/takeit/takeit/takeitController?action=takeitManageForm" method="post" id="takeitListForm">
					<input type="radio" class="takeitRange" id="takeitRangeall"	value="all" name="takeitRange">전체
					<input type="radio"	class="takeitRange" id="takeitRangelive" value="live" name="takeitRange">진행중
					<input type="radio" class="takeitRange" id="takeitRangeexpired" value="expired"	name="takeitRange">만료
					<input type="radio" class="takeitRange" id="takeitRangedead" value="dead" name="takeitRange">종료
				</form>
				<form action="${CONTEXT_PATH}/takeit/takeitController?action=takeitDelete&takeitRange=${takeitRange}" method="post">
				<c:forEach var="takeit" items="${takeitList}">
					<input type="hidden" value="${takeit.takeitNo}" name="takeitNo">
				</c:forEach>
					<input type="submit" class="takeitMgr-del-btn" style="float: right; margin-right: 50px;" value="전체종료">
				</form>
			</div>
			<div>
				<div class="takeitMgr-scroll">
					<table class="takeitMgr-table">
						<tr id="takeitMgr-tr">
							<th style="width: 105px;">지역시장</th>
							<th style="width: 69px;">구역코드</th>
							<th style="width: 69px;">회원코드</th>
							<th style="width: 100.5px;">모집일자</th>
							<th style="width: 100.5px;">종료일자</th>
							<th style="width: 69px;">현재금액</th>
							<th style="width: 69px;">목표금액</th>
							<th style="width: 85.5px;">진행중여부</th>
							<th style="width: 102px;">종료하기</th>
						</tr>
						<c:forEach var="takeit" items="${takeitList}">
						
							<tr class="takeitMgr-table" style="text-align: center;">
								<td style="width: 105px;">${takeit.shopLocName}</td>
								<td style="width: 69px;">${takeit.shopLocCode}</td>
								<td style="width: 69px;">${takeit.memberLocNo}</td>
								<td style="width: 100.5px;">${takeit.takeitDate}</td>
								<td style="width: 100.5px;">${takeit.takeitEndDate}</td>
								<td style="width: 69px;">
									<fmt:formatNumber value="${takeit.takeitCurrPrice}" pattern="###,###,###" />
								</td>
								<td style="width: 69px;">
									<fmt:formatNumber value="${takeit.takeitPrice}" pattern="###,###,###" />
								</td>
								<td style="width: 85.5px;">${takeit.takeitAlive}</td>
								<td style="width: 102px;">
									<form action="${CONTEXT_PATH}/takeit/takeitController?action=takeitDelete&takeitRange=${takeitRange}" method="post">
										<input type="hidden" value="${takeit.takeitNo}" name="takeitNo">
										<input type="submit" class="takeitMgr-del-btn" value="종료">
									</form>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- scroll function -->
	<jsp:include page="/common/back_to_top.jsp"></jsp:include>
	<!-- footer 구역 -->
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>