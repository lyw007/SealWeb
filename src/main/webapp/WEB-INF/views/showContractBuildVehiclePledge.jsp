<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<title>车贷业务-车辆质押</title>
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
        url : '/contractBuildVehicleMortgage/getProtocolNo',
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
    ProtocolNoService.prototype.setProtocolNo("loanAgreementProtocolNo");
    ProtocolNoService.prototype.setProtocolNo("loanPledgeNoGuaranteeProtocolNo");
    ProtocolNoService.prototype.setProtocolNo("confirmationReceiptProtocolNo");
    ProtocolNoService.prototype.setProtocolNo("powerOfattorneyProtocolNo");
    ProtocolNoService.prototype.setProtocolNo("carloanCarstransferProtocolNo");
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
    ProtocolNoService.prototype.setBatchProtocolNO();
    document.forms[0].action = actionUrl; 
    document.forms[0].submit();
}
PageService.prototype.downOneContractPdf = function(actionUrl, vehiclePledgeContractType){
    ProtocolNoService.prototype.setBatchProtocolNO();
    if(actionUrl == '' || vehiclePledgeContractType == ''){
        alert("请求有误!");
    }
    document.forms[0].vehiclePledgeContractType.value = vehiclePledgeContractType;
    if(vehiclePledgeContractType == 'CARLOAN_FINANCING_SERVICES_3PARTY'){
        document.forms[0].protocolNo.value = document.forms[0].financingservices3partyProtocolNo.value;
    }else if(vehiclePledgeContractType == 'GUARANTEED_LOAN_AGREEMENT'){
        document.forms[0].protocolNo.value = document.forms[0].loanAgreementProtocolNo.value;
    }else if(vehiclePledgeContractType == 'PLEDGE_NO_GUARANTEE'){
        document.forms[0].protocolNo.value = document.forms[0].loanPledgeNoGuaranteeProtocolNo.value;
    }else if(vehiclePledgeContractType == 'POWER_OFATTORNEY'){
        document.forms[0].protocolNo.value = document.forms[0].powerOfattorneyProtocolNo.value;
    }else if(vehiclePledgeContractType == 'CONFIRMATION_RECEIPT'){
        document.forms[0].protocolNo.value = document.forms[0].confirmationReceiptProtocolNo.value;
    }else if(vehiclePledgeContractType == 'CAR_STRANSFER'){
        document.forms[0].protocolNo.value = document.forms[0].carloanCarstransferProtocolNo.value;
    }else if(vehiclePledgeContractType == 'EXTENDING_LOAN_AGREEMENT'){
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
<form:form action="downBatchContractVehiclePledge" id="downBatchContractVehiclePledge" method="post" commandName="apiContractVehiclePledge"> 
    <input type="reset" name="reset" style="display: none;" />
    <!-- 隐藏参数通过(通用) -->
    <input type="hidden" name="vehiclePledgeContractType" id="vehiclePledgeContractType"/>
    <input type="hidden" name="protocolNo" id="protocolNo" />
    
    <input type="hidden" name="financingservices3partyProtocolNo" id="financingservices3partyProtocolNo" />
    <input type="hidden" name="loanAgreementProtocolNo" id="loanAgreementProtocolNo" />
    <input type="hidden" name="loanPledgeNoGuaranteeProtocolNo" id="loanPledgeNoGuaranteeProtocolNo" />
    <input type="hidden" name="clientStatementProtocolNo" id="clientStatementProtocolNo" />
    <input type="hidden" name="confirmationReceiptProtocolNo" id="confirmationReceiptProtocolNo" />
    <input type="hidden" name="powerOfattorneyProtocolNo" id="powerOfattorneyProtocolNo" />
    <input type="hidden" name="carloanCarstransferProtocolNo" id="carloanCarstransferProtocolNo" />
    <input type="hidden" name="extendingLoanAgreementProtocolNo" id="extendingLoanAgreementProtocolNo" />
    
    <div class="contmanage">
        <div class="contmanageTop">
            <h2>合同管理</h2>
        </div>
        <div class="tabCont-title">
            <div class="comWin">
                <div class="tabIcon">
                    <span onclick="PageService.prototype.pageJump('<%=request.getContextPath() %>/contractBuildVehicleMortgage/showContractBuildVehicleMortgage')">车贷业务-车辆<i>抵</i>押</span>
                    <span class="active">车贷业务-车辆<i>质</i>押</span>
                    <span onclick="PageService.prototype.pageJump('<%=request.getContextPath() %>/contractBuildPayLoan/showContractBuildPayLoan')" style="border:none;" >小佰薪贷业务</span>
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
                        <div class="nameInfo"><h2>借款人名称：</h2><p>（委托人/质押人/出让方/委托人）</p></div>
                        <div class="textInfo"><input name="auditeeName" id="auditeeName" onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5]/g,'')" maxlength="18" class="text" /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>借款人身份证号/注册号：</h2><p>（委托人/质押人/出让方/委托人）</p></div>
                        <div class="textInfo"><input name="auditeeIdNumber" id="auditeeIdNumber"  maxlength="18" class="text" /></div>
                    </div>
                    
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>担保人名称：</h2><p>（抵押权人/受让方/受托人）</p></div>
                        <div class="textInfo"><input name="guaranteeName" id="guaranteeName" onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5]/g,'')" maxlength="18" class="text" /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>担保方身份证号码：</h2><p>（抵押权人/受让方/受托人）</p></div>
                        <div class="textInfo"><input name="guaranteeIdNumber" id="guaranteeIdNumber"  maxlength="18" class="text" /></div>
                    </div>
                    
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>借款金额：</h2><p>（融资金额/借款金额/出借款）</p></div>
                        <div class="textInfo"><input name="auditeeAmount" id="auditeeAmount" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" onblur="amountToCapital(this,'auditeeAmountStr');PageService.prototype.calculationCommission(this,'feeAmount')" class="text"  />元</div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>借款期限：</h2><p>（服务期限）</p></div>
                        <div class="textInfo"><input name="periodStr" id="periodStr"  class="text" /></div>
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
                        <div class="nameInfo"><h2>借款年化利率：</h2></div>
                        <div class="textInfo"><input name="apr" id="apr"  class="text" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')"/>%</div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>借款用途：</h2></div>
                        <div class="textInfo"><input name="contractUse" id="contractUse" class="text"/></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo">
                            <h2>还款方式：</h2>
                        </div>
                        <div class="textInfo">
                            <div class="select select1">
                                <div class="selectInput"><input type="hidden" name="repaymentType" value="1"><span>一次性还款</span><i></i></div>
                                <div class="selectList" style="display: none;">
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
                        <div class="nameInfo"><h2>车牌号码：</h2></div>
                        <div class="textInfo"><input name="carNumber" id="carNumber"  maxlength="25"  class="text" /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>发动机号：</h2></div>
                        <div class="textInfo"><input name="engineNumber" id="engineNumber"   maxlength="25" class="text"  /></div>
                    </div>
                    
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>车架号：</h2></div>
                        <div class="textInfo"><input name="vehicleIdentificationNumber" id="vehicleIdentificationNumber"  maxlength="25"  class="text" /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>品牌：</h2></div>
                        <div class="textInfo"><input name="carBrand" id="carBrand"   maxlength="100"  class="text"  /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>车型号：</h2></div>
                        <div class="textInfo"><input name="carModel" id="carModel"  maxlength="25"  class="text" /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>抵押车辆购置日期：</h2></div>
                        <div class="textInfo"><input name="carBuyDate" id="carBuyDate" readonly="true" onclick="WdatePicker()" class="text Wdate" /></div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>抵押车辆评估值：</h2></div>
                        <div class="textInfo"><input name="vehicleEvaluationValue" id="vehicleEvaluationValue"  onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" class="text"  />元</div>
                    </div>
                    <div class="tdDiv">
                        <div class="nameInfo"><h2>抵押价值总额：</h2></div>
                        <div class="textInfo"><input name="totalValue" id="totalValue" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" class="text"  />元</div>
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
                    <div class="tdDiv tdDiv02"><p><a onclick="PageService.prototype.downOneContractPdf('downOneContract', 'CARLOAN_FINANCING_SERVICES_3PARTY')" style="cursor:pointer">融资服务协议（三方版）</a></p></div>
                    <div class="tdDiv tdDiv03">
                        <div class="select select1" style="z-index:123">
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
                    <div class="tdDiv tdDiv02"><p><a onclick="PageService.prototype.downOneContractPdf('downOneContract', 'GUARANTEED_LOAN_AGREEMENT')" style="cursor:pointer">保证借款协议(办理抵押登记)</a></p></div>
                    <div class="tdDiv tdDiv03">
                        <div class="select select2" style="z-index:122">
                            <select name="loanAgreementOption" >
                                 <c:forEach var="item" items="${contractBuildOption}">
                                    <option value="${item.key}">${item.value}</option> 
                                </c:forEach>
                            </select>
                        </div>
                        <span>一式<var>4</var>份</span>
                    </div>
                </div>
                <div class="rowDiv">
                    <div class="tdDiv tdDiv01"><p>3</p></div>
                    <div class="tdDiv tdDiv02"><p><a onclick="PageService.prototype.downOneContractPdf('downOneContract', 'PLEDGE_NO_GUARANTEE')" style="cursor:pointer">质押反担保(办理质押登记)</a></p></div>
                    <div class="tdDiv tdDiv03">
                        <div class="select select3" style="z-index:121">
                            <select name="loanPledgeNoGuaranteeOption" >
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
                    <div class="tdDiv tdDiv02"><p><a onclick="PageService.prototype.downOneContractPdf('downOneContract', 'BASEIC_CLIENT_STATEMENT')" style="cursor:pointer">客户申明</a></p></div>
                    <div class="tdDiv tdDiv03">
                        <div class="select select4" style="z-index:120">
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
                    <div class="tdDiv tdDiv01"><p>5</p></div>
                    <div class="tdDiv tdDiv02"><p><a onclick="PageService.prototype.downOneContractPdf('downOneContract', 'POWER_OFATTORNEY')" style="cursor:pointer">授权委托书</a></p></div>
                    <div class="tdDiv tdDiv03">
                        <div class="select select5" style="z-index:119">
                            <select name="powerOfattorneyOption" >
                                 <c:forEach var="item" items="${contractBuildOption}">
                                    <option value="${item.key}">${item.value}</option> 
                                </c:forEach>
                            </select>
                        </div>
                        <span>一式<var>2</var>份</span>
                    </div>
                </div>
                <div class="rowDiv">
                    <div class="tdDiv tdDiv01"><p>6</p></div>
                    <div class="tdDiv tdDiv02"><p><a onclick="PageService.prototype.downOneContractPdf('downOneContract', 'CONFIRMATION_RECEIPT')" style="cursor:pointer">收款确认函</a></p></div>
                    <div class="tdDiv tdDiv03">
                        <div class="select select6" style="z-index:118">
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
                    <div class="tdDiv tdDiv01"><p>7</p></div>
                    <div class="tdDiv tdDiv02"><p><a onclick="PageService.prototype.downOneContractPdf('downOneContract', 'CAR_STRANSFER')" style="cursor:pointer">车辆转让协议</a></p></div>
                    <div class="tdDiv tdDiv03">
                        <div class="select select7" style="z-index:117">
                            <select name="carloanCarstransferOption" >
                                 <c:forEach var="item" items="${contractBuildOption}">
                                    <option value="${item.key}">${item.value}</option> 
                                </c:forEach>
                            </select>
                        </div>
                        <span>一式<var>3</var>份</span>
                    </div>
                </div>
                <div class="rowDiv">
                    <div class="tdDiv tdDiv01"><p>8</p></div>
                    <div class="tdDiv tdDiv02"><p><a onclick="PageService.prototype.downOneContractPdf('downOneContract', 'EXTENDING_LOAN_AGREEMENT')" style="cursor:pointer">借款展期协议(第一次可不签)</a></p></div>
                    <div class="tdDiv tdDiv03">
                        <div class="select select8" style="z-index:116">
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
                <a onclick="PageService.prototype.downBatchContractPdf('downBatchContractVehiclePledge');"   style="cursor:pointer"  title="">一键下载</a>
            </div>
        </div>   
    </div>
</form:form>
</body>
</html>