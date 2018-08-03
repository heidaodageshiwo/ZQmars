<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 数据碰撞分析</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/plugins/select2/select2.min.css">
    <link rel="stylesheet" href="/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="/plugins/datatables/buttons.bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/daterangepicker/daterangepicker.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger-theme-air.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="/css/efence.css">
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
                数据碰撞分析
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 数据分析</span></li>
                <li class="active">数据碰撞分析</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <h3 class="box-title">任务列表</h3>
                        </div>
                        <div class="box-body">
                            <table id="dataCollide-task-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>分析名称</th>
                                    <th>时空条件</th>
                                    <th>创建时间</th>
                                    <th>备注</th>
                                    <th id="taskProcess">进度</th>
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

<#-- 任务创建窗体 -->
<div class="modal fade" id="create-dataCollide-dlg" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">创建碰撞分析任务</h4>
            </div>
            <div class="modal-body">
                 <div class="row">
                     <div class="col-sm-12">
                         <div class="form-group">
                             <label for="dataCollide-name" class="col-sm-2 control-label">名称</label>
                             <div class="col-sm-10">
                                 <input type="text" class="form-control" id="dataCollide-name" placeholder="任务名称" maxlength="40">
                             </div>
                         </div>
                         <div class="form-group">
                             <label for="dataCollide-space-time-condition" class="col-sm-2 control-label">时空条件</label>
                             <div class="col-sm-10">
                                 <table id="dataCollide-space-time-condition" class="table table-hover">
                                     <thead>
                                        <tr><th style="width: 45%">时间范围</th><th  style="width: 40%">站点名称</th><th  style="width: 20%">操作</th></tr>
                                     </thead>
                                     <tbody>
                                     </tbody>
                                 </table>
                                 <table id="add-space-time-condition-table"  class="table" border="0">
                                     <tbody>
                                         <tr>
                                             <td>
                                                 <a href="###" id="add-space-time-btn" ><b>+</b>添加条件</a>
                                             </td>
                                         </tr>
                                         <tr class="hidden">
                                             <td style="width: 44%">
                                                 <input type="text" class="form-control" id="dataCollide-range-time" placeholder="时间范围" >
                                             </td>
                                             <td style="width: 36%">
                                                 <input type="text" id="site-text" class="form-control pull-right" style="background-color: #ffff;" readonly="readonly" placeholder="选择站点"></input>
                                                 <!--<select name="dataCollide-sites" class="form-control select2"  data-placeholder="站点名称" id="dataCollide-sites" style="width: 100%;">
                                                 </select>-->
                                             </td>
                                             <td style="width: 20%">
                                                 <button class="btn btn-flat btn-sm btn-primary" id="add-space-time-ok" >确定</button>
                                                 <button class="btn btn-flat btn-sm" id="add-space-time-cancel" >取消</button>
                                             </td>
                                         </tr>
                                     </tbody>
                                 </table>
                             </div>
                         </div>
                         <div class="form-group">
                             <label for="dataCollide-name" class="col-sm-2 control-label">备注</label>
                             <div class="col-sm-10">
                                 <input type="text" class="form-control" id="remark" placeholder="备注" maxlength="40">
                             </div>
                         </div>
                     </div>
                 </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="create-task-confirm">创建</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<#-- 任务创建窗体 -->

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

<#-- 任务查看窗体 -->
<div class="modal fade" id="up-dataCollide-dlg" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">查看碰撞分析任务</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label for="dataCollide-name" class="col-sm-2 control-label">名称</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="up-dataCollide-name" placeholder="任务名称" maxlength="40">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="up-dataCollide-space-time-condition" class="col-sm-2 control-label">时空条件</label>
                            <div class="col-sm-10">
                                <table id="up-dataCollide-space-time-condition" class="table table-hover">
                                    <thead>
                                    <tr><th style="width: 45%">时间范围</th><th  style="width: 40%">站点名称</th><th  style="width: 20%">操作</th></tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                                <table id="up-space-time-condition-table"  class="table" border="0">
                                    <tbody>
                                    <tr>
                                        <td>
                                            <a href="###" id="up-space-time-btn" ><b>+</b>添加条件</a>
                                        </td>
                                    </tr>
                                    <tr class="hidden">
                                        <td style="width: 44%">
                                            <input type="text" class="form-control" id="up-dataCollide-range-time" placeholder="时间范围" >
                                        </td>
                                        <td style="width: 36%">
                                            <input type="text" id="up-site-text" class="form-control pull-right" style="background-color: #ffff;" readonly="readonly" placeholder="选择站点"></input>
                                            <!--<select name="up-dataCollide-sites" class="form-control select2"  data-placeholder="站点名称" id="up-dataCollide-sites" style="width: 100%;">
                                            </select>-->
                                        </td>
                                        <td style="width: 20%">
                                            <button class="btn btn-flat btn-sm btn-primary" id="up-add-space-time-ok" >确定</button>
                                            <button class="btn btn-flat btn-sm" id="up-add-space-time-cancel" >取消</button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="dataCollide-name" class="col-sm-2 control-label">名称</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="up-remark" placeholder="备注" maxlength="40">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="up-task-confirm">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<#-- 任务查看窗体 -->

