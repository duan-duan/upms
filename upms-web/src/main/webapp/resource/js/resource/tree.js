var sp_id,sp_parent_id,comboboxId,sys_id;
$.ajaxSetup ({
   cache: false //关闭AJAX相应的缓存
});
$(function(){
	$('#treeGrid').myTreegrid({
	url:'../resource/treegrid_json.json',
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
	columns:[[
			{field:'text',title:'系统资源',width:40,editor:{
			    "type":"validatebox",
			    "options":{
			        "required":true
			    }
			}},
			{field:'href',title:'菜单地址',width:40,editor:{
			    "type":"validatebox",
			    "options":{
			        "required":true
			    }
			}},
			{field:'rank',title:'菜单顺序',width:20,editor:{
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
            	 var sid = row.id;
        		 var sysIdVal = row.sys_id;
        		 var returnStr = "";
        		 if(permissable("upms:resource:button")){ //资源按钮权限
        			 returnStr +='<a id="setBtn" class="easyui-linkbutton" onclick="showEastLayout('+sid+','+sysIdVal+')" style="color: 000; cursor: pointer;">[按钮设置]</a>';
        		 }
            	 return returnStr;
              }
            }
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
		  //加载easyui-combobox数据
		  $.ajax({
			   url:'../systemModel/listAll.json', //加载所有系统
			   cache:false,
			   type:"get",
			   error: function () {
				      alert('请求失败');
			   },
			   dataType:"json",
			   success:function(data){
				    $('#sys2').combobox({//设置系统名称
				    	data:data,
				    	onChange:function(){//当触发选择事件时
				    		 $(this).combobox('setValue',$(this).combobox('getValue'));
				    		 sys_id = $(this).combobox('getValue');//获取系统ID 
				    		 $("#comboTree").combotree({
				    		    		 url:'../resource/get_system_resource.json?sys_id='+sys_id,
				    		    		 required:true,
				    		    		 method:'get',	 
				    		    		 onSelect:function(node){
				    		 				sp_parent_id=node.id;
				    		     			$.ajax({
				    		     				   url:'../resource/get_child_node.json?pid='+sp_parent_id,
				    		 		    		   cache:false,
				    		 		    		   type:"get",
				    		 		    		   error: function () {//请求失败处理函数
				    		 		    			         alert('请求失败');
				    		 		    			      },
				    		 		    		   dataType:"json",
				    		 		    		   success:function(data){
				    		 		    			  /* if(data.length<1){
				    		 		    				   sp_id = parseInt((node.id+"").concat("01"));
				    		 		    			   }else if(data.length==1){
				    		 		    				   sp_id=data[data.length-1].id+1;
				    		 		    			   }else{
				    		 		    				   var difference = data[data.length-1].id-data[data.length-2].id;
				    		 		    				   sp_id=data[data.length-1].id+difference;
				    		 		    			   }*/
				    		 		    		    	$('#add_form').form('load',{
				    		 		    		    		//sp_id:sp_id,
				    		 		    		    		sp_parent_id:sp_parent_id
				    		 		    		    	});
				    		 		    		   }
				    		     			});//end ajax
				    		 		  }// end onSelect		
				    		    	 });
					    	}// end onchange
					    });
				   }// end success
			  }); // end first ajax
			},
			permission: "upms:resource:add" //添加菜单权限
		},{
		id:'btnEdit',
		text:'修改',
		iconCls:'icon-edit',
		handler:function(){
			    $('#btnEdit').linkbutton('enable');
		        var row  = $('#treeGrid').treegrid('getSelected');
		    	if(row){
		    		if(row.isSystem == "true"){
		    			 $.messager.alert("提示信息", "请选择非系统节点，系统节点不允许编辑");
		    		}else if(row.isSystem == "false"){//非系统节点
		    			 $('#edit').dialog('open');//弹出edit对话框
		    			 $.ajax({
		    	  		   url:'../resource/get_node.json?id='+row.id,
		    	  		   cache:false,
		    	  		   type:"get",
		    	  		   error: function () {//请求失败处理函数
		    	  			      alert('请求失败');
		    	  			    },
		    	  		   dataType:"json",
		    	  		   success:function(data){ //加载节点数据成功
		    	  			       comboboxId = data.sys_id;
		    	  			      //加载easyui-combobox数据
		    	  		    	   $.ajax({
		    	  		    		   url:'../systemModel/listAll.json',//加载系统
		    	  		    		   cache:false,
		    	  		    		   type:"get",
		    	  		    		   error: function () {//请求失败处理函数
		    	  		    			         alert('请求失败');
		    	  		    			    },
		    	  		    		   dataType:"json",
		    	  		    		   success:function(sysdata){//加载远程数据成功，装载easyui-combobox数据
		    	  		    			    $('#sys1').combobox({
		    	  		    			    	data:sysdata,
		    	  		    			    	onChange:function(){
		       		    			    		    $(this).combobox('setValue',$(this).combobox('getValue'));
		       		    			    	    } 
		    	  		    			    });
		    	  		    			   for(var i=0;i<sysdata.length;i++){
		    	  		    			    	if(comboboxId==sysdata[i].id){
		    	  		    			    		$('#sys1').combobox('setValue',sysdata[i].name);
		    	  		    			    	}
		      		    			       }
		      		    			      $('#sys1').combobox('disable');
		    	  		    		   }
		    	  		    	   });
		    		  			    $('#edit_form').form('load',{
		    		  		    	     sp_name:data.sp_name,
		    		  		    	     sp_desc:data.sp_desc,
		    		  		    	     href:data.href,
		    		  		    	     sp_visible:data.sp_visible,
		    		  		    	     target:data.target,
		    		  		    	     permission:data.permission,
		    		  		    	     sp_type:data.sp_type,
		    		  		    	     icon:data.icon,
		    		  		    	     sp_id:data.sp_id,
		    		  		    	     sp_parent_id:data.sp_parent_id,
		    		  		    	     rank:data.rank
		    		  		       });
		    	  		   }
		    	  	   });// end ajax
		    	    } // end else if
		    	}else{
		    		$.messager.alert('温馨提示','请选择需要编辑的节点!', 'info');
		    	}
			},
			permission: "upms:resource:edit" //编辑菜单权限
		}
		,{
			id:'btndel',
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
			        var row  = $('#treeGrid').treegrid('getSelected');
			    	if(row && row.id){
			    		if(row.sys_id == 3){
			    			$.messager.alert('温馨提示','该节点以及子节点不允许删除', 'info');
			    		}else{
			    			deleteResource(row.id);
			    		}
			    	}else{
			    		$.messager.alert('温馨提示','请选择需要编辑的节点!', 'info');
			    	}
				},
				permission: "upms:resource:remove" //编辑菜单权限
			}
			]
	});
	
	$('#nodeLevel').combobox({//节点级别选择事件
		onChange:function(){
			$('#nodeLevel').combobox('setValue',$(this).combobox('getValue'));
			var value = $('#nodeLevel').combobox('getValue');
			if(value==-1){//如果是下级节点，显示父节点资源
				 $('#father').show();
			}else if(value==0){//一级节点
				 $('#father').hide();
				//计算添加节点的id
				$.ajax({
     				   url:'../resource/get_system_child_resource.json?pid=0&sys_id='+sys_id,
 		    		   cache:false,
 		    		   type:"get",
 		    		   error: function () {//请求失败处理函数
 		    			      alert('请求失败');
 		    		   },
 		    		   dataType:"json",
 		    		   success:function(data){
 		    			/*   if(data.length<1){
 		    				   sp_id = 1;
 		    			   }else if(data.length==1){
 		    				   sp_id=data[data.length-1].id+1;
 		    			   }else{
 		    				   var difference = data[data.length-1].id-data[data.length-2].id;
 		    				   sp_id=data[data.length-1].id+difference;
 		    			   }*/
 		    		    	$('#add_form').form('load',{
 		    		    		//sp_id:sp_id,
 		    		    		sp_parent_id:0
 		    		    	});
 		    		   }
     			});//end ajax
			}//end elseif
		}
	});//end combobox//节点级别选择事件
});

