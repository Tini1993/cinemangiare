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

        <style>

            #map-container { height: 400px }

        </style>

    </head>

    <body>
        <%@ include file="navbar.jsp" %>

        <div class="container">
            <div class="col-xs-12" style="height:50px;"></div>
            <div class="row">
                <div id="map-outer" class="col-md-12">
                    <div id="address" class="col-md-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Indirizzo</h3>
                            </div>
                            <div class="panel-body">
                                <address>
                                    <strong>Limoni Movie Theater</strong><br>
                                    Gold Street, 701<br>
                                    99742<br>
                                    Gambel<br>
                                    Alaska, USA<br>
                                    <abbr>P:</abbr> +39 041 240 5411
                                </address>
                            </div>
                        </div>

                    </div>
                    <div id="map" class="col-md-8">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Dove Siamo</h3>
                            </div>
                            <div class="panel-body">
                                <div id="map-container"></div>
                            </div>
                        </div>
                    </div>
                </div><!-- /map-outer -->
            </div> <!-- /row -->


        </div><!-- /container -->
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <script src="http://maps.google.com/maps/api/js?sensor=false"></script>
        <script>

            function init_map() {
                var myLocation = new google.maps.LatLng(63.7768887, -171.7231638);

                var mapOptions = {
                    mapTypeId: google.maps.MapTypeId.HYBRID,
                    center: myLocation,
                    zoom: 10
                };

                var marker = new google.maps.Marker({
                    position: myLocation,
                    title: "Limoni Movie Theater"});

                var map = new google.maps.Map(document.getElementById("map-container"),
                        mapOptions);

                marker.setMap(map);

            }

            google.maps.event.addDomListener(window, 'load', init_map);

        </script>
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="js/bootstrap.min.js"></script>
        <script src="js/mappa.js"></script>
    </body>

</html>