<#-- 修改站点窗体 -->
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
<#-- 修改站点窗体 -->


<#-- 任务详情窗体 -->
<div class="modal fade" id="xq-dataCollide-dlg">
    <div class="modal-dialog modal-lg">
        <div class="modal-content" style="width:900px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">任务详情</h4>
            </div>
            <div class="modal-body" style="width: 900px;/*overflow-y: scroll;height: 588px;*/">
                <div class="row">
                    <div class="col-sm-12" style="margin-top: -42px;">
                        <form id="xq-dataCollide-up-form" class="form-horizontal">
                            <div class="form-group">
                                <div class="col-sm-10" style="width:100%">
                                    <table id="xq-dataCollide-space-time-condition" class="table table-striped table-hover table-bordered" width="98%">
                                        <thead>
                                            <tr>
                                                <th style="width : 20%">imsi号码</th>
                                                <th style="width : 20%">符合时空条件个数</th>
                                                <th style="width : 20%">imsi号码总数</th>
                                                <th style="width : 20%">上号站点总数</th>
                                                <th style="width : 20%">操作</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-flat" data-dismiss="modal">关闭</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<#-- 任务详情窗体 -->

<#-- IMSI号码对应站点窗体 -->
<div class="modal fade" id="showSites-dlg">
    <div class="modal-dialog modal-lg">
        <div class="modal-content" style="width:900px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title center-block">任务详情</h4>
            </div>
            <div class="modal-body">
                <table id="showSites-condition" style="width:98%" class="table table-hover">
                    <thead>
                        <tr>
                            <th style="width : 40%">站点名称</th>
                            <th style="width : 40%">上号时间</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                 </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-flat" data-dismiss="modal" id="button_close">关闭</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<#-- IMSI号码对应站点窗体 -->

<#-- 下载提示框窗体 -->
<div class="modal fade" id="showWait" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content" style="margin-top: 150px;">
            <div class="modal-header" style="height:40px">
                <!--<div id="divId" style="font-size: large;font-weight: inherit;">提示:正在为您导出Excel文件，我们最多为您导出300000条数据 </div>-->
            </div>
            <div class="modal-body" >
                <div style="font-size: large;font-weight: inherit;text-align: center;">正在为您下载，请稍后...</div>
                <div class="progress">
                    <div id="bar" class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                        <span id="percent" class="sr-only">100% Complete</span>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
            </div>
        </div>
        &lt;!&ndash; /.modal-content &ndash;&gt;
    </div>
    &lt;!&ndash; /.modal-dialog &ndash;&gt;
</div>
<#-- 下载提示框窗体 -->

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
<script src="/plugins/daterangepicker/moment.min.js"></script>
<script src="/plugins/daterangepicker/daterangepicker.js"></script>
<script src="/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="/plugins/datatables/dataTables.buttons.min.js"></script>
<script src="/plugins/datatables/buttons.bootstrap.min.js"></script>
<script src="/plugins/messenger/messenger.js"></script>
<script src="/plugins/bootbox/bootbox.min.js"></script>
<script src="/plugins/jqueryValidation/jquery.validate.min.js"></script>
<script src="/plugins/jqueryValidation/localization/messages_zh.min.js"></script>
<script src="/plugins/jqueryValidation/additional-methods.min.js"></script>
<script src="/js/validatorMethod.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/newDataCollide.js"></script>
<script src="/m16/js/dataTables.default.js"></script>
<script src="/plugins/zTree-v3/js/jquery.ztree.all.js"></script>
</body>
</html>
