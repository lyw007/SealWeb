package com.pp100.seal.model.service.contractautogeneration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pp100.seal.api.contractautogeneration.ApiContractBuildOption;
import com.pp100.seal.api.contractautogeneration.ApiContractPayLoan;
import com.pp100.seal.api.contractautogeneration.PayLoanContractType;
import com.pp100.seal.model.service.template.TemplateManagerService;
import com.pp100.seal.utils.DateUtil;
import com.pp100.seal.utils.DownFileZip;

@Service
public class ContractBuildPayLoanService {
    private static Logger logger = Logger.getLogger(ContractBuildPayLoanService.class);

    private TemplateManagerService templateManagerService;

    @Autowired
    public ContractBuildPayLoanService(TemplateManagerService templateManagerService) {
        this.templateManagerService = templateManagerService;
    }

    /**
     * 压缩合同文件
     * 
     * @param applicationName
     * @param contractContents
     * @return
     */
    public File getContractZipFile(ApiContractPayLoan apiContractPayLoan) {
        List<File> files = new ArrayList<File>();
        File file = getFinancingServicesAgreement(apiContractPayLoan);
        if (file != null) { //融资服务协议（三方版 ）
            files.add(file);
        }
        file = getLoanAgreement(apiContractPayLoan);
        if (file != null) {//借款协议
            files.add(file);
        }
        file = getClientStatement(apiContractPayLoan);
        if (file != null) { //客户声明
            files.add(file);
        }
        file = getConfirmationReceipt(apiContractPayLoan);
        if (file != null) { //收款确认函
            files.add(file);
        }
        file = getExtendingLoanAgreement(apiContractPayLoan);
        if (file != null) { //借款展期协议
            files.add(file);
        }
        return DownFileZip.zipFile("小佰薪贷业务-", files);
    }

    private String getProtocolNo(PayLoanContractType payLoanContractType, ApiContractPayLoan apiContractPayLoan) {
        String protocolNo = "";
        switch (payLoanContractType) {
        case PAYLOAN_FINANCING_SERVICES_3PARTY:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()
                    .equals(apiContractPayLoan.getFinancingservices3partyOption())
                            ? apiContractPayLoan.getFinancingservices3partyProtocolNo() : "";
            break;
        case SMALLBAIPAID_LOAN_AGREEMENT:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()
                    .equals(apiContractPayLoan.getPaidLoanAgreementOption())
                            ? apiContractPayLoan.getPaidLoanAgreementProtocolNo() : "";
            break;
        case BASEIC_CLIENT_STATEMENT:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()
                    .equals(apiContractPayLoan.getClientStatementOption())
                            ? apiContractPayLoan.getClientStatementProtocolNo() : "";
            break;
        case CONFIRMATION_RECEIPT:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()
                    .equals(apiContractPayLoan.getConfirmationReceiptOption())
                            ? apiContractPayLoan.getConfirmationReceiptProtocolNo() : "";
            break;
        case EXTENDING_LOAN_AGREEMENT:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()
                    .equals(apiContractPayLoan.getExtendingLoanAgreementOption())
                            ? apiContractPayLoan.getExtendingLoanAgreementProtocolNo() : "";
            break;
        default:
            throw new RuntimeException(payLoanContractType + "无法获取协议编号");
        }
        return protocolNo;
    }

    public File downContractPDF(PayLoanContractType payLoanContractType, ApiContractPayLoan apiContractPayLoan) {
        File file = null;
        try {
            String templateContent = templateManagerService.getContractApplictionByApplictionName(payLoanContractType.getName()).getTemplate().getTemplateContent();
            apiContractPayLoan.setProtocolNo(this.getProtocolNo(payLoanContractType, apiContractPayLoan));
            Map<String, Object> contractContents = apiContractPayLoan2ContractMap(apiContractPayLoan);
            
            file = templateManagerService.getAgreementFile(payLoanContractType.getDescribe(), templateContent, new ArrayList(), contractContents, null);
        } catch (Exception e) {
            logger.error(payLoanContractType.getDescribe(), e);
        }
        return file;
    }

