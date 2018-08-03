/**
 * Created by jiangqi.yang  on 2016/12/13.
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
    
    var residentPeopleRangeTime = $('#residentPeople-range-time').daterangepicker({
        startDate:  moment().subtract(29, 'days'),
        endDate:  moment(),
        ranges: {
            '最近半年': [moment().subtract(6, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
            '最近一年': [moment().subtract(12, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
            '最近一年半': [moment().subtract(18, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
            '最近两年': [moment().subtract(24, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
            '最近三年': [moment().subtract(36, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        },
        locale: {
            applyLabel: '确定',
            cancelLabel: '取消',
            format: 'YYYY-MM-DD HH:mm:ss',
            fromLabel : '起始时间',
            toLabel : '结束时间',
            customRangeLabel : '自定义',
            daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
            monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月' ]
        }
    });

    $.ajax({
        type: "post",
        url: "/util/getAreas",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var areaData = $.map( data.data, function (obj) {
                    obj.text = obj.areaName;
                    obj.id = obj.areaId;
                    return obj;
                });
                $("#residentPeople-area").select2({
                    language: "zh-CN",
                    data: areaData
                });
            }
        }
    });


    var residentPeopleTaskTable = $('#residentPeople-task-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "processing": true,
        "ajax": {
            "url": "/analysis/residentPeople/getTasks",
            "dataType": "json"
        },
        "dataSrc": "",
        "columns": [
            { "data": "name" },
            { "data": "areaName" },
            {
                "data": "rangeTime",
                "orderable":   false,
            },
            { "data": "createTime" },
            {
                "data": "taskProcess",
                "defaultContent": "",
                "orderable":      false,
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
                    var dom = '<span class="badge bg-yellow pull-right">' + data +
                        '%</span><div data-taskid="' + row.id + '" class="progress progress-xs" > <div class="progress-bar progress-bar-yellow" style="width: ' + data +
                        '%"></div></div>';
                    return dom;
                },
                "targets": 4
            },
            {
                "render" : function (data, type, row ) {
                    var dom;
                    if( data.taskProcess >= 100 ) {
                        dom = '<a href="/analysis/residentPeople/report/' +  data.id +'" class="btn btn-flat btn-success btn-sm">查看</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-success btn-sm export-task-btn ">导出</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-danger btn-sm delete-task-btn">删除</a>';
                    } else {
                        dom = '<a href="/analysis/residentPeople/report/' + data.id + '" class="btn btn-flat btn-success btn-sm disabled">查看</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-success btn-sm export-task-btn disabled">导出</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-danger btn-sm delete-task-btn disabled">删除</a>';
                    }
                    //dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-danger btn-sm delete-task-btn">删除</a>';
                    return dom;
                },
                "targets": 5,
            }
        ],
        "order": [[ 3, "desc" ]],
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
                text: "创建",
                className: "btn bg-navy btn-flat",
                action: function () {
                    $("#task-name").val("");
                    $('#create-residentPeople-dlg').modal('show');
                }
            }
        ],
        "dom": "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });


    var residentPeopleTaskFormValidate = $( "#residentPeople-task-form" ).validate( {
        rules: {
            taskName: {
                required: true,
                stringCheck:true
            },
            area: {
                required: true
            },
            rangeTime:{
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
            area:{
                required: "请选择区域"
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

    $('#create-residentPeople-dlg').on('shown.bs.modal', function (e) {
        residentPeopleTaskFormValidate.resetForm();
    });

    $("#create-task-btn").click(function (e) {

        if( residentPeopleTaskFormValidate.form()) {
            var taskName = $("#task-name").val();
            // if( '' == $.trim(taskName)) {
            //     Messenger().post({
            //         message:  "请输入任务名称",
            //         type: 'error',
            //         showCloseButton: true
            //     }) ;
            //     return ;
            // }
            // var stringCheck = /^([a-zA-Z0-9\u4e00-\u9fa5-_])+([ ])*$/;
            // if( !stringCheck.test(taskName) ){
            //     Messenger().post({
            //         message:  "任务名称只能包括中文字、英文字母、数字和下划线!",
            //         type: 'error',
            //         showCloseButton: true
            //     }) ;
            //     return;
            // }

            var selArea = $("#residentPeople-area").select2("data");
            var areaId = selArea[0].id;
            var areaName =  selArea[0].text;

            var rangeTime = $("#residentPeople-range-time").val();
            var startTime = rangeTime.slice(0,19);
            var endTime =rangeTime.slice(22,41);

            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: "/analysis/residentPeople/createTask",
                data: {
                    'taskName': taskName,
                    'areaId': areaId,
                    'areaName': areaName,
                    'startTime': startTime,
                    'endTime': endTime
                },
                timeout: 5000,
                success: function ( data ) {
                    $('#create-residentPeople-dlg').modal('hide');
                    if( true == data.status ) {
                        Messenger().post({
                            message:  data.message,
                            type: 'success',
                            showCloseButton: true
                        }) ;
                        residentPeopleTaskTable.ajax.reload(null,false);
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

        residentPeopleTaskTable.ajax.reload();
    }


    if( efenceNotify ) {
        efenceNotify.removeHandleFun("updateTaskNotify");
        efenceNotify.addHandleFun( "updateTaskNotify", taskPushHandler );
    }

    $('#residentPeople-task-table tbody').on( 'click', 'tr a.delete-task-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = residentPeopleTaskTable.row( tr ).data();

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
                                residentPeopleTaskTable.ajax.reload(null,false);
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
    
    $('#residentPeople-task-table tbody').on( 'click', 'tr a.export-task-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = residentPeopleTaskTable.row( tr ).data();

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
                                 //var url = "http://" + window.location.host + "/analysis/exportZip?formData=" + rowData.id;
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