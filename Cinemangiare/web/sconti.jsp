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

    <div class="container">
        <div class="row">



        </div>

        <div class="row">
            <div class="col-xs-12 col-md-1"></div>
            <div class="col-xs-12 col-md-10"> 
                <div class="page-header">
                    <h2>Riepilogo prenotazione </h2>
                </div>

            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 col-md-1"></div>
            <div class="col-xs-12 col-md-2"> 
                <div class="thumbnail">
                    <p>Codice spettacolo: ${idShow} </p>
                    <p>Sala: ${idHall} </p>
                    <p>Titolo film: ${FilmSel.titolo} </p>
                    <p>Numero biglietti: ${numBiglietti} </p>

                </div>
            </div>
            <div class="col-xs-12 col-md-8">
                <div class="row">
                    <div class="col-xs-12 col-md-8">
                        <p>Segli il biglietto e poi la categoria</p>

                        <br>

                        <form id="form" action="PrenotazioneServlet" method="post">
                            <%-- I campi di tipo hidden servono per passare i valori necessari per delle query alla servlet successiva --%>
                            <input id="stringaPosti_" type="hidden" name="stringaPosti" value=""/>
                            <input id="email_" type="hidden" name="email" value=""/>
                            <input id="idShow_" type="hidden" name="idShow" value=""/>
                            <input id="idHall_" type="hidden" name="idHall" value=""/>
                    </div>
                </div>
                <%-- Inizzializzo un contatore (stile ciclo for) --%>
                <c:set var="count" value="0" scope="page" />
                <div class="nav nav-pills nav-stacked">
                    <%-- 'biglietti' è una arraylist passata dalla servlet precedente, contenente gli id dei posti che si vuole prenotare --%>
                    <c:forEach items="${biglietti}" var="biglietti">
                        <div class="row">
                            <div class="col-xs-12 col-md-4">
                                <button href="#" class="list-group-item" id="${biglietti.id_posto}">

                                    Posto${biglietti.id_posto} </button> 
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-md-10">
                                <div class="${biglietti.id_posto} hidden"> 




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
                                        <label class="radio-inline"><input type="radio" name="${count}" value="${prezzi.tipo}" rel ="${prezzi.prezzo}"required>${prezzi.tipo}</label>

                                    </c:forEach> 
                                    <c:set var="count" value="${count + 1}" scope="page"/> <%-- count++; --%>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <a href="#" class="btn btn-success" onclick="foo()">Procedi
                    Totale: <span id="euro"> <b>€ <span id="total">0</span></b> </span>
                </a>
                </form>



            </div>
        </div>






    </div>


    <%-- Sarebbe bello fare uno script che ti fa la somma dei prezzi mentre clicchi i vari radiobutton --%>
    <%-- ricalcolando la somma in tempo reale e facendola vedere in sta pagina da qualche parte --%>
    <%-- Bisognerebbe usare la variabile ${prezzi.prezzo} immagino --%>
    <%-- e comunque sarebbe solo una cosa comoda per l'utente, alla servlet successiva non serve --%>

    <script> <%-- Funzione che serve per rinominare le variabili scritte sopra, indispensabile per passare effettivamente i valori alla servlet successiva --%>
                    function foo() {
                        var Ngruppi = "${count}";
                        if ($("input[type=radio]:checked").length < Ngruppi)
{
    alert ("Seleziona almeno una tipologia per biglietto");
    return false;
}
else{                        var idShow = "${idShow}";
                        var idHall = "${idHall}";
                        var email = "${user.email}";
                        var stringaPosti = "${stringaPosti}"

                        console.log(idShow); //debug
                        console.log(idHall); //debug
                        console.log(email); //debug
                        console.log(stringaPosti); //debug

                        $("#idShow_").val(idShow);
                        $("#idHall_").val(idHall);
                        $("#email_").val(email);
                        $("#stringaPosti_").val(stringaPosti);

                        form.submit();
                    }}
    </script>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>

    <script>
                    $('.list-group-item').click(function () {
                        var num = $(this).attr('id');
                        $('.' + num).toggleClass('hidden');
                    });
    </script>
    <script>
        $(document).ready(function () {
            function recalculate() {
                var sum = 0;

                $("input[type=radio]:checked").each(function () {
                    sum += parseInt($(this).attr('rel'));
                });

                $("#total").html(sum);
            }

            $("input[type=radio]").change(function () {
                recalculate();
            });
        });
    </script>
</html>
