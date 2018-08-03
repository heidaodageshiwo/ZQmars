<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${systemInfo.projectName }  | 许可信息</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger-theme-air.css">
    <link rel="stylesheet" href="/plugins/iCheck/square/blue.css">
    <link rel="stylesheet" href="/css/login.css">
</head>
<body class="hold-transition register-page">

<div class="license-box">
    <div class="register-logo">
        <a href="/">${systemInfo.projectName }</a>
    </div>

    <div class="register-box-body">
        <p class="login-box-msg">软件许可信息</p>

        <div class="box box-info">

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
                <div class="box-footer">
                     <a class="btn bg-olive btn-flat" href="#" data-toggle="modal" data-target="#licenseModal">更改许可</a>
                     <a class="btn btn-primary pull-right btn-flat" href="/user/login">返回</a>
                </div>
            </div>
        </div>

        <p class="login-box-msg">Copyright © ${systemInfo.companyYear } ${systemInfo.companyName }</p>

     </div>
</div>

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
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script src="/plugins/messenger/messenger.js"></script>
<script src="/plugins/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.iframe-transport.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.fileupload.js"></script>
<script src="/js/page/license.js"></script>

</body>
</html>
