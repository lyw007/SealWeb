	var num_apiperson = 0;
	var listsize = 4; 
    function addTable_apiPerson() {
        var tableHtml = "";
        tableHtml += '<tr id="tr'+num_apiperson+'">'
         
            + '<td><input type="text" class="addtd_apiperson" id="cnashu_apiperson1'+num_apiperson+'" style="width:150px;" name="name" value=""></td>'
            + '<td><input type="text" class="addtd_apiperson" id="cnashu_apiperson2'+num_apiperson+'" style="width:150px;" onblur="setvalue_apiperson('+num_apiperson+')" name="idNumber" value=""></td>'
            + '<td><input type="text" class="addtd_apiperson" id="cnashu_apiperson3'+num_apiperson+'" style="width:150px;" name="mobile" value=""></td>'
            + '<td><input type="text" class="addtd_apiperson" id="cnashu_apiperson4'+num_apiperson+'" style="width:150px;" name="keyword" value=""></td>'
            + '<td><a style="cursor: pointer; color: blue;" onclick="removeTr_apiperson('
            + num_apiperson
            + ')">删除</a>' + '</td>' + '</tr>';

        var elements = $("#myTable_apiperson").children().length; //表示id为“mtTable”的标签下的子标签的个数

        $("#myTable_apiperson").children().eq(elements - 1).after(tableHtml); //在表头之后添加空白行
        num_apiperson++;
    }
    //删除行
    function removeTr_apiperson(trNum) {
        $("#tr" + trNum).remove();
    }
    
    function save_apiperson(){
    	var msg = $("#submitForm_apiperson").serialize();  //获得后的msg的值：canshu1=xxx&canshu2=xxx&canshu3=xxx&canshu4=xxx&canshu5=xxx
    	
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
    	$("#textareaJson_apiperson").val(json);
    }
    
    function setvalue_apiperson(num_apiperson){
    	$("#cnashu_apiperson4"+num_apiperson).val($("#cnashu_apiperson2"+num_apiperson).val()+"~");
    }
    