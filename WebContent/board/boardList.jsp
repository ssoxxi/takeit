<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib_menu.jsp" %> 
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.takeit.model.dto.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>takeit::공지사항</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit/css/board.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#go").on("click",function(){
		$("#go").attr("style", "color: #fe4b03")
	});
	
	function valueIsNull(){
		var inputVal = $("#searchInput").val();
		if(inputVal == ""){
			$("#board-searchInput").focus();
			return false;
		} else {
			return true;
		}
	}
});
	
</script>
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
<div id="notice">
<div id="title">
	<h3></h3>
</div>
<%
	ArrayList<Board> boardList = (ArrayList<Board>)request.getAttribute("boardList");
	String categoryName = boardList.get(0).getBoardCategoryName();
%>
<c:if test="${boardList.isEmpty()}">
	<jsp:forward page="/board/noBoardSearchResult.jsp"></jsp:forward>
</c:if>
<div id="title">
	<h3><%= categoryName %> </h3>
</div>
<%
	String category = boardList.get(0).getBoardCategory();
	String memberId = (String)session.getAttribute("memberId");
	String sellerId = (String)session.getAttribute("sellerId");
	if(memberId == null){ memberId = "";}
	if(category.equals("1") || category.equals("2") ){
		if(memberId.equals("admin") && memberId != null){
%>
<div id="small-btn">
	<a href="/takeit/boardController?action=boardInputForm">등록</a>
</div>
<%
		} 
	} else if(category.equals("3")) {
		if(memberId != null && memberId.trim().length() > 0 ||
			sellerId != null && sellerId.trim().length() > 0){
%>
<div id="small-btn">
	<a href="/takeit/boardController?action=boardInputForm">등록</a>
</div>
<%
		} 
	}
%>
<table id="notice-tbl" class="notice-table">
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>조회수</th>
		<th>작성일자</th>
	</tr>
	<c:forEach var="dto" items="${boardList}" begin="${startRow }" end="${endRow}">
	<tr>
		<td>${dto.boardNo}</td>
		<td>
			<a id="boardLink" href="/takeit/boardController?action=boardDetail&boardNo=${dto.boardNo}&boardCategory=${dto.boardCategory}">${dto.boardTitle}</a>
		</td>
		<td>${dto.boardWriter}</td>
		<td>${dto.boardViews}</td>
		<td>${dto.boardDate}</td>
	</tr>
	</c:forEach> 
</table>
<!-- 게시글 검색 -->
<div id="boardSearch-area">
	<form action="/takeit/boardController?action=boardSearch&boardCategory=<%= boardList.get(0).getBoardCategory() %>" method="post" style="width: fit-content;">
	<select id="boardSearch" name="boardSearch" class="searchInput" style="width:fit-content; margin: 0 auto;">
		<option value="board_writer">작성자아이디</option>
		<option value="board_title">글제목</option>
		<option value="board_contents">글내용</option>
		<option value="item_no">상품번호</option>
	</select>
	<input type="search" name="searchInput" id="board-searchInput" class="searchInput" placeholder="검색어를 입력하세요.." style="border: 1px solid grey; height: 25px;">
	<input type="submit" class="searchInput" id="searchInput-btn" value="검색" onclick="return valueIsNull(); ">
	</form>
</div>

<!-- 페이징 -->
<div class="view-width">
	<div id="paging">
		<c:choose>
			<c:when test="${whereGroup > 1 }">
				<span><a href="/takeit/boardController?action=boardListPaging&boardCategory=1&goGroup=1">[처음]</a></span>
				<span><a href="/takeit/boardController?action=boardListPaging&boardCategory=1&goGroup=${priorGroup}">[이전]</a></span>
			</c:when>
			<c:otherwise>
				<span>[처음]</span>
				<span>[이전]</span>
			</c:otherwise>
		</c:choose>
		<c:forEach var="i" begin="${startPageNo}" end="${endPageNo}" step="1">
			<a href="/takeit/boardController?action=boardListPaging&boardCategory=1&go=${i}" id="go">${i}</a>
		</c:forEach>
		<c:choose>
			<c:when test="${whereGroup < totalGroup}">
				<span><a href="/takeit/boardController?action=boardListPaging&boardCategory=1&goGroup=${nextGroup}">[다음]</a></span>
				<span><a href="/takeit/boardController?action=boardListPaging&boardCategory=1&goGroup=${totalGroup}">[마지막]</a></span>
			</c:when>
			<c:otherwise>
				<span>[다음]</span>
				<span>[마지막]</span>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<a href="/takeit/index" class="link">홈으로이동</a>
</div>

<!-- floating Banner -->
<jsp:include page="/common/floatingBanner.jsp"></jsp:include>
<!-- scroll function -->
<jsp:include page="/common/back_to_top.jsp"></jsp:include>
<!-- footer 구역 -->
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>