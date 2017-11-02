<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<title>小佰薪贷-合同自动生成</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<meta http-equiv="Pragma" content="no-cache"></meta>
<meta http-equiv="Cache-Control" content="no-cache"></meta>
<meta http-equiv="Expires" content="0"></meta>
<link href="/css/tab.css" rel="stylesheet">
<link href="/jquery/alert/jquery.alerts.css" rel="stylesheet">
<script type="text/javascript" src="/jquery/jquery-1.7.js"></script>
<script type="text/javascript" src="/jquery/alert/jquery.alerts.js"></script>
<script type="text/javascript" src="/jquery/alert/jquery-ui-1.10.3.min.js"></script>
<script type="text/javascript" src="/jquery/SelectSimu.js"></script>
<script type="text/javascript" src="/jquery/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/jquery/amountToCapital.js"></script>
<script type="text/javascript">
//协议编号生成逻辑
function ProtocolNoService(){}
ProtocolNoService.prototype.isInitProtocolNo = false;
ProtocolNoService.prototype.setProtocolNo = function (id){
    $.ajax({
        type : 'get',
        url : '/contractBuildPayLoan/getProtocolNo',
        async:false,
        success : function(data) {
            document.getElementById(id).value = data;
        },
        error : function() {
            alert("请求失败!");
        }
    });
}
ProtocolNoService.prototype.setBatchProtocolNO = function(){
	if(ProtocolNoService.prototype.isInitProtocolNo){
        return;
    }
	ProtocolNoService.prototype.isInitProtocolNo = true;
	ProtocolNoService.prototype.setProtocolNo("financingservices3partyProtocolNo");
	ProtocolNoService.prototype.setProtocolNo("paidLoanAgreementProtocolNo");
	ProtocolNoService.prototype.setProtocolNo("confirmationReceiptProtocolNo");
	ProtocolNoService.prototype.setProtocolNo("extendingLoanAgreementProtocolNo");
}
//页面相关服务
function PageService(){}
PageService.prototype.showDiv = function(){
	$(".contmanageBg").show();
    $(".contmanagePop").show();
}
PageService.prototype.hideDiv = function(){
    $(".contmanageBg").hide();
    $(".contmanagePop").hide();
}
PageService.prototype.pageJump = function(url){
	window.location.href=url;
}
PageService.prototype.downBatchContractPdf = function(actionUrl){
	document.forms[0].action = actionUrl;
	ProtocolNoService.prototype.setBatchProtocolNO();
    document.forms[0].submit();
}
PageService.prototype.downOneContractPdf = function(actionUrl, payLoanContractType){
	ProtocolNoService.prototype.setBatchProtocolNO();
    if(actionUrl == '' || payLoanContractType == ''){
        alert("请求有误!");
    }
    document.forms[0].payLoanContractType.value = payLoanContractType;
    if(payLoanContractType == 'PAYLOAN_FINANCING_SERVICES_3PARTY'){
        document.forms[0].protocolNo.value = document.forms[0].financingservices3partyProtocolNo.value;
    }else if(payLoanContractType == 'SMALLBAIPAID_LOAN_AGREEMENT'){
        document.forms[0].protocolNo.value = document.forms[0].paidLoanAgreementProtocolNo.value;
    }else if(payLoanContractType == 'CONFIRMATION_RECEIPT'){
        document.forms[0].protocolNo.value = document.forms[0].confirmationReceiptProtocolNo.value;
    }else if(payLoanContractType == 'EXTENDING_LOAN_AGREEMENT'){
        document.forms[0].protocolNo.value = document.forms[0].extendingLoanAgreementProtocolNo.value;
    }
    document.forms[0].action = actionUrl;   
    document.forms[0].submit();
}
PageService.prototype.calculationCommission = function (fromObject, toOjbectId){
	var feeAmount = document.getElementById(toOjbectId);
	feeAmount.value = fromObject.value * 0.02;
	amountToCapital(feeAmount,'feeAmountStr');
}
PageService.prototype.setRepaymentType = function (repaymentTypeValue){
	document.forms[0].repaymentType.value = repaymentTypeValue;
}

//初始化
$(function(){ 
    $(".contmanage .tabCont-cont").eq(0).show();
    // 下拉循环
    var selectNum = $(".select").size();
    for(var i =0; i<selectNum; i++){
        $('.select'+(i+1)+'').SelectSimu({});
    }
    $(".contmanagePop .title a").on("click", function(){
        jConfirm('是：将清空之前所填写的数据。否：保留以前的数据，在原有基础上修改。', '是否重新生成信息的合同', function(r) {
            if(r){
            	ProtocolNoService.prototype.isInitProtocolNo = false; //初始化状态
                $("input[type=reset]").trigger("click");
            }
        });
        PageService.prototype.hideDiv();
    });
});

