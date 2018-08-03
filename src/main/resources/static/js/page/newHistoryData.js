/**
 * Created by jiangqi.yang  on 2016/10/27.
 */

var params = new Array();
var zTreeSiteSn = new Array();
$(function () {
    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

    $("#capture-operator").select2({
        language: "zh-CN",
        minimumResultsForSearch: Infinity
    });

    function formatSelect ( data ) {
        if (!data.id) { return data.text; }
        var $pDom = $(
            '<p><span>' + data.text + '</span><span class="pull-right text-gray">' + data.id + '</span></p>'
        );
        return $pDom;
    };

    $.ajax({
        type: "post",
        url: "/util/getCityCodes",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var cityData = $.map( data.data, function (obj) {
                                    obj.text = obj.cityName;
                                    obj.id = obj.cityCode;
                                    return obj;
                                });
                cityData.unshift({ id:0 , text: "不限" });
                $("#capture-homeOwnership").select2({
                    language: "zh-CN",
                    data: cityData
                });
            }
        }
    });

    /* $.ajax({
        type: "post",
        url: "/device/getSites",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var siteData = $.map( data.data, function (obj) {
                    obj.text = obj.name;
                    obj.id = obj.sn;
                    return obj;
                });
                siteData.unshift({ id:0 , text: "不限" });
                $("#capture-site-sn").select2({
                    language: "zh-CN",
                    data: siteData,
                    templateResult: formatSelect
                });
            } else {
                $("#capture-site-sn").select2({
                    language: "zh-CN",
                    templateResult: formatSelect
                });
            }
        }
    });*/

    var setting = {
        check: {
            enable: true,
            chkStyle: "radio",  //单选框
            radioType: "all"   //对所有节点设置单选
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick,
            onCheck: onCheck
        }
    };

    function onCheck(event, treeId, treeNode){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getCheckedNodes(true);
        if(nodes.length == 0){
            treeObj.cancelSelectedNode();
        }
        treeObj.selectNode(nodes[0]);
    }

    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
    }

    $(document).ready(function(){
        $.ajax({
            type: "post",
            url: "/newHistory/queryZtree",
            dataType: "json",
            success: function(data){
                $.fn.zTree.init($("#treeDemo"), setting, data);
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                var node = treeObj .getNodeByParam('id','不限');
                treeObj.checkNode(node, true, true);
                $('#site-text').val('不限');
            }
        });
    });

    $('#site-text').click(function(){
        $('#site-dlg').modal('show');
    });

    $('#site_submit').click(function () {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getCheckedNodes(true);
        //var nodes = treeObj.getSelectedNodes();
        if(0 == nodes.length){
            Messenger().post({
                message: "请选择站点",
                type: 'error',
                showCloseButton: true
            });
            return;
        }
        zTreeSiteSn[0] = nodes[0].id;
        var siteName = nodes[0].name;
        $('#site-dlg').modal('hide');
        $('#site-text').val(siteName);
        var siteSN;
        if('不限' != zTreeSiteSn[0]){
            siteSN = zTreeSiteSn[0]
        }else{
            siteSN = '';
        }
        updateDevices(siteSN);
    });

    function updateDevices(siteSN){
        $("#capture-device-sn").empty();
        $.ajax({
            type: "post",
            url: "/device/getDevices",
            data: { siteSN: siteSN },
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    var deviceData = $.map( data.data, function (obj) {
                        obj.text = obj.name;
                        obj.id = obj.sn;
                        return obj;
                    });
                    deviceData.unshift({ id:0 , text: "不限" });
                    $("#capture-device-sn").select2({
                        language: "zh-CN",
                        data: deviceData,
                        templateResult: formatSelect
                    });
                } else {
                    $("#capture-device-sn").select2({
                        language: "zh-CN",
                        templateResult: formatSelect
                    });
                }
            }
        });
    }

    $.ajax({
        type: "post",
        url: "/device/getDevices",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var deviceData = $.map( data.data, function (obj) {
                    obj.text = obj.name;
                    obj.id = obj.sn;
                    return obj;
                });
                deviceData.unshift({ id:0 , text: "不限" });
                $("#capture-device-sn").select2({
                    language: "zh-CN",
                    data: deviceData,
                    templateResult: formatSelect
                });
            } else {
                $("#capture-device-sn").select2({
                    language: "zh-CN",
                    templateResult: formatSelect
                });
            }
        }
    });
    /*//站点选项发生改变时设备信息随之发生变化
    $("#capture-site-sn").change(function () {
        $("#capture-device-sn").empty();
        var siteSN =  $("#capture-site-sn").val();
        if( 0 == siteSN ) {
            siteSN="";
        }
        $.ajax({
            type: "post",
            url: "/device/getDevices",
            data: { siteSN: siteSN },
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    var deviceData = $.map( data.data, function (obj) {
                        obj.text = obj.name;
                        obj.id = obj.sn;
                        return obj;
                    });
                    deviceData.unshift({ id:0 , text: "不限" });
                    $("#capture-device-sn").select2({
                        language: "zh-CN",
                        data: deviceData,
                        templateResult: formatSelect
                    });
                } else {
                    $("#capture-device-sn").select2({
                        language: "zh-CN",
                        templateResult: formatSelect
                    });
                }
            }
        });
    });*/

    $.ajax({
        type:'post',
        url:'/newHistory/getUeinfoToDaterangepicker',
        dataType:'json',
        success:function(data){
            if(data.status){
                var captureRangeTime = $('#capture-range-time').daterangepicker({
                    timePicker: true,
                    timePickerIncrement: 1,
                    timePicker24Hour: true,
                    timePickerSeconds: true,
                    minDate:data.data,
                    maxDate: new Date().toLocaleDateString() + ' 23:59:59',
                    locale: {
                        applyLabel: '确定',
                        cancelLabel: '取消',
                        format: 'YYYY/MM/DD HH:mm:ss',
                        fromLabel : '起始时间',
                        toLabel : '结束时间',
                        customRangeLabel : '自定义',
                        daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
                        monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
                            '七月', '八月', '九月', '十月', '十一月', '十二月' ]
                    }
                });
            }else{
                var captureRangeTime = $('#capture-range-time').daterangepicker({
                    timePicker: true,
                    timePickerIncrement: 1,
                    timePicker24Hour: true,
                    timePickerSeconds: true,
                    maxDate: new Date().toLocaleDateString() + ' 23:59:59',
                    locale: {
                        applyLabel: '确定',
                        cancelLabel: '取消',
                        format: 'YYYY/MM/DD HH:mm:ss',
                        fromLabel : '起始时间',
                        toLabel : '结束时间',
                        customRangeLabel : '自定义',
                        daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
                        monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
                            '七月', '八月', '九月', '十月', '十一月', '十二月' ]
                    }
                });
            }
        }
    });



    $("#export-data-btn").click(function (e) {
        exportData('txt');
    });

    $("#export-data-txt-btn").click(function (e) {
        exportData('txt');
    })

    $("#export-data-csv-btn").click(function (e) {
        exportData('csv');
    })

    $("#export-data-xls-btn").click(function (e) {
        exportData('excel');
    });

    function exportData( fileType ) {
        if('txt' == fileType || 'csv' == fileType){
            $('#divId').hide();
        }
        var params = getParams();
        if(params == null || params == undefined){
            $('#waining').modal('show');
            return;
        }
        $('#showWait').modal('show');
        $.ajax({
            type: "post",
            url: "/newHistory/createExcel",
            dataType: "json",
            data: { formData: JSON.stringify(params),fileType : fileType },
            success:  function( data ){
                 $('#divId').show();
                if(data.status){
                    $('#showWait').modal('hide');
                    var url = window.location.protocol + "//" + window.location.host + "/newHistory/exportNewHistoryData?formData=" + data.result;
                    window.location.href = url;
                }else{
                    $('#showWait').modal('hide');
                    Messenger().post({
                        message: data.message,
                        type: 'error',
                        showCloseButton: true
                    });
                }
            }
        });
    }

    function getParams(){
        var startDate = $("#capture-range-time").val().slice(0,19);
        var startMil=Date.parse(startDate.slice(0, 10));
        var endDate = $("#capture-range-time").val().slice(22,41);
        var endMil=Date.parse(endDate.slice(0, 10));

        var operator = $("#capture-operator").val();
        //var homeOwnership = $("#capture-homeOwnership").val();
        var homeOwnership = $("#capture-homeOwnership").select2("data")[0].text;
        if('不限' == homeOwnership){
            homeOwnership="";
        }
        if( 0 == homeOwnership ) {
            homeOwnership="";
        }
        if(zTreeSiteSn.length != 0){
            var siteSN = zTreeSiteSn[0];
            if('不限' == siteSN){
                siteSN = "";
            }
        }else{
            var siteSN = "";
        }
        /*var siteSN = $("#capture-site-sn").val();
        if( 0 == siteSN ) {
            siteSN="";
        }*/
        var deviceSN = $("#capture-device-sn").val();
        if( 0 == deviceSN ) {
            deviceSN="";
        }
        var imsi = $("#capture-imsi").val();
        var imei = $("#capture-imei").val();

        var param = {
            startDate : $.trim(startDate),
            endDate : endDate,
            operator : $.trim(operator),
            homeOwnership : $.trim(homeOwnership),
            siteSN : $.trim(siteSN),
            deviceSN : $.trim(deviceSN),
            imsi : $.trim(imsi),
            imei : $.trim(imei)
        };
        var md5 = $.md5(JSON.stringify(param));
        var md5V = window.localStorage.getItem(md5);
        if(md5V != null){
            params = JSON.parse(md5V);
            return params;
        }
    }

    /*function getParams() {
        var params = new Array();
        var startDate = $("#capture-range-time").val().slice(0,19);
        var startMil=Date.parse(startDate.slice(0, 10));
        var endDate = $("#capture-range-time").val().slice(22,41);
        var endMil=Date.parse(endDate.slice(0, 10));

        var operator = $("#capture-operator").val();
        var homeOwnership = $("#capture-homeOwnership").val();
        if( 0 == homeOwnership ) {
            homeOwnership="";
        }
        var siteSN = $("#capture-site-sn").val();
        if( 0 == siteSN ) {
            siteSN="";
        }
        var deviceSN = $("#capture-device-sn").val();
        if( 0 == deviceSN ) {
            deviceSN="";
        }
        var imsi = $("#capture-imsi").val();

        var param = {
            startDate : $.trim(startDate),
            endDate : endDate,
            operator : $.trim(operator),
            homeOwnership : $.trim(homeOwnership),
            siteSN : $.trim(siteSN),
            deviceSN : $.trim(deviceSN),
            imsi : $.trim(imsi)
        };
        var daymils = 1000 * 60 * 60 * 24;
        do {
            var param = {};
            if(params.length == 0){
                param.tableName = 't_ue_info_' + startDate.slice(0, 10).replace(/\//g,'_');
            }else{
                var date = new Date(startMil);
                param.tableName = 't_ue_info_' + date.Format("yyyy_MM_dd").slice(0, 10)*//*.replace(/\//g,'_')*//*;
            }
            param.startTime = startDate.slice(11, 19);//开始时间
            param.siteSn = $.trim(siteSN);//站点编号
            param.deviceSn = $.trim(deviceSN);//设备编号
            param.imsi = $.trim(imsi);//imsi
            param.imei = "";
            param.operator = $.trim(operator);//运营商
            param.area = $.trim(homeOwnership);//归属地
            //结束时间
            if(endMil - startMil == 0) {
                param.endTime = endDate.slice(11, 19)
            } else {
                param.endTime = '23:59:59'
            }
            params[params.length] = param;
            startMil += daymils;
        } while(endMil - startMil >= 0);
        return params;
    }
*/
    Date.prototype.Format = function(fmt){
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            return fmt;
    };


   /* //数据导出
    function exportData( fileType ) {

        var startDate = $("#capture-range-time").val().slice(0,19);
        var endDate = $("#capture-range-time").val().slice(22,41);
        var operator = $("#capture-operator").val();
        var homeOwnership = $("#capture-homeOwnership").val();
        if( 0 == homeOwnership ) {
            homeOwnership="";
        }
        var siteSN = $("#capture-site-sn").val();
        if( 0 == siteSN ) {
            siteSN="";
        }
        var deviceSN = $("#capture-device-sn").val();
        if( 0 == deviceSN ) {
            deviceSN="";
        }
        var imsi = $("#capture-imsi").val();

        var exportCondition = {
            startTime: startDate,
            endTime: endDate,
            operator: operator,
            homeOwnership: homeOwnership,
            siteSN: siteSN,
            deviceSN: deviceSN,
            imsi: imsi
        };

        $("#export-data-btn").attr("disabled","disabled");
        $("#export-data-dropdown-btn").attr("disabled","disabled");

        $.ajax({
            type: "post",
            url: "/util/exportData",
            dataType:"json",
            data:{ type: 0, fileType: fileType, condition: JSON.stringify(exportCondition)},
            success:  function( data ){
                if( true == data.status ) {
                    $('#export-progress-dlg').modal({
                        backdrop: false,
                        keyboard: false,
                        show: true
                    });
                    exportDataProgress( data.data.exportId );
                } else {
                    $("#export-data-btn").removeAttr("disabled");
                    $("#export-data-dropdown-btn").removeAttr("disabled");
                    Messenger().post({
                        message: data.message,
                        type: 'error',
                        showCloseButton: true
                    }) ;
                }
            },
            error: function ( data  ) {
                $("#export-data-btn").removeAttr("disabled");
                $("#export-data-dropdown-btn").removeAttr("disabled");
                Messenger().post({
                    message:  "网络异常，请稍后再试",
                    type: 'error',
                    showCloseButton: true
                }) ;
            }
        });
    }*/

    function exportDataProgress( exoprtInfo ) {

        $.ajax({
            type: "post",
            url: "/util/exportDataProgress",
            dataType:"json",
            data:{ exportId: exoprtInfo },
            success:  function( data ){
                if( true == data.status ) {
                    if( data.data.progress >= 100) {
                        $('#export-progress-dlg').modal("hide");
                        $("#export-data-btn").removeAttr("disabled");
                        $("#export-data-dropdown-btn").removeAttr("disabled");
                        //window.open( data.data.url );
                        window.open(data.data.url,"title","height=260,width=466,toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
                    } else {
                        $('#export-progress-bar div.progress-bar').css("width",  data.data.progress + "%");
                        $('#export-progress-bar div.progress-bar').attr("aria-valuenow",  data.data.progress);
                        $("#export-progress-bar span").text( data.data.progress + "% Complete (success)");
                        window.setTimeout( function(){ exportDataProgress( exoprtInfo );}  , 1 * 1000 );
                    }
                } else {
                    $('#export-progress-dlg').modal("hide");
                    $("#export-data-btn").removeAttr("disabled");
                    $("#export-data-dropdown-btn").removeAttr("disabled");
                    Messenger().post({
                        message: data.message,
                        type: 'error',
                        showCloseButton: true
                    }) ;
                }
            },
            error: function ( data  ) {
                $('#export-progress-dlg').modal("hide");
                $("#export-data-btn").removeAttr("disabled");
                $("#export-data-dropdown-btn").removeAttr("disabled");
                Messenger().post({
                    message:  "网络异常，请稍后再试",
                    type: 'error',
                    showCloseButton: true
                }) ;
            }
        });
    }

    //新分页
    $("#query-condition-OK").bind("click", function() {
        processSearch();
    })
    //processSearch();
});



