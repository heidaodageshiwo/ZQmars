<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${systemInfo.projectName }  | 登陆</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/plugins/iCheck/minimal/blue.css">
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>

<div class="parallax" style="transform: translate3d(0px, 0px, 0px);">
    <div class="parallaxImg parallaxImg-header"></div>
</div>

<div class="login-page-efence">
    <div class="login-box">
        <div class="login-logo">
            <a href="/" style="color: #ffffff">${systemInfo.projectName }</a>
        </div>
        <#-- /.login-logo -->
        <div class="login-box-body">
            <p class="login-box-msg">请登录</p>

            <p id="error-text" class="text-red hidden"></p>
            <form  id="login-form" action="/user/loginPost" method="post">
                <div class="form-group">

                </div>
                <div class="form-group has-feedback">
                    <input type="text" name="username" class="form-control" placeholder="用户名" required>
                    <span class="glyphicon glyphicon-user form-control-feedback"></span>
                </div>
                <div class="form-group has-feedback">
                    <input type="password" name="password" class="form-control" placeholder="密码" required>
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
                <div class="row">
                    <div class="col-xs-8">
                        <div class="checkbox icheck">
                            <label class="login-font-size">
                                <input type="checkbox" class="minimal" name="remember"> 记住
                            </label>
                        </div>
                    </div>
                    <#-- /.col -->
                    <div class="col-xs-4">
                        <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
                    </div>
                    <#-- /.col -->
                </div>
                <div class="row">
                    <div class="col-xs-8">
                        <a class="login-font-size" href="/licensure">许可信息</a>
                    </div>
                    <#-- /.col -->
                    <div class="col-xs-4">
                        <a class="login-font-size" href="/user/forgetPassword">忘记密码</a>
                    </div>
                    <#-- /.col -->
                </div>
            </form>

        </div>
        <#-- /.login-box-body -->
    </div>
    <#-- /.login-box -->

    <div class="copy-label">
        <span>Copyright © ${systemInfo.companyYear } ${systemInfo.companyName }</span>
    </div>
</div>

<script src="/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script src="/plugins/iCheck/icheck.min.js"></script>
<script src="/plugins/jqueryValidation/jquery.validate.min.js"></script>
<script src="/plugins/jqueryValidation/localization/messages_zh.min.js"></script>
<script src="/plugins/jqueryValidation/additional-methods.min.js"></script>
<script src="/js/page/login.js"></script>
</body>
</html>
