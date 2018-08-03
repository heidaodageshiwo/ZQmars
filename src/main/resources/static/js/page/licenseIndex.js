/**
 * Created by  jiangqi.yang on 2016/12/6.
 */


$(function () {
    
    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

    var deviceLicenseTable = $('#device-license-table').DataTable({
        paging: true,
        lengthChange: false,
        searching: true,
        ordering: true,
        info: true,
        autoWidth: false,
        processing: true,
        ajax: "/device/license/getDevLicenses",
        dataSrc: "",
        columns: [
            { "data": "siteName" },
            { "data": "deviceSN" },
            { "data": "deviceName" },
            {
                "data": "isLicense",
                "render" : function (data, type, row ) {
                    var licenseStatusTxt="许可过期";
                    if( 0 == data ) {
                        licenseStatusTxt="许可正常";
                    }
                    return licenseStatusTxt;
                }
            },
            { "data": "expireTime" },
            {
                "orderable":      false,
                "data":            null,
                "render" : function (data, type, row ) {
                    var dom = '<button data-sn="' +  + data.deviceSN + '" class="btn btn-flat btn-sm bg-navy notify-licenseUpdate-btn">更新许可</button>';
                    return dom;
                }
            }
        ],
        order: [[ 1, 'desc' ]],
        "createdRow": function( row, data, dataIndex ) {
            if ( data.isLicense != 0  ) {
                $(row).addClass( 'text-red' );
            }
        },
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
        dom: "<'row'<'col-sm-4'><'col-sm-8'f>>" +
        "<'row'<'col-sm-12'tr>>" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });


    $('#device-license-table tbody').on( 'click', 'tr button.notify-licenseUpdate-btn', function () {
        var buttonDom = this;
        var tr = $(buttonDom).closest('tr');
        var rowData = deviceLicenseTable.row(tr).data();

        $("#filename").val('');
        $("#uploadFilename").val('');

        $("#dlg-device-info").text(rowData.deviceName + '(' + rowData.deviceSN + ')');
        $("#dlg-device-sn").val(rowData.deviceSN);

        $("#license-dlg").modal('show');
    });

    $('#uploadFile').fileupload({
        url: "/uploadFile",
        paramName: "uploadFile",
        dataType: 'json',
        done: function (e, data) {
            if( true == data.result.status) {
                $("#filename").val( data.result.data.originalFilename );
                $("#uploadFilename").val( data.result.data.filename );
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
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');


    $("#license-ok-btn").click(function (e) {

        var uploadFilename = $("#uploadFilename").val();
        if( (uploadFilename == undefined) || ( uploadFilename == "" )) {
            $("#filename").val("");
            Messenger().post({
                message:  "请上传授权文件",
                type: 'error',
                showCloseButton: true
            }) ;
            return ;
        }

        var filename = $("#filename").val();


        if( (filename == undefined) || ( filename == "" )) {
            $("#uploadFilename").val("");
            Messenger().post({
                message:  "请上传授权文件",
                type: 'error',
                showCloseButton: true
            }) ;
            return ;
        }
        var deviceSN = $("#dlg-device-sn").val();

        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: "/device/license/updateFile",
            data: { deviceSN: deviceSN, filename:filename, uploadFilename: uploadFilename},
            timeout: 5000,
            success: function ( data ) {
                $('#license-dlg').modal('hide');
                if( true == data.status ) {
                    Messenger().post({
                        message:  data.message,
                        type: 'success',
                        showCloseButton: true
                    }) ;
                    deviceLicenseTable.ajax.reload(null,false);
                } else {
                    Messenger().post({
                        message:  data.message,
                        type: 'error',
                        showCloseButton: true
                    }) ;
                }
            }
        });

    });
    
});