/**
 * Created by jiangqi.yang on 2016/11/8.
 */

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

    function formatSelect ( data ) {
        if (!data.id) { return data.text; }
        var $pDom = $(
            '<p><span>' + data.text + '</span><span class="pull-right text-gray">' + data.id + '</span></p>'
        );
        return $pDom;
    };

/*
    $.ajax({
        type: "post",
        url: "/target/getAlarmRuleNames",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var ruleData = $.map( data.data, function (obj) {
                    obj.text = obj.name;
                    obj.id = obj.name;

                    return obj;
                });
                ruleData.unshift({ id:0 , text: "不限" });
                $("#alarm-name").select2({
                    language: "zh-CN",
                    data: ruleData
                });
            }
        }
    });
*/
    $.ajax({
        type: "post",
        url: "/util/getCityCodes",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var cityData = $.map( data.data, function (obj) {
                    obj.text = obj.cityName;
                    obj.id = obj.cityName;
                    return obj;
                });
                cityData.unshift({ id:'' , text: "不限" });
                $("#alarm-homeOwnership").select2({
                    language: "zh-CN",
                    data: cityData
                });
            }
        }
    });

    $.ajax({
        type: "post",
        url: "/device/getSites",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var siteData = $.map( data.data, function (obj) {
                    obj.text = obj.name;
                    obj.id = obj.sn;
                    return obj;
                });
                siteData.unshift({ id:0 , text: "不限" });
                $("#alarm-site-sn").select2({
                    language: "zh-CN",
                    data: siteData,
                    templateResult: formatSelect
                });
            } else {
                $("#alarm-site-sn").select2({
                    language: "zh-CN",
                    templateResult: formatSelect
                });
            }
        }
    });

    $.ajax({
        type: "post",
        url: "/device/getDevices",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var deviceData = $.map( data.data, function (obj) {
                    obj.text = obj.name;
                    obj.id = obj.sn;
                    return obj;
                });
                deviceData.unshift({ id:0 , text: "不限" });
                $("#alarm-device-sn").select2({
                    language: "zh-CN",
                    data: deviceData,
                    templateResult: formatSelect
                });
            } else {
                $("#alarm-device-sn").select2({
                    language: "zh-CN",
                    templateResult: formatSelect
                });
            }
        }
    });

    $("#alarm-site-sn").change(function () {
        $("#alarm-device-sn").empty();
        var siteSN =  $("#alarm-site-sn").val();
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
                    deviceData.unshift({ id:0 , text: "不限" });
                    $("#alarm-device-sn").select2({
                        language: "zh-CN",
                        data: deviceData,
                        templateResult: formatSelect
                    });
                } else {
                    $("#alarm-device-sn").select2({
                        language: "zh-CN",
                        templateResult: formatSelect
                    });
                }
            }
        });
    });


    var alarmRangeTime = $('#alarm-range-time').daterangepicker({
        timePicker: true,
        timePickerIncrement: 1,
        timePicker24Hour: true,
        timePickerSeconds: true,
        locale: {
            applyLabel: '确定',
            cancelLabel: '取消',
            format: 'YYYY/MM/DD HH:mm:ss',
            fromLabel : '起始时间',
            toLabel : '结束时间',
            customRangeLabel : '自定义',
            daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
            monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月' ]
        }
    });

    $("#query-condition-OK").click(function ( evt ) {
        processSearch();
    });
