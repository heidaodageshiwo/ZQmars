/**
 * Created by jiangqi.yang on 2016/11/8.
 */

$(function () {
    $("#query").bind("click", function() {
        processSearch();
    })
    $("#reset").bind("click", function() {
        $("#alarmTargetName").val("");
        $("#targetType").val("");
        $("#alarmIMSI").val("");
    })
    processSearch();

    //
    $("#queryAuto").bind("click", function() {
        if(currentAutoState) {
            $("#queryAuto").removeClass("btn-primary").addClass("btn-warning");
            $("#queryAuto").text("禁用自动查询");
            $("#query").attr("disabled", true);
            $("#reset").attr("disabled", true);
            $("#targetType").attr("disabled", true);
            $("#searchForm input").attr("readonly","readonly");
         //
         timesIdx = 1;
         setTimeout('reSearch()', 10000);
       } else {
            $("#queryAuto").removeClass("btn-warning").addClass("btn-primary");
            $("#queryAuto").text("启用自动查询");
            $("#query").attr("disabled", false);
            $("#reset").attr("disabled", false);
            $("#targetType").attr("disabled", false);
            $("#searchForm input").removeAttr("readonly");
        }
        currentAutoState = !currentAutoState;
    });
    return;
});
var currentAutoState = true;
var timesIdx = 1;
function reSearch() {
    if(currentAutoState)
        return ;
   setTimeout('reSearch()', 10000);
    processSearch();
}

var targetsTable;

function processSearch () {
    var targetName = $("#alarmTargetName").val();
    var type = $("#targetType").val();
    var imsi = $("#alarmIMSI").val();

    var param = {
        targetName : $.trim(targetName),
        type : type,
        imsi : $.trim(imsi)
    };
    targetsTable = $('#targets-table').DataTable( {
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
        "sAjaxSource": "/target/getCurrentTargetsNew",
        "fnServerParams": function ( aoData ) {
                        aoData.push( { "name": "formData", "value": JSON.stringify(param) } );
                    },
        "columnDefs": [
            {
             sDefaultContent: '',
             aTargets: [ '_all' ]
            },
            {
                className: 'select-checkbox', targets: 0
            },
            {
                "aTargets" : [3],
                "mData" : "download_link",
                "mRender" : function(data, type, full) {
                    return data == 1 ? "黑名单预警" : "归属地预警";
                }
            },
            {
                "aTargets" : [9],
                "mData" : "download_link",
                "mRender" : function(data, type, full) {
                    var year = data.substring(0, 4);
                    var month = data.substring(4, 6);
                    var day = data.substring(6, 8);
                    var hour = data.substring(8, 10);
                    var min = data.substring(10, 12);
                    var s = data.substring(12, 14);
                    return year + "年" + month + "月" + day + "日 " + hour + ":" + min + ":" + s;
                }
            },
            {
                "aTargets" : [10],
                "mData": "isRead",
                "mRender" : function (data, type, row ) {
                    var dom = '<button  class="btn btn-flat btn-sm bg-orange target-confirm-btn">确认</button>';
                    return dom;
                }
            }
        ],
        "columns": [
            { "data": null},
            { "data": "groupName","sClass": "center"},
            { "data": "targetName","sClass": "center"},
            { "data": "indication","sClass": "center" },
            { "data": "imsi","sClass": "center" },
            { "data": "cityName","sClass": "center","bSortable": false },
            { "data": "operator","sClass": "center","bSortable": false },
            { "data": "siteName","sClass": "center" },
            { "data": "deviceName","sClass": "center" },
            { "data": "captureTime","sClass": "center","bSortable": false },
            { "data": null,"sClass": "center","bSortable": false }
        ],
         "oLanguage" : dataTable_language,
         buttons : [
            {
                 text: "全选",
                 className: "btn bg-navy btn-flat",
                 action: function () {
                     targetsTable.rows({page:'current'}).select();
                 }
             },
             { extend: 'selectNone', className: 'btn bg-navy btn-flat', text: "取消全选" },
             {
                 text: "确认",
                 className: "btn bg-navy btn-flat",
                 action: function () {
                     var count = targetsTable.row( { selected: true } ).count();
                     if( 0 == count ) {
                         bootbox.alert({
                             size: "small",
                             message:"请选择预警项"
                         });
                         return;
                     }
                     var rowData = targetsTable.rows( { selected: true }).data();
                     bootbox.confirm({
                         size: "small",
                         message: "是否确认预警项",
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
                                     url: "/target/confirmTargetAlarmNew",
                                     data: { 'ids': JSON.stringify(idArray) },
                                     dataType:"json",
                                     success:  function( data ){
                                         if( true == data.status ) {
                                             targetsTable.ajax.reload(null,false);
                                         } else {
                                             Messenger().post({
                                                 message: data.message,
                                                 type: 'error',
                                                 showCloseButton: false
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
    } );
}

$('#targets-table tbody').on( 'click', 'tr button.target-confirm-btn', function () {
    var tr = $(this).closest('tr');
    var rowData = targetsTable.row( tr ).data();
    var idArray = [];
    idArray.push( rowData.id );

    if( rowData ) {
        $.ajax({
            type: "post",
            url: "/target/confirmTargetAlarmNew",
            data: { 'ids': JSON.stringify(idArray) },
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    targetsTable.ajax.reload(null,false);
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

