<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="productPriceHistory" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="currency" type="java.util.Currency" scope="request"/>
<jsp:useBean id="name" type="java.lang.String" scope="request"/>
<tags:master pageTitle="Product List">
  <header>
    <tags:header/>
  </header>
  <p>
    ${name}
  </p>
  <table>
    <thead>
    <tr>
      <td>
        Date
      </td>
      <td>
        Price
      </td>
    </tr>
    </thead>

    <c:forEach var="product" items="${productPriceHistory}">
      <tr>
        <td>
          ${product.date}
        </td>
        <td>
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>

<footer>
  <tags:footer/>
</footer>