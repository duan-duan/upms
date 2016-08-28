var sp_id,sp_parent_id,comboboxId,sys_id;

$.ajaxSetup ({
	   cache: false //关闭AJAX相应的缓存
	});

$(function(){
	$('#treeGrid').myTreegrid({
		url:'../department/treegrid_json.json',
		iconCls:'icon-ok',
		rownumbers: true,
		animate:true,
		collapsible:true,
		fitColumns:true,
		method: 'get',
		cache:false,
		idField:'id',
		treeField:'text',
		border:false,
		fit: true,
		onClickRow: function(row){
			var node = $(this).treegrid('getSelected');
			if (node){
				$(this).treegrid('toggle', node.id);
			}
		},
		columns:[[
			{field:'text',title:'部门名称',width:40,editor:{
			    "type":"validatebox",
			    "options":{
			        "required":true
			    }
			}},
			{field:'code',title:'部门编码',width:40,editor:{
			    "type":"validatebox",
			    "options":{
			        "required":true
			    }
			}},
			{field:'rank',title:'顺序',width:20,editor:{
			    "type":"validatebox",
			    "options":{
			        "required":true
			    }
			}},
	        {field:'operation',title:'操作',editor:{
                  "type":"validatebox",
                  "options":{
                      "required":true
                  }
             },formatter:function(value,row){
            	 var sid ;
            	 if(permissable("upms:department:remove")){
            		  sid = row.id;
            		  value='<a id="delBtn" class="easyui-linkbutton" onclick="deleteDepartment('+sid+')" style="color:red;cursor:hand">[删除]</a>';
               		  return value;
            	 }
            	 return value;
             }}
		]],
		toolbar:[{
			id:'btnadd',
			text:'添加',
			iconCls:'icon-add',
			handler:function(){
				$('#btnadd').linkbutton('enable');
			 	$("#add").dialog('open');//打开添加区dialog
			  	$('#add_form').form('clear');
			  	$('#add_form').form('load',{
				         version:"2"
			    });
			  	$("#comboTree").combotree({
	    		    url:'../department/tree_json.json',
	    		    required:true,
	                valueField: 'departmentId',
	                textField: 'text',
	                required: true,
	                editable: false,
	    		    method:'get',	
	    		    onLoadSuccess:function(){
	    		    	 $('#comboTree').combotree('tree').tree("collapseAll"); 
	    		    },
	    		    onSelect:function(node){
	    		    	var departmentParentId = node.id;
	    		    	var departmentRank = node.rank; 
	    		    	$('#departmentParentId').val(departmentParentId);
	    		    	$('#departmentRank').val(departmentRank+1);
	 		        }// end onSelect		
		    	 });
			},
			permission: "upms:department:add" //添加部门权限
		},{
			id:'btnEdit',
			text:'修改',
			iconCls:'icon-edit',
			handler:function(){
			        var row  = $('#treeGrid').treegrid('getSelected');
			    	if(row){
			    		
			    		if(row.isSystem == "true"){
			    			 $.messager.alert("提示信息", "请选择非系统节点，系统节点不允许编辑");
			    		}else if(row.isSystem == "false"){//非系统节点
			    			 $('#edit').dialog('open');//弹出edit对话框
			    			 $.ajax({
			    	  		   url:'../department/get_node.json?id='+row.id,
			    	  		   cache:false,
			    	  		   type:"get",
			    	  		   error: function () {//请求失败处理函数
			    	  			      alert('请求失败');
			    	  			    },
			    	  		   dataType:"json",
			    	  		   success:function(data){ //加载节点数据成功
			    		  			    $('#edit_form').form('load', data);
			    	  		   }
			    	  	   });// end ajax
			    	    } // end else if
			    	}else{
			    		$.messager.alert('温馨提示','请选择需要编辑的节点!', 'info');
			    	}
			},
			permission: "upms:department:edit" //添加部门权限
		}]
	});
});

function submitAddForm(){
	 $('#add_form').form('load',{
		sys_id:sys_id
	 });
	 $('#add_form').form('submit', {
			url :'../department/add_node.do',
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				if (result) {
					$('#add').dialog('close');
					$("#treeGrid").treegrid('reload');
					window.parent.loadTree();
					$.messager.alert("提示信息", "操作成功");
				} else {
					$.messager.alert("失败信息", "操作失败");
					$.messager.show({
						title : 'Error',
						msg : result.errorMsg
					});
				}
			}
		});
}

function submitEditForm(){
	 $('#edit_form').form('load',{
		sys_id:sys_id
	 });
	 $('#edit_form').form('submit', {
			url :'../department/update.do',
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				if (result) {
					$('#edit').dialog('close');
					$("#treeGrid").treegrid('reload');
					window.parent.loadTree();
					$.messager.alert("提示信息", "操作成功");
				} else {
					$.messager.alert("失败信息", "操作失败");
					$.messager.show({
						title : 'Error',
						msg : result.errorMsg
					});
				}
			}
		});
}

//删除节点,如果该节点有子节点级联删除
function deleteDepartment(sid) { 
    $.messager.confirm('Confirm','您确定要删除该节点?',
  				  function(r) {
  					if (r) {
	   					 $.post('../department/delete.do?id='+sid,
	   							function(result) {
	   								if (result) {
	   									$.messager.alert("提示信息", "操作成功");
	   									$('#treeGrid').treegrid('remove',sid);
	   									$('#treeGrid').treegrid('reload');
	   									window.parent.loadTree();
	   								} else {
	   									$.messager.alert("提示信息", "操作失败");
	   								}
	   				     },'json');
  					}
             
  	});
}