<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 上号统计 </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/plugins/morris/morris.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="/css/efence.css">
    <link rel="stylesheet" href="/plugins/jquery-scrollbar/jquery.scrollbar.css">

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
                统计详情
                <small>${statistics.name}</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><span> 数据分析</span></li>
                <li><a href="/analysis/imsiStatistics">上号统计</a></li>
                <li class="active">统计详情</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
           <div class="row">
               <div class="col-xs-12">
                  <div class="box box-primary">
                      <div class="box-header">
                          <h3 class="box-title">输入信息</h3>
                      </div>
                      <div class="box-body">
                         <div class="row">
                             <div class="col-sm-6">
                                 <div class="form-horizontal">
                                     <div class="form-group">
                                         <label class="col-sm-4 control-label">名称</label>
                                         <div class="col-sm-8">
                                             <p class="form-control-static">${statistics.name}</p>
                                         </div>
                                     </div>
                                     <div class="form-group">
                                         <label class="col-sm-4 control-label">时间范围</label>
                                         <div class="col-sm-8">
                                             <p class="form-control-static">${statistics.rangeTime}</p>
                                         </div>
                                     </div>
                                 </div>
                             </div>
                             <div class="col-sm-6">
                                 <div class="form-horizontal">
                                     <div class="form-group">
                                         <label class="col-sm-4 control-label">类型</label>
                                         <div class="col-sm-8">
                                             <p class="form-control-static">${statistics.type}</p>
                                         </div>
                                     </div>
                                     <div class="form-group">
                                         <label class="col-sm-4 control-label">单位</label>
                                         <div class="col-sm-8">
                                             <p class="form-control-static">${statistics.unit}</p>
                                         </div>
                                     </div>
                                 </div>
                             </div>
                         </div>
                          <div class="row">
                              <div class="col-sm-6">
                                  <div class="form-horizontal">
                                      <div class="form-group">
                                          <label class="col-sm-4 control-label">统计项目</label>
                                          <div class="col-sm-8">
                                              <#list statistics.items as item>
                                                  <p class="form-control-static">${item.name}</p>
                                              </#list>
                                          </div>
                                      </div>
                                  </div>
                              </div>
                          </div>
                          <input id="statistics-task-id" type="hidden" name="taskId" value="${statistics.id?c}">
                      </div>
                  </div>
               </div>
           </div>
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-success">
                        <div class="box-header with-border">
                            <h3 class="box-title">统计结果(<small>总上号数:<strong id="sumTotal"></strong></small>)</h3>
                        </div>
                        <div class="box-body">
                            <div class="chart-scroll scrollbar-light">
                                <div id="statistics-barChart" style="height: 500px;"></div>
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
<script src="/plugins/jquery-scrollbar/jquery.scrollbar.min.js"></script>
<script src="/plugins/jquery-idleTimeout/store.min.js"></script>
<script src="/plugins/jquery-idleTimeout/jquery-idleTimeout.min.js"></script>
<script src="/plugins/sockjs/sockjs.min.js"></script>
<script src="/js/notifyHandle.js"></script>
<script src="/plugins/fastclick/fastclick.js"></script>
<script src="/plugins/morris/raphael-min.js"></script>
<script src="/plugins/morris/morris.js"></script>
<script src="/js/efence.js"></script>
<script src="/js/page/imsiStatisticsReport.js"></script>
</body>
</html>
