<%-- 
    Document   : posti
    Created on : 26-gen-2016, 16.18.06
    Author     : Mattia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page import="Bean.Film"%>
<%@page import="Bean.Posto"%>

<!DOCTYPE html>
<html>

    <head>
        <link rel="stylesheet" href="css/jquery.seat-charts.css">
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
            <div class="row">
                <div class="col-xs-12 col-md-2"></div>
                <div class="col-xs-12 col-md-4">
                    <div class="page-header">
                        <h1>Seleziona i posti</h1>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12 col-md-2"></div>
                <div class="col-xs-12 col-md-4">
                    <div class="front-indicator col-md-offset-3 ">Schermo</div>
                    <div id="seat-map">
                        
                                                  
                    </div> 

                </div>
                <div class="col-xs-12 col-md-4">
                    <div id="legend"></div>
                </div>
            </div>

            <br>
            <div class="row">
                <div class="col-xs-12 col-md-2"></div>

                <div class="col-xs-12 col-md-8">
                    <div class="page-header">
  <h2>Riepilogo <small>posti selezionati:  <span id="counter">0</span></small></h2>
</div>
                    <%--<p> ${ShowSel.xx}</p>--%>

                    <ol class="list-group booking-details" id="selected-seats">
                        <div class="row">
                        <a href="#" class="btn btn-success" id="createJSON">Procedi
                        Totale: <span id="euro"> <b>€ <span id="total">0</span></b> </span>
                        </a>
                            </div>
                            <div class="row">
                                 <div class="col-xs-12" style="height:10px;"></div>
                        <form id="form_" action="CheckSeatAvailabilityServlet" method="post">
                            <input id="posti_" type="hidden" name="posti" value=""/>
                            <input id="idHall_" type="hidden" name="idHall" value=""/>
                            <input id="idShow_" type="hidden" name="idShow" value=""/>

                        </form>
                            </div>
                    </ol>
                </div>
            </div>
        </div>
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="js/jquery.seat-charts.js"></script>
        <script>
            <c:if test="${ListPosti != null}">
            var posti = ${ListPosti};
            </c:if>
            <c:if test="${ListPosti == null}">
            var posti = ['_'];
            </c:if>
        </script>
        <script>
            var DC = '';
            //var posti=['sss_sss','sss_uus'];
            var firstSeatLabel = 1;
            var $cart = $('#selected-seats');
            var sc;
            $(document).ready(function () {


                var $counter = $('#counter'),
                        $total = $('#total');
                sc = $('#seat-map').seatCharts({
                    map: posti,
                    seats: {
                        s: {
                            price: 8,
                            classes: 'economy-class',
                            category: 'Standard'
                        }

                    },
                    naming: {
                        top: false,
                        getLabel: function (character, row, column) {
                            return firstSeatLabel++;
                        },
                    },
                    legend: {
                        node: $('#legend'),
                        items: [
                            ['s', 'available', 'Standard'],
                            ['u', 'unavailable', 'Non disponibile'],
                            ['a', 'selected', 'Selezionato']
                        ]
                    },
                    click: function () {
                        if (this.status() == 'available') {
                            //let's create a new <li> which we'll add to the cart items
                            DC += this.settings.label + ' ';
                            $('<li class="list-group-item"><span class="badge"> <b>' + this.data().price +'€ </b> <a href="#" class="cancel-cart-item"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a> </span> Posto ' + this.data().category +'  N°' + this.settings.label + '</li>')
                                    .attr('id', 'cart-item-' + this.settings.id)
                                    .data('seatId', this.settings.id)
                                    .appendTo($cart);



                            /*
                             * Lets update the counter and total
                             *
                             * .find function will not find the current seat, because it will change its stauts only after return
                             * 'selected'. This is why we have to add 1 to the length and the current seat price to the total.
                             */
                            $counter.text(sc.find('selected').length + 1);
                            $total.text(recalculateTotal(sc) + this.data().price);

                            return 'selected';
                        } else if (this.status() == 'selected') {
                            //update the counter
                            $counter.text(sc.find('selected').length - 1);
                            //and total
                            $total.text(recalculateTotal(sc) - this.data().price);

                            //remove the item from our cart
                            $('#cart-item-' + this.settings.id).remove();

                            //seat has been vacated
                            return 'available';
                        } else if (this.status() == 'unavailable') {
                            //seat has been already booked
                            return 'unavailable';
                        } else {
                            return this.style();
                        }
                    }
                });

                //this will handle "[cancel]" link clicks
                $('#selected-seats').on('click', '.cancel-cart-item', function () {
                    //let's just trigger Click event on the appropriate seat, so we don't have to repeat the logic here
                    sc.get($(this).parents('li:first').data('seatId')).click();
                });

                //let's pretend some seats have already been booked
                //Make all available 'u' seats unavailable
                sc.find('u.available').status('unavailable');

            });

            function recalculateTotal(sc) {
                var total = 0;

                //basically find every selected seat and sum its price
                sc.find('selected').each(function () {
                    total += this.data().price;
                });

                return total;
            }

        </script>
        <script>
            var idHall = "${idHall}";
            var idShow = "${idShow}";

            $("#createJSON").on('click', function () {

                console.log(DC);
                console.log(idHall);
                console.log(idShow);

                $("#posti_").val(DC);
                $("#idHall_").val(idHall);
                $("#idShow_").val(idShow);

                form_.submit();
            }
            );
        </script>
    </div>
</div>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>
