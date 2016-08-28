refrushCache();

var randomNu = (new Date().getTime()) ^ Math.random();

$(function() {
	
	//加载下拉框

	$("#sysId1").combobox({ 
		url:"../roleManage/sysList.json?random="+randomNu,
		valueField:'id',
		textField:'name',
		method:'post'
	});
	
	
	$('#roleview').myDatagrid({
		title: '角色管理',
		border: false,
		toolbar: [{
			id : 'btnadd',
			text : '添加',
			iconCls : 'icon-add',
			handler : function() {
				$('#role_dlg').dialog('open').dialog('setTitle', '添加新角色');
				$("#rolename").removeAttr("disabled");
				//加载下拉框
				$.ajax({  
					   type: "POST",  
					   url: "../roleManage/sysList.json?random="+randomNu,  
					   dataType:"json",  
					   success: function(data){
					     $("#sysId").combobox({  
					        data:data,  
					        valueField:'id',  
					        textField:'name',  
					        onLoadSuccess: function(data) {// 默认选中第一条
					                 if (data) {
					                     $('#sysId').combobox('setValue',data[0].id);
					                 }
					        }
					    });  
					   }  
				 });
				$('#sysId').combobox('getValue');//获取下拉框选中的值
				url = "../roleManage/create.htm";
				$('#role_fm').form('clear');
			},
			permission: "upms:role:add" //添加角色权限
		}, {
			id : 'btnedit',
			text : '修改',
			iconCls : 'icon-edit',
			handler : function() {
				var row = $('#roleview').datagrid('getSelected');
				// alert(JSON.stringify(row));可以看到具体的JSON值
				if (row) {
					$('#role_dlg').dialog('open').dialog('setTitle', '编辑角色');
					$("#rolename").attr("disabled",
							"disabled");
					//加载下拉框
					$.ajax({  
					   type: "POST",  
					   url: "../roleManage/sysList.json?random="+randomNu,  
					   dataType:"json",  
					   success: function(data){
					     $("#sysId").combobox({  
					        data:data,  
					        valueField:'id',  
					        textField:'name',  
					        onLoadSuccess: function(data) {
					        	 var da = $('#sysId').combobox('getData');
					             var defaultSysId = row.sysId;//JSON中传过来的默认值可以知道需要选取哪一个为默认值
					             var defaultIndex = 0;
					             //根据默认的SYS_ID找到需要显示的默认项
					             for(var i=0,len=da.length-1;i<=len;i++){
					            	 if(defaultSysId==(da[i].id)){
					            		 defaultIndex = i;
					            		 break;
					            	 }
					             }
					             //下拉列表显示默认的系统项
					              if (da.length > 0) {
					                 $('#sysId').combobox('select', da[defaultIndex].id);
					             } 
					        }
					    });  
					   }  
					 });
					$('#sysId').combobox('getValue');//获取下拉框选中的值
					
					
					url = "../roleManage/update.htm?random="+randomNu;
					$('#role_fm').form('load', row);
				} else {
					$.messager.alert('温馨提示',
							'请选择需要编辑的角色!', 'info');
				}
				
			},
			permission: "upms:role:edit" //修改角色权限
		}, {
			id : 'btndel',
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				var row = $('#roleview').datagrid('getSelected');
				if (row) {
					$.messager.confirm('Confirm','您确定要删除此条角色记录?', function(r) {
						if (r) {
							$.post('../roleManage/deleteRoleInfo.json',{srId : row.id}, function(data) {
								var operateFlag = data.isContainPermission;
								var isSuccess = data.success;
								if(operateFlag){
									msg.bottomRight("错误提示","删除角色失败！不允许删除已授权的角色");
								}else{
									if(isSuccess){
										$('#roleview').datagrid('reload');
										
										$('#button_panel').panel('close');
										
								        $('#authorize_panel').panel('close');
									}else{
										msg.bottomRight("错误提示","删除角色失败！");
									}
								}
							}, 'json');
						}
					});
				} else {
					$.messager.alert('温馨提示',
							'请选择需要删除的角色!', 'info');
				}
			},
			permission: "upms:role:remove" //删除角色权限
		} ],
		fitColumns : true,//自动大小
		url : '../roleManage/list.json?random='+randomNu,
		columns : [[{
			field : 'id',
			title : '编号',
			width : 25,
			sortable : true,
			align : 'center'
		},{
			field : 'name',
			title : '角色名',
			width : 20,
			sortable : true,
			align : 'center'
		}, {
			field : 'sysName',
			title : '所属系统',
			width : 20,
			sortable : false,
			align : 'center'
		}, {
			field : 'operate',
			title : '操作',
			width : 15,
			align : 'center',
			formatter : function(value,rowData,index){
				var a = "";
				if(permissable("upms:role:accredit")){ //角色授权权限
					var srId = rowData.id;
					var sysId = rowData.sysId;
					var a = '<a href="javascript:void(0)" style="color:#000; cursor: pointer;" onclick="authorizerow('+srId+','+sysId+')">[授权]</a> ';
				}
				return a;
			}
		}, 
		]]
	});
	
	/*查询grid*/
	$("#search_form_submit").on("click", function(){
		$('#roleview').datagrid("load", $("#search_form").form("formToJson"));
	});
	$("#search_form_reset").on("click", function(){
		$("#search_form").form("reset");
	});
});

/*
*点击“保存”后新增一条信息
*/
function saveRole() {
	$('#role_fm').form('submit', {
		url :url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			if (result) {
				msg.bottomRight("提示信息", "操作成功");
				$('#role_dlg').dialog('close'); // close the dialog
				$('#roleview').datagrid('reload');
			} else {
				msg.bottomRight("失败信息", "操作失败");
			}
		}
	});
}
	
