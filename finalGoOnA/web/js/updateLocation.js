        var timer1;
        function updateLocation(){
            if ( navigator.geolocation ) {
                function successUpdate(pos) {
                    var lat = $.session.get('latitude');
                    var lng = $.session.get('longitude');
                    //alert($path+"webresources/com.dn.entity.groupsandusers/api/editUserLocation");
                    if ((Math.abs(pos.coords.latitude-lat)<0.000099) && (Math.abs(pos.coords.longitude-lng)<0.000099))
                        {
                          //  alert("not change " + $.session.get('userid'));
                          //  alert(pos.coords.latitude+" "+pos.coords.longitude+" "+lat+" "+lng);
                            //editUserLocation($.session.get('userid'), pos.coords.latitude, pos.coords.longitude);
                        }
                    else{
                        //alert(pos.coords.latitude+" "+pos.coords.longitude);
                        editUserLocation($.session.get('userid'), pos.coords.latitude, pos.coords.longitude);
                      //  alert("change" +$.session.get('userid'));
                       // alert(pos.coords.latitude+" "+pos.coords.longitude+" "+lat+" "+lng);
                       $.session.set('latitude', pos.coords.latitude);
                        $.session.set('longitude', pos.coords.longitude);
                        
                    }
                }
                
                function fail(error) {
                //  alert("not");
                }
                // Find the users current position.  Cache the location for 5 minutes, timeout after 6 seconds
                //navigator.geolocation.getCurrentPosition(success, fail, {maximumAge: 500000, enableHighAccuracy:true, timeout: 6000});
                 timer1 = setInterval(function(){navigator.geolocation.getCurrentPosition(successUpdate, fail, {maximumAge: 500, enableHighAccuracy:true, timeout: 6000});},10000);
            } 
            else {
                alert("Not support Location");
                clearInterval(timer1);
            }
        }
        var rootURLeditUserLocation=$path+"webresources/com.dn.entity.groupsandusers/api/editUserLocation";
        function editUserLocation(useridTmp, latTmp, lngTmp) {
            $.ajax({
                        type: 'GET',
                        url: rootURLeditUserLocation + '/' + useridTmp + '/' + latTmp + '/' + lngTmp,
                        success: function (result) {
                          //  alert("ok");
                        },
                        error: function (error) {
                          //  alert("fail");
                        }
                 });
            }