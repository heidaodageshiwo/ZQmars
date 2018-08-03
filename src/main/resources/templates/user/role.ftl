<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 角色管理 </title>
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
                角色管理
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 用户管理</span></li>
                <li class="active">角色管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <i class="fa fa-users"></i>
                            <h3 class="box-title">角色列表</h3>
                        </div>
                        <div class="box-body">
                            <table id="roles-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>角色名</th>
                                    <th>系统默认角色</th>
                                    <th>创建时间</th>
                                    <th>角色中文名</th>
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

<div class="modal fade" id="role-dlg">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">角色信息</h4>
            </div>
            <div class="modal-body">
                <form id="role-form" class="form-horizontal">
                    <div class="form-group">
                        <label for="role-name" class="col-sm-2 control-label">角色名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="role-name" name="roleName" placeholder="角色名" maxlength="40">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="remark" class="col-sm-2 control-label">角色中文名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="remark" name="remark" placeholder="角色中文名" maxlength="200">
                        </div>
                    </div>
                    <#if modules?? && modules?size gt 0>
                        <#list modules as module>
                            <div class="form-group">
                                <label for="locked" class="col-sm-2 control-label">${module.diaplayName}</label>
                                <div class="col-sm-10">
                                    <#if module.resources?? && module.resources?size gt 0>
                                         <#list module.resources as resource>
                                             <div class="checkbox">
                                                 <label>
                                                     <input type="checkbox" id="resource-${resource.value}" name="resources"  value="${resource.value}">
                                                     ${resource.displayName}
                                                 </label>
                                             </div>
                                         </#list>
                                    </#if>
                                </div>
                            </div>
                        </#list>
                    </#if>
                    <input type="hidden" name="id" value="">
                    <input type="hidden" name="action" value="create">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="role-ok-btn" >确定</button>
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
<script src="/js/page/role.js"></script>
</body>
</html>
