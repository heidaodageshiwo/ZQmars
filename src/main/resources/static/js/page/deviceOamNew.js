/**
 * 设备维护新
 * Created by xuebing.zheng on 2017/12/07.
 */
$(function () {


    var statusMap = {
        "全部": "all",
        "运行中": "run",
        "离线": "offline",
        "有告警": "warning",
        "即将过期": "willexpire",
        "已过期": "expiredfailure"
    };

    var asyncUrl = "/device/getDeviceStatusTreeOnline";
    var getAsyncUrl = function (treeId, treeNode) {
        return asyncUrl;
    };

    //初始化树
    var setting = {

        //异步加载配置
        async:{
            enable: true,
            //contentType: "application/json",
            dataType: "json",
            type: "get",
            url: getAsyncUrl
        },
        data:{
            simpleData:{
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0
            }
        },
        check:{
            enable: false
        },
        view:{
            fontCss: getFont,
            nameIsHTML: true
        },
        callback:{
            onClick: zTreeOnClick
        }
    };

    function getFont(treeId, node) {
        return node.font ? node.font : {};
    }

    function zTreeOnClick(event, treeId, treeNode){
        //点击设备节点时执行操作
        if(treeNode.level == 4 && treeNode.sn){
            selectDeviceNode(treeNode);
        }
    }

    //init tree
    //when set async mode, third param must set null or []
    var zTreeObj = $.fn.zTree.init($("#devices-online-tree"), setting);

    function selectDeviceNode( node ) {

        var deviceInfo = node.sn + "-" + node.name;
        $("#device-info").text(deviceInfo);
        $("#device-sn").val( node.sn );

        var $boxBodyElm =  $("#accordion").parents(".box-body");
        var $overlayElm = $boxBodyElm.siblings(".overlay");
        if( (null == $overlayElm) || ( $overlayElm == undefined) ||  ($overlayElm.length==0) ) {
            $boxBodyElm.after('<div class="overlay"><i class="fa fa-refresh fa-spin"></i></div>');
        } else {
            $overlayElm.empty();
            $overlayElm.append('<i class="fa fa-refresh fa-spin"></i>')
        }

        oamFunction( node.sn, "getRFSwitch", "", function (data) {
            //赋值，
            assignRFSwitch( data.parameter );
        }, function ( data ) {
        });

        oamFunction( node.sn, "getRFParam", "", function ( data) {

            $('#collapseRF').collapse('show');
            $('#collapseFunc').collapse('hide');
            $('#collapseAglignGPS').collapse('hide');
            $('#collapsePA').collapse('hide');
            $('#collapseControlRange').collapse('hide');
            $('#collapseEnbId').collapse('hide');
            $('#collapseDataSend').collapse('hide');
            $('#collapseVersion').collapse('hide');
            $('#collapsePAStatus').collapse('hide');
            $('#collapsePAInfo').collapse('hide');

            //赋值，
            assignRFParam( data.parameter );

            //删除蒙版
            var $domElm = $("#accordion").parents(".box-body").siblings(".overlay");
            $domElm.remove();
        }, function ( data ) {
            //修改蒙版
            var  $domElm = $("#accordion").parents(".box-body").siblings(".overlay");
            $domElm.empty();
            $domElm.append('<i class="fa text-orange">链接设备出错,请再试!</i>');
        });
    }

    function oamFunction( deviceSN, actionName, param, successFun, failFun  ) {

        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: "/device/deviceAction",
            data:{ 'deviceSN': deviceSN, actionName: actionName, actionParam: param },
            timeout: 10000,
            success: function ( data ) {
                if( true == data.status ) {
                    successFun( data.data );
                } else {
                    failFun( data.data );
                    Messenger().post({
                        message:  data.message,
                        type: 'error',
                        showCloseButton: true
                    }) ;
                }
            },
            error: function ( data ) {
                failFun( data );
                Messenger().post({
                    message: "当前网络异常，请等会再试",
                    type: 'error',
                    showCloseButton: true
                }) ;
            }
        });

    }

    $('input.param-switch').bootstrapSwitch();

    var selectOption = {
        language: "zh-CN",
        minimumResultsForSearch: Infinity
    };
    $('select.form-select2').select2(selectOption);


    $("[data-mask]").inputmask( undefined, {
        rightAlignNumerics: false,
        oncomplete: function () {
            var minValue = Number($(this).data("min-value"));
            var maxValue = Number($(this).data("max-value"));
            var nowValue = Number($(this).val());

            if( ( isNaN(nowValue) ) || (nowValue > maxValue) || ( nowValue < minValue )  ){
                $(this).parents( ".form-group" ).addClass( "has-error" );
            } else {
                $(this).parents( ".form-group" ).removeClass( "has-error" );
            }
        },
        onincomplete: function () {
            var minValue = Number($(this).data("min-value"));
            var maxValue = Number($(this).data("max-value"));

            if( "" !== $(this).val() ) {
                var nowValue = Number($(this).val());
                if( ( isNaN(nowValue) ) || (nowValue > maxValue) || ( nowValue < minValue )  ){
                    $(this).parents( ".form-group" ).addClass( "has-error" );
                } else {
                    $(this).parents( ".form-group" ).removeClass( "has-error" );
                }
            } else {
                $(this).parents( ".form-group" ).addClass( "has-error" );
            }

        },
        onKeyUp: function () {
            var inputMinValue = Number($(this).attr("data-min-value"));
            var inputMaxValue = Number($(this).attr("data-max-value"));
            if( "" !== $(this).val() ) {
                var dddd = $(this).val();
                console.log()
                var nowValue = Number($(this).val());
                if( ( isNaN(nowValue) ) || (nowValue > inputMaxValue) || ( nowValue < inputMinValue )  ){
                    $(this).parents( ".form-group" ).addClass( "has-error" );
                } else {
                    console.log(nowValue);
                    $(this).parents( ".form-group" ).removeClass( "has-error" );
                    $(this).val(dddd);
                }
            } else {
                $(this).parents( ".form-group" ).addClass( "has-error" );
            }
        }
    });

    //参数联动
    $('#rf-param-framestrucuretype').on( 'change', function (e ) {
        var framestrucuretype = $(this).val();
        var $select =   $('#rf-param-eutraband');
        var options = $select.data('select2').options.options;
        $select.html('');
        var bandData = [];
        if( 0 == framestrucuretype ) { //tdd
            bandData = [
                { id: '38' , text: '38'} ,
                { id: '39' , text: '39'} ,
                { id: '40' , text: '40'} ,
                { id: '41' , text: '41'}
            ];
            $select.append('<option value="38">38</option>');
            $select.append('<option value="39">39</option>');
            $select.append('<option value="40">40</option>');
            $select.append('<option value="41">41</option>');
        } else { //fdd
            bandData = [
                { id: '1' , text: '1'} ,
                { id: '3' , text: '3'} ,
                { id: '6' , text: '6'} ,
                { id: '8' , text: '8'}
            ];
            $select.append('<option value="1">1</option>');
            $select.append('<option value="3">3</option>');
            $select.append('<option value="6">6</option>');
            $select.append('<option value="8">8</option>');
        }
        options.data = bandData;
        $select.select2( options );
        $select.select2( selectOption ).trigger("change");
    });

    $('#rf-param-eutraband').on('change', function (e) {

        var eutraband = Number($(this).val());
        var framestrucuretype = Number($('#rf-param-framestrucuretype').val());

        var $ulearfcn = $('#rf-param-ulearfcn');
        var $dlearfcn = $('#rf-param-dlearfcn');

        var ularfcnMax = 0;
        var ularfcnMin = 0;
        var  dlarfcnMax = 0;
        var  dlarfcnMin = 0;
        if( 0 == framestrucuretype ) { //tdd
            switch( eutraband ) {
                case 38:
                    ularfcnMax = 38249;
                    ularfcnMin = 37750;
                    dlarfcnMax = 38249;
                    dlarfcnMin = 37750;
                    break;
                case 39:
                    ularfcnMax = 38649;
                    ularfcnMin = 38250;
                    dlarfcnMax = 38649;
                    dlarfcnMin = 38250;
                    break;
                case 40:
                    ularfcnMax = 39549;
                    ularfcnMin = 38650;
                    dlarfcnMax = 39549;
                    dlarfcnMin = 38650;
                    break;
                case 41:
                    ularfcnMax = 41239;
                    ularfcnMin = 40240;
                    dlarfcnMax = 41239;
                    dlarfcnMin = 40240;
                    break
            }
        } else { //fdd
            switch( eutraband ) {
                case 1:
                    ularfcnMax = 18599;
                    ularfcnMin = 1800;
                    dlarfcnMax = 599;
                    dlarfcnMin = 0;
                    break;
                case 3:
                    ularfcnMax = 19949;
                    ularfcnMin = 19200;
                    dlarfcnMax = 1949;
                    dlarfcnMin = 1200;
                    break;
                case 6:
                    ularfcnMax = 2749;
                    ularfcnMin = 2650;
                    dlarfcnMax = 2749;
                    dlarfcnMin = 2650;
                    break;
                case 8:
                    ularfcnMax = 3799;
                    ularfcnMin = 3450;
                    dlarfcnMax = 3799;
                    dlarfcnMin = 3450;
                    break
            }
        }

        $ulearfcn.attr("data-min-value", ularfcnMin);
        $ulearfcn.attr("data-max-value", ularfcnMax);
        $ulearfcn.attr("placeholder", ularfcnMin + "~" + ularfcnMax);
        $ulearfcn.val(ularfcnMin);

        $dlearfcn.attr("data-min-value", dlarfcnMin);
        $dlearfcn.attr("data-max-value", dlarfcnMax);
        $dlearfcn.attr("placeholder", dlarfcnMin + "~" + dlarfcnMax);
        $dlearfcn.val(dlarfcnMin);

    });


    function assignRFSwitch( data ) {
        if( 1 == data.rfenable ) {
            $("#rf-switch").bootstrapSwitch("state", true );
        } else {
            $("#rf-switch").bootstrapSwitch("state", false );
        }
    }

    function assignRFParam( data ) {
        if( 1 == data.rfenable ) {
            //$("#rf-switch").bootstrapSwitch("state", true );
            $("#rf-param-rfenable").bootstrapSwitch("state", true );
        } else {
            //$("#rf-switch").bootstrapSwitch("state", false );
            $("#rf-param-rfenable").bootstrapSwitch("state", false );
        }
        if( 1 == data.fastconfigearfcn ){
            $("#rf-param-fastconfigearfcn").bootstrapSwitch("state", true );
        } else {
            $("#rf-param-fastconfigearfcn").bootstrapSwitch("state", true );
        }
        $("#rf-param-framestrucuretype").val(data.framestrucuretype);
        $("#rf-param-framestrucuretype").select2( selectOption ).trigger("change");
        $("#rf-param-eutraband").val(data.eutraband);
        $("#rf-param-eutraband").select2( selectOption ).trigger("change");

        $("#rf-param-ulearfcn").val(data.ulearfcn);
        $("#rf-param-dlearfcn").val(data.dlearfcn);
        $("#rf-param-subframeassinment").val(data.subframeassinment);
        $("#rf-param-specialsubframepatterns").val(data.specialsubframepatterns);
        $("#rf-param-ulbandwidth").val(data.ulbandwidth);
        $("#rf-param-ulbandwidth").select2( selectOption).trigger("change");
        $("#rf-param-dlbandwidth").val(data.dlbandwidth);
        $("#rf-param-dlbandwidth").select2( selectOption ).trigger("change");
        $("#rf-param-tx1powerattenuation").val(data.tx1powerattenuation);
        $("#rf-param-tx2powerattenuation").val(data.tx2powerattenuation);
        $("#rf-param-rfchoice").val(data.rfchoice);
        $("#rf-param-rfchoice").select2( selectOption ).trigger("change");

    }

    function assignFuncParam( data ) {

        $("#func-param-paramcc").val(data.paramcc);
        $("#func-param-paramnc").val(data.paramnc);
        $("#func-param-parapcino").val(data.parapcino);
        $("#func-param-paraperi").val(data.paraperi);
        $("#func-param-circletime").val(data.circletime);
        $("#func-param-controlrange").val(data.controlrange);
        $("#func-param-controlrange").select2(selectOption).trigger("change");
        $("#func-param-inandouttype").val(data.inandouttype);
        $("#func-param-inandouttype").select2(selectOption).trigger("change");
        $("#func-param-arfcn").val(data.arfcn);

        $("#func-param-arfcn1").val(data.arfcn1);
        $("#func-param-arfcn2").val(data.arfcn2);
        $("#func-param-arfcn3").val(data.arfcn3);

        if( 1 == data.boolstart  ) {
            $("#func-param-boolstart").bootstrapSwitch("state", true );
        } else {
            $("#func-param-boolstart").bootstrapSwitch("state", false );
        }
    }

    function assignAlignGPSParam( data ) {
        $("#gps-param-offset").val(data.offset);
        $("#gps-param-offset").select2(selectOption).trigger("change");
        $("#gps-param-friendoffset").val(data.friendoffset);
        $("#gps-param-friendoffset").select2(selectOption).trigger("change");
    }

    function assignPaParam( data ) {
        $("#pa-param-powerattenuation").val(data.powerattenuation);
    }

    function assignControlRangeParam( data ) {
        if( 1 == data.open  ) {
            $("#ctrlRange-param-open").bootstrapSwitch("state", true );
        } else {
            $("#ctrlRange-param-open").bootstrapSwitch("state", false );
        }
        $("#ctrlRange-param-lev").val(data.lev);
    }

    function assignAENBIdParam( data ) {
        $("#enbid-param-identity").val(data.identity);
    }

    function assignDataSendCfgParam( data ) {
        if( 1 == data.realtimesend  ) {
            $("#datasend-param-realtimesend").bootstrapSwitch("state", true );
            $("#datasend-param-interalmin").prop("disabled", true);
            $("#datasend-param-uecountsend").prop("disabled", true);
        } else {
            $("#datasend-param-realtimesend").bootstrapSwitch("state", false );
            $("#datasend-param-interalmin").prop("disabled", false);
            $("#datasend-param-uecountsend").prop("disabled", false);
        }
        $("#datasend-param-interalmin").val(data.interalmin);
        $("#datasend-param-interalmin").select2(selectOption).trigger("change");

        $("#datasend-param-uecountsend").val(data.uecountsend);
        $("#datasend-param-uecountsend").select2(selectOption).trigger("change");

    }

    function assignVersionParam( data ) {
        $("#version-param-fpgaversion").text(data.fpgaversion);
        $("#version-param-bbuversion").text(data.bbuversion);
        $("#version-param-softwareversion").text(data.softwareversion);
        if( 0 == data.license ) {
            $("#version-param-license").text("许可正常");
        } else {
            $("#version-param-license").text("许可已过期");
        }
    }

    function assignPAStatusParam( data ) {
        if( 0 == data.valid ){
            $("#pastatus-param-valid").text("无效");
        } else if( 1 == data.valid  ){
            $("#pastatus-param-valid").text("有效");
        } else {
            $("#pastatus-param-valid").text("485连接异常");
        }

        if( 0 == data.warnpa ){
            $("#pastatus-param-warnpa").text("正常");
        } else {
            $("#pastatus-param-warnpa").text("告警");
        }
        if( 0 == data.warnstandingwaveratio ){
            $("#pastatus-param-warnstandingwaveratio").text("正常");
        } else {
            $("#pastatus-param-warnstandingwaveratio").text("告警");
        }
        if( 0 == data.warntemp ){
            $("#pastatus-param-warntemp").text("正常");
        } else {
            $("#pastatus-param-warntemp").text("告警");
        }
        if( 0 == data.warnpower ){
            $("#pastatus-param-warnpower").text("正常");
        } else {
            $("#pastatus-param-warnpower").text("告警");
        }
        if( 0 == data.onoffpa ){
            $("#pastatus-param-onoffpa").text("关");
        } else {
            $("#pastatus-param-onoffpa").text("开");
        }

        $("#pastatus-param-inversepower").text( data.inversepower + "dbm");
        $("#pastatus-param-temp").text( data.temp + "°C");
        $("#pastatus-param-alcvalue").text( data.alcvalue + "dbm");
        $("#pastatus-param-standingwaveratio").text( (Number(data.standingwaveratio)/10) );
        $("#pastatus-param-currattpa").text( data.currattpa );
        $("#pastatus-param-forwardpower").text( data.forwardpower );
        $("#pastatus-param-forwardpower2").text( data.forwardpower2 );

    }

    function assignPAInfoParam( data ) {

        $("#painfo-param-pacount").text( data.pacount );
        if( 0 ==  data.valid ) {
            $("#painfo-param-valid").text( "无效" );
        } else {
            $("#painfo-param-valid").text( "有效" );
        }
        $("#painfo-param-power").text( data.power );
        $("#painfo-param-panum").text( data.panum );
        $("#painfo-param-band").text( data.band );
        if( 0 == data.en485 ) {
            $("#painfo-param-en485").text( "未开启" );
        } else {
            $("#painfo-param-en485").text( "开启" );
        }
        $("#painfo-param-addr485").text( data.addr485 );
        $("#painfo-param-provider").text( data.provider );
        $("#painfo-param-sn").text( data.sn );
        $("#painfo-param-factory").text( data.factory );
    }

    $('#rf-switch').bootstrapSwitch({
        onColor: "success",
        offColor: "danger",
        onText: "开",
        offText: "关",
        labelText: "射频开关",
        labelWidth: 100
    });

    $('#rf-switch').on('switchChange.bootstrapSwitch', function(event, state) {

        var deviceSN = $("#device-sn").val();
        var oamParam ;
        if( state ) {
            oamParam = { 'rfenable':  1 };
        } else {
            oamParam = { 'rfenable':  0 };
        }
        oamFunction( deviceSN, "switchRF", JSON.stringify(oamParam), function ( data) {
            /* $("#rf-param-rfenable").bootstrapSwitch("state", state );*/
        }, function ( data ) {
            $('#rf-switch').bootstrapSwitch('toggleState',true, true);
        });
    });

    $('#reboot-btn').on('click', function ( e ) {

        bootbox.confirm({
            size: "small",
            message: "是否复位设备",
            buttons: {
                confirm: {
                    label: '是',
                    className: 'btn-primary'
                },
                cancel: {
                    label: '否',
                    className: ''
                }
            },
            callback: function (result) {
                if( result ) {
                    var deviceSN = $("#device-sn").val();
                    oamFunction( deviceSN, "reboot", "", function ( data) {
                        Messenger().post({
                            message:  "复位命令已发送成功",
                            type: 'success',
                            showCloseButton: true
                        }) ;
                    }, function ( data ) {
                    });
                }
            }
        });

    });

    $('#reset-factory').on('click', function ( e ) {
        bootbox.confirm({
            size: "small",
            message: "是否恢复出厂设置",
            buttons: {
                confirm: {
                    label: '是',
                    className: 'btn-primary'
                },
                cancel: {
                    label: '否',
                    className: ''
                }
            },
            callback: function (result) {
                if( result ) {
                    var deviceSN = $("#device-sn").val();
                    oamFunction( deviceSN, "resetFactory", "", function ( data) {
                        Messenger().post({
                            message: "恢复出厂设置命令已发生成功",
                            type: 'success',
                            showCloseButton: true
                        }) ;
                    }, function ( data ) {
                    });
                }
            }
        });

    });


    function refurbishParam( em ) {
        var deviceSN = $("#device-sn").val();
        var actionName = em.data("get-action-name");

        em.after('<div class="overlay"><i class="fa fa-refresh fa-spin"></i></div>');

        oamFunction( deviceSN, actionName, "", function ( data) {

            //赋值，
            switch ( $.trim(data.actionName) ){
                case "getRFParam":
                    assignRFParam( data.parameter);
                    break;
                case "getFuncParam":
                    assignFuncParam( data.parameter);
                    break;
                case "getAlignGPSParam":
                    assignAlignGPSParam( data.parameter);
                    break;
                case "getPAParam":
                    assignPaParam( data.parameter);
                    break;
                case "getControlRangeParam":
                    assignControlRangeParam( data.parameter);
                    break;
                case "getENBIdParam":
                    assignAENBIdParam( data.parameter);
                    break;
                case "getDataSendCfgParam":
                    assignDataSendCfgParam( data.parameter);
                    break;
                case "queryVersion":
                    assignVersionParam( data.parameter);
                    break;
                case "queryPAStatus":
                    assignPAStatusParam( data.parameter);
                    break;
                case "queryPAInfo":
                    assignPAInfoParam( data.parameter);
                    break;

                //获取设备服务器地址配置
                case "getServerCnfParam":
                    {
                        $("#serverAddrType").val(data.parameter.serverAddrType);
                        $("#serverAddr").val(data.parameter.serverAddr);
                        $("#serverPort").val(data.parameter.serverPort);
                        $("#serverTryConnectionTime").val(data.parameter.serverTryConnectionTime);

                        $("#secondServerEnable").val(data.parameter.secondServerEnable);

                        $("#secondServerAddrType").val(data.parameter.secondServerAddrType);
                        $("#secondServerAddr").val(data.parameter.secondServerAddr);
                        $("#secondServerPort").val(data.parameter.secondServerPort);
                        $("#secondServerConnectionTime").val(data.parameter.secondServerConnectionTime);
                    }
                    break;
                case "getSrxParam":
                    //assignServerCnfParam( data.parameter);
                    $("#srx-param-DlEarfcn").val(data.parameter.DlEarfcn);
                    break;
                default:
                    console.log("unknown message.")
            }

            //删除蒙版
            var $domElm = em.siblings("div.overlay");
            $domElm.remove();

        }, function ( data ) {
            var $domElm = em.siblings("div.overlay");
            $domElm.remove();
        });
    }

    $(".panel-collapse").on('show.bs.collapse', function ( e ) {
        refurbishParam( $(this) );
    });


    $("[data-param-get]").click( function () {
        var panelId = $(this).data("param-get");
        refurbishParam( $(panelId) );
    });

    $("[data-param-set]").click( function () {
        var panelId = $(this).data("param-set");
        var $form = $(panelId).find("form");
        var errordom = $form.find("div.has-error");
        if( errordom.length > 0 ) {
            Messenger().post({
                message: "有非法参数",
                type: 'error',
                showCloseButton: true
            }) ;
            return ;
        }

        var actionName = $(panelId).data("set-action-name");
        var deviceSN = $("#device-sn").val();
        var formData = $form.serializeArray();
        var deviceParam = {};
        $.each( formData, function ( i, valObj ) {
            deviceParam[ valObj.name] =  valObj.value;
        });

        //开关量处理
        if( "setRFParam" ==  $.trim(actionName) ) {
            if( deviceParam.hasOwnProperty('rfenable') ){
                deviceParam.rfenable = 1;
            } else {
                deviceParam.rfenable = 0;
            }

            if( deviceParam.hasOwnProperty('fastconfigearfcn') ){
                deviceParam.fastconfigearfcn = 1;
            } else {
                deviceParam.fastconfigearfcn = 0;
            }

        } else if( "setFuncParam" ==  $.trim(actionName) ) {
            if( deviceParam.hasOwnProperty('boolstart') ){
                deviceParam.boolstart = 1;
            } else {
                deviceParam.boolstart = 0;
            }
        } else if( "setDataSendCfgParam" ==  $.trim(actionName) ) {
            if( deviceParam.hasOwnProperty('realtimesend') ){
                deviceParam.realtimesend = 1;
            } else {
                deviceParam.realtimesend = 0;
            }
        } else if( "setServerCnfParam" ==  $.trim(actionName) ) {
            deviceParam.secondServerEnable = $("#secondServerEnable").val();
        } else if( "setControlRangeParam" ==  $.trim(actionName) ) {
            if( deviceParam.hasOwnProperty('open') ){
                deviceParam.open = 1;
            } else {
                deviceParam.open = 0;
            }
        }

        oamFunction(deviceSN, actionName, JSON.stringify(deviceParam), function ( data ) {
            Messenger().post({
                message:  "配置成功",
                type: 'success',
                showCloseButton: true
            }) ;
        }, function ( data ) {

        });

    });

    $('#datasend-param-realtimesend').on('switchChange.bootstrapSwitch', function (event, state) {

        $("#datasend-param-interalmin").prop("disabled", state);
        $("#datasend-param-uecountsend").prop("disabled", state);

    });

    $('#downloadLog').click(function(){
        var deviceSN = $("#device-sn").val();
        var actionName = 'downloadLog';
       // $("#downloadLog").attr('disabled',true);
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: "/device/deviceAction",
            data:{ 'deviceSN': deviceSN, actionName: actionName, actionParam: '' },
            timeout: 10000,
            success: function ( data ) {
               // $("#downloadLog").attr('disabled',false);
                if(data.success && data.info == 1){
                    Messenger().post({
                        message: "命令已成功发送，请2分钟后尝试下载",
                        type: 'success',
                        showCloseButton: true
                    }) ;
                }else{
                    Messenger().post({
                        message: "当前网络异常，请等会再试",
                        type: 'error',
                        showCloseButton: true
                    }) ;
                }
            }
        });
       /* var t1 = setInterval( function () {
              $("#downloadLog").attr('disabled',false);
            }, 10000 );
        //去掉定时器
        window.clearInterval(t1);*/
    });

    $('#exportLog').click(function(){
        var deviceSn = $("#device-sn").val();
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: "/device/exportLog",
            data: { 'deviceSn': deviceSn } ,
            timeout: 10000,
            success: function ( data ) {
                if(data.status){
                    Messenger().post({
                        message: data.info,
                        type: 'success',
                        showCloseButton: true
                    }) ;
                    var FileName = deviceSn.toLowerCase()+".zip";
                    var url = window.location.protocol + "//" + window.location.host + "/device/deviceLogdownFromHttp?fileName="+FileName;
                    window.location.href = url;



                }else{
                    Messenger().post({
                        message: data.info,
                        type: 'error',
                        showCloseButton: true
                    }) ;
                }
            }
        });
    });

});