</script>
</head>
<body>
<form:form action="downBatchContractSmartPayLoan" id="downBatchContractSmartPayLoan" method="post" commandName="apiContractPayLoan"> 
    <input type="reset" name="reset" style="display: none;" />
    <!-- 隐藏参数通过(通用) -->
    <input type="hidden" name="payLoanContractType" id="payLoanContractType"/>
    <input type="hidden" name="protocolNo" id="protocolNo" />
    
    <input type="hidden" name="financingservices3partyProtocolNo" id="financingservices3partyProtocolNo" />
    <input type="hidden" name="paidLoanAgreementProtocolNo" id="paidLoanAgreementProtocolNo" />
    <input type="hidden" name="clientStatementProtocolNo" id="clientStatementProtocolNo" />
    <input type="hidden" name="confirmationReceiptProtocolNo" id="confirmationReceiptProtocolNo" />
    <input type="hidden" name="extendingLoanAgreementProtocolNo" id="extendingLoanAgreementProtocolNo" />

    <div class="contmanage">
        <div class="contmanageTop">
            <h2>合同管理</h2>
        </div>
        <div class="tabCont-title">
            <div class="comWin">
                <div class="tabIcon">
                    <span style="border:none;" onclick="PageService.prototype.pageJump('<%=request.getContextPath() %>/contractBuildVehicleMortgage/showContractBuildVehicleMortgage')">车贷业务-车辆<i>抵</i>押</span>
                    <span onclick="PageService.prototype.pageJump('<%=request.getContextPath() %>/contractBuildVehiclePledge/showContractBuildVehiclePledge')">车贷业务-车辆<i>质</i>押</span>
                    <span class="active" >小佰薪贷业务</span>
                </div>
                <p>请完善合同中使用到得以下信息，即可下载完整合同：</p>
            </div>
        </div>
        <div class="tabCont-cont">
            <div class="comWin">
                <div class="rowDiv">
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>出借人姓名：</h2><p>（受托方）</p></div>
                        <div class="textInfo"><input name="fiduciaryName" id="fiduciaryName" onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5]/g,'')" maxlength="18" class="text" /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>出借人身份证号：</h2><p>（受托方）</p></div>
                        <div class="textInfo"><input name="fiduciaryIdNumber" id="fiduciaryIdNumber" maxlength="18"  class="text"  /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>借款人名称：</h2><p>（委托方）</p></div>
                        <div class="textInfo"><input name="auditeeName" id="auditeeName" onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5]/g,'')" maxlength="18" class="text" /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>借款人身份证号/营业执照号：</h2><p>（委托方）</p></div>
                        <div class="textInfo"><input name="auditeeIdNumber" id="auditeeIdNumber"  maxlength="18" class="text" /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>借款金额(小写)：</h2><p>（服务期限）</p></div>
                        <div class="textInfo"><input name="auditeeAmount" id="auditeeAmount" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" onblur="amountToCapital(this,'auditeeAmountStr');PageService.prototype.calculationCommission(this,'feeAmount')" class="text"  />元</div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>借款期限：</h2><p>（服务期限）</p></div>
                        <div class="textInfo"><input name="periodStr" id="periodStr"  class="text" /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo">
                            <h2>还款方式：</h2>
                        </div>
                        <div class="textInfo">
                            <div class="select select1">
                                <div class="selectInput"><input type="hidden" name="repaymentType" value="1"><span>一次性还款</span><i></i></div>
                                <div class="selectList" style="display: none;z-index:999">
                                    <ul>
                                        <li value="1" onclick="PageService.prototype.setRepaymentType(1);">等额本息</li>
                                        <li value="2" onclick="PageService.prototype.setRepaymentType(2);">先息后本</li>
                                        <li value="3" onclick="PageService.prototype.setRepaymentType(3);">一次性还款</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>借款用途：</h2></div>
                        <div class="textInfo"><input name="contractUse" id="contractUse" class="text"/></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>借款起息日：</h2></div>
                        <div class="textInfo"><input name="valueDate" id="valueDate" readonly="true" onclick="WdatePicker()" class="text Wdate" /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>借款还款日：</h2></div>
                        <div class="textInfo"><input name="repaymentDate" id="repaymentDate"  readonly="true" onclick="WdatePicker()" class="text Wdate"  /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>借款年化利率</h2></div>
                        <div class="textInfo"><input name="apr" id="apr"  class="text" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')"/>%</div>
                    </div>
                    <div class="tdDiv" style="display:none">
                        <div class="nameInfo"><h2>借款金额(大写)：</h2></div>
                        <div class="textInfo"><input name="auditeeAmountStr" id="auditeeAmountStr" readonly="true" class="text"  />元</div>
                    </div>
                    <div class="tdDiv" style="display:none">
                        <div class="nameInfo"><h2>借款手续费(小写)：</h2></div>
                        <div class="textInfo"><input name="feeAmount" id="feeAmount" readonly="true" class="text"  />元</div>
                    </div>
                    <div class="tdDiv" style="display:none">
                        <div class="nameInfo"><h2>借款手续费(大写)：</h2></div>
                        <div class="textInfo"><input name="feeAmountStr" id="feeAmountStr" readonly="true" class="text"  />元</div>
                    </div>
                </div> 
                <div class="btnDiv">
                    <a onclick="javascript:PageService.prototype.showDiv()" title="预览及下载"  style="cursor:pointer">预览及下载</a>
                </div>
            </div>
        </div>
        <div class="foot">
            <p>深圳市壹佰金融服务有限公司CopyRight © 2017 </p>
        </div>
    </div>
    <div class="contmanageBg" style="display:none"></div>
    <div class="contmanagePop" style="display:none">
        <div class="title"><h2>合同预览及下载</h2><a href="javascript:;" title="关闭弹窗"><img src="/images/close.png" alt=""></a></div>
        <div class="connt">
            <div class="wordInfo">
                <h3>小佰薪贷业务所有合同</h3>
                <p>合同中已填您输入的信息，可点击合同名称预览，也可根据页面设置一键下载所以合同</p>
            </div>
            <div class="table">
                <div class="rowDiv heardDiv">
                    <div class="tdDiv tdDiv01"><p>序号</p></div>
                    <div class="tdDiv tdDiv02"><p>合同名称</p></div>
                    <div class="tdDiv tdDiv03"><p>打印设置</p></div>
                </div>
                <div class="rowDiv">
                    <div class="tdDiv tdDiv01"><p>1</p></div>
                    <div class="tdDiv tdDiv02"><p><a onclick="PageService.prototype.downOneContractPdf('downOneContractPayLoan', 'PAYLOAN_FINANCING_SERVICES_3PARTY')" style="cursor:pointer">融资服务协议（三方版）</a></p></div>
                    <div class="tdDiv tdDiv03">
                        <div class="select select1" style="z-index:120">
                            <select name="financingservices3partyOption" >
				                <c:forEach var="item" items="${contractBuildOption}">
				                    <option value="${item.key}">${item.value}</option> 
				                </c:forEach>
				            </select>
                        </div>
                        <span>一式<var>3</var>份</span>
                    </div>
                </div>
                <div class="rowDiv">
                    <div class="tdDiv tdDiv01"><p>2</p></div>
                    <div class="tdDiv tdDiv02"><p><a onclick="PageService.prototype.downOneContractPdf('downOneContractPayLoan', 'SMALLBAIPAID_LOAN_AGREEMENT')" style="cursor:pointer">借款协议</a></p></div>
                    <div class="tdDiv tdDiv03">
                        <div class="select select2" style="z-index:119">
                            <select name="paidLoanAgreementOption" >
				                 <c:forEach var="item" items="${contractBuildOption}">
				                    <option value="${item.key}">${item.value}</option> 
				                </c:forEach>
				            </select>
                        </div>
                        <span>一式<var>3</var>份</span>
                    </div>
                </div>
                <div class="rowDiv">
                    <div class="tdDiv tdDiv01"><p>3</p></div>
                    <div class="tdDiv tdDiv02"><p><a onclick="PageService.prototype.downOneContractPdf('downOneContractPayLoan', 'BASEIC_CLIENT_STATEMENT')" style="cursor:pointer">客户申明</a></p></div>
                    <div class="tdDiv tdDiv03">
                        <div class="select select3" style="z-index:118">
                            <select name="clientStatementOption" >
				                 <c:forEach var="item" items="${contractBuildOption}">
				                    <option value="${item.key}">${item.value}</option> 
				                </c:forEach>
				            </select>
                        </div>
                        <span>一式<var>2</var>份</span>
                    </div>
                </div>
                <div class="rowDiv">
                    <div class="tdDiv tdDiv01"><p>4</p></div>
                    <div class="tdDiv tdDiv02"><p><a onclick="PageService.prototype.downOneContractPdf('downOneContractPayLoan', 'CONFIRMATION_RECEIPT')" style="cursor:pointer">收款确认函</a></p></div>
                    <div class="tdDiv tdDiv03">
                        <div class="select select4" style="z-index:117">
                            <select name="confirmationReceiptOption" >
				                 <c:forEach var="item" items="${contractBuildOption}">
				                    <option value="${item.key}">${item.value}</option> 
				                </c:forEach>
				            </select>
                        </div>
                        <span>一式<var>2</var>份</span>
                    </div>
                </div>
                <div class="rowDiv">
                    <div class="tdDiv tdDiv01"><p>5</p></div>
                    <div class="tdDiv tdDiv02"><p><a onclick="PageService.prototype.downOneContractPdf('downOneContractPayLoan', 'EXTENDING_LOAN_AGREEMENT')" style="cursor:pointer">借款展期协议(第一次可不签)</a></p></div>
                    <div class="tdDiv tdDiv03">
                        <div class="select select5" style="z-index:116">
                             <select  name="extendingLoanAgreementOption" >
				                <c:forEach var="item" items="${contractBuildOption}">
				                    <option value="${item.key}" <c:if test='${item.key == "NO_GENERATION"}'>selected="selected"</c:if> >${item.value}</option> 
				                </c:forEach>
				            </select>
                        </div>
                        <span>一式<var>3</var>份</span>
                    </div>
                </div>
            </div>
            <div class="btn">
                <a onclick="PageService.prototype.downBatchContractPdf('downBatchContractSmartPayLoan');"   style="cursor:pointer"  title="">一键下载</a>
            </div>
        </div>   
    </div>
</form:form>
</body>
</html>