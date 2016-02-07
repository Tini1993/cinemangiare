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
  
    RIEPILOGO <br> <%-- ...da abbellire --%>   
    Codice spettacolo: <p> ${idShow} </p>
    Sala: <p> ${idHall} </p>
    Titolo film: <p> ${FilmSel.titolo} </p>
    Numero biglietti: <p> ${numBiglietti} </p>

    Scegli il tipo di biglietto per ogni biglietto: <br>

    <form id="form" action="PrenotazioneServlet" method="post">
        <%-- I campi di tipo hidden servono per passare i valori necessari per delle query alla servlet successiva --%>
        <input id="stringaPosti_" type="hidden" name="stringaPosti" value=""/>
        <input id="email_" type="hidden" name="email" value=""/>
        <input id="idShow_" type="hidden" name="idShow" value=""/>

        <%-- Inizzializzo un contatore (stile ciclo for) --%>
        <c:set var="count" value="0" scope="page" />

        <%-- 'biglietti' è una arraylist passata dalla servlet precedente, contenente gli id dei posti che si vuole prenotare --%>
        <c:forEach items="${biglietti}" var="biglietti">
            

            <br> Biglietto posto ${biglietti.id_posto}: <br>

            <%-- 'prezzi' è una arraylist passata dalla servlet precedente, contenente tutte le info sui prezzi reperibili dal db  --%>
            <c:forEach items="${prezzi}" var="prezzi">

                <%-- Questo è un magheggio: uso count per distinguere le varie serie di radiobutton, perchè risulterà una cosa del tipo: --%>
                <%-- <input type="radio" name="1" value="tipo1" ....--%>
                <%-- <input type="radio" name="1" value="tipo1" ....--%> <%-- FINE PRIMO CICLO FOR PIU INTERNO --%>
                <%-- <input type="radio" name="2" value="tipo2" ....--%>
                <%-- <input type="radio" name="2" value="tipo2" ....--%> <%-- FINE SECONDO CICLO FOR PIU INTERNO --%>
                <%-- ..... --%>
                <%-- <input type="radio" name="N" value="tipoN" ....--%> 
                <%-- <input type="radio" name="N" value="tipoN" ....--%> <%-- FINE N-ESIMO CICLO FOR PIU INTERNO --%>
                <%-- Così per ogni numero si potrà scegliere un solo radiobutton (un radio button per name) --%>
                <%-- E alla servlet successiva gli passo il value corrispondente al radiobutton scelto --%>
                <input type="radio" name="${count}" value="${prezzi.tipo}"  required> ${prezzi.tipo}  
            </c:forEach> 
            <c:set var="count" value="${count + 1}" scope="page"/> <%-- count++; --%>
        </c:forEach>
        <p><input type="button" value="Procedi" onclick="foo()"/></p>
    </form>
        
     <%-- Sarebbe bello fare uno script che ti fa la somma dei prezzi mentre clicchi i vari radiobutton --%>
     <%-- ricalcolando la somma in tempo reale e facendola vedere in sta pagina da qualche parte --%>
     <%-- Bisognerebbe usare la variabile ${prezzi.prezzo} immagino --%>
     <%-- e comunque sarebbe solo una cosa comoda per l'utente, alla servlet successiva non serve --%>

    <script> <%-- Funzione che serve per rinominare le variabili scritte sopra, indispensabile per passare effettivamente i valori alla servlet successiva --%>
            function foo() {
            var idShow = "${idShow}";
            var email = "${user.email}";
            var stringaPosti = "${stringaPosti}"

                    console.log(idShow); //debug
                    console.log(email); //debug
                    console.log(stringaPosti); //debug

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
