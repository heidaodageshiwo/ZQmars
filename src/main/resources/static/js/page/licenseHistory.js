/**
 * Created by  jiangqi.yang on 2016/12/6.
 */

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

    var licenseRangeTime = $('#license-range-time').daterangepicker({
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

    $("#license-status").select2({
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
                $("#license-device-sn").select2({
                    language: "zh-CN",
                    data: deviceData,
                    templateResult: formatDeviceSelect
                });
            } else {
                $("#license-device-sn").select2({
                    language: "zh-CN",
                    templateResult: formatDeviceSelect
                });
            }
        }
    });

    var historyLicenseTable = $('#history-dev-license-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "processing": true,
        "pageLength": 30,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": "/device/license/queryHistory"
        },
        columns: [
            { "data": "siteName" },
            { "data": "deviceSN" },
            { "data": "deviceName" },
            { "data": "updateTime" },
            { "data": "bExpireTime" },
            { "data": "aExpireTime" },
            {
                "data": "status",
                "render" : function (data, type, row ) {
                    var statusTxt= "失败";
                    if( 0 == data ) {
                        statusTxt= "成功"
                    }
                    return statusTxt;
                }
            }
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
        var startDate = $("#license-range-time").val().slice(0,19);
        var endDate = $("#license-range-time").val().slice(22,41);
        var deviceSN = $("#license-device-sn").val();
        console.log("daaa");
        if( 0 == deviceSN ) {
            deviceSN="";
        }
        var status = $("#license-status").val();

        console.log(deviceSN);
        console.log(status);

        var queryUrl = "/device/license/queryHistory?" + "startDate=" + startDate +
            "&endDate=" + endDate + "&deviceSN=" + deviceSN + "&status=" + status;

        console.log(queryUrl);

        historyLicenseTable.ajax.url( queryUrl ).load();

    });

    

});