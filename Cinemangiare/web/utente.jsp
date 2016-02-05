<%-- 
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

        <div class="container" id="allContent">
            <div class="row">
                <div class="col-sm-offset-3 col-sm-6">
                    
                    <h1>Riepilogo acquisti</h1>
                    <c:if test="${empty prenotazioni}">
                        <p> Non hai ancora fatto nessun acquisto :( </p>  
                        <p>Credito residuo: <c:out value="${user.credito}"/></p>
                    </c:if>

                    <c:if test="${! empty prenotazioni}">
                        <p> Credito residuo: <c:out value="${user.credito}"/> </p>
                        <table class="table table-striped">
                            <tr> 
                                <c:forEach items="${prenotazioni}" var="info">
                                <tr>
                                    <td> Data/Ora: ${info.data_ora_prenotazione} Titolo: ${info.titolo}</td>    
                                </tr>
                                </c:forEach>
                            </tr>
                        </table>
                    </c:if>

                </div>
            </div>
        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="js/bootstrap.min.js"></script>

    </body>
</html>