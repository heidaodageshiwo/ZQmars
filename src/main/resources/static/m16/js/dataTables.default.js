//dataTables中文设置
var dataTable_language = '';
	dataTable_language+= '{';
	dataTable_language+= '"sLengthMenu": " _MENU_ ",					';
	dataTable_language+= '"sZeroRecords": "抱歉， 没有找到",					';
	dataTable_language+= '"sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",	';
	dataTable_language+= '"sInfoEmpty": "没有数据",						';
	dataTable_language+= '"sInfoFiltered": "(从 _MAX_ 条数据中检索)",			';
	dataTable_language+= '"sZeroRecords": "没有检索到数据",					';
	dataTable_language+= '"sSearch": "名称:",								';
	dataTable_language+= '"select": {						';
	dataTable_language+= '	"rows": {						';
	dataTable_language+= '		"_": "已选择%d行",				';
	dataTable_language+= '		"0": "",				';
	dataTable_language+= '		"1": "已选择1行"				';
	dataTable_language+= ' 	}								';
	dataTable_language+= '},									';
	dataTable_language+= '"sProcessing":"正在查询,请稍后...",	';
	dataTable_language+= '"sLoadingRecords":"加载中...",		';
	dataTable_language+= '"oPaginate": {					';
	dataTable_language+= '"sFirst": "首页",					';
	dataTable_language+= '"sPrevious": "前一页",				';
	dataTable_language+= '"sNext": "后一页",					';
	dataTable_language+= '"sLast": "尾页"						';
	dataTable_language+= '}									';
	dataTable_language+= '}									';
	
	dataTable_language = JSON.parse(dataTable_language);