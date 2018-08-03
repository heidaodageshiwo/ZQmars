<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title> ${systemInfo.projectName } | 设备维护 </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/plugins/jQueryUI/jquery-ui.min.css">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <link rel="stylesheet" href="/css/ionicons.min.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger.css">
    <link rel="stylesheet" href="/plugins/messenger/messenger-theme-air.css">
    <link rel="stylesheet" href="/plugins/bootstrap-switch/bootstrap-switch.min.css">
    <link rel="stylesheet" href="/plugins/select2/select2.min.css">
    <link rel="stylesheet" href="/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="/css/efence.css">
    <link rel="stylesheet" href="/plugins/zTree-v3/css/deviceTreeStyle/metroStyle.css" >
</head>
<body class="hold-transition skin-blue sidebar-mini">
<script type="text/javascript">
    var count=120;
    function settime(val) {
       // alert(123);
        if (count == 0) {
            val.removeAttribute("disabled");
           // downloadLog
            count = 120;
            $("#downloadLog").text("获取日志");
        } else {
            val.setAttribute("disabled", true);
         //  var c= $("#downloadLog").val();
           //alert(c);
          //  val.value="获取日志(" + count + ")";

            $("#downloadLog").text("获取日志(" + count + "s)");

            count--;
            setTimeout(function() {
                settime(val)
            },1000)
        }

    }
