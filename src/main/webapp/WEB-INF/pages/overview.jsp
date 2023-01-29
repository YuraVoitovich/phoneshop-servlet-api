<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="order" type="com.es.phoneshop.model.entity.Order" scope="request"/>
<tags:master pageTitle="Order overview">
  <header>
    <tags:header/>
  </header>
  <h1>
       Order overview
  </h1>
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
    </tr>
    </thead>
    <c:forEach var="item" items="${order.items}">

      <c:set var ="currency"> ${item.product.currency} </c:set>
      <tr>
        <td>
          <img class="product-tile" src="${item.product.imageUrl}">
        </td>
        <td>
          <a href = "${pageContext.servletContext.contextPath}/products/${item.product.id}">${item.product.description}</a>
        </td>
        <td class="price">
          <p>
            ${item.quantity}
          </p>
        </td>
        <td class="price">

          <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency}"/>
        </td>
      </tr>
    </c:forEach>
    <tr>
      <td>

      </td>
      <td>
      </td>
      <td class="price">
        Total quantity:

      </td>
      <td class="price">
        <p>
            ${order.totalQuantity}
        </p>
      </td>
    </tr>

    <tr>
      <td>

      </td>
      <td>
      </td>
      <td class="price">
        Delivery cost:

      </td>
      <td class="price">
        <p>
          <fmt:formatNumber value="${order.deliveryCost}" type="currency" currencySymbol=" ${currency}"/>
        </p>
      </td>
    </tr>

    <tr>
      <td>

      </td>
      <td>
      </td>
      <td class="price">
        Subtotal:
      </td>
      <td class="price">
        <p>
          <fmt:formatNumber value="${order.subTotal}" type="currency" currencySymbol=" ${currency}"/>
        </p>
      </td>
    </tr>


  </table>

  <h2>Enter your details</h2>

  <table>
    <tags:orderFormRow fieldName="First name" fieldValue="${order.firstName}"/>
    <tags:orderFormRow fieldName="Last name" fieldValue="${order.lastName}"/>
    <tags:orderFormRow fieldName="Phone" fieldValue="${order.phone}"/>
    <tags:orderFormRow fieldName="Delivery date" fieldValue="${order.deliveryDate}"/>
    <tags:orderFormRow fieldName="Delivery address" fieldValue="${order.deliveryAddress}"/>
    <tags:orderFormRow fieldName="Payment method" fieldValue="${order.paymentMethod}"/>
  </table>

</tags:master>

<footer>
  <tags:footer/>
</footer>