    /**
     * 融资服务协议（三方版 ） showcarloan_financing_services_3party.ftl
     * @param apiContractPayLoan
     * @return
     */
    public File getFinancingServicesAgreement(ApiContractPayLoan apiContractPayLoan) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name().equals(apiContractPayLoan.getFinancingservices3partyOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractPayLoan.getFinancingservices3partyOption())) {
                apiContractPayLoan.setProtocolNo(apiContractPayLoan.getFinancingservices3partyProtocolNo());
            } else {
                apiContractPayLoan.setProtocolNo("");
            }

            Map<String, Object> contractContents = apiContractPayLoan2ContractMap(apiContractPayLoan);
            String templateContent = templateManagerService.getContractApplictionByApplictionName("showcarloan_financing_services_3party.ftl").getTemplate().getTemplateContent();
            file = templateManagerService.getAgreementFile("融资服务协议", templateContent, new ArrayList(), contractContents, null);
        } catch (Exception e) {
            logger.error("融资服务协议失败", e);
        }
        return file;
    }

    /**
     * 借款协议 showofflineSmallBaipaidloan.ftl
     * @param apiContractPayLoan
     * @return
     */
    public File getLoanAgreement(ApiContractPayLoan apiContractPayLoan) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name().equals(apiContractPayLoan.getPaidLoanAgreementOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractPayLoan.getPaidLoanAgreementOption())) {
                apiContractPayLoan.setProtocolNo(apiContractPayLoan.getPaidLoanAgreementProtocolNo());
            } else {
                apiContractPayLoan.setProtocolNo("");
            }
            
            Map<String, Object> contractContents = apiContractPayLoan2ContractMap(apiContractPayLoan);
            String templateContent = templateManagerService.getContractApplictionByApplictionName("showofflineSmallBaipaidloan.ftl").getTemplate().getTemplateContent();
            file = templateManagerService.getAgreementFile("借款协议", templateContent, new ArrayList(), contractContents, null);
        } catch (Exception e) {
            logger.error("借款协议失败", e);
        }
        return file;
    }

    /**
     * 客户声明 show_customer_readme.ftl
     * @param apiContractPayLoan
     * @return
     */
    public File getClientStatement(ApiContractPayLoan apiContractPayLoan) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name()
                    .equals(apiContractPayLoan.getClientStatementOption())) {
                return file;
            }
            apiContractPayLoan.setProtocolNo("");
           
            Map<String, Object> contractContents = new HashMap();
            String templateContent = templateManagerService.getContractApplictionByApplictionName("show_customer_readme.ftl").getTemplate().getTemplateContent();
            file = templateManagerService.getAgreementFile("客户声明", templateContent, new ArrayList(), contractContents, null);
        } catch (Exception e) {
            logger.error("客户声明失败", e);
        }
        return file;
    }

    /**
     * 收款确认函 show_confirmationofreceipt.ftl
     * @param apiContractPayLoan
     * @return
     */
    public File getConfirmationReceipt(ApiContractPayLoan apiContractPayLoan) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name().equals(apiContractPayLoan.getConfirmationReceiptOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractPayLoan.getConfirmationReceiptOption())) {
                apiContractPayLoan.setProtocolNo(apiContractPayLoan.getConfirmationReceiptProtocolNo());
            } else {
                apiContractPayLoan.setProtocolNo("");
            }
            
            Map<String, Object> contractContents = apiContractPayLoan2ContractMap(apiContractPayLoan);
            String templateContent = templateManagerService.getContractApplictionByApplictionName("show_confirmationofreceipt.ftl").getTemplate().getTemplateContent();
            file = templateManagerService.getAgreementFile("收款确认函", templateContent, new ArrayList(), contractContents, null);
        } catch (Exception e) {
            logger.error("收款确认函失败", e);
        }
        return file;
    }

    /**
     * 借款展期协议（第一次可不签） show_extending_loan_agreement.ftl
     * @param apiContractPayLoan
     * @return
     */
    public File getExtendingLoanAgreement(ApiContractPayLoan apiContractPayLoan) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name().equals(apiContractPayLoan.getExtendingLoanAgreementOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractPayLoan.getExtendingLoanAgreementOption())) {
                apiContractPayLoan.setProtocolNo(apiContractPayLoan.getExtendingLoanAgreementProtocolNo());
            } else {
                apiContractPayLoan.setProtocolNo("");
            }
            Map<String, Object> contractContents = apiContractPayLoan2ContractMap(apiContractPayLoan);
            String templateContent = templateManagerService.getContractApplictionByApplictionName("show_extending_loan_agreement.ftl").getTemplate().getTemplateContent();
            file = templateManagerService.getAgreementFile("借款展期协议", templateContent, new ArrayList(), contractContents, null);
        } catch (Exception e) {
            logger.error("借款展期协议失败", e);
        }
        return file;
    }

    public Map<String, Object> apiContractPayLoan2ContractMap(ApiContractPayLoan apiContractPayLoan) {
        HashMap<String, Object> contractContents = new HashMap<String, Object>();
        contractContents.put("protocolNo", StringUtils.trimToEmpty(apiContractPayLoan.getProtocolNo()));
        contractContents.put("auditeeName", StringUtils.trimToEmpty(apiContractPayLoan.getAuditeeName()));
        contractContents.put("auditeeIdNumber", StringUtils.trimToEmpty(apiContractPayLoan.getAuditeeIdNumber()));
        contractContents.put("fiduciaryName", StringUtils.trimToEmpty(apiContractPayLoan.getFiduciaryName()));
        contractContents.put("fiduciaryIdNumber", StringUtils.trimToEmpty(apiContractPayLoan.getFiduciaryIdNumber()));
        contractContents.put("auditeeAmount", StringUtils.trimToEmpty(apiContractPayLoan.getAuditeeAmount()));
        contractContents.put("auditeeAmountStr", StringUtils.trimToEmpty(apiContractPayLoan.getAuditeeAmountStr()));
        contractContents.put("feeAmount", StringUtils.trimToEmpty(apiContractPayLoan.getFeeAmount()));
        contractContents.put("feeAmountStr", StringUtils.trimToEmpty(apiContractPayLoan.getFeeAmountStr()));
        contractContents.put("repaymentType", StringUtils.trimToEmpty(apiContractPayLoan.getRepaymentType()));
        contractContents.put("contractUse", StringUtils.trimToEmpty(apiContractPayLoan.getContractUse()));
        contractContents.put("periodStr", StringUtils.trimToEmpty(apiContractPayLoan.getPeriodStr()));
        contractContents.put("valueDate", DateUtil.dateStringFormart(StringUtils.trimToEmpty(apiContractPayLoan.getValueDate())));
        contractContents.put("repaymentDate",DateUtil.dateStringFormart(StringUtils.trimToEmpty(apiContractPayLoan.getRepaymentDate())));
        contractContents.put("apr", StringUtils.trimToEmpty(apiContractPayLoan.getApr()));
        return contractContents;
    }
}