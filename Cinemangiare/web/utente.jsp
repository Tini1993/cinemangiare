
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">

    <head>
        <%@ include file="headDefaultContent.jsp" %>
        <!-- External plug-in-->
        <link rel="stylesheet" href="css/jquery.sliderTabs.min.css">
        <link href="http://netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
    </head>

    <body>
        <%@ include file="navbar.jsp" %>

        <div class="container" id="allContent">
            <div class="row">
                <div class="col-sm-offset-3 col-sm-6">
                    <h1>Riepilogo acquisti</h1>

                    <c:forEach items="${prenotazioni}" var="ticketInfo" varStatus="loop">

                        <ul class="list-group">
                            <li class="list-group-item">
                                <span class="badge">${ticketInfo.film}</span>
                                Titolo film
                            </li>
                            <li class="list-group-item">
                                <span class="badge">${ticketInfo.data}</span>
                                Data
                            </li>                           
                            <li class="list-group-item">
                                <span class="badge">${ticketInfo.sala}</span>
                                Posto
                            </li>
                            <li class="list-group-item">
                                <span class="badge">${ticketInfo.posto}</span>
                                Posto
                            </li>
                             <li class="list-group-item">
                                <span class="badge">${ticketInfo.price}</span>
                                Prezzo
                            </li>
                        </ul>
                        <br>
                        <br>
                    </c:forEach>

                </div>
            </div>
        </div>


        <!-- MODALS! -->
        <%@ include file="registrationModal.jsp" %>
        <%@ include file="loginModal.jsp" %>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="js/bootstrap.min.js"></script>

        <script src="js/jquery.sliderTabs.min.js"></script>

        <script src="js/app.js">
        </script>
        <script>
            $(document).ready(function () {
                $("#inputPassword").keyup(checkPasswordMatch);
                $("#inputPasswordAgain").keyup(checkPasswordMatch);

                $('.step').each(function (index, element) {
                    // element == this
                    $(element).not('.active').addClass('done');
                    $('.done').html('<i class="icon-ok"></i>');
                    if ($(this).is('.active')) {
                        return false;
                    }
                });

            });
        </script>

    </body>

</html>