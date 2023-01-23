<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page import = "com.es.phoneshop.service.impl.RecentlyViewedServiceImpl"%>
<%@ page import="com.es.phoneshop.model.entity.Product" %>
<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="RecentlyViewedServiceImplRecentlyViewed" type="java.util.LinkedList" scope="request"/>
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
  <form method="post" id="add">
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>Description
          <tags:sort sortfield="DESCRIPTION" order="ASC"/>
          <tags:sort sortfield="DESCRIPTION" order="DESC"/>
        </td>
        <td class="price">
          Quantity
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
        <td>
          <fmt:formatNumber value="${param.quantity}" var="quantity"/>
          <input class="price" name="quantity" value=${empty param.message ? "1" :
          product.id eq param.productId ? param.savedQuantity : "1"}>
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
  <tags:recentlyViewed/>
  <tags:minicart count="3"/>
</tags:master>
<footer>
  <tags:footer/>
</footer>