function showEastLayout(sid,sysIdVal){
	$('#add_button_form').form('clear');
    $("#eastContent").layout('expand','east');
    $('#resourceDataGrid').datagrid({
    			title:'操作管理',
    			url : '../resource/buttonListForMenu.json?pid='+sid,
    			collapsible : true,//是否可折叠的
    			rownumbers : true,//行号
    			multiple : true,
    			multiSort : true,//排序
    			singleSelect : true,//是否单选
    			border:false,
    			onLoadSuccess:function(){
    				$('#pId').val(sid);
    				$('#sp_type').val("button");
    			},
    			toolbar : [{
    				id : 'btnadd',
    				text : '添加',
    				iconCls : 'icon-add',
    				handler : function() {
    				   	 $('#btnadd').linkbutton('enable');
    				   	 $('#od').dialog('open');
    					 $.ajax({
    							   url:'../resource/get_child_button_node.json?pid='+sid,
    				    		   cache:false,
    				    		   type:"get",
    				    		   error: function () {//请求失败处理函数
    				    			         alert('请求失败');
    				    			      },
    				    		   dataType:"json",
    				    		   success:function(data){
    				    			   
    				    			  /* if(data.length<1){
    				    				   sp_id = parseInt((sid+"").concat("01"));
    				    			   }else if(data.length==1){
    				    				   sp_id=data[data.length-1].id+1;
    				    			   }else{
    				    				   var difference = data[data.length-1].id-data[data.length-2].id;
    				    				   sp_id=data[data.length-1].id+difference;
    				    			   }*/
    				    			   
    				    		    	$('#add_button_form').form('load',{
    				    		    		//sp_id:sp_id,
    				    		    		sp_parent_id:sid,
    				    		    		sys_id:sysIdVal
    				    		    	});
    				    		   }
    					});//end ajax
    				}// end handler
    			},
    			{
    				id : 'btndel',
    				text : '删除',
    				iconCls : 'icon-remove',
    				handler : function() {
    				   	var row = $('#resourceDataGrid').datagrid('getSelected');
    					if (row) {
    						$.messager.confirm('Confirm','您确定要删除此按钮?', function(r) {
    							
    							if (r) {
    								 $.post('../resource/delete_node.do?id='+row.sp_id,
    				   							function(result) {
    									           var flag = result.flag;
    				   								if (flag) {
    				   									$('#resourceDataGrid').datagrid('reload');
    				   									msg.bottomRight("提示信息", "操作成功");
    				   								} else {
    				   									msg.bottomRight("提示信息", "删除按钮操作失败");
    				   								}
    				   				  },'json');
    							}
    						});
    					} else {
    						$.messager.alert('温馨提示','请选择需要删除的按钮!', 'info');
    					}
    				}// end handler
    			}
    			]
    		}); //end datagrid
}

 //删除节点,如果该节点有子节点级联删除
 function deleteResource(sid) { 
     $.messager.confirm('Confirm','您确定要删除该节点?',
   				  function(r) {
   					if (r) {
	   					 $.post('../resource/delete_node.do?id='+sid,
	   							function(result) {
	   								if (result) {
	   									msg.bottomRight("提示信息", "操作成功");
	   									$('#treeGrid').treegrid('remove',sid);
	   									$('#treeGrid').treegrid('reload');
	   									window.parent.loadTree();
	   								} else {
	   									msg.bottomRight("提示信息", "操作失败");
	   								}
	   				     },'json');
   					}
              
   	});
}

