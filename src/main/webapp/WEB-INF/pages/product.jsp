<%--
  Created by IntelliJ IDEA.
  User: Yura
  Date: 09.01.2023
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.entity.Product" scope="request"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<tags:master pageTitle="Product Details">
<table>
    <header>
        <tags:header/>
    </header>
    <tr>
        <td>Image</td>
        <td>
            <img src="${product.imageUrl}">
        </td>
    </tr>
    <tr>
        <td>
            Name
        </td>
        <td>
                ${product.description}
        </td>
    </tr>
    <tr>
        <td>
            Price
        </td>
        <td >
            <a href="${pageContext.servletContext.contextPath}/products/price-history/${product.id}">
                    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </a>
        </td>
    </tr>
    <tr>
        <td>
            Stock
        </td>
        <td>
            ${product.stock}
        </td>
    </tr>

    <tr>
        <td>
            Code
        </td>
        <td>
                ${product.code}
        </td>
    </tr>

</table>
</tags:master>

<footer>
    <tags:footer/>
</footer>
</html>
