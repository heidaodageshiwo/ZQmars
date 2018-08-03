<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 数据上报 </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/plugins/select2/select2.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
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
                数据上报
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span>数据采集</span></li>
                <li class="active">数据上报</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <h3 class="box-title">过滤条件</h3>
                        </div>
                    <#-- /.box-header -->
                        <div class="box-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <form>
                                        <div class="row">
                                            <div class="col-sm-4">
                                                <div class="form-group" >
                                                    <label>站点:</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa  fa-cubes"></i>
                                                        </div>
                                                        <select id="capture-site-sn" name="captureSiteSN" class="form-control select2" style="width: 100%;">
                                                        </select>
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
                                                        <select id="capture-device-sn" name="captureDeviceSN" class="form-control select2" style="width: 100%;">
                                                        </select>
                                                    </div>
                                                <#-- /.input group -->
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
            <div class="row">
                <div class="col-xs-12">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#tabRealTimeDataReport" data-toggle="tab">实时数据上报</a></li>
                            <li><a href="#tabCacheDataReport" data-toggle="tab">缓存数据上报</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="tabRealTimeDataReport" >
                                <table id="realtime-table" class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th style="width: 80px">序号</th>
                                        <th>IMSI</th>
                                        <th>IMEI</th>
                                        <th>运营商</th>
                                        <th>归属地</th>
                                        <th>站点名称</th>
                                        <th>设备名称</th>
                                        <th>捕获时间</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                            <div class="tab-pane" id="tabCacheDataReport" >
                                <table id="cache-table" class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th style="width: 80px">序号</th>
                                        <th>IMSI</th>
                                        <th>IMEI</th>
                                        <th>运营商</th>
                                        <th>归属地</th>
                                        <th>站点名称</th>
                                        <th>设备名称</th>
                                        <th>捕获时间</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- /.box -->
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
<script src="/plugins/select2/select2.full.min.js"></script>
<script src="/plugins/select2/i18n/zh-CN.js"></script>
<script src="/plugins/sockjs/sockjs.min.js"></script>
<script src="/js/notifyHandle.js"></script>
<script src="/plugins/fastclick/fastclick.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/realTimeData.js"></script>
</body>
</html>
