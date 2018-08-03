/**
 * Created by jiangqi.yang on 2016/12/28.
 */


$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

    var emailTemplateTable = $('#email-template-table').DataTable({
        paging: true,
        lengthChange: false,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,
        processing: true,
        ajax: "/system/email/getEmailTpls",
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
            {
                "data": "eventType",
                "render" : function (data, type, row ) {
                    var eventTypeText = "未知";
                    switch (data) {
                        case 0:
                            eventTypeText="黑名单";
                            break;
                        case 1:
                            eventTypeText="特殊归属地";
                            break;
                        case 2:
                            eventTypeText="设备告警";
                            break;
                        case 3:
                            eventTypeText="找回密码";
                            break
                        default:
                            eventTypeText = "未知";
                       }
                    return eventTypeText;
                }
            },
            { "data": "subject" },
            { "data": "remark" },
            { "data": "createTime" },
            {
                "orderable":      false,
                "data":           "active",
                "render" : function (data, type, row ) {
                    var dom;
                    var activeDom;

                    var editDom = '<button class="btn btn-flat btn-sm bg-navy email-edit-btn">编辑</button>';

                    if( data == 1 ) {
                        activeDom = '<button class="btn btn-flat btn-warning btn-sm email-inactivated-btn">停用</button>';
                    } else {
                        activeDom = '<button class="btn btn-flat bg-olive btn-sm email-activated-btn">启用</button>';
                    }
                    dom = editDom +  activeDom;
                    return dom;
                }
            }
        ],
        columnDefs: [
            {
                orderable: false,
                className: 'select-checkbox',
                targets:   0
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
                text: "创建模板",
                className: "btn bg-navy btn-flat",
                action: function () {
                    $('#email-form')[0].reset();
                    $('#email-form input[name=action]').val("create");
                    $('#emailTpl-dlg').modal('show');
                }
            },
            {
                text: "删除模板",
                className: "btn bg-navy btn-flat",
                action: function () {

                    var count = emailTemplateTable.rows( { selected: true } ).count();
                    if( 0 == count ) {
                        bootbox.alert({
                            size: "small",
                            message:"请选择模板"
                        });
                        return;
                    }

                    var rowData = emailTemplateTable.rows( { selected: true }).data();

                    bootbox.confirm({
                        size: "small",
                        message: "是否删除模板",
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
                                    url: "/system/email/delEmailTps",
                                    data: { 'id':  idArray },
                                    dataType:"json",
                                    success:  function( data ){
                                        if( true == data.status ) {
                                            Messenger().post({
                                                message: data.message,
                                                type: 'success',
                                                showCloseButton: true
                                            });
                                            emailTemplateTable.ajax.reload(null,false);
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

    $("#email-content").wysihtml5();
    $("#event-type").select2({
        language: "zh-CN",
        minimumResultsForSearch: Infinity
    });


    $('#email-template-table tbody').on( 'click', 'tr button.email-activated-btn', function () {
        var buttonDom = this;
        var tr = $(buttonDom).closest('tr');
        var rowData = emailTemplateTable.row( tr ).data();
        bootbox.confirm({
            size: "small",
            message: "是否启用模板",
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
                        url: "/system/email/setEmailTpsActive",
                        data: { 'id': rowData.id, 'active': 1 },
                        dataType:"json",
                        success:  function( data ){
                            if( true == data.status ) {
                                emailTemplateTable.ajax.reload(null,false);
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

    $('#email-template-table tbody').on( 'click', 'tr button.email-inactivated-btn', function () {
        var buttonDom = this;
        var tr = $(buttonDom).closest('tr');
        var rowData = emailTemplateTable.row( tr ).data();
        bootbox.confirm({
            size: "small",
            message: "是否停用模板",
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
                        url: "/system/email/setEmailTpsActive",
                        data: { 'id': rowData.id, 'active': 0 },
                        dataType:"json",
                        success:  function( data ){
                            if( true == data.status ) {
                                emailTemplateTable.ajax.reload(null,false);
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

    $('#email-template-table tbody').on( 'click', 'tr button.email-edit-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = emailTemplateTable.row( tr ).data();
        if( rowData ) {

            $('#email-form')[0].reset();
            $('#email-form input[name=action]').val("update");
            $('#email-form input[name=id]').val(rowData.id);

            $('#email-subject').val(rowData.subject);
            $('#email-content').val(rowData.content);
            $('#event-type').val(rowData.eventType).trigger("change");
            $('#email-remark').val(rowData.remark);

            $('#emailTpl-dlg').modal('show');
        }
    });

    var emailTplFormValidate = $( "#email-form" ).validate( {
        rules: {
            emailSubject: {
                required: true,
                stringCheck:true
            },
            contentTpl: {
                required: true
            }
        },
        messages: {
            emailSubject: {
                required: "请输入标题内容",
                stringCheck:"请输入有效的标题内容"
            },
            contentTpl: {
                required: "请输入模板内容"
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


    $('#emailTpl-dlg').on('shown.bs.modal', function (e) {
        emailTplFormValidate.resetForm();
    });

    $("#emailTpl-ok-btn").on('click', function () {

        if( emailTplFormValidate.form()) {
            var data = $("#email-form").serialize();
            var actionVal = $("#email-form input[name=action]").val();
            var actionUrl = "";
            if( 'create' == actionVal) {
                actionUrl="/system/email/createEmailTps";
            } else if( 'update' == actionVal ) {
                actionUrl="/system/email/updateEmailTps";
            } else  {
                return;
            }
            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: actionUrl,
                data: data,
                timeout: 5000,
                success: function ( data ) {
                    $('#emailTpl-dlg').modal('hide');
                    if( true == data.status ) {
                        Messenger().post({
                            message:  data.message,
                            type: 'success',
                            showCloseButton: true
                        }) ;
                        emailTemplateTable.ajax.reload(null,false);
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

});