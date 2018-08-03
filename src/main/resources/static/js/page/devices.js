/**
 * Created by jiangqi.yang  on 2016/11/2.
 */


$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };
    
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
                required: "请输入设备名称",
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


    var sitesTable = $('#sites-table').DataTable({
        paging: true,
        lengthChange: false,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,
        processing: true,
        bStateSave:true,
        ajax: "/device/getSites",
        dataSrc: "",
        select: {
            style: "single",
            selector: "td:not(':last-child')"

        },
        rowId: "id",
        displayLength: 20,
        columns: [
            {
                "class":          "details-control",
                "orderable":      false,
                "data":           null,
                "defaultContent": ""
            },
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
            { "data": "remark" },
            {
                "orderable":      false,
                "data":           null,
                "render" : function (data, type, row ) {
                    var addDom = '<button class="btn btn-flat btn-sm bg-navy device-add-btn">添加设备</button>';
                    var editDom = '<button class="btn btn-flat btn-sm btn-success site-edit-btn">编辑</button>';
                    var delDom = '<button class="btn btn-flat btn-sm btn-danger site-del-btn">删除</button>';
                    return addDom + editDom + delDom;
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
            {
                text: "添加站点",
                className: "btn bg-navy btn-flat",
                action: function () {
                    $("#site-form")[0].reset();
                    $('#site-form input[name=action]').val("create");
                    $('#site-sn').parent().parent().removeClass("hidden");
                    $("#site-dlg").modal("show");
                }
            }
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
                    // '<button data-idx="' + i + '" data-rowidx="' + rowIdx  + '"  class="btn btn-flat btn-sm btn-warning site-device-upnumex-btn">上号异常</button>' +
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
        });
    });

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
                    // var upNumExDom = '<button class="btn btn-flat btn-sm btn-warning device-upnumex-btn">上号异常</button>';
                    // return editDom + delDom + upNumExDom;
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
        if( rowData ) {

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
            $('#site-CI').val(rowData.CI);
            $('#site-dlg').modal('show');

        }
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
            $('#site-ok-btn').attr('disabled',true);
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
                    $('#site-ok-btn').attr('disabled',false);
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

    $('#PLL').click(function(){
        $("#site-longitude2").val($("#site-longitude").val());
        $("#site-latitude2").val($("#site-latitude").val());
        $('#map-map').modal('show');

        /*
        //ztree初始化时候，已经选择的站点addSiteIds赋值给zTree
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        for(var i = 0;i<addSiteIds.length;i++){
            var node = treeObj .getNodeByParam('id',addSiteIds[i]);
            treeObj.checkNode(node, true, true);
        }
        $('#device-upnumex-dlg').modal('show');*/
    });

    $('#map-map').on('shown.bs.modal', function () {

        if($("#site-longitude2").val().length==0||$("#site-latitude2").val().length==0){
            J1=$("#center-point-lng").val();
            W1=$("#center-point-lat").val();
            var point11 = new BMap.Point(J1, W1);
            var zoomVal  = Number($("#zoom-value").val());
            deviceMap.setCenter(point11);
            deviceMap.clearOverlays();
        }else{
        J=$("#site-longitude2").val();//e.point.lng;
        W=$("#site-latitude2").val();//e.point.lat;
        var point1 = new BMap.Point(J, W);
        var zoomVal  = Number($("#zoom-value").val());
        deviceMap.setCenter(point1);
        deviceMap.clearOverlays();
        var marker = new BMap.Marker(point1);  // 创建标注
        deviceMap.addOverlay(marker);               // 将标注添加到地图中
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
        }
    })

    $("#map-ok").click(function (evt) {
       /* $("#site-longitude").val("123");
        $("#site-latitude").val("32321");*/
        $('#map-map').modal('hide');
    });

    $("#map-cancle").click(function (evt) {
         $("#site-longitude").val("");
         $("#site-latitude").val("");
        $('#map-map').modal('hide');
    });

    function buildMap(){
        var mapStyleData = [
            {
                "featureType": "land",
                "elementType": "all",
                "stylers": {
                    "lightness": 100,
                    "saturation": -100
                }
            },
            {
                "featureType": "water",
                "elementType": "all",
                "stylers": {
                    "lightness": 47
                }
            },
            {
                "featureType": "manmade",
                "elementType": "geometry",
                "stylers": {
                    "lightness": 28
                }
            },
            {
                "featureType": "road",
                "elementType": "geometry.fill",
                "stylers": {
                    "lightness": 82
                }
            },
            {
                "featureType": "road",
                "elementType": "geometry.stroke",
                "stylers": {
                    "lightness": -76
                }
            },
            {
                "featureType": "green",
                "elementType": "all",
                "stylers": {
                    "lightness": 63,
                    "saturation": -100
                }
            },
            {
                "featureType": "boundary",
                "elementType": "geometry.fill",
                "stylers": {
                    "lightness": 80,
                    "saturation": 1
                }
            },
            {
                "featureType": "boundary",
                "elementType": "geometry.stroke",
                "stylers": {
                    "lightness": -75,
                    "saturation": -100
                }
            }
        ];

        $(window, ".wrapper").resize(function () {
            fixMapperWrapper();
        });

        fixMapperWrapper();

        var mapOptions={
            minZoom: 5,
            maxZoom: 18,
            mapType:  BMAP_NORMAL_MAP
        }

        mp = new BMap.Map('device-map', mapOptions);

        var centerLng  = Number($("#center-point-lng").val());
        var centerLat  = Number($("#center-point-lat").val());
        var zoomVal  = Number($("#zoom-value").val());
        var mapOnlineSw  = Number($("#map-online-sw").val());
        var point = new BMap.Point( centerLng, centerLat );
        mp.centerAndZoom( point, zoomVal);

        mp.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

        //alert( $("#site-longitude2").val()+"------"+$("#site-latitude2").val());
        mp.clearOverlays();
        var Jump=new BMap.Point( $("#site-longitude2").val(), $("#site-latitude2").val() );
        var marker1 = new BMap.Marker(Jump);
        mp.addOverlay(marker1);
        marker1.setAnimation(BMAP_ANIMATION_BOUNCE);

        // mp.panTo(findPoint);
        //地图样式
        // if( 1 == mapOnlineSw ) {
        //     // 在线
        //    /*     mp.setMapStyle({style:'normal'});*/
        //     //mp.setMapStyle({ styleJson: mapStyleData });
        //  }
        /* else
         {
             // 离线
             mp.setMapStyle({ styleJson: mapStyleData });
         }*/

        mp.setMapStyle({style:'normal'});

        var scaleControl = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
        var navigationControl = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件

        //添加控件
        mp.addControl( scaleControl );
        mp.addControl( navigationControl );

        //创建站点标记
        //var siteMarkers = new SiteMarker({ map: mp, clickEvent: true ,url: "/device/getSites"});

        // 百度地图API功能
        // var map = new BMap.Map("device-map");
        //  map.centerAndZoom("重庆",10);
        //单击获取点击的经纬度单击获取点击的经纬度

        var J;
        var W;
        mp.addEventListener("click",function(e){
            mp.clearOverlays();
            //alert(e.point.lng + "," + e.point.lat);
            $("#site-longitude").val(e.point.lng);
            $("#site-latitude").val(e.point.lat);
            /*   $("#site-longitude1").val(e.point.lng);
               $("#site-latitude1").val(e.point.lat);*/
            $("#site-longitude2").val(e.point.lng);
            $("#site-latitude2").val(e.point.lat);
            /*J=e.point.lng;
            W=e.point.lat;*/
            J=$("#site-longitude2").val();//e.point.lng;
            W=$("#site-latitude2").val();//e.point.lat;
            var point1 = new BMap.Point(J, W);
            mp.panTo(point1);
            var marker = new BMap.Marker(point1);  // 创建标注
            mp.addOverlay(marker);               // 将标注添加到地图中
            marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
            J="";
            W="";

        });

        return mp;
    };

    function  fixMapperWrapper() {
        //Get window height and the wrapper heigh
        var neg = $('.main-header').outerHeight() + $('.main-footer').outerHeight();
        var window_height = $(window).height();
        var sidebar_height = $(".sidebar").height();
        //Set the min-height of the content and sidebar based on the
        //the height of the document.
        if ($("body").hasClass("fixed")) {
            $("#device-map").css('min-height', window_height - $('.main-footer').outerHeight()- $('.content-header').outerHeight() );
        } else {
            var postSetWidth;
            if (window_height >= sidebar_height) {
                $("#device-map").css('min-height', window_height - neg - $('.content-header').outerHeight());
                postSetWidth = window_height - neg - $('.content-header').outerHeight();
            } else {
                $("#device-map").css('min-height', sidebar_height- $('.content-header').outerHeight());
                postSetWidth = sidebar_height - $('.content-header').outerHeight();
            }

            //Fix for the control sidebar height
            var controlSidebar = $($.AdminLTE.options.controlSidebarOptions.selector);
            if (typeof controlSidebar !== "undefined") {
                if (controlSidebar.height() > postSetWidth)
                    $("#device-map").css('min-height', controlSidebar.height() - $('.content-header').outerHeight());
            }
        }
    }

    var deviceMap = buildMap();

});