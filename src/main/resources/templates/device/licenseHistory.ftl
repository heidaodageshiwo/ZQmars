<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 设备授权历史 </title>
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
                设备授权历史
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 设备管理</span></li>
                <li class="active">授权历史</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-lg-12">
                    <div class="box box-default">
                        <div class="box-header">
                            <h3 class="box-title">查询参数</h3>
                        </div>
                        <div class="box-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <form>
                                        <div class="row">
                                            <div class="col-sm-4">
                                                <div class="form-group" >
                                                    <label>时间范围:</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-clock-o"></i>
                                                        </div>
                                                        <input name="rangeTime" type="text" class="form-control" id="license-range-time">
                                                    </div>
                                                <#-- /.input group -->
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <div class="form-group" >
                                                    <label>设备:</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa  fa-cube"></i>
                                                        </div>
                                                        <select name="deviceSN" id="license-device-sn" class="form-control select2" style="width: 100%">
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <div class="form-group" >
                                                    <label>状态:</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-flag"></i>
                                                        </div>
                                                        <select name="licenseStatus" id="license-status" class="form-control select2" style="width: 100%">
                                                            <option value="0" selected="selected">不限</option>
                                                            <option value="1">成功</option>
                                                            <option value="2">失败</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="pull-right">
                                                    <button type="button" class="btn btn-primary" id="query-condition-OK" >应用</button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="box box-success">
                        <div class="box-header">
                            <h3 class="box-title">数据列表</h3>
                        </div>
                        <div class="box-body">
                            <table id="history-dev-license-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>站点</th>
                                    <th>设备编号</th>
                                    <th>设备名称</th>
                                    <th>更新时间</th>
                                    <th>更新前到期时间</th>
                                    <th>更新后到期时间</th>
                                    <th>状态</th>
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
<script src="/plugins/select2/select2.full.min.js"></script>
<script src="/plugins/select2/i18n/zh-CN.js"></script>
<script src="/plugins/daterangepicker/moment.min.js"></script>
<script src="/plugins/daterangepicker/daterangepicker.js"></script>
<script src="/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="/plugins/datatables/dataTables.buttons.min.js"></script>
<script src="/plugins/datatables/buttons.bootstrap.min.js"></script>
<script src="/plugins/datatables/dataTables.select.js"></script>
<script src="/plugins/messenger/messenger.js"></script>
<script src="/plugins/bootbox/bootbox.min.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/licenseHistory.js"></script>
</body>
</html>
