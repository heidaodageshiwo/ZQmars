
function  fixMapperWrapper() {
	//Get window height and the wrapper height
	var neg = $('.main-header').outerHeight() + $('.main-footer').outerHeight();
	var window_height = $(window).height();
	var sidebar_height = $(".sidebar").height();
	//Set the min-height of the content and sidebar based on the
	//the height of the document.
	if ($("body").hasClass("fixed")) {
		$("#device-map").css('min-height', window_height - $('.main-footer').outerHeight()- $('.content-header').outerHeight() );
	} else {
		var postSetWidth;
		if (window_height >= sidebar_height) {
			$("#device-map").css('min-height', window_height - neg - $('.content-header').outerHeight());
			postSetWidth = window_height - neg - $('.content-header').outerHeight();
		} else {
			$("#device-map").css('min-height', sidebar_height- $('.content-header').outerHeight());
			postSetWidth = sidebar_height - $('.content-header').outerHeight();
		}

		//Fix for the control sidebar height
		var controlSidebar = $($.AdminLTE.options.controlSidebarOptions.selector);
		if (typeof controlSidebar !== "undefined") {
			if (controlSidebar.height() > postSetWidth)
				$("#device-map").css('min-height', controlSidebar.height() - $('.content-header').outerHeight());
		}
	}
}


(function () {

	$(window, ".wrapper").resize(function () {
		fixMapperWrapper();
	});

	fixMapperWrapper();

	var mapOptions={
		minZoom: 10,
		maxZoom:14,
		mapType:  BMAP_NORMAL_MAP
	}
	mp = new BMap.Map('device-map',mapOptions);
	var point = new BMap.Point(121.491, 31.233); 
    mp.centerAndZoom( point, 13);

    mp.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

    // window.setTimeout(function(){  
    // 	mp.panTo(new BMap.Point(121.478675, 31.2288));    
    // }, 2000);

    //添加控件
    //mp.addControl(new BMap.NavigationControl());    
	//mp.addControl(new BMap.ScaleControl());    
	//mp.addControl(new BMap.OverviewMapControl());    
	//mp.addControl(new BMap.MapTypeControl());   
	//mp.addControl(new BMap.GeolocationControl()); 


     // 定义一个控件类，即function    
	function ZoomControl(){    
	    // 设置默认停靠位置和偏移量  
	    this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;    
	    this.defaultOffset = new BMap.Size(10, 10);    
	}    
	// 通过JavaScript的prototype属性继承于BMap.Control   
	ZoomControl.prototype = new BMap.Control();

	// 自定义控件必须实现initialize方法，并且将控件的DOM元素返回   
	// 在本方法中创建个div元素作为控件的容器，并将其添加到地图容器中   
	ZoomControl.prototype.initialize = function(map){    
		// 创建一个DOM元素   
		 var div = document.createElement("div");    
		// 添加文字说明    
		 div.appendChild(document.createTextNode("放大2级"));    
		 // 设置样式    
		 div.style.cursor = "pointer";    
		 div.style.border = "1px solid gray";    
		 div.style.backgroundColor = "white";    
		 // 绑定事件，点击一次放大两级    
		 div.onclick = function(e){  
		  map.zoomTo(map.getZoom() + 2);    
		 }    
		 // 添加DOM元素到地图中   
		 map.getContainer().appendChild(div);    
		 // 将DOM元素返回  
		 return div;    
	 }


	 // 创建控件实例    
	 var myZoomCtrl = new ZoomControl();
	 mp.addControl(myZoomCtrl); 

})();