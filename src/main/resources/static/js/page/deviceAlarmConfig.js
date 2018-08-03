/**
 * 上号异常配置模块
 */
$(function () {

    //上号异常配置数组，新增、更新、删除
    var configHistoryArray = [],
        configAddArray = [],
        configUpdateArray = [],
        configDeleteArray = [];

    var clearBuffArray = function () {
      configHistoryArray.length = 0;
      configAddArray.length = 0;
      configUpdateArray.length = 0;
      configDeleteArray.length = 0;
    };

    //上号异常配置表格
    var upnumexTable = $('#device-upnumex-table').DataTable({
        paging: false,
        lengthChange: false,
        searching: false,
        ordering: false,
        info: false,
        autoWidth: false,
        //processing: true,
        //bStateSave:true,
        //ajax: "/device/alarm/uploadNumConfigList",
        //rowId: "id",
        //displayLength: 0,
        columns: [
            { "data": "id", "visible": false },
            { "data": "startTime" },
            { "data": "endTime" },
            { "data": "interval" },
            {
                "orderable":      false,
                "data":           null,
                "render" : function (data, type, row ) {
                    var editDom = '<button  class="btn btn-flat btn-sm btn-success device-upnumex-edit-btn">编辑</button>';
                    var delDom = '<button class="btn btn-flat btn-sm btn-danger device-upnumex-del-btn">删除</button>';
                    return editDom + delDom;
                }
            }
        ],
        //order: [[ 0, 'asc' ]],
        language: {
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
        // buttons: [
        //     {
        //         text: "添加设备",
        //         className: "btn bg-navy btn-flat",
        //         action: function () {
        //             $("#device-form")[0].reset();
        //             $('#device-form input[name=action]').val("create");
        //             $('#device-sn').parent().parent().removeClass("hidden");
        //             deviceTypeSelect.val("1").trigger("change");
        //             $("#belongTo").prop("disabled", false);
        //             $("#device-dlg").modal("show");
        //         }
        //     }
        // ],
        // dom: "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
        // "<'row'<'col-sm-12'tr>>" +
        // "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });

    //站点-设备 上号异常按钮绑定点击
    $('#sites-table tbody').on('click', '.site-device-upnumex-btn', function () {
        var tr = $(this).closest('tr');
        var fatherTable = $(this).parents('.table:eq(0)').DataTable();
        var rowData = fatherTable.row( tr ).data();
        if( rowData ) {
            var upnumexTable = $('#device-upnumex-table').DataTable();
            upnumexTable.clear().draw();

            //清空buffer数组
            clearBuffArray();

            //读取历史
            getHistoryConfigList(rowData[0], upnumexTable);
            $('#upnumex_devicesn').val(rowData[0]);
            $('#device-upnumex-dlg').modal('show');
        }
    });

    //设备 上号异常按钮绑定点击
    $('#devices-table tbody').on( 'click', 'tr button.device-upnumex-btn', function () {
        var tr = $(this).closest('tr');
        var devicesTable = $('#devices-table').DataTable();
        var rowData = devicesTable.row( tr ).data();
        if( rowData ) {
            //清空之前数据
            var upnumexTable = $('#device-upnumex-table').DataTable();
            upnumexTable.clear().draw();

            //清空buffer数组
            clearBuffArray();

            //读取历史
            getHistoryConfigList(rowData.sn, upnumexTable);
            $('#upnumex_devicesn').val(rowData.sn);
            $('#device-upnumex-dlg').modal('show');
        }
    });

    //读取某设备之前上号异常配置信息
    var getHistoryConfigList = function(deviceSn, table){
        $.ajax({
            type: 'GET',
            dataType: 'json',
            url: "/device/upnumex/configlist?deviceSn=" + deviceSn,
            //data: JSON.stringify(configs),
            //设置超时时间
            timeout: 5000,
            //contentType: 'application/json',
            success: function ( data ) {
                if(data.success){
                    configHistoryArray = data.configs;
                    data.configs.forEach(function(config, index){
                        table.row.add( {
                            "id": config.id,
                            "startTime": config.startTime,
                            "endTime": config.endTime,
                            "interval": config.interval
                        } ).draw();
                    });
                }
            }
        });
    };

    //添加上号异常事件
    $("#add-upnumex-config-btn").click(function () {
        $("#add-upnumex-config-table tr:eq(0)").addClass("hidden");
        $("#add-upnumex-config-table tr:eq(1)").removeClass("hidden");
    });

    //添加上号异常 选择时间范围格式
    var upnumexConfigAddRangeTime = $('#upnumex-config-range-time').daterangepicker({
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

    //编辑上号异常，选择时间范围格式
    var upnumExConfigEditRangeTime = $('#upnumex-config-edit-range-time').daterangepicker({
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

    //添加上号异常配置确认
    $("#upnum-config-add-btn").click(function () {

        var table = $('#device-upnumex-table').DataTable();

        //选取的时间范围
        var rangeTime = $("#upnumex-config-range-time").val();
        var startTime = rangeTime.slice(0,19);
        var endTime = rangeTime.slice(22,41);
        var dayStartTime = rangeTime.slice(11,19);
        var dayEndTime = rangeTime.slice(33,41);
        var interval = $('#upnumex-interval').val();
        var tempInterval = parseInt(interval);
        if(!tempInterval || tempInterval <= 0){
            bootbox.alert({
                size: "small",
                title: "错误",
                message: "告警时间间隔必须为正整数！"
            });
        }

        //时间区间不能与之前配置冲突
        var data = table.rows().data();
        var isValid = true;
        for(var i = 0; i < data.length; i++){
            var rowData = data[i];
            var rowStartTime = rowData.startTime,
                rowEndTime = rowData.endTime;
            var dayRowStartTime = rowStartTime.slice(11,19);
            var dayRowEndTime = rowEndTime.slice(11,19);
            // if(!(startTime > rowEndTime || endTime < rowStartTime)){
            //     isValid = false;
            //     return;
            // }
            if(!(dayStartTime > dayRowEndTime || dayEndTime < dayRowStartTime)){
                isValid = false;
                break;
            }
        }

        if(!isValid){
            bootbox.alert({
                size: "small",
                title: "错误",
                message: "起始时间不能与之前配置有重叠！"
            });
        }

        //告警时间间隔不能<=0
        if(interval <= 0){
            bootbox.alert({
                size: "small",
                title: "错误",
                message: "告警时间间隔必须大于0！"
            });

            return;
        }

        var obj = {
            "id": -1,
            "deviceId": $('#upnumex_devicesn').val(),
            "startTime": startTime,
            "endTime": endTime,
            "interval": interval
        };

        //放入待添加数组
        configAddArray.push(obj);
        //添加到UI
        table.row.add(obj).draw();
    });

    //上号异常添加配置提交
    $("#upnum-config-confirm-btn").click(function(){
        var table = $('#device-upnumex-table').DataTable();
        //需要过滤新添加的数据跟老的数据
        //新添加的数据存放在列表里
        var data = table.rows().data();
        var deviceSn = $('#upnumex_devicesn').val();
        var configs = [];
        for(var i = 0; i < data.length; i++){
            var rowData = data[i];
            var config = {};
            config.deviceId = deviceSn;
            config.startTime = rowData.startTime;
            config.endTime = rowData.endTime;
            config.interval = rowData.interval;
            configs.push(config);
        }

        // $.ajax({
        //     type: 'POST',
        //     dataType: 'json',
        //     url: "/device/upnumex/add",
        //     data: JSON.stringify(configs),
        //     //设置超时时间
        //     timeout: 5000,
        //     contentType: 'application/json',
        //     success: function ( data ) {
        //         if(data.success){
        //             $('#device-upnumex-dlg').modal('hide');
        //         }
        //     }
        // });

        var configs = {
            "deviceId": deviceSn,
            "addList": configAddArray,
            "updateList": configUpdateArray,
            "deleteList": configDeleteArray
        };

        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: "/device/upnumex/write",
            data: JSON.stringify(configs),
            //设置超时时间
            //timeout: 5000,
            contentType: 'application/json',
            success: function ( data ) {
                if(data.success){
                    $('#device-upnumex-dlg').modal('hide');
                }else{
                    bootbox.alert({
                        size: "small",
                        title: "错误",
                        message: data.msg
                    });
                }
            }
        });

    });

    //上号异常配置表格 删除绑定
    $("#device-upnumex-table tbody").on('click', 'tr button.device-upnumex-del-btn', function () {
        var that = this;
        bootbox.confirm({
            size: "small",
            message: "是否删除",
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
                    //获取当前行 TODO怎么直接获取数据
                    var table = $('#device-upnumex-table').DataTable();
                    var data = table.row( $(that).parents('tr') ).data();
                    //根据id是否为空，做不同处理
                    if(data.id !== -1){
                        //从源数组中删除，并放入待删除数组
                        var deleteElement = configHistoryArray.find(function (element) {
                            return element.id === data.id;
                        });

                        configDeleteArray.push(deleteElement);
                        var index = configHistoryArray.indexOf(deleteElement);
                        configHistoryArray.splice(index, 1);
                    }else{
                        //从待添加数组删除
                        // var deleteElement = configAddArray.find(function (element) {
                        //     return element.startTime === data.startTime && element.endTime === data.endTime
                        //         && element.interval === data.interval;
                        // });

                        var index = configAddArray.indexOf(data);
                        configHistoryArray.splice(index, 1);
                    }

                    upnumexTable.row( $(that).parents('tr') ).remove().draw();
                }
            }
        });
    });

    //上号异常配置表格 编辑绑定
    $("#device-upnumex-table tbody").on('click', 'tr button.device-upnumex-edit-btn', function () {
        var table = $('#device-upnumex-table').DataTable();
        var clickedRow = $(this).parents('tr');
        var clickedRowData = table.row(clickedRow).data();

        $('#edit-upnumex-table tr:eq(0)').removeClass("hidden");
        $('#upnumex-config-edit-range-time').data('daterangepicker').setStartDate(clickedRowData.startTime);
        $('#upnumex-config-edit-range-time').data('daterangepicker').setEndDate(clickedRowData.endTime);
        $('#upnumex-config-edit-interval').val(clickedRowData.interval);

        (function (table, clickedRow, clickedRowData) {
            $('#edit-upnum-save-btn').on('click', function () {
                //var currentRowIndex = table.row(clickedRow).index();
                //获取当前编辑行号
                var tableData = table.rows().data();
                var currentRowIndex = tableData.indexOf(clickedRowData);

                var rangeTime = $("#upnumex-config-edit-range-time").val();
                clickedRowData.startTime = rangeTime.slice(0,19);
                clickedRowData.endTime = rangeTime.slice(22,41);
                var dayStartTime = rangeTime.slice(11,19);
                var dayEndTime = rangeTime.slice(33,41);
                clickedRowData.interval = $('#upnumex-config-edit-interval').val();
                var tempInterval = parseInt(clickedRowData.interval);
                if(!tempInterval || tempInterval <= 0){
                    bootbox.alert({
                        size: "small",
                        title: "错误",
                        message: "告警时间间隔必须为正整数！"
                    });
                }

                var isValid = true;
                //循环不包括当前编辑行的所有行
                for(var i = 0; i < tableData.length; i++){
                    if(i === currentRowIndex){
                        continue;
                    }
                    var rowData = tableData[i];
                    var rowStartTime = rowData.startTime,
                        rowEndTime = rowData.endTime;
                    var dayRowStartTime = rowStartTime.slice(11,19);
                    var dayRowEndTime = rowEndTime.slice(11,19);
                    if(!(dayStartTime > dayRowEndTime || dayEndTime < dayRowStartTime)){
                        isValid = false;
                        break;
                    }
                }

                if(!isValid){
                    bootbox.alert({
                        size: "small",
                        title: "错误",
                        message: "起始时间不能与之前配置有重叠！"
                    });
                }

                //告警时间间隔不能<=0
                if(clickedRowData.interval <= 0){
                    bootbox.alert({
                        size: "small",
                        title: "错误",
                        message: "告警时间间隔必须大于0！"
                    });

                    return;
                }

                table.row(clickedRow).data(clickedRowData).draw();
                //若id为老数据
                if(clickedRowData.id !== -1){
                    //先判断待更新数组是否存在
                    var updateElement = configUpdateArray.find(function (element) {
                        return element.id === clickedRowData.id;
                    });
                    if(updateElement){
                        var element = configUpdateArray.find(function (element) {
                            return element.id === clickedRowData.id;
                        });
                        var index = configUpdateArray.indexOf(element);
                        element.startTime = clickedRowData.startTime;
                        element.endTime = clickedRowData.endTime;
                        element.interval = clickedRowData.interval;
                        configUpdateArray[index] = element;
                    }else{
                        var element = configHistoryArray.find(function (element) {
                            return element.id === clickedRowData.id;
                        });
                        element.startTime = clickedRowData.startTime;
                        element.endTime = clickedRowData.endTime;
                        element.interval = clickedRowData.interval;
                        configUpdateArray.push(element);
                        var index = configHistoryArray.indexOf(element);
                        configHistoryArray.splice(index, 1);
                    }
                }else{
                    //若为新增数据，则需要修改configAddArray
                    var element = configAddArray.find(function (element) {
                        return element.id === clickedRowData.id;
                    });
                    var index = configAddArray.indexOf(element);
                    element.startTime = clickedRowData.startTime;
                    element.endTime = clickedRowData.endTime;
                    element.interval = clickedRowData.interval;
                    configAddArray[index] = element;
                }
                $('#edit-upnumex-table tr:eq(0)').addClass("hidden");
            });
        })(table, clickedRow, clickedRowData);
    });

    $('#edit-upnum-cancel-btn').on('click', function () {
        $('#edit-upnumex-table tr:eq(0)').addClass("hidden");
    });

    //上号异常取消添加配置
    $("#upnum-config-cancel-btn").click(function () {
        $("#add-upnumex-config-table tr:eq(0)").removeClass("hidden");
        $("#add-upnumex-config-table tr:eq(1)").addClass("hidden");
    });

});