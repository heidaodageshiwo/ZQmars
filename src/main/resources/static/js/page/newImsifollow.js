/**
 * Created by jiangqi.yang  on 2016/12/8.
 */

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

    var imsiFollowRangeTime = $('#imsiFollow-range-time').daterangepicker({
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

    //好用的zTree插件
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    $(document).ready(function(){
        $.ajax({
            type: "post",
            url: "/soMapping/queryZtree",
            dataType: "json",
            success: function(data){
                $.fn.zTree.init($("#treeDemo"), setting, data);
                $.fn.zTree.init($("#up-treeDemo"), setting, data);
                setting.check.chkboxType = { "Y" : "s", "N" : "s" };
            }
        });
    });

    var addSiteIds = [];
    $('#site-ok').click(function(){
        //ztree初始化时候，已经选择的站点addSiteIds赋值给zTree
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        for(var i = 0;i<addSiteIds.length;i++){
            var node = treeObj .getNodeByParam('id',addSiteIds[i]);
            treeObj.checkNode(node, true, true);
        }
        $('#site-dlg').modal('show');
    });

    //获取选中的站点ID
    var nodes;
    var x = 0;
    var siteNames;
    $('#site_submit').click(function(){
        addSiteIds = new Array();
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        nodes = treeObj.getCheckedNodes(true);
        var sites = 0;
        siteNames = '';
        for(var i = 0;i < nodes.length;i++){
            if(nodes[i].id < 100000){
                sites ++;
                siteNames += nodes[i].name + ';';
                addSiteIds[addSiteIds.length] = nodes[i].id;
            }
        }
        if(siteNames == ''){
            $("#siteName").html('全部站点');
        }else{
            $("#siteName").html(siteNames);
        }

        if(sites == 0){
            $('#site-name').val('提示:默认情况下将为您选择全部站点');
        }else{
            $('#site-name').val('您选择了'+ sites +'个站点');
        }
        $('#site-dlg').modal('hide');
    });



    /*setInterval( function () {
        imsiFollowTaskTable.ajax.reload(null, false);
    }, 2000 );*/
    setInterval( function () {
        //获取所有进度
        var cells = imsiFollowTaskTable.cells(null,4).data();
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
            url: "/analysis/queryImsiFollowTask",
            dataType: "json",
            success: function(data){
                var tableData = imsiFollowTaskTable.rows().data();
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

    var imsiFollowTaskTable = $('#imsifollow-task-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "processing": true,
        "ajax": {
            "url": "/analysis/queryImsiFollowTask",
            "dataType": "json"
        },
        "dataSrc": "",
        "columns": [
            { "data": "name" },
            { "data": "imsi" },
            {
                "data": "rangeTime",
                "orderable":  false,
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
                    var dom = '<span id="number'+row.id+'" class="badge bg-yellow pull-right">' + data +
                        '%</span><div data-taskid="' + row.id + '" class="progress progress-xs" > <div class="progress-bar progress-bar-yellow" id="'+ row.id +'" style="width: ' + data +
                        '%"></div></div>';
                    return dom;
                },
                "targets": 4
            },
            {
                "render" : function (data, type, row ) {
                    var dom;
                    if( data.status == 2 ) {
                        dom = '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-primary btn-sm look-task-btn">创建新任务</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-warning btn-sm show-task-btn">详情</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat bg-purple btn-sm createCsv-task-btn">导出csv</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-danger btn-sm delete-task-btn">删除</a>';
                    } else {
                        dom = '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-primary btn-sm look-task-btn">创建新任务</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-warning btn-sm show-task-btn" style="display: none;" id="details'+ row.id +'">详情</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat bg-purple btn-sm createCsv-task-btn" style="display: none;" id="export'+ row.id +'">导出csv</a>';
                        dom += '<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-danger btn-sm delete-task-btn">删除</a>';
                    }
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
                    $('#imsiFollow-task-form')[0].reset();
                    $("#siteName").html('全部站点');
                    //每次重新添加分组的时候zTree初始化
                    addSiteIds = new Array();
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    treeObj.checkAllNodes(false);
                    nodes = null;
                    $('#create-imsiFollow-dlg').modal('show');
                }
            }
        ],
        "dom": "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });

    var showDataTable;
    //展示详情
    $('#imsifollow-task-table tbody').on( 'click', 'tr a.show-task-btn', function () {
        $("#xq-imsiFollow-dlg").modal("show");
        $("#xq-imsiFollow-space-time-condition tbody").html('');
        var tr = $(this).closest('tr');
        var taskId = imsiFollowTaskTable.row( tr ).data().id;
        showDataTable = $('#xq-imsiFollow-space-time-condition').DataTable( {
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
            "sAjaxSource": "/analysis/imsiFollowDetails",
            "fnServerParams": function ( aoData ) { aoData.push( { "name": "formData", "value": taskId} ); },
            "select": {
                style: "multi",
                selector: "td:first-child"
            },
            columns: [
                { "data": "imsi" },
                { "data": "imsiCount"}
                /*{
                    "orderable": false,
                    "data": null,
                    "render" : function (data, type, row ) {
                        var detailDom ='<a href="javascript:;" data-taskid="' + taskId + '" class="btn btn-flat btn-sm btn-primary showThisRowData-btn">查看对应站点</a>';
                        return detailDom;
                    }
                }*/
            ],
            buttons:[],
            "oLanguage" : dataTable_language,
        });
    });

    var imsiFollowTaskFormValidate = $( "#imsiFollow-task-form" ).validate( {
        rules: {
            taskName: {
                required: true,
                stringCheck:true
            },
            rangeTime: {
                required: true
            },
            topTime:{
                required: true,
                digits: true,
                rangelength: [1,2]
            },
            bottomTime:{
                required: true,
                digits: true,
                rangelength: [1,2]
            },
            targetIMSI:{
                required: true,
                digits: true,
                rangelength: [15,15]
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
            topTime:{
                required: "请输入伴随时间窗上限",
                digits: "请输入数字格式的伴随时间窗上限",
                rangelength: "请输入小于99的伴随时间窗上限"
            },
            bottomTime:{
                required: "请输入伴随时间窗下限",
                digits: "请输入数字格式的伴随时间窗下限",
                rangelength: "请输入小于99的伴随时间窗下限"
            },
            targetIMSI:{
                required: "请输入目标IMSI值",
                digits: "请输入数字格式的IMSI值",
                rangelength: "请输入15位长度的IMSI值"
            }
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
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

    $('#create-imsiFollow-dlg').on('shown.bs.modal', function (e) {
        imsiFollowTaskFormValidate.resetForm();
    });

    //修改
    var upNodesIds = [];
    var upSiteNames;
    $('#imsifollow-task-table tbody').on( 'click', 'tr a.look-task-btn', function () {
        $('#up-imsiFollow-dlg').modal('show');
        var tr = $(this).closest('tr');
        //拿到这个任务的所有信息赋值
        var obj = imsiFollowTaskTable.row( tr ).data();
        $('#up-task-name').val(obj.name);
        var date = new Date();
        var alarmRangeTime = $('#up-imsiFollow-range-time').daterangepicker({
            timePicker: true,
            timePickerIncrement: 1,
            timePicker24Hour: true,
            timePickerSeconds: true,
            //给时间插件默认值
            startDate:obj.startTime,
            endDate:obj.endTime,
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
        //获取站点对应的ID并赋值给ztree站点赋值
        if(obj.siteNames == ''){
            $('#up-site-name').val('提示:默认情况下将为您选择全部站点');
            $('#up-siteName').html('全部站点');
        }else{
            var siteData = obj.siteNames.split(';');
            $('#up-site-name').val('您选择了'+ (siteData.length-1) +'个站点');
            upSiteNames = '';
            for(var i = 0;i < siteData.length-1;i++){
                upSiteNames += siteData[i]+";";
            }
            $('#up-siteName').html(upSiteNames);
        }

        var upSetting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        if(obj.siteIds.length > 0){
            var siteInfo = obj.siteIds;
            var treeObj = $.fn.zTree.getZTreeObj("up-treeDemo");
            for(var i = 0;i < siteInfo.length;i++){
                upNodesIds[i] = siteInfo[i];
                var node = treeObj .getNodeByParam('id',siteInfo[i]);
                if(node != null){
                    treeObj.checkNode(node, true, true);
                }
            }
        }

        $('#up-imsiFollow-timeWindow-top').val(obj.up);
        $('#up-imsiFollow-timeWindow-bottom').val(obj.down);
        $('#up-imsiFollow-target-imsi').val(obj.imsi);
    });

    $('#up-site-ok').click(function(){
        //ztree初始化
        var treeObj = $.fn.zTree.getZTreeObj("up-treeDemo");
        treeObj.checkAllNodes(false);
        for(var i = 0;i < upNodesIds.length;i++){
            var node = treeObj .getNodeByParam('id',upNodesIds[i]);
            if(node != null){
                treeObj.checkNode(node, true, true);
            }
        }
        $('#up-site-dlg').modal('show');
    });

    //获取选中的站点ID
    $('#up-site_submit').click(function(){
        var treeObj = $.fn.zTree.getZTreeObj("up-treeDemo");
        upNodes = treeObj.getCheckedNodes(true);
        var sites = 0;
        upSiteNames = '';
        upNodesIds.length = 0;

        for(var i = 0;i < upNodes.length;i++){
            if(upNodes[i].id < 100000){
                sites ++;
                upSiteNames += upNodes[i].name + ';';
                upNodesIds[upNodesIds.length] = upNodes[i].id;
            }
        }
        if(upSiteNames == ''){
            $("#up-siteName").html('全部站点');
        }else{
            $("#up-siteName").html(upSiteNames);
        }

        if(sites == 0){
            $('#up-site-name').val('提示:默认情况下将为您选择全部站点');
        }else{
            $('#up-site-name').val('您选择了'+ sites +'个站点');
        }
        $('#up-site-dlg').modal('hide');
    });

    //修改任务创建新任务
    $('#up-task-btn').click(function(){
        if( imsiFollowTaskFormValidate.form()) {
            var taskName = $("#up-task-name").val();
            var startDate = $("#up-imsiFollow-range-time").val().slice(0,19);
            var endDate = $("#up-imsiFollow-range-time").val().slice(22,41);
            var imsi = $("#up-imsiFollow-target-imsi").val();
            var up = $("#up-imsiFollow-timeWindow-top").val();
            var down = $("#up-imsiFollow-timeWindow-bottom").val();
            var mixCount = '2';
            var type = 'alongAnalysis';
            var siteIds = upNodesIds;

            $.ajax({
                type: 'post',
                dataType: 'json',
                url: "/analysis/createImsiFollow",
                //data: {spaceTime : JSON.stringify(spaceTime)},
                data: {taskName:taskName, startDate:startDate,endDate:endDate,up:up,down:down,imsi:imsi,mixCount:mixCount,type:type,siteIds:JSON.stringify(siteIds),siteNames:upSiteNames},
                timeout: 5000,
                success: function ( data ) {
                    $('#create-imsiFollow-dlg').modal('hide');
                    if( true == data.status ) {
                        Messenger().post({
                            message:  data.message,
                            type: 'success',
                            showCloseButton: true
                        }) ;
                        $("#up-imsiFollow-dlg").modal("hide");
                        imsiFollowTaskTable.ajax.reload();
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


    //创建任务
    $("#create-task-btn").click(function () {
        if( imsiFollowTaskFormValidate.form()) {
         /*   if(addSiteIds.length <= 0){
                Messenger().post({
                    message: "请选择检测站点",
                    type: 'error',
                    showCloseButton: true
                });
                return;
            }*/
            var taskName = $("#task-name").val();
            var startDate = $("#imsiFollow-range-time").val().slice(0,19);
            var endDate = $("#imsiFollow-range-time").val().slice(22,41);
            var imsi = $("#imsiFollow-target-imsi").val();
            var up = $("#imsiFollow-timeWindow-top").val();
            var down = $("#imsiFollow-timeWindow-bottom").val();
            var mixCount = '2';
            var type = 'alongAnalysis';
            var siteIds = addSiteIds;


            $.ajax({
                type: 'post',
                dataType: 'json',
                url: "/analysis/createImsiFollow",
                //data: {spaceTime : JSON.stringify(spaceTime)},
                data: {taskName:taskName, startDate:startDate,endDate:endDate,up:up,down:down,imsi:imsi,mixCount:mixCount,type:type,siteIds:JSON.stringify(siteIds),siteNames:siteNames},
                timeout: 5000,
                success: function ( data ) {
                    $('#create-imsiFollow-dlg').modal('hide');
                    if( true == data.status ) {
                        Messenger().post({
                            message:  data.message,
                            type: 'success',
                            showCloseButton: true
                        }) ;
                        imsiFollowTaskTable.ajax.reload(null,false);
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

    $('#imsifollow-task-table tbody').on( 'click', 'tr a.createCsv-task-btn', function (rowData) {
        $('#showWait').modal('show');
        var tr = $(this).closest('tr');
        var obj = imsiFollowTaskTable.row( tr ).data();
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

    $('#imsifollow-task-table tbody').on( 'click', 'tr a.delete-task-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = imsiFollowTaskTable.row( tr ).data();

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
                                imsiFollowTaskTable.ajax.reload(null,false);
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