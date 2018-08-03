/**
 * Created by jiangqi.yang  on 2016/10/27.
 */



$(function () {

    function formatSelect ( data ) {
        if (!data.id) { return data.text; }
        var $pDom = $(
            '<p><span>' + data.text + '</span><span class="pull-right text-gray">' + data.id + '</span></p>'
        );
        return $pDom;
    }

    $.ajax({
        type: "post",
        url: "/device/getSitesByUserId",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var siteData = $.map( data.data, function (obj) {
                    obj.text = obj.name;
                    obj.id = obj.sn;
                    return obj;
                });
                siteData.unshift({ id:0 , text: "不限" });
                $("#capture-site-sn").select2({
                    language: "zh-CN",
                    data: siteData,
                    templateResult: formatSelect
                });
            } else {
                $("#capture-site-sn").select2({
                    language: "zh-CN",
                    templateResult: formatSelect
                });
            }
        }
    });

    var deviceSNList = [];
    $.ajax({
        type: "post",
        url: "/device/getDevicesByUserId",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var deviceData = $.map( data.data, function (obj) {
                    obj.text = obj.name;
                    obj.id = obj.sn;
                    return obj;
                });
                deviceSNList = deviceData;
                deviceData.unshift({ id:0 , text: "不限" });
                $("#capture-device-sn").select2({
                    language: "zh-CN",
                    data: deviceData,
                    templateResult: formatSelect
                });
            } else {
                $("#capture-device-sn").select2({
                    language: "zh-CN",
                    templateResult: formatSelect
                });
            }
        }
    });

    $("#capture-site-sn").change(function () {
        $("#capture-device-sn").empty();
        var siteSN =  $("#capture-site-sn").val();
        if( 0 == siteSN ) {
            siteSN="";
        }
        $.ajax({
            type: "post",
            url: "/device/getDevices",
            data: { siteSN: siteSN },
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    var deviceData = $.map( data.data, function (obj) {
                        obj.text = obj.name;
                        obj.id = obj.sn;
                        return obj;
                    });
                    deviceSNList = deviceData;
                    deviceData.unshift({ id:0 , text: "不限" });
                    $("#capture-device-sn").select2({
                        language: "zh-CN",
                        data: deviceData,
                        templateResult: formatSelect
                    });
                    //清空当前表
                    $("#realtime-table tbody").empty();
                    $("#cache-table tbody").empty();

                } else {
                    $("#capture-device-sn").select2({
                        language: "zh-CN",
                        templateResult: formatSelect
                    });
                }
            }
        });
    });

    $("#capture-device-sn").change(function () {
        //清空当前表
        $("#realtime-table tbody").empty();
        $("#cache-table tbody").empty();
    });

    function  addRowsTable( ueInfoType,  rows ) {

        var $tbody;
        if( 0 == ueInfoType ) {
            $tbody =  $("#cache-table tbody");
        } else {
            $tbody =  $("#realtime-table tbody");
        }

        $tbody.empty(); //清除

        $.each( rows, function ( idx, rowVal ) {

            var trDom;
            var index = idx + 1;
            if( 0 == this.indication  ) {
                trDom = ['<tr>'];
                trDom.push('<td>' +  index + '</td>' );
                trDom.push('<td>' + this.imsi + '</td>' );
                trDom.push('<td>' + this.imei + '</td>' );
                trDom.push('<td>' + this.operatorText + '</td>' );
                trDom.push('<td>' + this.cityName + '</td>' );
                trDom.push('<td>' + this.siteName + '</td>' );
                trDom.push('<td>' + this.deviceName + '</td>' );
                trDom.push('<td>' + this.captureTime + '</td>' );
                trDom.push('</tr>');
            } else {
                trDom = ['<tr class="text-red">'];
                trDom.push('<td><strong>' +  index + '</strong></td>' );
                trDom.push('<td><strong>' + this.imsi + '</strong></td>' );
                trDom.push('<td><strong>' + this.operatorText + '</strong></td>' );
                trDom.push('<td><strong>' + this.cityName + '</strong></td>' );
                trDom.push('<td><strong>' + this.siteName + '</strong></td>' );
                trDom.push('<td><strong>' + this.deviceName + '</strong></td>' );
                trDom.push('<td><strong>' + this.captureTime + '</strong></td>' );
                trDom.push('</tr>');
            }

            $tbody.append(trDom.join(''));

        } );

    }

    function getUeInfoData( ueInfoType  ) {

        var siteSN = $("#capture-site-sn").val();
        var deviceSN = $("#capture-device-sn").val();
        var deviceSNArray = [];
        if( 0 != deviceSN ) {
            deviceSNArray.push(deviceSN);
        } else if( 0 != siteSN ) {
            $.each( deviceSNList, function ( i, deviceValue ) {
                if( 0 != deviceValue.id) {
                    deviceSNArray.push(deviceValue.id);
                }
            })
        }

        $.ajax({
            type: "get",
            url: "/getRealTimeData",
            data: { type: ueInfoType, sn: deviceSNArray },
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    if( 0 == ueInfoType ) {
                        addRowsTable( ueInfoType,data.data );
                    } else {
                        addRowsTable( ueInfoType, data.data);
                    }
                }
            }
        });
    }

    function getRealTimeData() {
        getUeInfoData(1);
    }

    function getCacheData() {
        getUeInfoData(0);
    }

    getRealTimeData(0);
    getCacheData(1);

    var getRealimeDataTimer = setInterval( getRealTimeData, 2000);
    var getCacheDataTimer = setInterval( getCacheData, 2000);

});