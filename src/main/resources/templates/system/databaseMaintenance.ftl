<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 数据库维护 </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/plugins/iCheck/minimal/blue.css">
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
                数据库维护
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 系统维护</span></li>
                <li class="active"> 数据库维护</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-sm-12">
                    <div class="box">
                        <div class="box-body pad">
                            <div class="box-group" id="database-accordion">
                                <#--<div class="panel box box-primary">-->
                                    <#--<div class="box-header with-border">-->
                                        <#--<h4 class="box-title">-->
                                            <#--<a class="collapsed" data-toggle="collapse" data-parent="#database-accordion" href="#collapse-maintenance-data">业务数据维护</a>-->
                                        <#--</h4>-->
                                    <#--</div>-->
                                    <#--<div id="collapse-maintenance-data" class="panel-collapse collapse in">-->
                                        <#--<div class="box-body">-->

                                        <#--</div>-->
                                    <#--</div>-->
                                <#--</div>-->
                                <div class="panel box box-success">
                                    <div class="box-header with-border">
                                        <h4 class="box-title">
                                            <a class="collapsed" data-toggle="collapse" data-parent="#database-accordion" href="#collapse-dump-data">导出基础数据</a>
                                        </h4>
                                    </div>
                                    <div id="collapse-dump-data" class="panel-collapse collapse">
                                        <div class="box-body">
                                            <div class="row">
                                                <div class="col-sm-6 col-sm-offset-3">
                                                    <form id="dump-data-form"  role="form" class="form-horizontal">
                                                        <div class="form-group">
                                                            <div class="col-xs-6">
                                                                <div class="checkbox">
                                                                    <label>
                                                                        <input type="checkbox" name="userData"> 用户数据
                                                                    </label>
                                                                </div>
                                                            </div>
                                                            <div class="col-xs-6">
                                                                <div class="checkbox">
                                                                    <label>
                                                                        <input type="checkbox" name="siteData"> 站点数据
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="col-xs-6">
                                                                <div class="checkbox">
                                                                    <label>
                                                                        <input type="checkbox" name="blacklistData"> 黑名单配置
                                                                    </label>
                                                                </div>
                                                            </div>
                                                            <div class="col-xs-6">
                                                                <div class="checkbox">
                                                                    <label>
                                                                        <input type="checkbox" name="homeOwnershipData"> 特殊归属地配置
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="col-xs-8 col-xs-offset-2">
                                                                <button class="btn btn-flat bg-navy btn-block">导出</button>
                                                            </div>
                                                        </div>
                                                        <div class="form-group" style="display: none">
                                                            <div class="col-xs-12">
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
                                        </div>
                                    </div>
                                </div>
                                <div class="panel box box-danger">
                                    <div class="box-header with-border">
                                        <h4 class="box-title">
                                            <a class="collapsed" data-toggle="collapse" data-parent="#database-accordion" href="#collapse-execute-data">导入基础数据</a>
                                        </h4>
                                    </div>
                                    <div id="collapse-execute-data" class="panel-collapse collapse">
                                        <div class="box-body">
                                            <div class="row">
                                                <div class="col-sm-6 col-sm-offset-3">
                                                    <form id="upload-file-form"  enctype="multipart/form-data" role="form" class="form-horizontal">
                                                        <div class="form-group">
                                                            <label  for="sqlPath" class="col-xs-4 control-label" >脚本文件:</label>
                                                            <div class="col-xs-8">
                                                                <div class="input-group file-box">
                                                                    <input type="text" class="form-control" id="uploadFileName" name="uploadFileName">
                                                                    <input type="hidden" class="form-control" id="fileUrl" name="fileUrl">
                                                                    <span class="input-group-btn">
                                                                      <button class="btn btn-primary btn-flat" type="button"
                                                                              onclick="document.getElementById('uploadFile').click()">浏览</button>
                                                                    </span>
                                                                    <input type="file" class="file-input" id="uploadFile" name="uploadFile" >
                                                                </div>
                                                             </div>
                                                        </div>
                                                        <div id="upload-executeFile-progress" class="form-group" style="display: none">
                                                            <div class="col-xs-8 col-xs-offset-4">
                                                                <div class="progress">
                                                                    <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                                                                        <span class="sr-only">0% Complete</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-6 col-sm-offset-3">
                                                        <div class="row">
                                                            <div class="col-xs-4 col-xs-offset-5">
                                                                <button class="btn btn-block btn-flat bg-navy" id="execute-script-btn" disabled >执行</button>
                                                            </div>
                                                        </div>
                                                        <div class="row" style="margin-top: 20px">
                                                            <div class="col-xs-8 col-xs-offset-4">
                                                                <div id="execute-script-task-progress"  class="progress" style="display: none">
                                                                    <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                                                                        <span class="sr-only">0% Complete</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
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
<script src="/plugins/iCheck/icheck.min.js"></script>
<script src="/plugins/messenger/messenger.js"></script>
<script src="/plugins/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.iframe-transport.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.fileupload.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/databaseMaintenance.js"></script>

</body>
</html>
