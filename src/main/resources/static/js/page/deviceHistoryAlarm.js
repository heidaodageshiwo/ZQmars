/**
 * Created by jiangqi.yang on 2016/11/12.
 */

$(function () {

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
    

    var historyAlarmTable = $('#history-alarm-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "processing": true,
        "pageLength": 30,
        "searching": false,
        "ordering": false,
        "info": true,
        "autoWidth": true,
        "serverSide": true,
        "ajax": {
            "url": "/device/alarm/queryHistoryAlarms"
        },
        columns: [
            {
                "orderable":      false,
                "data":           null
            },
            { "data": "code"},
            { "data": "info"},
            { "data": "time"},
            { "data": "deviceSN"},
            { "data": "deviceName"},
            { "data": "siteSN"},
            { "data": "siteName"}
        ],
        "fnDrawCallback": function(){
            var api = this.api();
            var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
            api.column(0).nodes().each(function(cell, i) {
                cell.innerHTML = startIndex + i + 1;
            });
        },
        "language": {
            "lengthMenu": "每页显示 _MENU_ 记录",
            "zeroRecords": "对不起-没有数据发现",
            "info": "显示 _START_ 到 _END_ 条记录共 _TOTAL_ 记录，显示第_PAGE_页, 共_PAGES_页",
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
        }
    });


    var deviceAlarmQueryFormValidate = $( "#device-alarm-query-form" ).validate( {
        rules: {
            alarmCode:{
                digits: true
            }
        },
        messages: {
            alarmCode:{
                digits: "请输入正确的告警码"
            }
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            // Add the `help-block` class to the error element
            error.addClass( "help-block" );

            if ( element.prop( "type" ) === "checkbox" ) {
                error.insertAfter( element.parent( "label" ) );
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

    function formatSelect ( data ) {
        if (!data.id) { return data.text; }
        var $pDom = $(
            '<p><span>' + data.text + '</span><span class="pull-right text-gray">' + data.id + '</span></p>'
        );
        return $pDom;
    }
    
    var deviceSNList = [];
    $.ajax({
        type: "post",
        url: "/device/getDevicesByUserId",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                var deviceData = $.map( data.data, function (obj) {
                    obj.text = obj.name;
                    obj.id = obj.sn;
                    return obj;
                });
                /*deviceSNList = deviceData;*/
                deviceData.unshift({ id:0 , text: "不限" });
                $("#alarm-device-sn").select2({
                    language: "zh-CN",
                    data: deviceData,
                    templateResult: formatSelect
                });
            } else {
                $("#alarm-device-sn").select2({
                    language: "zh-CN",
                    templateResult: formatSelect
                });
            }
        }
    });

    
    $("#query-condition-OK").click(function ( evt ) {

        if( deviceAlarmQueryFormValidate.form() ) {

            var startDate = $("#alarm-range-time").val().slice(0,19);
            var endDate = $("#alarm-range-time").val().slice(22,41);

            var deviceName = $("#device-name").val();
            var alarmCode = $("#alarm-code").val();
            var deviceSN = $("#alarm-device-sn").val();
            if( 0 == deviceSN ) {
            	deviceSN="";
            }

            var queryUrl = "/device/alarm/queryHistoryAlarms?" + "startDate=" + startDate +
                "&endDate=" + endDate + "&deviceName=" + deviceName +
                "&deviceSN=" + deviceSN + "&alarmCode=" + alarmCode ;

            historyAlarmTable.ajax.url( queryUrl ).load();
        }

    });

});