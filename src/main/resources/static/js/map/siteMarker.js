/**
 * Created by jiangqi.yang on 2016/11/15.
 */

var SiteMarker = function ( options ) {
    this.data = options.data;
    this.url  = options.url;
    this.map  = options.map;
    this.infoBox = null;
    this.view  = null;
    this.statusTimer = null;
    this.clickEvent = false || options.clickEvent;
    this.init(options);
};

SiteMarker.prototype.init = function (options) {

    var _this  = this;
    if( this.map == null ) {
        return ;
    }

    if( (this.data != null) ) {
        this.renderMarkers( data );
    } else {
        if( this.url == null ) {
            return;
        }

        $.ajax({
            type: "post",
            url: _this.url,
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    _this.data = data.data;
                    _this.renderMarkers(  _this.data );
                } else {
                    _this.data = [];
                }
            }
        });
    }

};


SiteMarker.prototype.updateData = function () {

};

SiteMarker.prototype.renderMarkers = function ( sties ) {

    var _this = this;
    var points = [];
    $.each( sties, function ( index, site ) {

        var sitePoint = new BMap.Point( site.longitude, site.latitude);
        points.push(sitePoint);
        var html = [];
        html.push('<div class="map-marker" data-marker-site-sn="' + site.sn +  '" >');
        html.push('<p class="marker-title"><strong>' + site.sn + '</strong></p>')
        html.push('<i class="marker-icon offline"></i>');
        html.push('</div>');

        var richMarker = new BMapLib.RichMarker( site, html.join(""),  sitePoint,{
            // "anchor" : new BMap.Size(-18, -27),
            "enableDragging" : false});

        if( _this.clickEvent ) {
            richMarker.addEventListener("click", function ( e ) {
                _this.onClickFunction( e, site );
            });
        }


        _this.map.addOverlay(richMarker);
    });

    _this.view = _this.map.getViewport(eval(points));

};

SiteMarker.prototype.clearMarkers = function () {
    this.map.clearOverlays();
};

SiteMarker.prototype.onClickFunction = function ( e, site ) {

    var _this = this;
    var currentPoint = e.target.getPosition();

    if( (this.infoBox !=null ) && this.infoBox.isOpen() ) {
        this.infoBox.close();
    }

    SiteMarker.prototype.siteSN= site.sn;

    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: "/device/getSiteStatusInfo",
        data:{ 'sn': site.sn },
        timeout: 5000,
        success: function ( data ) {
            if( true == data.status ) {

                _this.updateSiteData(data.data);

                var html = _this.renderHtml(site);

                _this.infoBox = new BMapLib.InfoBox( _this.map, html,{
                    boxStyle:{
                        background: 'rgba(0, 0, 0, 0.7)'                      
                        ,width: "400px"
                        ,height: "600px"
                    },
                    closeIconUrl: "/img/infobox_close.gif"
                    ,closeIconMargin: "10px 5px 0 0"
                    ,enableAutoPan: true
                    ,align: INFOBOX_AT_RIGHT
                    ,offset: new BMap.Size( 50,-150)
                });

                _this.infoBox.addEventListener("close", function(e) {
                    if( _this.statusTimer !=null ) {
                        window.clearInterval(_this.statusTimer);
                    }
                });

                _this.infoBox.open(currentPoint);

                $('.slimScrollDiv').slimScroll({ height: "559px" });
                $('.collapse').collapse({  toggle: false });
                $('#device-accordion h3 a').each(function () {
                    $(this).click(function () {
                        var collapseId = $(this).data("collaps-id");
                        $(collapseId).collapse('toggle');
                    });
                })

            }
        }
    });

    // debugger;
    _this.statusTimer = window.setInterval( function(){ _this.getSiteStatus( _this );}  , 30 * 1000);

};

SiteMarker.prototype.updateSiteData = function ( data ) {

    var _this = this;
    this.data =  $.map(_this.data, function ( site ) {
        if( site.id == data.id ) {

            site.status = data.status || {};

            site.devices = $.map( site.devices, function ( device ) {
                var stauts = {};
                $.each(data.devices, function ( i , newDevice ) {
                    if( device.id == newDevice.id ) {
                        stauts = newDevice.status;
                    }
                });
                device.status = stauts;

                return device;
            } );

        }

        return site;

    });

};

