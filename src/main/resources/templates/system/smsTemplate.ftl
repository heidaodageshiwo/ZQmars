<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 短信模板 </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger-theme-air.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/css/skins/_all-skins.min.css">
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
                短信模板
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 系统维护</span></li>
                <li><span> 短信通知</span></li>
                <li class="active"> 模板管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3">
                    <div class="box box-primary">
                        <div class="box-header">
                            <h3 class="box-title">模板</h3>
                        </div>
                        <div class="box-body pad">
                            <form id="sms-template-form">
                                <textarea id="sms-template" name="smsTemplate" rows="10" cols="80" class="form-control" style="resize: none;"
                                          placeholder="模板参考：'尊敬的{%user%}, 在{%cap_time%}时间,目标{%target_name%}imsi号为{%imsi%}出现于{%site_address%}位置'"></textarea>
                            </form>
                        </div>
                        <div class="box-footer">
                            <div class="pull-right box-tools">
                                <button type="button" class="btn btn-flat bg-navy"  id="sms-template-refresh-btn" >
                                    <i class="fa fa-refresh"></i>刷新</button>
                                <button type="button" class="btn btn-flat bg-navy" id="sms-template-change-btn">
                                    <i class="fa fa-thumbs-up"></i>修改</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3">
                    <div style="padding: 20px 10px 10px; width:100%;color:#a5a5a5">
                        <p>模板替换符号:</p>
                        <div style="padding-left: 30px; width:100%;">
                            <p>{%imsi%}: imsi替代符</p>
                            <p>{%site_name%}: 站点名称替代符</p>
                            <p>{%site_address%}: 站点地址替代符</p>
                            <p>{%site_province%}: 站点所在省替代符</p>
                            <p>{%site_city%}: 站点所在市替代符</p>
                            <p>{%site_town%}: 站点所在区县替代符</p>
                            <p>{%site_longti%}: 站点经度替代符</p>
                            <p>{%site_lati%}: 站点纬度替代符</p>
                            <p>{%target_name%}: 目标名称替代符</p>
                            <p>{%target_id%}: 目标身份证号替代符</p>
                            <p>{%target_phone%}: 目标手机号替代符</p>
                            <p>{%operator%}: 运营商替代符</p>
                            <p>{%group_name%}: 黑名单分组名替代符</p>
                            <p>{%cap_time%}: 终端设备捕获时间替代符</p>
                            <p>{%user%}: 消息接收人替代符</p>
<#--





                            <p>{%user%}: 通知人名替代符</p>
                            <p>{%time%}: 目标捕获时间替代符</p>
                            <p>{%target%}: 目标名称替代符</p>
                            <p>{%imsi%}: imsi号替代符</p>
                            <p>{%location%}: 目标捕获地址替代符</p>-->
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
<script src="/js/efence.js"></script>
<script src="/js/page/smsTemplate.js"></script>

</body>
</html>
