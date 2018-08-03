<!DOCTYPE html>
<html xmlns:background="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 预警 </title>
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
    <link rel="stylesheet" href="/plugins/messenger/messenger.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger-theme-air.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="/css/efence.css">
    <link rel="stylesheet" href="/plugins/daterangepicker/daterangepicker.css">
    <link rel="stylesheet" href="/plugins/zTree-v3/css/metroStyle/metroStyle.css">

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
                归属地预警管理
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active"><a href="###"> 预警</a></li>
                <li class="active">归属地预警管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-lg-12">
                    <div class="box box-primary ">
                        <div class="box-header">
                            <i class="fa fa-th-list"></i>
                            <h3 class="box-title">归属地预警列表</h3>
                        </div>
                        <div class="box-body">
                            <table id="homeOwnership-table" class="table table-middle table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>分组名称</th>
                                    <th>创建人</th>
                                    <th>站点信息</th>
                                    <th>区域信息</th>
                                    <th>消息接收人</th>
                                    <th>创建时间</th>
                                    <th>开始时间</th>
                                    <th>到期时间</th>
                                    <th>备注</th>
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

<#---创建名单窗体-->
<div class="modal fade" id="homeOwnership-dlg" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 700px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">添加分组信息</h4>
            </div>
            <div class="modal-body">
                <div  class="form-horizontal">
                    <form id="homeOwnership-form">
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label">分组名称</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name" name="name" placeholder="请填写分组名称" maxlength="40">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="site-ok" class="col-sm-2 control-label">检测站点</label>
                            <div class="col-sm-10" >
                                <button  type="button" class="btn btn-flat btn-sm btn-primary" id="site-ok" >请选择检测站点</button>
                                <input type="text" disabled="disabled"  class="form-control" id="site-name" name="name" placeholder="请点击左侧按钮选择检测站点"
                                       maxlength="16" style="margin-left: 120px;width: 433px;margin-top: -30px;height: 30px;">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="site-name" class="col-sm-2 control-label">所选站点</label>
                            <div class="col-sm-10">
                                <div id="siteName" style="overflow:auto;height: 40px;border:1px solid #CCC;"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="ownership" class="col-sm-2 control-label">预警归属地</label>
                            <div class="col-sm-10">
                                <select id="ownership" name="ownership"  multiple='multiple' class="form-control select2" style="width: 100%;" data-placeholder="请选择归属地">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">有效期时间</label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <div class="input-group-addon">
                                        <i class="fa fa-clock-o"></i>
                                    </div>
                                    <input name="alarmRangeTime" type="text" class="form-control pull-right" id="alarm-range-time" PLACEHOLDER="请选择有效时间">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="remark" class="col-sm-2 control-label">备注</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="remark" name="remark" placeholder="备注" maxlength="200">
                            </div>
                        </div>
                    </form>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">通知人</label>
                        <div class="col-sm-10">
                            <table id="homeOwnership-receiver-table" class="table table-striped table-hover table-bordered">
                                <thead class="bg-gray">
                                <tr><th>姓名</th><th>电话</th><th>邮件</th><th>操作</th></tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                            <form id="homeOwnership-receiver-form">
                                <table id="homeOwnership-add-receiver-table"  class="table" border="0">
                                    <tbody>
                                    <tr>
                                        <td colspan="4" >
                                            <a href="###" id="homeOwnership-add-receiver-btn" ><b>+</b>添加</a>
                                        </td>
                                    </tr>
                                    <tr class="hidden">
                                        <td>
                                            <input type="text" name="name" class="form-control"  placeholder="请输入姓名" ></input>
                                        </td>
                                        <td>
                                            <input type="text" name="phone" class="form-control" placeholder="请输入手机号码"  ></input>
                                        </td>
                                        <td>
                                            <input type="email" name="email" class="form-control" placeholder="请输入邮箱"  ></input>
                                        </td>
                                        <td>
                                            <button  type="button" class="btn btn-flat btn-sm btn-primary" id="homeOwnership-add-receiver-ok" >确定</button>
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-flat btn-sm" id="homeOwnership-add-receiver-cancel" >取消</button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="homeOwnership-ok-btn" >确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<#-- 选择站点窗体 -->
<div class="modal fade" id="site-dlg">
    <div class="modal-dialog modal-lg">
        <div class="modal-content" style="width: 300px;margin-left: 280px;height: 532px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title center-block">请选择检测站点</h4>
            </div>
            <div class="modal-body" style="width: 300px;height: 417px;overflow-y: scroll;">
                <div id="treeDemo" class="ztree"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary btn-flat"  id="site_submit" >确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<#-- 选择站点窗体 -->

    <#---修改名单窗体-->
        <div class="modal fade" id="update-dlg" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog">
                <div class="modal-content" style="width: 700px;">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title center-block">修改分组信息</h4>
                    </div>
                    <div class="modal-body">
                        <div  class="form-horizontal">
                            <form id="update-form">
                                <div class="form-group">
                                    <label for="name" class="col-sm-2 control-label">分组名称</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="upname" name="name" placeholder="请填写分组名称" maxlength="40">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="site-ok" class="col-sm-2 control-label">检测站点</label>
                                    <div class="col-sm-10" >
                                        <button  type="button" class="btn btn-flat btn-sm btn-primary" id="up-site-ok" >请选择检测站点</button>
                                        <input type="text" disabled="disabled"  class="form-control" id="up-site-name" name="name" placeholder="请点击左侧按钮选择检测站点"
                                               maxlength="16" style="margin-left: 120px;width: 433px;margin-top: -30px;height: 30px;">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="up-site-name" class="col-sm-2 control-label">所选站点</label>
                                    <div class="col-sm-10">
                                        <div id="up-siteName" style="overflow:auto;height: 40px;border:1px solid #CCC;"></div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="ownership" class="col-sm-2 control-label">预警归属地</label>
                                    <div class="col-sm-10">
                                        <select id="upownership" name="ownership"  multiple='multiple' class="form-control select2" style="width: 100%;" data-placeholder="请选择归属地">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">有效期时间</label>
                                    <div class="col-sm-10">
                                        <div class="input-group">
                                            <div class="input-group-addon">
                                                <i class="fa fa-clock-o"></i>
                                            </div>
                                            <input name="alarmRangeTime" type="text" class="form-control pull-right" id="up-alarm-range-time" PLACEHOLDER="请选择有效时间">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="remark" class="col-sm-2 control-label">备注</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="upremark" name="remark" placeholder="备注" maxlength="200">
                                    </div>
                                </div>
                            </form>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">通知人</label>
                                <div class="col-sm-10">
                                    <table id="update-receiver-table" class="table table-striped table-hover table-bordered">
                                        <thead class="bg-gray">
                                        <tr><th>姓名</th><th>电话</th><th>邮件</th><th>操作</th></tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                    <form id="update-receiver-form">
                                        <table id="update-add-receiver-table"  class="table" border="0">
                                            <tbody>
                                            <tr>
                                                <td colspan="4" >
                                                    <a href="###" id="update-add-receiver-btn" ><b>+</b>添加</a>
                                                </td>
                                            </tr>
                                            <tr class="hidden">
                                                <td>
                                                    <input type="text" name="name" class="form-control"  placeholder="请输入姓名" ></input>
                                                </td>
                                                <td>
                                                    <input type="text" name="phone" class="form-control" placeholder="请输入手机号码"  ></input>
                                                </td>
                                                <td>
                                                    <input type="email" name="email" class="form-control" placeholder="请输入邮箱"  ></input>
                                                </td>
                                                <td>
                                                    <button  type="button" class="btn btn-flat btn-sm btn-primary" id="update-add-receiver-ok" >确定</button>
                                                </td>
                                                <td>
                                                    <button type="button" class="btn btn-flat btn-sm" id="update-add-receiver-cancel" >取消</button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary btn-flat" id="update-ok-btn" >确定</button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>

