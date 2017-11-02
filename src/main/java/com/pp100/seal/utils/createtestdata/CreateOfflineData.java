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
public class CreateOfflineData {
    public static Map createContractContents() {
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

        contractContents.put("protocol_no", "协议编号");
        contractContents.put("out_user_reality_name", "出借人");
        contractContents.put("out_user_reality_idNumber", "出借人身份证号");
        contractContents.put("currentUserName", "借款人");
        contractContents.put("currentUserIdNumber", "借款人身份证号/注册号");
        contractContents.put("currentUserType", "借款人身份类型(0:个人  1:企业)");
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

        return contractContents;
    }

    public static void create_show_offline_loan_agreements() {
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
    
    public static List createContractList() {
        List contractList = new ArrayList();
        Map map = new HashMap();
        map.put("name", "投资人账号");
        map.put("reality_name", "投资人姓名");
        map.put("id_number", "身份证号");
        map.put("payable", "购买价格");
        map.put("accept_amount", "债权本金");
        contractList.add(map);
        return contractList;
    }
    
    //授权委托书
    public static void create_showcarloan_powerofattorney() {
        Map<String, Object> contractContents = new LinkedHashMap();
        
        contractContents.put("clientName", "委托人");
        contractContents.put("clientIdNumber", "委托人身份证号");
        contractContents.put("trusteeName", "受托人");
        contractContents.put("trusteeIdNumber", "受托人身份证号");
        contractContents.put("carNumber", "车牌号");
        contractContents.put("engineNumber", "发动机号");
        contractContents.put("frameNumber", "车架号");
        contractContents.put("carBrand", "车品牌");
        contractContents.put("relatedMatters", "相关事宜");
        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //委托扣划授权书
    public static void create_showcarloan_delegatededuction() {
        Map<String, Object> contractContents = new LinkedHashMap();
        
        contractContents.put("clientName", "授权人");
        contractContents.put("clientIdNumber", "授权人身份证号");
        contractContents.put("financing_services_agreement_code", "融资服务协议协议编号");
        contractContents.put("loan_agreement", "借款协议服务编号");
        contractContents.put("accountName", "授权银行账号-户名");
        contractContents.put("accountIdnumber", "授权银行账号-身份证号");
        contractContents.put("bankCard", "授权银行账号-银行卡账号");
        contractContents.put("silverAccount", "授权银行账号-银行卡开户银行");
        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //收款确认函(showcarloan-confirmationofreceipt.ftl)
    public static void create_showcarloan_confirmationofreceipt() {
        Map<String, Object> contractContents = new LinkedHashMap();
        contractContents.put("code", "编号");
        contractContents.put("loan_agreement", "借款协议服务编号");
        contractContents.put("amount", "借款本金数额（小写）");
        contractContents.put("amountStr", "借款本金数额（大写）");
        contractContents.put("accountName", "授权银行账号-户名");
        contractContents.put("bankCard", "授权银行账号-银行卡账号");
        contractContents.put("silverAccount", "授权银行账号-银行卡开户银行");
        
        List contractList = new ArrayList();
        Map map = new HashMap();
        map.put("projectName", "项目编号");
        map.put("loanAgreementCode", "借款协议编号");
        map.put("amount", "金额");
        map.put("projectTerm", "期限");
        map.put("receivablesTime", "收款时间");
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
    
    
    //车辆转让协议
    public static void create_showcarloan_carstransfer() {
        
        Map<String, Object> contractContents = new LinkedHashMap();
        contractContents.put("protocol_no", "编号");
        contractContents.put("transferorName", "出让方名称");
        contractContents.put("transferorIdNumber", "出让方身份证号");
        contractContents.put("transferorAddress", "出让方地址");
        contractContents.put("transferorMobile", "出让方联系电话");
        contractContents.put("transfereeName", "受让方名称");
        contractContents.put("transfereeIdNumber", "受让方身份证号");
        contractContents.put("transfereeAddress", "受让方地址");
        contractContents.put("transfereeMobile", "受让方联系电话");
        contractContents.put("amount", "借款本金数额（小写）");
        contractContents.put("amountStr", "借款本金数额（大写）");
        contractContents.put("carName", "车名称");
        contractContents.put("carModel", "车型号");
        contractContents.put("carNumber", "车牌号");
        contractContents.put("engineNumber", "发动机号");
        contractContents.put("frameNumber", "车架号");
        contractContents.put("thirdPartyEvaluators", "第三方评定机构");
        contractContents.put("transfer_valid_year", "车辆移交有效年--乙方未在xxxx年xx月xx日期，甲方有权解除《车辆转让协议》");
        contractContents.put("transfer_valid_month", "车辆移交有效-月-乙方未在xxxx年xx月xx日期，甲方有权解除《车辆转让协议》");
        contractContents.put("transfer_valid_day", "车辆移交有效-日-乙方未在xxxx年xx月xx日期，甲方有权解除《车辆转让协议》");
        contractContents.put("apr", "年利率");
        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    //车辆转让协议(抵押无担保协议)
    public static void create_showcarloan_mortgage_no_guarantee() {
        
        Map<String, Object> contractContents = new LinkedHashMap();
        contractContents.put("protocol_no", "编号");
        contractContents.put("mortgageName", "抵押人名称");
        contractContents.put("mortgageIdNumber", "抵押人身份证号/注册号");
        contractContents.put("mortgageAddress", "住所");
        contractContents.put("mortgageeName", "受让方名称");
        contractContents.put("mortgageeIdNumber", "受让方身份证号");
        contractContents.put("mortgageeAddress", "受让方地址");
        contractContents.put("guaranteed_loan_agreement_code", "保证借款协议编号");
        contractContents.put("guaranteed_loan_agreement_year", "保证借款协议签订-年");
        contractContents.put("guaranteed_loan_agreement_month", "保证借款协议签订-月");
        contractContents.put("guaranteed_loan_agreement_day", "保证借款协议签订-日");
        
        contractContents.put("carNumber", "车牌号");
        contractContents.put("engineNumber", "发动机号");
        contractContents.put("frameNumber", "车架号");
        contractContents.put("carBrand", "车品牌");
        contractContents.put("buyingTime_year", "抵押车辆购置日期-年");
        contractContents.put("buyingTime_month", "抵押车辆购置日期-月");
        contractContents.put("buyingTime_day", "抵押车辆购置日期-日");
       
        contractContents.put("vehicle_valuation_price", "车辆估值价格（人民币-元）");
        contractContents.put("mortgage_vehicle_total", "商定抵押车辆的抵押价值总额（人民币-元）");
        contractContents.put("drive_away", "允许驾离范围");
        
        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    //质押无担保协议
    public static void create_showcarloan_pledge_no_guarantee() {
        
        Map<String, Object> contractContents = new LinkedHashMap();
        contractContents.put("protocol_no", "编号");
        contractContents.put("pledgorName", "质押人名称");
        contractContents.put("pledgorIdNumber", "质押人身份证号/注册号");
        contractContents.put("pledgorAddress", "质押人住所");
        contractContents.put("pledgeeName", "质押权人名称");
        contractContents.put("pledgeeIdNumber", "质押权人身份证号");
        contractContents.put("pledgeeAddress", "质押权人住所");
        
        contractContents.put("guaranteed_loan_agreement_code", "保证借款协议编号");
        contractContents.put("guaranteed_loan_agreement_year", "保证借款协议签订-年");
        contractContents.put("guaranteed_loan_agreement_month", "保证借款协议签订-月");
        contractContents.put("guaranteed_loan_agreement_day", "保证借款协议签订-日");
        
        contractContents.put("carNumber", "车牌号");
        contractContents.put("engineNumber", "发动机号");
        contractContents.put("frameNumber", "车架号");
        contractContents.put("carBrand", "车品牌");
        contractContents.put("buyingTime_year", "质押车辆购置日期-年");
        contractContents.put("buyingTime_month", "质押车辆购置日期-月");
        contractContents.put("buyingTime_day", "质押车辆购置日期-日");
       
        contractContents.put("vehicle_valuation_price", "车辆估值价格（人民币-元）");
        contractContents.put("mortgage_vehicle_total", "商定质押车辆的抵押价值总额（人民币-元）");
        
        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    //保证借款协议
    public static void create_showcarloan_guaranteed_loan_agreement() {
        
        Map<String, Object> contractContents = new LinkedHashMap();
        contractContents.put("protocol_no", "编号");
        contractContents.put("borrowerName", "借款人名称");
        contractContents.put("borrowerIdNumber", "借款人身份证号");
        contractContents.put("lenderName", "出借人名称");
        contractContents.put("lenderIdNumber", "出借人身份证号");
        contractContents.put("guaranteeName", "担保方名称");
        contractContents.put("guaranteeIdNumber", "担保方注册号");
        contractContents.put("guaranteeAddress", "住所");
        
        contractContents.put("amount", "借款金额（小写）（单位：元）");
        contractContents.put("amountStr", "借款金额（大写）（单位：元）");
        
        contractContents.put("loan_period", "借款期限(单位：天)");
        contractContents.put("begin_time_year", "借款期限的开始时间-年");
        contractContents.put("begin_time_month", "借款期限的开始时间-月");
        contractContents.put("begin_time_day", "借款期限的开始时间-日");
        contractContents.put("end_time_year", "借款期限的结束时间-年");
        contractContents.put("end_time_month", "借款期限的结束时间-月");
        contractContents.put("end_time_day", "借款期限的结束时间-日");
        
        
        contractContents.put("usage_of_loan", "借款用途");

        contractContents.put("loan_interest_text", "借款计息计算标准(年/月/日)");
        contractContents.put("borrowing_rate_text", "借款利率计算标准(年/月/日)");
        contractContents.put("borrowing_rate", "借款利率(单位：%)");
        
        contractContents.put("user_days", "用款标准-标准天数(利息计算标准,单位：天)");
      
        
        contractContents.put("accountName", "收取利息的账户-账户名称");
        contractContents.put("bankCard", "收取利息的账户-银行卡账号");
        contractContents.put("silverAccount", "收取利息的账户-开户银行");
       
        contractContents.put("repayment_type_id", "还款方式 1：等额本息  2： 先息后本  3：一次性还款");
        
        contractContents.put("penal_sum", "违约金每日标准（如：10%、千分比）");
        
        
        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    //融资服务协议（三方版）
    public static void create_showcarloan_financing_services_3party() {
        
        Map<String, Object> contractContents = new LinkedHashMap();
        contractContents.put("protocol_no", "编号");
        contractContents.put("auditeeName", "委托方名称");
        contractContents.put("auditeeIdNumber", "委托方身份证号/注册号");
        contractContents.put("auditeeAddress", "委托方住所");
        
        contractContents.put("fiduciaryName", "受托方名称");
        contractContents.put("fiduciaryIdNumber", "受托方身份证号/注册号");
        contractContents.put("fiduciaryAddress", "受托方住所");
        
        contractContents.put("auditee_amount", "甲方（委托方）融资额度（小写）（单位：元）");
        contractContents.put("auditee_amountStr", "甲方（委托方）融资额度（大写）（单位：元）");
        
        contractContents.put("service_period", "服务期限");
        contractContents.put("begin_time_year", "服务期限的开始时间-年");
        contractContents.put("begin_time_month", "服务期限的开始时间-月");
        contractContents.put("begin_time_day", "服务期限的开始时间-日");
        contractContents.put("end_time_year", "服务期限的结束时间-年");
        contractContents.put("end_time_month", "服务期限的结束时间-月");
        contractContents.put("end_time_day", "服务期限的结束时间-日");
        
        contractContents.put("fee", "手续费(单位:%)");
        contractContents.put("fee_amount", "手续费金额(小写)（单位：元）");
        contractContents.put("fee_amountStr", "手续费金额(大写)（单位：元）");
        
        contractContents.put("service_charge", "融资服务费(单位:%)");
        contractContents.put("service_charge_amount", "服务费金额(小写)（单位：元）");
        contractContents.put("service_charge_amountStr", "服务费金额(大写)（单位：元）");
        contractContents.put("service_charge_mode", "融资服务费收取方式(A、B、C 三种)");
        
        contractContents.put("fiduciary_accountName", "受托方-收取融资服务费-账户名称");
        contractContents.put("fiduciary_bankCard", "受托方-收取融资服务费-银行卡账号");
        contractContents.put("fiduciary_silverAccount", "受托方-收取融资服务费-开户银行");
        
        contractContents.put("platform_service_fee", "平台服务费(单位:%)");
        contractContents.put("platform_service_fee_amount", "平台服务费金额(小写)（单位：元）");
        contractContents.put("platform_service_fee_amountStr", "平台服务费金额(大写)（单位：元）");
        contractContents.put("platform_service_fee_mode", "平台服务费收取方式(A、B、C 三种)");
        
        contractContents.put("early_repayment_service_fee_standard", "提前还款服务费收费标准（如：10%，10‰）");
        contractContents.put("overdue_repayment_service_fee_standard", "逾期还款服务费收费标准（如：10%，10‰）");
        
        contractContents.put("platform_accountName", "平台方-收取融资服务费-账户名称");
        contractContents.put("platform_bankCard", "平台方-收取融资服务费-银行卡账号");
        contractContents.put("platform_silverAccount", "平台方-收取融资服务费-开户银行");
        
        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //融资服务协议（二方版）
    public static void create_showcarloan_financing_services_2party() {
        
        Map<String, Object> contractContents = new LinkedHashMap();
        contractContents.put("protocol_no", "编号");
        contractContents.put("auditeeName", "委托方名称");
        contractContents.put("auditeeIdNumber", "委托方身份证号/注册号");
        contractContents.put("auditeeAddress", "委托方住所");
        
        contractContents.put("auditee_amount", "甲方（委托方）融资额度（小写）（单位：元）");
        contractContents.put("auditee_amountStr", "甲方（委托方）融资额度（大写）（单位：元）");
        
        contractContents.put("service_period", "服务期限");
        contractContents.put("begin_time_year", "服务期限的开始时间-年");
        contractContents.put("begin_time_month", "服务期限的开始时间-月");
        contractContents.put("begin_time_day", "服务期限的开始时间-日");
        contractContents.put("end_time_year", "服务期限的结束时间-年");
        contractContents.put("end_time_month", "服务期限的结束时间-月");
        contractContents.put("end_time_day", "服务期限的结束时间-日");
        
        contractContents.put("fee", "手续费(单位:%)");
        contractContents.put("fee_amount", "手续费金额(小写)（单位：元）");
        contractContents.put("fee_amountStr", "手续费金额(大写)（单位：元）");
        
        contractContents.put("pre_service_charge_amount", "前期服务费金额(小写)");
        contractContents.put("pre_service_charge_amountStr", "前期服务费金额(大写)");
        contractContents.put("pre_service_charge_maxtimeallowed;", "甲方提交前期服务费最大允许时间(单位：月)");
        
        contractContents.put("late_service_charge_standard", "后期服务费按照借款金额的百分比（单位%）");
        contractContents.put("late_service_charge_amount", "后期服务费金额(小写)");
        contractContents.put("late_service_charge_amountStr", "后期服务费金额(大写)");
        contractContents.put("late_service_charge_mode", "后期服务费收取方式（有 A、B、C 三种方式）");
        
        contractContents.put("early_repayment_service_fee_standard", "提前还款服务费收费标准（如：10%，10‰）");
        contractContents.put("overdue_repayment_service_fee_standard", "逾期还款服务费收费标准（如：10%，10‰）");
        
        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            apiGenerateContractParameter.setValues(contractContents);
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //融资服务协议（二方版）
    public static void create_showcarloan_customer_readme() {

        ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
        try {
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(apiGenerateContractParameter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        try {
            create_showcarloan_powerofattorney();
            create_showcarloan_delegatededuction();
            create_showcarloan_confirmationofreceipt();
            create_showcarloan_carstransfer();
            create_showcarloan_mortgage_no_guarantee();
            create_showcarloan_pledge_no_guarantee();
            create_showcarloan_guaranteed_loan_agreement();
            create_showcarloan_financing_services_3party();
            create_showcarloan_financing_services_2party();
            create_showcarloan_customer_readme();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
