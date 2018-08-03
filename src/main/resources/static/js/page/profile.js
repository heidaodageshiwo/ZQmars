/**
 * Created by jiangqi.yang on 2016/11/5.
 */

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    }

    $("#sex").select2();

    var changeUserFormValidate = $( "#change-form" ).validate( {
        rules: {
            newPassword:{
                required: true,
                isSpace: true,
                normalizer: function( value ) {
                    return $.trim(value);
                }
            },
            reNewPassword:{
                required: true,
                equalTo: "#new-password",
                normalizer: function( value ) {
                    return $.trim(value);
                }
            },
            username: {
                required: true,
                stringCheck:true,
                normalizer: function( value ) {
                    return $.trim(value);
                }
            },
            sex: {
                required: true
            },
            email: {
                required: true,
                email: true,
                normalizer: function( value ) {
                    return $.trim(value);
                }
            },
            mobilePhone: {
                required: true,
                isMobile: true,
                normalizer: function( value ) {
                    return $.trim(value);
                }
            }
        },
        messages: {
            newPassword:{
                required: "请输入的密码",
                isSpace: "密码不能为空"
            },
            reNewPassword:{
                required: "请再次输入的密码",
                equalTo: "两次密码不相等"
            },
            username: {
                required: "请输入用户姓名",
                stringCheck:"只能包括中文字、英文字母、数字和下划线"
            },
            sex: {
                required: "选择性别"
            },
            email: {
                required: "请输入电子邮件地址",
                email: "请输入有效的电子邮件地址"
            },
            mobilePhone: {
                required: "请输入手机号码",
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

    $('#change-dlg').on('show.bs.modal', function (e) {

        var changeModel = $(e.relatedTarget).data("change");

        $("#username").parent().parent(".form-group").hide();
        $("#sex").parent().parent(".form-group").hide();
        $("#email").parent().parent(".form-group").hide();
        $("#mobile-phone").parent().parent(".form-group").hide();
        $("#new-password").parent().parent(".form-group").hide();
        $("#re-newPassword").parent().parent(".form-group").hide();

        $("#username").prop("disabled", true);
        $("#sex").prop("disabled", true);
        $("#email").prop("disabled", true);
        $("#mobile-phone").prop("disabled", true);
        $("#new-password").prop("disabled", true);
        $("#re-newPassword").prop("disabled", true);

        switch( changeModel ) {
            case "username":
                $("#change-dlg h4.modal-title").text("新姓名");
                $("#username").parent().parent(".form-group").show();
                $("#username").prop("disabled", false);
                break;
            case "sex":
                $("#change-dlg h4.modal-title").text("新性别");
                $("#sex").parent().parent(".form-group").show();
                $("#sex").prop("disabled", false);
                break;
            case "mPhone":
                $("#change-dlg h4.modal-title").text("新电话");
                $("#mobile-phone").parent().parent(".form-group").show();
                $("#mobile-phone").prop("disabled", false);
                break;
            case "email":
                $("#change-dlg h4.modal-title").text("新邮箱");
                $("#email").parent().parent(".form-group").show();
                $("#email").prop("disabled", false);
                break;
            case "password":
                $("#change-dlg h4.modal-title").text("新密码");
                $("#new-password").parent().parent(".form-group").show();
                $("#re-newPassword").parent().parent(".form-group").show();
                $("#new-password").prop("disabled", false);
                $("#re-newPassword").prop("disabled", false);
                break;
            default:
                return false;
        }

    });

    $('#change-dlg').on('shown.bs.modal', function (e) {
        changeUserFormValidate.resetForm();
    });
    
    $("#chg-ok-btn").click(function () {

        if( changeUserFormValidate.form() ) {
            var data = $("#change-form").serialize();
            console.log(data);
            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: "/user/updateSelfInfo",
                data: data,
                timeout: 5000,
                success: function ( data ) {
                    $('#change-dlg').modal('hide');
                    if( true == data.status ) {
                        document.location.reload();
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