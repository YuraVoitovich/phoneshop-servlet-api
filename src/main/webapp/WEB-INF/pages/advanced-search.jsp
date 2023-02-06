<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page import = "com.es.phoneshop.service.impl.RecentlyViewedServiceImpl"%>
<%@ page import="com.es.phoneshop.model.entity.Product" %>
<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">

  <header>
    <tags:header/>
  </header>
  <p>
    Advanced Search
  </p>

  <form method="post" action="${pageContext.servletContext.contextPath}/products/advanced-search">
    <p>
      ${param.method}
      ${param.query}wefwef
    Description <input name="description" value="${param.description}">
      <select name="searchDescriptionType" value="${param.method}">
      <c:forEach var="method" items="${methods}">
        <option>${method}</option>
      </c:forEach>
    </select>
    </p>
    <p>
    Min price <input name="minPrice" value="${not empty param.minPrice ? param.minPrice : "0"}">
      <c:if test = "${not empty messages['minPriceString']}">
    <div class="error-message">
      ${messages['minPriceString']}
    </div>
    </c:if>
    </p>
    <p>
    Max price <input name="maxPrice" value="${not empty param.maxPrice ? param.maxPrice : "0"}">
      <c:if test = "${not empty messages['minPriceString']}">
    <div class="error-message">
        ${messages['maxPriceString']}
    </div>
    </c:if>
    </p>

    <button >Search</button>
  </form>
  <c:if test="${ not empty products}">
  <form method="post" id="add">

  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>Description

        </td>
        <td class="price">
          Quantity
        </td>
        <td class="price">
          Price

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
        <td>
          <fmt:formatNumber value="${param.quantity}" var="quantity"/>
          <p>
            ${param.savedQuantity}
          </p>
          <input class="price" name="quantity" value=${empty param.message ? "1" :
                  (product.id eq param.productId) ? param.savedQuantity : "1"}>
          <input type="hidden" name="productId" value="${product.id}">
          <c:if test = "${not empty param.message and param.message != 'success' and param.productId eq product.id}">
            <div class="error-message">
                ${param.message}
            </div>
          </c:if>
          <c:if test = "${param.message eq 'success' and param.productId eq product.id}" >
            <div class="success-message">
              Product added to cart successfully
            </div>
          </c:if>
        </td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
        <td>
          <button form="add" formaction="${pageContext.servletContext.contextPath}/products/add?productId=${product.id}">
            Add to cart
          </button>
        </td>
      </tr>
    </c:forEach>

  </table>
  </form>
  </c:if>

</tags:master>
<footer>
  <tags:footer/>
</footer>