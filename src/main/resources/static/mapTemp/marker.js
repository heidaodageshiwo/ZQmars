(function () {

	//创建小狐狸
	//var pt = new BMap.Point(121.469297, 31.255476);
	var pt = new BMap.Point(119,35);
	var myIcon = new BMap.Icon("../img/marker.png", new BMap.Size(32,32));
	var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
	//mp.addOverlay(marker2);              // 将标注添加到地图中
	//marker2.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画

})();