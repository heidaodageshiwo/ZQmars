/**
 * Created by jiangqi.yang on 2016/11/3.
 */

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    }

    var usersTable = $('#users-table').DataTable({
        paging: true,
        lengthChange: false,
        searching: true,
        ordering: false,
        info: false,
        autoWidth: false,
        processing: true,
        ajax: "/user/getUsers",
        dataSrc: "",
        rowId: "loginName",
        select: true,
        displayLength: 30,
        scrollY: 700,
        columns: [
            {
                "orderable": false,
                "data": "userName"
            }
        ],
        language: {
            "lengthMenu": "每页显示 _MENU_ 记录",
            "zeroRecords": "对不起-没有数据发现",
            "info": "显示第_PAGE_页, 共_PAGES_页",
            "infoEmpty": "没有数据记录",
            "infoFiltered": "(从总_MAX_条记录中过滤)",
            "sEmptyTable":"表中没有有效数据",
            "paginate": {
                "first": "最后一页",
                "last": "第一页",
                "next": "下一页",
                "previous": "上一页"
            },
            "sSearch": "搜索:",
            "sSearchPlaceholder": "请输入过滤条件",
            select: {
                rows: {
                    _: "选中 %d 行",
                    1: "选中 1 行"
                }
            }
        },
        dom: "<'row'<'col-sm-6'f><'col-sm-6'>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row '<'col-sm-12'p>>"
    });

/*
    function assignDevice ( node, type ) {

        if( node.nodes && ( node.nodes.length > 0 ) ){
            //有叶子节点

            $.each( node.nodes, function ( i, childNode ) {
                if( "unchecked" == type ) {
                    $('#devices-tree').treeview('uncheckNode', [ childNode.nodeId, { silent: true } ]);
                } else {
                    $('#devices-tree').treeview('checkNode', [ childNode.nodeId, { silent: true } ]);
                }
                assignDevice( childNode, type );
            });
        } else {
            //判断是否是设备节点
            if( node.sn ){
                var userData = usersTable.row('.selected').data();
                var deviceId = node.id;
                var actionType = 0;
                if( "checked" == type ) {
                    actionType = 1;
                }

                $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: "/device/assignDevice",
                    data: { action: actionType, userId: userData.id, siteId: deviceId},
                    timeout: 5000,
                    success: function ( data ) {
                        if( true == data.status ) {
                        } else {
                            $('#devices-tree').treeview('toggleNodeChecked', [ node.nodeId, { silent: true } ]);
                            Messenger().post({
                                message:  data.message,
                                type: 'error',
                                showCloseButton: true
                            }) ;
                        }
                    }
                });

            }
        }
    }

    var initSelectableTree = function( jsonData) {
        return $('#devices-tree').treeview({
            data: jsonData,
            showCheckbox: true,
            levels: 5,
            showBorder: true,
            showTags: true,
            onNodeChecked: function(event, node) {
                assignDevice( node, "checked");
            },
            onNodeUnchecked: function (event, node) {
                assignDevice( node, "unchecked");
            }
        });
    };

    initSelectableTree( [] );

   usersTable.on( 'select', function ( e, dt, type, indexes ) {

        if ( type === 'row' ) {
            var data = usersTable.row( indexes[0] ).data();
            if(data) {
                $("#devices-tree").treeview(true).remove();
                $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: "/device/getAllDeviceTree",
                    data: { id: data.id },
                    timeout: 5000,
                    success: function ( data ) {
                        if( true == data.status ) {
                            initSelectableTree(data.data);
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
        }

    } );*/
 //好用的zTree插件
    var setting = {
        view: {
            selectedMulti: false
        },
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
           // beforeCheck: beforeCheck,
            onCheck: treenodeClick/*,
            onCheck: onCheck*/
        }
    };
    usersTable.on( 'select', function ( e, dt, type, indexes ) {

        if ( type === 'row' ) {
            var data = usersTable.row( indexes[0] ).data();
            if(data) {
                //$("#devices-tree").treeview(true).remove();
                $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: "/sitegroupcontroller/queryZtree",
                    data: { id: data.id },
                    timeout: 5000,
                    success: function ( data ) {
                            $.fn.zTree.init($("#devices-tree"), setting, data);//up-treeDemo
                            data.initCache(setting);
                            setting.check.chkboxType = { "Y" : "s", "N" : "s" };
                    }
                });




            }
        }
    } );

    function treenodeClick(event, treeId, treeNode, clickFlag) {
        //此处treeNode 为当前节点
        var str ='' ;
        str = getAllChildrenNodes(treeNode,str);
        var jq  =str.substring(1,str.length).split(",");
        var variable='';
        for(var i=0;i<jq.length;i++){
            if(jq[i]<1000){
                //alert(jq[i]);
                if(variable.length==0){
                    variable=jq[i];
                }else{
                    variable=variable+","+jq[i];
                }
            }
        }

        var cz;
        if(variable.length==0){
                cz=treeNode.id;
        }else{
                cz=variable;
        }

        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: "/sitegroupcontroller/update",
            data: { id: cz,checked:treeNode.checked,userid:usersTable.row('.selected').data().id },
            timeout: 5000,
            success: function ( data ) {
                if (true == data.status) {
                    Messenger().post({
                        message: data.message,
                        type: 'success',
                        showCloseButton: true
                    });
                }
            }
        });


    }

    function getAllChildrenNodes(treeNode,result){
        if (treeNode.isParent) {
            var childrenNodes = treeNode.children;
            if (childrenNodes) {
                for (var i = 0; i < childrenNodes.length; i++) {
                    result += ',' + childrenNodes[i].id;
                    result = getAllChildrenNodes(childrenNodes[i], result);
                }
            }
        }
        return result;
    }
});