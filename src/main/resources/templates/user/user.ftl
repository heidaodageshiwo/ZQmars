<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 用户管理 </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/plugins/select2/select2.min.css">
    <link rel="stylesheet" href="/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="/plugins/datatables/buttons.bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/datatables/select.dataTables.min.css">
    <link rel="stylesheet" href="/plugins/datatables/select.bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/daterangepicker/daterangepicker.css">
    <link rel="stylesheet" href="/plugins/iCheck/minimal/blue.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger-theme-air.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="/css/efence.css">

</head>
<body class="hold-transition skin-blue sidebar-mini">
<#-- Site wrapper -->
<div class="wrapper">
<#-- load header bar -->
<#include "../comm/headerBar.ftl">
<#-- load left side menus sidebar -->
<#include "../comm/sideMenu.ftl">

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                用户管理
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active"><a href="###"> 用户管理</a></li>
                <li class="active">用户管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <i class="fa fa-users"></i>
                            <h3 class="box-title">用户列表</h3>
                        </div>
                        <div class="box-body">
                            <table id="users-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th style="word-break:break-all; max-width:150px">登录名</th>
                                    <th style="word-break:break-all; max-width:150px">用户名</th>
                                    <th>性别</th>
                                    <th style="word-break:break-all; max-width:20px">邮箱</th>
                                    <th>手机</th>
                                    <th>角色</th>
                                    <th>创建时间</th>
                                    <th style="word-break:break-all; max-width:150px">备注</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

<#-- load footer bar -->
<#include "../comm/footer.ftl">

</div>
<#-- ./wrapper -->

<#-- 用户窗体 -->
<div class="modal fade" id="reset-pwd-dlg">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">修改密码</h4>
            </div>
            <div class="modal-body">
                <form id="reset-pwd-form" class="form-horizontal">
                    <div class="form-group">
                        <label for="newPwd" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="newPwd" name="newPassword" placeholder="登陆密码" maxlength="64">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="re-newPwd" class="col-sm-2 control-label">密码确认</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="re-newPwd" name="reNewPassword" placeholder="请再输入一次登陆密码" maxlength="64">
                        </div>
                    </div>
                    <input id="reset-pwd-id" type="hidden" name="id" value="" >
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="resetPwd-ok-btn" >确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>


<div class="modal fade" id="users-dlg">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">用户信息</h4>
            </div>
            <div class="modal-body">
                <form id="user-form" class="form-horizontal">
                    <div class="form-group">
                        <label for="login-name" class="col-sm-2 control-label">登陆名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="login-name" name="loginName" placeholder="登陆名" maxlength="40">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="role" class="col-sm-2 control-label">角色</label>
                        <div class="col-sm-10">
                            <select name="role" id="role" style="width: 100%">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="password" name="password" placeholder="登陆密码" maxlength="64">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="re-password" class="col-sm-2 control-label">密码确认</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="re-password" name="rePassword" placeholder="请再输入一次登陆密码" maxlength="64">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user-name" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="user-name" name="userName" placeholder="姓名" maxlength="30">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user-name" class="col-sm-2 control-label">性别</label>
                        <div class="col-sm-10">
                            <select name="sex" id="sex" style="width: 100%">
                                <option value="0" selected="selected">男</option>
                                <option value="1">女</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" id="email" name="email" placeholder="邮箱" maxlength="50">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mobile-phone" class="col-sm-2 control-label">手机</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="mobile-phone" name="mobilePhone" placeholder="手机" maxlength="15">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="remark" class="col-sm-2 control-label">备注</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="remark" name="remark" placeholder="备注" maxlength="200">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="locked" class="col-sm-2 control-label">锁定</label>
                        <div class="col-sm-10">
                            <input type="checkbox" name="locked" id="locked">
                        </div>
                    </div>
                    <input type="hidden" name="id" value="">
                    <input type="hidden" name="action" value="create">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="user-ok-btn" >确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<#-- 用户窗体 -->


<script src="/plugins/jQuery/jquery.js"></script>
<script src="/plugins/jQueryUI/jquery-ui.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script src="/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<script src="/plugins/jquery-idleTimeout/store.min.js"></script>
<script src="/plugins/jquery-idleTimeout/jquery-idleTimeout.min.js"></script>
<script src="/plugins/sockjs/sockjs.min.js"></script>
<script src="/js/notifyHandle.js"></script>
<script src="/plugins/fastclick/fastclick.js"></script>
<script src="/plugins/select2/select2.full.min.js"></script>
<script src="/plugins/select2/i18n/zh-CN.js"></script>
<script src="/plugins/daterangepicker/moment.min.js"></script>
<script src="/plugins/daterangepicker/daterangepicker.js"></script>
<script src="/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="/plugins/datatables/dataTables.buttons.min.js"></script>
<script src="/plugins/datatables/buttons.bootstrap.min.js"></script>
<script src="/plugins/datatables/dataTables.select.js"></script>
<script src="/plugins/iCheck/icheck.min.js"></script>
<script src="/plugins/messenger/messenger.js"></script>
<script src="/plugins/bootbox/bootbox.min.js"></script>
<script src="/plugins/jqueryValidation/jquery.validate.min.js"></script>
<script src="/plugins/jqueryValidation/localization/messages_zh.min.js"></script>
<script src="/plugins/jqueryValidation/additional-methods.min.js"></script>
<script src="/js/validatorMethod.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/users.js"></script>
</body>
</html>
