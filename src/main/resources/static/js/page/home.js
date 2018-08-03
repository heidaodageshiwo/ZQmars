/**
 * Created by jiangqi.yang on 2016/10/27.
 */

$(function () {

    /* 硬盘资源 */
    var hardDiskChart = new Morris.Donut({
        element: 'hard-disk-chart',
        resize: true,
        colors: ["#f56954", "#00a65a"],
        data: [
            {label: "已用空间", value: 0},
            {label: "可用空间", value: 100}
        ],
        formatter:function (y, data) { return y + 'M' },
        hideHover: 'auto'
    });

    hardDiskChart.setData([
        {label: "已用空间", value: Number( $("#hard-disk-used").val() ) },
        {label: "可用空间", value: Number( $("#hard-disk-free").val() ) }
    ]);



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

    function updateRunTime( data ) {
        if( data ) {
            $("#system-running-time").text( data.day + '天' + data.hour + '小时'+ data.minute + '分钟' );
        }
    }

    function updateDeviceStatus( data ) {
        if( data ) {
            $("#device-total").text(data.total);
            $("#device-running-total").text(data.running);
            $("#device-offline-total").text(data.offline);
            $("#device-willexpire-total").text(data.willExpire);
            $("#device-expiredfailure-total").text(data.expiredFailure);
            $("#device-warning-total").text(data.warning);
        }
    }

    function updateHardDisk( updateHardDisk ) {
        if( updateHardDisk ) {
            hardDiskChart.setData([
                {label: "已用空间", value: updateHardDisk.used },
                {label: "可用空间", value: updateHardDisk.free }
            ]);
        }
    }


    if( efenceNotify ) {

        efenceNotify.addHandleFun( "updateUETotal", updateUETotal );
        efenceNotify.addHandleFun( "updateBlacklistTotal", updateBlacklistTotal );
        efenceNotify.addHandleFun( "updateHomeOwnershipTotal", updateHomeOwnershipTotal );
        efenceNotify.addHandleFun( "updateRunTime", updateRunTime );
        efenceNotify.addHandleFun( "updateDeviceStatus", updateDeviceStatus );
        efenceNotify.addHandleFun( "updateHardDisk", updateHardDisk );
    }


});