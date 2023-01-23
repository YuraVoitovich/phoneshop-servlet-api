<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="count" required="true" %>

<p>
    MiniCart:
</p>
<jsp:include page="/cart/minicart?count=${count}"/>

<form method="get" action="${pageContext.request.contextPath}/cart">
    <button> Open cart </button>
</form>