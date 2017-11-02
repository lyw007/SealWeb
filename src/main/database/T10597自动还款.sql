--自动还款协议模板sql
UPDATE contract_template set template_content = '<!DOCTYPE html>
<html>
<head>
  <title>委托扣划授权书</title>
  <style>
    @page {
        size: A4 portrait;
        @bottom-center {
      		color:#646464;
     		font-family:Arial Unicode MS;
            content: "第 " counter(page) " 页 共  " counter(pages) " 页 ";
        }
    }

	*{ margin:0; padding:0; font-family:''Microsoft YaHei'',"微软雅黑",Arial Unicode MS; color:#646464;}
    body {background-image: url(''./water.png''); }
	.protocol { width:auto; height:auto; margin:0 auto; margin:10px auto 0; padding:10px; padding-bottom:50px; word-wrap:break-word; word-break:break-all; }
	.protocol .bigTit { width:100%; height:auto; margin:0 auto; text-align:center; padding-top:30px; }
	.protocol .bigTit h2 { font-size:20px; }
	.protocol .bianhao { width:100%; height:auto; margin:0 auto; text-align:center; }
	.protocol .bianhao p { font-size:12px; line-height:30px; }
	.protocol .conter { width:100%; height:auto; margin:50px auto 0; }
	.protocol .conter p { font-size:14px; width:100%; line-height:26px; text-indent:26px; }
	.protocol .conter .nodent { text-indent:0; }
	.protocol .conter .b {font-weight:bold;}
    .protocol .conter i { font-size:14px; font-style:normal; font-weight:normal; }
    .protocol .conter em { font-style: normal; margin-left: 40px; }
    .protocol .conter .bigdent01 { text-indent:170px; }
    .protocol .conter .bigdent02 { text-indent:180px; }
    .protocol .conter .mt10 { margin-top:10px; }
    .protocol .conter .mt20 { margin-top:20px; }
    .protocol .conter .mt50 { margin-top:50px; }
    .protocol .conter .mb20 { margin-bottom:20px;}
    .protocol .conter .mb50 { margin-bottom:50px; }
    .protocol .conter .re { position:relative; }
    .underline { text-decoration:underline; font-weight:normal; }
    .protocol .conter .table {border-collapse:collapse; border-spacing:0; margin:10px 0 10px; font-size:14px; line-height:32px; margin-left:30px; width:650px; }
    .protocol .conter .table td { border:1px solid #999; font-size:14px; padding-left:10px; box-sizing:border-box; text-align:center; }
    .protocol .conter .signet { width:162px; height:171px; position:absolute; top:-50px; left:160px; }
    .dbImgGz { width:162px; height:171px; display:block; position:absolute; bottom:-20px; right:20px; }
	.protocol .line {display: inline-block;padding:0 3px; border-bottom:1px solid #000;text-indent: 0;text-align: center;}
    .protocol h5 { font-size: 16px; color: #000; width: 100%; text-align: center; line-height: 30px; font-weight: normal; margin-top: 20px; }
	.content{ text-indent:26px; margin-top:100px;}
	.left{float:left;}
    .right{	float:right;}
	.b p, .c p{	 padding-top:10px;	}
</style>
</head>
<body>
    <div class="protocol">
    	<div class="bigTit">
    		<h2>委托扣划授权书</h2>
    	</div>
    	<div class="conter">
	    <p class="b mt20">深圳市壹佰金融服务有限公司：</p>
        <p>鉴于本人<span class="line"  style="min-width: 80px">${map.realName}</span>（身份证号：<span class="line" style="min-width: 30px">${map.idNumber}</span>）因融资需求签署了《融资服务协议》和《借款协议》/《借款担保合同》（协议编号：<span class="line"  style="min-width: 80px">${map.protocolNo}</span>）。为便利本人/本公司履行《融资服务协议》和《借款协议》/《借款担保合同》的义务，本人/本公司特授权<i class="b mt20">深圳市壹佰金融服务有限公司</i>从事下列行为：</p>
		<p>一、本人/本公司同意并授权贵司委托银行或第三方支付机构从本授权书中指定的银行账户内按照《融资服务协议》和《借款协议》/《借款担保合同》的约定扣划本人/本公司应付的各项费用。</p>
        <p>二、本人/本公司承诺本授权书中指定的银行账户有足够的款项支付各项费用，否则因本人/本公司账户余额不足导致无法及时扣划或扣划不成功的，因此而产生的责任由本人/本公司承担。</p>
        <p>三、因本人/本公司账户变更、挂失、冻结、销户等情形发生，本人/本公司同意立即通知贵司，并于三个工作日内重新签订《委托扣划授权书》，授权资料以最新的授权委托协议为准。</p>
        <p>四、自本人/本公司在线点击确认本协议后，即视为本人/本公司认可本授权书约定的内容，对贵司依照本协议从事的行为，本人/本公司均予以认可，并自愿承担相应的法律责任。</p>
        <p>五、本授权书自在线点击确认后生效，至本人/本公司通知终止授权，授权银行账号信息更改或委托事项完成之日起终止。</p>
        <h5>授权银行账号信息</h5>
        <table class="table" style="width:90%">
                <tbody>
                <tr>
                    <td style="width:30%; font-weight: bold;">户名</td>
                    <td>${map.realName}</td>
                </tr>
                <tr>
                    <td style="width:30%; font-weight: bold;">身份证号</td>
                    <td style="width:70%;">${map.idNumber}</td>
                </tr>
				<tr>
                    <td style="width:30%; font-weight: bold;">银行卡账号</td>
                    <td style="width:70%;">${map.bankAccount}</td>
                </tr>
				<tr>
                    <td style="width:30%; font-weight: bold;">银行卡开户银行</td>
                    <td style="width:70%;">${map.bankName}</td>
                </tr>
                </tbody>
            </table>
            <p></p>
            <p></p>
            <p></p>
            <div class="content">
                <span class="b mt20 left"><i>授权人（签字）：${map.realName}<span style="color:#FFFFFF">${map.idNumber}~</span></i>
                    <p><i>日期：${map.signTime}</i></p>
                </span>
                <span class="c mt20 right"><i>受托人（盖章）：深圳市壹佰金融服务有限公司<span style="color:#FFFFFF">深圳市壹佰金融服务有限公司~</span></i>
                    <p><i>日期：${map.signTime}</i></p>
                </span>
            </div>
    	</div>
    </div>
</body>
</html>' where id = 22 and template_name = 'auto_repay_protocol.ftl';