/*
*点击“授权”后，显示右方数据
*/
function authorizerow(srId,sysId){ 
	
	refrushCache();
	
	/*展开右边  &ouyang*/
	$("#test_layout").layout("expand", "east");
	$("#test_layout").layout("collapse", "east");
	$("#test_layout").layout("expand", "east");

	$('#button_panel').panel('close');

	$('#authorize_panel').panel('open');

	/**
	 * 页面上保存选中行的角色ID和系统Id
	 */
	$('#hiddenSrId').val(srId);
	$('#hiddenSysId').val(sysId);

	$("#resourceTree").tree(
			{
				url : '../rolepermission/tree_json.json?srId=' + srId
						+ '&sysId=' + sysId+"&random="+randomNu,
				border : false,
				// cascadeCheck: false,
				onCheck : function(node) {// 点击勾选事件发生的时候，根据是否勾选的情况，在树的右侧显示Button列表
					var isCheckedFlag = node.checked;
					if (!isCheckedFlag) {// CheckBox选框被选中以后
						// 调用显示按钮的函数
						showButtonViewFunc(node, srId, sysId);

					} else {// 取消勾选的时候关闭按钮视图
						$("#test_layout2").layout("collapse", "east");
						$('#button_panel').panel('close');
					}
				},
				onClick : function(node) {// 树形菜单中，点击某个节点，如果它被勾选着，则在右侧显示Button视图
					var isCheckedFlag = node.checked;
					if (isCheckedFlag) {// CheckBox选框是被选中的
						// 调用显示按钮的函数
						showButtonViewFunc(node, srId, sysId);
					} else {
						$('#button_panel').panel('close');
					}
				}
			});
        
} 
	
	
/*
 * 在树形的右侧显示某个节点对应的Button按钮情况
 * 
 */
function showButtonViewFunc(node,srId,sysId){
	
	refrushCache();
	
	$('#button_panel').panel('open');// 显示视图Panel
	/* 展开右边 &ouyang */
	$("#test_layout2").layout("expand", "east");
	$("#test_layout2").layout("collapse", "east");
	$("#test_layout2").layout("expand", "east");
	
	
	$('#buttonview').datagrid({//打开并显示按钮列表
		//title : '按钮功能',
		collapsible : true,//是否可折叠的
		rownumbers : false,//行号
		multiple : true,
		multiSort : true,//排序
		pagination : false,//分页控件
		singleSelect : false,//是否单选
		remoteSort : false,
		fitColumns : true,//自动大小
		method : 'post',
		//border: false,
		url : '../roleManage/buttonList.json?parentId='+node.id+'&srId='+srId+'&sysId='+sysId+"&random="+randomNu,
        frozenColumns:[[
		                {field:'ck',checkbox:true}
					]],
		columns : [ [
		  {
			field : 'spName',
			title : '操作按钮',
			width : 40,
			sortable : true,
			align : 'center'
		  } 
		] ],
		onLoadSuccess:function(data){  
			 $("#hiddenParentId").val(node.id);
			if(data){
	            $.each(data.rows, function(index, item){
			      if(item.checked){
			         $('#buttonview').datagrid('checkRow', index);
			      }
			    });
			 }
		}        
	});
}

/*
*为角色授权操作,针对树形菜单上的内容进行授权
*/
function makeRolePermission(){
	
	var nodes = $('#resourceTree').tree('getChecked',['checked','indeterminate']);
	var s = '';
	for(var i=0; i<nodes.length; i++){
		if (s != '') s += ',';
		s += nodes[i].id;
	}
	var row_srId = $("#hiddenSrId").val();
	
	$.post("../rolepermission/makePermission.json?random="+randomNu,
			{"idStr":s,"srId":row_srId},
			function(data){
			    var opResult = data.success;
			     if(opResult==true){
			    	 msg.bottomRight("提示信息", "保存成功");
						$('#roleview').datagrid('reload');
						$('#authorize_panel').panel('open').panel('refresh');
			     }else{
			    	 msg.bottomRight("失败信息", "操作失败");
			     }
			},
			"json");
}


/*
*为角色授权操作,针对树形菜单上节点对应的Button按钮操作授权
*/
function makeButtonPermission(){
	var rows = $('#buttonview').datagrid('getSelections');
	var s = '';
	for(var i=0;i<rows.length;i++){
		if (s != '') s += ',';
		s += rows[i].spId;
	}
	
	var parentId ;
	if((!rows)&&(rows.length>0)){
		parentId = rows[0].spParentId;//所有按钮的父节点都一样
	}else{
		parentId =  $("#hiddenParentId").val();
	}
	 
	var row_sysId = $("#hiddenSysId").val();
	var row_srId = $("#hiddenSrId").val();
	
		$.post("../rolepermission/makeButtonPermission.json?random="+randomNu,
				{"idStr":s,'sysId':row_sysId,'srId':row_srId,'parentId':parentId},
				function(data){
				    var opResult = data.success;
				     if(opResult==true){
				    	 msg.bottomRight("提示信息", "保存成功");
				    	
				     }else{
				    	 var msgStr = data.msg;
				    	 msg.bottomRight("失败信息", "操作失败!"+msgStr);
				    	
				     }
				},"json");
	
}


function refrushCache(){
	$.ajaxSetup ({
		   cache: false //关闭AJAX相应的缓存
		});
}
	