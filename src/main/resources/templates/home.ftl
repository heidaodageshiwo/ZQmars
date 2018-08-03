<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 首页</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/plugins/morris/morris.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="/css/efence.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<#-- Site wrapper -->
<div class="wrapper">
   <#-- load header bar -->
   <#include "./comm/headerBar.ftl">
   <#-- load left side menus sidebar -->
   <#include "./comm/sideMenu.ftl">

    <#-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <#-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                总控台
                <small>概览</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active">总控台</li>
            </ol>
        </section>

        <#-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-lg-6">
                    <#-- 硬盘资源 box -->
                    <div class="box">
                        <div class="box-header">
                            <i class="fa fa-database"></i>
                            <h3 class="box-title">
                                硬盘资源
                            </h3>
                        </div>
                        <div class="box-body">
                            <div id="hard-disk-chart" style="height: 300px; width: 100%;"></div>
                            <input type="hidden" id="hard-disk-used" value="${statistics.system.hardDisk.used}">
                            <input type="hidden" id="hard-disk-free" value="${statistics.system.hardDisk.free}">
                        </div>
                        <#-- /.box-body-->
                    </div>
                    <#-- /.box -->
                </div>
                <div class="col-lg-6">
                    <div class="box">
                        <div class="box-header">
                            <i class="fa  fa-cubes"></i>
                            <h3 class="box-title">
                                设备状态
                            </h3>
                        </div>
                        <div class="box-body">
                           <div class="device-region-content">
                               <ul class="pull-left">
                                   <li class="">
                                       <a class="label label-default"  href="/device/status?status=all" >
                                           <span class="text-black">采集设备</span>
                                           <span style="margin-left:3px" id="device-total">${statistics.device.total}</span>
                                       </a>
                                   </li>
                               </ul>
                               <div class="relation-line pull-left"></div>
                               <ul class="pull-left">
                                   <li class="run-status run-status-running">
                                       <i></i>
                                       <a class="label label-default" href="/device/status?status=run">
                                           <span style="color:#090">运行中</span>
                                           <span style="margin-left:3px" id="device-running-total">${statistics.device.running}</span></a>
                                   </li>
                                   <li class="run-status run-status-offline">
                                       <i></i>
                                       <a class="label label-default" href="/device/status?status=offline">
                                           <span style="color: #999999">离线</span>
                                           <span style="margin-left:3px" id="device-offline-total">${statistics.device.offline}</span>
                                       </a>
                                   </li>
                                   <li class="run-status run-status-recent">
                                       <i></i>
                                       <a class="label label-default" href="/device/status?status=willexpire">
                                           <span  style="color:#00ccff">即将过期</span>
                                           <span style="margin-left:3px" id="device-willexpire-total">${statistics.device.willExpire}</span>
                                       </a>
                                   </li>
                                   <li class="run-status run-status-expiredfailure">
                                       <i></i>
                                       <a class="label label-default" href="/device/status?status=expiredfailure">
                                           <span  style="color:#ff0000">已过期</span>
                                           <span style="margin-left:3px" id="device-expiredfailure-total">${statistics.device.expiredFailure}</span>
                                       </a>
                                   </li>
                                   <li class="run-status run-status-warning">
                                       <i></i>
                                       <a class="label label-default" href="/device/status?status=warning">
                                           <span class="text-orange">有告警</span>
                                           <span style="margin-left:3px" id="device-warning-total">${statistics.device.warning}</span>
                                       </a>
                                   </li>
                               </ul>
                           </div>
                        </div>
                        <#-- /.box-body-->
                    </div>
                    <#-- /.box -->
                </div>
            </div>
            <#-- /.row -->
            <div class="row">
            </div>
            <#-- /.row -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

   <#-- load footer bar -->
   <#include "./comm/footer.ftl">

</div>
<#-- ./wrapper -->

<script src="/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/plugins/jQueryUI/jquery-ui.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script src="/plugins/jquery-idleTimeout/store.min.js"></script>
<script src="/plugins/jquery-idleTimeout/jquery-idleTimeout.min.js"></script>
<script src="/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<script src="/plugins/sockjs/sockjs.min.js"></script>
<script src="/js/notifyHandle.js"></script>
<script src="/plugins/fastclick/fastclick.js"></script>
<script src="/plugins/morris/raphael-min.js"></script>
<script src="/plugins/morris/morris.min.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/home.js"></script>

</body>
</html>
