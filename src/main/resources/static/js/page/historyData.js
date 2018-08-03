/**
 * Created by jiangqi.yang  on 2016/10/27.
 */


$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

    $("#capture-operator").select2({
        language: "zh-CN",
        minimumResultsForSearch: Infinity
    });

    function formatSelect ( data ) {
        if (!data.id) { return data.text; }
        var $pDom = $(
            '<p><span>' + data.text + '</span><span class="pull-right text-gray">' + data.id + '</span></p>'
        );
        return $pDom;
    };

    $.ajax({
        type: "post",
        url: "/util/getCityCodes",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var cityData = $.map( data.data, function (obj) {
                                    obj.text = obj.cityName;
                                    obj.id = obj.cityCode;

                                    return obj;
                                });
                cityData.unshift({ id:0 , text: "不限" });
                $("#capture-homeOwnership").select2({
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
    });


    // $("#capture-homeOwnership").select2({
    //     language: "zh-CN",
    //     ajax: {
    //         url: "/util/getCityCodes",
    //         dataType: 'json',
    //         data: function (params) {
    //             return {
    //                 filter: params.term
    //             };
    //         },
    //         processResults: function (data, params) {
    //             var cityData = $.map( data.data, function (obj) {
    //                 obj.text = obj.cityName;
    //                 obj.id = obj.cityCode;
    //
    //                 return obj;
    //             });
    //             return {
    //                 results: cityData
    //             };
    //         },
    //         cache: true
    //     }
    //
    // });

    // $("#capture-site-sn").select2({
    //     language: "zh-CN",
    //     ajax: {
    //         url: "/device/getSites",
    //         dataType: 'json',
    //         data: function (params) {
    //             return {
    //                 filter: params.term
    //             };
    //         },
    //         processResults: function (data, params) {
    //             var siteData = $.map( data.data, function (obj) {
    //                 obj.text = obj.name;
    //                 obj.id = obj.sn;
    //
    //                 return obj;
    //             });
    //             return {
    //                 results: siteData
    //             };
    //         },
    //         cache: true
    //     },
    //     templateResult: formatSelect
    // });

    // $("#capture-device-sn").select2({
    //     language: "zh-CN",
    //     ajax: {
    //         url: "/device/getDevices",
    //         dataType: 'json',
    //         data: function (params) {
    //             var siteSN =  $("#capture-site-sn").val();
    //             return {
    //                 filter: params.term,
    //                 siteSN: siteSN
    //             };
    //         },
    //         processResults: function (data, params) {
    //             var deviceData = $.map( data.data, function (obj) {
    //                 obj.text = obj.name;
    //                 obj.id = obj.sn;
    //
    //                 return obj;
    //             });
    //             return {
    //                 results: deviceData
    //             };
    //         },
    //         cache: true
    //     },
    //     templateResult: formatSelect
    // });
    //
    //
    // $("#capture-site-sn").change(function () {
    //     $("#capture-device-sn").empty();
    // });


    var captureRangeTime = $('#capture-range-time').daterangepicker({
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
//2017.11.6注释开始
    /*var historyDataTable = $('#history-data-table').DataTable({
        "paging": true,
        "processing": true,
        "lengthChange": false,
        "pageLength": 30,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": true,
        "serverSide": true,
        "ajax": {
            "url": "/queryHistoryData"
        },
        columns: [
            {
                "orderable":      false,
                "data":           null
            },
            { "data": "imsi" },
            { "data": "imei" },
            { "data": "operator" },
            { "data": "cityName" },
            { "data": "siteSN" },
            { "data": "siteName" },
            { "data": "deviceSN" },
            { "data": "deviceName" },
            { "data": "captureTime" }
        ],
        "createdRow": function( row, data, dataIndex ) {
            if ( data.indication != 0  ) {
                $(row).addClass( 'text-red' );
            }
        },
        "fnDrawCallback": function(){
            var api = this.api();
            var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
            api.column(0).nodes().each(function(cell, i) {
                cell.innerHTML = startIndex + i + 1;
            });
        },
        "language": {
            "lengthMenu": "每页显示 _MENU_ 记录",
            "zeroRecords": "对不起-没有数据发现",
            "info": "显示 _START_ 到 _END_ 条记录共 _TOTAL_ 记录，显示第_PAGE_页, 共_PAGES_页",
            "infoEmpty": "没有数据记录",
            "infoFiltered": "(从总_MAX_条记录中过滤)",
            "sEmptyTable":"表中没有有效数据",
            "paginate": {
                "first": "最后一页",
                "last": "第一页",
                "next": "下一页",
                "previous": "上一页"
            },
            "sSearch": "搜索:",
            "sSearchPlaceholder": "请输入过滤条件",
            select: {
                rows: {
                    _: "选中 %d 行",
                    1: "选中 1 行"
                }
            }
        }
    });

    $("#query-condition-OK").click(function ( evt ) {
        var startDate = $("#capture-range-time").val().slice(0,19);
        var endDate = $("#capture-range-time").val().slice(22,41);
        var operator = $("#capture-operator").val();
        var homeOwnership = $("#capture-homeOwnership").val();
        if( 0 == homeOwnership ) {
            homeOwnership="";
        }
        var siteSN = $("#capture-site-sn").val();
        if( 0 == siteSN ) {
            siteSN="";
        }
        var deviceSN = $("#capture-device-sn").val();
        if( 0 == deviceSN ) {
            deviceSN="";
        }
        var imsi = $("#capture-imsi").val();

        var queryUrl = "/queryHistoryData?" + "startDate=" + startDate +
                "&endDate=" + endDate + "&operator=" + operator +
                "&homeOwnership=" + homeOwnership + "&siteSN=" + siteSN +
                "&deviceSN=" + deviceSN + "&imsi=" +  encodeURIComponent(imsi) ;
                 historyDataTable.ajax.url( queryUrl ).load();

    });*/

    

    function exportData( fileType ) {

        var startDate = $("#capture-range-time").val().slice(0,19);
        var endDate = $("#capture-range-time").val().slice(22,41);
        var operator = $("#capture-operator").val();
        var homeOwnership = $("#capture-homeOwnership").val();
        if( 0 == homeOwnership ) {
            homeOwnership="";
        }
        var siteSN = $("#capture-site-sn").val();
        if( 0 == siteSN ) {
            siteSN="";
        }
        var deviceSN = $("#capture-device-sn").val();
        if( 0 == deviceSN ) {
            deviceSN="";
        }
        var imsi = $("#capture-imsi").val();

        var exportCondition = {
            startTime: startDate,
            endTime: endDate,
            operator: operator,
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
            data:{ type: 0, fileType: fileType, condition: JSON.stringify(exportCondition) },
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
        exportData(0);
    });

    $("#export-data-txt-btn").click(function (e) {
        exportData(0);
    });

    $("#export-data-csv-btn").click(function (e) {
        exportData(1);
    });
    $("#export-data-xls-btn").click(function (e) {
        exportData(2);
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
                        //window.open( data.data.url );
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

    //新分页
    $("#query-condition-OK").bind("click", function() {
        processSearch();
    })
    processSearch();


});


function processSearch () {
    var startDate = $("#capture-range-time").val().slice(0,19);
    var endDate = $("#capture-range-time").val().slice(22,41);
    var operator = $("#capture-operator").val();
    var homeOwnership = $("#capture-homeOwnership").val();
    if( 0 == homeOwnership ) {
        homeOwnership="";
    }
    var siteSN = $("#capture-site-sn").val();
    if( 0 == siteSN ) {
        siteSN="";
    }
    var deviceSN = $("#capture-device-sn").val();
    if( 0 == deviceSN ) {
        deviceSN="";
    }
    var imsi = $("#capture-imsi").val();
  /*
    var targetName = $("#alarmTargetName").val();
    var type = $("#targetType").val();
    var imsi = $("#alarmIMSI").val();*/

    var param = {
        startDate : $.trim(startDate),
        endDate : endDate,
        operator : $.trim(operator),
        homeOwnership : $.trim(homeOwnership),
        siteSN : $.trim(siteSN),
        deviceSN : $.trim(deviceSN),
        imsi : $.trim(imsi)
    };
    historyDataTable = $('#history-data-table').DataTable( {
        "sDom": "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-1'l><'col-sm-5'i><'col-sm-6'p>>",
        "select": {
            style: "multi",
            selector: "td:first-child"
        },
        "sServerMethod":"POST",
        "searching":false,
        "bFilter":true,
        "bDestroy": true,
        "bServerSide": true,
        "bProcessing":true,
        "bSort":false,
        "sAjaxDataProp" : "dataResult",
        "sAjaxSource": "/queryHistoryData",
        "fnServerParams": function ( aoData ) {
            aoData.push( { "name": "formData", "value": JSON.stringify(param) } );
        },

        "createdRow": function( row, data, dataIndex ) {
            if ( data.indication != 0  ) {
                $(row).addClass( 'text-red' );
            }
        },
        "columnDefs": [
            {
                sDefaultContent: '',
                aTargets: [ '_all' ]
            }
        ],
        "columns": [
            { "data": "imsi","sClass": "center"},
            { "data": "imei","sClass": "center"},
            { "data": "operator","sClass": "center" },
            { "data": "cityName","sClass": "center" },
            { "data": "siteSn","sClass": "center"},
            { "data": "siteName","sClass": "center" },
            { "data": "deviceSn","sClass": "center" },
            /*{ "data": "deviceName","sClass": "center" },*/
            { "data": "captureTime","sClass": "center"}/*,
            { "data": null,"sClass": "center","bSortable": false }*/
        ],
        "oLanguage" : dataTable_language
    } );
}