</script>
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
                设备维护
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active">设备维护</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-4">
                    <div class="box box-success">
                        <div class="box-header with-border">
                            <h3 class="box-title">设备列表</h3>
                        </div>
                        <div class="box-body" style="min-height: 820px">
                            <div class="row" style="margin-bottom: 20px">
                                <div class="col-md-8">
                                    <span class="" style="color: #090"><i class="fa fa-fw fa-circle"></i>运行</span>
                                    <span class="" style="color: #00ccff"><i class="fa fa-fw fa-circle"></i>即将过期</span>
                                    <span class="" style="color: #ff0000"><i class="fa fa-fw fa-circle"></i>已过期</span>
                                    <span class="" style="color: #ff851b"><i class="fa fa-fw fa-circle"></i>有告警</span>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div id="devices-online-tree" class="ztree"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">设备配置</h3>
                            <small><em id="device-info"></em></small>
                            <input type="hidden" name="deviceSN" id="device-sn">
                        </div>
                        <div class="box-body">
                            <div class="row">
                                <section class="col-lg-12">
                                    <div class="panel box box-info">
                                        <div class="box-body">
                                            <div class="row">
                                                <div class="col-sm-4">
                                                    <div class="text-center">
                                                        <input id="rf-switch" type="checkbox" checked />
                                                    </div>
                                                </div>
                                                <div class="col-sm-2">
                                                    <div class="text-center">
                                                        <button id="reboot-btn" class="btn btn-flat btn-warning" data-message="reboot" data-param="" >重启设备</button>
                                                    </div>
                                                </div>
                                                <div class="col-sm-2">
                                                    <div class="text-center">
                                                        <button id="reset-factory" class="btn btn-flat btn-warning"  data-message="resetFactory" data-param=""   >恢复出厂设置</button>
                                                    </div>
                                                </div>
                                                <div class="col-sm-2">
                                                    <div class="text-center">
                                                        <button id="downloadLog" class="btn btn-flat btn-primary" data-message="reboot" data-param="" onclick="settime(this)" >获取日志</button>
                                                    </div>
                                                </div>
                                                <div class="col-sm-2">
                                                    <div class="text-center">
                                                        <button id="exportLog" class="btn btn-flat btn-primary" data-message="reboot" data-param="" >下载日志</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="box-group" id="accordion">
                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseRF">
                                                        射频参数
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseRF" class="panel-collapse collapse in"
                                                 data-get-action-name="getRFParam" data-set-action-name="setRFParam" >
                                                <div class="box-body">
                                                    <form class="form-horizontal" role="form">
                                                        <div class="form-group">
                                                            <div class="col-sm-6 col-sm-offset-2">
                                                                <div class="pull-right">
                                                                    <button type="button" data-param-get="#collapseRF" class="btn btn-flat bg-navy ">刷新</button>
                                                                    <button type="button" data-param-set="#collapseRF"  class="btn btn-flat bg-navy ">配置</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="rf-param-rfenable" class="col-sm-2 control-label">射频使能</label>
                                                            <div class="col-sm-6">
                                                                <input type="checkbox" class="param-switch" id="rf-param-rfenable" name="rfenable"
                                                                       data-size="small" data-on-color="success" data-off-text="关" data-on-text="开">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="rf-param-fastconfigearfcn" class="col-sm-2 control-label">快速配置频点</label>
                                                            <div class="col-sm-6">
                                                                <input type="checkbox" class="param-switch" id="rf-param-fastconfigearfcn" name="fastconfigearfcn"
                                                                       data-size="small" data-on-color="success" data-off-text="关" data-on-text="开" >
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="rf-param-framestrucuretype" class="col-sm-2 control-label">帧结构</label>
                                                            <div class="col-sm-6">
                                                                <select id="rf-param-framestrucuretype" name="framestrucuretype" class="form-control select2 form-select2" style="width: 100%;">
                                                                    <option value="0" selected="selected">TDD</option>
                                                                    <option value="1">FDD</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="rf-param-eutraband" class="col-sm-2 control-label">频带号</label>
                                                            <div class="col-sm-6">
                                                                <select id="rf-param-eutraband" name="eutraband" class="form-control select2 form-select2" style="width: 100%;">
                                                                    <option value="38" selected="selected">38</option>
                                                                    <option value="39">39</option>
                                                                    <option value="40">40</option>
                                                                    <option value="41">41</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="rf-param-ulearfcn" class="col-sm-2 control-label">上行信道号</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="rf-param-ulearfcn"  name="ulearfcn"
                                                                       data-inputmask="'alias': 'decimal'" data-mask value=""
                                                                       data-min-value="37750" data-max-value="38249" placeholder="37750~38249" >
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="rf-param-dlearfcn" class="col-sm-2 control-label">下行信道号</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="rf-param-dlearfcn" name="dlearfcn"
                                                                       data-inputmask="'alias': 'decimal'" data-mask value=""
                                                                       data-min-value="37750" data-max-value="38249" placeholder="37750~38249">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="rf-param-subframeassinment" class="col-sm-2 control-label">上下行时隙配置</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="rf-param-subframeassinment" name="subframeassinment"
                                                                       data-inputmask="'mask': '9'" data-mask
                                                                       data-min-value="0" data-max-value="6" placeholder="0~6">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="rf-param-specialsubframepatterns" class="col-sm-2 control-label">特殊子帧配置</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="rf-param-specialsubframepatterns" name="specialsubframepatterns"
                                                                       data-inputmask="'mask': '9'" data-mask
                                                                       data-min-value="0" data-max-value="8" placeholder="0~8">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="rf-param-ulbandwidth" class="col-sm-2 control-label">上行带宽</label>
                                                            <div class="col-sm-6">
                                                                <select id="rf-param-ulbandwidth"  name="ulbandwidth"  class="form-control select2 form-select2" style="width: 100%;">
                                                                    <option value="25" selected="selected">5M</option>
                                                                    <option value="50">10M</option>
                                                                    <option value="75">15M</option>
                                                                    <option value="100">20M</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="rf-param-dlbandwidth" class="col-sm-2 control-label">下行带宽</label>
                                                            <div class="col-sm-6">
                                                                <select id="rf-param-dlbandwidth" name="dlbandwidth"  class="form-control select2 form-select2" style="width: 100%;">
                                                                    <option value="25" selected="selected">5M</option>
                                                                    <option value="50">10M</option>
                                                                    <option value="75">15M</option>
                                                                    <option value="100">20M</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="rf-param-tx1powerattenuation" class="col-sm-2 control-label">TX1功率衰减值</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="rf-param-tx1powerattenuation" name="tx1powerattenuation"
                                                                       data-inputmask="'alias': 'decimal'" data-mask
                                                                       data-min-value="-300" data-max-value="7000" placeholder="-300~7000" >
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="rf-param-tx2powerattenuation" class="col-sm-2 control-label">TX2功率衰减值</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="rf-param-tx2powerattenuation" name="tx2powerattenuation"
                                                                       data-inputmask="'alias': 'decimal'" data-mask
                                                                       data-min-value="-300" data-max-value="7000" placeholder="-300~7000">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="rf-param-rfchoice" class="col-sm-2 control-label">PA选择</label>
                                                            <div class="col-sm-6">
                                                                <select id="rf-param-rfchoice" name="rfchoice"  class="form-control select2 form-select2" style="width: 100%;">
                                                                    <option value="1" >9361单板</option>
                                                                    <option value="2">BAND38_250mW</option>
                                                                    <option value="3">BAND3_20W</option>
                                                                    <option value="4">BAND39_20W</option>
                                                                    <option value="5">BAND38_10W</option>
                                                                    <option value="6">空闲</option>
                                                                    <option value="7">30W</option>
                                                                    <option value="8" selected="selected">40W</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseFunc">
                                                        功能参数
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseFunc" class="panel-collapse collapse"
                                                 data-get-action-name="getFuncParam" data-set-action-name="setFuncParam" >
                                                <div class="box-body">
                                                    <form class="form-horizontal" role="form">
                                                        <div class="form-group">
                                                            <div class="col-sm-6 col-sm-offset-2">
                                                                <div class="pull-right">
                                                                    <button type="button" data-param-get="#collapseFunc" class="btn btn-flat bg-navy">刷新</button>
                                                                    <button type="button" data-param-set="#collapseFunc" class="btn btn-flat bg-navy">配置</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="func-param-paramcc" class="col-sm-2 control-label">移动国家码</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="func-param-paramcc" name="paramcc"
                                                                       data-inputmask="'mask': '999'" data-mask
                                                                       data-min-value="0" data-max-value="999"  placeholder="000~999, 中国为460">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="func-param-paramnc" class="col-sm-2 control-label">移动网号</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="func-param-paramnc" name="paramnc"
                                                                       data-inputmask="'mask': '99'" data-mask
                                                                       data-min-value="0" data-max-value="99" placeholder="00~99，00-移动，01-联通，11-电信">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="func-param-parapcino" class="col-sm-2 control-label">物理层小区号</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="func-param-parapcino" name="parapcino"
                                                                       data-inputmask="'alias': 'decimal'" data-mask
                                                                       data-min-value="0" data-max-value="503" placeholder="0~503">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="func-param-paraperi" class="col-sm-2 control-label">上号周期</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="func-param-paraperi" name="paraperi"
                                                                       data-inputmask="'alias': 'decimal'" data-mask
                                                                       data-min-value="0" data-max-value="4294967295" placeholder="0~4294967295">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="func-param-circletime" class="col-sm-2 control-label">周期扫号时间</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="func-param-circletime" name="circletime"
                                                                       data-inputmask="'alias': 'decimal'" data-mask
                                                                       data-min-value="0" data-max-value="255" placeholder="0~255">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="func-param-controlrange" class="col-sm-2 control-label">控制范围</label>
                                                            <div class="col-sm-6">
                                                                <select id="func-param-controlrange" name="controlrange" class="form-control select2 form-select2" style="width: 100%;">
                                                                    <option value="1"  selected="selected">黑名单</option>
                                                                    <option value="2">全部</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="func-param-inandouttype " class="col-sm-2 control-label">控制类型</label>
                                                            <div class="col-sm-6">
                                                                <select id="func-param-inandouttype" name="inandouttype" class="form-control select2 form-select2" style="width: 100%;">
                                                                    <option value="0"  selected="selected">周期扫号</option>
                                                                    <option value="1">控制范围内，允许反复接入</option>
                                                                    <option value="2">重定向频点</option>
                                                                    <option value="4">接入一次后，按照周期再拉回来</option>
                                                                    <option value="12">重定向频点R13</option>
                                                                    <option value="14">接入一次后，按照周期再拉回来R13</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="func-param-arfcn" class="col-sm-2 control-label">通用定向频点</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="func-param-arfcn" name="arfcn"
                                                                       data-inputmask="'alias': 'decimal'" data-mask
                                                                       data-min-value="0" data-max-value="65535" placeholder="0~65535" >
                                                            </div>
                                                        </div>


                                                        <div class="form-group">
                                                            <label for="func-param-arfcn1" class="col-sm-2 control-label">移动定向频点</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="func-param-arfcn1" name="arfcn1"
                                                                       data-inputmask="'alias': 'decimal'" data-mask
                                                                       data-min-value="0" data-max-value="65535" placeholder="0~65535" >
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="func-param-arfcn2" class="col-sm-2 control-label">联通定向频点</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="func-param-arfcn2" name="arfcn2"
                                                                       data-inputmask="'alias': 'decimal'" data-mask
                                                                       data-min-value="0" data-max-value="65535" placeholder="0~65535" >
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="func-param-arfcn3" class="col-sm-2 control-label">电信定向频点</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="func-param-arfcn3" name="arfcn3"
                                                                       data-inputmask="'alias': 'decimal'" data-mask
                                                                       data-min-value="0" data-max-value="65535" placeholder="0~65535" >
                                                            </div>
                                                        </div>




                                                        <div class="form-group">
                                                            <label for="func-param-boolstart" class="col-sm-2 control-label">上电后是否自动建立小区</label>
                                                            <div class="col-sm-6">
                                                                <input type="checkbox" class="param-switch" id="func-param-boolstart" name="boolstart"
                                                                       data-size="small" data-on-color="success" data-off-text="关" data-on-text="开" >
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseAglignGPS">
                                                        帧偏设置
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseAglignGPS" class="panel-collapse collapse"
                                                 data-get-action-name="getAlignGPSParam" data-set-action-name="setAlignGPSParam" >
                                                <div class="box-body">
                                                    <form class="form-horizontal" role="form">
                                                        <div class="form-group">
                                                            <div class="col-sm-6 col-sm-offset-2">
                                                                <div class="pull-right">
                                                                    <button type="button" data-param-get="#collapseAglignGPS" class="btn btn-flat bg-navy">刷新</button>
                                                                    <button type="button" data-param-set="#collapseAglignGPS"  class="btn btn-flat bg-navy">配置</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="gps-param-offset" class="col-sm-2 control-label">偏移提前量</label>
                                                            <div class="col-sm-6">
                                                                <select id="gps-param-offset" name="offset" class="form-control select2 form-select2" style="width: 100%;">
                                                                    <option value="0"  selected="selected">0</option>
                                                                    <option value="69297">69297</option>
                                                                    <option value="70000">70000</option>
                                                                    <option value="96480">96480</option>
                                                                    <option value="101720">101720</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="gps-param-offset" class="col-sm-2 control-label">双载波配对板子偏移提前量</label>
                                                            <div class="col-sm-6">
                                                                <select id="gps-param-friendoffset" name="friendoffset" class="form-control select2 form-select2" style="width: 100%;">
                                                                    <option value="0"  selected="selected">0</option>
                                                                    <option value="69297">69297</option>
                                                                    <option value="70000">70000</option>
                                                                    <option value="96480">96480</option>
                                                                    <option value="101720">101720</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <!--
                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapsePA">
                                                        PA设置
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapsePA" class="panel-collapse collapse"
                                                 data-get-action-name="getPAParam" data-set-action-name="setPAParam" >
                                                <div class="box-body">
                                                    <form class="form-horizontal" role="form">
                                                        <div class="form-group">
                                                            <div class="col-sm-6 col-sm-offset-2">
                                                                <div class="pull-right">
                                                                    <button type="button" data-param-get="#collapsePA" class="btn btn-flat bg-navy">刷新</button>
                                                                    <button type="button" data-param-set="#collapsePA"  class="btn btn-flat bg-navy">配置</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="pa-param-powerattenuation" class="col-sm-2 control-label">功率衰减值</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="pa-param-powerattenuation" name="powerattenuation"
                                                                       data-inputmask="'alias': 'decimal'" data-mask
                                                                       data-min-value="0" data-max-value="30" placeholder="0~30" >
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        -->
                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseControlRange">
                                                        精准控制压制范围
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseControlRange" class="panel-collapse collapse"
                                                 data-get-action-name="getControlRangeParam" data-set-action-name="setControlRangeParam" >
                                                <div class="box-body">
                                                    <form class="form-horizontal" role="form">
                                                        <div class="form-group">
                                                            <div class="col-sm-6 col-sm-offset-2">
                                                                <div class="pull-right">
                                                                    <button type="button" data-param-get="#collapseControlRange" class="btn btn-flat bg-navy">刷新</button>
                                                                    <button type="button" data-param-set="#collapseControlRange"  class="btn btn-flat bg-navy">配置</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="ctrlRange-param-open" class="col-sm-2 control-label">是否精准控制压制范围</label>
                                                            <div class="col-sm-6">
                                                                <input type="checkbox" class="param-switch" id="ctrlRange-param-open" name="open"
                                                                       data-size="small" data-on-color="success" data-off-text="关" data-on-text="开">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="ctrlRange-param-lev" class="col-sm-2 control-label">电平</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="ctrlRange-param-lev" name="lev"
                                                                       data-inputmask="'alias': 'decimal'" data-mask
                                                                       data-min-value="-70" data-max-value="-22" placeholder="-70~-22" >
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseEnbId">
                                                        配置基站ID
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseEnbId" class="panel-collapse collapse"
                                                 data-get-action-name="getENBIdParam" data-set-action-name="setENBIdParam" >
                                                <div class="box-body">
                                                    <form class="form-horizontal" role="form">
                                                        <div class="form-group">
                                                            <div class="col-sm-6 col-sm-offset-2">
                                                                <div class="pull-right">
                                                                    <button type="button" data-param-get="#collapseEnbId" class="btn btn-flat bg-navy">刷新</button>
                                                                    <button type="button" data-param-set="#collapseEnbId"  class="btn btn-flat bg-navy">配置</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="enbid-param-identity" class="col-sm-2 control-label">基站标识</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="enbid-param-identity" name="identity"
                                                                       data-inputmask="'alias': 'decimal'" data-mask
                                                                       data-min-value="0" data-max-value="999" placeholder="0~999" >
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseDataSend">
                                                        基站数据发送方式
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseDataSend" class="panel-collapse collapse"
                                                 data-get-action-name="getDataSendCfgParam" data-set-action-name="setDataSendCfgParam" >
                                                <div class="box-body">
                                                    <form class="form-horizontal" role="form">
                                                        <div class="form-group">
                                                            <div class="col-sm-6 col-sm-offset-2">
                                                                <div class="pull-right">
                                                                    <button type="button" data-param-get="#collapseDataSend"  class="btn btn-flat bg-navy">刷新</button>
                                                                    <button type="button" data-param-set="#collapseDataSend" class="btn btn-flat bg-navy">配置</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="datasend-param-realtimesend" class="col-sm-2 control-label">数据是否实时上报</label>
                                                            <div class="col-sm-6">
                                                                <input type="checkbox" class="param-switch" id="datasend-param-realtimesend" name="realtimesend"
                                                                       data-size="small" data-on-color="success" data-off-text="关" data-on-text="开" >
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="datasend-param-interalmin" class="col-sm-2 control-label">数据上报分钟的间隔数</label>
                                                            <div class="col-sm-6">
                                                                <select id="datasend-param-interalmin" name="interalmin" class="form-control select2 form-select2" style="width: 100%;">
                                                                    <option value="1"  >1分钟</option>
                                                                    <option value="2" selected="selected">2分钟</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="datasend-param-uecountsend" class="col-sm-2 control-label">Imsi满多少个时数据上报</label>
                                                            <div class="col-sm-6">
                                                                <select id="datasend-param-uecountsend" name="uecountsend" class="form-control select2 form-select2" style="width: 100%;">
                                                                    <option value="50"  >50</option>
                                                                    <option value="100" selected="selected">100</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseServerCnf">
                                                        配置服务器地址
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseServerCnf" class="panel-collapse collapse"
                                                 data-get-action-name="getServerCnfParam" data-set-action-name="setServerCnfParam" >
                                                <div class="box-body">
                                                    <form class="form-horizontal" role="form">
                                                        <div class="form-group">
                                                            <div class="col-sm-6 col-sm-offset-2">
                                                                <div class="pull-right">
                                                                    <button type="button" data-param-get="#collapseServerCnf" class="btn btn-flat bg-navy">刷新</button>
                                                                    <button type="button" data-param-set="#collapseServerCnf"  class="btn btn-flat bg-navy">配置</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="enbid-param-identity" class="col-sm-2 control-label">主服务类型</label>
                                                            <div class="col-sm-6">
                                                                <select id="serverAddrType" name="serverAddrType" class="form-control select2 form-select2" style="width: 100%;">
                                                                    <option value="0" >IP</option>
                                                                    <option value="1" selected="selected">域名</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="enbid-param-identity" class="col-sm-2 control-label">IP地址或域名</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="serverAddr" name="serverAddr">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="enbid-param-identity" class="col-sm-2 control-label">端口号</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="serverPort" name="serverPort">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="enbid-param-identity" class="col-sm-2 control-label">尝试连接时间</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="serverTryConnectionTime" name="serverTryConnectionTime"
                                                                   data-inputmask="'alias': 'decimal'" data-mask
                                                                   data-min-value="0" data-max-value="999" placeholder="0~999" >
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="enbid-param-identity" class="col-sm-2 control-label">是否开启备用服务器</label>
                                                            <div class="col-sm-6">
                                                                <input type="checkbox" class="param-switch" id="secondServerEnable" name="secondServerEnable"
                                                                data-size="small" data-on-color="success" data-off-text="关" data-on-text="开" >
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="enbid-param-identity" class="col-sm-2 control-label">备用服务类型</label>
                                                            <div class="col-sm-6">
                                                                <select id="secondServerAddrType" name="secondServerAddrType" class="form-control select2 form-select2" style="width: 100%;">
                                                                    <option value="0" >IP</option>
                                                                    <option value="1" selected="selected">域名</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="enbid-param-identity" class="col-sm-2 control-label">备用服务器IP地址或域名</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="secondServerAddr" name="secondServerAddr">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="enbid-param-identity" class="col-sm-2 control-label">备用服务器端口号</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="secondServerPort" name="secondServerPort">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="enbid-param-identity" class="col-sm-2 control-label">备用服务器尝试连接时间</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="secondServerConnectionTime" name="secondServerConnectionTime"
                                                                       data-inputmask="'alias': 'decimal'" data-mask
                                                                       data-min-value="0" data-max-value="999" placeholder="0~999" >
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapsePoll">
                                                        轮询不同频点
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapsePoll" class="panel-collapse collapse"
                                                 data-get-action-name="getENBIdParam" data-set-action-name="setENBIdParam" >
                                                <div class="box-body">
                                                    <form class="form-horizontal" role="form">
                                                        <div class="form-group">
                                                            <div class="col-sm-6 col-sm-offset-2">
                                                                <div class="pull-right">
                                                                    <button type="button" data-param-get="#collapseEnbId" class="btn btn-flat bg-navy">刷新</button>
                                                                    <button type="button" data-param-set="#collapseEnbId"  class="btn btn-flat bg-navy">配置</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="enbid-param-identity" class="col-sm-2 control-label">服务器地址</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="enbid-param-identity" name="identity">
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseSrx">
                                                        配置异频下行频点
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseSrx" class="panel-collapse collapse"
                                                 data-get-action-name="getSrxParam" data-set-action-name="setSrxParam" >
                                                <div class="box-body">
                                                    <form class="form-horizontal" role="form">
                                                        <div class="form-group">
                                                            <div class="col-sm-6 col-sm-offset-2">
                                                                <div class="pull-right">
                                                                    <button type="button" data-param-get="#collapseSrx" class="btn btn-flat bg-navy">刷新</button>
                                                                    <button type="button" data-param-set="#collapseSrx"  class="btn btn-flat bg-navy">配置</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="enbid-param-identity" class="col-sm-2 control-label">异频下行频点</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control" id="srx-param-DlEarfcn" name="DlEarfcn"
                                                                   data-inputmask="'alias': 'decimal'" data-mask
                                                                   data-min-value="37750" data-max-value="41239" placeholder="" >
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="enbid-param-identity" class="col-sm-2 control-label"></label>
                                                            <div class="col-sm-6">
                                                                范围说明： TDD<br>
                                                                <table border="1" cellspacing="0">
                                                                    <tr><td style="width:80px;">设备类型</td><td style="width:150px;">取值范围</td></tr>
                                                                    <tr><td>Band38：</td><td>37750-38249</td></tr>
                                                                    <tr><td>Band39：</td><td>38250-38649</td></tr>
                                                                    <tr><td>Band40：</td><td>38650-38849<br>38850-39349<br>39350-39549<br></td></tr>
                                                                    <tr><td>Band41：</td><td>40240-40439<br>40440-41039<br>41040-41239<br></td></tr>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </section>
                            </div>
                        </div>
                        <div class="overlay">
                            <i class="fa text-orange">请选择设备</i>
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
<script src="/plugins/select2/select2.full.min.js"></script>
<script src="/plugins/select2/i18n/zh-CN.js"></script>
<script src="/plugins/messenger/messenger.js"></script>
<script src="/plugins/bootbox/bootbox.min.js"></script>
<script src="/plugins/bootstrap-switch/bootstrap-switch.min.js"></script>
<script src="/plugins/input-mask/jquery.inputmask.js"></script>
<script src="/plugins/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script src="/plugins/zTree-v3/js/jquery.ztree.all.min.js"></script>
<script src="/js/efence.js"></script>
<!-- <script src="/js/page/deviceOam.js"></script> -->
<script src="/js/page/deviceOamNew.js"></script>
</body>
</html>
