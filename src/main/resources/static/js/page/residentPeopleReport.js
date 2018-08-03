/**
 * Created by jiangqi.yang on 2016/12/13.
 */


$(function () {

    var taskId = $("#residentPeople-task-id").val();
    var getDataUrl = "/analysis/residentPeople/report/" + parseInt(taskId) + "/getReportData";

    var residentPeopleChart = new Morris.Donut({
        element: 'residentPeople-chart',
        resize: true,
        data: [
            {label: "常驻人口", value: 0},
            {label: "外来人口", value: 0}
        ],
        hideHover: 'auto'
    });

    $.ajax({
        type: "post",
        url: getDataUrl + "?dataType=total",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                residentPeopleChart.setData([
                    {label: "常驻人口", value: data.data.fixed },
                    {label: "外来人口", value: data.data.period }
                ]);
            }
        }
    });

    var fixedPeopleReportTable = $('#fixedPeople-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "pageLength": 10,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": getDataUrl+ "?dataType=fixed"
        },
        columns: [
            {
                "orderable":      false,
                "data":           null
            },
            { "data": "imsi"}
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

    var periodPeopleReportTable = $('#periodPeople-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "pageLength": 10,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": getDataUrl+ "?dataType=period"
        },
        columns: [
            {
                "orderable":      false,
                "data":           null
            },
            { "data": "imsi"}
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

});