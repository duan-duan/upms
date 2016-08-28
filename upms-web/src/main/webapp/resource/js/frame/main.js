var navFlag = true; //Tab选择
$(function(){
	setInterval(showTime,1000);
	/*
	*检测浏览器窗口大小改变,来改变页面layout大小
	*/
	$(window).resize(function(){
		$('#cc').layout('resize');
	});
	loadTree();
	bindTabRightMenu();
	//tabs事件
	$('#tt').tabs({
		onSelect: function(title, index){
			if(navFlag){
				$("#index_nav").html("&gt;&nbsp;" + title);
			}
			navFlag = true;
		}
	});
});

/*
 * view(url) 在layout中打开页面
 */
function view(url){
	$('#iframe').attr('src',url);
}
/*
 *添加选项卡方法
 */
function addTab(title, url){
	var $tab = $('#tt');
	//先判断是否存在标题为title的选项卡
	var tab = $tab.tabs('exists',title);
	if(tab){
		//若存在，则直接打开
		$tab.tabs('select',title);
	}else{
		//否则创建
		$tab.tabs('add',{
			title:title,
			content:"<iframe width='100%' height='100%'  id='iframe' frameborder='0' scrolling='auto'  src='"+url+"?s="+Math.random()+"'></iframe>",
			closable:true
		});
	}
}
/*
*刷新时间
*/
function showTime(){
	var date=new Date();
	$('#timeInfo').html();
	$('#timeInfo').html(date.toLocaleString()+"&nbsp;&nbsp;");
}
/**
 *获取tab页面
 */
function getAllTabObj(tabs){
	//存放所有tab标题
	var closeTabsTitle = [];
	//所有所有tab对象
	var allTabs = tabs.tabs('tabs');
	$.each(allTabs,function(){
		var tab = this;
		var opt = tab.panel('options');
		//获取标题
		var title = opt.title;
		//是否可关闭 ture:会显示一个关闭按钮，点击该按钮将关闭选项卡
		var closable = opt.closable;
		if(closable){
			closeTabsTitle.push(title);
		}
	});
	return closeTabsTitle;
}

/**
 *Tab关闭方法
 */
function closeTab(tabId,tabMenuId,type){
	//tab组件对象
	var tabs = $('#' + tabId);
	//tab组件右键菜单对象
	var tab_menu = $('#' + tabMenuId);
	//获取当前tab的标题
	var curTabTitle = tab_menu.data('tabTitle');
	
	//关闭当前tab
	if(type === 'tab_menu-tabclose'){
		//通过标题关闭tab
		if(curTabTitle!='欢迎页'){
			tabs.tabs("close",curTabTitle);
		}
		
	}else if(type === 'tab_menu-tabcloseall'){
		//获取所有关闭的tab对象
		var closeTabsTitle = getAllTabObj(tabs);
		//循环删除要关闭的tab
		$.each(closeTabsTitle,function(){
			var title = this;
			tabs.tabs('close',title);
		});
	}else if(type === 'tab_menu-tabcloseother'){
		//获取所有关闭的tab对象
		var closeTabsTitle = getAllTabObj(tabs);
		//循环删除要关闭的tab
		$.each(closeTabsTitle,function(){
			var title = this;
			if(title != curTabTitle){
				tabs.tabs('close',title);
			}
		});
	}
}

/**
 *绑定Tab右键菜单
 */
function bindTabRightMenu(){
	var tabsId = 'tt';//tabs页签Id
	var tab_rightmenuId = 'tab_rightmenu';//tabs右键菜单Id
	
	//绑定tabs的右键菜单
	$("#"+tabsId).tabs({
	    width: $("#"+tabsId).parent().width(),
	    height: "auto",
	    fit:true,
        border:false,
		onContextMenu:function(e,title){//这时去掉 tabsId所在的div的这个属性：class="easyui-tabs"，否则会加载2次
		  e.preventDefault();
		  $('#'+tab_rightmenuId).menu('show',{  
			left: e.pageX,  
			top: e.pageY  
		  }).data("tabTitle",title);
		}
	});
	
	//实例化menu的onClick事件
	$("#"+tab_rightmenuId).menu({
		onClick:function(item){
			closeTab(tabsId,tab_rightmenuId,item.name);
		}
	});	
}
function loadTree(){
	//alert($("#navigateTree").length);
	$("#navigateTree").tree({
		url:"./mainPage/user_tree_json.json?r="+Math.random(),
		lines: false,
		animate: true,
		method:"POST",
		onClick:function(node){
			navFlag = false;
			var str = "";
			str = treeNav($(this), node, str);
			$("#index_nav").html(str);
			if($(this).tree('isLeaf', node.target)){
				if(node.text=="安全退出"){
					window.location.href="logout.htm"; 
				}
				addTab(node.text, "."+node.href);
			}
		},
		onSelect:function(node){
			//collapseAll $(this).tree('toggle',node.target);
			//$(this).tree('collapseAll');  
			$(this).tree('toggle',node.target); 	 
		}
	});
}

function treeNav(_this, node, str){
	str = "&gt;&nbsp;"+node.text + "&nbsp;" + str;
	var parent = _this.tree('getParent', node.target);
	if(parent){
		return treeNav(_this, parent, str);
	} else {
		return str;
	}
}


function showModifyPassword(){
	$('#modify_user_password_dlg').dialog('open');
}

function modifyUserPassword(){
	
	$("#fm_user").form("jsubmit", {
	    "url": "./mainPage/modifyPassword.json",
	    "method": "post",//默认为post
	    "validate": true,//默认为true
	    "success": function(result){
	        var successFlag = result.success;
	        if (successFlag) {
				msg.bottomRight("提示信息", "操作成功");
				$('#modify_user_password_dlg').dialog('close'); // close the dialog
			} else {
				msg.bottomRight("失败信息", "操作失败或异常"+result.msg);
			}
	    },
	    "error": function(msg){
	    	msg.bottomRight("异常信息", "操作异常"+msg);
	    }
	});
}


$.extend($.fn.validatebox.defaults.rules, {  
    
    equalTo: {
        validator:function(value,param){
            return $(param[0]).val() == value;
        },
        message:'字段不匹配'
    }
           
});