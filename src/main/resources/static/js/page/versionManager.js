/**
 * Created by jiangqi.yang on 2016/12/1.
 */

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

    var deviceVersionTable = $('#device-version-table').DataTable({
        paging: true,
        lengthChange: false,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,
        processing: true,
        ajax: "/device/version/getDevVerList",
        dataSrc: "",
        columns: [
            { "data": "siteName" },
            { "data": "deviceSN" },
            { "data": "deviceName" },
            { "data": "version" },
            { "data": "fpgaVersion" },
            { "data": "bbuVersion" },
            { "data": "swVersion" },
            {
                "orderable":      false,
                "data":            null,
                "render" : function (data, type, row ) {
                    var dom = '<button data-sn="' +  + data.deviceSN + '" class="btn btn-flat btn-sm bg-navy notify-upgrade-btn">下载版本</button>';
                    return dom;
                }
            }
        ],
        order: [[ 3, 'desc' ]],
        language: {
            "lengthMenu": "每页显示 _MENU_ 记录",
            "zeroRecords": "对不起-没有数据发现",
            "info": "显示第_PAGE_页, 共_PAGES_页",
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
        },
        dom: "<'row'<'col-sm-4'><'col-sm-8'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });

    var upgradeRangeTime = $('#upgrade-range-time').daterangepicker({
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

    function formatVersionSelect ( data ) {
        if (!data.id) { return data.text; }
        var $pDom = $(
            '<div style="padding: 0 4px;"><p class="row"><span class="col-xs-4">' + data.text
             + '</span><span class="col-xs-8 pull-right text-gray" ' +
            'style="text-overflow: ellipsis; overflow: hidden; white-space:nowrap">' + data.remark + '</span></p></div>'
        );
        return $pDom;
    };

    $.ajax({
        type: "post",
        url: "/device/version/getLibraries",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var versionDevData = $.map( data.data, function (obj) {
                    obj.text = obj.version;
                    obj.id = obj.id;

                    return obj;
                });
                $("#upgrade-dlg-version-id").select2({
                    language: "zh-CN",
                    data: versionDevData,
                    templateResult: formatVersionSelect
                });
                var historyVerData = versionDevData;
                historyVerData.unshift({ id: -1, text: "不限",  remark:""});
                $("#upgrade-device-version").select2({
                    language: "zh-CN",
                    data: historyVerData,
                    templateResult: formatVersionSelect
                });
            }
        }
    });

    $('#device-version-table tbody').on( 'click', 'tr button.notify-upgrade-btn', function () {
        var buttonDom = this;
        var tr = $(buttonDom).closest('tr');
        var rowData = deviceVersionTable.row(tr).data();

        $("#upgrade-dlg-device-info").text( rowData.deviceName + '(' + rowData.deviceSN + ')');
        
        $("#upgrade-dlg-device-sn").val(rowData.deviceSN);

        $("#upgrade-dlg").modal("show");
        
    });

    $("#upgrade-ok-btn").click(function () {

        var versionId = $("#upgrade-dlg-version-id").val();
        var deviceSN = $("#upgrade-dlg-device-sn").val();
        if(  (''==versionId)  || (''==deviceSN) ){
            Messenger().post({
                message:  "参数错误",
                type: 'error',
                showCloseButton: true
            }) ;
            return;
        }

        var snArray = [];
        snArray.push(deviceSN);

        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: "/device/version/upgradeDevice",
            data: { versionId: versionId,  sn:snArray },
            timeout: 5000,
            success: function ( data ) {
                $('#upgrade-dlg').modal('hide');
                if( true == data.status ) {
                    Messenger().post({
                        message:  data.message,
                        type: 'success',
                        showCloseButton: true
                    }) ;
                } else {
                    Messenger().post({
                        message:  data.message,
                        type: 'error',
                        showCloseButton: true
                    }) ;
                }
            }
        });

    });

    $("#upgrade-status").select2({
        language: "zh-CN",
        minimumResultsForSearch: Infinity
    });

    function formatDeviceSelect ( data ) {
        if (!data.id) { return data.text; }
        var $pDom = $(
            '<p><span>' + data.text + '</span><span class="pull-right text-gray">' + data.id + '</span></p>'
        );
        return $pDom;
    }

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
                $("#upgrade-device-sn").select2({
                    language: "zh-CN",
                    data: deviceData,
                    templateResult: formatDeviceSelect
                });
            } else {
                $("#upgrade-device-sn").select2({
                    language: "zh-CN",
                    templateResult: formatDeviceSelect
                });
            }
        }
    });

    var historyVersionTable = $('#history-dev-version-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "pageLength": 30,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": "/device/version/queryHistory"
        },
        columns: [
            { "data": "siteName" },
            { "data": "deviceSN" },
            { "data": "deviceName" },
            { "data": "oldVersion" },
            { "data": "newVersion" },
            { "data": "upgradeTime" },
            { "data": "successTime" },
            { "data": "status" }
            /*{
                "data": "status",
                "render" : function (data, type, row ) {
                    var statusTxt= "失败";
                    if( 0 == data ) {
                        statusTxt= "成功"
                    }
                    return statusTxt;
                }
            }*/
        ],
        "createdRow": function( row, data, dataIndex ) {
            if ( data.status != 0  ) {
                $(row).addClass( 'text-red' );
            }
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
        var startDate = $("#upgrade-range-time").val().slice(0,19);
        var endDate = $("#upgrade-range-time").val().slice(22,41);
        var deviceSN = $("#upgrade-device-sn").val();
        if( 0 == deviceSN ) {
            deviceSN="";
        }
        var version = $("#upgrade-device-version").val();
        if( "-1" == version ) {
            version="";
        }
        var status = $("#upgrade-status").val();

        var queryUrl = "/device/version/queryHistory?" + "startDate=" + startDate +
            "&endDate=" + endDate + "&deviceSN=" + deviceSN +
            "&version=" + version + "&status=" + status;

        historyVersionTable.ajax.url( queryUrl ).load();

    });


});