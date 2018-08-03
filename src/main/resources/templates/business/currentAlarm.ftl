<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 预警 </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="/plugins/datatables/buttons.bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/datatables/select.dataTables.min.css">
    <link rel="stylesheet" href="/plugins/datatables/select.bootstrap.min.css">
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
                当前预警
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 预警</span></li>
                <li class="active">当前预警</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary" style="margin-bottom: 10px;">
                        <div class="box-header">
                            <h3 class="box-title">查询参数</h3>
                        </div>
                    <#-- /.box-header -->
                        <div class="box-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <form id="searchForm">
                                        <div class="row">
                                            <div class="col-sm-4">
                                                <div class="form-group" >
                                                    <label>预警名称:</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa  fa-cubes"></i>
                                                        </div>
                                                        <input name="alarmTargetName" type="text" maxlength="16" class="form-control pull-right" id="alarmTargetName">
                                                    </div>
                                                <#-- /.input group -->
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <div class="form-group" >
                                                    <label>类型:</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa  fa-cube"></i>
                                                        </div>
                                                        <select id="targetType" name="targetType" class="form-control select2" style="width: 100%;">
                                                            <option value="">不限</option>
                                                            <option value="1">黑名单预警</option>
                                                            <option value="2">归属地预警</option>
                                                        </select>
                                                    </div>
                                                <#-- /.input group -->
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <div class="form-group" >
                                                    <label>IMSI:</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-mobile-phone"></i>
                                                        </div>
                                                        <input name="alarmIMSI" type="text" maxlength="16" class="form-control pull-right" id="alarmIMSI">
                                                    </div>
                                                <#-- /.input group -->
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="pull-right">
                                                    <button type="button" class="btn btn-primary" id="query" >查询</button>
                                                    <button type="button" class="btn btn-primary" id="queryAuto" >启用自动查询</button>
                                                    <button type="button" class="btn btn-warning" id="reset" >重置</button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
            </div>
            <!-- 列表区域 -->
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <i class="fa  fa-th-list"></i>
                            <h4 class="box-title">当前预警列表</h4>
                        </div>
                        <div class="box-body">
                            <table id="targets-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>分组名称</th>
                                    <th>预警名称</th>
                                    <th>类型</th>
                                    <th>IMSI</th>
                                    <th>归属地</th>
                                    <th>运营商</th>
                                    <th>站点名称</th>
                                    <th>设备名称</th>
                                    <th>告警时间</th>
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

<script src="/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/plugins/jQueryUI/jquery-ui.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script src="/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<script src="/plugins/jquery-idleTimeout/store.min.js"></script>
<script src="/plugins/jquery-idleTimeout/jquery-idleTimeout.min.js"></script>
<script src="/plugins/sockjs/sockjs.min.js"></script>
<script src="/js/notifyHandle.js"></script>
<script src="/plugins/fastclick/fastclick.js"></script>
<script src="/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="/plugins/datatables/dataTables.buttons.min.js"></script>
<script src="/plugins/datatables/buttons.bootstrap.min.js"></script>
<script src="/plugins/datatables/dataTables.select.js"></script>
<script src="/plugins/messenger/messenger.js"></script>
<script src="/plugins/bootbox/bootbox.min.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/currentAlarm.js"></script>
<script src="/m16/js/dataTables.default.js"></script>

</body>
</html>
