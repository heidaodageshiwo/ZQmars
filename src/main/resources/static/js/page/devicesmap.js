/**
 * Created by  jiangqi.yang on 2016/11/15.
 */

function  fixMapperWrapper() {
    //Get window height and the wrapper heigh
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

    //alert( $("#site-longitude2").val()+"------"+$("#site-latitude2").val());
    mp.clearOverlays();
    var Jump=new BMap.Point( $("#site-longitude2").val(), $("#site-latitude2").val() );
    var marker1 = new BMap.Marker(Jump);
    mp.addOverlay(marker1);
    marker1.setAnimation(BMAP_ANIMATION_BOUNCE);



    // mp.panTo(findPoint);
    //地图样式
    // if( 1 == mapOnlineSw ) {
    //     // 在线
    //    /*     mp.setMapStyle({style:'normal'});*/
    //     //mp.setMapStyle({ styleJson: mapStyleData });
    //  }
   /* else
    {
    	// 离线
    	mp.setMapStyle({ styleJson: mapStyleData });
    }*/

        mp.setMapStyle({style:'normal'});

    var scaleControl = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
    var navigationControl = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件

    //添加控件
    mp.addControl( scaleControl );
    mp.addControl( navigationControl );

    //创建站点标记
  //  var siteMarkers = new SiteMarker({ map: mp, clickEvent: true ,url: "/device/getSites"});

    // 百度地图API功能
    // var map = new BMap.Map("device-map");
    //  map.centerAndZoom("重庆",10);
    //单击获取点击的经纬度单击获取点击的经纬度

    var J;
    var W;
    mp.addEventListener("click",function(e){
        mp.clearOverlays();
        //alert(e.point.lng + "," + e.point.lat);
        $("#site-longitude").val(e.point.lng);
        $("#site-latitude").val(e.point.lat);
     /*   $("#site-longitude1").val(e.point.lng);
        $("#site-latitude1").val(e.point.lat);*/
        $("#site-longitude2").val(e.point.lng);
        $("#site-latitude2").val(e.point.lat);
        /*J=e.point.lng;
        W=e.point.lat;*/
        J=$("#site-longitude2").val();//e.point.lng;
        W=$("#site-latitude2").val();//e.point.lat;
        var point1 = new BMap.Point(J, W);
        var marker = new BMap.Marker(point1);  // 创建标注
        mp.addOverlay(marker);               // 将标注添加到地图中
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
        J="";
        W="";

    });



   /* var point1 = new BMap.Point(117.822812, 36.403172);
    var marker = new BMap.Marker(point1);  // 创建标注
    mp.addOverlay(marker);               // 将标注添加到地图中
    marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
*/

/*//回车事件绑定
    $('#search_input').bind('keypress', function(event) {
        if (event.keyCode == "13") {
           // event.preventDefault();
            //回车执行查询
           // $('#search_button').click();
            mp.centerAndZoom( "烟台", 8);
            alert(1212333);
        }
    });*/


    /*  $("#search-input").hide();

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
      });*/

    // function showGPSInfo(e){
    //   	console.log( e.point.lng + ", " + e.point.lat );
    // }
    // mp.addEventListener("click", showGPSInfo);


});