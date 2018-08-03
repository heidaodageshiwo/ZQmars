/**
 * Created by jiangqi.yang on 2016/12/3.
 */

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

    $('#uploadFile').fileupload({
        url: "/uploadLicFile",
        paramName: "uploadFile",
        dataType: 'json',
        done: function (e, data) {
            if( true == data.result.status) {
                $("#uploadFileName").val( data.result.data.originalFilename );
                $("#fileTempName").val( data.result.data.filename );
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
            $("#uploadFileName").val("");
            $("#fileTempName").val("");
            $("#upload-progress").show();
        },
        stop: function ( e ) {
            $("#upload-progress").hide();
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');

    $("#licenseModal").on("show.bs.modal", function (e) {
        $("#uploadFileName").val("");
        $("#fileTempName").val("");
    });

    
    $("#submit-license-btn").on("click", function (e) {

        var originalFilename = $("#uploadFileName").val();
        var tempFilename = $("#fileTempName").val();

        if( "" == originalFilename || "" == tempFilename ) {
            Messenger().post({
                message:  "请上传许可文件",
                type: 'error',
                showCloseButton: true
            });
            return ;
        }

        $.ajax({
            async: false,
            type: "post",
            url: "/system/updateLicense",
            data: { originalFilename: originalFilename, filename: tempFilename },
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    $("#licenseModal").modal("hide");
                    window.location.reload();
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