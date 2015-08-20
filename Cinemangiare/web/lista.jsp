<%-- 
    Document   : lista
    Created on : 13-ago-2015, 19.27.31
    Author     : Mattia
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <ol>
            <c:forEach items="${Films}" var="film">
                    <li>${film.titolo}</li>            
            </c:forEach>
        </ol>
    </body>
</html>
