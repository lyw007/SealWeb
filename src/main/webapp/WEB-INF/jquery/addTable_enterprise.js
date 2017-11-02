	var num_enterprise = 0;
	var listsize = 12; 
    function addTable_enterprise() {
        var tableHtml = ""; 
        tableHtml += '<tr id="tr'+num_enterprise+'">'
	        + '<td><input class="addtd_enterprise" id="cnashu_enterprise1'+num_enterprise+'" onblur="setvalue_enterprise('+num_enterprise+')" type="text" name="name" value=""></td>'
	        + '<td><input class="addtd_enterprise" id="cnashu_enterprise2'+num_enterprise+'" type="text" name="mobile" value=""></td>'
	        + '<td><select class="addtd_enterprise" id="cnashu_enterprise3'+num_enterprise+'" name="enterpriseCodeType">'
	        + '        <option value="1">组织机构代码</option>'
	        + '       <option value="0">社会信用代码号</option>'
	        + '</select>'
	        + '<td><input class="addtd_enterprise" id="cnashu_enterprise4'+num_enterprise+'" type="text" name="enterpriseCode" value=""></td>'
	        + '<td><select class="addtd_enterprise" id="cnashu_enterprise5'+num_enterprise+'" name="enterpriseUserType">'
	        + '        <option value="1">代理人注册</option>'
	        + '        <option value="2">法人注册</option>'
	        + '</select></td>'
	        + '<td><input class="addtd_enterprise" id="cnashu_enterprise6'+num_enterprise+'" type="text" name="representativeAgentName" value=""></td>'
	        + '<td><input class="addtd_enterprise" id="cnashu_enterprise7'+num_enterprise+'" type="text" name="representativeAgentIdNumber" value=""></td>'
	        + '<td><input class="addtd_enterprise" id="cnashu_enterprise8'+num_enterprise+'" type="text" name="uText" value=""></td>'
	        + '<td><select class="addtd_enterprise" id="cnashu_enterprise9'+num_enterprise+'" name="legalArea">'
	        + '        <option value="0">大陆</option>'
	        + '        <option value="1">香港</option>'
	        + '        <option value="2">澳门</option>'
	        + '       <option value="3">台湾</option>'
	        + '        <option value="4">外籍</option>'
	        + '</select>'
	        + '<td><input class="addtd_enterprise" id="cnashu_enterprise10'+num_enterprise+'" type="text" name="email" value=""></td>'
	        + '<td><select class="addtd_enterprise" id="cnashu_enterprise11'+num_enterprise+'" name="enterpriseType">'
	        + '       <option value="0">普通企业</option>'
	        + '       <option value="1">社会团体</option>'
	        + '       <option value="2">事业单位</option>'
	        + '      <option value="3">民办非企业单位</option>'
	        + '       <option value="4">党政及国家机构</option>'
	        + '</select>'
	        + '<td><input class="addtd_enterprise" id="cnashu_enterprise12'+num_enterprise+'" type="text" name="keyword" value=""></td>'
            + '<td><a style="cursor: pointer; color: blue;" onclick="removeTr_enterprise('
            + num_enterprise
            + ')">删除</a>' + '</td>' + '</tr>';

        var elements = $("#myTable_enterprise").children().length; //表示id为“mtTable”的标签下的子标签的个数

        $("#myTable_enterprise").children().eq(elements - 1).after(tableHtml); //在表头之后添加空白行
        num_enterprise++;
    }
    //删除行
    function removeTr_enterprise(trNum) {
        $("#tr" + trNum).remove();
    }
    
    function save_enterprise(){
    	var msg = $("#submitForm_enterprise").serialize();  //获得后的msg的值：canshu1=xxx&canshu2=xxx&canshu3=xxx&canshu4=xxx&canshu5=xxx
    	
    	var json = "[{";
    	var msg2 = msg.split("&");   //先以“&”符号进行分割，得到一个key=value形式的数组
    	var t = false;
    	for(var i = 0; i<msg2.length; i++){
    	  var msg3 = msg2[i].split("=");  //再以“=”进行分割，得到key，value形式的数组
    	  for(var j = 0; j<msg3.length; j++){
    	    json+="\""+decodeURIComponent(msg3[j])+"\"";
    	    if(j+1 != msg3.length){
    	      json+=":";
    	    }
    	    if(t){
    	      json+="}";
    	      if(i+1 != msg2.length){  //表示是否到了当前行的最后一列
    	        json+=",{";
    	      }
    	      t=false;
    	    }
    	    if(msg3[j] == "keyword"){  //这里的“canshu5”是你的表格的最后一列的input标签的name值，表示是否到了当前行的最后一个input
    	      t = true;
    	    }
    	  }
    	  if(!msg2[i].match("keyword")){  //同上
      	    json+=",";
      	  }
    	}
    	json+="]";
    	//最终msg的值就被转换为：[{"key":"value"；"key":"value"},{"key":"value"；"key":"value"}]格式的json数据
    	$("#textareaJson_enterprise").val(json);
    }
    
    function setvalue_enterprise(num_enterprise){
    	$("#cnashu_enterprise12"+num_enterprise).val($("#cnashu_enterprise1"+num_enterprise).val()+"~");
    }