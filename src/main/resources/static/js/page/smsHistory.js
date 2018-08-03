/**
 * Created by  jiangqi.yang on 2016/11/21.
 */

$(function () {

    var smsRangeTime = $('#sms-range-time').daterangepicker({
        timePicker: true,
        timePickerIncrement: 1,
        timePicker24Hour: true,
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


    var historySMSTable = $('#history-sms-table').DataTable({
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
            "url": "/system/sms/queryHistorySms"
        },
        columns: [
            {
                "orderable":      false,
                "data":           null
            },
            { "data": "notifyName"},
            { "data": "notifyPhone"},
            {
                "data": "eventType",
                "render" : function (data, type, row ) {
                    var eventTypeText="未知";
                    if( 0 == data ) {
                        eventTypeText = "黑名单预警"
                    } else if( 1 == data ) {
                        eventTypeText = "归属地预警"
                    } else if( 2 == data  ) {
                        eventTypeText = "设备告警";
                    } else if ( 3 == data ) {
                        eventTypeText = "系统事件"
                    }
                    return eventTypeText;
                }
            },
            { "data": "content" },
            { "data": "lastSendTime" }
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
        var startDate = $("#sms-range-time").val().slice(0,19);
        var endDate = $("#sms-range-time").val().slice(22,41);
        var notifyName = $("#sms-notifier-name").val();
        var notifyPhone = $("#sms-notifier-phone").val();

        var queryUrl = "/system/sms/queryHistorySms?" + "startDate=" + startDate +
            "&endDate=" + endDate + "&notifyName=" + notifyName +
            "&notifyPhone=" + notifyPhone ;

        historySMSTable.ajax.url( queryUrl ).load();

    });

});