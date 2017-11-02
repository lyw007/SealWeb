package com.pp100.seal.model.sealutil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.timevale.esign.sdk.tech.bean.OrganizeBean;
import com.timevale.esign.sdk.tech.bean.PersonBean;
import com.timevale.esign.sdk.tech.bean.result.AddAccountResult;
import com.timevale.esign.sdk.tech.impl.constants.OrganRegType;
import com.timevale.esign.sdk.tech.service.AccountService;
import com.timevale.esign.sdk.tech.service.factory.AccountServiceFactory;

@Service
public class EsignAccountService {
    private AccountService serviceImpl = AccountServiceFactory.instance();

    /**
     * 注册个人用户，并返回唯一标识
     * 
     * @param openId
     *            开发者ID
     * @param personName
     *            用户姓名 （不为空）
     * @param idno
     *            身份证号（不为空）
     * @param mobile
     *            手机号 （不为空）
     * @param address地址
     *            （为空 - 不进行维护）
     * @param organ
     *            所在公司 （为空 - 不进行维护）
     * @param title
     *            职务 （为空 - 不进行维护）
     * @param email
     *            邮箱 （为空 - 不进行维护）
     * @return 用户唯一标识
     */
    public AddAccountResult addPerson2AccountId(String openId, String personName, String personIdNumber, String mobile) {
        PersonBean personBean = new PersonBean();
        personBean.setName(personName).setIdNo(personIdNumber).setMobile(mobile).setEmail("").setPersonArea(0);
        return serviceImpl.addAccount(openId, personBean);
    }

    /**
     * 注册企业用户，并返回唯一标识
     * 
     * @param openId
     *            开发者Id
     * @param name
     *            结构名称
     * @param mobile
     *            手机号
     * @param regCode
     *            工商注册号 （为空 - 不进行维护）
     * @param organCode组织机构代码证
     * @param address
     *            地区 （为空 - 不进行维护）
     * @param scope
     *            经营范围（为空 - 不进行维护）
     * @param regType
     *            企业注册类型(为空 - 不进行维护)
     * @param legalName
     *            法人代表名称
     * @param legalIdNo
     *            法人身份证号
     * @param legalArea
     *            法定代表归属地法定代表人归属地, 0-大陆，1-香港，2-澳门，3-台湾， 4-外籍，默认 0
     * @param userType
     *            注册类型
     * @param agentName
     *            代理人身份证号
     * @param agentIdNo
     *            代理人身份证号
     * @param organType
     *            单位类型 - 0-普通企业，1-社会团体，2-事业单位，3-民办非企业单位，4-党政及国家机构，默认0
     */
    public AddAccountResult addOrganize2AccountId(String openId, String organizeName, String mobile, String enterpriseCode,
            String legalName, String legalIdNo, int legalArea, int userType, String agentName, String agentIdNo,
            String email, int organType, String regCode) {

        OrganizeBean organizeBean = new OrganizeBean();

        organizeBean.setName(organizeName);
        organizeBean.setOrganCode(enterpriseCode.replaceAll("-", "").replaceAll("—", ""));
        organizeBean.setEmail(email);
        organizeBean.setMobile(mobile);
        organizeBean.setRegCode(regCode); // 工商注册号

        organizeBean.setOrganType(organType); // 单位类型，0-普通企业，1-社会团体，2-事业单位，3-民办非企业单位，4-党政及国家机构，默认0
        
        organizeBean.setRegType(enterpriseCode.length() > 12 ? OrganRegType.MERGE : OrganRegType.NORMAL); 
        
        organizeBean.setUserType(userType);
        agentName = userType == 1 ? agentName : null; // 代理人姓名
        agentIdNo = userType == 1 ? agentIdNo : null; // 代理人身份证号/护照
        legalName = userType == 2 ? legalName : null; // 法人代表姓名
        legalIdNo = userType == 2 ? legalIdNo : null; // 法人代表身份证号/护照

        organizeBean.setAgentIdNo(agentIdNo);
        organizeBean.setAgentName(agentName);
        organizeBean.setLegalIdNo(legalIdNo);
        organizeBean.setLegalName(legalName);

        organizeBean.setLegalArea(legalArea); // 法人代表所在地 （默认为0，表示大陆）
        return serviceImpl.addAccount(openId, organizeBean);
    }
}
