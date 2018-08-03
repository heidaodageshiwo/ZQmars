/**
 * Created by jiangqi.yang  on 2016/10/29.
 */
var daterangepickerNumber = 0;
function delSiteTableRow( row ) {
    $(row).parent().parent().remove();
    daterangepickerNumber = daterangepickerNumber - 1;
    if(daterangepickerNumber >= 4){
        var dataCollideRangeTime = $('#dataCollide-range-time').daterangepicker({
            timePicker: true,
            timePickerIncrement: 1,
            timePicker24Hour: true,
            timePickerSeconds: true,
            drops: 'up',
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
    }else{
        var dataCollideRangeTime = $('#dataCollide-range-time').daterangepicker({
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
    }
}

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


    //选择时间范围格式
    var dataCollideRangeTime = $('#dataCollide-range-time').daterangepicker({
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

    var sitesSelect = null;
    //查询对应的站点信息和站点对应的设备信息
    $.ajax({
        type: "post",
        url: "/device/getSites",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var siteData = $.map( data.data, function (obj) {
                    obj.text = obj.name;
                    obj.id = obj.sn;
                    return obj;
                });
                sitesSelect =  $("#dataCollide-sites").select2({
                    language: "zh-CN",
                    data: siteData,
                    templateResult: formatSelect
                });
            } else {
                sitesSelect = $("#dataCollide-sites").select2({
                    language: "zh-CN",
                    templateResult: formatSelect
                });
            }
        }
    });

    var dataCollideTaskTable = $('#dataCollide-task-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "processing": true,
        "ajax": {
            "url": "/analysis/dataCollide/getTasks",
            "dataType": "json"
        },
        "columns": [
            { "data": "name" },
            {
                "data": "spaceTime",
                "orderable": false,
                "render": function ( data, type, row ) {
                    var $spaceTimeDom = [];
                    $.each( data, function (i, spaceTimeItem ) {
                        $spaceTimeDom.push('<p><span>'+ spaceTimeItem.rangeTime + '</span><span style="margin-left: 15px" class="text-bold">' + spaceTimeItem.siteName + '</span></p>')
                    });
                    return $spaceTimeDom.join('');
                }
            },
            { "data": "createTime" },
            {
                "orderable":      false,
                "data":           "taskProcess",
                "render": function ( data, type, row ) {
                    var dom = '<span class="badge bg-yellow pull-right">' + data +
                        '%</span><div data-taskid="' + row.id + '"  class="progress progress-xs" > <div class="progress-bar progress-bar-yellow" style="width: ' + data +
                        '%"></div></div>';
                    return dom;
                }
            },
            {
                "orderable":    false,
                "data":         null,
                "render" : function (data, type, row ) {
                    var dom;
                    if( data.taskProcess >= 100 ) {
                        dom = '<a href="/analysis/dataCollide/report/' +  data.id +'" class="btn btn-flat btn-success btn-sm">查看</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-success btn-sm export-task-btn">导出</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-danger btn-sm delete-task-btn">删除</a>';
                    } else {
                        dom = '<a href="/analysis/dataCollide/report/' + data.id + '" class="btn btn-flat btn-success btn-sm disabled">查看</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-success btn-sm export-task-btn disabled">导出</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-danger btn-sm delete-task-btn disabled">删除</a>';
                    }
                    //dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-danger btn-sm delete-task-btn">删除</a>';
                    return dom;
                }
            }
        ],
        "order": [[ 2, "desc" ]],
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
                 daterangepickerNumber = 0;
                 $('#create-dataCollide-dlg').modal('show');
                }
            }
        ],
        "dom": "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });

    $('#create-dataCollide-dlg').on('show.bs.modal', function (e) {
        $("#dataCollide-name").val("");
        $("#dataCollide-space-time-condition tbody").empty();
        $("#add-space-time-condition-table tr:eq(0)").removeClass("hidden");
        $("#add-space-time-condition-table tr:eq(1)").addClass("hidden");
    });


    $("#add-space-time-btn").click(function () {
        $("#add-space-time-condition-table tr:eq(0)").addClass("hidden");
        $("#add-space-time-condition-table tr:eq(1)").removeClass("hidden");
    });

    $("#add-space-time-cancel").click(function () {
        $("#add-space-time-condition-table tr:eq(0)").removeClass("hidden");
        $("#add-space-time-condition-table tr:eq(1)").addClass("hidden");
    });

    //添加时空条件放入缓存
    $("#add-space-time-ok").click(function () {
        //select2选取的信息
        var selSite = $("#dataCollide-sites").select2("data");
        //站点编号
        var siteSN = selSite[0].id;
        //站点名字
        var siteName =  selSite[0].text;

        //选取的时间范围
        var rangeTime = $("#dataCollide-range-time").val();
        var startTime = rangeTime.slice(0,19);
        var endTime =rangeTime.slice(22,41);
        daterangepickerNumber = daterangepickerNumber + 1;
        if(daterangepickerNumber >= 4){
            var dataCollideRangeTime = $('#dataCollide-range-time').daterangepicker({
                timePicker: true,
                timePickerIncrement: 1,
                timePicker24Hour: true,
                timePickerSeconds: true,
                drops: 'up',
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
        }else{
            var dataCollideRangeTime = $('#dataCollide-range-time').daterangepicker({
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
        }
        var trDom = $('<tr></tr>');
        var tdRangeTimeDom = $('<td data-starttime="'+startTime + '" data-endtime="' + endTime + '">' + rangeTime + '</td>');
        var tdNameDom = $('<td data-sn="' + siteSN + '" >' + siteName + '</td>');
        var tdOptDom = $('<td><button class="btn btn-sm btn-flat btn-danger" onclick="delSiteTableRow(this)">删除</button></td>');

        tdRangeTimeDom.appendTo(trDom);
        tdNameDom.appendTo(trDom);
        tdOptDom.appendTo(trDom);
        trDom.appendTo($("#dataCollide-space-time-condition tbody"));

    });



    $("#create-task-confirm").click(function (e) {
        var taskName =  $("#dataCollide-name").val();
        //判断分组名字是否含有非法字符
        var pat=new RegExp("[^a-zA-Z0-9\_\u4e00-\u9fa5]","i");
        var b =  pat.test(taskName);
        if(b) {
            Messenger().post({
                 message: "任务名称含有非法字符,请更换!",
                 type: 'error',
                 showCloseButton: true
            });
            return;
        }
        if( "" == $.trim(taskName)) {
            Messenger().post({
                message:  "任务名称不能为空",
                type: 'error',
                showCloseButton: true
            }) ;
            return ;
        }
        var trDom= $("#dataCollide-space-time-condition tbody tr");
        if( trDom.length < 2 ) {
            Messenger().post({
                message:  "时空条件不能少于2个",
                type: 'error',
                showCloseButton: true
            }) ;
            return ;
        }
        var spaceTimeCondition = [];
        $.each(trDom, function () {
            var timeTd =  $(this).find("td:eq(0)");
            var startTime = timeTd.data("starttime");
            var endTime = timeTd.data("endtime");
            var rangeTime = timeTd.text();
            var siteTd = $(this).find("td:eq(1)");
            var siteSN  = siteTd.data("sn");
            var siteName  = siteTd.text();
            var spaceTime = {
                "startTime": startTime,
                "endTime" : endTime,
                "rangeTime": rangeTime,
                "siteSN" : siteSN,
                "siteName" : siteName
            };
            spaceTimeCondition.push(spaceTime);
        });

        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: "/analysis/dataCollide/createTask",
            data: { 'taskName': taskName, 'spaceTime': JSON.stringify(spaceTimeCondition) },
            //设置超时时间
            timeout: 5000,
            success: function ( data ) {
                $('#create-dataCollide-dlg').modal('hide');
                if( true == data.status ) {
                    Messenger().post({
                        message:  data.message,
                        type: 'success',
                        showCloseButton: true
                    }) ;
                    dataCollideTaskTable.ajax.reload(null,false);
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

        dataCollideTaskTable.ajax.reload();
    }


    if( efenceNotify ) {
        efenceNotify.removeHandleFun("updateTaskNotify");
        efenceNotify.addHandleFun( "updateTaskNotify", taskPushHandler );
    }

    $('#dataCollide-task-table tbody').on( 'click', 'tr a.delete-task-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = dataCollideTaskTable.row( tr ).data();

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
                                dataCollideTaskTable.ajax.reload(null,false);
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
    
    $('#dataCollide-task-table tbody').on( 'click', 'tr a.export-task-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = dataCollideTaskTable.row( tr ).data();

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