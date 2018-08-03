/**
 * Created by jiangqi.yang on 2016/11/9.
 */


function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    }

    //Make the dashboard widgets sortable Using jquery UI
    $(".connectedSortable").sortable({
        placeholder: "sort-highlight",
        connectWith: ".connectedSortable",
        handle: ".box-header, .nav-tabs",
        forcePlaceholderSize: true,
        zIndex: 999999
    });
    $(".connectedSortable .box-header, .connectedSortable .nav-tabs-custom").css("cursor", "move");

    var initDeviceStatusTree = function( jsonData) {
        return $('#devices-status-tree').treeview({
            data: jsonData,
            injectStyle: false,
            showCheckbox: false,
            levels: 5,
            showBorder: true,
            showTags: true,
            onNodeSelected: function ( event, node) {
                if( node.color && node.type ) {
                    var deviceInfo = node.sn + "-" + node.name;
                    $("#device-info").text(deviceInfo);
                    updateDeviceInfo(node);
                }
            }
        });
    };

    var initDeviceStatus = false;
    function loadDeviceStatusTree() {
        
        var status = getQueryString("status");
        if( null == status ) {
            status = "all";
        }

        var filterText="";
        switch( status ){
            case "run":
                filterText="运行中";
                break;
            case "offline":
                filterText="离线";
                break;
            case "willexpire":
                filterText="即将过期";
                break;
            case "expiredfailure":
                filterText="已过期";
                break;
            case "warning":
                filterText="有告警";
                break;
            default:
                filterText="全部";
        }
        $("#filter-text").text(filterText);

        $.ajax({
            type: 'GET',
            dataType: 'json',
            url: "/device/getDeviceStatusTree",
            data:{ 'filter': status },
            timeout: 5000,
            success: function ( data ) {
                if( true == data.status ) {
                    if( initDeviceStatus ) {
                        $('#devices-status-tree').treeview(true).updateData(data.data);
                    } else {
                        initDeviceStatusTree(data.data);
                        initDeviceStatus = true;
                    }

                } else {
                    Messenger().post({
                        message:  data.message,
                        type: 'error',
                        showCloseButton: true
                    }) ;
                }
            }
        });
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
                rowDom.push('<dd style="word-break:break-all">' + statusValue + '</dd>');
            });
            $("#device-" + name + "-info").empty();
            $("#device-" + name + "-info").prepend(rowDom.join(''));
        }
        
        function updateStatusUI( data ) {

            updateDlList(data, "normal");
            updateDlList(data, "board");
            updateDlList(data, "license");
            updateDlList(data, "pa");
            updateDlList(data, "sniffer");
            updateDlList(data, "debug");
        }

        if( updateDeviceInfoTimer ) {
            window.clearInterval(updateDeviceInfoTimer);
        }
        updateDeviceAjax();
        updateDeviceInfoTimer = window.setInterval( updateDeviceAjax, 30 * 1000);
    }

    loadDeviceStatusTree();

    var t1 = window.setInterval(loadDeviceStatusTree, 20 * 1000);


});