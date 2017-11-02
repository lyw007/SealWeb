package com.pp100.seal.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pp100.model.domain.JsonMapper;
import com.pp100.seal.api.ApiEnterprise;
import com.pp100.seal.api.ApiPerson;
import com.pp100.seal.utils.JsonUtil;

/**
 * 需要从网上找一下，真是的身份证号及组织机构代码
 * 
 * @author Administrator
 *
 */
public class CreateTestData {
    public static Map createContractContents() {
        Map<String, Object> contractContents = new HashMap();
        contractContents.put("transfer_no", "协议编号");
        contractContents.put("out_user_reality_name", "债权出让人");
        contractContents.put("out_user_reality_idNumber", "债权出让人身份证号");
        contractContents.put("currentUserName", "受让人");
        contractContents.put("currentUserIdNumber", "受让人身份证号");
        contractContents.put("begin_time", "开始时间");
        contractContents.put("bid_title", "借款项目名称");
        contractContents.put("bid_no", "借款协议编号");
        contractContents.put("bid_apr", "借款项目年化利率");
        contractContents.put("bid_amount", "借款金额");
        contractContents.put("bid_period", "借款项目期限");
        contractContents.put("bid_period_unit", "借款项目期限单位");
        contractContents.put("bid_repayment_time", "借款项目到期日");
        contractContents.put("bid_repayment_type", "还款方式");
        contractContents.put("bid_guarantor", "保障措施s");
        contractContents.put("invest_amount", "出借金额");
        contractContents.put("invest_over_corpus", "已偿还本金");
        contractContents.put("invest_surplus_corpus", "剩余本金");
        contractContents.put("bid_surplus_period", "剩余期限");
        contractContents.put("transfer_amount", "拟转让债权数额");
        contractContents.put("transfer_price", "转让价格");
        contractContents.put("transfer_over_invest_amount_str", "乙方受让债权数额");
        contractContents.put("transfer_over_amount_str", "标的债权转让价款");
        contractContents.put("transfer_over_amount", "标的债权转让价款");

        return contractContents;
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
    
    public static void main(String[] args) {
        try {
            JsonMapper jsonMapper = new JsonMapper();
            System.out.println(jsonMapper.toJson(createContractContents()));
            System.out.println(jsonMapper.toJson(createContractList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
