<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="CartServiceImplCart" type="com.es.phoneshop.model.entity.Cart" scope="request"/>
<tags:master pageTitle="Cart">

  <header>
    <tags:header/>
  </header>
  <h1>
    Cart
  </h1>

  <c:if test = "${not empty param.message and param.message != 'success' and param.message != 'Product deleted successfully'}">
    <div class="error-message">
        ${param.message}
    </div>
  </c:if>
  <c:if test = "${param.message eq 'success'}">
    <div class="success-message">
      Cart updated successfully
    </div>
  </c:if>
  <c:if test = "${param.message eq 'Product deleted successfully'}">
    <div class="success-message">
      Item deleted successfully
    </div>
  </c:if>
  <form id="checkout"></form>
  <form method="post" action="${pageContext.servletContext.contextPath}/cart">
    <c:if test="${not empty CartServiceImplCart.items}">
      <button>update</button>
      <button form="checkout" formaction="${pageContext.servletContext.contextPath}/checkout" formmethod="get">
        checkout
      </button>
    </c:if>

  <table>
    <thead>
    <tr>
      <td>Image</td>
      <td>
        Description
      </td>
      <td>
        Count
      </td>
      <td class="price">
        Price
      </td>
      <td>

      </td>
    </tr>
    </thead>

    <c:forEach var="item" items="${CartServiceImplCart.items}">
      <tr>
        <td>
          <img class="product-tile" src="${item.product.imageUrl}">
        </td>
        <td>
          <a href = "${pageContext.servletContext.contextPath}/products/${item.product.id}">${item.product.description}</a>
        </td>
        <td class="price">
          <fmt:formatNumber value="${item.quantity}" var="quantity"/>
          <input name="quantity" value="${item.quantity}" class="price"/>
          <input type="hidden" name="productId" value="${item.product.id}"/>
          <c:if test = "${not empty messages[item.product.id] and messages[item.product.id] != 'success'}">
            <div class="error-message">
                ${messages[item.product.id]}
            </div>
          </c:if>
          <c:if test = "${messages[item.product.id] eq 'success'}">
            <div class="success-message">
              Product quantity changed successfully
            </div>
          </c:if>
        </td>
        <td class="price">
          <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/>
        </td>
      <td>
        <button form="delete" formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${item.product.id}">
          delete
        </button>
      </td>
      </tr>
    </c:forEach>
  </table>
  </form>
  <form id = "delete" method="post"></form>
</tags:master>

<footer>
  <tags:footer/>
</footer>