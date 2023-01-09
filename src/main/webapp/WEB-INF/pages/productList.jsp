<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <form>
    <input name="query" value="${param.query}">
    <button>Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>Description
          <a href="?sortfield=DESCRIPTION&order=ASC&query=${param.query}">asc</a>
          <a href="?sortfield=DESCRIPTION&order=DESC&query=${param.query}">desc</a>
        </td>
        <td class="price">
          <a href="?sortfield=PRICE&order=ASC&query=${param.query}">asc</a>
          <a href="?sortfield=PRICE&order=DESC&query=${param.query}">desc</a>
        </td>
      </tr>
    </thead>

    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="${product.imageUrl}">
        </td>
        <td>${product.description}</td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>