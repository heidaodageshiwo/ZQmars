<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 设备管理 </title>
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
                设备详情
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active"><a href="###"> 设备管理</a></li>
                <li class="active">设备详情</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="nav-tabs-custom" >
                      <#--  <ul class="nav nav-tabs pull-right">
                              &nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-primary btn-flat" id="export-data-btn" >导出</button>
                        </ul>-->
                        <div class="tab-content">
                            <div class="chart tab-pane active" id="sites-list" >
                                <table id="sites-table" class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <th></th>
                                        <th>站点编号</th>
                                        <th>站点名称</th>
                                        <#--<th>地址</th>-->
                                        <th>经度</th>
                                        <th>纬度</th>

                                        <th>创建日期</th>
                                        <th>备注</th>
                                        <#--<th>操作</th>-->
                                    </thead>
                                </table>
                            </div>
                            <div class="chart tab-pane" id="device-list" >
                                <table id="devices-table" class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <th>设备编号</th>
                                        <th>状态</th>
                                        <th>移动上号数</th>
                                        <th>联通上号数</th>
                                        <th>电信上号数</th>
                                        <th>未知运营商上号数</th>
                                        <th>操作</th>
                                        <#--<th>创建日期</th>-->
                                        <#--<th>备注</th>-->
                                        <#--<th>所属站点</th>-->
                                        <#--<th>操作</th>-->
                                    </thead>
                                </table>
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

<#--弹出窗体-->
<#---site 窗体-->
<div class="modal fade" id="site-dlg">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">站点信息</h4>
            </div>
            <div class="modal-body">
                <form id="site-form" class="form-horizontal">
                    <div class="form-group">
                        <label for="site-sn" class="col-sm-2 control-label">编号</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="site-sn" name="siteSn" placeholder="站点编号" maxlength="16">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="site-name" class="col-sm-2 control-label">名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="site-name" name="siteName" placeholder="站点名称" maxlength="40">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="province" class="col-sm-2 control-label">省</label>
                        <div class="col-sm-10">
                            <select name="province" id="province" style="width: 100%">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="city" class="col-sm-2 control-label">市</label>
                        <div class="col-sm-10">
                            <select name="city" id="city" style="width: 100%">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="town" class="col-sm-2 control-label">区</label>
                        <div class="col-sm-10">
                            <select name="town" id="town" style="width: 100%">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="site-address" class="col-sm-2 control-label">地址</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="site-address" name="siteAddress" placeholder="详细地址" maxlength="40">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="site-longitude" class="col-sm-2 control-label">经度</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="site-longitude" name="siteLongitude" placeholder="经度" maxlength="40">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="site-latitude" class="col-sm-2 control-label">纬度</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="site-latitude" name="siteLatitude" placeholder="纬度" maxlength="40">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="site-remark" class="col-sm-2 control-label">LAC</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="site-LC" name="siteLC" placeholder="LAC" maxlength="200">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="site-remark" class="col-sm-2 control-label">CI</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="site-CI" name="siteCI" placeholder="CI" maxlength="200">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="site-remark" class="col-sm-2 control-label">备注</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="site-remark" name="siteRemark" placeholder="备注信息" maxlength="200">
                        </div>
                    </div>
                    <input type="hidden" name="id" value="">
                    <input type="hidden" name="action" value="create">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="site-ok-btn" >确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<#--device 窗体 -->
<div class="modal fade" id="device-dlg" style="width: auto;height: auto">
    <div class="modal-dialog">
        <div class="modal-content">
            <#--<div class="modal-header" style="width: auto;height: auto">-->
                <#--<button type="button" class="close" data-dismiss="modal" aria-label="Close">-->
                    <#--<span aria-hidden="true">&times;</span></button>-->
                <#--<h4 class="modal-title center-block">设备信息</h4>-->
            <#--</div>-->
            <div class="modal-body" style="width: auto;height: auto">
                    <div >
                       <#-- <div class="box-header with-border">
                            <h3 class="box-title">设备状态信息</h3>
                            <small><em id="device-info"></em></small>
                        </div>-->
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
            <#--<div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="device-ok-btn" >确定</button>
            </div>-->
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<#--弹出窗体-->

<div class="modal fade" id="export-progress-dlg">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">数据导出</h4>
            </div>
            <div class="modal-body">
                <div class="progress" id="export-progress-bar">
                    <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                        <span class="sr-only">0% Complete (success)</span>
                    </div>
                </div>
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
<script src="/plugins/daterangepicker/moment.min.js"></script>
<script src="/plugins/daterangepicker/daterangepicker.js"></script>
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
<script src="/js/page/details.js"></script>
</body>
</html>
