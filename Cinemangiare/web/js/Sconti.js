                    
//Nascondi/visualizza i radio del posto
$('.list-group-item').click(function () {
                        var num = $(this).attr('id');
                        $('.' + num).toggleClass('hidden');
                        $(this).toggleClass('active');
                    });
//Ricalcola il totale ogni volta che i radio vengono selezioanti o cambiati                   
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