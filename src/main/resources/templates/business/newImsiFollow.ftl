<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | IMSI伴随分析 </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="/plugins/datatables/buttons.bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/daterangepicker/daterangepicker.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger-theme-air.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/css/skins/_all-skins.min.css">
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
                IMSI伴随分析
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 数据分析</span></li>
                <li class="active">IMSI伴随分析</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <h3 class="box-title">伴随分析任务列表</h3>
                        </div>
                        <div class="box-body">
                            <table id="imsifollow-task-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>分析名称</th>
                                    <th>目标IMSI</th>
                                    <th>时间范围</th>
                                    <th>创建时间</th>
                                    <th>进度</th>
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

<div class="modal fade" id="create-imsiFollow-dlg" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">IMSI伴随分析</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12">
                        <form id="imsiFollow-task-form" class="form-horizontal">
                            <div class="form-group">
                                <label for="task-name" class="col-sm-2 control-label">名称</label>
                                <div class="col-sm-10">
                                    <input type="text"  name="taskName" class="form-control" id="task-name" placeholder="请输入任务名称" maxlength="40">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="imsiFollow-range-time" class="col-sm-2 control-label">时间范围</label>
                                <div class="col-sm-10">
                                    <input type="text" name="rangeTime" class="form-control" id="imsiFollow-range-time" placeholder="请选择时间范围" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="site-ok" class="col-sm-2 control-label">检测站点</label>
                                <div class="col-sm-10" >
                                    <button  type="button" class="btn btn-flat btn-sm btn-primary" id="site-ok" >请选择检测站点</button>
                                    <input type="text" disabled="disabled"  class="form-control" id="site-name" name="name" placeholder="提示:默认情况下将您选择全部站点"
                                           maxlength="16" style="margin-left: 120px;width: 350px;margin-top: -30px;height: 30px;">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="site-name" class="col-sm-2 control-label">所选站点</label>
                                <div class="col-sm-10">
                                    <div id="siteName" style="overflow:auto;height: 40px;border:1px solid #CCC;"></div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="imsiFollow-timeWindow-top" class="col-sm-2 control-label">伴随时间窗上限(分钟)</label>
                                <div class="col-sm-10">
                                    <input type="text" name="topTime" class="form-control" id="imsiFollow-timeWindow-top" value ="5">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="imsiFollow-timeWindow-bottom" class="col-sm-2 control-label">伴随时间窗下限(分钟)</label>
                                <div class="col-sm-10">
                                    <input type="text" name="bottomTime" class="form-control" id="imsiFollow-timeWindow-bottom" value="10">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="imsiFollow-target-imsi" class="col-sm-2 control-label">目标IMSI</label>
                                <div class="col-sm-10">
                                    <input type="text"  name="targetIMSI" class="form-control" id="imsiFollow-target-imsi" placeholder="请输入嫌疑人IMSI值" >
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="create-task-btn">创建</button>
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

<div class="modal fade" id="up-imsiFollow-dlg" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content"width: 684px;>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">修改任务信息</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12">
                        <form id="imsiFollow-up-form" class="form-horizontal">
                            <div class="form-group">
                                <label for="task-name" class="col-sm-2 control-label">名称</label>
                                <div class="col-sm-10">
                                    <input type="text"  name="up-taskName" class="form-control" id="up-task-name" placeholder="请输入任务名称" maxlength="40">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="imsiFollow-range-time" class="col-sm-2 control-label">时间范围</label>
                                <div class="col-sm-10">
                                    <input type="text" name="up-rangeTime" class="form-control" id="up-imsiFollow-range-time" placeholder="请选择时间范围" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="site-ok" class="col-sm-2 control-label">检测站点</label>
                                <div class="col-sm-10" >
                                    <button  type="button" class="btn btn-flat btn-sm btn-primary" id="up-site-ok" >请选择检测站点</button>
                                    <input type="text" disabled="disabled"  class="form-control" id="up-site-name" name="name" placeholder="提示:默认情况下将您选择全部站点"
                                           maxlength="16" style="margin-left: 120px;width: 350px;margin-top: -30px;height: 30px;">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="up-site-name" class="col-sm-2 control-label">所选站点</label>
                                <div class="col-sm-10">
                                    <div id="up-siteName" style="overflow:auto;height: 40px;border:1px solid #CCC;"></div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="imsiFollow-timeWindow-top" style="margin-top: -10px;" class="col-sm-2 control-label">伴随时间窗上限(分钟)</label>
                                <div class="col-sm-10">
                                    <input type="text" name="up-topTime" class="form-control" id="up-imsiFollow-timeWindow-top" value ="5">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="imsiFollow-timeWindow-bottom" style="margin-top: -10px;" class="col-sm-2 control-label">伴随时间窗下限(分钟)</label>
                                <div class="col-sm-10">
                                    <input type="text" name="up-bottomTime" class="form-control" id="up-imsiFollow-timeWindow-bottom" value="10">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="imsiFollow-target-imsi" class="col-sm-2 control-label">目标IMSI</label>
                                <div class="col-sm-10">
                                    <input type="text"  name="up-targetIMSI" class="form-control" id="up-imsiFollow-target-imsi" placeholder="请输入嫌疑人IMSI值" >
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="up-task-btn">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

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
<div class="modal fade" id="xq-imsiFollow-dlg">
    <div class="modal-dialog modal-lg">
        <div class="modal-content" style="width:900px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">任务详情</h4>
            </div>
            <div class="modal-body" style="width: 900px;overflow-y: scroll;height: 588px;">
                <div class="row">
                    <div class="col-sm-12" style="margin-top: -42px;">
                        <form id="xq-dataCollide-up-form" class="form-horizontal">
                            <div class="form-group">
                                <div class="col-sm-10" style="width:100%">
                                    <table id="xq-imsiFollow-space-time-condition" class="table table-striped table-hover table-bordered" width="98%">
                                        <thead>
                                        <tr>
                                            <th style="width : 50%">imsi号码</th>
                                            <th style="width : 50%">时空条件个数</th>
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
<script src="/js/page/newImsifollow.js"></script>
<script src="/m16/js/dataTables.default.js"></script>
<script src="/plugins/zTree-v3/js/jquery.ztree.all.js"></script>
</body>
</html>
