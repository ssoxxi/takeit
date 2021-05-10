<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 네비게이션 -->
<div id="navi-wrap" class="view-width">
	<div id="navi">
		<a href="#" id="category-wrap">전체카테고리</a>
		<span id="pipe">|</span>
		<a href="/takeit/item/itemController?action=itemList">신상품</a>
		<span id="pipe">|</span>
		<a href="/takeit/item/reviewController?action=reviewList">사용자리뷰</a>
		<span id="pipe">|</span>
		<a href="/takeit/takeit/takeitController?action=takeitItemList&scope=all">잇거래</a>
		<span id="pipe">|</span>
		<a href="/takeit/boardController?action=boardListPaging&boardCategory=1&goGroup=1">공지사항</a>
	</div>
	<div id="search-wrap">
	<form action="/takeit/searchController?action=searchList" id="mainSearch" method="post">
		<input type="text" id="searchInput" name="searchInput"  placeholder="검색어를 입력하세요..">
		<button type="submit" id="search-btn-wrap"><img id="search-btn" alt="검색" src="/takeit/img/icon/ico_search_x2.png"></button>
	</form>
	</div>
</div>
<div id="drop-menu">
	<ul class="sub-nav">
		<li><a href="/takeit/categoryController?action=categoryList&categoryNo=sd">국/반찬/메인요리</a></li>
		<li><a href="/takeit/categoryController?action=categoryList&categoryNo=fr">과일</a></li>
		<li><a href="/takeit/categoryController?action=categoryList&categoryNo=me">정육/계란</a></li>
		<li><a href="/takeit/categoryController?action=categoryList&categoryNo=ve">채소</a></li>
		<li><a href="/takeit/categoryController?action=categoryList&categoryNo=ri">견과/쌀/곡물</a></li>
		<li><a href="/takeit/categoryController?action=categoryList&categoryNo=mi">유제품</a></li>
		<li><a href="/takeit/categoryController?action=categoryList&categoryNo=dr">간식/과자/음료</a></li>
	</ul>
</div>
<hr>
<script type="text/javascript">
	$(document).ready(function(){
		$("#drop-menu").hide();
	});
	$("#category-wrap, #drop-menu").on("click",function(){
		$("#drop-menu").stop().slideToggle(400);
	});	
	
</script>
