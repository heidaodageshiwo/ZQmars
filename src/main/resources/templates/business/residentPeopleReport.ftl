<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 常驻人口外来人口分析 </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/plugins/morris/morris.css">
    <link rel="stylesheet" href="/plugins/datatables/dataTables.bootstrap.css">
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
                常驻人口外来人口分析详情
                <small>${taskReport.taskName}</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 数据分析</span></li>
                <li><a href="/analysis/residentPeople">常驻人口外来人口分析</a></li>
                <li class="active">分析详情</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <h3 class="box-title">输入信息</h3>
                        </div>
                        <div class="box-body">
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-horizontal">
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">名称</label>
                                            <div class="col-sm-8">
                                                <p class="form-control-static">${taskReport.taskName}</p>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">创建时间</label>
                                            <div class="col-sm-8">
                                                <p class="form-control-static">${taskReport.createTime}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-horizontal">
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">地区</label>
                                            <div class="col-sm-8">
                                                <p class="form-control-static">${taskReport.areaName}</p>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">时间范围</label>
                                            <div class="col-sm-8">
                                                <p class="form-control-static">${taskReport.rangeTime}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <input id="residentPeople-task-id" type="hidden" name="taskId" value="${taskReport.taskId?c}">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-success">
                        <div class="box-header with-border">
                            <h3 class="box-title">分析结果</h3>
                        </div>
                        <div class="box-body">
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="chart" id="residentPeople-chart" style="height: 500px; width: 100%;"></div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="nav-tabs-custom" >
                                        <ul class="nav nav-tabs pull-right">
                                            <li class="active"><a href="#fixedPeople-list" data-toggle="tab">常驻人口</a></li>
                                            <li><a href="#periodPeople-list" data-toggle="tab">外来人口</a></li>
                                            <li class="pull-left header"><i class="fa fa-users"></i> IMSI列表</li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="chart tab-pane active" id="fixedPeople-list" >
                                                <table id="fixedPeople-table" class="table table-striped table-bordered table-hover">
                                                    <thead>
                                                        <th>序号</th>
                                                        <th>IMSI</th>
                                                    </thead>
                                                </table>
                                            </div>
                                            <div class="chart tab-pane" id="periodPeople-list" >
                                                <table id="periodPeople-table" class="table table-striped table-bordered table-hover">
                                                    <thead>
                                                        <th>序号</th>
                                                        <th>IMSI</th>
                                                    </thead>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
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

<script src="/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/plugins/jQueryUI/jquery-ui.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script src="/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<script src="/plugins/jquery-idleTimeout/store.min.js"></script>
<script src="/plugins/jquery-idleTimeout/jquery-idleTimeout.min.js"></script>
<script src="/plugins/sockjs/sockjs.min.js"></script>
<script src="/js/notifyHandle.js"></script>
<script src="/plugins/fastclick/fastclick.js"></script>
<script src="/plugins/morris/raphael-min.js"></script>
<script src="/plugins/morris/raphael-min.js"></script>
<script src="/plugins/morris/morris.min.js"></script>
<script src="/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/residentPeopleReport.js"></script>
</body>
</html>
