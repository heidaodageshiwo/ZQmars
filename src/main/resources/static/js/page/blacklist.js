/**
 * Created by jiangqi.yang  on 2016/11/8.
 */
var addSiteIds = [];
var jsonObj;
var rowDataRen = '';
function delReceiverRow( imsi ) {
    for(var j = 0; j < recordBuffer.length; ++j) {
        if(recordBuffer[j].phone == imsi) {
            recordBuffer.splice(j, 1);
        }
    }

    showRecordsHtml();
}
$(function () {
    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    }

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

    $.ajax({
         type: "post",
         url: "/target/queryAllSiteByUserId",//通过用户ID查询对应的站点信息
         dataType:"json",
         success:  function( data ){
             if( true == data.status ) {
                 var siteData = $.map( data.data, function (obj) {
                    obj.text = obj.name;
                    obj.id = obj.id;
                     return obj;
                 });
                // siteData.unshift({ id:0 , text: "--站点名称--" });
               //  siteData.unshift({ id:1 });
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
     });

    $("#importblacklist-ok-btn").click(function (e) {
    });

   $('#blacklist-table tbody').on( 'click', 'tr a.blacklist_dr', function () {
         $('#importblacklist-form')[0].reset();
         $('#importblacklist-dlg').modal('show');
         var tr = $(this).closest('tr');
         var groupId = blacklistTable.row( tr ).data().id;
         $('#uploadFile').fileupload({
                 url: "/target/addExcelTarget",//Excel导入黑名单
                 paramName: "uploadFile",
                 formData:{groupId:groupId},//用来传递传分组id
                 dataType: 'json',
                 done: function (e, data) {
                     if( true == data.result.status) {
                      Messenger().post({
                            message: data.result.message,
                            type: 'success',
                            showCloseButton: true
                        });
                       $('#importblacklist-dlg').modal('hide');
                       blacklistTable.ajax.reload();
                     } else {
                         Messenger().post({
                             message: data.result.message,
                             type: 'error',
                             showCloseButton: true
                         });
                     }
                 },
                 progressall: function (e, data) {
                     var progress = parseInt(data.loaded / data.total * 100, 10);

                     $("#upload-progress .progress-bar").css(
                         'width',
                         progress + '%'
                     );
                     $("#upload-progress .progress-bar").attr("aria-valuenow", progress);
                     $("#upload-progress .progress-bar span").text(  progress + '% Complete' );

                 },
                 start: function ( e) {
                     $("#filename").val("");
                     $("#uploadFilename").val("");
                     $("#upload-progress").show();
                 },
                 stop: function ( e ) {
                     $("#upload-progress").hide();
                    // importblacklistFormValidate.element( "#filename" );
                 }
             }).prop('disabled', !$.support.fileInput)
                 .parent().addClass($.support.fileInput ? undefined : 'disabled');
   });
    var detailsDataTable;
    $('#blacklist-table tbody').on( 'click', 'tr a.blacklist_xq', function () {

        var tr = $(this).closest('tr');
        var groupId = blacklistTable.row( tr ).data().id;
        $("#receiver-dlg input[name=id]").val(rowDataRen.id);
        $("#receiver-dlg").modal("show");
        detailsDataTable = $('#receiver-table').DataTable( {
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
            "sAjaxSource": "/target/queryBlacklist",
            "fnServerParams": function ( aoData ) { aoData.push( { "name": "formData", "value": groupId} ); },
            "select": {
                style: "multi",
                selector: "td:first-child"
                },
            columns: [
                 { "data": "name" },
                 { "data": "imsi" },
                 { "data": "idCard" },
                 { "data": "phone" },
                 { "data": "createTime" },
                 { "data": "remark" },
                 {
                     "orderable":      false,
                     "data":           null,
                     "render" : function (data, type, row ) {
                         var detailDom ='<a href="javascript:;" data-taskid="' + data.id + '" class="btn btn-flat btn-sm bg-navy email-detail-btn">删除</a>';
                         return detailDom;
                     }
                 }
            ],
            buttons:[],
            "oLanguage" : dataTable_language,
        });
    });

    $('#receiver-table tbody').on( 'click', 'tr a.email-detail-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = detailsDataTable.row( tr ).data();
        var messageText = '是否删除任务"' + rowData.name + '"';
        bootbox.confirm({
            size: "small",
            message: messageText,
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
                      var imsi = rowData.imsi;
                      var targetRuleId = rowData.id;
                      $.ajax({
                          type: "post",
                          url: "/target/delGroupTargetPerson",//删除分组黑名单人员
                          data: { targetRuleId:targetRuleId,imsi: imsi},
                          dataType:"json",
                          success:  function( data ){
                               if( true == data.status ) {
                                  Messenger().post({
                                      message: data.message,
                                      type: 'success',
                                      showCloseButton: true
                                  });
                                 $("#receiver-table tbody").empty();
                                  detailsDataTable.ajax.reload();
                                  blacklistTable.ajax.reload();
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
    });

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
            url: "/target/queryZtree",
            dataType: "json",
            success: function(data){
                $.fn.zTree.init($("#treeDemo"), setting, data);//up-treeDemo
                $.fn.zTree.init($("#up-treeDemo"), setting, data);//data
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

    //添加分组
    $("#blacklist-ok-btn").click(function () {
        if(addSiteIds.length <= 0){
            Messenger().post({
                message: "请选择检测站点",
                type: 'error',
                showCloseButton: true
            });
            return;
        }
        //取值
        var name = $("#name").val();
        if(name == ''){
            Messenger().post({
                message: "请填写分组名字!",
                type: 'error',
                showCloseButton: true
            });
            return;
        }
        //判断分组名字是否含有非法字符
        var pat=new RegExp("[^a-zA-Z0-9\_\u4e00-\u9fa5]","i");
        var b =  pat.test(name);
        if(b) {
            Messenger().post({
                 message: "分组名字含有非法字符,请更换!",
                 type: 'error',
                 showCloseButton: true
            });
            return;
        }
        var warning = JSON.stringify(recordMsgReceiverBuffer);
        var startDate = $("#alarm-range-time").val().slice(0,19);//取开始时间
        if(startDate == '') {
            Messenger().post({
                message: "请选择有效期时间",
                type: 'error',
                showCloseButton: true
            });
            return;
        }
        startDate = startDate.split("/").join("").split(":").join("").split(" ").join("");
        var endDate = $("#alarm-range-time").val().slice(22,41);//取结束时间
        endDate = endDate.split("/").join("").split(":").join("").split(" ").join("");
        var remark = $("#remark").val();//备注

        $.ajax({
           type: "post",
           url: "/target/addGroup",
           traditional:true,
           data: { name:name, site:addSiteIds,  warning:warning,  startDate:startDate,  endDate:endDate,  remark:remark },
           dataType:"json",
           success:  function( data ){
               if( true == data.status ) {
                    addSiteIds = new Array();
                   Messenger().post({
                       message: data.message,
                       type: 'success',
                       showCloseButton: true
                   });
                   $("#blacklist-dlg").modal("hide");
                   blacklistTable.ajax.reload(null,false);
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


    $("#add-receiver-btn").click(function () {
        $("#add-receiver-table tr:eq(0)").addClass("hidden");
        $("#add-receiver-table tr:eq(1)").removeClass("hidden");
    });

    $("#add-receiver-cancel").click(function () {
        $("#add-receiver-table tr:eq(0)").removeClass("hidden");
        $("#add-receiver-table tr:eq(1)").addClass("hidden");
    });


    $("#blacklist-add-receiver-btn").click(function () {
        $("#blacklist-add-receiver-table tr:eq(0)").addClass("hidden");
        $("#blacklist-add-receiver-table tr:eq(1)").removeClass("hidden");
    });

    $("#blacklist-add-receiver-cancel").click(function () {
        $("#blacklist-add-receiver-table tr:eq(0)").removeClass("hidden");
        $("#blacklist-add-receiver-table tr:eq(1)").addClass("hidden");
        //reset
        blacklistReceiverFormValidate3.resetForm();
        $("#blacklist-receiver-form input").val('');
    });



    var blacklistFormValidate = $( "#blacklist-form" ).validate( {
        rules: {
            name: {
                required: true,
                stringCheck: true
            },
         /*   site : {
                required: true,
            }*/
        },
        messages: {
            name: {
                required: "请输入分组名称",
                stringCheck: "只能包括中文字、英文字母、数字和下划线!"
            },
           /* site : {
                required: "请选择站点"
            }*/
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            // Add the `help-block` class to the error element
          /*  error.addClass( "help-block" );

            if ( element.prop( "type" ) === "checkbox" ) {
                error.insertAfter( element.parent( "label" ) );
            } else {
                error.insertAfter( element );
            }*/
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

    fullDatatables();
});

/**
删除分组中黑名单人员
*/
function delReceiverInfo(idH) {
        //定义一数组
      strs=idH.split(","); //字符分割
      var imsi = strs[0];
      var targetRuleId = strs[1];
      var groupId = strs[2];
      $.ajax({
          type: "post",
          url: "/target/delGroupTargetPerson",//删除分组黑名单人员
          data: { targetRuleId:targetRuleId,imsi: imsi,groupId: groupId},
          dataType:"json",
          success:  function( data ){
               if( true == data.status ) {
                  Messenger().post({
                      message: data.message,
                      type: 'success',
                      showCloseButton: true
                  });
                 $("#receiver-table tbody").empty();
                  blacklistTable.ajax.reload();
                 receiversRen(groupId);
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

//通过分组ID查询各个分组对应的黑名单人员信息
function receiversRen(IdRen) {
  $.ajax({
      type: "post",
      url: "/target/queryBlacklist",
      data: { IdRen: IdRen },
      dataType:"json",
      success:  function( data ){

          if( true == data.status ) {
               rowDataRen = data.data;
                   $("#receiver-table tbody").empty();
                   $.each(rowDataRen,function ( i, receiver ) {
                       var trDom = ['<tr>'];
                       trDom.push('<td>' + receiver.name + '</td>' );
                       trDom.push('<td>' + receiver.imsi + '</td>' );
                       trDom.push('<td>' + receiver.idCard + '</td>');
                       trDom.push('<td>' + receiver.phone+ '</td>');
                       trDom.push('<td>' + receiver.createTime+ '</td>');
                       trDom.push('<td>' + receiver.remark+ '</td>');
                       trDom.push("<td><a href='###' onclick=\"delReceiverInfo('"+receiver.imsi+","+receiver.id+","+IdRen+","+i+"');\" >删除</a></td>");
                       trDom.push('</tr>');
                       $("#receiver-table tbody").prepend(trDom.join(''));
                   });
                   $("#receiver-dlg input[name=id]").val(rowDataRen.id);
                   $("#receiver-dlg").modal("show");
          }
      }
  });
}


//添加多个黑名单人员信息放入缓存
var recordBuffer = new Array();
$("#blacklist-add-receiver-ok").click(function () {
      if(!blacklistReceiverFormValidate.form()) {
        return;
      }
       var name = $("#blacklist-receiver-form input[name=name]").val();
       if(name == '') {
          Messenger().post({
               message: "黑名单人员姓名不能为空!",
               type: 'error',
               showCloseButton: true
           });
          return;
       }

       var phone = $("#blacklist-receiver-form input[name=phone]").val();
       if(phone == '') {
          Messenger().post({
               message: "imsi不能为空!",
               type: 'error',
               showCloseButton: true
           });
          return;
       }
        //判断IMSI号码是否只包含阿拉伯数字
        var r = /^\+?[1-9][0-9]*$/;
        var b =  r.test(phone);
         if(!b) {
             Messenger().post({
                  message: "IMSI号码含有非法字符!",
                  type: 'error',
                  showCloseButton: true
              });
             return;
          }

        if(phone.length != 15) {
         Messenger().post({
              message: "请输入正确格式的IMSI号码!",
              type: 'error',
              showCloseButton: true
          });
         return;
      }

       var idCard = $("#blacklist-receiver-form input[name=idCard]").val();
       if(idCard != ''){
           var c =  r.test(idCard);
           if(!c) {
              Messenger().post({
                   message: "身份证号码含有非法字符!",
                   type: 'error',
                   showCloseButton: true
               });
              return;
           }
           if(idCard.length != 18){
              Messenger().post({
                  message: "请输入正确格式的身份证号码!",
                  type: 'error',
                  showCloseButton: true
              });
             return;
           }
       }

       var targetphone = $("#blacklist-receiver-form input[name=targetphone]").val();
       if(targetphone != ''){
           var d =  r.test(targetphone);
           if(!d) {
               Messenger().post({
                   message: "手机号码含有非法字符!",
                   type: 'error',
                   showCloseButton: true
               });
               return;
           }
           if(targetphone.length != 11){
               Messenger().post({
                  message: "请输入正确格式的手机号码!",
                  type: 'error',
                  showCloseButton: true
               });
               return;
           }
       }

       var email = $("#blacklist-receiver-form input[name=email]").val();

       for(var j = 0; j < recordBuffer.length; ++j) {
            //IMSI号码不能重复
          if(phone == recordBuffer[j].phone){
               Messenger().post({
                   message: "imsi号码重复!",
                   type: 'error',
                   showCloseButton: true
               });
               return;
          }

       }
      recordBuffer[recordBuffer.length] = {
          name : name,
          phone : phone,
          idCard : idCard,
          targetphone : targetphone,
          email : email
      }
      showRecordsHtml();
      $("#blacklist-receiver-form")[0].reset();
      blacklistReceiverFormValidate.resetForm();

});
//////////////////////////////////////////修改/////////////////////////////////////////////
 var groupIdUp;
 var upNodesIds = [];
$('#blacklist-table tbody').on( 'click', 'tr a.blacklist_xg', function () {
      //所有样式初始化
      $("#update-table tbody").empty();
      $("#update-form")[0].reset();
      $("#update-dlg").modal("show");
      $("#update-receiver-table tr:eq(0)").removeClass("hidden");
      $("#update-receiver-table tr:eq(1)").addClass("hidden");
      recordMsgReceiverBuffer1 = new Array();
      $("#upsite").val(null).trigger("change");

     var tr = $(this).closest('tr');
     //拿到这个分组的所有信息赋值
     var obj = blacklistTable.row( tr ).data();
     $("#upname").val(obj.name);
     $("#upremark").val(obj.remark);
     //给站点赋值
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
     /*var siteInfo = obj.site;
     var array = new Array();
     var aa = siteInfo.split(";").pop().replace('[','').replace(']','');//[46,67]
     array = aa.split(',');*/
    var treeObj = $.fn.zTree.getZTreeObj("up-treeDemo");
    upNodesIds = new Array();
     $.ajax({
         type: "post",
         url: "/target/querySiteIdsByGroupId",
         data: { groupId : obj.id},
         dataType:"json",
         success:  function( data ){
             if( true == data.status ) {
                 for(var i = 0;i < data.data.length;i++){
                     upNodesIds[i] = data.data[i];
                     var node = treeObj .getNodeByParam('id',data.data[i]);
                     if(node != null){
                         treeObj.checkNode(node, true, true);
                     }
                 }
             } else {
                 Messenger().post({
                     message: data.message,
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
         startDate:obj.create_time,
         endDate:obj.overdue_time,
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
          jsonObj =  JSON.parse(obj.warning);
     }
     groupIdUp = obj.id;
     //给通知人赋值
     var html = '';
     if(jsonObj != null){
         for(var x = 0; x < jsonObj.length; x++) {
             recordMsgReceiverBuffer1[recordMsgReceiverBuffer1.length] = {
                 name : jsonObj[x].name,
                 phone : jsonObj[x].phone,
                 email : jsonObj[x].email,
             }
             showMsgReceiverRecordsHtml1();
             $("#blacklist-receiver-form1")[0].reset();
         }
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
         blacklistTable.ajax.reload(null,false);
        //取值
         var name = $("#upname").val();
         //判断分组名字是否含有非法字符
         var pat=new RegExp("[^a-zA-Z0-9\_\u4e00-\u9fa5]","i");
         var b =  pat.test(name);
         if(b) {
             Messenger().post({
                 message: "分组名字含有非法字符,请更换!",
                 type: 'error',
                 showCloseButton: true
             });
             return;
         }

         var warning = JSON.stringify(recordMsgReceiverBuffer1);
         var startDate1 = $("#up-alarm-range-time").val().slice(0,19);//取开始时间
         startDate = startDate1.split("/").join("").split(":").join("").split(" ").join("");
         var endDate1 = $("#up-alarm-range-time").val().slice(22,41);//取结束时间
         endDate = endDate1.split("/").join("").split(":").join("").split(" ").join("");
         var remark = $("#upremark").val();//备注

         $.ajax({
             type: "post",
             url: "/target/updateGroup",
             traditional:true,
             data: { groupIdUp:groupIdUp, name:name, site:upNodesIds,  warning:warning,  startDate:startDate,  endDate:endDate,  remark:remark },
             dataType:"json",
             success:  function( data ){
                 if( true == data.status ) {
                     Messenger().post({
                         message: data.message,
                         type: 'success',
                         showCloseButton: true
                     });
                      $("#update-dlg").modal("hide");
                     blacklistTable.ajax.reload(null,false);
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


//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
$('#blacklist-table tbody').on( 'click', 'tr a.blacklist_bl', function () {
          recordBuffer = new Array();
         $("#blacklist-receiver-table tbody").html("");
         $('#blacklist-receiver-form')[0].reset();
         $('#addTarget-dlg').modal('show');
         var tr = $(this).closest('tr');
         var groupId = blacklistTable.row( tr ).data().id;
          $("#addTarget-ok-btn").unbind("click");
          $("#addTarget-ok-btn").click(function () {
              // if( blacklistFormValidate.form() )  {
                    var receivers=[];
                    for(var j = 0; j < recordBuffer.length; ++j) {
                        var receiverName  = recordBuffer[j].name;//姓名
                        var receiverImsi = recordBuffer[j].phone;//imsi号码
                        var receiverIdCard = recordBuffer[j].idCard;//身份证号码
                        var receiverPhone = recordBuffer[j].targetphone;//手机号码
                        var receiverEmail = recordBuffer[j].email;//备注
                        var receive = { name: receiverName , imsi: receiverImsi , idCard: receiverIdCard, phone:receiverPhone, remark: receiverEmail , creatorId:groupId + ''};
                        receivers.push(receive);
                     }

                     if(receivers.length == 0){
                          Messenger().post({
                               message: "提交数据不能为空!",
                               type: 'error',
                               showCloseButton: true
                           });
                           return;
                     }
                       $.ajax({
                           type: "post",
                           url: "/target/addOneTarget",//补录黑名单人员
                           data: { groupId:groupId,  receivers: JSON.stringify(receivers) },
                           dataType:"json",
                           success:  function( data ){
                               if( true == data.status ) {
                                   Messenger().post({
                                       message: data.message,
                                       type: 'success',
                                       showCloseButton: true
                                   });
                                  $("#addTarget-dlg").modal("hide");
                                 blacklistTable.ajax.reload();
                               } else {
                                  var badData = data.bad;
                                  var message = "以下IMSI号在分组中已存在：";
                                  for(var j = 0; j < badData.length; ++j) {
                                      message += badData[j] + ",";
                                  }
                                   Messenger().post({
                                       message: message,
                                       type: 'error',
                                       showCloseButton: true
                                   });

                               }
                        }
                    });
               // }
             });
 });

var blacklistTable;
function fullDatatables() {
  blacklistTable =  $('#blacklist-table').DataTable({
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
     "sAjaxSource": "/target/queryAllGroup",
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
            { "data": "name" },//分组名称
            { "data": "creator_name" },//创建人名称
            /*{ "data": "site" },//站点信息*/
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
            {
              "data": "warning",
                      "render" : function(data, type, full) {
                      // body...
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
            { "data": "create_time" },//创建时间
            { "data": "overdue_time" },//到期时间
            { "data": "count" },//黑名单人数
            { "data": "remark" },//备注
            {
                "render" : function (data, type, row ) {
                    var dom;
                    dom =  '<a href="###"  class="btn btn-flat btn-sm btn-primary blacklist_xq">详情</a>';
                    dom += '<a href="###"  class="btn btn-flat btn-sm btn-warning blacklist_dr">导入</a>';
                    dom += '<a href="###"  class="btn btn-flat btn-sm bg-purple blacklist_bl">补录</a>';
                    dom += '<a href="###"  class="btn btn-flat btn-sm btn-danger  blacklist_xg">修改</a>';
                    return dom;
                },
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
         //表格样式编译
        "oLanguage" : dataTable_language,
        buttons: [
            {
                text: "添加分组",
                className: "btn bg-navy btn-flat",
                action: function () {
                    $("#site").val(null).trigger("change");
                    $("#blacklist-receiver-table tbody").empty();
                    $("#blacklist-add-receiver-table tr:eq(0)").removeClass("hidden");
                    $("#blacklist-add-receiver-table tr:eq(1)").addClass("hidden");
                    $("#blacklist-form")[0].reset();
                    $("#siteName").html('请点击上方按钮选择检测站点');
                    //每次重新添加分组的时候zTree初始化
                    addSiteIds = new Array();
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    treeObj.checkAllNodes(false);
                    nodes = null;
                    $("#blacklist-dlg").modal("show");
                }
            },
            {
                text: "删除分组",
                className: "btn bg-navy btn-flat",
                action: function () {
                    var count = blacklistTable.rows( { selected: true } ).count();
                    if( 0 == count ) {
                        bootbox.alert({
                            size: "small",
                            message:"请选择分组"
                        });
                        return;
                    }

                    var rowData = blacklistTable.rows( { selected: true }).data();

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
                                //获取分组IDlist
                                var idArray = [];
                                $.each(rowData, function ( i,val ) {
                                    idArray.push( val.id);
                                });
                                $.ajax({
                                    type: "post",
                                    url: "/target/delGrouplist",
                                    data: { 'ids':  JSON.stringify(idArray) },
                                    dataType:"json",
                                    success:  function( data ){
                                        if( true == data.status ) {
                                            Messenger().post({
                                                message: data.message,
                                                type: 'success',
                                                showCloseButton: true
                                            });
                                            blacklistTable.ajax.reload(null,false);
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
        ]
    });
}

function showRecordsHtml() {
    var html = '';
    for(var j = 0; j < recordBuffer.length; ++j) {
        html += '<tr>'
            + '<td>' + recordBuffer[j].name + '</td>'
            + '<td>' + recordBuffer[j].phone + '</td>'
            + '<td>' + recordBuffer[j].idCard + '</td>'
            + '<td>' + recordBuffer[j].targetphone + '</td>'
            + '<td>' + recordBuffer[j].email + '</td>'
            + '<td><a style="cursor:pointer;" onclick="delReceiverRow(\'' + recordBuffer[j].phone + '\')" >删除</a></td>'
            + '</tr>'
    }

    $("#blacklist-receiver-table tbody").html('');
     $("#blacklist-receiver-table tbody").append(html);
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

    $("#blacklist-receiver-table1 tbody").html('');
    $("#blacklist-receiver-table1 tbody").append(html);
}

function showMsgReceiverRecordsHtml1() {
    var html = '';
    for(var j = 0; j < recordMsgReceiverBuffer1.length; ++j) {
        html += '<tr>'
            + '<td>' + recordMsgReceiverBuffer1[j].name + '</td>'
            + '<td>' + recordMsgReceiverBuffer1[j].phone + '</td>'
            + '<td>' + recordMsgReceiverBuffer1[j].email + '</td>'
            + '<td><a style="cursor:pointer;" onclick="delMsgReceiverRecordsRow1(\'' + recordMsgReceiverBuffer1[j].phone + '\')" >删除</a></td>'
            + '</tr>'
    }

    $("#update-table tbody").html('');
    $("#update-table tbody").append(html);
}

//修改通知人信息放入缓存
var recordMsgReceiverBuffer = new Array();
$("#update-ok").click(function () {
  if(!blacklistReceiverFormValidate2.form()) {
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

   for(var j = 0; j < recordMsgReceiverBuffer1.length; ++j) {
      if(phone == recordMsgReceiverBuffer1[j].phone){
           Messenger().post({
               message: "手机号码重复!",
               type: 'error',
               showCloseButton: true
           });
           return;
      }
   }

  recordMsgReceiverBuffer1[recordMsgReceiverBuffer1.length] = {
      name : name,
      phone : phone,
      email : email
  }

  showMsgReceiverRecordsHtml1();
  $("#update-receiver-form")[0].reset();
});

//添加分组时通知人信息放入缓存
var recordMsgReceiverBuffer = new Array();
$("#blacklist-add-receiver-ok1").click(function () {
   if(!blacklistReceiverFormValidate3.form()) {
       return;
     }
   var name = $("#blacklist-receiver-form1 input[name=name]").val();
   var phone = $("#blacklist-receiver-form1 input[name=phone]").val();
   var email = $("#blacklist-receiver-form1 input[name=email]").val();
   if(name == ''){
      Messenger().post({
         message: "请填写通知人姓名!",
         type: 'error',
         showCloseButton: true
      });
      return;
   }
   if(phone == ''){
      Messenger().post({
          message: "请填写手机号码!",
          type: 'error',
          showCloseButton: true
      });
      return;
   }
   if(phone.length != 11){
      Messenger().post({
          message: "请填写正确格式手机号码!",
          type: 'error',
          showCloseButton: true
      });
      return;
   }
   //判断IMSI号码是否只包含阿拉伯数字
   var r = /^\+?[1-9][0-9]*$/;
   var b =  r.test(phone);
   if(!b) {
      Messenger().post({
         message: "手机号码含有非法字符!",
         type: 'error',
         showCloseButton: true
      });
      return;
   }
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
  $("#blacklist-receiver-form1")[0].reset();
});

var recordMsgReceiverBuffer1 = new Array();
$("#update-receiver-btn").click(function () {
    $("#update-receiver-table tr:eq(0)").addClass("hidden");
    $("#update-receiver-table tr:eq(1)").removeClass("hidden");
});
$("#blacklist-add-receiver-btn1").click(function () {
    $("#blacklist-add-receiver-table1 tr:eq(0)").addClass("hidden");
    $("#blacklist-add-receiver-table1 tr:eq(1)").removeClass("hidden");
});

//添加通知人取消按钮
$("#update-cancel").click(function () {
    $("#update-receiver-table tr:eq(0)").removeClass("hidden");
    $("#update-receiver-table tr:eq(1)").addClass("hidden");
    blacklistReceiverFormValidate2.reset();
    $("#update-receiver-form input").val('');
});

$("#blacklist-add-receiver-cancel1").click(function () {
    $("#blacklist-add-receiver-table1 tr:eq(0)").removeClass("hidden");
    $("#blacklist-add-receiver-table1 tr:eq(1)").addClass("hidden");
    $("#blacklist-receiver-form1 input").val('');
});


function delMsgReceiverRecordsRow(phone) {
    for(var j = 0; j < recordMsgReceiverBuffer.length; ++j) {
        if(recordMsgReceiverBuffer[j].phone == phone) {
            recordMsgReceiverBuffer.splice(j, 1);
        }
    }
    showMsgReceiverRecordsHtml();
}

function delMsgReceiverRecordsRow1(phone) {
    for(var j = 0; j < recordMsgReceiverBuffer1.length; ++j) {
        if(recordMsgReceiverBuffer1[j].phone == phone) {
            recordMsgReceiverBuffer1.splice(j, 1);
        }
    }
    showMsgReceiverRecordsHtml1();
}
//更新通知人信息追加校验
var blacklistReceiverFormValidate2 = $( "#update-receiver-form" ).validate( {
    rules: {
       name: {
            required: true,
            stringCheck: true
        },
        phone:{
            required: true,
            stringCheck: true
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
            stringCheck: "请输入正确格式的手机号码"
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
//添加分组时通知人信息追加校验
var blacklistReceiverFormValidate3 = $( "#blacklist-receiver-form1" ).validate( {
    rules: {
       name: {
            required: true,
            stringCheck: true
        },
        phone:{
            required: true,
            stringCheck: true
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
            stringCheck: "请输入正确格式的手机号码"
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

//添加黑名单人员黑名单信息追加校验
var blacklistReceiverFormValidate = $( "#blacklist-receiver-form" ).validate( {
    rules: {
       name: {
            required: true,
            stringCheck: true
        },
        phone:{
            required: true,
            stringCheck: true
        },
    },
    messages: {
        name: {
            required: "请输入姓名",
            stringCheck: "只能包括中文字、英文字母、数字和下划线!"
        },
        phone:{
            required: "请输入IMSI号码",
            stringCheck: "请输入正确格式的IMSI号码"
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