function submitAddForm(){
	 $('#add_form').form('submit', {
			url :'../resource/add_node.do',
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				if (result) {
					$('#add').dialog('close');
					$("#treeGrid").treegrid('reload');
					window.parent.loadTree();
					msg.bottomRight("提示信息", "操作成功");
				} else {
					msg.bottomRight("提示信息", "操作失败");
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
		sys_id:comboboxId
	});
	
	$('#edit_form').form('submit', {
			url :'../resource/update_node.do',
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				if (result) {
					$('#edit').dialog('close');
					$("#treeGrid").treegrid('reload');
					window.parent.loadTree();
					msg.bottomRight("提示信息", "操作成功");
				} else {
					msg.bottomRight("提示信息", "操作失败");
					$.messager.show({
						title : 'Error',
						msg : result.errorMsg
					});
				}
			}
		});
}

function submitAddButtonForm(){
	 $('#add_button_form').form('submit', {
			url :'../resource/add_node.do',
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				if (result) {
					$('#od').dialog('close');
					$("#resourceDataGrid").datagrid({
						url : '../resource/buttonListForMenu.json?pid='+$("#add_button_form").find("#pId").val()
					});
					$('#add_button_form').form('clear');
					msg.bottomRight("提示信息", "操作成功");
				} else {
					msg.bottomRight("提示信息", "操作失败");
					$.messager.show({
						title : 'Error',
						msg : result.errorMsg
					});
				}
			}
	 });
}