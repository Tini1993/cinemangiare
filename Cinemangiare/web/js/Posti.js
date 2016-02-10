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
                            //crea un nuovo <li> che verrà aggiunto agli item del cart
                            
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



//Al momento di submittare crea la stringa DC concatenando tutti i posti selezionati
            var idHall = "${idHall}";
            var idShow = "${idShow}";
            var DC = '';
            $("#createJSON").on('click', function () {
                
                $(".selected").each(function () {
                    console.log($(this).text());
                    DC += $(this).text() + ' ';
                });

                console.log(DC);

                $("#posti_").val(DC);
                $("#idHall_").val(idHall);
                $("#idShow_").val(idShow);

                form_.submit();
            }
            );
