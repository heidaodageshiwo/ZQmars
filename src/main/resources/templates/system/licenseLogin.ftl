<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 系统授权 </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
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
                系统授权
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 系统维护</span></li>
                <li class="active"> 系统授权</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3">
                    <div class="box box-primary">
                        <div class="box-header">
                            <h3 class="box-title">授权信息</h3>
                        </div>
                        <div class="box-body pad">
                            <div class="form-horizontal">
                                <div class="box-body">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">软件名称</label>
                                        <div class="col-sm-9">
                                            <p class="form-control-static">${licenseInfo.softwareName}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">版本</label>
                                        <div class="col-sm-9">
                                            <p class="form-control-static">${licenseInfo.version}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">产品序号</label>
                                        <div class="col-sm-9">
                                            <p class="form-control-static">${licenseInfo.productSN}</p>
                                        </div>
                                    </div>
                                    <div class="form-group <#if licenseInfo.verifyLicense!=0 >text-danger</#if>">
                                        <label class="col-sm-3 control-label">许可信息</label>
                                        <div class="col-sm-9">
                                            <p class="form-control-static">${licenseInfo.licensedTo}</p>
                                        </div>
                                    </div>
                                    <div class="form-group <#if licenseInfo.verifyLicense!=0 >text-danger</#if>">
                                        <label class="col-sm-3 control-label">许可发布日期</label>
                                        <div class="col-sm-9">
                                            <p class="form-control-static">${licenseInfo.publishDate}</p>
                                        </div>
                                    </div>
                                    <div class="form-group <#if licenseInfo.verifyLicense!=0 >text-danger</#if>">
                                        <label class="col-sm-3 control-label">到期日期</label>
                                        <div class="col-sm-9">
                                            <p class="form-control-static">${licenseInfo.expiredDate}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">JRE</label>
                                        <div class="col-sm-9">
                                            <p class="form-control-static">${licenseInfo.jreInfo}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">JVM</label>
                                        <div class="col-sm-9">
                                            <p class="form-control-static">${licenseInfo.jvmInfo}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="box-footer">
                            <div class="pull-right box-tools">
                                <button type="button" class="btn btn-flat bg-navy" href="#" data-toggle="modal" data-target="#licenseModal" >
                                    <i class="fa fa-refresh"></i>更新</button>
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

<#-- license Modal -->
<div class="modal  fade" id="licenseModal" tabindex="-1" role="dialog" aria-labelledby="licenseModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="licenseModal">上传许可文件</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" enctype="multipart/form-data">
                    <div class="form-group">
                        <label class="col-xs-3 control-label" for="licensurePath">许可文件</label>
                        <div class="col-xs-8">
                            <div class="input-group file-box">
                                <input type="text" class="form-control" id="uploadFileName" name="uploadFileName">
                                <input type="hidden" class="form-control" id="fileTempName" name="fileTempName">
                                <span class="input-group-btn">
                                  <button class="btn btn-primary btn-flat" type="button"
                                          onclick="document.getElementById('uploadFile').click()">浏览</button>
                                </span>
                                <input type="file" class="file-input" id="uploadFile" name="uploadFile" >
                            </div>
                        </div>
                    </div>
                    <div id="upload-progress" class="form-group" style="display: none">
                        <div class="col-xs-8 col-xs-offset-1">
                            <div class="progress">
                                <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                                    <span class="sr-only">0% Complete</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-flat pull-left" type="button">关闭</button>
                <button class="btn btn-flat btn-primary" type="button" id="submit-license-btn">提交</button>
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
<script src="/plugins/messenger/messenger.js"></script>
<script src="/plugins/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.iframe-transport.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.fileupload.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/license.js"></script>

</body>
</html>
