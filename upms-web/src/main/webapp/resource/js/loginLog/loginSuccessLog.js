$(function(){
	$("#loginSuccess_grid").height($(window).height()-106);
	/*初始化用户grid高度*/
	$('#loginSuccessLogview').myDatagrid({
		title : '登录成功列表',
		url : "../loginSuccess/list.json",
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
			formatter : function(val, rec){
				//var d = new Date(val);
				return UI.Date.dateStr(val, "yyyy-mm-dd hh:mm:ss");
			}
		}
		]]
	});
});

/*
*点击“查询”后提交查询
*/
function submitQuerySuccessLoginLog() {
	$('#loginSuccessLogview').datagrid('load', $("#fm_search").form("formToJson"));
}