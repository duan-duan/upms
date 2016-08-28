
/**
 * 提交登录请求
 */
function submitLoginInfo(){
	$("#loginFm").form("jsubmit", {
	    "url": "login.json",
	    "method": "post",//默认为post
	    "validate": true,//默认为true
	    "success": function(data){
	    	   var operateResult = data.success;
			   if(operateResult){//登录成功.直接进入系统
				   window.location.href="index.htm";
			   }else{
				   var errorCode = data.ErrorCode;
				   if(errorCode==2004){
					   $.messager.alert("提示", data.ErrorMessage);
				   }else if(errorCode==2005){//重复登录的情况
					   $.messager.alert("提示",data.ErrorMessage);
					   window.location.href="index.htm";
				   }else{
					   $.messager.alert("提示","登录失败!"+data.ErrorMessage);
				   }
			   }
	    },
	    "error": function(msg){
	    	  msg.bottomRight("登录失败!提交登录请求发生异常.");
	    }
	});

}


/**
 * 重置录入框
 */
function resetLoginForm(){
	document.getElementById('loginFm').reset();
}