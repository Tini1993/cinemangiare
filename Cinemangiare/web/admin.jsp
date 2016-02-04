<%-- 
    Document   : admin
    Created on : 1-feb-2016, 11.49.56
    Author     : jack
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Cinema Limoni</title>

        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="style.css">
    </head>
    
        
        <%@ include file="navbar.jsp" %>
        
        <table class="table table-striped">
        <c:forEach items="${ShowSel}" var="show">   
            <tr>    
                <td> INCASSO PER SPETTAOLO </td> 
                <td>SPETTACOLO: ${show.id_spettacolo} SALA: ${show.id_sala} POSTI PRENOTATI: ${show.posti} DATA: ${show.data_ora} INCASSI: ${show.prezzo} € </td>
            </tr>
        </c:forEach>
            <c:forEach items="${Incassi}" var="incassi">   
            <tr>
                <td> INCASSO PER FILM </td>
                <td>TITOLO: ${incassi.titolo} INCASSI: ${incassi.incassi} €</td>
            </tr>
        </c:forEach>
        <c:forEach items="${Utente}" var="utente">   
            <tr>
                <td> INCASSO PER UTENTE </td>
                <td>UTENTE: ${utente.email} INCASSI: ${utente.paga} € </td>
            </tr>
        </c:forEach>
        </table>
        
    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</html>
