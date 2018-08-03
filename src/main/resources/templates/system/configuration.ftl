<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 系统配置 </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger-theme-air.css">
    <link rel="stylesheet" href="/plugins/bootstrap-switch/bootstrap-switch.min.css">
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
                系统配置
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 系统维护</span></li>
                <li class="active"> 系统配置</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3">
                    <div class="box box-primary">
                        <div class="box-header">
                            <h3 class="box-title">系统参数</h3>
                        </div>
                        <div class="box-body pad">
                            <form id="sysConf-form" class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">系统名称</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="sys-name" name="systemName" placeholder="当前系统名称" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">系统图标</label>
                                    <div class="col-sm-6">
                                        <a href="javascript:void(0)" id="sys-icon">
                                            <img src="" class="img-circle" alt="logo Image" style="width: 100%;max-width: 32px;height: auto;">
                                        </a>
                                        <div class="file-box">
                                            <input type="hidden"  id="sys-icon-url" name="systemIcon" >
                                            <input type="file" class="file-input" id="upload-file" name="file" >
                                        </div>
                                    </div>
                                </div>
                                <div id="upload-progress" class="form-group" style="display: none" >
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <div class="progress">
                                            <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                                                <span class="sr-only">0% Complete</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">公司</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control"  name="companyName" placeholder="公司名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">公司网址</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="companyURL" placeholder="公司网站地址">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">公司成立时间</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="companyYear" placeholder="公司成立年份">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">升级服务URL</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="upgradeURL" placeholder="升级服务访问URL">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">默认经度</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="mapCenterLng" placeholder="地图中心点经度值" maxlength="15">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">默认纬度</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="mapCenterLat" placeholder="地图中心点维度值" maxlength="15">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">默认级别</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="mapZoom" placeholder="地图默认缩级别值" maxlength="5">
                                    </div>
                                </div>
                                                              
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">FTP服务IP</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="ftpIP" placeholder="FTP服务IP">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">FTP服务端口</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="ftpPort" placeholder="FTP服务端口">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">地图数据</label>
                                    <div class="col-sm-9">
                                        <input type="checkbox" id="mapData-switch" name="mapDataSwitch"
                                               data-size="small" data-on-color="success" data-off-text="离线" data-on-text="在线">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">设备短信告警</label>
                                    <div class="col-sm-9">
                                        <input type="checkbox" id="devAlarmSms-switch" name="devAlarmSmsSwitch"
                                               data-size="small" data-on-color="success" data-off-text="关" data-on-text="开">
                                    </div>
                                </div>
                                
                            </form>
                        </div>
                        <div class="box-footer">
                            <div class="pull-right box-tools">
                                <button type="button" class="btn btn-flat bg-navy"  id="sysConf-refresh-btn" >
                                    <i class="fa fa-refresh"></i>刷新</button>
                                <button type="button" class="btn btn-flat bg-navy" id="sysConf-change-btn">
                                    <i class="fa fa-thumbs-up"></i>修改</button>
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
<script src="/plugins/messenger/messenger.js"></script>
<script src="/plugins/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.iframe-transport.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.fileupload.js"></script>
<script src="/plugins/jqueryValidation/jquery.validate.min.js"></script>
<script src="/plugins/jqueryValidation/localization/messages_zh.min.js"></script>
<script src="/plugins/jqueryValidation/additional-methods.min.js"></script>
<script src="/plugins/bootstrap-switch/bootstrap-switch.min.js"></script>
<script src="/js/validatorMethod.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/configuration.js"></script>

</body>
</html>