<#-- 选择站点窗体 -->
<div class="modal fade" id="up-site-dlg">
    <div class="modal-dialog modal-lg">
        <div class="modal-content" style="width: 300px;margin-left: 280px;height: 570px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title center-block">请选择检测站点</h4>
            </div>
            <div class="modal-body" style="width: 300px;height: 455px;overflow-y: scroll;">
                <div id="up-treeDemo" class="ztree"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary btn-flat"  id="up-site_submit" >确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<#-- 选择站点窗体 -->



<div class="modal fade" id="receiver-dlg">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">消息接受人</h4>
            </div>
            <div class="modal-body">
                <input type="hidden" name="id" value="">
                <div class="row">
                    <div class="col-sm-12">
                        <table id="receiver-table" class="table table-striped table-hover table-bordered">
                            <thead class="bg-gray">
                            <tr><th>姓名</th><th>电话</th><th>邮件</th><th>操作</th></tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                        <form id="receiver-form">
                            <table id="add-receiver-table"  class="table" border="0">
                                <tbody>
                                <tr>
                                    <td colspan="4" >
                                        <a href="###" id="add-receiver-btn" ><b>+</b>添加</a>
                                    </td>
                                </tr>
                                <tr class="hidden">
                                    <td>
                                        <input type="text" name="name" class="form-control"  placeholder="请输入姓名" ></input>
                                    </td>
                                    <td>
                                        <input type="text" name="phone" class="form-control" placeholder="请输入手机号码"  ></input>
                                    </td>
                                    <td>
                                        <input type="email" name="email" class="form-control" placeholder="请输入邮箱"  ></input>
                                    </td>
                                    <td>
                                        <button  type="button" class="btn btn-flat btn-sm btn-primary" id="add-receiver-ok" >确定</button>
                                        <button type="button" class="btn btn-flat btn-sm" id="add-receiver-cancel" >取消</button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat"  id="change-receiver-confirm" >确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<script src="/plugins/jQuery/jquery-2.2.3.min.js"></script>
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
<script src="/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="/plugins/datatables/dataTables.buttons.min.js"></script>
<script src="/plugins/datatables/buttons.bootstrap.min.js"></script>
<script src="/plugins/datatables/dataTables.select.js"></script>
<script src="/plugins/messenger/messenger.js"></script>
<script src="/plugins/bootbox/bootbox.min.js"></script>
<script src="/plugins/jqueryValidation/jquery.validate.min.js"></script>
<script src="/plugins/jqueryValidation/localization/messages_zh.min.js"></script>
<script src="/plugins/jqueryValidation/additional-methods.min.js"></script>
<script src="/js/validatorMethod.js"></script>
<script src="/js/efence.js"></script>
<script src="/plugins/daterangepicker/moment.min.js"></script>
<script src="/plugins/daterangepicker/daterangepicker.js"></script>
<script src="/js/page/homeOwnership.js"></script>
<script src="/m16/js/dataTables.default.js"></script>
<script src="/plugins/zTree-v3/js/jquery.ztree.all.js"></script>

</body>
</html>
