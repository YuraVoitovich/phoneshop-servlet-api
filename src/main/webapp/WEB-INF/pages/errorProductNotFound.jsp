<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Error">
  <header>
    <tags:header/>
  </header>
  <h1>
      ${pageContext.request.getAttribute("javax.servlet.error.exception").getMessage()}
  </h1>
</tags:master>

<footer>
  <tags:footer/>
</footer>