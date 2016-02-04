<!DOCTYPE html>
<html lang="it">
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <%@page import="java.util.*"%>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>

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

            <div class="col-xs-12 col-md-3">


            </div>

            <div class="col-xs-12 col-md-6">
                <div class="row">
                    <div class="page-header">
                        <h1>Prezzi biglietto spettacolo </h1>
                    </div>

                </div>
                <div class="row">
                    <div class="col-xs-12 col-md-6">  

                        <div class="list-group">
                            <c:forEach items="${ShowPrice}" var="price">   
                                <button class="list-group-item " type="button"  value="${price.prezzo}">${price.tipo}</button>                       


                            </c:forEach>
                        </div>

                    </div>



                    <div class="col-xs-12 col-md-5">
                        <div class="panel panel-success">
                            <h3 class="panel-title">Biglietto</h3>
                            <div class="panel-body">
                                <div class="well well-lg col-centered"><h1><div class="valore center-block">└[ ∵ ]┘</div></h1></div>
                            </div>    
                        </div> 
                    </div>
                </div>
            </div>
        </div>









        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="js/bootstrap.min.js"></script>
        <script>
            $('.list-group-item').click(function () {
                var num = $(this).val();
                $('.valore').text(num + "€");
            });
        </script>
    </body>

</html>
