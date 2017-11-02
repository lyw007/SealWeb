package com.pp100.seal.utils.createtestdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pp100.model.domain.JsonMapper;
import com.pp100.seal.api.ApiEnterprise;
import com.pp100.seal.api.ApiGenerateContractParameter;
import com.pp100.seal.api.ApiPerson;
import com.pp100.seal.utils.JsonUtil;

/**
 * 需要从网上找一下，真是的身份证号及组织机构代码
 * 
 * @author Administrator
 *
 */
public class CreateOnlineData {
    //借款协议（无担保)
    public static void create(){
        Map<String, Object> contractContents = new LinkedHashMap();
     // contractContents.put("transfer_no", "协议编号");
        // contractContents.put("out_user_reality_name", "债权出让人");
        // contractContents.put("out_user_reality_idNumber", "债权出让人身份证号");
        // contractContents.put("currentUserName", "受让人");
        // contractContents.put("currentUserIdNumber", "受让人身份证号");
        // contractContents.put("begin_time", "开始时间");
        // contractContents.put("bid_title", "借款项目名称");
        // contractContents.put("bid_no", "借款协议编号");
        // contractContents.put("bid_apr", "借款项目年化利率");
        // contractContents.put("bid_amount", "借款金额");
        // contractContents.put("bid_period", "借款项目期限");
        // contractContents.put("bid_period_unit", "借款项目期限单位");
        // contractContents.put("bid_repayment_time", "借款项目到期日");
        // contractContents.put("bid_repayment_type", "还款方式");
        // contractContents.put("bid_guarantor", "保障措施s");
        // contractContents.put("invest_amount", "出借金额");
        // contractContents.put("invest_over_corpus", "已偿还本金");
        // contractContents.put("invest_surplus_corpus", "剩余本金");
        // contractContents.put("bid_surplus_period", "剩余期限");
        // contractContents.put("transfer_amount", "拟转让债权数额");
        // contractContents.put("transfer_price", "转让价格");
        // contractContents.put("transfer_over_invest_amount_str", "乙方受让债权数额");
        // contractContents.put("transfer_over_amount_str", "标的债权转让价款");
        // contractContents.put("transfer_over_amount", "标的债权转让价款");
        
        //受让人名称
        
        contractContents.put("protocol_no", "协议编号");
        
        contractContents.put("currentUserName", "受让人");
        contractContents.put("currentIdNumber", "受让人身份证号");
        contractContents.put("currentUserName", "借款人");
        contractContents.put("currentUserIdNumber", "借款人身份证号/注册号");
        contractContents.put("currentUserType", "借款人身份类型(0:个人  1:企业)");
        
        contractContents.put("isLoan", "是否是借款人(true false)");

        
        contractContents.put("loan_name", "借款方");
        contractContents.put("loan_id_number", "借款方身份证号/组织机构代码");
        
        contractContents.put("agencyName", "担保方");
        contractContents.put("agencyIdNumber", "担保方身份证号/注册号");
        contractContents.put("agencyAddress", "担保方住址");
        
        contractContents.put("amountStr", "借款金额(大写)");
        contractContents.put("amount", "借款金额");
        contractContents.put("invest_amountStr", "投资金额(大写)");
        contractContents.put("invest_amount", "投资金额");
        
        contractContents.put("contract_use", "借款用途");
        contractContents.put("repayment_type_id", "还款方式。还款方式 1：等额本息  2： 先息后本  3：一次性还款");
        contractContents.put("invest_rate", "平台服务费收取标准");
        
        contractContents.put("exchange_id", "资产的债权和收益权类型  5 ： 乙方从金融资产交易所/产权交易所等第三方交易机构购得，（包括但不限于主债权、利息、违约金、滞纳金、担保债权）。/n    6属于乙方合法持有的资产权益（包括但不限于主债权、利息、违约金、滞纳金、担保债权）");
        contractContents.put("investment_fee", "平台管理费");

        contractContents.put("apr", "年利率");
        contractContents.put("periodStr", "回购期限");
        contractContents.put("repaymentStr", "还款日");
        contractContents.put("end_time", "回购日");
        contractContents.put("begin_time", "签约时间/起息日");
        contractContents.put("investor_apr", "逾期回购违约金费率");
        contractContents.put("platform_apr", "逾期回购服务费率");
        contractContents.put("advance_rate_investor", "提前还款服务费(‰)");
        contractContents.put("advance_rate_platform", "提前还款平台费(‰)");
        
        contractContents.put("signed_time", "签约时间(如：2016年12月6日)");
        contractContents.put("contract_use", "借款用途");
        contractContents.put("amount", "借款本金数额（小写）");
        contractContents.put("amountStr", "借款本金数额（大写）");
        contractContents.put("periodStr", "借款期限");
        contractContents.put("apr", "借款年化利率");
        contractContents.put("repayment_type_id", "还款方式 1：等额本息  2： 先息后本  3：一次性还款");
        contractContents.put("begin_time", "借款起息日");
        contractContents.put("repaymentStr", "还款日");
        contractContents.put("end_time", "借款到期日");
        contractContents.put("prepayment_effective_date", "提前还款有效日。合同生效几天之后，才能进行提前还款");
        contractContents.put("advance_rate_platform", "还款服务费(%)");
        contractContents.put("advance_rate_investor", "提前还款违约金百分比(%)");
        contractContents.put("platform_apr", "逾期罚息");
        
        List contractList = new ArrayList();
        Map map = new HashMap();
        map.put("name", "投资人账号");
        map.put("reality_name", "投资人姓名");
        map.put("id_number", "身份证号");
        map.put("amount", "出借金额");
        map.put("period", "借款期限(输入时候，请带上单位)");
        contractList.add(map);
        
        contractContents.put("investlist", contractList);
        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //借款协议（无担保)
    public static void create_showProtocolHasGuaranteePDF(){
        Map<String, Object> contractContents = new LinkedHashMap();
        contractContents.put("protocol_no", "协议编号");
        
        contractContents.put("currentUserName", "出借人姓名。多个的时候，不用填写");
        contractContents.put("currentIdNumber", "出借人身份证号。多个的时候，不用填写");
        contractContents.put("isLoan", "出借人个数状态。多个借款人填写true，一个借款人填false");
        
        contractContents.put("loan_name", "借款方");
        contractContents.put("loan_id_number", "借款方身份证号/组织机构代码");
        contractContents.put("loan_type", "借款人类型，0：个人   1：企业");
        
        contractContents.put("agencyName", "担保方");
        contractContents.put("agencyIdNumber", "担保方身份证号/注册号");
        contractContents.put("agencyAddress", "担保方住址");
        
        contractContents.put("contract_use", "借款用途");
        contractContents.put("amount", "借款金额");
        contractContents.put("amountStr", "借款金额(大写)");
        contractContents.put("periodStr", "借款期限");
        contractContents.put("apr", "借款年化利率");
        contractContents.put("repayment_type_id", "还款方式。还款方式 1：等额本息  2： 先息后本  3：一次性还款");
        contractContents.put("begin_time", "签约时间/起息日");
        contractContents.put("repaymentStr", "还款日");
        contractContents.put("end_time", "借款到期日");
        contractContents.put("advance_rate_investor", "提前还款服务费率(‰)");
        contractContents.put("advance_rate_platform", "提前还款平台费率(‰)");
        contractContents.put("overdue_investor_apr", "逾期还款违约金费率(‰)");
        contractContents.put("overdue_platform_apr", "逾期还款服务费率(‰)");
        contractContents.put("invest_rate", "平台服务费收取标准（%）");
        
        contractContents.put("asset_no", "asset_no");

        List contractList = new ArrayList();
        Map map = new LinkedHashMap();
        map.put("name", "投资人账号");
        map.put("reality_name", "投资人姓名");
        map.put("id_number", "身份证号");
        map.put("amount", "出借金额");
        map.put("period", "借款期限(输入时候，请带上单位)");
        contractList.add(map);
        
        contractContents.put("investlist", contractList);
        
        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void create_showProtocolNoGuaranteePDF(){
        Map<String, Object> contractContents = new LinkedHashMap();
        contractContents.put("protocol_no", "协议编号");
        
        contractContents.put("currentUserName", "出借人姓名。多个的时候，不用填写");
        contractContents.put("currentIdNumber", "出借人身份证号。多个的时候，不用填写");
        contractContents.put("isLoan", "出借人个数状态。多个借款人填写true，一个借款人填false");
        
        contractContents.put("loan_name", "借款方");
        contractContents.put("loan_id_number", "借款方身份证号/组织机构代码");
        contractContents.put("loan_type", "借款人类型，0：个人   1：企业");
        
        contractContents.put("contract_use", "借款用途");
        contractContents.put("amount", "借款金额");
        contractContents.put("amountStr", "借款金额(大写)");
        contractContents.put("periodStr", "借款期限");
        contractContents.put("apr", "借款年化利率");
        contractContents.put("repayment_type_id", "还款方式。还款方式 1：等额本息  2： 先息后本  3：一次性还款");
        contractContents.put("begin_time", "签约时间/起息日");
        contractContents.put("repaymentStr", "还款日");
        contractContents.put("end_time", "借款到期日");
        contractContents.put("advance_rate_investor", "提前还款服务费率(‰)");
        contractContents.put("advance_rate_platform", "提前还款平台费率(‰)");
        contractContents.put("overdue_investor_apr", "逾期还款违约金费率(‰)");
        contractContents.put("overdue_platform_apr", "逾期还款服务费率(‰)");
        contractContents.put("invest_rate", "平台服务费收取标准（%）");
        
        contractContents.put("asset_no", "asset_no");

        List contractList = new ArrayList();
        Map map = new LinkedHashMap();
        map.put("name", "投资人账号");
        map.put("reality_name", "投资人姓名");
        map.put("id_number", "身份证号");
        map.put("amount", "出借金额");
        map.put("period", "借款期限(输入时候，请带上单位)");
        contractList.add(map);
        contractContents.put("investlist", contractList);
        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //借款协议（无担保)
    public static void create_showSecuredTransferProtocol(){
        Map<String, Object> contractContents = new LinkedHashMap();
        
        contractContents.put("protocol_no", "协议编号");
        
        contractContents.put("currentUserName", "受让人");
        contractContents.put("currentIdNumber", "受让人身份证号");
        
        contractContents.put("isLoan", "受让人个数状态。多个借款人填写true，一个借款人填false");
        
        contractContents.put("asset_no", "asset_no");
        

        
        contractContents.put("loan_name", "出让人");
        contractContents.put("loan_id_number", "出让人身份证号/组织机构代码");
        contractContents.put("loan_type", "出让人身份类型(0:个人  1:企业)");
        
        contractContents.put("agencyName", "担保方");
        contractContents.put("agencyIdNumber", "担保方身份证号/注册号");
        contractContents.put("agencyAddress", "担保方住址");
        
        contractContents.put("amountStr", "转让价格(大写)");
        contractContents.put("amount", "转让价格");
        contractContents.put("periodStr", "回购期限");
        contractContents.put("apr", "年利率");
        contractContents.put("repayment_type_id", "利息和回购款支付方式 1：等额本息  2： 先息后本  3：一次性还款");
        contractContents.put("begin_time", "签约时间/起息日");
        contractContents.put("repaymentStr", "还款日");
        contractContents.put("end_time", "回购日");
        
        
        contractContents.put("exchange_id", "资产的债权和收益权类型  5 ： 乙方从金融资产交易所/产权交易所等第三方交易机构购得，（包括但不限于主债权、利息、违约金、滞纳金、担保债权）。/n    6属于乙方合法持有的资产权益（包括但不限于主债权、利息、违约金、滞纳金、担保债权）");
        
        contractContents.put("advance_rate_investor", "提前还款服务费(‰)");
        contractContents.put("advance_rate_platform", "提前还款平台费(‰)");
        
        contractContents.put("investor_apr", "逾期回购违约金费率");
        contractContents.put("platform_apr", "逾期回购服务费率");
        
        contractContents.put("invest_rate", "平台服务费收取标准（%）");
        
        List contractList = new ArrayList();
        Map map = new LinkedHashMap();
        map.put("name", "投资人账号");
        map.put("reality_name", "投资人姓名");
        map.put("id_number", "身份证号");
        map.put("amount", "出借金额");
        map.put("period", "借款期限(输入时候，请带上单位)");
        contractList.add(map);
        
        contractContents.put("investlist", contractList);
        
        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //借款协议（无担保)
    public static void create_showUnsecuredTransferProtocol(){
        Map<String, Object> contractContents = new LinkedHashMap();
        
        contractContents.put("protocol_no", "协议编号");
        
        contractContents.put("currentUserName", "受让人");
        contractContents.put("currentIdNumber", "受让人身份证号");
        contractContents.put("isLoan", "受让人个数状态。多个借款人填写true，一个借款人填false");
        contractContents.put("asset_no", "asset_no");
        
        contractContents.put("loan_name", "出让人");
        contractContents.put("loan_id_number", "出让人身份证号/组织机构代码");
        contractContents.put("loan_type", "出让人身份类型(0:个人  1:企业)");
        
        contractContents.put("amountStr", "转让价格(大写)");
        contractContents.put("amount", "转让价格");
        contractContents.put("periodStr", "回购期限");
        contractContents.put("apr", "年利率");
        contractContents.put("repayment_type_id", "利息和回购款支付方式 1：等额本息  2： 先息后本  3：一次性还款");
        contractContents.put("begin_time", "签约时间/起息日");
        contractContents.put("repaymentStr", "还款日");
        contractContents.put("end_time", "回购日");
        
        
        contractContents.put("exchange_id", "资产的债权和收益权类型  5 ： 乙方从金融资产交易所/产权交易所等第三方交易机构购得，（包括但不限于主债权、利息、违约金、滞纳金、担保债权）。/n    6属于乙方合法持有的资产权益（包括但不限于主债权、利息、违约金、滞纳金、担保债权）");
        
        contractContents.put("advance_rate_investor", "提前还款服务费(‰)");
        contractContents.put("advance_rate_platform", "提前还款平台费(‰)");
        
        contractContents.put("investor_apr", "逾期回购违约金费率");
        contractContents.put("platform_apr", "逾期回购服务费率");
        
        contractContents.put("invest_rate", "平台服务费收取标准（%）");
        
        List contractList = new ArrayList();
        Map map = new LinkedHashMap();
        map.put("name", "投资人账号");
        map.put("reality_name", "投资人姓名");
        map.put("id_number", "身份证号");
        map.put("amount", "出借金额");
        map.put("period", "借款期限(输入时候，请带上单位)");
        contractList.add(map);
        contractContents.put("investlist", contractList);
        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void create_show_online_loan_agreements() {
        Map<String, Object> contractContents = new LinkedHashMap();

        contractContents.put("protocol_no", "协议编号");
        contractContents.put("lenderName", "出借人名称");
        contractContents.put("lenderIdNumber", "出借人身份证号");
        contractContents.put("borrowerName", "借款人名称");
        contractContents.put("borrowerIdNumber", "借款人身份证号/注册号");
        contractContents.put("borrowerUserType", "借款人身份类型(0:个人  1:企业)");
        
        contractContents.put("signed_time", "签约时间(如：2016年12月6日)");
        contractContents.put("contract_use", "借款用途");
        contractContents.put("amount", "借款本金数额（小写）");
        contractContents.put("amountStr", "借款本金数额（大写）");
        contractContents.put("periodStr", "借款期限");
        contractContents.put("apr", "借款年化利率");
        contractContents.put("repayment_type_id", "还款方式 1：等额本息  2： 先息后本  3：一次性还款");
        contractContents.put("begin_time", "借款起息日");
        contractContents.put("repaymentStr", "还款日");
        contractContents.put("end_time", "借款到期日");
        
        contractContents.put("prepayment_effective_date", "提前还款有效日。合同生效几天之后，才能进行提前还款");
        contractContents.put("advance_rate_platform", "还款服务费(%)");
        contractContents.put("advance_rate_investor", "提前还款违约金百分比(%)");
        contractContents.put("platform_apr", "逾期罚息");

        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        create_showUnsecuredTransferProtocol();
        create_showSecuredTransferProtocol();
        create_showProtocolNoGuaranteePDF();
        create_showProtocolHasGuaranteePDF();
        create_show_online_loan_agreements();
    }
}
