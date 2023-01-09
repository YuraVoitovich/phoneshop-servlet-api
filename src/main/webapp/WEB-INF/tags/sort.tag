<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortfield" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?sortfield=${sortfield}&order=${order}&query=${param.query}">${order}</a>