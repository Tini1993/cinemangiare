

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

