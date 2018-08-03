/**
 * Created by  jiangqi.yang  on 2016/10/31.
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

    $("#role").select2({
        ajax: {
            url: "/user/getRoles",
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

    $("#sex").select2();

    var resetPwdFormValidate = $( "#reset-pwd-form" ).validate( {
        rules: {
            newPassword: "required",
            reNewPassword:{
                required: true,
                equalTo: "#newPwd"
            }
        },
        messages: {
            newPassword: "请输入新的密码",
            reNewPassword:{
                required: "请在输入一次密码",
                equalTo: "两次密码不相等"
            }
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            // Add the `help-block` class to the error element
            error.addClass( "help-block" );

            if ( element.prop( "type" ) === "checkbox" ) {
                error.insertAfter( element.parent( "label" ) );
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

    var userFormValidate = $( "#user-form" ).validate( {
        rules: {
            loginName: {
                required: true,
                stringCheck:true,
                remote: {
                    type: "POST",
                    url: "/user/checkLoginName",
                    data: {
                        loginName: function() {
                            return $( "#login-name" ).val();
                        }
                    }
                }
            },
            role: {
                required: true
            },
            password:{
                required: true,
                isSpace: true
            },
            rePassword:{
                required: true,
                equalTo: "#password"
            },
            userName: {
                required: true,
                stringCheck:true
            },
            sex: {
                required: true
            },
            email: "email",
            mobilePhone: {
                isMobile: true
            }
        },
        messages: {
            loginName: {
                required: "请输入登陆名",
                stringCheck:"只能包括中文字、英文字母、数字和下划线",
                remote:"当前登录名已存在，请输入新的登录名"
            },
            role: {
                required: "请选择角色"
            },
            password:{
                required: "请输入的密码",
                isSpace: "密码不能为空"
            },
            rePassword:{
                required: "请再次输入的密码",
                equalTo: "两次密码不相等"
            },
            userName: {
                required: "请输入用户姓名",
                stringCheck:"只能包括中文字、英文字母、数字和下划线"
            },
            sex: {
                required: "选择性别"
            },
            email: "请输入有效的电子邮件地址",
            mobilePhone: {
                isMobile: "手机号码格式错误"
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

    var usersTable = $('#users-table').DataTable({
        paging: true,
        lengthChange: false,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,
        processing: true,
        ajax: "/user/getUsers",
        dataSrc: "",
        select: {
            style: "multi",
            selector: "td:first-child"
        },
        rowId: "loginName",
        columns: [
            {
                "orderable":      false,
                "data":           null,
                "defaultContent": ""
            },
            { "data": "loginName" },
            { "data": "userName" },
            { "data": "sex" },
            { "data": "email" },
            { "data": "mobilePhone" },
            { "data": "role" },
            { "data": "createTime" },
            { "data": "remark" },
            {
                "orderable":      false,
                 "data":           "locked",
                 "render" : function (data, type, row ) {
                     var dom;
                     var activeDom;

                     var editDom = '<button class="btn btn-flat btn-sm bg-navy user-edit-btn">编辑</button>';
                     var pwdDom = '<button class="btn btn-flat btn-sm bg-purple user-reset-pwd-btn">修改密码</button>';

                     if( data == 0 ) {
                         activeDom = '<button class="btn btn-flat btn-warning btn-sm user-inactivated-btn">冻结</button>';
                     } else {
                         activeDom = '<button class="btn btn-flat bg-olive btn-sm user-activated-btn">激活</button>';
                     }
                     dom = editDom + pwdDom + activeDom;
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
                text: "创建用户",
                className: "btn bg-navy btn-flat",
                action: function () {
                    
                    $('#user-form')[0].reset();
                    $('#user-form input[name=action]').val("create");
                    $('#login-name').parent().parent().removeClass("hidden");
                    $('#password').parent().parent().removeClass("hidden");
                    $('#re-password').parent().parent().removeClass("hidden");
                    $('#locked').iCheck('destroy');
                    $('#locked').iCheck({
                        checkboxClass: 'icheckbox_minimal-blue',
                        radioClass: 'iradio_minimal-blue'
                    });
                    $('#locked').iCheck('uncheck');
                    $('#users-dlg').modal('show');
                }
            },
            {
                text: "删除用户",
                className: "btn bg-navy btn-flat",
                action: function () {

                    var count = usersTable.rows( { selected: true } ).count();
                    if( 0 == count ) {
                        bootbox.alert({
                            size: "small",
                            message:"请选择用户"
                        });
                        return;
                    }

                    var rowData = usersTable.rows( { selected: true }).data();

                    bootbox.confirm({
                        size: "small",
                        message: "是否删除用户",
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
                                    url: "/user/delUser",
                                    data: { 'id':  idArray },
                                    dataType:"json",
                                    success:  function( data ){
                                        if( true == data.status ) {
                                            Messenger().post({
                                                message: data.message,
                                                type: 'success',
                                                showCloseButton: true
                                            });
                                            usersTable.ajax.reload(null,false);
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

    $('#users-table tbody').on( 'click', 'tr button.user-edit-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = usersTable.row( tr ).data();
        if( rowData ) {

            $('#user-form')[0].reset();
            $('#user-form input[name=action]').val("update");
            $('#login-name').parent().parent().addClass("hidden");
            $('#password').parent().parent().addClass("hidden");
            $('#re-password').parent().parent().addClass("hidden");

            $('#user-form input[name=id]').val(rowData.id);
            $('#role').empty();
            $('#role').append(
                '<option value="' + rowData.roleId + '" selected>' + rowData.role + '</option>'
            );
            $('#user-name').val(rowData.userName);
            $("#sex").val(rowData.sexVal).trigger("change");
            $('#email').val(rowData.email);
            $('#mobile-phone').val(rowData.mobilePhone);
            $('#remark').val(rowData.remark);
            $('#locked').iCheck('destroy');
            $('#locked').iCheck({
                checkboxClass: 'icheckbox_minimal-blue',
                radioClass: 'iradio_minimal-blue'
            });
            if( 1 ==  rowData.locked ) {
                $('#locked').iCheck('check');
            } else {
                $('#locked').iCheck('uncheck');
            }

            $('#users-dlg').modal('show');
        }
    });

    $('#users-table tbody').on( 'click', 'tr button.user-reset-pwd-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = usersTable.row( tr ).data();

        $('#reset-pwd-form')[0].reset();
        if( rowData ) {
            $('#reset-pwd-id').val(rowData.id);
            $('#reset-pwd-dlg').modal('show');
        }

    });

    $('#users-table tbody').on( 'click', 'tr button.user-activated-btn', function () {
        var buttonDom = this;
        var tr = $(buttonDom).closest('tr');
        var rowData = usersTable.row( tr ).data();

        bootbox.confirm({
            size: "small",
            message: "是否激活用户",
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
                        url: "/user/setAccountActive",
                        data: { 'id': rowData.id, 'locked': false },
                        dataType:"json",
                        success:  function( data ){
                            if( true == data.status ) {
                                $(buttonDom).removeClass("user-activated-btn");
                                $(buttonDom).removeClass("bg-olive");
                                $(buttonDom).addClass("user-inactivated-btn");
                                $(buttonDom).addClass("btn-warning");
                                $(buttonDom).text("冻结");
                                usersTable.ajax.reload(null,false);
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

    $('#users-table tbody').on( 'click', 'tr button.user-inactivated-btn', function () {
        var buttonDom = this;
        var tr = $(buttonDom).closest('tr');
        var rowData = usersTable.row( tr ).data();
        bootbox.confirm({
            size: "small",
            message: "是否冻结用户",
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
                       url: "/user/setAccountActive",
                       data: { 'id': rowData.id, 'locked': true },
                       dataType:"json",
                       success:  function( data ){
                           if( true == data.status ) {
                               $(buttonDom).removeClass("user-inactivated-btn");
                               $(buttonDom).removeClass("btn-warning");
                               $(buttonDom).addClass("user-activated-btn");
                               $(buttonDom).addClass("bg-olive");
                               $(buttonDom).text("激活");
                               usersTable.ajax.reload(null,false);
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

    $('#reset-pwd-dlg').on('shown.bs.modal', function (e) {
        resetPwdFormValidate.resetForm();
    });

    $('#resetPwd-ok-btn').on("click", function () {

        if( resetPwdFormValidate.form()) {

            var  data = $("#reset-pwd-form").serialize();
            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: '/user/updatePassword',
                data: data,
                timeout: 5000,
                success: function ( data ) {
                    $('#reset-pwd-dlg').modal('hide');
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
        }
    });

    $('#users-dlg').on('shown.bs.modal', function (e) {
        userFormValidate.resetForm();
    });

    $("#user-ok-btn").on('click', function () {

        if( userFormValidate.form()) {
            var data = $("#user-form").serialize();
            var actionVal = $("#user-form input[name=action]").val();
            var actionUrl = "";
            if( 'create' == actionVal) {
                actionUrl="/user/createUser";
            } else if( 'update' == actionVal ) {
                actionUrl="/user/updateUser";
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
                    $('#users-dlg').modal('hide');
                    if( true == data.status ) {
                        Messenger().post({
                            message:  data.message,
                            type: 'success',
                            showCloseButton: true
                        }) ;
                        usersTable.ajax.reload(null,false);
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