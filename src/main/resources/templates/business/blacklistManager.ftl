<!DOCTYPE html>
<html xmlns:margin="http://www.w3.org/1999/xhtml" xmlns:overflow="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 预警 </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/plugins/select2/select2.min.css">
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
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="/css/efence.css">
    <link rel="stylesheet" href="/plugins/daterangepicker/daterangepicker.css">
    <link rel="stylesheet" href="/plugins/zTree-v3/css/metroStyle/metroStyle.css">

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
                                    黑名单分组管理
                                    <small></small>
                                </h1>
                                <ol class="breadcrumb">
                                    <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                                    <li class="active"><a href="###"> 预警</a></li>
                                    <li class="active">黑名单分组管理</li>
                                </ol>
                            </section>

                            <!-- Main content -->
                            <section class="content">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="box box-primary ">
                                            <div class="box-header">
                                                <i class="fa fa-th-list"></i>
                                                <h3 class="box-title">黑名单分组列表</h3>
                                            </div>
                                            <div class="box-body">
                                                <table id="blacklist-table" class="table table-middle table-striped table-bordered table-hover">
                                                    <thead>
                                                        <tr>
                                                            <th></th>
                                                            <th>分组名称</th>
                                                            <th>用户</th>
                                                            <th>站点信息</th>
                                                            <th>预警接收人信息</th>
                                                            <th>开始时间</th>
                                                            <th>到期时间</th>
                                                            <th>名单人数</th>
                                                            <th>备注</th>
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


        <#---创建名单窗体-->
            <div class="modal fade" id="blacklist-dlg" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog">
                    <div class="modal-content" style="width: 900px;margin-left: -130px;" >
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title center-block">添加分组</h4>
                        </div>
                        <div class="modal-body">
                            <div  class="form-horizontal">
                                <form id="blacklist-form">
                                    <div class="form-group">
                                        <label for="name" class="col-sm-2 control-label">分组名称</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="name" name="name" placeholder="请填写分组名称" maxlength="16">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="site-ok" class="col-sm-2 control-label">检测站点</label>
                                        <div class="col-sm-10" >
                                            <button  type="button" class="btn btn-flat btn-sm btn-primary" id="site-ok" >请选择检测站点</button>
                                            <input type="text" disabled="disabled"  class="form-control" id="site-name" name="name" placeholder="请点击左侧按钮选择检测站点"
                                                    maxlength="16" style="margin-left: 120px;width: 433px;margin-top: -30px;height: 30px;">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="site-name" class="col-sm-2 control-label">所选站点</label>
                                        <div class="col-sm-10">
                                            <div id="siteName" style="overflow:auto;height: 40px;border:1px solid #CCC;"></div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">有效期时间</label>
                                        <div class="col-sm-10">
                                            <div class="input-group">
                                                <div class="input-group-addon">
                                                    <i class="fa fa-clock-o"></i>
                                                </div>
                                                <input name="alarm-range-time" type="text" class="form-control pull-right" id="alarm-range-time" PLACEHOLDER="请选择有效时间">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="remark" class="col-sm-2 control-label">备注</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="remark" name="remark" placeholder="备注" maxlength="200">
                                        </div>
                                    </div>
                                </form>
                               <div class="form-group">
                                    <label class="col-sm-2 control-label">通知人</label>
                                    <div class="col-sm-10">
                                        <table id="blacklist-receiver-table1" class="table table-striped table-hover table-bordered">
                                            <thead class="bg-gray">
                                                <tr><th>姓名</th><th>电话</th><th>邮件</th><th>操作</th></tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                        <form id="blacklist-receiver-form1">
                                            <table id="blacklist-add-receiver-table1"  class="table" border="0">
                                                <tbody>
                                                    <tr>
                                                        <td colspan="4" >
                                                            <a href="###" id="blacklist-add-receiver-btn1" ><b>+</b>添加</a>
                                                        </td>
                                                    </tr>
                                                    <tr class="hidden">
                                                        <td>
                                                            <input type="text" name="name" class="form-control"  placeholder="请输入姓名" ></input>
                                                        </td>
                                                        <td>
                                                            <input type="text" name="phone" class="form-control" placeholder="请输入手机号码"  ></input>
                                                        </td>
                                                        <td>
                                                            <input type="email" name="email" class="form-control" placeholder="请输入邮箱"  ></input>
                                                        </td>
                                                        <td>
                                                            <button  type="button" class="btn btn-flat btn-sm btn-primary" id="blacklist-add-receiver-ok1" >确定</button>
                                                        </td>
                                                        <td>
                                                            <button type="button" class="btn btn-flat btn-sm" id="blacklist-add-receiver-cancel1" >取消</button>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary btn-flat" id="blacklist-ok-btn" >确定</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>

            <#-- 选择站点窗体 -->
                <div class="modal fade" id="site-dlg">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content" style="width: 300px;margin-left: 280px;height: 532px;">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <h4 class="modal-title center-block">请选择检测站点</h4>
                            </div>
                            <div class="modal-body" style="width: 300px;height: 417px;overflow-y: scroll;">
                                <div id="treeDemo" class="ztree"></div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">关闭</button>
                                <button type="button" class="btn btn-primary btn-flat"  id="site_submit" >确定</button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
            <#-- 选择站点窗体 -->


            <div class="modal fade" id="receiver-dlg" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title center-block">本组黑名单人员信息</h4>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="id" value="">
                            <div class="row">
                                <div class="col-sm-12" style="margin-top: -15px;height: 585px;overflow-y: scroll;">
                                    <table id="receiver-table" class="table table-striped table-hover table-bordered" width="98%">
                                        <thead >
                                        <tr>
                                            <th>姓名</th>
                                            <th>IMSI号码</th>
                                            <th>身份证号码</th>
                                            <th>手机号码</th>
                                            <th>添加日期</th>
                                            <th>备注</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer" style="margin-top: -15px;">
                            <button type="button" class="btn btn-primary btn-flat" data-dismiss="modal">关闭</button>
                            <#--<button type="button" class="btn btn-primary btn-flat"  id="change-receiver-confirm" >确定</button>-->
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>

            <div class="modal fade" id="importblacklist-dlg" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title center-block">导入黑名单信息</h4>
                        </div>
                        <div class="modal-body">
                            <form id="importblacklist-form" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="licensurePath">黑名单文件</label>
                                    <div class="col-sm-10">
                                        <div class="input-group file-box">
                                            <input type="text" class="form-control" id="filename" name="filename" data-upload="display" >
                                            <input type="hidden" class="form-control" id="uploadFilename" name="uploadFilename">
                                            <span class="input-group-btn">
                                                <button class="btn btn-primary btn-flat" type="button"
                                                 onclick="document.getElementById('uploadFile').click()">名单导入</button>
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

                            </form>
                        </div>

                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>

            <div class="modal fade" id="addTarget" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog modal-lg" margin:0 auto>
                    <div class="modal-content" data-ng-style="width:1000px">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title center-block">添加黑名单人员</h4>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="id" value="">
                            <div class="row">
                                <div class="col-sm-12">
                                    <table id="addTarget-table" class="table table-striped table-hover table-bordered">
                                        <thead class="bg-gray">
                                        <tr><th>姓名</th><th>IMSI</th><th>备注</th><th>操作</th></tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                    <form id="receiver-form">
                                        <table id="add-receiver-table"  class="table" border="0">
                                            <tbody>
                                            <tr>
                                                <td colspan="4" >
                                                    <a href="###" id="add-receiver-btn" ><b>+</b>添加</a>
                                                </td>
                                            </tr>
                                            <tr class="hidden">
                                                <td>
                                                    <input type="text" name="name" class="form-control"  placeholder="请输入姓名" ></input>
                                                </td>
                                                <td>
                                                    <input type="text" name="imsi" class="form-control" placeholder="请输入IMSI"  ></input>
                                                </td>
                                                <td>
                                                    <input type="email" name="remark" class="form-control" placeholder="备注"  ></input>
                                                </td>
                                                <td>
                                                    <button  type="button" class="btn btn-flat btn-sm btn-primary" id="add-receiver-ok" >确定</button>
                                                    <button type="button" class="btn btn-flat btn-sm" id="add-receiver-cancel" >重置</button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary btn-flat" data-dismiss="modal">关闭</button>
                           <!-- <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary btn-flat"  id="change-receiver-confirm" >确定</button>-->
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>

            <#---创建名单窗体-->
                <div class="modal fade" id="addTarget-dlg" data-backdrop="static" data-keyboard="false">
                    <div class="modal-dialog" style='width:70%'>
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title center-block">添加黑名单</h4>
                            </div>
                            <div class="modal-body"  >
                                <div  class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">添加黑名单</label>
                                        <div class="col-sm-10">
                                            <table id="blacklist-receiver-table" class="table table-striped table-hover table-bordered">
                                                <thead class="bg-gray">
                                                    <tr><th>姓名</th><th>IMSI</th><th>身份证号码</th><th>手机号码</th><th>备注</th><th>操作</th></tr>
                                                </thead>
                                                <tbody>
                                                </tbody>
                                            </table>
                                            <form id="blacklist-receiver-form">
                                                <table id="blacklist-add-receiver-table"  class="table" border="0">
                                                    <tbody>
                                                    <tr>
                                                        <td colspan="4" >
                                                            <a href="###" id="blacklist-add-receiver-btn" ><b>+</b>添加</a>
                                                        </td>
                                                    </tr>
                                                    <tr class="hidden">
                                                        <td>
                                                            <input type="text" name="name" class="form-control"  placeholder="请输入姓名" ></input>
                                                        </td>
                                                        <td>
                                                            <input type="text" name="phone" class="form-control" placeholder="请输入IMSI"  ></input>
                                                        </td>
                                                        <td>
                                                            <input type="text" name="idCard" class="form-control" placeholder="请输入身份证号码"  ></input>
                                                        </td>
                                                        <td>
                                                            <input type="text" name="targetphone" class="form-control" placeholder="请输入手机号码"  ></input>
                                                        </td>
                                                        <td>
                                                            <input type="text" name="email" class="form-control" placeholder="请输入备注"  ></input>
                                                        </td>
                                                        <td>
                                                            <button  type="button" class="btn btn-flat btn-sm btn-primary" id="blacklist-add-receiver-ok" >确定</button>
                                                        </td>
                                                        <td>
                                                            <button type="button" class="btn btn-flat btn-sm" id="blacklist-add-receiver-cancel" >取消</button>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                                <button type="button" class="btn btn-primary btn-flat" id="addTarget-ok-btn" >确定</button>
                            </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>

    <#---修改分组信息窗体-->
        <div class="modal fade" id="update-dlg" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog">
                <div class="modal-content" style="width: 900px;margin-left: -164px;" >
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title center-block">修改分组信息</h4>
                    </div>
                    <div class="modal-body">
                        <div  class="form-horizontal">
                            <form id="update-form">
                                <div class="form-group">
                                    <label for="create_name" class="col-sm-2 control-label">分组名字</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="upname" name="name" placeholder="填写名单名字" maxlength="16">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="site-ok" class="col-sm-2 control-label">检测站点</label>
                                    <div class="col-sm-10" >
                                        <button  type="button" class="btn btn-flat btn-sm btn-primary" id="up-site-ok" >请选择检测站点</button>
                                        <input type="text" disabled="disabled"  class="form-control" id="up-site-name" name="name" placeholder="请点击左侧按钮选择检测站点"
                                               maxlength="16" style="margin-left: 120px;width: 433px;margin-top: -30px;height: 30px;">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="up-site-name" class="col-sm-2 control-label">所选站点</label>
                                    <div class="col-sm-10">
                                        <div id="up-siteName" style="overflow:auto;height: 40px;border:1px solid #CCC;"></div>
                                    </div>
                                </div>
                         <!--       <div class="form-group">
                                    <label for="site" class="col-sm-2 control-label">检测站点</label>
                                    <div class="col-sm-10">
                                        <select id="upsite" name="site"  multiple='multiple' class="form-control select2" style="width: 100%;" data-placeholder="请选择检测站点">
                                        </select>
                                    </div>
                                </div>-->
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">有效期时间</label>
                                    <div class="col-sm-10">
                                        <div class="input-group">
                                            <div class="input-group-addon">
                                                <i class="fa fa-clock-o"></i>
                                            </div>
                                            <input name="alarm-range-time" type="text" class="form-control pull-right" id="up-alarm-range-time" PLACEHOLDER="请选择有效时间">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="remark" class="col-sm-2 control-label">备注</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="upremark" name="remark" placeholder="备注" maxlength="200">
                                    </div>
                                </div>
                            </form>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">通知人</label>
                                <div class="col-sm-10">
                                    <table id="update-table" class="table table-striped table-hover table-bordered">
                                        <thead class="bg-gray">
                                            <tr><th>姓名</th><th>电话</th><th>邮件</th><th>操作</th></tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                    <form id="update-receiver-form">
                                        <table id="update-receiver-table"  class="table" border="0">
                                            <tbody>
                                            <tr>
                                                <td colspan="4" >
                                                    <a href="###" id="update-receiver-btn" ><b>+</b>添加通知人信息</a>
                                                </td>
                                            </tr>
                                            <tr class="hidden">
                                                <td>
                                                    <input type="text" name="name" class="form-control"  placeholder="请输入姓名" ></input>
                                                </td>
                                                <td>
                                                    <input type="text" name="phone" class="form-control" placeholder="请输入手机号码"  ></input>
                                                </td>
                                                <td>
                                                    <input type="email" name="email" class="form-control" placeholder="请输入邮箱"  ></input>
                                                </td>
                                                <td>
                                                    <button  type="button" class="btn btn-flat btn-sm btn-primary" id="update-ok" >确定</button>
                                                </td>
                                                <td>
                                                    <button type="button" class="btn btn-flat btn-sm" id="update-cancel" >取消</button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary btn-flat" id="update-ok-btn" >提交</button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>

        <#-- 选择站点窗体 -->
            <div class="modal fade" id="up-site-dlg">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content" style="width: 300px;margin-left: 280px;height: 570px;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title center-block">请选择检测站点</h4>
                        </div>
                        <div class="modal-body" style="width: 300px;height: 455px;overflow-y: scroll;">
                            <div id="up-treeDemo" class="ztree"></div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default pull-left btn-flat" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary btn-flat"  id="up-site_submit" >确定</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
        <#-- 选择站点窗体 -->

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
<script src="/js/efence.js"></script>
<script src="/plugins/messenger/messenger.js"></script>
<script src="/plugins/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.iframe-transport.js"></script>
<script src="/plugins/jQuery-File-Upload/js/jquery.fileupload.js"></script>
<script src="/plugins/daterangepicker/moment.min.js"></script>
<script src="/plugins/daterangepicker/daterangepicker.js"></script>
<script src="/plugins/zTree-v3/js/jquery.ztree.all.js"></script>
<script src="/js/page/blacklist.js"></script>
<script src="/plugins/select2/select2.full.min.js"></script>
<script src="/plugins/select2/i18n/zh-CN.js"></script>
<script src="/m16/js/dataTables.default.js"></script>
</body>
</html>
