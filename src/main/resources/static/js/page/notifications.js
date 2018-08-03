/**
 * Created by jiangqi.yang on 2016/12/14.
 */


$(function () {

    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
       if( "#unreadNotifyTab" == $(e.target).attr("href") ){
           $("#mark-all-read-btn").removeClass("disabled");
           $("#mark-all-read-btn").removeAttr("disabled");
       } else {
           $("#mark-all-read-btn").addClass("disabled");
           $("#mark-all-read-btn").attr("disabled", "disabled");
       }
    });

    $("#mark-all-read-btn").click(function (e) {
        bootbox.confirm({
            size: "small",
            message: "是否全部标记为已读",
            buttons: {
                confirm: {
                    label: '是',
                    className: 'btn-primary'
                },
                cancel: {
                    label: '否',
                    className: ''
                }
            },
            callback: function (result) {
                if( result ) {
                    var allData = unReadNotifyTable.rows().data();
                    var ids = [];
                    $.each(allData, function () {
                       ids.push( this.id);
                    });

                    $.ajax({
                        type: "post",
                        url: "/user/markNotificationRead",
                        data: { 'ids': ids },
                        dataType:"json",
                        success:  function( data ){
                            if( true == data.status ) {
                                unReadNotifyTable.ajax.reload(null,false);
                                notifyHistoryTable.ajax.reload(null,false);
                            } else {
                                Messenger().post({
                                    message: data.message,
                                    type: 'error',
                                    showCloseButton: true
                                });
                            }
                        }
                    });

                }
            }
        });
    });

    var unReadNotifyTable = $('#unread-notify-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "pageLength": 30,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": "/user/getNotifications?isRead=0"
        },
        columns: [
            {
                "orderable":      false,
                "data":           null
            },
            { "data": "message" },
            { "data": "createTime" },
            {
                "orderable":    false,
                "data":         null,
                "render" : function (data, type, row ) {
                    var dom;
                    dom = '<button  data-message-id="' + data.id + '" class="btn btn-flat btn-sm mark-read-btn">标记为已读</button>';
                    return dom;
                }

            }
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

    $('#unread-notify-table tbody').on( 'click', 'tr button.mark-read-btn', function () {
        var buttonDom = this;
        var tr = $(buttonDom).closest('tr');
        var rowData = unReadNotifyTable.row( tr ).data();

        var ids = [];
        ids.push(rowData.id);

        $.ajax({
            type: "post",
            url: "/user/markNotificationRead",
            data: { 'ids': ids },
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    unReadNotifyTable.ajax.reload(null,false);
                    notifyHistoryTable.ajax.reload(null,false);
                } else {
                    Messenger().post({
                        message: data.message,
                        type: 'error',
                        showCloseButton: true
                    });
                }
            }
        });

    });

    var notifyHistoryTable = $('#notify-history-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "pageLength": 30,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": false,
        "serverSide": true,
        "ajax": {
            "url": "/user/getNotifications?isRead=1"
        },
        columns: [
            {
                "orderable":      false,
                "data":           null
            },
            { "data": "message" },
            { "data": "createTime" }
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