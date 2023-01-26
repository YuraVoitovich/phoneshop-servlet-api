<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="cartItems" type="java.util.ArrayList" scope="request"/>

<table>
  <c:forEach var="cartItem" items="${cartItems}">
    <td>
      <img class="product-tile" src="${cartItem.product.imageUrl}">
      <p>
        <a href = "${pageContext.servletContext.contextPath}/products/${cartItem.product.id}">${cartItem.product.description}</a>
      </p>
      <p>
        count: ${cartItem.quantity}
      </p>
      <fmt:formatNumber value="${cartItem.product.price}" type="currency" currencySymbol="${cartItem.product.currency.symbol}"/>
    </td>
  </c:forEach>
</table>
