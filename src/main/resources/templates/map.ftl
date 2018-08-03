<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 设备分布图</title>
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
<#include "./comm/headerBar.ftl">
<#-- load left side menus sidebar -->
<#include "./comm/sideMenu.ftl">

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                设备分布图
                <small>地图</small>
            </h1>
            <ol class="breadcrumb">
                <li ><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active">设备分布图</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content" style="position:relative">
            <div style="min-height: 800px; width:100%" id="device-map"></div>
            <div class="map-total-info">
                <span class="info-item">
                    <span class="total-name text-orange">上号总数</span>
                    <span class="total-value text-maroon text-bold" id="imsi-total">${statistics.system.ueTotal}</span>
                </span>
                <span class="info-item">
                    <span class="total-name text-orange">黑名单预警总数</span>
                    <span class="total-value text-maroon text-bold" id="blacklist-warring-total">${statistics.system.blacklistTotal}</span>
                </span>
                <span class="info-item">
                    <span class="total-name text-orange">归属地预警总数</span>
                    <span class="total-value text-maroon text-bold" id="homeownership-warring-total">${statistics.system.homeOwnershipTotal}</span>
                </span>
            </div>
            <div class="map-btn-bar">
                <a class="btn btn-app" id="whole-layout-btn">
                    <i class="fa fa-flag"></i>全览
                </a>
                <a class="btn btn-app" id="search-btn">
                    <i class="fa fa-search"></i>查找
                </a>
            </div>
            <div class="map-search" id="search-input">
               <input type="text" placeholder="请输入站点名称"><a href="javascript:void(0)" class="btn">确认</a>
            </div>
            <input type="hidden" name="centerPointLng" id="center-point-lng" value="${map.centerPoint.lng}">
            <input type="hidden" name="centerPointLat" id="center-point-lat" value="${map.centerPoint.lat}">
            <input type="hidden" name="zoomValue" id="zoom-value" value="${map.zoom}">
            <input type="hidden" name="mapOnline" id="map-online-sw" value="${map.mapOnline}">
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

<#-- load footer bar -->
<#include "./comm/footer.ftl">

</div>
<#-- ./wrapper -->

<script src="/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/plugins/jQueryUI/jquery-ui.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script src="/plugins/jquery-idleTimeout/store.min.js"></script>
<script src="/plugins/jquery-idleTimeout/jquery-idleTimeout.min.js"></script>
<script src="/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<script src="/plugins/sockjs/sockjs.min.js"></script>
<script src="/js/notifyHandle.js"></script>
<script src="/plugins/fastclick/fastclick.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/map/RichMarker.js"></script>
<script src="/js/map/InfoBox.js"></script>
<script src="/js/map/siteMarker.js"></script>
<script src="/js/map/map.js"></script>

</body>
</html>
