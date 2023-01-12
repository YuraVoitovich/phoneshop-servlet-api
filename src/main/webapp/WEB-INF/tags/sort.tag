<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortfield" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="?sortfield=${sortfield}&order=${order}&query=${param.query}"
   style= "${sortfield eq param.sortfield and order eq param.order
? 'font-weight: bold' : ''}">${order}</a>