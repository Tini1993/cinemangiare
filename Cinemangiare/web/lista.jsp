<%-- 
    Document   : lista
    Created on : 13-ago-2015, 19.27.31
    Author     : Mattia
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%--se inculudo sta roba da errore--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="it">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Cinema Limoni</title>

        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="style.css">

    </head>

    <body>
        
        <%@ include file="navbar.jsp" %>

        <div class="container">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>titolo</th>
                    </tr>
                </thead>
                <tbody>

                    <c:forEach items="${Films}" var="film">
                        <tr>

                            <td><a href="FilmServlet?titolo=${film.titolo}">${film.titolo}</td>
                            <!--<td> <button class="btn btn-success">Prenota</button></a> -->

                        </tr>
                    </c:forEach>

                </tbody>
            </table>


        </div>
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="js/bootstrap.min.js"></script>
    </body>

</html>
