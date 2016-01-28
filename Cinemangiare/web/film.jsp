<%-- 
    Document   : film
    Created on : 4-set-2015, 4-set-2015 20.21.16
    Author     : Andrea
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%--se inculudo sta roba da errore--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page import="Bean.Film"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <c:forEach items="${Filmsel}" var="film">
        <tr>
            <td> ${film.titolo}</td>
            <td><p>ccdcdcd</p></td>
        </tr>
    </c:forEach>
    <c:forEach items="${Films}" var="film">
        <tr>
            <td> ${film.titolo}</td>
            <td> ${film.durata}</td>
        </tr>
    </c:forEach>

</body>
</html>
