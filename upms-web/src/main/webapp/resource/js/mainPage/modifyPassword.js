function modifyPassword(){
	$("#fm").form("jsubmit", {
	    "url": "../mainPage/modifyPassword.json",
	    "method": "post",//默认为post
	    "validate": true,//默认为true
	    "success": function(result){
	    	   var successFlag = result.success;
		        if (successFlag) {
					msg.bottomRight("提示信息", "操作成功");
					$('#modify_password_dlg').dialog('close'); // close the dialog
				} else {
					msg.bottomRight("失败信息", "操作失败或异常"+result.msg);
				}
	    },
	    "error": function(msg){
	    }
	});
}

$.extend($.fn.validatebox.defaults.rules, {  
    /*必须和某个字段相等*/
    equalTo: {
        validator:function(value,param){
            return $(param[0]).val() == value;
        },
        message:'字段不匹配'
    }
           
});