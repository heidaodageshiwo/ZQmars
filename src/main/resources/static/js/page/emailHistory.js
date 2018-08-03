/**
 * Created by jiangqi.yang on 2016/12/28.
 */



$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

      $("#query-condition-OK").bind("click", function() {
            processSearch();
        })

    var emailRangeTime = $('#email-range-time').daterangepicker({
        timePicker: true,
        timePickerIncrement: 1,
        timePicker24Hour: true,
        timePickerSeconds: true,
        locale: {
            applyLabel: '确定',
            cancelLabel: '取消',
            format: 'YYYY-MM-DD HH:mm:ss',
            format: 'YYYY-MM-DD HH:mm:ss',
            fromLabel : '起始时间',
            toLabel : '结束时间',
            customRangeLabel : '自定义',
            daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
            monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月' ]
        }
    });

    var targetsTable;
    function processSearch (){
         var startTime = $("#email-range-time").val().slice(0,19);
         var endTime = $("#email-range-time").val().slice(22,41);
         var email = $("#email").val();

        var param = {
            startTime :startTime,
            endTime : endTime,
            email : email,
        };
        targetsTable = $('#history-email-table').DataTable( {
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
            "sAjaxSource": "/system/email/queryHistoryEmail?",
            "fnServerParams": function ( aoData ) {
                                    aoData.push( { "name": "formData", "value": JSON.stringify(param) } );
                                },
           "select": {
                          style: "multi",
                          selector: "td:first-child"
                      },
              columns: [
                      /*  {
                           "orderable":      false,
                           "data":           null
                        },*/
                       { "data": "notifyName" },
                       { "data": "emailSubject" },
                       { "data": "lastSendTime" },
                       {
                         "data": "status",
                          "render" : function (data, type, row ) {
                               var statusText = "发送成功";
                               if( 2 == data) {
                                   statusText = "发送失败";
                               }
                               return statusText;
                           }
                       },
                       {
                           "orderable":      false,
                           "data":           null,
                           "render" : function (data, type, row ) {
                               var detailDom = '<button class="btn btn-flat btn-sm bg-navy email-detail-btn">查看</button>';
                               return detailDom;
                           }
                       }
                   ],
                   //表格样式编译
                    "oLanguage" : dataTable_language,
        });
    }

    //查看每个邮箱详情
    $('#history-email-table tbody').on( 'click', 'tr button.email-detail-btn', function () {
        var tr = $(this).closest('tr');
        var rowData = targetsTable.row(tr).data();

        $.ajax({
            type: "post",
            url: "/system/email/getEmailDetail",
            data: { id: rowData.id },
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    var emailDetail = data.data;
                    $("#email-subject").text(emailDetail.subject);
                    $("#email-receiver").text(emailDetail.receiver);
                    $("#email-sender").text(emailDetail.sender);
                    $("#email-sendTime").text(emailDetail.sendTime);
                    $("#email-body").html(emailDetail.body);
                    $("#emailDetail-dlg").modal("show");
                } else {
                    Messenger().post({
                        message: data.message,
                        type: 'error',
                        showCloseButton: true
                    }) ;
                }
            },
            error: function () {
                Messenger().post({
                    message:  "网络异常，请稍后再试",
                    type: 'error',
                    showCloseButton: true
                }) ;
            }
        });

    });


});
