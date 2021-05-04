<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ include file="/common/taglib_menu.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품등록</title>
<link type="text/css" rel="stylesheet" href="/takeit/css/link.css">
<link type="text/css" rel="stylesheet" href="/takeit2/css/item.css">
<script type="text/javascript">


</script>
</head>
<body>
<!-- 상단 메뉴 -->

<!-- logo.jsp 삽입 -->
<jsp:include page="/common/logo.jsp"></jsp:include>

	<h3 align="center">판매상품 등록</h3>
	<div align='center'>
	  <form name="form1" method="post" enctype="multipart/form-data"> 
		<table class="enroll_table" id="enroll_table">
	
			<tr>
				<td>상품</td>
				<td><input type="text" name="itemName" placeholder="상품명">
				</td>
			</tr>
			
			<tr>
				<td>판매가</td>
				<td><input type="text" name="itemPrice" placeholder="판매가">
				</td>
			</tr>
			
			
			<tr>
				<td>상품카테고리이름</td>
				<td>
					<select id="itemCategoryNo" style="width: 100px;">
						
					<c:if test="${categoryList != null}"> 	
				<c:forEach items="${categoryList }" var="list">
							<option value ="${list.itemCategoryNo }">${list.itemCategoryName }</option>
					</c:forEach>
					</c:if>
					</select>
				</td>
			</tr>
				
			<tr>
				<td>포장타입</td>
				<td>
				
				<select id="packTypeName" style="width: 100px;">
						
					<c:if test="${packTypeList != null}"> 	
				<c:forEach items="${packTypeList }" var="packTypeList">
							<option value ="${packTypeList.packTypeNo }">${packTypeList.packTypeName }</option>
					</c:forEach>
					</c:if>
					</select>
				
				</td>
			</tr>
			
			<tr>
				<td>재고량</td>
				<td><input type="text" name="itemStock" placeholder="재고량" value="">
				</td>
			</tr>
          

			<tr>
				<td>원산지</td>
				<td><input type="text" name="itemOrigin" placeholder="원산지">
				</td>
			</tr>

			
			<tr>
				<td>상품이미지</td>
				<td><input type="file" name="itemImg" placeholder="상품이미지">
				</td>
			</tr>
	

			<tr>
				<td>잇거래여부</td>
				<td><select onchange="takeIt">
						<option value="">==잇거래 여부==</option>
						<option value="">True(등록)</option>
						<option value="">False(등록안함)</option>
				</select></td>
			</tr>
			
				
			<tr>
				<td>판매자</td>
				<td><input type="text" name="sellerId" placeholder="판매자" value="${seller.sellerId }">
				</td>
			</tr>

		
		</table>
	<br></form>
		<div id="signup" align='center'>
		
			<tr>
				<td><input type="submit" value="상품등록"></td>
				<td><input type="reset" value="취소"></td>
			</tr>
		</div>
	</div>
	<!-- scroll function -->
	<jsp:include page="/common/back_to_top.jsp"></jsp:include>
	<!-- footer 구역 -->
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>