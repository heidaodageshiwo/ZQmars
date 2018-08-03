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
                可疑人员分析
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active"><a href="###"> 数据分析</a></li>
                <li class="active">可疑人员分析</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">



            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <h3 class="box-title">查询参数</h3>
                        </div>
                    <#-- /.box-header -->
                        <div class="box-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <form>
                                        <div class="row">
                                       <#--     <div class="col-sm-4">
                                                <div class="form-group" >
                                                    <label>时间范围:</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-clock-o"></i>
                                                        </div>
                                                        <input name="captureRangeTime" type="text" class="form-control pull-right" id="capture-range-time">
                                                    </div>
                                                &lt;#&ndash; /.input group &ndash;&gt;
                                                </div>
                                            </div>-->


                                           <div class="col-sm-4">
                                               <div class="form-group" >
                                                   <label>IMSI:</label>
                                                   <div class="input-group">
                                                       <div class="input-group-addon">
                                                           <i class="fa fa-mobile-phone"></i>
                                                       </div>
                                                       <input name="imsi" type="text" maxlength="16" class="form-control pull-right" id="imsi">
                                                   </div>
                                               <#-- /.input group -->
                                               </div>
                                           </div>




                                            <div class="col-sm-4">
                                                <div class="form-group" >
                                                    <label>上号天数:</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-university"></i>
                                                        </div>

                                                        <input name="daycount" type="text" maxlength="16" class="form-control pull-right" id="daycount">
                                                        <#--<select id="capture-operator" name="captureOperator" class="form-control select2" style="width: 100%;">
                                                            <option value="0" selected="selected">不限</option>
                                                            <option value="1">中国移动</option>
                                                            <option value="2">中国电信</option>
                                                            <option value="3">中国联通</option>
                                                        </select>-->
                                                    </div>
                                                </div>
                                            </div>
                                         <#--   <div class="col-sm-4">
                                                <div class="form-group" >
                                                    <label>归属地:</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa  fa-street-view"></i>
                                                        </div>
                                                        <select id="capture-homeOwnership" name="captureHomeOwnership" class="form-control select2" style="width: 100%;">
                                                        </select>
                                                    </div>
                                                &lt;#&ndash; /.input group &ndash;&gt;
                                                </div>
                                            </div>-->

                                           <div class="col-sm-4">
                                               <div class="form-group" >
                                                   <label>上号总数:</label>
                                                   <div class="input-group">
                                                       <div class="input-group-addon">
                                                           <i class="fa  fa-street-view"></i>
                                                       </div>

                                                       <input name="datacount" type="text" maxlength="16" class="form-control pull-right" id="datacount">
                                                   <#--<select id="capture-operator" name="captureOperator" class="form-control select2" style="width: 100%;">
                                                       <option value="0" selected="selected">不限</option>
                                                       <option value="1">中国移动</option>
                                                       <option value="2">中国电信</option>
                                                       <option value="3">中国联通</option>
                                                   </select>-->
                                                   </div>
                                               </div>
                                           </div>



                                        </div>
                                        <div class="row">
                                            <div class="col-sm-4">
                                                <div class="form-group" >
                                                    <label>周期:</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa  fa-cubes"></i>
                                                        </div>
                                                        <select id="period" name="period" class="form-control select2" style="width: 100%;">
                                                            <option value="0" selected="selected">请选择</option>
                                                            <option value="1">近一周</option>
                                                            <option value="2">近一月</option>
                                                            <option value="3">近半年</option>
                                                        </select>
                                                        </select>
                                                    </div>
                                                <#-- /.input group -->
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <div class="form-group" >
                                                    <label>时间范围:</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-clock-o"></i>
                                                        </div>
                                                        <input name="captureRangeTime" type="text" class="form-control pull-right" id="capture-range-time">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-4" style="padding-top: 23px;">
                                                <div class="form-group" >
                                                  <#--  <label>IMSI:</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-mobile-phone"></i>
                                                        </div>
                                                        <input name="captureIMSI" type="text" maxlength="16" class="form-control pull-right" id="capture-imsi">
                                                    </div>-->
                                                <#-- /.input group -->
                                                      <button type="button" class="btn btn-primary" id="query-condition-OK" >应用</button>
                                                      <div class="btn-group">
                                                          <button type="button" class="btn btn-primary" id="export-data-btn" >导出</button>
                                                          <button type="button" class="btn btn-primary btn-flat dropdown-toggle" data-toggle="dropdown" id="export-data-dropdown-btn">
                                                              <span class="caret"></span>
                                                              <span class="sr-only">Toggle Dropdown</span>
                                                          </button>
                                                          <ul class="dropdown-menu" role="menu">
                                                          <#--<li><a href="javascript:void(0);" id="export-data-txt-btn" >导出TXT</a></li>-->
                                                          <#--<li><a href="javascript:void(0);" id="export-data-csv-btn">导出CSV</a></li>-->
                                                              <li><a href="javascript:void(0);" id="export-data-xls-btn">导出XLS</a></li>

                                                          </ul>
                                                      </div>


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
                    <div class="nav-tabs-custom" >

                        <div class="tab-content">
                            <div class="chart tab-pane active" id="sites-list" >
                                <table id="sites-table" class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <th style="width: 80px">编号</th>
                                   <#-- <th>站点编号</th>
                                    <th>站点名称</th>
                                    <th>地址</th>
                                    <th>经纬度</th>
                                    <th>创建日期</th>
                                    <th>备注</th>
                                    <th>操作</th>-->
                                    <th>IMSI</th>
                                    <#-- <th>IMEI</th>-->
                                    <th>运营商</th>
                                    <th>归属地</th>
                                    <th>第1可能位置</th>
                                    <th>第2可能位置</th>
                                    <th>上号天数</th>
                                    <th>上号总数</th>
                                    <th>详情</th>
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
<div class="modal fade" id="site-dlg1">
    <div class="modal-dialog">
        <div class="modal-content" style="width:auto;">
            <div class="tab-content">
                <div class="chart tab-pane active" id="sites-list" >
                    <table id="sites-table1" class="table table-striped table-bordered table-hover">
                        </br>

                       &nbsp;&nbsp;<tr class="active" ><font size="5px;">IMSI详情信息</font></tr></br>
                        &nbsp;&nbsp;<tr>IMSI: <input type="text" disabled="disabled"  style="background:transparent;border:0;width: 130px;"  id="imsi1" name="imsi1"  value=""></tr>
                        &nbsp;&nbsp;&nbsp;&nbsp;<tr>运营商: <input type="text" disabled="disabled" style="background:transparent;border:0;width: 60px;"  id="operator" name="operator"  value=""></tr>
                        &nbsp;&nbsp;&nbsp;&nbsp;<tr>归属地: <input type="text" disabled="disabled" style="background:transparent;border:0"  id="cityname" name="cityname"  value=""></tr></br>
                        &nbsp;&nbsp;<tr class="active"><font size="5px;">上号历史</font></tr>
                        <thead>
                        <th style="width: 80px">编号</th>

                        <th>站点位置</th>


                        <th>采集时间</th>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<#--device 窗体 -->

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
    </div>
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
<script src="/js/page/analysisData.js"></script>












</body>
</html>
