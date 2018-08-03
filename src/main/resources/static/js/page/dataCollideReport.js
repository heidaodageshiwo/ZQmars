/**
 * Created by jiangqi.yang  on 2016/12/12.
 */

$(function () {

    var taskId = $("#dataCollide-task-id").val();
    var getDataUrl = "/analysis/dataCollide/report/" + parseInt(taskId) + "/getReportData";

    var dataCollideReportTable = $('#dataCollide-reportData-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "pageLength": 30,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": getDataUrl
        },
        columns: [
            { "data": "imsi"},
            { "data": "operator"},
            { "data": "cityName"},
            { "data": "siteSN"},
            { "data": "siteName"},
            { "data": "deviceSN"},
            { "data": "deviceName"},
            { "data": "captureTime"}
        ],
        "columnDefs": [
            { "visible": false, "targets": 0 }
        ],
        "drawCallback": function ( settings ) {
            var api = this.api();
            var rows = api.rows( {page:'current'} ).nodes();
            var last=null;

            api.column( 0, {page:'current'} ).data().each( function ( group, i ) {
                if ( last !== group ) {
                    $(rows).eq( i ).before(
                        '<tr class="group bg-black"><td colspan="7">'+group+'</td></tr>'
                    );

                    last = group;
                }
            } );
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

});