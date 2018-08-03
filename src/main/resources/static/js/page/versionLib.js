/**
 * Created by  jiangqi.yang  on 2016/11/30.
 */

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    }


    var versionLibTable = $('#version-lib-table').DataTable({
        paging: true,
        lengthChange: false,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,
        processing: true,
        ajax: "/device/version/getLibraries",
        dataSrc: "",
        select: {
            style: "multi",
            selector: "td:first-child"
        },
        rowId: "id",
        columns: [
            {
                "orderable":      false,
                "data":           null,
                "defaultContent": ""
            },
            { "data": "version" },
            { "data": "remark" },
            { "data": "fpgaVersion" },
            { "data": "bbuVersion" },
            { "data": "swVersion" },
            { "data": "uploadTime" },
            { "data": "uploadUser" },
            {
                "orderable":      false,
                "data":            null,
                "render" : function (data, type, row ) {
                    var dom = '<button data-id="' +  + data.id + '" class="btn btn-flat btn-sm bg-navy notify-upgrade-btn">下载版本</button>';
                    return dom;
                }
            }
        ],
        columnDefs: [
            {
                orderable: false,
                className: 'select-checkbox',
                targets:   0
            },
        ],
        order: [[ 1, 'desc' ]],
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
                text: "创建版本",
                className: "btn bg-navy btn-flat",
                action: function () {

                    $('#version-form')[0].reset();
                    $('#version-dlg').modal('show');
                }
            },
            {
                text: "删除版本",
                className: "btn bg-navy btn-flat",
                action: function () {

                    var count = versionLibTable.rows( { selected: true } ).count();
                    if( 0 == count ) {
                        bootbox.alert({
                            size: "small",
                            message:"请选择版本"
                        });
                        return;
                    }

                    var rowData = versionLibTable.rows( { selected: true }).data();

                    bootbox.confirm({
                        size: "small",
                        message: "是否删除版本",
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

                                var idArray = [];
                                $.each(rowData, function ( i,val ) {
                                    idArray.push( val.id);
                                });

                                $.ajax({
                                    type: "post",
                                    url: "/device/version/delLibrary",
                                    data: { 'id':  idArray },
                                    dataType:"json",
                                    success:  function( data ){
                                        if( true == data.status ) {
                                            Messenger().post({
                                                message: data.message,
                                                type: 'success',
                                                showCloseButton: true
                                            });
                                            versionLibTable.ajax.reload(null,false);
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
            }
        ],
        dom: "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });

    $('#uploadFile').fileupload({
        url: "/uploadDvVerFile",
        paramName: "uploadFile",
        dataType: 'json',
        done: function (e, data) {
            if( true == data.result.status) {
                $("#filename").val( data.result.data.originalFilename );
                $("#uploadFilename").val( data.result.data.filename );
            } else {
                Messenger().post({
                    message:  data.result.message,
                    type: 'error',
                    showCloseButton: true
                });
            }
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);

            $("#upload-progress .progress-bar").css(
                'width',
                progress + '%'
            );
            $("#upload-progress .progress-bar").attr("aria-valuenow", progress);
            $("#upload-progress .progress-bar span").text(  progress + '% Complete' );

        },
        start: function ( e) {
            $("#filename").val("");
            $("#uploadFilename").val("");
            $("#upload-progress").show();
        },
        stop: function ( e ) {
            $("#upload-progress").hide();
            versionFormValidate.element( "#filename" );
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');


    var versionFormValidate = $( "#version-form" ).validate( {
        rules: {
            filename: "required",
            version:{
                required: true
            }
        },
        messages: {
            filename: "请上传版本文件",
            version:{
                required: "请输入版本号"
            }
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            // Add the `help-block` class to the error element
            error.addClass( "help-block" );

            if ( element.prop( "type" ) === "checkbox" ) {
                error.insertAfter( element.parent( "label" ) );
            } else if( element.data( "upload" ) === "display" ) {
                error.insertAfter( element.parent( ".input-group" ) );
            }
            else {
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

    $("#version-dlg").on("shown.bs.modal", function (e) {
        versionFormValidate.resetForm();
    });

    $("#version-ok-btn").click(function (e) {

        var uploadFilename = $("#uploadFilename").val();
        if( (uploadFilename == undefined) || ( uploadFilename == "" )) {
            $("#filename").val("");
            versionFormValidate.element( "#filename" );
            return ;
        }

        if( versionFormValidate.form()) {

            var versionData = $("#version-form").serialize();

            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: "/device/version/createLibrary",
                data: versionData,
                timeout: 5000,
                success: function ( data ) {
                    $('#version-dlg').modal('hide');
                    if( true == data.status ) {
                        Messenger().post({
                            message:  data.message,
                            type: 'success',
                            showCloseButton: true
                        }) ;
                        versionLibTable.ajax.reload(null,false);
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

    $('.slimScrollDiv').slimScroll({ height: "450px" });

    var $devicesTree = null;

    $('#version-lib-table tbody').on( 'click', 'tr button.notify-upgrade-btn', function () {
        var buttonDom = this;
        var tr = $(buttonDom).closest('tr');
        var rowData = versionLibTable.row(tr).data();
        $("#upgrade-version-id").val(rowData.id);
        $("#upgrade-version").text(rowData.version);
        $("#upgrade-remark").text(rowData.remark);
        $("#upgrade-fpgaVersion").text(rowData.fpgaVersion);
        $("#upgrade-bbuVersion").text(rowData.bbuVersion);
        $("#upgrade-appVersion").text(rowData.swVersion);


        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: "/device/getSites",
            timeout: 5000,
            success: function ( data ) {
                if( true == data.status ) {

                    var treeData = [];
                    $.each( data.data, function ( i, siteInfo ) {
                        var node = {
                            isDevice: false,
                            text:  siteInfo.name
                        };
                        var childNodes = [];

                        $.each( siteInfo.devices, function ( j, deviceInfo ) {
                            var childNode = {
                                isDevice: true,
                                text:  deviceInfo.name,
                                sn:  deviceInfo.sn,
                                selectable: true,
                                state: {
                                    checked: false,
                                    disabled: false,
                                    expanded: true,
                                    selected: false
                                }
                            };

                            childNodes.push(childNode);
                        } );

                        node.nodes = childNodes;

                        treeData.push(node);
                    } );

                    $devicesTree =  $('#devices-tree').treeview({
                        data: treeData,
                        // injectStyle: false,
                        showCheckbox: true,
                        levels: 5,
                        showBorder: false,
                        showTags: true,
                        onNodeChecked: function ( event, node ) {
                            if( (node.nodes != undefined) &&  (node.nodes.length > 0) ) {
                                $.each( node.nodes, function () {
                                    $devicesTree.treeview('checkNode', [ this.nodeId, { silent: true }] );
                                });
                            }
                        },
                        onNodeUnchecked: function ( event, node) {
                            if( (node.nodes != undefined) &&  (node.nodes.length > 0) ) {
                                $.each( node.nodes, function () {
                                    $devicesTree.treeview('uncheckNode', [ this.nodeId, { silent: true }] );
                                });
                            }
                        }
                    });

                    $("#upgrade-dlg").modal("show");

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

    $("#upgrade-ok-btn").click(function () {

        if( null == $devicesTree ) {
            return;
        }

        var checkNodes =  $devicesTree.treeview('getChecked');
        var snArray = [];
        $.each(checkNodes, function ( i, deviceCheckNode ) {

            if(deviceCheckNode.isDevice ) {
                snArray.push(deviceCheckNode.sn);
            }
        } );

        if( snArray.length == 0 ) {
            Messenger().post({
                message:  "请选择设备",
                type: 'error',
                showCloseButton: true
            }) ;
            return ;
        }

        var versionId = $("#upgrade-version-id").val();

        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: "/device/version/upgradeDevice",
            data: { versionId: versionId,  sn:snArray },
            timeout: 5000,
            success: function ( data ) {
                $('#upgrade-dlg').modal('hide');
                if( true == data.status ) {
                    Messenger().post({
                        message:  data.message,
                        type: 'success',
                        showCloseButton: true
                    }) ;
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


});