//    $("#query-condition-reset").click(function(evt) {
//        $("#alarm-range-time").val('');
//        $("#alarm-name").val('');
//        $("#alarm-homeOwnership").val('');
//        $("#alarm-site-sn").val(0);
//        $("#select2-alarm-site-sn-container").val('不限');
//        $("#alarm-device-sn").val(0);
//        $("#select2-alarm-device-sn-container").val('不限');
//        $("#alarm-imsi").val('');
//    })
    
    function exportData( fileType ) {

        var startDate = $("#alarm-range-time").val().slice(0,19);
        var endDate = $("#alarm-range-time").val().slice(22,41);
        var ruleName = $("#alarm-name").val();
        if( 0 == ruleName) {
            ruleName="";
        }
        var homeOwnership = $("#alarm-homeOwnership").val();
        if( 0 == homeOwnership ) {
            homeOwnership="";
        }
        var siteSN = $("#alarm-site-sn").val();
        if( 0 == siteSN ) {
            siteSN="";
        }
        var deviceSN = $("#alarm-device-sn").val();
        if( 0 == deviceSN ) {
            deviceSN="";
        }
        var imsi = $("#alarm-imsi").val();

        var exportCondition = {
            startTime: startDate,
            endTime: endDate,
            ruleName: ruleName,
            homeOwnership: homeOwnership,
            siteSN: siteSN,
            deviceSN: deviceSN,
            imsi: imsi
        };

        $("#export-data-btn").attr("disabled","disabled");
        $("#export-data-dropdown-btn").attr("disabled","disabled");

        $.ajax({
            type: "post",
            url: "/util/exportData",
            dataType:"json",
            data:{ type: 1, fileType: fileType, condition: JSON.stringify(exportCondition) },
            success:  function( data ){
                if( true == data.status ) {
                    $('#export-progress-dlg').modal({
                        backdrop: false,
                        keyboard: false,
                        show: true
                    });
                    exportDataProgress( data.data.exportId );
                } else {
                    $("#export-data-btn").removeAttr("disabled");
                    $("#export-data-dropdown-btn").removeAttr("disabled");
                    Messenger().post({
                        message: data.message,
                        type: 'error',
                        showCloseButton: true
                    }) ;
                }
            },
            error: function ( data  ) {
                $("#export-data-btn").removeAttr("disabled");
                $("#export-data-dropdown-btn").removeAttr("disabled");
                Messenger().post({
                    message:  "网络异常，请稍后再试",
                    type: 'error',
                    showCloseButton: true
                }) ;
            }
        });
   }


    $("#export-data-btn").click(function (e) {

        $.ajax({
            type: "post",
            url: "/target/checkIsCanDownLoadExcelFile",
            dataType:"json",
            data:{},
            success:  function( data ){
                if(data.status) {
                    //exportData(0);
                    var param = getParams();
                    var formData = JSON.stringify(param);
                    var url = window.location.protocol + "//" + window.location.host + "/target/exportTxtHistoryData?formData=" + formData;
                    //window.open(url,"预警信息下载", "height=260,width=466,toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
                    window.location.href = url;
                } else {
                    Messenger().post({
                    message:  data.message,
                    type: 'error',
                    showCloseButton: true
                }) ;
                }
            },
            error : function (data) {
                // body...
                Messenger().post({
                    message:  "网络异常，请稍后再试",
                    type: 'error',
                    showCloseButton: true
                }) ;
            }
        });
        
    });

    $("#export-data-txt-btn").click(function (e) {
        //exportData(0);
        $('#export-progress-dlg').modal({
            backdrop: false,
            keyboard: false,
            show: true
        });
    });

    $("#export-data-csv-btn").click(function (e) {
        exportData(1);
    });



    function exportDataProgress( exoprtInfo ) {

        $.ajax({
            type: "post",
            url: "/util/exportDataProgress",
            dataType:"json",
            data:{ exportId: exoprtInfo },
            success:  function( data ){
                if( true == data.status ) {
                    if( data.data.progress >= 100) {
                        $('#export-progress-dlg').modal("hide");
                        $("#export-data-btn").removeAttr("disabled");
                        $("#export-data-dropdown-btn").removeAttr("disabled");
                        //window.open( data.data.url, "first");
                        window.open(data.data.url,"title","height=260,width=466,toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
                    } else {
                        $('#export-progress-bar div.progress-bar').css("width",  data.data.progress + "%");
                        $('#export-progress-bar div.progress-bar').attr("aria-valuenow",  data.data.progress);
                        $("#export-progress-bar span").text( data.data.progress + "% Complete (success)");
                        window.setTimeout( function(){ exportDataProgress( exoprtInfo );}  , 1 * 1000 );
                    }
                } else {
                    $('#export-progress-dlg').modal("hide");
                    $("#export-data-btn").removeAttr("disabled");
                    $("#export-data-dropdown-btn").removeAttr("disabled");
                    Messenger().post({
                        message: data.message,
                        type: 'error',
                        showCloseButton: true
                    }) ;
                }
            },
            error: function ( data  ) {
                $('#export-progress-dlg').modal("hide");
                $("#export-data-btn").removeAttr("disabled");
                $("#export-data-dropdown-btn").removeAttr("disabled");
                Messenger().post({
                    message:  "网络异常，请稍后再试",
                    type: 'error',
                    showCloseButton: true
                }) ;
            }
        });
    }
    //查询
    processSearch();
});

