<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 用户信息 </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/plugins/select2/select2.min.css">
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
                用户信息
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active"><a href="###"> 用户中心</a></li>
                <li class="active">用户信息</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <i class="fa fa-user"></i>
                            <h3 class="box-title">用户信息</h3>
                        </div>
                        <div class="box-body">
                            <div class="row">
                              <div class="col-lg-12">
                                  <div class="pull-left" style="margin-left: 33px; margin-right:20px; ">
                                      <img class="profile-user-img img-responsive img-circle " src="${user.avata}" alt="User profile picture">
                                  </div>
                                  <div class="profile-summary">
                                      <p><span>登陆账号：</span><span>${user.loginName}</span></p>
                                      <p><span>用户角色：</span><span>${user.role}</span></p>
                                      <p><span>开户时间：</span><span>${user.create_time}</span></p>
                                  </div>
                              </div>
                            </div>
                            <div class="row" style="margin-top: 20px;">
                                <div class="col-lg-12">
                                    <ul class="profile-set-list">
                                        <li>
                                            <div class="set-list-left pull-left"><b>姓名</b></div>
                                            <div class="set-list-right pull-right">
                                                <a href="###" data-change="username" data-toggle="modal" data-target="#change-dlg" >修改</a>
                                            </div>
                                            <div class="set-list-mid ">
                                                <span class="profile-list-info">${user.userName}</span>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="set-list-left pull-left"><b>性别</b></div>
                                            <div class="set-list-right pull-right">
                                                <a href="###" data-change="sex" data-toggle="modal" data-target="#change-dlg" >修改</a>
                                            </div>
                                            <div class="set-list-mid ">
                                                <span class="profile-list-info">${user.sex}</span>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="set-list-left pull-left"><b>手机</b></div>
                                            <div class="set-list-right pull-right">
                                                <a href="###" data-change="mPhone" data-toggle="modal" data-target="#change-dlg">修改</a>
                                            </div>
                                            <div class="set-list-mid ">
                                                <span class="profile-list-info">${user.mobilePhone}</span>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="set-list-left pull-left"><b>电子邮箱</b></div>
                                            <div class="set-list-right pull-right">
                                                <a href="###" data-change="email" data-toggle="modal" data-target="#change-dlg" >修改</a>
                                            </div>
                                            <div class="set-list-mid ">
                                                <span class="profile-list-info">${user.email}</span>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="set-list-left pull-left"><b>密码</b></div>
                                            <div class="set-list-right pull-right">
                                                <a href="###" data-change="password" data-toggle="modal" data-target="#change-dlg" >修改</a>
                                            </div>
                                            <div class="set-list-mid ">
                                                <span></span>
                                                <!--<span> 修改安全性高的密码可以使帐号更安全。建议您定期更换密码，设置一个包含字母，符号或数字中至少两项且长度超过6位的密码。</span>-->
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
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

<#--弹出窗体-->
<div class="modal fade" id="change-dlg">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block"></h4>
            </div>
            <div class="modal-body">
                <form id="change-form" class="form-horizontal">
                    <div class="form-group">
                        <label for="username" class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="username" name="username"  value="${user.userName}"  placeholder="姓名" maxlength="64">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user-name" class="col-sm-2 control-label">性别</label>
                        <div class="col-sm-10">
                            <select name="sex" id="sex" style="width: 100%">
                                <#if user.sexVal==0>
                                    <option value="0" selected="selected" >男</option>
                                    <option value="1">女</option>
                                <#else>
                                    <option value="0" >男</option>
                                    <option value="1" selected="selected" >女</option>
                                </#if>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" id="email" name="email" value="${user.email}" placeholder="邮箱" maxlength="200">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mobile-phone" class="col-sm-2 control-label">手机</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="mobile-phone" name="mobilePhone"  value="${user.mobilePhone}" placeholder="手机" maxlength="15">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="newPassword" class="col-sm-2 control-label">新密码</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="new-password" name="newPassword" placeholder="新密码" maxlength="64">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="re-newPassword" class="col-sm-2 control-label">新密码确认</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="re-newPassword" name="reNewPassword" placeholder="请再输入一次新密码" maxlength="64">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="chg-ok-btn" >确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>



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
<script src="/plugins/iCheck/icheck.min.js"></script>
<script src="/plugins/messenger/messenger.js"></script>
<script src="/plugins/bootbox/bootbox.min.js"></script>
<script src="/plugins/jqueryValidation/jquery.validate.min.js"></script>
<script src="/plugins/jqueryValidation/localization/messages_zh.min.js"></script>
<script src="/plugins/jqueryValidation/additional-methods.min.js"></script>
<script src="/js/validatorMethod.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/profile.js"></script>
</body>
</html>
