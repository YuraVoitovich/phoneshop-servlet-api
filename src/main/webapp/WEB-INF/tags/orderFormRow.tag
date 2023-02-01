<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="fieldName" required="true"%>
<%@ attribute name="fieldValue" required="true"%>

<tr>
    <td>
        ${fieldName}
    </td>
    <td>
        ${fieldValue}
    </td>
</tr>