SiteMarker.prototype.getSiteStatus = function ( obj ) {
    var _this = obj;
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: "/device/getSiteStatusInfo",
        data:{ 'sn': SiteMarker.prototype.siteSN },
        timeout: 5000,
        success: function ( data ) {
            if( true == data.status ) {
                _this.updateSiteData( data.data );
                _this.renderInfoBoxDeviceInfo( data.data);
            }
        }
    });
}


SiteMarker.prototype.renderInfoBoxDeviceInfo = function ( siteStatus ) {

    if( (this.infoBox !=null ) && this.infoBox.isOpen() ) {

        $.each( siteStatus.devices, function ( i, device ) {

            var rowDom=[];
            $.each( device.status, function ( i, info ) {
                rowDom.push('<dt>' + info.label + '</dt>');
                rowDom.push('<dd>' + info.value + '</dd>');
            });

            var $deviceInfo = $('#device-' + device.sn + '-status-info');
            $deviceInfo.empty();
            $deviceInfo.prepend(rowDom.join(''));
        });
    }
};


SiteMarker.prototype.FindSiteMarker = function ( name ) {
    var _this = this;
    return $.grep(_this.data, function ( site ) {
        var val = site.name;
        if (typeof val === 'string') {
            return val.match(new RegExp( name, 'g'));
        }
    });
}

SiteMarker.prototype.renderHtml = function ( site ) {

    var html = [];

    html.push('<div class="infoBoxContent">');
        html.push('<div class="nav-tabs-custom infoBox-header">');
            html.push('<ul class="nav nav-tabs pull-right">');
                html.push('<li class="pull-left infoBox-title">' + site.name + '-' + site.sn +  '</li>');
            html.push('</ul>');
        html.push('</div>');
        html.push('<div class="tab-content slimScrollDiv">');
            html.push('<div class="box-group panel-group" id="device-accordion" role="tablist" aria-multiselectable="true">');
                $.each(site.devices, function ( i, device ) {
                    html.push('<div class="panel box infoBox-box">');
                        html.push('<div class="box-header with-border">');
                            html.push(' <h3 class="box-title">');
                                html.push('<a  class="collapsed" data-toggle="collapse" data-parent="#device-accordion"  href="javascript:void(0);"  data-collaps-id="#collapse' + device.sn+ '">' + device.name + '</a>');
                            html.push('</h3>');
                        html.push('</div>');
                        html.push('<div id="collapse' + device.sn + '" class="panel-collapse collapse " role="tabpanel">');
                            html.push('<div class="box-body">');
                                html.push('<dl class="dl-horizontal" style="color: #fff;" id="device-' + device.sn + '-status-info">');
                                    $.each(device.status, function ( i, status ) {
                                        html.push('<dt>' + status.label + '</dt>');
                                        html.push('<dd>' + status.value + '</dd>');
                                    });
                                html.push('</dl>');
                            html.push('</div>');
                        html.push('</div>');
                     html.push('</div>');
                });
            html.push('</div>');
        html.push('</div>');
    html.push('</div>');

    return html.join("");
}


SiteMarker.prototype.centerAndZoom  = function () {

    if( null != this.view ) {
        var mapZoom  = this.view.zoom - 1;
        var centerPoint  = this.view.center;
        this.map.centerAndZoom(centerPoint, mapZoom) ;
    }

};

SiteMarker.prototype.setSiteStatus = function ( sn, status ) {

    $markerIcon =  $('div.map-marker[data-marker-site-sn='+ sn + ']').find("i");
    $markerIcon.removeClass();
    if( 1 ==  status ) {
        $markerIcon.addClass("marker-icon online");
    } else if( 2 == status ) {
        $markerIcon.addClass("marker-icon alarm");
    } else {
        $markerIcon.addClass("marker-icon offline");
    }

};