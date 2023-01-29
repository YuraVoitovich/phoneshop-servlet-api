<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="order" type="com.es.phoneshop.model.entity.Order" scope="request"/>
<tags:master pageTitle="Checkout">
  <header>
    <tags:header/>
  </header>
  <h1>
    Checkout
  </h1>
  <c:if test = "${not empty messages}">
    <div class="error-message">
        ${message}
    </div>
  </c:if>
  <c:if test = "${empty messages}">
    <div class="success-message">
        ${param.message}
    </div>
  </c:if>
  <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
    <button>update</button>

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
    <tr>
      <td>
        First name
        <span style="color:red">*</span>
      </td>
      <td>
        <tags:orderFormField fieldName="firstName" fieldValue="${param.firstName}" errorValue="${messages['firstName']}"/>
      </td>
    </tr>

    <tr>
      <td>
        Last name
        <span style="color:red">*</span>
      </td>
      <td>
        <tags:orderFormField fieldName="lastName" fieldValue="${param.lastName}" errorValue="${messages['lastName']}"/>
      </td>
    </tr>

    <tr>
      <td>
        Phone
        <span style="color:red">*</span>
      </td>
      <td>
        <tags:orderFormField fieldName="phone" fieldValue="${param.phone}" errorValue="${messages['phone']}"/>
      </td>
    </tr>

    <tr>
      <td>
        Delivery date
        <span style="color:red">*</span>
      </td>
      <td>
        <tags:orderFormField fieldName="deliveryDate" fieldValue="${param.deliveryDate}" errorValue="${messages['deliveryDate']}"/>
      </td>
    </tr>

    <tr>
      <td>
        Delivery address
        <span style="color:red">*</span>
      </td>
      <td>
        <tags:orderFormField fieldName="deliveryAddress" fieldValue="${param.deliveryAddress}" errorValue="${messages['deliveryAddress']}"/>
      </td>
    </tr>

    <td>
      Payment method
      <span style="color:red">*</span>
    </td>
    <td>
      <c:set var = "error" value = "${messages['paymentMethod']}"/>
      <select name="paymentMethod" value="${not empty error ? param.paymentMethod : ''}">
        <c:forEach var="method" items="${paymentMethods}">
          <option>${method}</option>
        </c:forEach>
      </select>
      <c:if test = "${not empty error}">
        <div class="error-message">
            ${error}
        </div>
      </c:if>
    </td>
    </tr>

  </table>
    <button>Place order</button>
  </form>

</tags:master>

<footer>
  <tags:footer/>
</footer>