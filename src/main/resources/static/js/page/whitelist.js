/**
 * Created by jiangqi.yang on 2016/11/9.
 */

function delReceiverRow( row ) {
    $(row).parent().parent().remove();
}

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    }

    var whitelistTable =  $('#whitelist-table').DataTable({
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
        "sAjaxSource": "/whitelist/queryAll",
        "fnServerParams": function ( aoData ){},
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
            {
                "orderable":      false,
                "data":           null,
                "defaultContent": ""
            },
            { "data": "imsi" },
            { "data": "phone" },
            { "data": "name" },
            { "data": "operator"},
            { "data": "ownership" },
            { "data": "organization"},
            {
                "orderable": false,
                "data": null,
                "render" : function (data, type, row ) {
                    var dom = '<a  href="###" class="btn btn-primary whitelist_update">修改</a>';
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
        "fnDrawCallback": function(){
            var api = this.api();
            var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
            api.column(1).nodes().each(function(cell, i) {
                cell.innerHTML = startIndex + i + 1;
            });
        },
        "oLanguage" : dataTable_language,
        buttons: [
            {
                text: "添加白名单",
                className: "btn bg-navy btn-flat",
                action: function () {
                    $("#whitelist-form")[0].reset();
                    $("#whitelist-dlg").modal("show");
                }
            },
            {
                text: "导入白名单",
                className: "btn bg-navy versionFormValidatebtn-flat",
                action: function () {
                      $('#importblacklist-form')[0].reset();
                      $('#importblacklist-dlg').modal('show');
                }
            },
            {
                text: "删除白名单",
                className: "btn bg-navy btn-flat",
                action: function () {
                    var count = whitelistTable.rows( { selected: true } ).count();
                    if( 0 == count ) {
                        bootbox.alert({
                            size: "small",
                            message:"请选择要删除的白名单"
                        });
                        return;
                    }
                    var rowData = whitelistTable.rows( { selected: true }).data();
                    bootbox.confirm({
                        size: "small",
                        message: "是否删除名单",
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
                                    url: "/whitelist/delWhitelist",
                                    data: { 'ids':  JSON.stringify(idArray) },
                                    dataType:"json",
                                    success:  function( data ){
                                        if( true == data.status ) {
                                            Messenger().post({
                                                message: data.message,
                                                type: 'success',
                                                showCloseButton: true
                                            });
                                            whitelistTable.ajax.reload(null,false);
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

    //添加白名单人员信息
    $("#whitelist-ok-btn").click(function () {
        //取值
        var imsi = $("#imsi").val();
        if(imsi == '') {
              Messenger().post({
                   message: "请填写imsi号码!",
                   type: 'error',
                   showCloseButton: true
               });
              return;
        }
        var r = /^\+?[1-9][0-9]*$/;
        var b =  r.test(imsi);
        if(b == false){
            Messenger().post({
                 message: "imsi号码含有非法字符!",
                 type: 'error',
                 showCloseButton: true
            });
            return;
        }
        if(imsi.length != 15){
            Messenger().post({
                 message: "请填写正确格式的imsi号码!",
                 type: 'error',
                 showCloseButton: true
            });
            return;
        }
        var phone = $("#phone").val();
        if(phone != ''){
             var c =  r.test(phone);
            if(c == false){
                Messenger().post({
                     message: "手机号码含有非法字符!",
                     type: 'error',
                     showCloseButton: true
                });
                return;
            }
            if(phone.length !=11){
                Messenger().post({
                     message: "请填写正确格式的手机号码!",
                     type: 'error',
                     showCloseButton: true
                });
                return;
            }
        }
        var name = $("#name").val();
        var operator = $("#operator").val();
        var ownership = $("#ownership").val();
        var organization = $("#organization").val();
        $.ajax({
            type: "post",
            url: "/whitelist/addWhitelist",
            traditional:true,
            data: { imsi:imsi, phone:phone, name:name, operator:operator, ownership:ownership, organization:organization },
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    Messenger().post({
                        message: data.message,
                        type: 'success',
                        showCloseButton: true
                    });
                    $("#whitelist-dlg").modal("hide");
                    whitelistTable.ajax.reload(null,false);
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

     //修改白名单人员信息赋值
     var upid;
     $('#whitelist-table tbody').on( 'click', 'tr a.whitelist_update', function () {

        $("#upwhitelist-form")[0].reset();
        $("#upwhitelist-dlg").modal("show");
        //拿到这个分组的所有信息赋值
        var tr = $(this).closest('tr');
        var obj = whitelistTable.row( tr ).data();
        $("#upimsi").val(obj.imsi);
        $("#upphone").val(obj.phone);
        $("#upname").val(obj.name);
        if(obj.operator == ''){
            $("#upoperator").val("请选择运营商");
        }else{
            $("#upoperator").val(obj.operator);
        }
        $("#upownership").val(obj.ownership);
        $("#uporganization").val(obj.organization);
        upid = obj.id;

     });

     //提交修改白名单人员信息
     $("#upwhitelist-ok-btn").click(function (){
         //取值
         var upimsi = $("#upimsi").val();
         if(upimsi == '') {
               Messenger().post({
                    message: "请填写imsi号码!",
                    type: 'error',
                    showCloseButton: true
                });
               return;
         }
         var r = /^\+?[1-9][0-9]*$/;
         var b =  r.test(upimsi);
         if(b == false){
             Messenger().post({
                  message: "imsi号码含有非法字符!",
                  type: 'error',
                  showCloseButton: true
             });
             return;
         }
         if(upimsi.length != 15){
             Messenger().post({
                  message: "请填写正确格式的imsi号码!",
                  type: 'error',
                  showCloseButton: true
             });
             return;
         }
         var upphone = $("#upphone").val();
         if(upphone != ''){
              var c =  r.test(upphone);
             if(c == false){
                 Messenger().post({
                      message: "手机号码含有非法字符!",
                      type: 'error',
                      showCloseButton: true
                 });
                 return;
             }
             if(upphone.length !=11){
                 Messenger().post({
                      message: "请填写正确格式的手机号码!",
                      type: 'error',
                      showCloseButton: true
                 });
                 return;
             }
         }
         var upname = $("#upname").val();
         var upoperator = $("#upoperator").val();
         var upownership = $("#upownership").val();
         var uporganization = $("#uporganization").val();

         $.ajax({
             type: "post",
             url: "/whitelist/updateWhitelist",
             traditional:true,
             data: { upid:upid,upimsi:upimsi, upphone:upphone, upname:upname, upoperator:upoperator, upownership:upownership, uporganization:uporganization },
             dataType:"json",
             success:  function( data ){
                 if( true == data.status ) {
                     Messenger().post({
                         message: data.message,
                         type: 'success',
                         showCloseButton: true
                     });
                     $("#upwhitelist-dlg").modal("hide");
                     whitelistTable.ajax.reload(null,false);
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

     //导入白名单人员信息
     $('#uploadFile').fileupload( {
         url: "/whitelist/readExcel",
         paramName: "uploadFile",
         dataType: 'json',
         done: function (e, data) {
             if( true == data.result.status) {
                 Messenger().post({
                     message:  data.result.message,
                     type: 'success',
                     showCloseButton: true
                 });
                 $('#importblacklist-dlg').modal('hide');
                 whitelistTable.ajax.reload();
             } else {
                 Messenger().post({
                     message:  data.result.message,
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
           //  importblacklistFormValidate.element( "#filename" );
         }
     }).prop('disabled', !$.support.fileInput)
         .parent().addClass($.support.fileInput ? undefined : 'disabled');

  /*   $("#importblacklist-dlg").on("shown.bs.modal", function (e) {
        importblacklistFormValidate.resetForm();
     });

     var importblacklistFormValidate = $( "#importblacklist-form" ).validate( {
          rules: {
              filename: "required",
              version:{
                  required: true
              }
          },
          messages: {
              filename: "上传失败",
          },
          errorElement: "em",
          errorPlacement: function ( error, element ) {
              error.addClass( "help-block" );
              if ( element.prop( "type" ) === "checkbox" ) {
                  error.insertAfter( element.parent( "label" ) );
              } else if( element.data( "upload" ) === "display" ) {
                  error.insertAfter( element.parent( ".input-group" ) );
              }
              else {
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
     });*/

});





