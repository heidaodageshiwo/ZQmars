/**
 * Created by jiangqi.yang on 2016/11/21.
 */

$(function () {

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };

    $.ajax({
        type: "post",
        url: "/system/sms/getSmsTemplate",
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                $("#sms-template").val(data.data);
            }
        }
    });
    
    $("#sms-template-refresh-btn").click(function () {
        $.ajax({
            type: "post",
            url: "/system/sms/getSmsTemplate",
            dataType:"json",
            success:  function( data ){
                if( true == data.status ) {
                    $("#sms-template").val(data.data);
                }else {
                    Messenger().post({
                        message: data.message,
                        type: 'error',
                        showCloseButton: true
                    });
                }
            }
        });
    });

    function verificationSmsTpl( smsTpl ) {
        var verifyReslt = false;
        smsTpl = smsTpl.trim();
        if( smsTpl.length <=0 ) {
            Messenger().post({
                message: "请输入模板",
                type: 'error',
                showCloseButton: true
            });
            return verifyReslt;
        }

        if( smsTpl.length >= 255 ) {
            Messenger().post({
                message: "短信模板过长，请控制在255个字符内！",
                type: 'error',
                showCloseButton: true
            });
            return verifyReslt;
        }

        if(smsTpl.indexOf("{%user%}") == -1 ) {
            Messenger().post({
                message: "没有{%user%}标识",
                type: 'error',
                showCloseButton: true
            });
            return verifyReslt;
        }

        verifyReslt = true;

        return verifyReslt;

    }

    $("#sms-template-change-btn").click(function () {
        var data = $("#sms-template").val();
        if( false === verificationSmsTpl( data) ) {
            return ;
        }

        $.ajax({
            url:"/system/sms/setSmsTemplate",
            type:"post",
            data:{ smsTemplate: data},
            success:function(data){
                if( true == data.status ){
                    Messenger().post({
                        message: data.message,
                        type: 'success',
                        showCloseButton: true
                    });
                }else{
                    Messenger().post({
                        message: data.message,
                        type: 'error',
                        showCloseButton: true
                    });
                }
            },
            error:function(data){
                Messenger().post({
                    message: "修改出错，请稍后尝试...",
                    type: 'error',
                    showCloseButton: true
                });
            }
        });
    });

    
});
