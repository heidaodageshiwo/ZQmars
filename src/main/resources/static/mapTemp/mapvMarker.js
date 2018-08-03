(function(){

	var mapv = new Mapv({
    	map: mp  // 百度地图的map实例
    });


    var data = [
         { lng: 121.469297,  lat: 31.255476, count: parseInt(Math.random() * 10) },
         { lng: 121.416836,  lat: 31.138838, count: parseInt(Math.random() * 10) },
         { lng: 121.413418,  lat: 31.133607, count: parseInt(Math.random() * 10) }
        
    ]; 

    var layer = new Mapv.Layer({
        mapv: mapv, // 对应的mapv实例
        zIndex: 1, // 图层层级
        dataType: 'point', // 数据类型，点类型
        data: data, // 数据
        drawType: 'density', // 展示形式
        drawOptions: { // 绘制参数
            type: "honeycomb", // 网格类型，方形网格或蜂窝形
            size: 50, // 网格大小
            globalAlpha: 0.8,
            unit: 'px', // 单位
            label: { // 是否显示文字标签
                show: true,
            },
            gradient: { // 显示的颜色渐变范围
                '0': 'blue',
                '0.6': 'cyan',
                '0.7': 'lime',
                '0.8': 'yellow',
                '1.0': 'red'
            },
            events: {             
              'click' : function(e, point){
            	 console.log(e);
              },   
            }             
            
        }
    });

    function updateGPSData(e){
   	    var data = [
         	{ lng: 121.469297,  lat: 31.255476, count: parseInt(Math.random() * 10) },
         	{ lng: 121.416836,  lat: 31.138838, count: parseInt(Math.random() * 10) },
         	{ lng: 121.413418,  lat: 31.133607, count: parseInt(Math.random() * 10) }        
    	]; 

    	layer.setData(data);
    	console.log(data);

	}
	//mp.addEventListener("click", updateGPSData);

 //    var layer3 = new Mapv.Layer({
	//     zIndex: 3,
	//     mapv: mapv,
	//     dataType: 'polygon',
	//     data: [
	//         {
	//             geo: [
	//                 [113.39507, 28.879101],
	//                 [113.49507, 28.889101],
	//                 [113.46507, 28.929101],
	//                 [113.43507, 28.909101]
	//             ],
	//             count: 10
	//         }
	//     ],
	//     drawType: 'simple',
	//     drawOptions: {
	//         lineWidth: 8,
	//         strokeStyle: "rgba(255, 255, 0, 1)",
	//         fillStyle: "rgba(255, 0, 0, 0.8)"
	//     }
	// });

	// var layer4 = new Mapv.Layer({
	//     mapv: mapv,
	//     dataType: 'polyline',
	//     data: [
	//         {
	//             geo: [
	//                 [113.39507, 28.879101],
	//                 [113.49507, 28.889101],
	//                 [113.46507, 28.929101],
	//                 [113.43507, 28.909101]
	//             ],
	//             count: 10
	//         }
	//     ],
	//     drawType: 'simple',
	//     zIndex: 5,
	//     animation: true,
	//     drawOptions: {
	//         lineWidth: 2,
	//         strokeStyle: "rgba(0, 0, 255, 1)"
	//     },
	//     animationOptions: {
	//         radius: 10
	//     }
	// });

	// 创建一个图层
	// var layer5 = new Mapv.Layer({
	//     zIndex: 3, // 图层的层级
	//     mapv: mapv, // 对应的mapv
	//     dataType: 'point', // 数据类型，point:点数据类型,polyline:线数据类型,polygon:面数据类型
	//     //数据，格式如下
	//     data: [
	//         {
	//             lng: 113.46507, // 经度
	//             lat: 28.929101, // 纬度
	//             count: 1 // 当前点的权重值
	//         },
	//         {
	//             lng: 113.43507,
	//             lat: 28.909101,
	//             count: 2
	//         }
	//     ],
	//     drawType: 'simple', // 渲染数据方式, simple:普通的打点, [更多查看类参考](https://github.com/huiyan-fe/mapv/wiki/%E7%B1%BB%E5%8F%82%E8%80%83)
	//     // 渲染数据参数
	//     drawOptions: {
	//         fillStyle: "rgba(255, 255, 50, 1)",  // 填充颜色
	//         strokeStyle: "rgba(50, 50, 255, 0.8)", // 描边颜色，不传就不描边
	//         lineWidth: 5, // 描边宽度
	//         radius: 5, // 半径大小
	//         unit: 'px' // 半径对应的单位，px:默认值，屏幕像素单位,m:米,对应地图上的大约距离,18级别时候1像素大约代表1米
	//     }
	// });


})();