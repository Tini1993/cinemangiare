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
                    <div class="page-header">
                        <h1>Riepilogo acquisti</h1>
                    </div>
                    
                    <c:if test="${empty prenotazioni}">
                        <div class="alert alert-warning" role="alert"> Nessuna Prenotazione Effettuata</div>
                        

                    </c:if>
<div class="alert alert-info" role="alert">Credito residuo: <c:out value="${user.credito}"/></div>
                    <c:if test="${! empty prenotazioni}">
                        <h2>Prenotazioni</h2>
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>Data/Ora prenotazione</th>
                                        <th>Titolo Film</th>
                                        <th>Numero prenotazione</th>
                                        <th>Numero spettacolo</th>
                                        <th> Sala </th>
                                        <th> Posto </th>
                                        <th> Prezzo pagato </th>
                                        <th> Data/ora spettacolo </th>
                                        

                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${prenotazioni}" var="info">  
                                        <tr>
                                            <td>${info.data_ora_prenotazione}</td>
                                            <td>${info.titolo}</td>
                                            <td>${info.id_prenotazione}</td>
                                            <td>${info.id_spettacolo}</td>
                                            <td>${info.sala}</td>
                                            <td>${info.id_posto}</td>
                                            <td>${info.id_prezzo}</td>
                                            <td>${info.data_ora_spettacolo}</td>

                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            </div>
                   
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