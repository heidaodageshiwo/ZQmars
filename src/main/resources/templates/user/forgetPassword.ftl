<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${systemInfo.projectName }  | 忘记密码</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/plugins/iCheck/square/blue.css">
    <link rel="stylesheet" href="/css/login.css">
</head>
<body class="hold-transition login-page">


<div class="login-box">
    <div class="login-logo">
        <a href="/">${systemInfo.projectName }</a>
    </div>
    <div class="login-box-body">
        <p class="login-box-msg">找回密码</p>
        <p id="error-text" class="text-red hidden"></p>
        <form  id="find-pwd-form" action="" method="post" style="margin-bottom: 10px;">
            <div class="form-group has-feedback">
                <input type="text" name="username" class="form-control" placeholder="登陆用户名" required>
                <span class="glyphicon glyphicon-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="email" name="email" class="form-control" placeholder="开户邮箱" required>
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">提交</button>
                </div>
            </div>
        </form>

        <a class="login-font-size" href="/user/login">返回登录页</a><br>

    </div>
</div>


<script src="/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script src="/plugins/jqueryValidation/jquery.validate.min.js"></script>
<script src="/plugins/jqueryValidation/localization/messages_zh.min.js"></script>
<script src="/plugins/jqueryValidation/additional-methods.min.js"></script>
<script src="/plugins/bootbox/bootbox.min.js"></script>
<script>
    $(function () {

        var findPwdFormValidate = $( "#find-pwd-form" ).validate( {
            rules: {
                username: {
                    required: true,
                    normalizer: function( value ) {
                        return $.trim(value);
                    }
                },
                email:{
                    required: true,
                    normalizer: function( value ) {
                        return $.trim(value);
                    }
                }
            },
            messages: {
                username: "请输入用户名",
                email:{
                    required: "请输入邮箱"
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

        $("#find-pwd-form").bind("submit", function () {

            if( findPwdFormValidate.form()) {
                var data = $(this).serialize();
                $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: '/user/findPwdPost',
                    data: data,
                    timeout: 5000,
                    success: function ( data ) {
                        if( true == data.status ) {
                            bootbox.alert({
                                message: data.message,
                                callback: function () {
                                    window.location.href = "/user/login";
                                }
                            })
                        } else {
                            $("#error-text").text(data.message);
                            $("#error-text").removeClass("hidden");
                        }
                    }
                });
            }

            return false;

        });

    });
</script>

</body>
</html>
