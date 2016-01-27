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

    <body>
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="http://www.google.com">
                        <img alt="Brand" src="img/icona.png">
                    </a>
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li><a href="index.html">Home <span class="sr-only">(current)</span></a></li>
                        <li  class="active"><a href="films.html">I Film </a></li>
                        <li><a href="prices.html">Prezzi </a></li>
                        <li><a href="contact.html">Dove Siamo </a></li>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="register.html">Registrati</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><b>Login</b> <span class="caret"></span></a>
                            <ul id="login-dp" class="dropdown-menu">
                                <li>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <form class="form" role="form" method="post" action="LoginServlet" accept-charset="UTF-8" id="login-nav">
                                                <div class="form-group">
                                                    <label class="sr-only" for="exampleInputEmail2">Indirizzo Email</label>
                                                    <input type="text" name="username" class="form-control" id="exampleInputEmail2" placeholder="Email address" required>
                                                </div>
                                                <div class="form-group">
                                                    <label class="sr-only" for="exampleInputPassword2">Password</label>
                                                    <input type="password" name="password" class="form-control" id="exampleInputPassword2" placeholder="Password" required>
                                                    <div class="help-block text-right"><a href="">Password dimenticata ?</a></div>
                                                </div>
                                                <div class="form-group">
                                                    <button type="submit" class="btn btn-primary btn-block">Login</button>
                                                </div>
                                                <div class="checkbox">
                                                    <label>
                                                        <input type="checkbox"> tienimi loggato
                                                    </label>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="bottom text-center">
                                            Nuovo ? <a href="register.html"><b>Registrati</b></a>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
                <!-- /.navbar-collapse -->
            </div>
        </nav>

        <div class="container modContainer" id="allContent">

            <div class="row">
                <div class="col-sm-8 ">
                    <div id="steps">
                        <div class="step" data-desc="Data e ora">1</div>
                        <div class="step active" data-desc="Posizine posti">2</div>
                        <div class="step " data-desc="Sconti">3</div>
                        <div class="step" data-desc="Info pagameno">4</div>
                    </div>
                    <h1>SELEZIONA I POSTI CHE TI INTERESSANO</h1>
                    <div class="row">
                        <div class="col-sm-8" id="seat-map">

                            <div class="front-indicator">Schermo</div>
                            <br>                              
                        </div>                             
                    </div>
                    <br>
                    <div id="legend"></div>
                </div>


                <div class="col-sm-4">
                    <h1>Riepilogo prenotazione</h1>
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">${Movie.title}</h3>
                        </div>
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item">
                                    <span class="badge" id="selData">${CurrentShow.dayFormatted}</span>
                                    Data
                                </li>
                                <li class="list-group-item">
                                    <span class="badge" id="selOra">${CurrentShow.timeFormatted}</span>
                                    Ora
                                </li>
                                <li class="list-group-item">
                                    <span class="badge" id="selSala">${CurrentShow.idHall}</span>
                                    Sala
                                </li>
                            </ul>
                            <ol class="list-group booking-details" id="selected-seats">
                                <h3> Posti selezionati <span id="counter">0</span>):</h3>


                            </ol>
                        </div>
                        <div class="panel-footer">
                            Totale: <span id="euro"> <b>€ <span id="total">0</span></b> </span>
                        </div>
                    </div>
                    <a href="#" class="btn btn-success" id="createJSON">Procedi</a>
                </div>
            </div>
        </div>

        <script src="js/jquery.seat-charts.js"></script>
        <script>
            <c:if test="${SeatString != null}">
            var posti = ${SeatString};
            </c:if>
            <c:if test="${SeatString == null}">
            var posti = ['_'];
            </c:if>
        </script>
        <script>
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
                            price: 10,
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
                            ['u', 'unavailable', 'Non disponibile']
                        ]
                    },
                    click: function () {
                        if (this.status() == 'available') {
                            //let's create a new <li> which we'll add to the cart items
                            $('<li class="list-group-item"><span class="badge"> # ' + this.settings.label + ': <b>€' + this.data().price + '</b> <a href="#" class="cancel-cart-item">[cancella]</a></span> Posto ' + this.data().category + '</li>')
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
            var selSeat = [];
            var postiSel = "";
            var showId = "${CurrentShow.idShow}";
            var hallId = "${CurrentShow.idHall}";
            $("#createJSON").on('click', function () {
                //console.log($("#seat-map").find('div.selected').text());
                $("div.selected").each(function () {

                    selSeat.push($(this).attr('id'));
                });
                postiString = selSeat.join(",");
                console.log(postiString);
                post('CheckSeatAvailabilityServlet', {postiString: postiString, showId: showId, hallId: hallId});
            }
            );
        </script>

    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</body>
</html>
