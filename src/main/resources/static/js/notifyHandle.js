/**
 * Created by jiangqi.yang  on 2016/11/14.
 */

var EfenceWebSocket = function ( options ) {
    this.url = options.url;
    this.ws = null;
    this.handlers = [];
};

EfenceWebSocket.prototype.connect = function () {
    _this = this;
    if( this.url ) {
        this.ws = new SockJS(this.url);
        this.ws.onopen = function () {
            console.log("Info: WebSocket connection opened.");
        };
        this.ws.onmessage = function ( data ) {
            var packet = eval('(' + data.data + ')');
            _this.handler(packet);
        };
        this.ws.onclose = function () {
            console.log('Info: WebSocket connection closed.');
        };
    }
};

EfenceWebSocket.prototype.disconnect = function () {
    if ( this.ws != null) {
        this.ws.close();
        this.ws = null;
    }
};

EfenceWebSocket.prototype.addHandleFun = function ( name, handlerFun ) {
    var handler = {
        name: name,
        handler: handlerFun
    };

    this.handlers.push(handler);
};

EfenceWebSocket.prototype.removeHandleFun = function ( name ) {
    var _this = this;
    this.handlers =  $.grep(_this.handlers, function ( value, index ) {
        if( value.name == name ) {
            return false;
        }
        return true;
    });
};

EfenceWebSocket.prototype.clearHandleFun = function () {
    this.handlers = [];
};

EfenceWebSocket.prototype.handler = function ( packet ) {
   var _this = this;
    $.each( _this.handlers, function ( index , value ) {
        if( value.name === packet.type ) {
            value.handler( packet.data  );
        }
    })
};

function  messageHandler ( data ) {

    if( data.count ) {
        $("#message-notify-count").text(data.count);
        $("#message-notify li:eq(0)").text("您有" + data.count +"条消息");
    }

    var $messageUl = $("#message-notify li:eq(1) ul");
    $messageUl.empty();
    $.each(data.messages, function ( index, value ) {
        var liDom= [];
        liDom.push('<li>');
        liDom.push('<a href="' + value.url + '">');
        liDom.push('<i class="fa fa-envelope-o text-info"></i>' + value.info );
        liDom.push('</a>');
        liDom.push('</li>');

        $messageUl.prepend(liDom.join(''));
    });

}

function  alarmHandler ( data ) {

    if( data.count ) {
        $("#alarm-notify-count").text(data.count);
        $("#alarm-notify li:eq(0)").text("您有" + data.count +"条告警");
    }

    var $alarmUl = $("#alarm-notify li:eq(1) ul");
    $alarmUl.empty();
    $.each(data.alarms, function ( index, value ) {

        var liDom= [];
        liDom.push('<li>');
        liDom.push('<a href="' + value.url  + '">');
        if( 1 == value.type ) {
            liDom.push('<i class="fa fa-warning text-yellow"></i>' +  value.info );
        } else {
            liDom.push('<i class="fa fa-user text-red"></i>' +  value.info );
        }
        liDom.push('</a>');
        liDom.push('</li>');

        $alarmUl.prepend(liDom.join(''));

    });

}

var timestamp2;

function servTimeHandle ( data ) {
	
	timestamp2 = Date.parse(new Date(data));
	timestamp2 = (timestamp2 / 1000);

}

Date.prototype.format = function(format) {
    var date = {
       "M+": this.getMonth() + 1,
       "d+": this.getDate(),
       "h+": this.getHours(),
       "m+": this.getMinutes(),
       "s+": this.getSeconds(),
       "q+": Math.floor((this.getMonth() + 3) / 3),
       "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
       format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
       if (new RegExp("(" + k + ")").test(format)) {
           format = format.replace(RegExp.$1, RegExp.$1.length == 1
              ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
       }
    }
    return format;
}

function getServerTimeData() {
	/*$("#showServTime").text(serverTime);*/
	var newDate = new Date();
	timestamp2 = timestamp2 + 1;
	newDate.setTime(timestamp2 * 1000);
	$("#showServTime").text(newDate.format('yyyy-MM-dd hh:mm:ss'));
}

var getServTimeDataTimer = setInterval( getServerTimeData, 1000);

function  taskHandler ( data ) {
    if( data.count != undefined ) {
        $("#task-notify-count").text(data.count);
        $("#task-notify li:eq(0)").text("您有" + data.count +"条任务");
    }

    var $taskUl = $("#task-notify li:eq(1) ul");
    $taskUl.empty();
    $.each(data.tasks, function ( index, value ) {

        var liDom= [];
        liDom.push('<li>');
        liDom.push('<a href="' +  value.url  +'">');
        liDom.push('<h3>' + value.info);
        liDom.push('<small class="pull-right">' +  value.progress +  '%</small>');
        liDom.push('</h3>');
        liDom.push('<div data-taskid=' +  value.id + ' class="progress xs">');
        liDom.push('<div class="progress-bar progress-bar-green" style="width:'+  value.progress + '%" role="progressbar" aria-valuenow="'
                       + value.progress + '" aria-valuemin="0" aria-valuemax="100">');
        liDom.push('<span class="sr-only">' + value.progress + '% Complete</span>');
        liDom.push('</div>');
        liDom.push('</div>');
        liDom.push('</a>');
        liDom.push('</li>');

        $taskUl.prepend(liDom.join(''));
    });

}


function taskProgressHandler( data ) {

    var progressNowVal = data.progress.toFixed(2);

    //刷新top
    var $progressDiv = $("#task-notify div[data-taskid=" + data.taskId +  "] ");
    if( $progressDiv != undefined ) {
        $($progressDiv).find("div.progress-bar").css("width", progressNowVal + '%');
        $($progressDiv).find("div.progress-bar").attr("aria-valuenow", progressNowVal);
        $($progressDiv).find("div.progress-bar span").text( progressNowVal + '% Complete');
        $($progressDiv).parent().find("h3 small").text( progressNowVal + '% Complete');
    }

    //刷新table
    var $tableProgressDiv = $("table div.progress[data-taskid=" + data.taskId +  "] ");
    if( $tableProgressDiv != undefined ){
        $($tableProgressDiv).find("div.progress-bar").css("width", progressNowVal + '%');
        $($tableProgressDiv).parent("td").find("span").text( progressNowVal + '%');
    }

}


$(function () {

    efenceNotify  =  new EfenceWebSocket( { url: "/noticeWS"});
    efenceNotify.addHandleFun( "updateMessageNotify", messageHandler );
    efenceNotify.addHandleFun( "updateAlarmNotify", alarmHandler );
    efenceNotify.addHandleFun( "updateTaskNotify", taskHandler );
    efenceNotify.addHandleFun( "updateProgressValue", taskProgressHandler );
    efenceNotify.addHandleFun( "serverTimeNotify", servTimeHandle );

    efenceNotify.connect();


});
