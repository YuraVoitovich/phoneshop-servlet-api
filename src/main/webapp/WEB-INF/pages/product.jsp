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

<form method="post">
    <table>
        <header>
            <tags:header/>
        </header>
        <c:if test = "${not empty param.message and param.message != 'success'}">
            <div class="error-message">
                    ${param.message}
            </div>
        </c:if>
        <c:if test = "${param.message eq 'success'}">
            <div class="success-message">
                    Product added to cart successfully
            </div>
        </c:if>

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

        <tr>

            <td>
                <fmt:formatNumber value="${param.quantity}" var="quantity"/>
                <input class="price" name="quantity" value=${empty param.message ? "1" : param.savedQuantity}>
            </td>
            <td>
                <button>Add to cart</button>
            </td>
        </tr>

    </table>
</form>
<tags:minicart count="3"/>
</tags:master>

<footer>
    <tags:footer/>
</footer>
</html>
