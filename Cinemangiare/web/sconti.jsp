<%-- 
    Author     : Mattia
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%--se inculudo sta roba da errore--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page import="Bean.Film"%>
<%@page import="Bean.Spettacolo"%>
<%@page import="Bean.Price"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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

    RIEPILOGO <br>    
    Codice spettacolo: <p> ${idShow} </p>
    Sala: <p> ${idHall} </p>
    Titolo film: <p> ${FilmSel.titolo} </p>
    Numero biglietti: <p> ${numBiglietti} </p>

    Scegli il tipo di biglietto per ogni biglietto: <br>

    <form id="form" action="PrenotazioneServlet" method="post">
        <input id="stringaPosti_" type="hidden" name="stringaPosti" value=""/>
        <input id="email_" type="hidden" name="email" value=""/>
        <input id="idShow_" type="hidden" name="idShow" value=""/>
        <c:set var="count" value="0" scope="page" />

        <c:forEach items="${biglietti}" var="biglietti">
            <c:set var="count" value="${count + 1}" scope="page"/> 

            <br> Biglietto posto ${biglietti.id_posto}: <br>

            <c:forEach items="${prezzi}" var="prezzi">             
                <input type="radio" name="${count}" value="${prezzi.tipo}"  required> ${prezzi.tipo}  
            </c:forEach> 

        </c:forEach>
        <p><input type="button" value="Procedi" onclick="foo()"/></p>
    </form>

    <script>
        function foo() {
            var idShow = "${idShow}";
            var email = "${user.email}";
            var stringaPosti = "${stringaPosti}"

            console.log(idShow);
            console.log(email);
            console.log(stringaPosti);

            $("#idShow_").val(idShow);
            $("#email_").val(email);
            $("#stringaPosti_").val(stringaPosti);

            form.submit();
        }
    </script>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</html>
