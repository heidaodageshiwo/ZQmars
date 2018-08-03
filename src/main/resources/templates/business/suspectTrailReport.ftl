<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 嫌疑人运动轨迹</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="/css/efence.css">
    <link rel="stylesheet" href="/bdmapApi2/bmap.css"/>
    <script type="text/javascript" src="/bdmapApi2/baidumap_offline_v2_load.js"></script>
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
                分析结果报告
                <small>嫌疑人运动轨迹</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 数据分析</span></li>
                <li><a href="/analysis/suspectTrail">嫌疑人运动轨迹</a></li>
                <li class="active">分析报告</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content" style="position:relative" >
            <div style="min-height: 800px; width:100%" id="suspectTrailReport-map"></div>
            <div class="map-btn-bar">
                <a class="btn btn-app" id="lusu-start-btn">
                    <i class="fa fa-play"></i>开始
                </a>
                <a class="btn btn-app" id="lusu-pause-btn">
                    <i class="fa fa-pause"></i>暂停
                </a>
                <a class="btn btn-app" id="lusu-stop-btn">
                    <i class="fa fa-stop"></i>停止
                </a>
                <a class="btn btn-app" id="whole-layout-btn">
                    <i class="fa fa-flag"></i>全览
                </a>
            </div>
            <div class="map-suspect-trail-content" >
                <div class="suspect-trail-header">
                    <p class="row"><span class="col-xs-3">名称：</span><span class="col-xs-9"><strong>${taskReport.taskName}</strong></span></p>
                    <p class="row"><span class="col-xs-3">时间范围：</span><span class="col-xs-9"><strong>${taskReport.rangeTime}</strong></span></p>
                </div>
                <div class="nav-tabs-custom suspect-trail-bg" >
                    <ul class="nav nav-tabs">
                        <#if taskReport.imsiList?? && taskReport.imsiList?size gt 0>
                            <#list taskReport.imsiList as trailImsi >
                                <li  <#if trailImsi_index=0>class="active"</#if>  ><a href="#tab_${trailImsi.imsi}" data-toggle="tab">${trailImsi.imsi}</a></li>
                            </#list>
                        </#if>
                    </ul>
                    <div class="tab-content no-padding suspect-trail-bg" >
                        <#if taskReport.imsiList?? && taskReport.imsiList?size gt 0>
                            <#list taskReport.imsiList as trailImsi >
                                <div class="tab-pane <#if trailImsi_index=0>active</#if> " id="tab_${trailImsi.imsi}">
                                    <div class="suspect-trail-body slimScrollDiv">
                                        <table id="suspectTrail-table-${trailImsi.imsi}" class="table" >
                                            <thead>
                                            <tr><th class="text-center">站点</th><th class="text-center">时间</th></tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </#list>
                        </#if>
                    </div>
                </div>
            </div>
            <input type="hidden" name="centerPointLng" id="center-point-lng" value="${taskReport.centerPoint.lng}">
            <input type="hidden" name="centerPointLat" id="center-point-lat" value="${taskReport.centerPoint.lat}">
            <input type="hidden" name="zoomValue" id="zoom-value" value="${taskReport.zoom}">
            <input type="hidden" name="mapOnline" id="map-online-sw" value="${taskReport.mapOnline}">
            <input type="hidden" name="taskId" id="task-id" value="${taskReport.taskId?c}">
            <input type="hidden" name="taskName" id="task-name" value="${taskReport.taskName}">
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
<script src="/js/efence.js"></script>
<script src="/js/map/RichMarker.js"></script>
<script src="/js/map/LuShu_min.js"></script>
<script src="/js/map/InfoBox.js"></script>
<script src="/js/map/siteMarker.js"></script>
<script src="/js/page/suspectTrailReport.js"></script>
</body>
</html>
