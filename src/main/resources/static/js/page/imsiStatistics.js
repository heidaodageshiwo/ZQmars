/**
 * Created by jiangqi.yang  on 2016/10/28.
 */

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

    function downloadFile( url ){
        var IFrameRequest=document.createElement("iframe");
        IFrameRequest.id="IFrameRequest";
        IFrameRequest.src= url;
        IFrameRequest.style.display="none";
        document.body.appendChild(IFrameRequest);
    }
    
    var  $statisticsType = $("#statistics-type").select2();
    var  $statisticsUnit =  $("#statistics-unit").select2();
    var  $statisticsItems = null;
    var siteData=[];
    var deviceData=[];


    $.ajax({
        type: "post",
        url: "/device/getSites",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {

                 $.each(  data.data, function (i, obj) {

                     siteData.push( { id: obj.sn, text: obj.name });
                     var deviceChildren = [];
                     $.each( obj.devices, function ( j, subObj ) {
                         deviceChildren.push({ id: subObj.sn, text: subObj.name });
                     });
                     deviceData.push( { id: obj.sn, text: obj.name, children:deviceChildren });
                });

                $statisticsItems = $("#statistics-items").select2({
                    language: "zh-CN",
                    maximumSelectionLength: 500,
                    data: siteData
                });

            } else {
                $statisticsItems = $("#statistics-items").select2({
                    language: "zh-CN"
                });
            }
        }
    });

    $('#statistics-type').on('select2:select', function (evt) {

        $("#statistics-items").empty();

        if( 1 ==  evt.params.data.id ) {
            $("#statistics-items").select2({
                language: "zh-CN",
                maximumSelectionLength: 500,
                data: siteData
            });
        } else if( 2 == evt.params.data.id )  {
            $("#statistics-items").select2({
                language: "zh-CN",
                maximumSelectionLength: 500,
                data: deviceData
            });
        } else if( 3 == evt.params.data.id) {
            $("#statistics-items").select2({
                language: "zh-CN",
                maximumSelectionLength: 3,
                data: [{ id: 1, text: '中国移动' },
                    { id: 2, text: '中国联通' },
                    { id: 3, text: '中国电信' }]
            });
        }
    });


    var $statisticsRangeTime = $('#statistics-range-time').daterangepicker({
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

    var statisticsTaskTable = $('#statistics-task-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "processing": true,
        "ajax": {
            "url": "/analysis/imsiStatistics/getTasks",
            "dataType": "json"
        },
        "dataSrc": "",
        "columns": [
            {
                "class":          "details-control",
                "orderable":      false,
                "data":           null,
                "defaultContent": ""
            },
            { "data": "name" },
            { "data": "type" },
            { "data": "unit" },
            {
                "data": "rangeTime",
                "orderable":  false,
            },
            { "data": "createTime" },
            {
                "data": "taskProcess",
                "defaultContent": "",
                "orderable": false,
            },
            {
                "orderable":      false,
                "data":           null,
                "defaultContent": ""
            }
        ],
        "columnDefs": [
            {
                "render": function ( data, type, row ) {
                    var typeTxt="未知";
                    switch( Number(data) ) {
                        case 1:
                            typeTxt="站点";
                            break;
                        case 2:
                            typeTxt="设备";
                            break;
                        case 3:
                            typeTxt="运营商";
                            break
                    }
                    return typeTxt;
                },
                "targets": 2
            },
            {
                "render": function ( data, type, row ) {
                    var unitTxt="未知";
                    switch( Number(data) ) {
                        case 1:
                            unitTxt="天";
                            break;
                        case 2:
                            unitTxt="小时";
                            break;
                        case 3:
                            unitTxt="分钟";
                            break
                    }
                    return unitTxt;
                },
                "targets": 3
            },
            {
                "render": function ( data, type, row ) {
                    var dom = '<span class="badge bg-yellow pull-right">' + data +
                            '%</span><div data-taskid="' + row.id + '"  class="progress progress-xs" > <div class="progress-bar progress-bar-yellow" style="width: ' + data +
                            '%"></div></div>';
                    return dom;
                },
                "targets": 6
            },
            {
                "render" : function (data, type, row ) {
                    var dom;
                    if( data.taskProcess >= 100 ) {
                        dom = '<a href="/analysis/imsiStatistics/report/' +  data.id +'" class="btn btn-flat btn-success btn-sm">查看</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-success btn-sm export-task-btn">导出</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-danger btn-sm delete-task-btn">删除</a>';
                    } else {
                        dom = '<a href="/analysis/imsiStatistics/report/' + data.id + '" class="btn btn-flat btn-success btn-sm disabled">查看</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-success btn-sm  export-task-btn disabled">导出</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-danger btn-sm delete-task-btn disabled">删除</a>';
                    }
                    //dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-danger btn-sm delete-task-btn">删除</a>';
                    return dom;
                },
                "targets": 7,
            }
        ],
        "order": [[ 5, "desc" ]],
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
        buttons: [
            {
                text: "创建统计",
                className: "btn bg-navy btn-flat",
                action: function () {
                    $('#statistics-task-form')[0].reset();
                    $('#create-statistics-dlg').modal('show');
                }
            }
        ],
        "dom": "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });

    function format ( d ) {
        var detailText = '统计项:  ';
        $.each(d.items, function ( i , value) {
            detailText = detailText + value.name + ", ";
        });
        detailText = detailText +  '<br>';
        return detailText;
    }


    var detailRows = [];

    $('#statistics-task-table tbody').on( 'click', 'tr td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = statisticsTaskTable.row( tr );

        var idx = $.inArray( tr.attr('id'), detailRows );

        if ( row.child.isShown() ) {
            tr.removeClass( 'details' );
            row.child.hide();

            // Remove from the 'open' array
            detailRows.splice( idx, 1 );
        }
        else {
            tr.addClass( 'details' );
            row.child( format( row.data() ) ).show();

            // Add to the 'open' array
            if ( idx === -1 ) {
                detailRows.push( tr.attr('id') );
            }
        }
    } );


    var statisticsTaskFormValidate = $( "#statistics-task-form" ).validate( {
        rules: {
            taskName: {
                required: true,
                stringCheck:true
            },
            rangeTime: {
                required: true
            },
            type:{
                required: true
            },
            items:{
                required: true
            },
            unit: {
                required: true
            }
        },
        messages: {
            taskName: {
                required: "请输入任务名称",
                stringCheck:"只能包括中文字、英文字母、数字和下划线!"
            },
            rangeTime: {
                required: "请输入时间范围"
            },
            type:{
                required: "请选择统计类型"
            },
            items:{
                required:  "请选择统计项目"
            },
            unit: {
                required:  "请选择统计单位"
            }
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            // Add the `help-block` class to the error element
            error.addClass( "help-block" );
            if ( element.prop( "type" ) === "checkbox" ) {
                error.insertAfter( element.parent( "label" ) );
            } else if( element[0].tagName == "SELECT") {
                error.insertAfter( element.next( "span" ) );
            } else {
                error.insertAfter( element );
            }
        },
        highlight: function ( element, errorClass, validClass ) {
            $( element ).parents( ".form-group" ).addClass( "has-error" ).removeClass( "has-success" );
        },
        unhighlight: function (element, errorClass, validClass) {
            if( validClass && ("valid" == validClass) ) {
                $( element ).parents( ".form-group" ).addClass( "has-success" ).removeClass( "has-error" );
            } else {
                $( element ).parents( ".form-group" ).removeClass( "has-success" );
                $( element ).parents( ".form-group" ).removeClass( "has-error" );
            }
        }
    } );

    statisticsTaskTable.on( 'draw', function () {
        $.each( detailRows, function ( i, id ) {
            $('#'+id+' td.details-control').trigger( 'click' );
        } );
    } );

    $('#create-statistics-dlg').on('shown.bs.modal', function (e) {
        statisticsTaskFormValidate.resetForm();
        $statisticsItems.val(null).trigger("change");
    });

    $("#create-task-btn").click(function () {
        if( statisticsTaskFormValidate.form()) {

            var itemsData =   $statisticsItems.select2("data");
            var items = [];
            items.push('[');
            $.each(itemsData, function ( i, item ) {
                items.push('{ "value":"' + item.id + '", "name":"' + item.text + '" },');
            });
            items.push(']');

            var taskName = $("#statistics-name").val();
            var type = $statisticsType.select2("data")[0].id;
            var unit = $statisticsUnit.select2("data")[0].id;
            var startDate = $("#statistics-range-time").val().slice(0,19);
            var endDate = $("#statistics-range-time").val().slice(22,41);

            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: "/analysis/imsiStatistics/createTask",
                data: { taskName:taskName,  type: type, unit: unit, items: items.join(""), startDate: startDate, endDate: endDate },
                timeout: 5000,
                success: function ( data ) {
                    $('#create-statistics-dlg').modal('hide');
                    if( true == data.status ) {
                        Messenger().post({
                            message:  data.message,
                            type: 'success',
                            showCloseButton: true
                        }) ;
                        statisticsTaskTable.ajax.reload(null,false);
                    } else {
                        Messenger().post({
                            message:  data.message,
                            type: 'error',
                            showCloseButton: true
                        }) ;
                    }
                }
            });
        }
    });

    function taskPushHandler( data ) {

        if( data.count != undefined ) {
            $("#task-notify-count").text(data.count);
            $("#task-notify li:eq(0)").text("您有" + data.count +"条任务");
        }

        var $taskUl = $("#task-notify li:eq(1) ul");
        $taskUl.empty();
        $.each(data.tasks, function ( index, value ) {

            var liDom= [];
            liDom.push('<li>');
            liDom.push('<a href="' +  value.url  +'">');
            liDom.push('<h3>' + value.info);
            liDom.push('<small class="pull-right">' +  value.progress +  '%</small>');
            liDom.push('</h3>');
            liDom.push('<div data-taskid=' +  value.id + ' class="progress xs">');
            liDom.push('<div class="progress-bar progress-bar-green" style="width:'+  value.progress + '%" role="progressbar" aria-valuenow="'
                + value.progress + '" aria-valuemin="0" aria-valuemax="100">');
            liDom.push('<span class="sr-only">' + value.progress + '% Complete</span>');
            liDom.push('</div>');
            liDom.push('</div>');
            liDom.push('</a>');
            liDom.push('</li>');

            $taskUl.prepend(liDom.join(''));
        });

        statisticsTaskTable.ajax.reload();
    }


    if( efenceNotify ) {
        efenceNotify.removeHandleFun("updateTaskNotify");
        efenceNotify.addHandleFun( "updateTaskNotify", taskPushHandler );
    }

    $('#statistics-task-table tbody').on( 'click', 'tr a.delete-task-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = statisticsTaskTable.row( tr ).data();

        var messageText = '是否删除任务"' + rowData.name + '"';

        bootbox.confirm({
            size: "small",
            message: messageText,
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

                    $.ajax({
                        type: "post",
                        url: "/analysis/deleteTask",
                        data: { 'taskId':  rowData.id },
                        dataType:"json",
                        success:  function( data ){
                            if( true == data.status ) {
                                Messenger().post({
                                    message: data.message,
                                    type: 'success',
                                    showCloseButton: true
                                });
                                statisticsTaskTable.ajax.reload(null,false);
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
    } );
    
    $('#statistics-task-table tbody').on( 'click', 'tr a.export-task-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = statisticsTaskTable.row( tr ).data();

        var messageText = '是否导出"' + rowData.name + '"';

        bootbox.confirm({
            size: "small",
            message: messageText,
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

                    $.ajax({
                        type: "post",
                        url: "/analysis/exportTask",
                        data: { 'taskId':  rowData.id },
                        dataType:"json",
                        success:  function( data ){
                            if( true == data.status ) {
                                 Messenger().post({
                                    message: data.message,
                                    type: 'success',
                                    showCloseButton: true
                                 });

                               var url = window.location.protocol + "//" + window.location.host + "/analysis/exportZip?formData=" + rowData.id;
                           //    var url = "http://" + window.location.host + "/analysis/exportZip?formData=" + rowData.id;
                               window.location.href = url;
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
    } );

});