<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 设备状态 </title>
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
    <link rel="stylesheet" href="/plugins/bootstrap-treeview/bootstrap-treeview.min.css">
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
                设备状态
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active"><a href="###"> 设备管理</a></li>
                <li class="active">设备状态</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-4">
                    <div class="box box-success">
                        <div class="box-header with-border">
                            <h3 class="box-title">设备列表</h3>
                        </div>
                        <div class="box-body" style="min-height: 820px">
                            <div class="row" style="margin-bottom: 20px">
                                <div class="col-md-8">
                                    <span class="" style="color: #090"><i class="fa fa-fw fa-circle"></i>运行</span>
                                    <span class="" style="color: #999999"><i class="fa fa-fw fa-circle"></i>离线</span>
                                    <span class="" style="color: #00ccff"><i class="fa fa-fw fa-circle"></i>即将过期</span>
                                    <span class="" style="color: #ff0000"><i class="fa fa-fw fa-circle"></i>已过期</span>
                                    <span class="" style="color: #ff851b"><i class="fa fa-fw fa-circle"></i>有告警</span>
                                </div>
                                <div class="col-md-4">
                                    <div class="dropdown pull-right">
                                        <i class="fa fa-filter"></i>
                                        <a class="text-black" href=""  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >状态（<span id="filter-text">全部</span>）<span class="caret"></span></a>
                                        <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                                            <li role="presentation"><a role="menuitem" tabindex="-1" href="/device/status?status=all">全部</a></li>
                                            <li role="presentation"><a role="menuitem" tabindex="-1" href="/device/status?status=run">运行中</a></li>
                                            <li role="presentation"><a role="menuitem" tabindex="-1" href="/device/status?status=offline">离线</a></li>
                                            <li role="presentation"><a role="menuitem" tabindex="-1" href="/device/status?status=warning">有告警</a></li>
                                            <li role="presentation"><a role="menuitem" tabindex="-1" href="/device/status?status=willexpire">即将过期</a></li>
                                            <li role="presentation"><a role="menuitem" tabindex="-1" href="/device/status?status=expiredfailure">已过期</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div id="devices-status-tree"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">设备状态信息</h3>
                            <small><em id="device-info"></em></small>
                        </div>
                        <div class="box-body">
                            <div class="row">
                                <section class="col-lg-6  connectedSortable">
                                    <div class="box box-info">
                                        <div class="box-header">
                                            <h3 class="box-title">一般信息</h3>
                                        </div>
                                        <div class="box-body">
                                            <dl class="dl-horizontal" id="device-normal-info">
                                            </dl>
                                        </div>
                                    </div>
                                    <div class="box box-info">
                                        <div class="box-header">
                                            <h3 class="box-title">设备板信息</h3>
                                        </div>
                                        <div class="box-body">
                                            <dl class="dl-horizontal" id="device-board-info">
                                            </dl>
                                        </div>
                                    </div>
                                    <div class="box box-info">
                                        <div class="box-header">
                                            <h3 class="box-title">Sniffer信息</h3>
                                        </div>
                                        <div class="box-body">
                                            <dl class="dl-horizontal" id="device-sniffer-info">
                                            </dl>
                                        </div>
                                    </div>
                                    <div class="box box-info">
                                        <div class="box-header">
                                            <h3 class="box-title">许可信息</h3>
                                        </div>
                                        <div class="box-body">
                                            <dl class="dl-horizontal" id="device-license-info">
                                            </dl>
                                        </div>
                                    </div>
                                </section>
                                <section class="col-lg-6 connectedSortable" >
                                    <div class="box box-info">
                                        <div class="box-header">
                                            <h3 class="box-title">功放信息</h3>
                                        </div>
                                        <div class="box-body">
                                            <dl class="dl-horizontal" id="device-pa-info">
                                            </dl>
                                        </div>
                                    </div>
                                    <div class="box box-info">
                                        <div class="box-header">
                                            <h3 class="box-title">调试信息</h3>
                                        </div>
                                        <div class="box-body">
                                            <dl class="dl-horizontal" id="device-debug-info">
                                            </dl>
                                        </div>
                                    </div>
                                </section>
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
<script src="/plugins/bootstrap-treeview/bootstrap-treeview-ext.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/deviceStatus.js"></script>
</body>
</html>
