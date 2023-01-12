<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Product List">

  <header>
    <tags:header/>
  </header>
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
          <tags:sort sortfield="DESCRIPTION" order="ASC"/>
          <tags:sort sortfield="DESCRIPTION" order="DESC"/>
        </td>
        <td class="price">
          Price
          <tags:sort sortfield="PRICE" order="ASC"/>
          <tags:sort sortfield="PRICE" order="DESC"/>
        </td>
      </tr>
    </thead>

    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="${product.imageUrl}">
        </td>
        <td>
          <a href = "${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
        </td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>

<footer>
  <tags:footer/>
</footer>