/**
 * Created by jiangqi.yang on 2016/11/18.
 */

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

    function downloadFile( url ){
        var IFrameRequest=document.createElement("iframe");
        IFrameRequest.id="IFrameRequest";
        IFrameRequest.src= url;
        IFrameRequest.style.display="none";
        document.body.appendChild(IFrameRequest);
    }

    $('input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });

    $("#dump-data-form").submit(function () {
        var dataForm = $(this).serialize();
        if( (dataForm == null) || ( '' == dataForm) ) {
            Messenger().post({
                message: "请选择导出数据类型",
                type: 'info',
                showCloseButton: true
            });
            return false;
        }

        $("#dump-data-form button").prop('disabled', true);

        $.ajax({
            type: "post",
            url: "/system/databaseMaintenance/exportBaseData",
            data: dataForm,
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    $("#dump-data-form button").prop('disabled', false);
                    downloadFile(data.data.url);
                } else {
                    Messenger().post({
                        message: data.message,
                        type: 'error',
                        showCloseButton: true
                    });
                }
            }
        });
        return false;
    });

    $('#uploadFile').fileupload({
        url: "/uploadSqlFile",
        paramName: "uploadFile",
        dataType: 'json',
        done: function (e, data) {
            if( true == data.result.status) {
                $("#execute-script-btn").prop('disabled', false);
                $("#uploadFileName").val( data.result.data.originalFilename );
                $("#fileUrl").val( data.result.data.filename );
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

            $("#upload-executeFile-progress .progress-bar").css(
                'width',
                progress + '%'
            );
            $("#upload-executeFile-progress .progress-bar").attr("aria-valuenow", progress);
            $("#upload-executeFile-progress .progress-bar span").text(  progress + '% Complete' );

        },
        start: function ( e) {
            $("#execute-script-btn").prop('disabled', true);
            $("#upload-executeFile-progress").show();
        },
        stop: function ( e ) {
            $("#upload-executeFile-progress").hide();
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');


    $("#execute-script-btn").click(function () {

        $("#execute-script-btn").prop('disabled', true);
        var fileUrl = $("#fileUrl").val();
        $.ajax({
            type: "post",
            url: "/system/databaseMaintenance/importBaseData",
            data: { file: fileUrl},
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    //todo  添加任务进度
                    Messenger().post({
                        message: data.message,
                        type: 'success',
                        showCloseButton: true
                    });
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
    
});