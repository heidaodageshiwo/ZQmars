/**
 * Created by  jiangqi.yang on 2016/11/15.
 */

function  fixMapperWrapper() {
    //Get window height and the wrapper height
    var neg = $('.main-header').outerHeight() + $('.main-footer').outerHeight();
    var window_height = $(window).height();
    var sidebar_height = $(".sidebar").height();
    //Set the min-height of the content and sidebar based on the
    //the height of the document.
    if ($("body").hasClass("fixed")) {
        $("#device-map").css('min-height', window_height - $('.main-footer').outerHeight()- $('.content-header').outerHeight() );
    } else {
        var postSetWidth;
        if (window_height >= sidebar_height) {
            $("#device-map").css('min-height', window_height - neg - $('.content-header').outerHeight());
            postSetWidth = window_height - neg - $('.content-header').outerHeight();
        } else {
            $("#device-map").css('min-height', sidebar_height- $('.content-header').outerHeight());
            postSetWidth = sidebar_height - $('.content-header').outerHeight();
        }

        //Fix for the control sidebar height
        var controlSidebar = $($.AdminLTE.options.controlSidebarOptions.selector);
        if (typeof controlSidebar !== "undefined") {
            if (controlSidebar.height() > postSetWidth)
                $("#device-map").css('min-height', controlSidebar.height() - $('.content-header').outerHeight());
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
        minZoom: 5,
        maxZoom: 18,
        mapType:  BMAP_NORMAL_MAP
    }
    
    mp = new BMap.Map('device-map',mapOptions);
    var centerLng  = Number($("#center-point-lng").val());
    var centerLat  = Number($("#center-point-lat").val());
    var zoomVal  = Number($("#zoom-value").val());
    var mapOnlineSw  = Number($("#map-online-sw").val());
    var point = new BMap.Point( centerLng, centerLat );
    mp.centerAndZoom( point, zoomVal);

    mp.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

    //地图样式
    if( 1 == mapOnlineSw ) {
        // 在线
        mp.setMapStyle({style:'normal'});
    }
    // else
    // {
    // 	// 离线
    // 	mp.setMapStyle({ styleJson: mapStyleData });
    // }

    var scaleControl = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
    var navigationControl = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件

    //添加控件
    mp.addControl( scaleControl );
    mp.addControl( navigationControl );

    //创建站点标记
    var siteMarkers = new SiteMarker({ map: mp, clickEvent: true ,url: "/device/getSites"});


    $("#search-input").hide();

    function updateUETotal( data ) {
        if( data != undefined ) {
            $("#imsi-total").text( data );
        }
    }

    function updateBlacklistTotal( data ) {
        if( data != undefined ) {
            $("#blacklist-warring-total").text( data );
        }
    }

    function updateHomeOwnershipTotal( data ) {
        if( data != undefined ) {
            $("#homeownership-warring-total").text( data );
        }
    }

    function updateSitesState( data ) {
        if( data != undefined ) {
            $.each( data, function (i, siteStateInfo) {
                siteMarkers.setSiteStatus( siteStateInfo.sn , siteStateInfo.status);
            });
        }
    }

    if( efenceNotify ) {
        efenceNotify.addHandleFun( "updateUETotal", updateUETotal );
        efenceNotify.addHandleFun( "updateBlacklistTotal", updateBlacklistTotal );
        efenceNotify.addHandleFun( "updateHomeOwnershipTotal", updateHomeOwnershipTotal );
        efenceNotify.addHandleFun( "updateSitesState", updateSitesState );
    }
    
    function  findSiteMarkerAndMove( name ) {
        if( (null == name) || ( "" == name ) ) {
            return
        }
        var findSites =  siteMarkers.FindSiteMarker(name);
        if( findSites.length > 0 ){
            var findPoint = new BMap.Point(  findSites[0].longitude, findSites[0].latitude);
            mp.panTo(findPoint);
        }
    }

    $("#search-btn").click(function () {
        $("#search-input input").val("");
        $("#search-input").show();
    });

    $('#search-input input').bind('keypress',function(event){
        if(event.keyCode == "13") {
            $("#search-input").hide();
            findSiteMarkerAndMove($("#search-input input").val());
        }
    });

    $("#search-input a").on("click", function () {
        $("#search-input").hide();
        findSiteMarkerAndMove($("#search-input input").val());
    });

    $("#whole-layout-btn").click(function () {
        siteMarkers.centerAndZoom();
    });

    // function showGPSInfo(e){
    //   	console.log( e.point.lng + ", " + e.point.lat );
    // }
    // mp.addEventListener("click", showGPSInfo);


});