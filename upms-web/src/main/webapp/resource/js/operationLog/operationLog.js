$(function() {
	
	$("#operationlog_grid").height($(window).height()-106);
	$('#operationLogview').myDatagrid({
		title : '操作日志列表',
		collapsible : true,//是否可折叠的
		rownumbers : true,//行号
		multiple : true,
		multiSort : true,//排序
		pagination : true,//分页控件
		singleSelect : true,//是否单选
		remoteSort : false,
		fitColumns : true,//自动大小
		url : '../operationLog/list.json',
		fit: true,
		columns : [ [ {
			field : 'id',
			title : '编号',
			width : 40,
			sortable : true,
			align : 'center'
		}, {
			field : 'title',
			title : '标题',
			width : 60,
			sortable : true,
			align : 'center'
		}, {
			field : 'username',
			title : '用户名',
			width : 60,
			sortable : true,
			align : 'center'
		},{
			field : 'ip',
			title : 'IP地址',
			width : 60,
			sortable : true,
			align : 'center'
		},{
			field : 'content',
			title : '操作内容',
			width : 60,
			sortable : true,
			align : 'center'
		},{
			field : 'insertDate',
			title : '操作时间',
			width : 60,
			sortable : true,
			align : 'center',
			formatter : function(val, rec){
				var d = new Date(val);
				return UI.Date.dateStr(d, "yyyy-mm-dd hh:mm:ss");
			}
		}
		] ]
	});
	
});
	


	/*
	*点击“查询”后提交查询
	*/
	function submitQuery() {
		$('#operationLogview').datagrid('load', $("#fm_search").form("formToJson"));
	}