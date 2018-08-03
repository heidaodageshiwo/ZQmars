/**
 * Created by jiangqi.yang on 2016/11/5.
 */

$(function () {

    $('input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });

    var loginFormValidate = $( "#login-form" ).validate( {
        rules: {
            username: {
                required: true,
                normalizer: function( value ) {
                    return $.trim(value);
                }
            },
            password:{
                required: true,
                normalizer: function( value ) {
                    return $.trim(value);
                }
            }
        },
        messages: {
            username: "请输入用户名",
            password:{
                required: "请在输入密码"
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

    $("#login-form").bind("submit", function () {

        if( loginFormValidate.form()) {
            var data = $(this).serialize();
            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: '/user/loginPost',
                data: data,
                timeout: 5000,
                success: function ( data ) {
                    if( true == data.status ) {
                        window.location.href = "/";
                    } else {
                        $("#error-text").text(data.message);
                        $("#error-text").removeClass("hidden");
                    }
                }
            });
        }
        
        return false;

    });

    $("input[name=password]").on("change", function (evt) {
        $("#error-text").addClass("hidden");
    });
    $("input[name=username]").on("change", function (evt) {
        $("#error-text").addClass("hidden");
    })

});