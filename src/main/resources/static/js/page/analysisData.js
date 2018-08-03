/**
 * Created by jiangqi.yang  on 2016/11/2.
 */


$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    }

    var provinceSelect = $("#province").select2({
        placeholder: "请选择一个省",
        ajax: {
            url: "/util/getProvinces",
            dataType: 'json',
            data: function (params) {
            },
            processResults: function (data, params) {
                return {
                    results: data.data
                };
            },
            cache: true
        }
    });

    var citySelect = $("#city").select2({
        placeholder: "请选择一个市",
    });
    var townSelect = $("#town").select2({
        placeholder: "请选择一个区",
    });

    $('#province').on('change', function (evt) {
        var provinceId = $(this).val();
        $("#city").empty();
        $.ajax({
            async: false,
            type: "post",
            url: "/util/getCities",
            data: { 'provinceId': provinceId },
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    $.each( data.data, function ( i, city ) {
                        var optionDom = $('<option value="' + city.id + '">'+ city.text +'</option>') ;
                        optionDom.appendTo($("#city"));
                    });
                    $("#city").select2().trigger("change");
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


    $('#city').on('change', function (evt) {
        var cityId = $(this).val();
        $("#town").empty();
        $.ajax({
            async: false,
            type: "post",
            url: "/util/getTowns",
            data: { 'cityId': cityId },
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    $.each( data.data, function ( i, town ) {
                        var optionDom = $('<option value="' + town.id + '">'+ town.text +'</option>') ;
                        optionDom.appendTo($("#town"));
                    });
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

    var deviceOperatorSelect = $("#device-operator").select2();
    var deviceTypeSelect = $("#device-type").select2();
    var deviceBandSelect = $("#device-band").select2();
    var belongToSelect = $("#belongTo").select2({
        placeholder: "请选择一个所属站点",
        ajax: {
            url: "/device/getSites",
            dataType: 'json',
            data: function (params) {
            },
            processResults: function (data, params) {

                var belongToData = $.map( data.data, function (obj) {
                    obj.text = obj.text || obj.name;
                    return obj;
                });

                return {
                    results: belongToData
                };
            },
            cache: true
        }
    });

    $("#device-type").on('change', function (evt) {
        var typeVal = $(this).val();
        $("#device-band").empty();
        if( 5 == typeVal ) {  //TDD LTE
            $('<option value="band38">Band 38</option>').appendTo($("#device-band"));
            $('<option value="band39">Band 39</option>').appendTo($("#device-band"));
            $('<option value="band40">Band 40</option>').appendTo($("#device-band"));
            $('<option value="band41">Band 41</option>').appendTo($("#device-band"));
        } else if( 6 == typeVal  ) { //FDD LTE
            $('<option value="band1">Band 1</option>').appendTo($("#device-band"));
            $('<option value="band2">Band 2</option>').appendTo($("#device-band"));
            $('<option value="band3">Band 3</option>').appendTo($("#device-band"));
        }
    });


    var siteFormValidate =  $("#site-form" ).validate( {
        rules: {
            siteSn: {
                required: true,
                stringCheck:true,
                remote: {
                    type: "POST",
                    url: "/device/checkSitSn",
                    data: {
                        sn: function() {
                            return $( "#site-sn" ).val();
                        }
                    }
                }
            },
            siteName:{
                required: true,
                stringCheck:true
            },
            province: "required",
            city: "required",
            town: "required",

            siteAddress: {
                required: true,
                normalizer: function( value ) {
                    return $.trim( value );
                }
            },
            siteLongitude: {
                required: true,
                number:true
            },

            siteLatitude: {
                required: true,
                number:true
            }
        },
        messages: {
            siteSn: {
                required: "请输入设备序号",
                stringCheck: "只能包括中文字、英文字母、数字和下划线!",
                remote: "站点编号已经使用，请输入新的编号"
            },
            siteName:{
                required: "请输入站点名称",
                stringCheck:"只能包括中文字、英文字母、数字和下划线!"
            },
            province: "请选择一个省份",
            city: "请选择一个城市",
            town: "请选择一个地区",
            siteAddress: {
                required: "输入详细地址信息"
            },
            siteLongitude: {
                required: "请输入经度",
                number: "请输入正确的数据"
            },
            siteLatitude: {
                required: "请输入纬度",
                number:"请输入正确的数据"
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


    var deviceFormValidate =  $("#device-form" ).validate( {
        rules: {
            deviceSn: {
                required: true,
                stringCheck:true,
                remote: {
                    type: "POST",
                    url: "/device/checkDeviceSn",
                    data: {
                        sn: function() {
                            return $( "#device-sn" ).val();
                        }
                    }
                }
            },
            deviceName:{
                required: true,
                stringCheck:true
            },
            belongTo: "required"
        },
        messages: {
            deviceSn: {
                required: "请输入设备序号",
                stringCheck: "只能包括中文字、英文字母、数字和下划线!",
                remote:"设备编号已经使用，请输入新的编号"
            },
            deviceName:{
                required: "请输入站点名称",
                stringCheck:"只能包括中文字、英文字母、数字和下划线!"
            },
            belongTo: "请选择一个所属站点"
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



    var captureRangeTime = $('#capture-range-time').daterangepicker({
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

    //$('#capture-range-time').val("");
    $("#period").select2({
        language: "zh-CN",
        minimumResultsForSearch: Infinity
    });

    var sitesTable1 = $('#sites-table1').DataTable({
        paging: true,
        lengthChange: false,
        searching: false,
        ordering: false,
        info: true,
        autoWidth: false,
        processing: true,
        bStateSave:true,
       ajax: "/queryAnalysisiDataAjaxByImsi",
        dataSrc: "",
        select: {
            style: "single",
            selector: "td:not(':last-child')"

        },
        rowId: "id",
        displayLength: 8,
        columns: [
            /* {
             "class":          "details-control",
             "orderable":      false,
             "data":           null,
             "defaultContent": ""
             },
             {
             "orderable":      false,
             "data":           null
             },*/
            { "data": "id" },
            { "data": "sitename" },
            { "data": "capturetime" }/*,
            { "data": "cityname" },
            { "data": "one" },
            { "data": "two" },
            { "data": "daycount" },
            { "data": "datacount" },*//*
             { "data": "sn" },
             { "data": "name" },
             {
             "data": "address",
             "render" : function (data, type, row ) {
             var addressText =   row.province +  row.city + row.town  + row.address;
             return  addressText;
             }
             },
             {
             "data": null,
             "orderable":      false,
             "render" : function (data, type, row ) {
             var gpsText =   row.longitude + ", " + row.latitude;
             return  gpsText;
             }
             },
             { "data": "createTime" },
             { "data": "remark" },*/
           /* {
                "orderable":      false,
                "data":           null,
                "render" : function (data, type, row ) {
                    /!* var addDom = '<button class="btn btn-flat btn-sm bg-navy device-add-btn">添加设备</button>';*!/
                    var editDom = '<button class="btn btn-flat btn-sm btn-success site-edit-btn">详情</button>';
                    /!*var delDom = '<button class="btn btn-flat btn-sm btn-danger site-del-btn">删除</button>';*!/
                    return  editDom;
                }
            }*/
        ],
        order: [[ 1, 'asc' ]],
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
        buttons: [
          /*  {
                text: "添加站点",
                className: "btn bg-navy btn-flat",
                action: function () {
                    $("#site-form")[0].reset();
                    $('#site-form input[name=action]').val("create");
                    $('#site-sn').parent().parent().removeClass("hidden");
                    $("#site-dlg").modal("show");
                }
            }*/
        ],
        dom: "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });




    var startDate = $("#capture-range-time").val().slice(0,19);
    var endDate = $("#capture-range-time").val().slice(22,41);

    var sitesTable = $('#sites-table').DataTable({
        paging: true,
        lengthChange: false,
        searching: true,
        ordering: false,
        info: true,
        autoWidth: false,
        processing: true,
        bStateSave:true,
        ajax: "/queryAnalysisiData?startDate="+$("#capture-range-time").val().slice(0,19)+"&endDate="+$("#capture-range-time").val().slice(22,41),
        dataSrc: "",
        select: {
            style: "single",
            selector: "td:not(':last-child')"

        },
        rowId: "id",
        displayLength: 20,
        columns: [
           /* {
                "class":          "details-control",
                "orderable":      false,
                "data":           null,
                "defaultContent": ""
            },
            {
                "orderable":      false,
                "data":           null
            },*/
            { "data": "id" },
            { "data": "imsi" },
            { "data": "operator" },
            { "data": "cityname" },
            { "data": "one" },
            { "data": "two" },
            { "data": "daycount" },
            { "data": "datacount" },/*
            { "data": "sn" },
            { "data": "name" },
            {
                "data": "address",
                "render" : function (data, type, row ) {
                    var addressText =   row.province +  row.city + row.town  + row.address;
                    return  addressText;
                }
            },
            {
                "data": null,
                "orderable":      false,
                "render" : function (data, type, row ) {
                    var gpsText =   row.longitude + ", " + row.latitude;
                    return  gpsText;
                }
            },
            { "data": "createTime" },
            { "data": "remark" },*/
            {
                "orderable":      false,
                "data":           null,
                "render" : function (data, type, row ) {
                   /* var addDom = '<button class="btn btn-flat btn-sm bg-navy device-add-btn">添加设备</button>';*/
                    var editDom = '<button class="btn btn-flat btn-sm btn-success site-edit-btn">详情</button>';
                    /*var delDom = '<button class="btn btn-flat btn-sm btn-danger site-del-btn">删除</button>';*/
                    return  editDom;
                }
            }
        ],
        order: [[ 1, 'asc' ]],
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
        buttons: [
            // {
            //     text: "添加站点",
            //     className: "btn bg-navy btn-flat",
            //     action: function () {
            //         $("#site-form")[0].reset();
            //         $('#site-form input[name=action]').val("create");
            //         $('#site-sn').parent().parent().removeClass("hidden");
            //         $("#site-dlg").modal("show");
            //     }
            // }
        ],
        dom: "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });

    function format ( d , rowIdx ) {

        var tableDom = $('<table class="table table-bordered"></table>');
        var tableHeaderDom = $('<thead><th>设备编号</th><th>设备名称</th><th>设备类型</th><th>运营商</th><th>工作频段</th><th>创建日期</th><th>备注</th><th>操作</th></thead>');
        tableHeaderDom.appendTo(tableDom);

        var tableBodyDom = $('<tbody></tbody>');

        if(  d.devices ) {
            $.each( d.devices, function ( i, device) {
                var tableTrDom = $('<tr ></tr>');
                var tableTdDom = $('<td>'+ device.sn + '</td><td>'+ device.name +'</td><td>'
                    + device.typeText + '</td><td>'+ device.operatorText
                    + '</td><td>'+ device.band + '</td><td>'+ device.createTime
                    + '</td><td>'+ device.remark + '</td><td> ' +
                    '<button data-idx="' + i + '" data-rowidx="' + rowIdx +  '"  class="btn btn-flat btn-sm btn-success site-device-edit-btn">编辑</button>'  +
                    '<button data-idx="' + i + '" data-rowidx="' + rowIdx  + '"  class="btn btn-flat btn-sm btn-danger site-device-del-btn">删除</button>'  +
                    '</td>');
                tableTdDom.appendTo(tableTrDom);
                tableTrDom.appendTo(tableBodyDom);
            });
        }
        tableBodyDom.appendTo(tableDom);

        return tableDom;
    }

    var detailRows = [];

    $('#sites-table tbody').on( 'click', 'tr td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = sitesTable.row( tr );

        var idx = $.inArray( tr.attr('id'), detailRows );

        if ( row.child.isShown() ) {
            tr.removeClass( 'details' );
            row.child.hide();

            // Remove from the 'open' array
            detailRows.splice( idx, 1 );
        }
        else {
            tr.addClass( 'details' );
            row.child( format( row.data(), row.index() ) ).show();

            // Add to the 'open' array
            if ( idx === -1 ) {
                detailRows.push( tr.attr('id') );
            }
        }
    } );

    sitesTable.on( 'draw', function () {
        $.each( detailRows, function ( i, id ) {
            $('#'+id+' td.details-control').trigger( 'click' );
        } );
    } );

    var devicesTable = $('#devices-table').DataTable({
        paging: true,
        lengthChange: false,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,
        processing: true,
        bStateSave:true,
        ajax: "/device/getDevices",
        dataSrc: "",
        rowId: "id",
        displayLength: 20,
        columns: [
            { "data": "sn" },
            { "data": "name" },
            { "data": "typeText" },
            { "data": "operatorText" },
            { "data": "band" },
            { "data": "createTime" },
            { "data": "remark" },
            { "data": "siteName" },
            {
                "orderable":      false,
                "data":           null,
                "render" : function (data, type, row ) {
                    var editDom = '<button  class="btn btn-flat btn-sm btn-success device-edit-btn">编辑</button>';
                    var delDom = '<button class="btn btn-flat btn-sm btn-danger device-del-btn">删除</button>';
                    return editDom + delDom;
                }
            }
        ],
        order: [[ 0, 'asc' ]],
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
        buttons: [
            {
                text: "添加设备",
                className: "btn bg-navy btn-flat",
                action: function () {
                    $("#device-form")[0].reset();
                    $('#device-form input[name=action]').val("create");
                    $('#device-sn').parent().parent().removeClass("hidden");
                    deviceTypeSelect.val("1").trigger("change");
                    $("#belongTo").prop("disabled", false);
                    $("#device-dlg").modal("show");
                }
            }
        ],
        dom: "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });


    $("#site-dlg").on('shown.bs.modal', function (e) {
        siteFormValidate.resetForm();
    });

    $('#sites-table tbody').on( 'click', 'tr button.site-edit-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = sitesTable.row( tr ).data();
        //alert(rowData.imsi);
        if( rowData ) {
/*
            $('#site-form')[0].reset();
            $('#site-sn').parent().parent().addClass("hidden");
            $('#site-form input[name=action]').val("update");
            $('#site-form input[name=id]').val(rowData.id);
            $("#site-name").val(rowData.name);
            $('#province').empty();
            $('#province').append(
                '<option value="' + rowData.provinceId + '" selected>' + rowData.province + '</option>'
            );
            $("#province").trigger("change");

            citySelect.val([rowData.cityId]).trigger("change");
            townSelect.val([rowData.townId]).trigger("change");

            $("#site-address").val(rowData.address);
            $("#site-longitude").val(rowData.longitude);
            $("#site-latitude").val(rowData.latitude);
            $("#site-remark").val(rowData.remark);
            $('#site-LC').val(rowData.LC);
            $('#site-CI').val(rowData.CI);*/
            $("#imsi1").val(rowData.imsi);
            $("#operator").val(rowData.operator);
            $("#cityname").val(rowData.cityname);




           /* var queryUrl = "/queryAnalysisiDataAjaxByImsi?" + "startDate=" + startDate +
                "&endDate=" + endDate + "&operator=" + operator +
                "&homeOwnership=" + homeOwnership + "&siteSN=" + siteSN +
                "&deviceSN=" + deviceSN + "&imsi=" +  encodeURIComponent(imsi) ;*/
      //  alert(rowData.imsi);
           /* var queryUrl = "/queryAnalysisiDataAjaxByImsi?"+ "imsi=" +  rowData.imsi ;
            sitesTable1.ajax.url( queryUrl ).load();*/







           // startDate="+$("#capture-range-time").val().slice(0,19)+"&endDate="+$("#capture-range-time").val().slice(22,41)


            $('#site-dlg1').modal('show');
            var queryUrl = "/queryAnalysisiDataAjaxByImsi?"+ "imsi=" +  rowData.imsi + "&startDate="+$("#capture-range-time").val().slice(0,19)+"&endDate="+$("#capture-range-time").val().slice(22,41);
            sitesTable1.ajax.url( queryUrl ).load();

        }
    });


    $("#query-condition-OK").click(function ( evt ) {
        var startDate = $("#capture-range-time").val().slice(0,19);
        var endDate = $("#capture-range-time").val().slice(22,41);

        var imsi = $("#imsi").val();

        var daycount = $("#daycount").val();
        var datacount = $("#datacount").val();
        var period = $("#period").val();


        var queryUrl = "/queryAnalysisiDataAjaxByAll?" + "startDate=" + startDate +
            "&endDate=" + endDate + "&imsi=" + imsi +
            "&daycount=" + daycount + "&datacount=" + datacount +
            "&period=" + period ;
        sitesTable.ajax.url( queryUrl ).load();
        //+ "&imsi=" +  encodeURIComponent(imsi)

      //  alert(startDate+"--"+endDate+"--"+imsi+"--"+daycount+"--"+datacount+"--"+period);

       /* var operator = $("#capture-operator").val();
        var homeOwnership = $("#capture-homeOwnership").val();
        if( 0 == homeOwnership ) {
            homeOwnership="";
        }
        var siteSN = $("#capture-site-sn").val();
        if( 0 == siteSN ) {
            siteSN="";
        }
        var deviceSN = $("#capture-device-sn").val();
        if( 0 == deviceSN ) {
            deviceSN="";
        }
        var imsi = $("#capture-imsi").val();

        var queryUrl = "/queryHistoryData?" + "startDate=" + startDate +
            "&endDate=" + endDate + "&operator=" + operator +
            "&homeOwnership=" + homeOwnership + "&siteSN=" + siteSN +
            "&deviceSN=" + deviceSN + "&imsi=" +  encodeURIComponent(imsi) ;
        historyDataTable.ajax.url( queryUrl ).load();*/

    });




















    $('#sites-table tbody').on( 'click', 'tr button.site-del-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = sitesTable.row( tr ).data();
        if( rowData ) {
            bootbox.confirm({
                size: "small",
                message: "是否删除 '" + rowData.name  + "' 站点",
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
                            url: "/device/delSite",
                            data: { 'id': rowData.id  },
                            dataType:"json",
                            success:  function( data ){
                                if( true == data.status ) {
                                    Messenger().post({
                                        message: data.message,
                                        type: 'success',
                                        showCloseButton: true
                                    });
                                    sitesTable.ajax.reload(null,false);
                                    devicesTable.ajax.reload(null,false);
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
        }

    });


    $("#site-ok-btn").click(function (evt) {
        if( siteFormValidate.form()) {
            var data = $("#site-form").serialize();
            var actionVal = $("#site-form input[name=action]").val();
            var actionUrl = "";
            if( 'create' == actionVal) {
                actionUrl="/device/createSite";
            } else if( 'update' == actionVal ) {
                actionUrl="/device/updateSite";
            } else  {
                return;
            }

            var provinceText = $("#province").find("option:selected").text();
            var cityText = $("#city").find("option:selected").text();
            var townText = $("#town").find("option:selected").text();
            var LC = $("#site-LC").val();
            var CI = $("#site-CI").val();
            data = data + "&provinceText=" + provinceText +
                "&cityText=" + cityText + "&townText=" + townText+ "&LC=" +LC.toString()+ "&CI=" + CI.toString();

            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: actionUrl,
                data: data,
                timeout: 5000,
                success: function ( data ) {
                    $('#site-dlg').modal('hide');
                    if( true == data.status ) {
                        Messenger().post({
                            message:  data.message,
                            type: 'success',
                            showCloseButton: true
                        }) ;
                        sitesTable.ajax.reload(null,false);
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


    $("#device-dlg").on('shown.bs.modal', function (e) {
        deviceFormValidate.resetForm();
    });

    $('#sites-table tbody').on( 'click', 'tr button.device-add-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = sitesTable.row( tr ).data();
        if( rowData ) {

            $('#device-form')[0].reset();
            $('#device-sn').parent().parent().removeClass("hidden");
            $('#device-form input[name=action]').val("create");
            deviceTypeSelect.val("1").trigger("change");
            $('#belongTo').empty();
            $('#belongTo').append(
                '<option value="' + rowData.id + '" selected>' + rowData.name + '</option>'
            );
            $("#belongTo").prop("disabled", true);

            $('#device-dlg').modal('show');
        }
    });

    $('#sites-table tbody').on( 'click', 'tr button.site-device-edit-btn', function () {
        var idx = $(this).data("idx");
        var rowIdx = $(this).data("rowidx");
        var rowData = sitesTable.row( rowIdx ).data();
        if( rowData ) {

            $('#device-form')[0].reset();
            $('#device-sn').parent().parent().addClass("hidden");
            $('#device-form input[name=action]').val("update");
            $('#device-form input[name=id]').val(rowData.devices[idx].id);

            $("#device-name").val(rowData.devices[idx].name);
            deviceTypeSelect.val([rowData.devices[idx].type]).trigger("change");
            if(rowData.devices[idx].band ) {
                deviceBandSelect.val([rowData.devices[idx].band]).trigger("change");
            }
            deviceOperatorSelect.val([rowData.devices[idx].operator]).trigger("change");
            $("#device-manufacturer").val(rowData.devices[idx].manufacturer);
            $("#device-remark").val(rowData.devices[idx].remark);
            $('#belongTo').empty();
            $('#belongTo').append(
                '<option value="' + rowData.id + '" selected>' + rowData.name + '</option>'
            );
            $("#belongTo").prop("disabled", true);

            $('#device-dlg').modal('show');

        }
    });

    $('#sites-table tbody').on( 'click', 'tr button.site-device-del-btn', function () {
        var idx = $(this).data("idx");
        var rowIdx = $(this).data("rowidx");

        var rowData = sitesTable.row( rowIdx ).data();
        if( rowData ) {
            bootbox.confirm({
                size: "small",
                message: "是否删除 '" + rowData.devices[idx].name  + "' 设备",
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
                            url: "/device/delDevice",
                            data: { 'id': rowData.devices[idx].id  },
                            dataType:"json",
                            success:  function( data ){
                                if( true == data.status ) {
                                    Messenger().post({
                                        message: data.message,
                                        type: 'success',
                                        showCloseButton: true
                                    });
                                    sitesTable.ajax.reload(null,false);
                                    devicesTable.ajax.reload(null,false);
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
        }
    });


    $('#devices-table tbody').on( 'click', 'tr button.device-del-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = devicesTable.row( tr ).data();
        if( rowData ) {
            bootbox.confirm({
                size: "small",
                message: "是否删除 '" + rowData.name  + "' 设备",
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
                            url: "/device/delDevice",
                            data: { 'id': rowData.id  },
                            dataType:"json",
                            success:  function( data ){
                                if( true == data.status ) {
                                    Messenger().post({
                                        message: data.message,
                                        type: 'success',
                                        showCloseButton: true
                                    });
                                    devicesTable.ajax.reload(null,false);
                                    sitesTable.ajax.reload(null,false);
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
        }
    });

    $('#devices-table tbody').on( 'click', 'tr button.device-edit-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = devicesTable.row( tr ).data();
        if( rowData ) {

            $('#device-form')[0].reset();
            $('#device-sn').parent().parent().addClass("hidden");
            $('#device-form input[name=action]').val("update");
            $('#device-form input[name=id]').val(rowData.id);

            $("#device-name").val(rowData.name);
            deviceTypeSelect.val([rowData.type]).trigger("change");
            if(rowData.band ) {
                deviceBandSelect.val([rowData.band]).trigger("change");
            }
            deviceOperatorSelect.val([rowData.operator]).trigger("change");
            $("#device-manufacturer").val(rowData.manufacturer);
            $("#device-remark").val(rowData.remark);
            $('#belongTo').empty();
            $('#belongTo').append(
                '<option value="' + rowData.siteId + '" selected>' + rowData.siteName + '</option>'
            );
            $("#belongTo").prop("disabled", false);

            $('#device-dlg').modal('show');

        }
    });

    $("#device-ok-btn").click(function (evt) {
        if( deviceFormValidate.form()) {
            var data = $("#device-form").serialize();
            var actionVal = $("#device-form input[name=action]").val();
            var actionUrl = "";
            if( 'create' == actionVal) {
                actionUrl="/device/createDevice";
            } else if( 'update' == actionVal ) {
                actionUrl="/device/updateDevice";
            } else  {
                return;
            }

            if( $("#belongTo").prop("disabled" ) ){
                var belongToVal = $("#belongTo").val();
                data = data + "&belongTo=" + belongToVal;
            }

            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: actionUrl,
                data: data,
                timeout: 5000,
                success: function ( data ) {
                    $('#device-dlg').modal('hide');
                    if( true == data.status ) {
                        Messenger().post({
                            message:  data.message,
                            type: 'success',
                            showCloseButton: true
                        }) ;
                        devicesTable.ajax.reload(null,false);
                        sitesTable.ajax.reload(null,false);
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


    $("#export-data-xls-btn").click(function (e) {
        exportData(2);
    });

    function exportData( fileType ) {

        /*  var startDate = $("#capture-range-time").val().slice(0,19);
         var endDate = $("#capture-range-time").val().slice(22,41);
         var operator = $("#capture-operator").val();
         var homeOwnership = $("#capture-homeOwnership").val();
         if( 0 == homeOwnership ) {
         homeOwnership="";
         }
         var siteSN = $("#capture-site-sn").val();
         if( 0 == siteSN ) {
         siteSN="";
         }
         var deviceSN = $("#capture-device-sn").val();
         if( 0 == deviceSN ) {
         deviceSN="";
         }
         var imsi = $("#capture-imsi").val();

         var exportCondition = {
         startTime: startDate,
         endTime: endDate,
         operator: operator,
         homeOwnership: homeOwnership,
         siteSN: siteSN,
         deviceSN: deviceSN,
         imsi: imsi
         };

         $("#export-data-btn").attr("disabled","disabled");
         $("#export-data-dropdown-btn").attr("disabled","disabled");*/
        var startDate = $("#capture-range-time").val().slice(0,19);
        var endDate = $("#capture-range-time").val().slice(22,41);

        var imsi = $("#imsi").val();

        var daycount = $("#daycount").val();
        var datacount = $("#datacount").val();
        var period = $("#period").val();

        $.ajax({
            type: "post",
            url: "/queryAnalysisiDataAjaxexportData",
            dataType:"json",
            data:{ startDate: startDate, endDate: endDate, imsi: imsi,daycount: daycount,datacount:datacount,period:period },
            success:  function( data ){
                if( true == data.status ) {
                    $('#export-progress-dlg').modal({
                        backdrop: false,
                        keyboard: false,
                        show: true
                    });
                    exportDataProgress( data.data.exportId );
                } else {
                    $("#export-data-btn").removeAttr("disabled");
                    $("#export-data-dropdown-btn").removeAttr("disabled");
                    Messenger().post({
                        message: data.message,
                        type: 'error',
                        showCloseButton: true
                    }) ;
                }
            },
            error: function ( data  ) {
                $("#export-data-btn").removeAttr("disabled");
                $("#export-data-dropdown-btn").removeAttr("disabled");
                Messenger().post({
                    message:  "网络异常，请稍后再试",
                    type: 'error',
                    showCloseButton: true
                }) ;
            }
        });
    }
    function updateDlList( data , name ) {
        var rowDom=[];
        $.each(data[name], function ( i, info ) {
            rowDom.push('<dt>' + info.label + '</dt>');
            var statusValue = info.value;
            if( (statusValue == undefined) || ( null == statusValue)  ){
                statusValue= "";
            }
            rowDom.push('<dd style="word-break:break-all">' + statusValue + '</dd>');
        });
        $("#device-" + name + "-info").empty();
        $("#device-" + name + "-info").prepend(rowDom.join(''));
    }
    function updateStatusUI( data ) {

        updateDlList(data, "normal");
        updateDlList(data, "board");
        updateDlList(data, "license");
        updateDlList(data, "pa");
        updateDlList(data, "sniffer");
        updateDlList(data, "debug");
    }

    function exportDataProgress( exoprtInfo ) {
        $.ajax({
            type: "post",
            url: "/queryAnalysisiDataAjaxexportDataProgress",
            dataType:"json",
            data:{ exportId: exoprtInfo },
            success:  function( data ){
                if( true == data.status ) {
                    if( data.data.progress >= 100) {
                        $('#export-progress-dlg').modal("hide");
                        $("#export-data-btn").removeAttr("disabled");
                        $("#export-data-dropdown-btn").removeAttr("disabled");
                        //window.open( data.data.url );
                        window.open(data.data.url,"title","height=260,width=466,toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
                    } else {
                        $('#export-progress-bar div.progress-bar').css("width",  data.data.progress + "%");
                        $('#export-progress-bar div.progress-bar').attr("aria-valuenow",  data.data.progress);
                        $("#export-progress-bar span").text( data.data.progress + "% Complete (success)");
                        window.setTimeout( function(){ exportDataProgress( exoprtInfo );}  , 1 * 1000 );
                    }
                } else {
                    $('#export-progress-dlg').modal("hide");
                    $("#export-data-btn").removeAttr("disabled");
                    $("#export-data-dropdown-btn").removeAttr("disabled");
                    Messenger().post({
                        message: data.message,
                        type: 'error',
                        showCloseButton: true
                    }) ;
                }
            },
            error: function ( data  ) {
                $('#export-progress-dlg').modal("hide");
                $("#export-data-btn").removeAttr("disabled");
                $("#export-data-dropdown-btn").removeAttr("disabled");
                Messenger().post({
                    message:  "网络异常，请稍后再试",
                    type: 'error',
                    showCloseButton: true
                }) ;
            }
        });
    }


});