<!DOCTYPE html>
<html xmlns:background="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 预警 </title>
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
    <link rel="stylesheet" href="/plugins/messenger/messenger.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger-theme-air.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="/css/efence.css">
    <link rel="stylesheet" href="/plugins/daterangepicker/daterangepicker.css">

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
                白名单预警管理
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active"><a href="###"> 预警</a></li>
                <li class="active">白名单管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-lg-12">
                    <div class="box box-primary ">
                        <div class="box-header">
                            <i class="fa fa-th-list"></i>
                            <h3 class="box-title">白名单管理</h3>
                        </div>
                        <div class="box-body">
                            <table id="whitelist-table" class="table table-middle table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>编号</th>
                                    <th>IMSI</th>
                                    <th>手机号</th>
                                    <th>姓名</th>
                                    <th>运营商</th>
                                    <th>归属地</th>
                                    <th>所属机构</th>
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

<#---添加名单窗体-->
<div class="modal fade" id="whitelist-dlg">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">添加白名单</h4>
            </div>
            <div class="modal-body">
                <div  class="form-horizontal">
                    <form id="whitelist-form">
                        <div class="form-group">
                            <label for="imsi" class="col-sm-2 control-label">IMSI</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="imsi" name="imsi" placeholder="请填写IMSI号码" maxlength="40">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="phone" class="col-sm-2 control-label">手机号</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="phone" name="phone" placeholder="请填写手机号码" maxlength="40">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label">姓名</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name" name="name" placeholder="请填写姓名" maxlength="40">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="operator" class="col-sm-2 control-label">运营商</label>
                            <div class="col-sm-10">
                                <select name="deviceOperator" id="operator" style="width:100%;height: 34px">
                                    <option disabled selected>请选择运营商</option>
                                    <option value="中国移动">中国移动</option>
                                    <option value="中国电信">中国电信</option>
                                    <option value="中国联通">中国联通</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="ownership" class="col-sm-2 control-label">归属地</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="ownership" name="ownership" placeholder="请填写归属地" maxlength="200">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="organization" class="col-sm-2 control-label">所属机构</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="organization" name="organization" placeholder="请填写所属机构" maxlength="200">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="whitelist-ok-btn" >提交</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

    <#---修改名单窗体-->
        <div class="modal fade" id="upwhitelist-dlg">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title center-block">修改白名单信息</h4>
                    </div>
                    <div class="modal-body">
                        <div  class="form-horizontal">
                            <form id="upwhitelist-form">
                                <div class="form-group">
                                    <label for="upimsi" class="col-sm-2 control-label">IMSI</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="upimsi" name="upimsi" placeholder="请填写IMSI号码" maxlength="40">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="upphone" class="col-sm-2 control-label">手机号</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="upphone" name="upphone" placeholder="请填写手机号码" maxlength="40">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="upname" class="col-sm-2 control-label">姓名</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="upname" name="upname" placeholder="请填写姓名" maxlength="40">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="upoperator" class="col-sm-2 control-label">运营商</label>
                                    <div class="col-sm-10">
                                        <select name="deviceOperator" id="upoperator" style="width:100%;height: 34px">
                                            <option value="请选择运营商" disabled selected>请选择运营商</option>
                                            <option value="中国移动">中国移动</option>
                                            <option value="中国电信">中国电信</option>
                                            <option value="中国联通">中国联通</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="upownership" class="col-sm-2 control-label">归属地</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="upownership" name="upownership" placeholder="备注" maxlength="200">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="uporganization" class="col-sm-2 control-label">所属机构</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="uporganization" name="uporganization" placeholder="备注" maxlength="200">
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary btn-flat" id="upwhitelist-ok-btn" >提交</button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>

        <div class="modal fade" id="importblacklist-dlg">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title center-block">导入白名单信息</h4>
                    </div>
                    <div class="modal-body">
                        <form id="importblacklist-form" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="licensurePath">白名单文件</label>
                                <div class="col-sm-10">
                                    <div class="input-group file-box">
                                        <input type="text" class="form-control" id="filename" name="filename" data-upload="display" >
                                        <input type="hidden" class="form-control" id="uploadFilename" name="uploadFilename">
                                        <span class="input-group-btn">
                                  <button class="btn btn-primary btn-flat" type="button"
                                          onclick="document.getElementById('uploadFile').click()">名单导入</button>
                                </span>
                                        <input type="file" class="file-input" id="uploadFile" name="uploadFile" >
                                    </div>
                                </div>
                            </div>
                            <div id="upload-progress" class="form-group" style="display: none">
                                <div class="col-sm-8 col-sm-offset-1">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                                            <span class="sr-only">0% Complete</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </form>
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
<script src="/plugins/daterangepicker/moment.min.js"></script>
<script src="/plugins/daterangepicker/daterangepicker.js"></script>
<script src="/js/page/whitelist.js"></script>
<script src="/m16/js/dataTables.default.js"></script>
<script src="/plugins/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.iframe-transport.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.fileupload.js"></script>

</body>
</html>
