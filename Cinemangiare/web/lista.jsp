<%-- 
    Document   : lista
    Created on : 13-ago-2015, 19.27.31
    Author     : Mattia
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%--se inculudo sta roba da errore--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="count" value="0" scope="page" />



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
        <link href="css/3-col-portfolio.css" rel="stylesheet">



    </head>

    <body>

        <%@ include file="navbar.jsp" %>
        <div class="container">

            <%-- ciclo for che stampa in una tabella ogni elemento (film) salvato nella variabile di sessione ${Films} --%>             
            <c:forEach items="${Films}" var="film"> <%-- è come fare:   var film = ${Films} --%>
                <c:choose>
                    <c:when test="${count== '0'}">
                        <div class="row">
                        </c:when>                           
                    </c:choose>

                    <c:set var="count" value="${count + 1}" scope="page"/>
                    <div class="col-md-3 portfolio-item">
                        <div class="thumbnail">
                            
                            <c:if test="${! empty user}">
                            <a href="FilmServlet?titolo=${film.titolo}">
                                <img class="img-responsive" src="${film.url_locandina}" alt="${film.titolo}">
                            </a>
                            </c:if>
                                
                            <c:if test="${empty user}">
                                <a href="#" onclick="Popup();">
                                <img class="img-responsive" src="${film.url_locandina}" alt="${film.titolo}">
                            </a>
                            </c:if>

                            <c:if test="${! empty user}">
                                <h3>
                                    <a class="text-uppercase center-block" href="FilmServlet?titolo=${film.titolo}">${film.titolo}</a>
                                </h3>
                            </c:if>

                            <c:if test="${empty user}">
                                <h3>
                                    <a class="text-uppercase center-block" href="#" onclick="Popup();">${film.titolo}</a>
                                </h3>
                            </c:if>
             <!--               <p class="center-block"><a href="#" class="btn btn-primary" role="button">Prenota</a> <a href="FilmServlet?titolo=${film.titolo}" class="btn btn-default" role="button">Info</a></p>-->
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${count== '4'}">
                        </div>
                        <c:set var="count" value="${count = '0'}" scope="page"/>
                    </c:when>                           
                </c:choose>


            </c:forEach>

            <c:choose>
                <c:when test="${count < '4'}">
                </div>
                <c:set var="count" value="${count = '0'}" scope="page"/>
            </c:when>                           
        </c:choose>
    </div>

    <script language="javascript">
        function Popup()
        {
            alert('Loggati per continuare!');
        }
    </script>  


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>


</body>

</html>
