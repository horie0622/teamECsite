<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./css/style.css">
<link rel="stylesheet" type="text/css" href="./css/productList.css">
<title>ProductList画面</title>
</head>
<body>
<jsp:include page="header.jsp"/>

	<div id="main">
			<h1>商品一覧画面</h1>
		<s:if test="errorList !=null && errorList.size()>0">
			<div class="error">
			<div class="error-message">
				<s:iterator value="errorList"><s:property /><br></s:iterator>
			</div>
			</div>
		</s:if>
		<s:elseif test="productInfoDTOList !=null && productInfoDTOList.size()>0">
			<div class="productList">
				<table class="product-list-table">

					<s:iterator value="productInfoDTOList" status="st">
						<div class="product">

							<a href='<s:url action="ProductDetailsAction"><s:param name="productId" value="%{productId}"/></s:url>'>
								<img src='<s:property value="imgFilePath"/>' class="image-box-18"/><br>
								<s:property value="productName"/><br>
								<s:property value="productNameKana"/><br>
								<s:property value="price"/>円<br>
							</a>

						</div>
					</s:iterator>

				</table>
			</div>
		</s:elseif>

 		<s:else>
	 		<div class="info">
	 		検索結果がありません。
	 		</div>
 		</s:else>
	</div>
</body>
</html>