/**
 * Created by  jiangqi.yang on 2016/11/1.
 */

$(function () {

    //扩展表单验证
    // 字符验证
    jQuery.validator.addMethod("stringCheck", function(value, element) {
        return this.optional(element) || /^([a-zA-Z0-9\u4e00-\u9fa5-_])+([ ])*$/.test(value);
    }, "只能包括中文字、英文字母、数字和下划线!");

    //身份证号验证
    jQuery.validator.addMethod("isIdCardNo", function(value, element) {
        var idcard = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
        return this.optional(element) || (idcard.test(value));
    }, "请输入正确格式的身份证号！");

    // 联系电话(手机/电话皆可)验证
    jQuery.validator.addMethod("isPhone", function(value,element) {
        var length = value.length;
        var mobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
        var tel = /^(\d{3,4}-)?\d{7,9}$/;
        return this.optional(element) || (tel.test(value) || mobile.test(value));
    }, "请正确填写您的联系电话");

    // 手机号码验证
    jQuery.validator.addMethod("isMobile", function(value, element) {
        var length = value.length;
        var mobile =  /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "手机号码格式错误");

    // 电话号码验证
    jQuery.validator.addMethod("isTel", function(value, element) {
        var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
        return this.optional(element) || (tel.test(value));
    }, "电话号码格式错误");

    // 验证值不允许与特定值等于
    jQuery.validator.addMethod("notEqual", function(value, element,param) {
        return value != param;
    }, "输入值不允许为{0}!");


    // 验证值不允许为空字符
    jQuery.validator.addMethod("isSpace", function(value, element,param) {
        return !(value.replace(/(^\s*)|(\s*$)/g, "").length ==0);
    }, "不能为空格字符");

    //费率
    jQuery.validator.addMethod( "rate", function( value, element ) {
        return this.optional( element ) || /^[0-9]{1,2}$/.test( value );
    }, "请输入有效数据" );

    //比较时间
    jQuery.validator.addMethod( "compareTime", function( value, element, param ) {
        var startTime = jQuery(param).val();
        var time1 = new Date(Date.parse( "01/01/2016 " + startTime ));
        var time2 = new Date(Date.parse( "01/01/2016 " + value ));
        return time1 < time2;

    }, "结束时间必须大于开始时间!" );

    //年份
    jQuery.validator.addMethod( "year", function( value, element ) {
        return this.optional( element ) || /^[0-9][0-9][0-9][0-9]$/.test( value );
    }, "请输入有效年份" );

});
