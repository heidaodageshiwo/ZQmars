/**
 * Created by jiangqi.yang on 2016/11/23.
 */

function  fixMapperWrapper() {
    //Get window height and the wrapper height
    var neg = $('.main-header').outerHeight() + $('.main-footer').outerHeight();
    var window_height = $(window).height();
    var sidebar_height = $(".sidebar").height();
    var postSetWidth;
    //Set the min-height of the content and sidebar based on the
    //the height of the document.
    if ($("body").hasClass("fixed")) {
        $("#suspectTrailReport-map").css('min-height', window_height - $('.main-footer').outerHeight()- $('.content-header').outerHeight() );
    } else {
      /*  var postSetWidth;*/
        if (window_height >= sidebar_height) {
            $("#suspectTrailReport-map").css('min-height', window_height - neg - $('.content-header').outerHeight());
            postSetWidth = window_height - neg - $('.content-header').outerHeight();
        } else {
            $("#suspectTrailReport-map").css('min-height', sidebar_height- $('.content-header').outerHeight());
            postSetWidth = sidebar_height - $('.content-header').outerHeight();
        }

        //Fix for the control sidebar height
        var controlSidebar = $($.AdminLTE.options.controlSidebarOptions.selector);
        if (typeof controlSidebar !== "undefined") {
            if (controlSidebar.height() > postSetWidth)
                $("#suspectTrailReport-map").css('min-height', controlSidebar.height() - $('.content-header').outerHeight());
        }
    }
}

