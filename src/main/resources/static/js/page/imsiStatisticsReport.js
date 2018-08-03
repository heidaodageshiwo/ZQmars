/**
 * Created by jiangqi.yang on 2016/10/29.
 */

function createBarChart() {
    var taskId = $("#statistics-task-id").val();
    // taskId = taskId.replace(/[^0-9]/g, '');
    // taskId = parseInt(taskId);
    var getDataUrl = "/analysis/imsiStatistics/report/" + parseInt(taskId) + "/getReportData";
    $.ajax({
        type: "post",
        url: getDataUrl,
        dataType:"json",
        success:  function( data ){
            if( true == data.status ) {
                if( data.data.sumTotal != undefined ) {
                    $("#sumTotal").text(data.data.sumTotal);
                }

                var yCount = data.data.data.ykeys.length;
                var xCount = data.data.data.data.length;
                var xyCount = yCount * xCount * 10;
                var divWidth = $(".chart-scroll").width();
                if( xyCount > divWidth ) {
                    $("#statistics-barChart").css("width", xyCount );
                }

                $('.chart-scroll').scrollbar({
                    "showArrows": true,
                    "scrollx": "advanced"
                    // "scrolly": "advanced"
                });

                var bar = new Morris.Bar({
                    element: 'statistics-barChart',
                    resize: true,
                    data: data.data.data.data,
                    // barColors: data.data.data.colors,
                    xkey: data.data.data.xkey ,
                    ykeys: data.data.data.ykeys,
                    labels: data.data.data.labels,
                    hideHover: 'auto'
                });
            }
        }
    });
}


$(function () {

    createBarChart();

});