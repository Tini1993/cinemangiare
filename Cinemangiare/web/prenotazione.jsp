<%-- 
    Document   : prenotazione
    Created on : 8-feb-2016, 11.20.49
    Author     : jack
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page import="Bean.Film"%>
<%@page import="Bean.Spettacolo"%>
<%@page import="Bean.Price"%>

    <%@page import="java.util.*"%>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>

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
    
    <div class="container">
        <div class="row">
            <div class="col-xs-12 col-md-3"></div>
            <div class="col-xs-12 col-md-6 "> 
                <div class="page-header">
                    <h2>Registrazione Avvenuta Con Successo!</h2>
                </div>
                <p>Controlla la tua casella email, a breve troverai il riepilogo dell'acquisto in formato digitale:
                potrai stamparli oppure presentare direttamente il qrcode agli sportelli. </p>
                <nav>
  <ul class="pager">
    <li class="next"><a href="ListaFilmServlet">└[∵┌]└[ ∵ ]┘[┐∵]┘ <span aria-hidden="true">&rarr;</span></a></li>
  </ul>
</nav>

            </div>
        </div>
 
        
        
        
    </div>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</html>
