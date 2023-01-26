<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="RecentlyViewedServiceImplRecentlyViewed" type="java.util.LinkedList" scope="request"/>

<table>
  <c:forEach var="product" items="${RecentlyViewedServiceImplRecentlyViewed}">
    <td>
      <img class="product-tile" src="${product.imageUrl}">
      <p>
        <a href = "${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
      </p>
      <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
    </td>
  </c:forEach>
</table>