<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 设备授权状态 </title>
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
    <link rel="stylesheet" href="/plugins/select2/select2.min.css">
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
                设备授权状态
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 设备管理</span></li>
                <li class="active">授权状态</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-lg-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <i class="fa  fa-info"></i>
                            <h3 class="box-title">设备许可信息</h3>
                        </div>
                        <div class="box-body">
                            <table id="device-license-table" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>站点</th>
                                    <th>设备编号</th>
                                    <th>设备名</th>
                                    <th>是否过期</th>
                                    <th>到期时间</th>
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

<div class="modal fade" id="license-dlg">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title center-block">设备授权-<small id="dlg-device-info"></small></h4>
            </div>
            <div class="modal-body">
                <form id="license-form" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="licensurePath">授权文件</label>
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
                    <input type="hidden" name="deviceSN" id="dlg-device-sn">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-flat" id="license-ok-btn" >确定</button>
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
<script src="/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="/plugins/datatables/dataTables.buttons.min.js"></script>
<script src="/plugins/datatables/buttons.bootstrap.min.js"></script>
<script src="/plugins/datatables/dataTables.select.js"></script>
<script src="/plugins/messenger/messenger.js"></script>
<script src="/plugins/bootbox/bootbox.min.js"></script>
<script src="/plugins/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.iframe-transport.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.fileupload.js"></script>
<script src="/plugins/bootstrap-treeview/bootstrap-treeview-ext.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/licenseIndex.js"></script>
</body>
</html>
