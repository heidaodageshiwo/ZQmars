$(function () {
    //Make the dashboard widgets sortable Using jquery UI
    $(".connectedSortable").sortable({
        placeholder: "sort-highlight",
        connectWith: ".connectedSortable",
        handle: ".box-header, .nav-tabs",
        forcePlaceholderSize: true,
        zIndex: 999999
    });
    $(".connectedSortable .box-header, .connectedSortable .nav-tabs-custom").css("cursor", "move");

    $(".btn-refresh").hide();

    //清空状态缓存
    if(store.get("zTree" + window.location)){
        store.remove("zTree" + window.location);
    }

    var statusMap = {
        "全部": "all",
        "运行中": "run",
        "离线": "offline",
        "有告警": "warning",
        "即将过期": "willexpire",
        "已过期": "expiredfailure"
    };

    var status = "all";
    var deviceSn; //当前选中设备sn号
    var showRefresh; //是否显示刷新按钮

    var asyncUrl = "/device/getDeviceStatusTreeNew?status=all";
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
            url: getAsyncUrl,
            dataFilter: ajaxDataFilter
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
            onClick: zTreeOnClick,
            // onAsyncSuccess: zTreeOnAsyncSuccess,
            onExpand: onExpand,
            onCollapse: onCollapse
        }
    };

    function getFont(treeId, node) {
        return node.font ? node.font : {};
    }

    function zTreeOnClick(event, treeId, treeNode){
        //点击设备节点时执行操作
        if(treeNode.level == 4 && treeNode.sn){
            deviceSn = treeNode.sn;
            var deviceInfo = treeNode.sn + "-" + treeNode.name;
            $("#device-info").text(deviceInfo);

            updateDeviceInfo(treeNode);

            //显示刷新按钮
            $(".btn-refresh").show();
        }
    }

    /**
     * 异步请求的数据预处理
     * @param treeId
     * @param parentNode
     * @param responseData
     */
    function ajaxDataFilter(treeId, parentNode, responseData) {
        //恢复节点Collapse状态
        //获取异步加载的节点数据
        if(responseData && responseData.length != 0){
            //若存在历史操作记录
            if(store.get("zTree" + window.location)){
                var zTreeNodeArray = JSON.parse(store.get('zTree' + window.location));
                for (var i = 0; i < zTreeNodeArray.length; i++) {
                    //恢复节点状态
                    var index = responseData.findIndex(function (value) {
                        return value.id === zTreeNodeArray[i];
                    });

                    responseData[index].open = false;
                }
            }
        }

        return responseData;
    }

    /**
     * Ztree 异步数据加载完成事件
     * @param event
     * @param treeId
     * @param treeNode
     * @param msg
     */
    // function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
    //     //获取异步加载的节点数据
    //     if(msg && msg.length != 0){
    //         //若存在历史操作记录
    //         if(store.get("zTree" + window.location)){
    //             var zTreeNodeArray = JSON.parse(store.get('zTree' + window.location));
    //             for (var i = 0; i < zTreeNodeArray.length; i++) {
    //                 var node = zTreeObj.getNodeByParam('id', zTreeNodeArray[i])
    //                 zTreeObj.expandNode(node, false, false)
    //             }
    //         }
    //     }
    // }

    /**
     * 将节点合并状态存入浏览器本地缓存
     * @param event
     * @param treeId
     * @param treeNode
     */
    function onCollapse(event, treeId, treeNode) {
        var cookie = store.get("zTree" + window.location);
        var z_tree = new Array();
        if (cookie) {
            z_tree = JSON.parse(cookie);
        }
        if ($.inArray(treeNode.id, z_tree) < 0) {
            z_tree.push(treeNode.id);
        }
        //$.cookie("z_tree" + window.location, JSON2.stringify(z_tree))
        store.set("zTree" + window.location, JSON.stringify(z_tree));
    }

    /**
     * 从浏览器本地缓存删除节点合并记录
     * @param event
     * @param treeId
     * @param treeNode
     */
    function onExpand(event, treeId, treeNode) {
        var cookie = store.get("zTree" + window.location);
        var z_tree = new Array();
        if (cookie) {
            z_tree = JSON.parse(cookie);
        }
        var index = $.inArray(treeNode.id, z_tree);
        z_tree.splice(index, 1);
        for (var i = 0; i < treeNode.children.length; i++) {
            index = $.inArray(treeNode.children[i].id, z_tree);
            if (index > -1) z_tree.splice(index, 1);
        }
        //$.cookie("z_tree" + window.location, JSON2.stringify(z_tree))
        store.set("zTree" + window.location, JSON.stringify(z_tree));
    }

    var updateDeviceInfoTimer = null;
    function updateDeviceInfo ( node ) {
        var sn = node.sn;

        function updateDeviceAjax() {
            $.ajax({
                type: 'GET',
                dataType: 'json',
                url: "/device/getDeviceStatusInfo",
                data:{ 'sn': sn },
                timeout: 5000,
                success: function ( data ) {
                    if( true == data.status ) {
                        updateStatusUI(data.data);
                    }
                }
            });
        }

        function updateDlList( data , name ) {
            var rowDom=[];
            $.each(data[name], function ( i, info ) {
                rowDom.push('<dt>' + info.label + '</dt>');
                var statusValue = info.value;
                if( (statusValue == undefined) || ( null == statusValue)  ){
                    statusValue= "";
                }
                rowDom.push('<dd style="word-break:break-all" id="' + info.label + '">' + statusValue + '</dd>');
            });
            $("#device-" + name + "-info").empty();
            $("#device-" + name + "-info").prepend(rowDom.join(''));
        }

        function updateStatusUI( data ) {
            updateDlList(data, "normal");
            updateDlList(data, "board");
            updateDlList(data, "license");
            updateDlList(data, "paStatus");
            updateDlList(data, "paInfo");
            updateDlList(data, "sniffer");
            updateDlList(data, "workmode");
            updateDlList(data, "debug");
        }

        if( updateDeviceInfoTimer ) {
            window.clearInterval(updateDeviceInfoTimer);
        }
        updateDeviceAjax();
        updateDeviceInfoTimer = window.setInterval( updateDeviceAjax, 30 * 1000);
    }


    //init tree
    //when set async mode, third param must set null or []
    var zTreeObj = $.fn.zTree.init($("#devices-status-tree"), setting);

    $("#device-status-list li a").on('click', function () {
        status = statusMap[this.innerText];
        asyncUrl = "/device/getDeviceStatusTreeNew?status=" + status;
        //清空设备树状态缓存
        store.remove("zTree" + window.location);
        zTreeObj.reAsyncChildNodes(null, "refresh");
    });

    //定时刷新
    var refreshStatusTree = function () {
        asyncUrl = "/device/getDeviceStatusTreeNew?status=" + status;
        zTreeObj.reAsyncChildNodes(null, "refresh", false);
        //获取所有“站点”节点，刷新之
        // var nodes = zTreeObj.getNodesByParam("level", "3", null);
        // for(i = 0; i < nodes.length; i++){
        //     zTreeObj.reAsyncChildNodes(nodes[i], "refresh", false);
        // }
    };

    //20s刷新一次设备状态树
    window.setInterval(refreshStatusTree, 20 * 1000);

    $("[data-param-get]").click( function () {
        var panelId = $(this).data("param-get");
        refurbishParam( $(panelId) );
    });

    function refurbishParam( em ) {
        //var deviceSN = $("#device-sn").val();
        var actionName = em.data("get-action-name");

        em.after('<div class="overlay"><i class="fa fa-refresh fa-spin"></i></div>');

        oamFunction( deviceSn, actionName, "", function ( data) {

            //赋值，
            switch ( $.trim(data.actionName) ){
                case "queryVersion":
                    assignVersionParam( data.parameter);
                    break;
                case "queryPAStatus":
                    assignPAStatusParam( data.parameter);
                    break;
                case "queryPAInfo":
                    assignPAInfoParam( data.parameter);
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

    function oamFunction( deviceSN, actionName, param, successFun, failFun  ) {

        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: "/device/deviceAction",
            data:{ 'deviceSN': deviceSN, actionName: actionName, actionParam: param },
            timeout: 100000,
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

    function assignVersionParam( data ) {
        $("#软件版本").text(replaceNullOfStr(data.softwareversion));
        $("#FPGA版本").text(replaceNullOfStr(data.fpgaversion));
        // $("#version-param-fpgaversion").text(data.fpgaversion);
        // $("#version-param-bbuversion").text(data.bbuversion);
        // $("#version-param-softwareversion").text(data.softwareversion);
        // if( 0 == data.license ) {
        //     $("#version-param-license").text("许可正常");
        // } else {
        //     $("#version-param-license").text("许可已过期");
        // }
    }

    /**
     * 功放状态
     * @param data
     */
    function assignPAStatusParam( data ) {
        if( 0 == data.valid ){
            $("#是否有效").text("无效");
        } else if( 1 == data.valid  ){
            $("#是否有效").text("有效");
        } else {
            $("#是否有效").text("485连接异常");
        }

        if( 0 == data.warnpa ){
            $("#故障告警").text("正常");
        } else {
            $("#故障告警").text("告警");
        }
        if( 0 == data.warnstandingwaveratio ){
            $("#驻波比告警").text("正常");
        } else {
            $("#驻波比告警").text("告警");
        }
        if( 0 == data.warntemp ){
            $("#过温告警").text("正常");
        } else {
            $("#过温告警").text("告警");
        }
        if( 0 == data.warnpower ){
            $("#过功率告警").text("正常");
        } else {
            $("#过功率告警").text("告警");
        }
        if( 0 == data.onoffpa ){
            $("#功放开关").text("关");
        } else {
            $("#功放开关").text("开");
        }

        $("#反向功率").text( data.inversepower + "dbm");
        $("#功放温度").text( data.temp + "°C");
        $("#ALC").text( data.alcvalue + "dbm");
        $("#驻波比").text( (Number(data.standingwaveratio)/10) );
        $("#功放衰减").text( data.currattpa );
        $("#前向功率").text( data.forwardpower );
        $("#前向功率2").text( data.forwardpower2);
    }

    /**
     * 功放信息
     * @param data
     */
    function assignPAInfoParam( data ) {
        $("#功放个数").text( data.pacount );
        $("#额定输出功率").text( data.power );
        $("#功放序号").text( data.panum );
        $("#频带").text( data.band );
        if( 0 == data.en485 ) {
            $("#485接口").text( "未开启" );
        } else {
            $("#485接口").text( "开启" );
        }
        $("#485接口的地址").text( data.addr485 );
        $("#供应商").text( data.provider );
        $("#序列号").text( data.sn );
        $("#出厂文件").text( data.factory );
    }

    /**
     * 替换掉字符串中的\u0000
     * @param String
     * @returns {*|{by}|undefined|void}
     */
    function replaceNullOfStr(String){
        return String.replace(/\0/g, '');
    }

});