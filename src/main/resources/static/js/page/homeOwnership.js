/**
 * Created by jiangqi.yang on 2016/11/9.
 */
var addSiteIds = [];
function delReceiverRow( row ) {
    $(row).parent().parent().remove();
}

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    }

    /*$.ajax({
       type: "post",
       url: "/soMapping/queryAllSiteByUserId",//通过用户ID查询对应的站点信息
       dataType:"json",
       success:  function( data ){
           if( true == data.status ) {
               var siteData = $.map( data.data, function (obj) {
                  obj.text = obj.name;
                  obj.id = obj.id;
                   return obj;
               });
               $("#site").select2({
                   language: "zh-CN",
                   data: siteData
               });
               $("#upsite").select2({
                   language: "zh-CN",
                   data: siteData
               });
           }
       }
     });*/

    //好用的zTree插件
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    $(document).ready(function(){
        $.ajax({
            type: "post",
            url: "/soMapping/queryZtree",
            dataType: "json",
            success: function(data){
                $.fn.zTree.init($("#treeDemo"), setting, data);
                $.fn.zTree.init($("#up-treeDemo"), setting, data);
                setting.check.chkboxType = { "Y" : "s", "N" : "s" };
            }
        });
    });

    $('#site-ok').click(function(){
        //ztree初始化时候，已经选择的站点addSiteIds赋值给zTree
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        for(var i = 0;i<addSiteIds.length;i++){
            var node = treeObj .getNodeByParam('id',addSiteIds[i]);
            treeObj.checkNode(node, true, true);
        }
        $('#site-dlg').modal('show');
    });

    //获取选中的站点ID
    var nodes;
    var x = 0;
    $('#site_submit').click(function(){
        addSiteIds = new Array();
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        nodes = treeObj.getCheckedNodes(true);
        var sites = 0;
        var siteNames = '';
        for(var i = 0;i < nodes.length;i++){
            if(nodes[i].id < 100000){
                sites ++;
                siteNames += nodes[i].name + ';';
                addSiteIds[addSiteIds.length] = nodes[i].id;
            }
        }
        $("#siteName").html(siteNames);
        $('#site-name').val('您选择了'+ sites +'个站点');
        $('#site-dlg').modal('hide');
    });

    $.ajax({
        type: "post",
        url: "/util/getCityCodes",//查询所有区域信息
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var cityData = $.map( data.data, function (obj) {
                    obj.text = obj.cityName;
                    obj.id = obj.cityCode;
                    return obj;
                });
                $("#ownership").select2({
                    language: "zh-CN",
                    data: cityData
                });
                $("#upownership").select2({
                    language: "zh-CN",
                    data: cityData
                });
            }
        }
    });

    //时间样式
     var alarmRangeTime = $('#alarm-range-time').daterangepicker({
           timePicker: true,
           timePickerIncrement: 1,
           timePicker24Hour: true,
           timePickerSeconds: true,
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


    $("#area").change( function () {
        if( $(this).parents( ".form-group" ).hasClass("has-error"))  {
            homeOwnershipFormValidate.element( "#area" );
        }
    });
    
    var homeOwnershipTable =  $('#homeOwnership-table').DataTable({
         "sDom": "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
                                  "<'row'<'col-sm-12'tr>>" +
                                  "<'row'<'col-sm-1'l><'col-sm-5'i><'col-sm-6'p>>",
         "sServerMethod":"POST",
         "searching":false,
         "bFilter":true,
         "bDestroy": true,
         "bServerSide": true,
         "bProcessing":true,
         "bSort":false,
         "sAjaxDataProp" : "dataResult",
         "sAjaxSource": "/soMapping/queryAllGroup",
         "fnServerParams": function ( aoData ) {},
         "select": {
            style: "multi",
            selector: "td:first-child"
        },
        "columns": [
            {
                "orderable":      false,
                "data":           null,
                "defaultContent": ""
            },
            { "data": "name" },
            { "data": "creatorName" },
            /*{ "data": "site" },*/
            {
                "data": "site",
                "render" : function(data, type, full) {
                    if(data == "")
                        return '';
                    var array = new Array();
                    array = data.split(";");
                    var html = '';
                    if(array.length-1 >= 10){
                        for(var i = 0;i<array.length-1;i++){
                            if(i <= 9){
                                html += array[i] + ';';
                            }
                        }
                        if(array.length-1 > 10){
                            html += '<span style="font-weight : 900">... ...共' + (array.length-1) + '个站点</span>';
                        }
                    }else{
                        for(var i = 0;i<array.length-1;i++) {
                            if (i < 10) {
                                html += array[i] + ';';
                            }
                        }
                    }
                    return html;
                }
            },
            { "data": "ownership"},
            {
              "data": "warning",
              "render" : function(data, type, full) {
                if(data == "")
                    return '';
                var arr = $.parseJSON(data);
                var html = '';
                for(var j = 0; j < arr.length; ++j) {
                  html += "姓名:" + arr[j].name + ' 电话:' + arr[j].phone + ' 邮箱:' + arr[j].email;
                  if(j < arr.length - 1)
                    html += '<br>';
                }
                return html;
              }
            },//预警接收人信息*/
            { "data": "createTime" },
            { "data": "startTime"},
            { "data": "overdueTime"},
            { "data": "remark" },
            {
                "orderable": false,
                "data": null,
                "render" : function (data, type, row ) {
                    var dom = '<a  href="###" class="btn btn-primary blacklist_xg">修改</a>';
                    return dom;
                }
            }
        ],
        columnDefs: [
            {
                orderable: false,
                className: 'select-checkbox',
                targets:   0
            },
        ],
        order: [[ 1, 'asc' ]],
        "oLanguage" : dataTable_language,
        buttons: [
            {
                text: "创建分组",
                className: "btn bg-navy btn-flat",
                action: function () {
                    //清空select2下拉框
                    //$("#site").val(null).trigger("change");
                    $("#ownership").val(null).trigger("change");
                    $("#siteName").html('请点击上方按钮选择检测站点');
                    //每次重新添加分组的时候zTree初始化
                    addSiteIds = new Array();
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    treeObj.checkAllNodes(false);
                    nodes = null;
                    $("#homeOwnership-receiver-table tbody").empty();
                    $("#homeOwnership-add-receiver-table tr:eq(0)").removeClass("hidden");
                    $("#homeOwnership-add-receiver-table tr:eq(1)").addClass("hidden");
                    $("#homeOwnership-form")[0].reset();
                    $("#homeOwnership-dlg").modal("show");
                }
            },
            {
                text: "删除分组",
                className: "btn bg-navy btn-flat",
                action: function () {
                    var count = homeOwnershipTable.rows( { selected: true } ).count();
                    if( 0 == count ) {
                        bootbox.alert({
                            size: "small",
                            message:"请选择要删除的分组"
                        });
                        return;
                    }

                    var rowData = homeOwnershipTable.rows( { selected: true }).data();

                    bootbox.confirm({
                        size: "small",
                        message: "是否删除分组",
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

                                var idArray = [];
                                $.each(rowData, function ( i,val ) {
                                    idArray.push( val.id);
                                });

                                $.ajax({
                                    type: "post",
                                    url: "/soMapping/delGroupByIds",
                                    data: { 'ids':  JSON.stringify(idArray) },
                                    dataType:"json",
                                    success:  function( data ){
                                        if( true == data.status ) {
                                            Messenger().post({
                                                message: data.message,
                                                type: 'success',
                                                showCloseButton: true
                                            });
                                            homeOwnershipTable.ajax.reload(null,false);
                                        } else {
                                            Messenger().post({
                                                message: data.message,
                                                type: 'error',
                                                showCloseButton: true
                                            });
                                        }
                                    }
                                });

                            }
                        }
                    });
                }

            }
        ],
        "dom": "<'row'<'col-sm-6'B><'col-sm-6'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });

    var receiverFormValidate = $( "#receiver-form" ).validate( {
        rules: {
            name: {
                required: true,
                stringCheck: true
            },
            phone:{
                required: true,
                isMobile: true
            },
            email: {
                email: true
            }
        },
        messages: {
            name: {
                required: "请输入姓名",
                stringCheck: "只能包括中文字、英文字母、数字和下划线!"
            },
            phone:{
                required: "请输入手机号码",
                isMobile: "请输入正确格式的手机号码"
            },
            email: {
                email: "请输入正确格式的电子邮箱"
            }
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            error.addClass( "help-block" );
            error.insertAfter( element );
        },
        highlight: function ( element, errorClass, validClass ) {
            $( element ).parent().addClass( "has-error" ).removeClass( "has-success" );
        },
        unhighlight: function (element, errorClass, validClass) {
            if( validClass && ("valid" == validClass) ) {
                $( element ).parent().addClass( "has-success" ).removeClass( "has-error" );
            } else {
                $( element ).parent().removeClass( "has-success" );
                $( element ).parent().removeClass( "has-error" );
            }
        }
    } );


    $('#homeOwnership-table tbody').on( 'click', 'tr a.homeOwnership-receiver-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = homeOwnershipTable.row( tr ).data();
        if( rowData ) {
            $("#receiver-table tbody").empty();
            $.each(rowData.receivers,function ( i, receiver ) {
                var trDom = ['<tr>'];
                trDom.push('<td>' + receiver.name + '</td>' );
                trDom.push('<td>' + receiver.phone + '</td>' );
                trDom.push('<td>' + receiver.email + '</td>' );
                trDom.push('<td><a href="###" onclick="delReceiverRow(this)" >删除</a></td>' );
                trDom.push('</tr>');
                $("#receiver-table tbody").prepend(trDom.join(''));
            });

            $("#receiver-dlg input[name=id]").val(rowData.id);

        }

        $("#receiver-dlg").modal("show");
    });

    $("#add-receiver-btn").click(function () {
        $("#add-receiver-table tr:eq(0)").addClass("hidden");
        $("#add-receiver-table tr:eq(1)").removeClass("hidden");
    });

    $("#add-receiver-cancel").click(function () {
        $("#add-receiver-table tr:eq(0)").removeClass("hidden");
        $("#add-receiver-table tr:eq(1)").addClass("hidden");
    });


    $("#add-receiver-ok").click(function () {
        if( receiverFormValidate.form()){
            var name = $("#receiver-form input[name=name]").val();
            var phone = $("#receiver-form input[name=phone]").val();
            var email = $("#receiver-form input[name=email]").val();

            var trDom = ['<tr>'];
            trDom.push('<td>' + name + '</td>' );
            trDom.push('<td>' + phone + '</td>' );
            trDom.push('<td>' + email + '</td>' );
            trDom.push('<td><a href="###" onclick="delReceiverRow(this)" >删除</a></td>' );
            trDom.push('</tr>');
            $("#receiver-table tbody").prepend(trDom.join(''));

            $("#receiver-form")[0].reset();
            receiverFormValidate.resetForm();
        }
    });

    $("#change-receiver-confirm").click(function () {
        var id = $("#receiver-dlg input[name=id]").val();
        var receivers=[];
        $("#receiver-table tbody tr").each(function () {
            var name  = $(this).find("td:eq(0)").text();
            var phone = $(this).find("td:eq(1)").text();
            var email = $(this).find("td:eq(2)").text();

            var receive = { name: name , phone: phone , email: email };
            receivers.push(receive);
        });

        $("#receiver-dlg").modal('hide');
        $.ajax({
            type: "post",
            url: "/target/updateReceivers",
            data: { type: 1,  id:  id, receivers: JSON.stringify(receivers) },
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    Messenger().post({
                        message: data.message,
                        type: 'success',
                        showCloseButton: true
                    });
                    homeOwnershipTable.ajax.reload(null,false);
                } else {
                    Messenger().post({
                        message: data.message,
                        type: 'error',
                        showCloseButton: true
                    });
                }
            }
        });

    });


    var homeOwnershipReceiverFormValidate = $( "#homeOwnership-receiver-form" ).validate( {
        rules: {
            name: {
                required: true,
                stringCheck: true
            },
            phone:{
                required: true,
                isMobile: true
            },
            email: {
                email: true
            }
        },
        messages: {
            name: {
                required: "请输入姓名",
                stringCheck: "只能包括中文字、英文字母、数字和下划线!"
            },
            phone:{
                required: "请输入手机号码",
                isMobile: "请输入正确格式的手机号码"
            },
            email: {
                email: "请输入正确格式的电子邮箱"
            }
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            // Add the `help-block` class to the error element
            error.addClass( "help-block" );
            error.insertAfter( element );

        },
        highlight: function ( element, errorClass, validClass ) {
            $( element ).parent().addClass( "has-error" ).removeClass( "has-success" );
        },
        unhighlight: function (element, errorClass, validClass) {
            if( validClass && ("valid" == validClass) ) {
                $( element ).parent().addClass( "has-success" ).removeClass( "has-error" );
            } else {
                $( element ).parent().removeClass( "has-success" );
                $( element ).parent().removeClass( "has-error" );
            }
        }
    } );

    //添加通知人取消按钮
    $("#update-add-receiver-cancel").click(function () {
        $("#update-add-receiver-table tr:eq(0)").removeClass("hidden");
        $("#update-add-receiver-table tr:eq(1)").addClass("hidden");
        blackListReceiverForm1.reset();
        $("#update-receiver-form input").val('');
    });

    $("#update-add-receiver-btn").click(function () {
        $("#update-add-receiver-table tr:eq(0)").addClass("hidden");
        $("#update-add-receiver-table tr:eq(1)").removeClass("hidden");
    });

    $("#homeOwnership-add-receiver-btn").click(function () {
        $("#homeOwnership-add-receiver-table tr:eq(0)").addClass("hidden");
        $("#homeOwnership-add-receiver-table tr:eq(1)").removeClass("hidden");
    });

    $("#homeOwnership-add-receiver-cancel").click(function () {
        $("#homeOwnership-add-receiver-table tr:eq(0)").removeClass("hidden");
        $("#homeOwnership-add-receiver-table tr:eq(1)").addClass("hidden");
        $("#homeOwnership-receiver-form input").val('');
    });


    $("#homeOwnership-add-receiver-ok").click(function () {
        if( homeOwnershipReceiverFormValidate.form()){
            var name = $("#homeOwnership-receiver-form input[name=name]").val();
            var phone = $("#homeOwnership-receiver-form input[name=phone]").val();
            var email = $("#homeOwnership-receiver-form input[name=email]").val();

            var trDom = ['<tr>'];
            trDom.push('<td>' + name + '</td>' );
            trDom.push('<td>' + phone + '</td>' );
            trDom.push('<td>' + email + '</td>' );
            trDom.push('<td><a href="###" onclick="delReceiverRow(this)" >删除</a></td>' );
            trDom.push('</tr>');
            $("#homeOwnership-receiver-table tbody").prepend(trDom.join(''));

            $("#homeOwnership-receiver-form")[0].reset();
            homeOwnershipReceiverFormValidate.resetForm();
        }
    });


    var homeOwnershipFormValidate = $( "#homeOwnership-form" ).validate( {
        rules: {
            name: {
                required: true,
                stringCheck: true
            },
            area:{
                required: true
            }
        },
        messages: {
            name: {
                required: "请输入名单名称",
                stringCheck: "只能包括中文字、英文字母、数字和下划线!"
            },
            area:{
                required: "请选择预警归属地"
            }
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            // Add the `help-block` class to the error element
            error.addClass( "help-block" );
            if ( element.prop( "type" ) === "checkbox" ) {
                error.insertAfter( element.parent( "label" ) );
            } else if( element[0].tagName == "SELECT") {
                error.insertAfter( element.next( "span" ) );
            } else {
                error.insertAfter( element );
            }
        },
        highlight: function ( element, errorClass, validClass ) {
            $( element ).parents( ".form-group" ).addClass( "has-error" ).removeClass( "has-success" );
        },
        unhighlight: function (element, errorClass, validClass) {
            if( validClass && ("valid" == validClass) ) {
                $( element ).parents( ".form-group" ).addClass( "has-success" ).removeClass( "has-error" );
            } else {
                $( element ).parents( ".form-group" ).removeClass( "has-success" );
                $( element ).parents( ".form-group" ).removeClass( "has-error" );
            }
        }
    } );

    //添加分组
    $("#homeOwnership-ok-btn").click(function () {
        if( homeOwnershipFormValidate.form() )  {
            //取值
            var name = $("#name").val();
            if(addSiteIds.length <= 0){
                Messenger().post({
                    message: "请选择检测站点",
                    type: 'error',
                    showCloseButton: true
                });
                return;
            }
            var ownershipData = $("#ownership").val();//预警归属地
             if(ownershipData == null) {
                  Messenger().post({
                       message: "请选择预警归属地!",
                       type: 'error',
                       showCloseButton: true
                   });
                  return;
               }
            var startDate1 = $("#alarm-range-time").val().slice(0,19);//取开始时间
            if(startDate1 == '') {
                Messenger().post({
                    message: "请选择有效期时间",
                    type: 'error',
                    showCloseButton: true
                });
                return;
            }
            startDate = startDate1.split('/').join('').split(':').join('').split(' ').join('');
            var endDate1 = $("#alarm-range-time").val().slice(22,41);//取结束时间
            endDate = endDate1.split('/').join('').split(':').join('').split(' ').join('');
            var remark = $("#remark").val();
            var receivers=[];
            $("#homeOwnership-receiver-table tbody tr").each(function () {
                var receiverName  = $(this).find("td:eq(0)").text();
                var receiverPhone = $(this).find("td:eq(1)").text();
                var receiverEmail = $(this).find("td:eq(2)").text();
                var receive = { name: receiverName , phone: receiverPhone , email: receiverEmail };
                receivers.push(receive);
            });

            $.ajax({
                type: "post",
                url: "/soMapping/addASGroup",
                traditional:true,
                data: { name:  name, siteData:addSiteIds, ownershipData: ownershipData, startDate: startDate,
                        endDate:endDate, remark,remark, receivers: JSON.stringify(receivers) },
                dataType:"json",
                success:  function( data ){
                    if( true == data.status ) {
                        Messenger().post({
                            message: data.message,
                            type: 'success',
                            showCloseButton: true
                        });
                        $("#homeOwnership-dlg").modal("hide");
                        homeOwnershipTable.ajax.reload(null,false);
                    } else {
                        Messenger().post({
                            message: data.message,
                            type: 'error',
                            showCloseButton: true
                        });
                    }
                }
            });
        }
    });

    //////////////////////////////////////////修改/////////////////////////////////////////////
     var site;
     var ownership;
     var groupIdUp;
     var upNodesIds = [];
    $('#homeOwnership-table tbody').on( 'click', 'tr a.blacklist_xg', function () {
         //所有样式初始化
         $("#update-table tbody").empty();
         $("#update-form")[0].reset();
         $("#update-dlg").modal("show");
         $("#update-add-receiver-table tr:eq(0)").removeClass("hidden");
         $("#update-add-receiver-table tr:eq(1)").addClass("hidden");
         recordMsgReceiverBuffer = new Array();
         var tr = $(this).closest('tr');
         //拿到这个分组的所有信息赋值
         var obj = homeOwnershipTable.row( tr ).data();
         $("#upname").val(obj.name);
         $("#upremark").val(obj.remark);
         //获取站点对应的ID并赋值给select2站点赋值
        var siteData = obj.site.split(';');
        $('#up-site-name').val('您选择了'+ (siteData.length-1) +'个站点');
        var upSiteNames = '';
        for(var i = 0;i < siteData.length-1;i++){
            upSiteNames += siteData[i]+";";
        }
        $('#up-siteName').html(upSiteNames);
        var upSetting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };
        var siteInfo = obj.site;
        var array = new Array();
        var aa = siteInfo.split(";").pop().replace('[','').replace(']','');//[46,67]
        array = aa.split(',');
        var treeObj = $.fn.zTree.getZTreeObj("up-treeDemo");
        for(var i = 0;i < array.length;i++){
            upNodesIds[i] = array[i];
            var node = treeObj .getNodeByParam('id',array[i]);
            if(node != null){
                treeObj.checkNode(node, true, true);
            }
        }
        //获取站点对应的ID并赋值给select2归属地赋值
        var ownershipNames = [];//[52,50,51]
        $.ajax({
            type: "post",
            data: { groupId : obj.id },
            url: "/soMapping/queryOwnershipIdByGroupId",//通过站点名字查询对应的站点ID
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    ownershipNames = data.ownershipNames;
                    $("#upownership").val(ownershipNames).trigger('change');
                } else {
                    Messenger().post({
                        message: message,
                        type: 'error',
                        showCloseButton: true
                    });
                }
            }
        });

        //给时间插件赋值
        var alarmRangeTime = $('#up-alarm-range-time').daterangepicker({
            timePicker: true,
            timePickerIncrement: 1,
            timePicker24Hour: true,
            timePickerSeconds: true,
            //给时间插件默认值
            startDate:obj.startTime,
            endDate:obj.overdueTime,
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
        if(obj.warning == ""){
            jsonObj = '';
        }else{
            jsonObj = JSON.parse(obj.warning);
        }
           groupIdUp = obj.id;
          //给通知人赋值
         var html = '';
        if(jsonObj != null){
            for(var x = 0; x < jsonObj.length; x++) {
                recordMsgReceiverBuffer[recordMsgReceiverBuffer.length] = {
                       name : jsonObj[x].name,
                       phone : jsonObj[x].phone,
                       email : jsonObj[x].email,
                }
                showMsgReceiverRecordsHtml();
             }
              $("#update-receiver-form")[0].reset();
        }
    });

    $('#up-site-ok').click(function(){
        //ztree初始化
        var treeObj = $.fn.zTree.getZTreeObj("up-treeDemo");
        treeObj.checkAllNodes(false);
        for(var i = 0;i < upNodesIds.length;i++){
            var node = treeObj .getNodeByParam('id',upNodesIds[i]);
            if(node != null){
                treeObj.checkNode(node, true, true);
            }
        }
        $('#up-site-dlg').modal('show');
    });

    //获取选中的站点ID
    $('#up-site_submit').click(function(){
        var treeObj = $.fn.zTree.getZTreeObj("up-treeDemo");
        upNodes = treeObj.getCheckedNodes(true);
        var sites = 0;
        var siteNames = '';
        upNodesIds.length = 0;
        var x = 0;
        for(var i = 0;i < upNodes.length;i++){
            if(upNodes[i].id < 100000){
                sites ++;
                siteNames += upNodes[i].name + ';';
                upNodesIds[x] = upNodes[i].id;
                x++;
            }
        }
        $("#up-siteName").html(siteNames);
        $('#up-site-name').val('您选择了'+ sites +'个站点');
        $('#up-site-dlg').modal('hide');
    });

     //提交修改分组信息
     $("#update-ok-btn").click(function (){
         homeOwnershipTable.ajax.reload(null,false);
        //取值
         var name = $("#upname").val();
         var newownership = $("#upownership").val();
             if(newownership == null) {
                 Messenger().post({
                    message: "请选择预警归属地",
                    type: 'error',
                    showCloseButton: true

                 });
                return;
             }
         var warning = JSON.stringify(recordMsgReceiverBuffer);
         var startDate1 = $("#up-alarm-range-time").val().slice(0,19);//取开始时间
         startDate = startDate1.split("/").join("").split(":").join("").split(" ").join("");
         var endDate1 = $("#up-alarm-range-time").val().slice(22,41);//取结束时间
         endDate = endDate1.split("/").join("").split(":").join("").split(" ").join("");
         var remark = $("#upremark").val();//备注

         $.ajax({
             type: "post",
             url: "/soMapping/updateGroup",
             traditional:true,
             data: { groupIdUp:groupIdUp, name:name, newSite:upNodesIds, newownership:newownership, warning:warning,  startDate:startDate,  endDate:endDate,  remark:remark },
             dataType:"json",
             success:  function( data ){
                 if( true == data.status ) {
                     Messenger().post({
                         message: data.message,
                         type: 'success',
                         showCloseButton: true
                     });
                      $("#update-dlg").modal("hide");
                     homeOwnershipTable.ajax.reload(null,false);
                 } else {
                     Messenger().post({
                         message: data.message,
                         type: 'error',
                         showCloseButton: true
                     });
                 }
             }
         });
     });

      //修改通知人信息放入缓存
    $("#update-add-receiver-ok").click(function () {
        if(!blackListReceiverForm1.form()) {
          return;
        }
        var name = $("#update-receiver-form input[name=name]").val();
        if(name == ''){
            Messenger().post({
               message: "请输入通知人姓名!",
               type: 'error',
               showCloseButton: true
            });
            return;
        }
        var phone = $("#update-receiver-form input[name=phone]").val();
        if(phone == ''){
            Messenger().post({
                 message: "请输入手机号码!",
                 type: 'error',
                 showCloseButton: true
            });
            return;
        }
        //验证手机号码格式是否正确
        var r = /^\+?[1-9][0-9]*$/;
        var b =  r.test(phone);
        if(phone.length != 11 || !b){
            Messenger().post({
                message: "请输入正确格式的手机号码!",
                type: 'error',
                showCloseButton: true
            });
            return;
        }
        var email = $("#update-receiver-form input[name=email]").val();
        for(var j = 0; j < recordMsgReceiverBuffer.length; ++j) {
            if(phone == recordMsgReceiverBuffer[j].phone){
                Messenger().post({
                     message: "手机号码重复!",
                     type: 'error',
                     showCloseButton: true
                });
                return;
            }
        }

        recordMsgReceiverBuffer[recordMsgReceiverBuffer.length] = {
            name : name,
            phone : phone,
            email : email
        }

        showMsgReceiverRecordsHtml();
        $("#update-receiver-form")[0].reset();
      });

});
   var recordMsgReceiverBuffer = new Array();
   function delMsgReceiverRecordsRow(phone) {
        for(var j = 0; j < recordMsgReceiverBuffer.length; ++j) {
            if(recordMsgReceiverBuffer[j].phone == phone) {
                recordMsgReceiverBuffer.splice(j, 1);
            }
        }
        showMsgReceiverRecordsHtml();
    }

   function showMsgReceiverRecordsHtml() {
      var html = '';
      for(var j = 0; j < recordMsgReceiverBuffer.length; ++j) {
          html += '<tr>'
              + '<td>' + recordMsgReceiverBuffer[j].name + '</td>'
              + '<td>' + recordMsgReceiverBuffer[j].phone + '</td>'
              + '<td>' + recordMsgReceiverBuffer[j].email + '</td>'
              + '<td><a style="cursor:pointer;" onclick="delMsgReceiverRecordsRow(\'' + recordMsgReceiverBuffer[j].phone + '\')" >删除</a></td>'
              + '</tr>'
      }
      $("#update-receiver-table tbody").html('');
      $("#update-receiver-table tbody").append(html);
   }

   //消息接收人校验
   blackListReceiverForm1 = $("#update-receiver-form").validate({
       rules: {
           name: {
               required: true,
               stringCheck: true
           },
           phone : {
               required: true,
               isMobile: true
           },
           email: {
               email: true
           }
       },
       messages: {
           name: {
               required: "请输入分组名称",
               stringCheck: "只能包括中文字、英文字母、数字和下划线!"
           },
           phone : {
               required: "请输入手机号",
               isMobile: "请输入正确格式的手机号码"
           },
           email: {
               email: "请输入正确格式的电子邮箱"
           }
       },
       errorElement: "em",
       errorPlacement: function ( error, element ) {
           error.addClass( "help-block" );
           error.insertAfter( element );

       },
       highlight: function ( element, errorClass, validClass ) {
           $( element ).parent().addClass( "has-error" ).removeClass( "has-success" );
       },
       unhighlight: function (element, errorClass, validClass) {
           if( validClass && ("valid" == validClass) ) {
               $( element ).parent().addClass( "has-success" ).removeClass( "has-error" );
           } else {
               $( element ).parent().removeClass( "has-success" );
               $( element ).parent().removeClass( "has-error" );
           }
       }
   });


