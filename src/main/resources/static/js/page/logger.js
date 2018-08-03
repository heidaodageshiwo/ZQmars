/**
 * Created by  jiangqi.yang s on 2016/11/12.
 */


$(function () {

    $("#logger-type").select2();

    var loggerRangeTime = $('#logger-range-time').daterangepicker({
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

    function formatSelect ( data ) {
        if (!data.id) { return data.text; }
        var $pDom = $(
            '<p><span>' + data.text + '</span><span class="pull-right text-gray">' + data.id + '</span></p>'
        );
        return $pDom;
    }
    
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
                /*deviceSNList = deviceData;*/
                deviceData.unshift({ id:0 , text: "不限" });
                $("#logger-device-sn").select2({
                    language: "zh-CN",
                    data: deviceData,
                    templateResult: formatSelect
                });
            } else {
                $("#logger-device-sn").select2({
                    language: "zh-CN",
                    templateResult: formatSelect
                });
            }
        }
    });

    var loggerTable = $('#logger-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "processing": true,
        "pageLength": 30,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": true,
        "serverSide": true,
        "ajax": {
            "url": "/system/queryLoggerData"
        },
        columns: [
            {
                "orderable":      false,
                "data":           null
            },
            { "data": "time"},
            { "data": "username"},
            { "data": "deviceSn"},
            { "data": "operator"},
            { "data": "typeName"}
        ],
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
        var startDate = $("#logger-range-time").val().slice(0,19);
        var endDate = $("#logger-range-time").val().slice(22,41);
        var username = $("#username").val();
        var type = $("#logger-type").val();
        var deviceSn = $("#logger-device-sn").val();
        if( 0 == deviceSn ) {
        	deviceSn="";
        }

        var queryUrl = "/system/queryLoggerData?" + "startDate=" + startDate +
            "&endDate=" + endDate + "&username=" + username + 
            "&type=" + type + "&deviceSn=" + deviceSn;

        loggerTable.ajax.url( queryUrl ).load();

    });

    function exportData( fileType ) {

        var startDate = $("#logger-range-time").val().slice(0,19);
        var endDate = $("#logger-range-time").val().slice(22,41);
        var username = $("#username").val();
        var type = $("#logger-type").val();
        var deviceSn = $("#logger-device-sn").val();
        if( 0 == deviceSn ) {
        	deviceSn="";
        }

        var exportCondition = {
            startTime: startDate,
            endTime: endDate,
            username: username,
            type: type,
            deviceSn:deviceSn
        };

        $("#export-data-btn").attr("disabled","disabled");
        $("#export-data-dropdown-btn").attr("disabled","disabled");

        $.ajax({
            type: "post",
            url: "/util/exportData",
            dataType:"json",
            data:{ type: 2, fileType: fileType,  condition: JSON.stringify(exportCondition) },
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
 
   function exportDataProgress( exoprtInfo ) {

        $.ajax({
            type: "post",
            async:false,
            url: "/util/exportDataProgress",
            dataType:"json",
            data:{ exportId: exoprtInfo },
            success:  function( data ){
                if( true == data.status ) {
                    if( data.data.progress >= 100) {
                        $('#export-progress-dlg').modal("hide");
                        $("#export-data-btn").removeAttr("disabled");
                        $("#export-data-dropdown-btn").removeAttr("disabled");
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


});