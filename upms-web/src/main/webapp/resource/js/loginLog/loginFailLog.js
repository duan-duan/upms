$(function(){
	$("#loginFail_grid").height($(window).height()-106);
	/*初始化用户grid高度*/
	$('#loginFailLogview').myDatagrid({
		title : '登录失败列表',
		url : "../loginFail/list.json",
		columns : [ [ {
			field : 'id',
			title : '编号',
			width : 40,
			sortable : true,
			align : 'center'
		}, {
			field : 'username',
			title : '用户名',
			width : 60,
			sortable : true,
			align : 'center'
		},{
			field : 'content',
			title : '内容',
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
			field : 'insertDate',
			title : '操作时间',
			width : 60,
			sortable : true,
			align : 'center',
			formatter : function(val,rec){
				var d = new Date(val);
				return UI.Date.dateStr(d, "yyyy-mm-dd hh:mm:ss");
			}
		}
		]]
	});
});


/*
*点击“查询”后提交查询
*/
function submitQueryFailLoginLog() {
	$('#loginFailLogview').datagrid('load', $("#fm_search").form("formToJson"));
}