<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 상단메뉴 -->
<div id="top-menu" class="view-width">
<c:if test="${not empty memberId and empty sellerId}">
	<%= session.getAttribute("memberId") %> 님
	<span id="pipe">|</span>
	<a href="/takeit/member/controller?action=memberLogout">로그아웃</a>
	<span id="pipe">|</span>
	<a href="/takeit/member/myPage.jsp">마이페이지</a>
	<span id="pipe">|</span>
	<a href="/takeit/board/customerCenter.jsp">고객센터</a>
</c:if>
<c:if test="${empty memberId and not empty sellerId}">
	<%= session.getAttribute("sellerId") %> 님
	<span id="pipe">|</span>
	<a href="/takeit/seller/controller?action=sellerLogout">로그아웃</a>
	<span id="pipe">|</span>
	<a href="/takeit/member/myPage.jsp">마이페이지</a>
	<span id="pipe">|</span>
	<a href="/takeit/board/customerCenter.jsp">고객센터</a>
</c:if>

</div>	