$(function () {

    var mapStyleData = [
        {
            "featureType": "land",
            "elementType": "all",
            "stylers": {
                "lightness": 100,
                "saturation": -100
            }
        },
        {
            "featureType": "water",
            "elementType": "all",
            "stylers": {
                "lightness": 47
            }
        },
        {
            "featureType": "manmade",
            "elementType": "geometry",
            "stylers": {
                "lightness": 28
            }
        },
        {
            "featureType": "road",
            "elementType": "geometry.fill",
            "stylers": {
                "lightness": 82
            }
        },
        {
            "featureType": "road",
            "elementType": "geometry.stroke",
            "stylers": {
                "lightness": -76
            }
        },
        {
            "featureType": "green",
            "elementType": "all",
            "stylers": {
                "lightness": 63,
                "saturation": -100
            }
        },
        {
            "featureType": "boundary",
            "elementType": "geometry.fill",
            "stylers": {
                "lightness": 80,
                "saturation": 1
            }
        },
        {
            "featureType": "boundary",
            "elementType": "geometry.stroke",
            "stylers": {
                "lightness": -75,
                "saturation": -100
            }
        }
    ];

    $(window, ".wrapper").resize(function () {
        fixMapperWrapper();
    });

    fixMapperWrapper();

    var mapOptions={
        minZoom: 10,
        maxZoom: 14,
        mapType:  BMAP_NORMAL_MAP
    };

    var trailMap = new BMap.Map('suspectTrailReport-map',mapOptions);
    var centerLng  = Number($("#center-point-lng").val());
    var centerLat  = Number($("#center-point-lat").val());
    var zoomVal  = Number($("#zoom-value").val());
    var mapOnlineSw  = Number($("#map-online-sw").val());


    if( 1 == mapOnlineSw ) {
        trailMap.setMapStyle({ styleJson: mapStyleData });
    }

    trailMap.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

    $('.slimScrollDiv').slimScroll({ height: "300px" });

    var lushuList = [];
    var poi = [];
    var taskId = $("#task-id").val();
    var taskName = $("#task-name").val();
    var getReportDataURL = "/analysis/suspectTrail/report/" + parseInt(taskId) + "/getReportData";
    var trailColor = ['blue','orange','pink'];

    function renderTableTr( data ) {
        var $tableTbody = $("#suspectTrail-table-" + data.imsi +" tbody");
        $.each( data.data, function ( k , value ) {
            var trDom;

            trDom = ['<tr>'];
            trDom.push('<td class="text-center">' + value.siteName + '</td>' );
            trDom.push('<td class="text-center">' + value.time + '</td>' );
            trDom.push('</tr>');
            $tableTbody.prepend(trDom.join(''));
        });

    }

    $.ajax({
        type: "post",
        url: getReportDataURL,
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {

                if(  (data.data.data.length > 0) && (data.data.data[0].imsi != undefined) ) {

                    $.each(data.data.data, function ( i , someoneTrail ) {

                        if( someoneTrail.data.length>0 ) {

                        	renderTableTr(someoneTrail);
                            var currentPoi = [];

                            var lastPoi;
                            var trailPoi = [];
                            

                            $.each( someoneTrail.data, function ( j , value ) {

                                var sitePoint = new BMap.Point( value.longitude, value.latitude);
                                trailMap.addOverlay(new BMap.Marker(sitePoint,  { title:  value.siteName }));
                                poi.push( sitePoint );
                                currentPoi.push( sitePoint );

                                if( j == 0 ) {
                                    trailPoi.push( sitePoint );
                                } else {
                                    if( lastPoi != undefined ) {
                                        var logDif = lastPoi.longitude - value.longitude;
                                        var latDif  = lastPoi.latitude - value.latitude;
                                        if( (Math.abs( logDif) > 0) || ( Math.abs( latDif ) > 0  ) ) {
                                            var logStep = logDif/25;
                                            var latStep = latDif/25;
                                            for( var gpsStep =1; gpsStep < 25; gpsStep ++){
                                                var tmpPoint = new BMap.Point( Number(lastPoi.longitude ) - Number((logStep * gpsStep)) ,
                                                    Number(lastPoi.latitude) - Number((latStep * gpsStep)) );
                                                trailPoi.push(tmpPoint);
                                            }

                                        }

                                    }
                                    trailPoi.push( sitePoint );
                                }
                                lastPoi = value;

                            });

                            var polyline = new BMap.Polyline(currentPoi, {strokeColor: trailColor[i], strokeWeight:6, strokeOpacity:0.5});
                            trailMap.addOverlay(polyline);

                            var lushu= new BMapLib.LuShu(trailMap, trailPoi, {
                                defaultContent: taskName + '-' + someoneTrail.imsi ,
                                speed: 4500,//路书速度,
                                enableRotation:true,
                                icon  : new BMap.Icon('/img/walk-black-32.png', new BMap.Size(32,32),{ anchor : new BMap.Size(20, 32) }),
                                autoView: true,//自动调整路线视野
                                landmarkPois:[]
                            });

                            lushuList.push(lushu);

                        }

                    });

                }

                if( poi.length > 0 )  {
                    var view = trailMap.getViewport(eval(poi));
                    var mapZoom = view.zoom;
                    var centerPoint = view.center;
                    trailMap.centerAndZoom(centerPoint,mapZoom);
                } else {
                    var point = new BMap.Point( centerLng, centerLat );
                    trailMap.centerAndZoom( point, zoomVal);
                }

            }
        }
    });

    $("#lusu-start-btn").click(function (e) {
        if( lushuList != null && lushuList.length > 0 ) {
            $.each( lushuList, function (i, lushu ) {
                lushu.start();
            })

        }
    });

    $("#lusu-pause-btn").click(function (e) {
        if( lushuList != null && lushuList.length > 0 ) {
            $.each( lushuList, function (i, lushu ) {
                lushu.pause();
            });
        }
    });

    $("#lusu-stop-btn").click(function (e) {
        if( lushuList != null && lushuList.length > 0 ) {
            $.each( lushuList, function (i, lushu ) {
                lushu.stop();
            });
        }
    });


    $("#whole-layout-btn").click(function (e) {

        if( poi.length > 0 ) {
            var view = trailMap.getViewport(eval(poi));
            var mapZoom = view.zoom;
            var centerPoint = view.center;
            trailMap.centerAndZoom(centerPoint,mapZoom);
        } else {
            var wholeCenterLng  = Number($("#center-point-lng").val());
            var wholeCenterLat  = Number($("#center-point-lat").val());
            var wholeZoomVal  = Number($("#zoom-value").val());
            var wholeCenterPoint = new BMap.Point( wholeCenterLng, wholeCenterLat );

            trailMap.setZoom(wholeZoomVal);
            trailMap.panTo(wholeCenterPoint);
        }
    });

});