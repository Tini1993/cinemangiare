<%-- 
    Document   : lista
    Created on : 13-ago-2015, 19.27.31
    Author     : Mattia
--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>ciao
        <%ResultSet results = (ResultSet)request.getAttribute("film");%>
        <table>
            <c:forEach var="film" items="${film}">
                <tr>
                    <td><c:out value="${film.titolo}"</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
