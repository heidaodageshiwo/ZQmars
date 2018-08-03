/**
 * Created by jiangqi.yang  on 2016/12/8.
 */


$(function () {

    var taskId = $("#imsiFollow-task-id").val();
    var getDataUrl = "/analysis/imsiFollow/report/" + parseInt(taskId) + "/getReportData";

    var imsiFollowReportDataTable = $('#imsifollow-reportData-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": getDataUrl
        },
        "columns": [
            { "data": "imsi" },
            { "data": "rate" }
        ],
        "language": {
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
        "dom": "<'row'<'col-sm-6'><'col-sm-6'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });

});