$.ajaxSetup ({
	   cache: false //关闭AJAX相应的缓存
	});


$(function(){
	/*初始化用户grid高度*/
	$("#system_grid").height($(window).height()-80);
	
	$('#systemModelview').myDatagrid({
		"title" : "系统信息列表",
		"url" : "../systemModel/list.json",
		"columns" : [[
			{field : 'id', 			title : '编号', 	width : 40, align : 'center', sortable : true},
			{field : 'name',		title : '系统名称',width : 60, align : 'center', sortable : true}, 
			{field : 'description',	title : '系统描述',width : 60, align : 'center'}
		]],
		"system": "upms",
		"model": "systemModel",
		"dblClickHandler": "editHandler",
		/*增删改配置*/
		"editHandler": {
			"enable": true,
			"title": "修改", 
			"form": {"submitUrl": "../systemModel/update.do"}
		},
		"addHandler": {
			"enable": true,
			"title": "添加", 
			"form": {"submitUrl": "../systemModel/create.do"}
		},
		"removeHandler": {
			"enable": true,
			"title": "删除",
			"removeUrl": "../systemModel/deleteSystemModelInfo.json", 
			"idField": "id",
			"idParams": "sysId"
		}
	});
});

/*
*点击“查询”后提交查询
*/
function submitQuerySystemModel() {
	$('#systemModelview').datagrid('load', $("#fm_search").form("formToJson"));
}
