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
                设备管理
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active"><a href="###"> 设备管理</a></li>
                <li class="active">设备管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="nav-tabs-custom" >
                        <ul class="nav nav-tabs pull-right">
                            <li class="active"><a href="#sites-list" data-toggle="tab">站点</a></li>
                            <li><a href="#device-list" data-toggle="tab">设备</a></li>
                            <li class="pull-left header"><i class="fa fa-cubes"></i> 站点设备列表</li>
                        </ul>
                        <div class="tab-content">
                            <div class="chart tab-pane active" id="sites-list" >
                                <table id="sites-table" class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <th></th>
                                        <th>站点编号</th>
                                        <th>站点名称</th>
                                        <th>地址</th>
                                        <th>经纬度</th>
                                        <th>创建日期</th>
                                        <th>备注</th>
                                        <th>操作</th>
                                    </thead>
                                </table>
                            </div>
                            <div class="chart tab-pane" id="device-list" >
                                <table id="devices-table" class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <th>设备编号</th>
                                        <th>设备名称</th>
                                        <th>设备类型</th>
                                        <th>运营商</th>
                                        <th>工作频段</th>
                                        <th>创建日期</th>
                                        <th>备注</th>
                                        <th>所属站点</th>
                                        <th>操作</th>
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
<div class="modal fade" id="device-dlg">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">设备信息</h4>
            </div>
            <div class="modal-body">
                <form id="device-form" class="form-horizontal">
                    <div class="form-group">
                        <label for="device-sn" class="col-sm-2 control-label">编号</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="device-sn" name="deviceSn" placeholder="设备编号" maxlength="16">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="device-name" class="col-sm-2 control-label">名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="device-name" name="deviceName" placeholder="设备名称" maxlength="40">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="device-type" class="col-sm-2 control-label">类型</label>
                        <div class="col-sm-10">
                            <select name="deviceType" id="device-type" style="width: 100%">
                                <option value="1">CDMA</option>
                                <option value="2">GSM</option>
                                <option value="3">WCDMA</option>
                                <option value="4">TD-SCDMA</option>
                                <option value="5">TDD LTE</option>
                                <option value="6">FDD LTE</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="device-band" class="col-sm-2 control-label">Band</label>
                        <div class="col-sm-10">
                            <select name="deviceBand" id="device-band" style="width: 100%">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="device-operator" class="col-sm-2 control-label">运营商</label>
                        <div class="col-sm-10">
                            <select name="deviceOperator" id="device-operator" style="width: 100%">
                                <option value="1">中国移动</option>
                                <option value="2">中国电信</option>
                                <option value="3">中国联通</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="device-manufacturer" class="col-sm-2 control-label">生产厂家</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="device-manufacturer" name="deviceManufacturer" placeholder="设备生产厂家" maxlength="40">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="device-remark" class="col-sm-2 control-label">备注</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="device-remark" name="deviceRemark" placeholder="备注信息" maxlength="200">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="belongTo" class="col-sm-2 control-label">所属站点</label>
                        <div class="col-sm-10">
                            <select name="belongTo" id="belongTo" style="width: 100%">
                            </select>
                        </div>
                    </div>
                    <input type="hidden" name="id" value="">
                    <input type="hidden" name="action" value="create">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="device-ok-btn" >确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<#--弹出窗体-->


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
<script src="/js/page/devices.js"></script>
<script src="/js/page/deviceAlarmConfig.js"></script>
</body>
</html>
