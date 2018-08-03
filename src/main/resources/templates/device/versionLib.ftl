<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 版本库管理 </title>
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
                版本管理
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 版本管理</span></li>
                <li class="active">版本库管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-lg-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <i class="fa fa-file-text"></i>
                            <h3 class="box-title">版本列表</h3>
                        </div>
                        <div class="box-body">
                            <table id="version-lib-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>版本</th>
                                    <th>描述</th>
                                    <th>FGPA</th>
                                    <th>BBU</th>
                                    <th>APP</th>
                                    <th>上传时间</th>
                                    <th>上传用户</th>
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

<div class="modal fade" id="version-dlg">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">版本信息</h4>
            </div>
            <div class="modal-body">
                <form id="version-form" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="licensurePath">版本文件</label>
                        <div class="col-sm-10">
                            <div class="input-group file-box">
                                <input type="text" class="form-control" id="filename" name="filename" data-upload="display" >
                                <input type="hidden" class="form-control" id="uploadFilename" name="uploadFilename">
                                <span class="input-group-btn">
                                  <button class="btn btn-primary btn-flat" type="button"
                                          onclick="document.getElementById('uploadFile').click()">浏览</button>
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
                    <div class="form-group">
                        <label for="version" class="col-sm-2 control-label">版本</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="version" name="version" placeholder="升级包版本" maxlength="40">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="fpgaVersion" class="col-sm-2 control-label">FPGA</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="fpgaVersion" name="fpgaVersion" placeholder="FPGA版本" maxlength="40">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bbuVersion" class="col-sm-2 control-label">BBU</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="bbuVersion" name="bbuVersion" placeholder="BBU版本" maxlength="40">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="swVersion" class="col-sm-2 control-label">APP</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="swVersion" name="swVersion" placeholder="APP版本" maxlength="40">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="remark" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10">
                            <textarea  class="form-control" id="remark" name="remark" placeholder="升级版本描述信息" rows="10" cols="80" style="resize: none;"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="version-ok-btn" >确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade" id="upgrade-dlg">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">版本升级</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="upgrade-version">版本</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="upgrade-version"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="upgrade-remark">备注</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="upgrade-remark"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="upgrade-fpgaVersion">FPGA</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="upgrade-fpgaVersion"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="upgrade-bbuVersion">BBU</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="upgrade-bbuVersion"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="upgrade-appVersion">APP</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="upgrade-appVersion"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="exampleInputEmail1">设备</label>
                        <div class="col-sm-10">
                            <div class="slimScrollDiv">
                                <div id="devices-tree"></div>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="versionId" id="upgrade-version-id">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="upgrade-ok-btn" >确定</button>
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
<script src="/plugins/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.iframe-transport.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.fileupload.js"></script>
<script src="/plugins/bootstrap-treeview/bootstrap-treeview-ext.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/versionLib.js"></script>
</body>
</html>
