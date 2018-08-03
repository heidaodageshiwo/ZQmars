/**
 * Created by jiangqi.yang  on 2016/10/29.
 */
 var daterangepickerNumber = 0;
 var upDaterangepickerNumber = 0;
//添加时空条件删除
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
            maxDate: new Date().toLocaleDateString() + ' 23:59:59',
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
            maxDate: new Date().toLocaleDateString() + ' 23:59:59',
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
//查看更新删除
function delSiteTableRow1( row ) {
    $(row).parent().parent().remove();
    upDaterangepickerNumber = upDaterangepickerNumber -1;
    if(upDaterangepickerNumber >= 4){
        var dataCollideRangeTime = $('#up-dataCollide-range-time').daterangepicker({
            timePicker: true,
            timePickerIncrement: 1,
            timePicker24Hour: true,
            timePickerSeconds: true,
            drops: 'up',
            maxDate: new Date().toLocaleDateString() + ' 23:59:59',
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
        var dataCollideRangeTime = $('#up-dataCollide-range-time').daterangepicker({
            timePicker: true,
            timePickerIncrement: 1,
            timePicker24Hour: true,
            timePickerSeconds: true,
            maxDate: new Date().toLocaleDateString() + ' 23:59:59',
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

    //选择时间范围格式
    var dataCollideRangeTime = $('#dataCollide-range-time').daterangepicker({
        timePicker: true,
        timePickerIncrement: 1,
        timePicker24Hour: true,
        timePickerSeconds: true,
        maxDate: new Date().toLocaleDateString() + ' 23:59:59',
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

     //选择时间范围格式
    var dataCollideRangeTime = $('#up-dataCollide-range-time').daterangepicker({
        timePicker: true,
        timePickerIncrement: 1,
        timePicker24Hour: true,
        timePickerSeconds: true,
        maxDate: new Date().toLocaleDateString() + ' 23:59:59',
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

    /*var sitesSelect = null;
    //查询对应的站点信息
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
                sitesSelect =  $("#up-dataCollide-sites").select2({
                    language: "zh-CN",
                    data: siteData,
                    templateResult: formatSelect
                });
            } else {
                sitesSelect = $("#dataCollide-sites").select2({
                    language: "zh-CN",
                    templateResult: formatSelect
                });
                sitesSelect = $("#up-dataCollide-sites").select2({
                    language: "zh-CN",
                    templateResult: formatSelect
                });
            }
        }
    });*/

    var setting = {
        check: {
            enable: true,
            chkStyle: "radio",  //单选框
            radioType: "all"   //对所有节点设置单选
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick,
            onCheck: onCheck
        }
    };

    function onCheck(event, treeId, treeNode){
        if(treeId == 'treeDemo'){
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        }
        if(treeId == 'up-treeDemo'){
            var treeObj = $.fn.zTree.getZTreeObj("up-treeDemo");
        }
        var nodes = treeObj.getCheckedNodes(true);
        if(nodes.length == 0){
            treeObj.cancelSelectedNode();
        }
        treeObj.selectNode(nodes[0]);
    }

    function onClick(e, treeId, treeNode) {
        if(treeId == 'treeDemo'){
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        }
        if(treeId == 'up-treeDemo'){
            var zTree = $.fn.zTree.getZTreeObj("up-treeDemo");
        }
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
    }

    $(document).ready(function(){
        $.ajax({
            type: "post",
            url: "/analysis/queryZtree",
            dataType: "json",
            success: function(data){
                $.fn.zTree.init($("#treeDemo"), setting, data);
                $.fn.zTree.init($("#up-treeDemo"), setting, data);
            }
        });
    });

    $('#site-text').click(function(){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length != 0){
            treeObj.checkNode(nodes[0], false, false);
        }
        treeObj.cancelSelectedNode();
        $('#site-dlg').modal('show');
    });

    var zTreeSiteSn = '';
    var zTreeSiteName = '';
    $('#site_submit').click(function () {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getCheckedNodes(true);
        if(0 == nodes.length){
            Messenger().post({
                message: "请选择站点",
                type: 'error',
                showCloseButton: true
            });
            return;
        }
        zTreeSiteSn = nodes[0].id;
        zTreeSiteName = nodes[0].name;
        $('#site-dlg').modal('hide');
        $('#site-text').val(zTreeSiteName);
    });

    $('#up-site-text').click(function(){
        var treeObj = $.fn.zTree.getZTreeObj("up-treeDemo");
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length != 0){
            treeObj.checkNode(nodes[0], false, false);
        }
        treeObj.cancelSelectedNode();
        $('#up-site-dlg').modal('show');
    });

    var upzTreeSiteSn = '';
    var upzTreeSiteName = '';
    $('#up-site_submit').click(function () {
        var treeObj = $.fn.zTree.getZTreeObj("up-treeDemo");
        var nodes = treeObj.getCheckedNodes(true);
        if(0 == nodes.length){
            Messenger().post({
                message: "请选择站点",
                type: 'error',
                showCloseButton: true
            });
            return;
        }
        upzTreeSiteSn = nodes[0].id;
        upzTreeSiteName = nodes[0].name;
        $('#up-site-dlg').modal('hide');
        $('#up-site-text').val(upzTreeSiteName);
    });

    setInterval( function () {
        //获取所有进度
        var cells = dataCollideTaskTable.cells(null,4).data();
        var allProgress = 0;
        for(var i = 0;i < cells.length;i++){
            allProgress = allProgress + cells[i];
        }
        var result = allProgress/cells.length;
        if(100 == result){
            return;
        }
        $.ajax({
            type: "post",
            url: "/analysis/queryDataCollide",
            dataType: "json",
            success: function(data){
                var tableData = dataCollideTaskTable.rows().data();
                for(var i = 0;i < tableData.length;i++){
                    for(var x = 0;x<data.data.length;x++){
                        if((tableData[i].id == data.data[x].id) && (tableData[i].taskProcess != data.data[x].taskProcess)){
                            var progressBar =document.getElementById(data.data[x].id);
                            progressBar.style.width = data.data[x].taskProcess + "%";
                            progressData = "#number" + data.data[x].id;
                            $(progressData).html(data.data[x].taskProcess + "%");
                            if(100 == data.data[x].taskProcess){
                                var details = '#details'+ data.data[x].id;
                                var expor = '#export'+ data.data[x].id;
                                $(details).removeAttr("style");
                                $(expor).removeAttr("style");
                            }
                        }
                    }
                }
            }
        });
    }, 2000 );

    var dataCollideTaskTable = $('#dataCollide-task-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "processing": true,
        "fixedColumns": true,
        "ajax": {
            "url": "/analysis/queryDataCollide",
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
                        $spaceTimeDom.push('<p><span>'+ spaceTimeItem.rangeTime + '</span><span style="margin-left: 8px" class="text-bold">' + spaceTimeItem.siteName + '</span></p>')
                    });
                    return $spaceTimeDom.join('');

                }
            },
            { "data": "createTime" },
            { "data": "remark" },
            {
                "orderable":      false,
                "data":           "taskProcess",
                "render": function ( data, type, row ) {
                    var dom = '<span id="number'+row.id+'" class="badge bg-yellow pull-right">' + data +
                        '%</span><div data-taskid="' + row.id + '" class="progress progress-xs" > <div class="progress-bar progress-bar-yellow" id="'+ row.id +'" style="width: ' + data +
                        '%"></div></div>';
                    return dom;
                }
            },
            {
                "orderable":    false,
                "data":         null,
                "render" : function (data, type, row ) {
                    var dom;
                    if( 2 == data.jobStatus ) {
                        dom = '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-primary btn-sm look-task-btn">创建新任务</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-warning btn-sm show-task-btn">详情</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat bg-purple btn-sm createCsv-task-btn">导出csv</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-danger btn-sm delete-task-btn">删除</a>';
                    } else {
                        dom = '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-primary btn-sm look-task-btn" id="tr'+data.id+'">创建新任务</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-warning btn-sm show-task-btn" style="display: none;" id="details'+ row.id +'">详情</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat bg-purple btn-sm createCsv-task-btn" style="display: none;" id="export'+ row.id +'">导出csv</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-danger btn-sm delete-task-btn">删除</a>';
                    }
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
                text: "创建任务",
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
        //$("#site-text").attr("placeholder","选择站点");
        $("#add-space-time-condition-table tr:eq(0)").addClass("hidden");
        $("#add-space-time-condition-table tr:eq(1)").removeClass("hidden");
    });

    $("#up-space-time-btn").click(function () {
       $("#up-space-time-condition-table tr:eq(0)").addClass("hidden");
       $("#up-space-time-condition-table tr:eq(1)").removeClass("hidden");
    });

    $("#add-space-time-cancel").click(function () {
        $("#add-space-time-condition-table tr:eq(0)").removeClass("hidden");
        $("#add-space-time-condition-table tr:eq(1)").addClass("hidden");
    });

     $("#up-add-space-time-cancel").click(function () {
         $("#up-space-time-condition-table tr:eq(0)").removeClass("hidden");
         $("#up-space-time-condition-table tr:eq(1)").addClass("hidden");
     });

    $("#button_close").click(function () {
       // 代码如下: showSites-dlg是第二个模态框的id，xq-dataCollide-dlg是第一个模态框的id
        $('#showSites-dlg').on('hidden.bs.modal', function() {
            $('#xq-dataCollide-dlg').css({'overflow-y':'scroll'});
        });
    });

      $('#dataCollide-task-table tbody').on( 'click', 'tr a.createCsv-task-btn', function (rowData) {
          $('#showWait').modal('show');
          var tr = $(this).closest('tr');
          var obj = dataCollideTaskTable.row( tr ).data();
          var taskId = obj.id;
          var taskName = obj.name;
          $.ajax({
               type: 'post',
               dataType: 'json',
               url: "/analysis/createFile",
               data: { taskId: taskId, taskName:taskName },
               success: function ( data ) {
                   $('#showWait').modal('hide');
                   if( true == data.status ) {
                        var url = window.location.protocol + "//" + window.location.host + "/analysis/exportFile?formData=" + data.result;
                        window.location.href = url;
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

    //展示上号站点信息
    var showSiteDataTable;
    $('#xq-dataCollide-space-time-condition tbody').on( 'click', 'tr a.showThisRowData-btn', function (rowData) {
        $("#showSites-dlg").modal("show");
        $("#showSites-condition tbody").html('');
        var tr = $(this).closest('tr');
        var obj = showDataTable.row( tr ).data();
        //获取imsi号码
        var imsi = obj.imsi;
        //获取此任务ID
        var taskId = rowData.currentTarget.dataset.taskid;
        var param = {
            imsi : imsi,
            taskId : taskId,
        };
        showSiteDataTable = $('#showSites-condition').DataTable( {
            "sDom": "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
                     "<'row'<'col-sm-12'tr>>" +
                     "<'row'<'col-sm-1'l><'col-sm-5'i><'col-sm-6'p>>",
            "sServerMethod":"POST",
            "searching":false,
            "bFilter":true,
            "bDestroy": true,
            "bServerSide": true,
            "bProcessing":true,
            "bSort":false,
            "sAjaxDataProp" : "dataResult",
            "sAjaxSource": "/analysis/showThisRowData",
            "fnServerParams": function ( aoData ) {
                                          aoData.push( { "name": "RowData", "value":JSON.stringify(param) } );
            },
            "select": {
                style: "multi",
                selector: "td:first-child"
                },
            columns: [
                 { "data": "siteName" , "width": "40%", 'sClass': "text-center"},
                 { "data": "captureTime", "width": "40%", 'sClass': "text-center"},
            ],
            buttons:[],
            "oLanguage" : dataTable_language,
        });
    });

    var showDataTable;
    //展示详情
    $('#dataCollide-task-table tbody').on( 'click', 'tr a.show-task-btn', function () {
        $("#xq-dataCollide-dlg").modal("show");
        $("#xq-dataCollide-space-time-condition tbody").html('');
        var tr = $(this).closest('tr');
        var taskId = dataCollideTaskTable.row( tr ).data().id;
        showDataTable = $('#xq-dataCollide-space-time-condition').DataTable( {
            "sDom": "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
                     "<'row'<'col-sm-12'tr>>" +
                     "<'row'<'col-sm-1'l><'col-sm-5'i><'col-sm-6'p>>",
            "sServerMethod":"POST",
            "searching":false,
            "bFilter":true,
            "bDestroy": true,
            "bServerSide": true,
            "bProcessing":true,
            "bSort":false,
            "rowId" : "id",
            "sAjaxDataProp" : "dataResult",
            "sAjaxSource": "/analysis/queryRowDataCollide",
            "fnServerParams": function ( aoData ) { aoData.push( { "name": "formData", "value": taskId} ); },
            "select": {
                style: "multi",
                selector: "td:first-child"
                },
            columns: [
                 { "data": "imsi" },
                 { "data": "IMSI_COUNT"},
                 { "data": "imsiCount" },
                 { "data": "siteCount"},
                 {
                     "orderable": false,
                     "data": null,
                     "render" : function (data, type, row ) {
                         var detailDom ='<a href="javascript:;" data-taskid="' + taskId + '" class="btn btn-flat btn-sm btn-primary showThisRowData-btn">查看对应站点</a>';
                         return detailDom;
                     }
                 }
            ],
            buttons:[],
            "oLanguage" : dataTable_language,
        });
    });

    //查看任务
    $('#dataCollide-task-table tbody').on( 'click', 'tr a.look-task-btn', function () {
        upDaterangepickerNumber = 0;
        $("#up-dataCollide-space-time-condition tbody").html('');
        $('#up-dataCollide-dlg').modal('show');
        $("#up-space-time-condition-table tr:eq(0)").removeClass("hidden");
        $("#up-space-time-condition-table tr:eq(1)").addClass("hidden");

        var tr = $(this).closest('tr');
         //拿到这个任务的所有信息赋值
        var obj = dataCollideTaskTable.row( tr ).data();
        $('#up-dataCollide-name').val(obj.name);
        $('#up-remark').val(obj.remark);
        //给时空条件赋值
        for(var x = 0;x < obj.spaceTime.length;x++){
            var startTime = obj.spaceTime[x].startTime;
            var endTime = obj.spaceTime[x].endTime;
            var rangeTime = obj.spaceTime[x].rangeTime;
            var siteSN = obj.spaceTime[x].siteSN;
            var siteName = obj.spaceTime[x].siteName;
            var trDom = $('<tr></tr>');
            var tdRangeTimeDom = $('<td data-starttime="'+startTime + '" data-endtime="' + endTime + '">' + rangeTime + '</td>');
            var tdNameDom = $('<td data-sn="' + siteSN + '" >' + siteName + '</td>');
            var tdOptDom = $('<td><button class="btn btn-sm btn-flat btn-danger" onclick="delSiteTableRow1(this)">删除</button></td>');
            tdRangeTimeDom.appendTo(trDom);
            tdNameDom.appendTo(trDom);
            tdOptDom.appendTo(trDom);
            trDom.appendTo($("#up-dataCollide-space-time-condition tbody"))
            upDaterangepickerNumber = upDaterangepickerNumber + 1;
        }
    });

    //更新时空条件放入缓存
    $("#up-add-space-time-ok").click(function () {
        if('' == upzTreeSiteSn){
            Messenger().post({
                 message: "请选择站点!",
                 type: 'error',
                 showCloseButton: true
            });
            return;
        }
        //站点编号
        var siteSN = upzTreeSiteSn;
        //站点名字
        var siteName = upzTreeSiteName;
        upDaterangepickerNumber = upDaterangepickerNumber + 1;
            if(upDaterangepickerNumber >= 4){
                var dataCollideRangeTime = $('#up-dataCollide-range-time').daterangepicker({
                    timePicker: true,
                    timePickerIncrement: 1,
                    timePicker24Hour: true,
                    timePickerSeconds: true,
                    drops: 'up',
                    maxDate: new Date().toLocaleDateString() + ' 23:59:59',
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
                    var dataCollideRangeTime = $('#up-dataCollide-range-time').daterangepicker({
                        timePicker: true,
                        timePickerIncrement: 1,
                        timePicker24Hour: true,
                        timePickerSeconds: true,
                        maxDate: new Date().toLocaleDateString() + ' 23:59:59',
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
        //选取的时间范围
        var rangeTime = $("#up-dataCollide-range-time").val();
        var startTime = rangeTime.slice(0,19);
        var endTime =rangeTime.slice(22,41);
        var trDom = $('<tr></tr>');
        var tdRangeTimeDom = $('<td data-starttime="'+startTime + '" data-endtime="' + endTime + '">' + rangeTime + '</td>');
        var tdNameDom = $('<td data-sn="' + siteSN + '" >' + siteName + '</td>');
        var tdOptDom = $('<td><button class="btn btn-sm btn-flat btn-danger" onclick="delSiteTableRow1(this)">删除</button></td>');
        tdRangeTimeDom.appendTo(trDom);
        tdNameDom.appendTo(trDom);
        tdOptDom.appendTo(trDom);
        trDom.appendTo($("#up-dataCollide-space-time-condition tbody"));
        $('#up-site-text').val('');
        upzTreeSiteSn = '';
        upzTreeSiteName = '';
    });

    //更新增加新的任务
    $("#up-task-confirm").click(function (e) {
        var taskName =  $("#up-dataCollide-name").val();
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
        var trDom= $("#up-dataCollide-space-time-condition tbody tr");
        if( trDom.length < 2 ) {
            Messenger().post({
                message:  "时空条件不能少于2个",
                type: 'error',
                showCloseButton: true
            }) ;
            return ;
        }
        var remark = $("#up-remark").val();
        var mixCount = '2';
        var spaceTimeCondition = [];
        $.each(trDom, function () {
            var timeTd =  $(this).find("td:eq(0)");
            var startTime = timeTd.data("starttime");
            var endTime = timeTd.data("endtime");
            var rangeTime = timeTd.text();
            var siteTd = $(this).find("td:eq(1)");
            var siteSN  = siteTd.data("sn")+'';
            var siteName  = siteTd.text();
            var spaceTime = {
                "startTime": startTime,
                "endTime" : endTime,
                "rangeTime": rangeTime,
                "siteSN" : siteSN,
                "siteName" : siteName,
            };
            spaceTimeCondition.push(spaceTime);
        });

        $.ajax({
            type: 'post',
            dataType: 'json',
            url: "/analysis/creatorDataCollide",
            data: { taskName: taskName, spaceTime: JSON.stringify(spaceTimeCondition) , mixCount:mixCount,remark: remark},
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
                    $("#up-dataCollide-dlg").modal("hide");
                   dataCollideTaskTable.ajax.reload();
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

    //添加时空条件放入缓存
    $("#add-space-time-ok").click(function () {
        /*//select2选取的信息
        var selSite = $("#dataCollide-sites").select2("data");
        //站点编号
        var siteSN = selSite[0].id;
        //站点名字
        var siteName =  selSite[0].text;*/
        if('' == zTreeSiteSn){
            Messenger().post({
                 message: "请选择站点!",
                 type: 'error',
                 showCloseButton: true
            });
            return;
        }
        //站点编号
        var siteSN = zTreeSiteSn;
        //站点名字
        var siteName =  zTreeSiteName;
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
                maxDate: new Date().toLocaleDateString() + ' 23:59:59',
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
                maxDate: new Date().toLocaleDateString() + ' 23:59:59',
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
        $('#site-text').val('');
        zTreeSiteSn = '';
        zTreeSiteName = '';
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
        var remark = $("#remark").val();
        var mixCount = '2';
        var spaceTimeCondition = [];
        var times = [];
        $.each(trDom, function () {
            var timeTd =  $(this).find("td:eq(0)");
            var startTime = timeTd.data("starttime");
            var endTime = timeTd.data("endtime");
            var rangeTime = timeTd.text();
            var siteTd = $(this).find("td:eq(1)");
            var siteSN  = siteTd.data("sn")+'';
            var siteName  = siteTd.text();
            var spaceTime = {
                "startTime": startTime,
                "endTime" : endTime,
                "rangeTime": rangeTime,
                "siteSN" : siteSN,
                "siteName" : siteName,
            };
            spaceTimeCondition.push(spaceTime);
        });
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: "/analysis/creatorDataCollide",
            data: { taskName: taskName, spaceTime: JSON.stringify(spaceTimeCondition) , mixCount : mixCount, remark: remark},
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
                        url: "/analysis/deleteData",
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