function getParams() {
    var hTime = $("#alarm-range-time").val();
    var hStartTime =hTime.slice(0,19);
    hStartTime = hStartTime.split('/').join('').split(':').join('').split(' ').join('');
    var hEndTime = hTime.slice(22,41);
    hEndTime = hEndTime.split('/').join('').split(':').join('').split(' ').join('');
    var hTargetName = $("#alarm-name").val();
    var hArea = $("#alarm-homeOwnership").val();
    if(hArea == '不限')
        hArea = '';
    var siteSN = $("#alarm-site-sn").val();
    var hSite = siteSN == '0' ?  '' : siteSN;
    var deviceSN = $("#alarm-device-sn").val();
    var hDevice = deviceSN == '0' ?  '' : deviceSN;
    var hImsi = $("#alarm-imsi").val();

    var param = {
        hStartTime : $.trim(hStartTime),
        hEndTime : $.trim(hEndTime),
        hTargetName : $.trim(hTargetName),
        hArea : $.trim(hArea),
        hSite : $.trim(hSite),
        hDevice : $.trim(hDevice),
        hImsi : $.trim(hImsi)
    };

    return param;
}

function processSearch () {
    var param = getParams();
    targetsTable = $('#history-alarm-table').DataTable( {
        "sDom": "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-1'l><'col-sm-5'i><'col-sm-6'p>>",
        "select": {
            style: "os",
        },
        "sServerMethod":"POST",
        "searching":false,
        "bFilter":true,
        "bDestroy": true,
        "bServerSide": true,
        "bProcessing":true,
        "bSort":false,
        "indexNum": 1,
        "order": [[ 0, 'asc' ]],
        "sAjaxDataProp" : "dataResult",
        "sAjaxSource": "/target/queryHistoryDataNew",
        "fnServerParams": function ( aoData ) {
                        aoData.push( { "name": "formData", "value": JSON.stringify(param) } );
                    },
        "columnDefs": [
            {
             sDefaultContent: '',
             aTargets: [ '_all' ]
            },
            {
                "aTargets" : [2],
                "mData" : "download_link",
                "mRender" : function(data, type, full) {
                    return data == 1 ? "黑名单预警" : "归属地预警";
                }
            },
            {
                "aTargets" : [8],
                "mData" : "download_link",
                "mRender" : function(data, type, full) {
                    var year = data.substring(0, 4);
                    var month = data.substring(4, 6);
                    var day = data.substring(6, 8);
                    var hour = data.substring(8, 10);
                    var min = data.substring(10, 12);
                    var s = data.substring(12, 14);
                    return year + "年" + month + "月" + day + "日 " + hour + ":" + min + ":" + s;
                }
            },
        ],
        "columns": [
            { "data": "groupName","sClass": "center"},
            { "data": "targetName","sClass": "center"},
            { "data": "indication","sClass": "center" },
            { "data": "imsi","sClass": "center" },
            { "data": "cityName","sClass": "center","bSortable": false },
            { "data": "operator","sClass": "center","bSortable": false },
            { "data": "siteName","sClass": "center" },
            { "data": "deviceName","sClass": "center" },
            { "data": "captureTime","sClass": "center","bSortable": false }
        ],
         "oLanguage" : dataTable_language
    });

    targetsTable.on( 'order.dt search.dt', function () {
        targetsTable.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
            cell.innerHTML = i+1;
        } );
    } ).draw();
}
