<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="fieldName" required="true"%>
<%@ attribute name="fieldValue" required="true"%>
<%@ attribute name="errorValue" required="true"%>

<c:set var = "error" value = "${errorValue}"/>
<input name="${fieldName}" value="${fieldValue}"/>
<c:if test = "${not empty error}">
    <div class="error-message">
            ${error}
    </div>
</c:if>