function processSearch () {
    var startDate = $("#capture-range-time").val().slice(0,19);
    var startMil=Date.parse(startDate.slice(0, 10));
    var endDate = $("#capture-range-time").val().slice(22,41);
    var endMil=Date.parse(endDate.slice(0, 10));

    var operator = $("#capture-operator").val();
  /*  var homeOwnership = $("#capture-homeOwnership").val();
    var homeOwnership1 = $("#capture-homeOwnership").html();*/
    var homeOwnership = $("#capture-homeOwnership").select2("data")[0].text;
    if('不限' == homeOwnership){
        homeOwnership="";
    }
    if( 0 == homeOwnership ) {
        homeOwnership="";
    }
    if(zTreeSiteSn.length != 0){
        var siteSN = zTreeSiteSn[0];
        if('不限' == siteSN){
            siteSN = "";
        }
    }else{
        var siteSN = "";
    }
    /*var siteSN = $("#capture-site-sn").val();
    if( 0 == siteSN ) {
        siteSN="";
    }*/
    var deviceSN = $("#capture-device-sn").val();
    if( 0 == deviceSN ) {
        deviceSN="";
    }
    var imsi = $("#capture-imsi").val();
    var imei = $("#capture-imei").val();

    var param = {
        startDate : $.trim(startDate),
        endDate : endDate,
        operator : $.trim(operator),
        homeOwnership : $.trim(homeOwnership),
        siteSN : $.trim(siteSN),
        deviceSN : $.trim(deviceSN),
        imsi : $.trim(imsi),
        imei : $.trim(imei)
    };

    var md5 = $.md5(JSON.stringify(param));
    var md5V = window.localStorage.getItem(md5);
    if(md5V == null || md5V == undefined || "[]" == md5V){
        var daymils = 1000 * 60 * 60 * 24;

        params = new Array();
        do {
            var param = {};
            if(params.length == 0){
                param.tableName = 't_ue_info_' + startDate.slice(0, 10).replace(/\//g,'_');
                param.startTime = startDate.replace(/\//g,'-');
                param.filterStart = startDate.slice(11,19);
            }else{
                var date = new Date(startMil);
                param.tableName = 't_ue_info_' + date.Format("yyyy_MM_dd").slice(0, 10)/*.replace(/\//g,'_')*/;
                param.startTime = date.Format("yyyy-MM-dd") + ' 00:00:00';
                param.filterStart = '00:00:00';
            }
            //param.startTime = startDate.slice(11, 19);//开始时间

            param.siteSn = $.trim(siteSN);//站点编号
            param.deviceSn = $.trim(deviceSN);//设备编号
            param.imsi = $.trim(imsi);//imsi
            param.imei = $.trim(imei);
            param.operator = $.trim(operator);//运营商
            param.area = $.trim(homeOwnership);//归属地
            //结束时间
            if(endMil - startMil == 0) {
                param.endTime = endDate.replace(/\//g,'-')
                //param.endTime = endDate.slice(11, 19)
                param.filterEnd = endDate.slice(11, 19);
            } else {
                var date = new Date(startMil);
                param.endTime = date.Format("yyyy-MM-dd") + ' 23:59:59'
                param.filterEnd = "23:59:59";
            }
            params[params.length] = param;
            startMil += daymils;
        } while(endMil - startMil >= 0);
    } else {
        params = JSON.parse(md5V);
    }

    historyDataTable = $('#history-data-table').DataTable( {
        "sDom": "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-1'l><'col-sm-5'i><'col-sm-6'p>>",
        "select": {
            style: "multi",
            selector: "td:first-child"
        },
        "sServerMethod":"POST",
        "searching":false,
        "bFilter":true,
        "bDestroy": true,
        "bServerSide": true,
        "bProcessing":true,
        "bSort":false,
        "sAjaxDataProp" : "dataResult",
        "sAjaxSource": "/newHistory/getHistory",
        "fnServerParams": function ( aoData ) {
            aoData.push( { "name": "formData", "value": JSON.stringify(params) } );
        },
        "fnServerData" : function(sSource,aoData,fnCallback){
            $.ajax({
                "type": "post",
                "url": sSource,
                "dataType": "json",
                "data": aoData,
                success:  function( data ){
                    fnCallback(data.records);
                    var json =  JSON.stringify(data.params);
                    window.localStorage.setItem(md5, json);
                    params = data.params;
                }
            });
        },

        "createdRow": function( row, data, dataIndex ) {
            if ( data.indication != 0  ) {
                $(row).addClass( 'text-red' );
            }
        },
        "columnDefs": [
            {
                sDefaultContent: '',
                aTargets: [ '_all' ]
            }
        ],
        "columns": [
            { "data": "imsi","sClass": "center"},
            { "data": "imei","sClass": "center"},
            { "data": "operator","sClass": "center" },
            { "data": "cityName","sClass": "center" },
            { "data": "siteSn","sClass": "center"},
            { "data": "siteName","sClass": "center" },
            { "data": "deviceSn","sClass": "center" },
            /*{ "data": "deviceName","sClass": "center" },*/
            { "data": "captureTime","sClass": "center"}/*,
            { "data": null,"sClass": "center","bSortable": false }*/
        ],
        "oLanguage" : dataTable_language
    } );

    Date.prototype.Format = function(fmt){
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
          if(new RegExp("("+ k +")").test(fmt))
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
          return fmt;
    };
}
    function timeOut() {
        var result = $('#hideIframe').prop('contentWindow').document;
        alert(result.readyState)
    }
