/**
 * Created by  jiangqi.yang on 2017/1/31.
 */

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    }

    $('input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });

    var rolesTable = $('#roles-table').DataTable({
        paging: true,
        lengthChange: false,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,
        processing: true,
        ajax: "/user/getRoles",
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
            { "data": "name" },
            {
                "data": "defaultFlag",
                "render": function (data, type, row) {
                    var defaultText = "否";
                    if( data == 1 ) {
                        defaultText = "是";
                    }
                    return defaultText;
                }
            },
            { "data": "createTime" },
            { "data": "remark" },
            {
                "orderable":     false,
                "data":           "defaultFlag",
                "render" : function (data, type, row ) {
                    var dom;
                    var defaultDom;

                    var editDom = '<button class="btn btn-flat btn-sm bg-navy role-edit-btn">编辑</button>';

                   /* if( data == 0 ) {
                        defaultDom = '<button class="btn btn-flat bg-olive btn-sm role-default-btn" >默认</button>';
                    } else {
                        defaultDom = '<button class="btn btn-flat bg-olive btn-sm disabled role-default-btn" disabled >默认</button>';
                    }*/
                    dom = editDom /*+ defaultDom*/;
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
                text: "创建角色",
                className: "btn bg-navy btn-flat",
                action: function () {

                    $('#role-form input[name=action]').val("create");
                    $('#role-name').parent().parent().removeClass("hidden");
                    $('input[type="checkbox"]').iCheck('uncheck');
                    $('#role-name').val("");
                    $('#remark').val("");
                    $('#role-dlg').modal('show');
                }
            },
            {
                text: "删除角色",
                className: "btn bg-navy btn-flat",
                action: function () {

                    var count = rolesTable.rows( { selected: true } ).count();
                    if( 0 == count ) {
                        bootbox.alert({
                            size: "small",
                            message:"请选择角色"
                        });
                        return;
                    }

                    var rowData = rolesTable.rows( { selected: true }).data();

                    bootbox.confirm({
                        size: "small",
                        message: "是否删除角色",
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
                                    url: "/user/delRole",
                                    data: { 'id':  idArray },
                                    dataType:"json",
                                    success:  function( data ){
                                        if( true == data.status ) {
                                            Messenger().post({
                                                message: data.message,
                                                type: 'success',
                                                showCloseButton: true
                                            });
                                            rolesTable.ajax.reload(null,false);
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


    $('#roles-table tbody').on( 'click', 'tr button.role-default-btn', function () {
        var buttonDom = this;
        var tr = $(buttonDom).closest('tr');
        var rowData = rolesTable.row( tr ).data();

        var notifyText = '设置当前角色"' + rowData.name +  '"为系统默认角色';
        bootbox.confirm({
            size: "small",
            message: notifyText,
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
                        url: "/user/setRoleDefault",
                        data: { 'id': rowData.id },
                        dataType:"json",
                        success:  function( data ){
                            if( true == data.status ) {
                                Messenger().post({
                                    message: data.message,
                                    type: 'success',
                                    showCloseButton: true
                                });
                                rolesTable.ajax.reload(null,false);
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


    var roleFormValidate = $( "#role-form" ).validate( {
        rules: {
            roleName: {
                required: true,
                stringCheck:true,
                remote: {
                    type: "POST",
                    url: "/user/checkRoleName",
                    data: {
                        roleName: function() {
                            return $( "#role-name" ).val();
                        }
                    }
                }
            },
	    remark:{
	  	  	required: true,
	        stringCheck:true
	    	}
        },
       
        messages: {
            roleName: {
                required: "请输入角色名",
                stringCheck:"只能包括中文字、英文字母、数字和下划线",
                remote:"角色名已经使用"
            },
            remark: {
                required: "请输入中文角色名",
                stringCheck:"只能包括中文字、英文字母、数字和下划线"
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

    $('#role-dlg').on('shown.bs.modal', function (e) {
        roleFormValidate.resetForm();
    });

    $("#role-ok-btn").on('click', function () {

        var that  = this;

        if( roleFormValidate.form()) {

            var data = $("#role-form").serialize();
            var actionVal = $("#role-form input[name=action]").val();
            var actionUrl = "";
            if( 'create' == actionVal) {
                actionUrl="/user/createRole";
            } else if( 'update' == actionVal ) {
                actionUrl="/user/updateRole";
            } else  {
                return;
            }

            $(that).attr("disabled","disabled");

            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: actionUrl,
                data: data,
                timeout: 5000,
                success: function ( data ) {
                    $(that).removeAttr("disabled");
                    $('#role-dlg').modal('hide');
                    if( true == data.status ) {
                        Messenger().post({
                            message:  data.message,
                            type: 'success',
                            showCloseButton: true
                        }) ;
                        rolesTable.ajax.reload(null,false);
                    } else {
                        Messenger().post({
                            message:  data.message,
                            type: 'error',
                            showCloseButton: true
                        }) ;
                    }
                },
                error: function () {
                    $(that).removeAttr("disabled");
                }
            });

        }
    });

    $('#roles-table tbody').on( 'click', 'tr button.role-edit-btn', function () {
        var buttonDom = this;
        var tr = $(buttonDom).closest('tr');
        var rowData = rolesTable.row( tr ).data();

        if(rowData) {

            $('#role-form input[name=id]').val(rowData.id);
            $('#role-form input[name=action]').val("update");
            $('input[type="checkbox"]').iCheck('uncheck');
            $('#role-name').parent().parent().addClass("hidden");

            $('#role-name').val(rowData.name);
            $('#remark').val(rowData.remark);
            
            $.each( rowData.resources, function ( idx, resoucre ) {
                $("#resource-" + resoucre.value).iCheck("check");
            } );

            $('#role-dlg').modal('show');
        }


    });

});