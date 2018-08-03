/**
 * Created by jiangqi.yang on 2016/12/13.
 */

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

    $("#sys-icon").click(function (e) {
        $("#upload-file").trigger("click");
    });

    $('#upload-file').fileupload({
        url: "/uploadImage",
        paramName: "file",
        dataType: 'json',
        done: function (e, data) {
            if( true == data.result.status) {
                var d = new Date();
                var imgUrl = data.result.data.url + "?t=" + d.getTime();
                $("#sysConf-form input[name=systemIcon]").val( data.result.data.url );
                $("#sys-icon img").attr( "src", imgUrl );

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
            $("#upload-progress").show();
        },
        stop: function ( e ) {
            $("#upload-progress").hide();
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');


    function loadConfigInfo() {
        $.ajax({
            type: "get",
            url: "/system/getConfig",
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    $("#sysConf-form input[name=systemName]").val( data.data.systemName );
                    $("#sysConf-form input[name=systemIcon]").val( data.data.systemIcon );
                    $("#sys-icon img").attr( "src", data.data.systemIcon );
                    $("#sysConf-form input[name=companyName]").val( data.data.companyName);
                    $("#sysConf-form input[name=companyURL]").val( data.data.companyURL );
                    $("#sysConf-form input[name=companyYear]").val( data.data.companyYear );
                    $("#sysConf-form input[name=upgradeURL]").val( data.data.upgradeURL );
                    $("#sysConf-form input[name=ftpIP]").val( data.data.ftpIP );
                    $("#sysConf-form input[name=ftpPort]").val( data.data.ftpPort );
                    $("#sysConf-form input[name=mapCenterLng]").val( data.data.mapCenterLng );
                    $("#sysConf-form input[name=mapCenterLat]").val( data.data.mapCenterLat );
                    $("#sysConf-form input[name=mapZoom]").val( data.data.mapZoom );
                    if( 1 ==  data.data.mapDataSwitch ) {
                        $("#mapData-switch").bootstrapSwitch("state", true );
                    } else {
                        $("#mapData-switch").bootstrapSwitch("state", false );
                    }
                    if( 1 ==  data.data.devAlarmSms ) {
                        $("#devAlarmSms-switch").bootstrapSwitch("state", true );
                    } else {
                        $("#devAlarmSms-switch").bootstrapSwitch("state", false );
                    }

                }
            }
        });
    }

    loadConfigInfo();

    $("#sysConf-refresh-btn").click(function (e) {
        loadConfigInfo();
    });

    var sysConfFormValidate = $( "#sysConf-form" ).validate( {
        rules: {
            systemName: {
                required: true,
                stringCheck: true
            },
            companyName: {
                required: true,
                stringCheck: true
            },
            companyYear: {
                year: true
            },
            upgradeURL: {
                required: true,
                url2: true
            },
            mapCenterLng: {
                required: true,
                number: true
            },
            mapCenterLat: {
                required: true,
                number: true
            },
            mapZoom: {
                required: true,
                digits: true,
                range: [8, 18]
            }
        },
        messages: {
            systemName: {
                required: "请输入系统名称",
                stringCheck: "只能包括中文字、英文字母、数字和下划线!"
            },
            companyName: {
                required: "请输入公司名称",
                stringCheck: "只能包括中文字、英文字母、数字和下划线!"
            },
            companyYear: {
                year: "请输入正确格式的年份"
            },
            upgradeURL: {
                required: "请输入升级服务URL地址",
                url2: "请输入正确格式的URL"
            },
            mapCenterLng: {
                required: "请输入地图中心点经度值",
                number: "请正确格式的数据"
            },
            mapCenterLat: {
                required: "请输入地图中心点纬度值",
                number: "请正确格式的数据"
            },
            mapZoom: {
                required: "请输入地图默认显示级别,",
                digits: "请正确格式的数据",
                range: "请输入8-18级直接的数据"
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

    $("#sysConf-change-btn").click(function (e) {

        if( sysConfFormValidate.form()) {
            var data = $("#sysConf-form").serialize();
            var devAlarmSms = $('#devAlarmSms-switch').bootstrapSwitch("state");
            if( devAlarmSms ) {
                data = data + "&devAlarmSms=1";
            } else {
                data = data + "&devAlarmSms=0";
            }
            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: "/system/setConfig",
                data: data,
                timeout: 5000,
                success: function ( data ) {
                    if( true == data.status ) {
                        Messenger().post({
                            message:  data.message,
                            type: 'success',
                            showCloseButton: true
                        }) ;
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

    });

    $('#mapData-switch').bootstrapSwitch();
    $('#devAlarmSms-switch').bootstrapSwitch();

});