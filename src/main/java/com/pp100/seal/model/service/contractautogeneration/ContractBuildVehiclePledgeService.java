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
import com.pp100.seal.api.contractautogeneration.ApiContractVehiclePledge;
import com.pp100.seal.api.contractautogeneration.VehiclePledgeContractType;
import com.pp100.seal.model.service.template.TemplateManagerService;
import com.pp100.seal.utils.DateUtil;
import com.pp100.seal.utils.DownFileZip;

@Service
public class ContractBuildVehiclePledgeService {
    private static Logger logger = Logger.getLogger(ContractBuildVehiclePledgeService.class);

    private TemplateManagerService templateManagerService;

    @Autowired
    public ContractBuildVehiclePledgeService(TemplateManagerService templateManagerService) {
        this.templateManagerService = templateManagerService;
    }

    /**
     * 压缩合同文件
     * 
     * @param contractContents
     * @return
     */
    public File getContractZipFile(ApiContractVehiclePledge apiContractVehiclePledge) {
        List<File> files = new ArrayList<File>();
        File file = getFinancingServicesAgreement(apiContractVehiclePledge);
        if (file != null) {
            files.add(file);
        }
        file = getGuaranteedLoanAgreement(apiContractVehiclePledge);
        if (file != null) {
            files.add(file);
        }
        file = getPledgeCounterGuaranteeAgreement(apiContractVehiclePledge);
        if (file != null) {
            files.add(file);
        }
        file = getClientStatement(apiContractVehiclePledge);
        if (file != null) {
            files.add(file);
        }
        file = getletterAttorney(apiContractVehiclePledge);
        if (file != null) {
            files.add(file);
        }
        file = getConfirmationReceipt(apiContractVehiclePledge);
        if (file != null) {
            files.add(file);
        }
        file = getVehicleTransferAgreement(apiContractVehiclePledge);
        if (file != null) {
            files.add(file);
        }
        file = getExtendingLoanAgreement(apiContractVehiclePledge);
        if (file != null) {
            files.add(file);
        }
        return DownFileZip.zipFile("车贷业务-车辆抵押", files);
    }

    private String getProtocolNo(VehiclePledgeContractType VehiclePledgeContractType, ApiContractVehiclePledge apiContractVehiclePledge) {
        String protocolNo = "";
        switch (VehiclePledgeContractType) {
        case CARLOAN_FINANCING_SERVICES_3PARTY:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehiclePledge.getCarloanCarstransferOption()) ? apiContractVehiclePledge.getFinancingservices3partyProtocolNo() : "";
            break;
        case GUARANTEED_LOAN_AGREEMENT:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehiclePledge.getLoanAgreementOption()) ? apiContractVehiclePledge.getLoanAgreementProtocolNo() : "";
            break;
        case PLEDGE_NO_GUARANTEE:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehiclePledge.getLoanPledgeNoGuaranteeOption()) ? apiContractVehiclePledge.getLoanPledgeNoGuaranteeProtocolNo() : "";
            break;
        case BASEIC_CLIENT_STATEMENT:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehiclePledge.getClientStatementOption()) ? apiContractVehiclePledge.getClientStatementProtocolNo() : "";
            break;
        case POWER_OFATTORNEY:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehiclePledge.getPowerOfattorneyOption()) ? apiContractVehiclePledge.getPowerOfattorneyProtocolNo() : "";
            break;
        case CONFIRMATION_RECEIPT:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name() .equals(apiContractVehiclePledge.getConfirmationReceiptOption()) ? apiContractVehiclePledge.getConfirmationReceiptProtocolNo() : "";
            break;
        case CAR_STRANSFER:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()  .equals(apiContractVehiclePledge.getCarloanCarstransferOption()) ? apiContractVehiclePledge.getCarloanCarstransferProtocolNo() : "";
            break;
        case EXTENDING_LOAN_AGREEMENT:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()  .equals(apiContractVehiclePledge.getExtendingLoanAgreementOption()) ? apiContractVehiclePledge.getExtendingLoanAgreementProtocolNo() : "";
            break;
        }
        return protocolNo;
    }

    public File downContractPDF(VehiclePledgeContractType VehiclePledgeContractType, ApiContractVehiclePledge apiContractVehiclePledge) {
        File file = null;
        try {
            String templateContent = templateManagerService.getContractApplictionByApplictionName(VehiclePledgeContractType.getName()).getTemplate().getTemplateContent();
            apiContractVehiclePledge.setProtocolNo(this.getProtocolNo(VehiclePledgeContractType, apiContractVehiclePledge));
            Map<String, Object> contractContents = this.apiContract2ContractMap(apiContractVehiclePledge);
            file = templateManagerService.getAgreementFile(VehiclePledgeContractType.getDescribe(), templateContent, new ArrayList(), contractContents, null);
        } catch (Exception e) {
            logger.error(VehiclePledgeContractType.getDescribe(), e);
        }
        return file;
    }
    
    private File getContractPdf(ApiContractVehiclePledge apiContractVehiclePledge,
            VehiclePledgeContractType VehiclePledgeContractType) throws Exception {
        Map<String, Object> contractContents = apiContract2ContractMap(apiContractVehiclePledge);
        String templateContent = templateManagerService.getContractApplictionByApplictionName(VehiclePledgeContractType.getName()).getTemplate().getTemplateContent();
        return templateManagerService.getAgreementFile(VehiclePledgeContractType.getDescribe(), templateContent, new ArrayList(), contractContents, null);
    }

    /**
     * 融资服务协议（三方版 ）showcarloan_financing_services_3party.ftl
     * @param apiContractVehiclePledge
     * @return
     */
    private File getFinancingServicesAgreement(ApiContractVehiclePledge apiContractVehiclePledge) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name().equals(apiContractVehiclePledge.getFinancingservices3partyOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehiclePledge.getFinancingservices3partyOption())) {
                apiContractVehiclePledge.setProtocolNo(apiContractVehiclePledge.getFinancingservices3partyProtocolNo());
            } else {
                apiContractVehiclePledge.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehiclePledge, VehiclePledgeContractType.CARLOAN_FINANCING_SERVICES_3PARTY);
        } catch (Exception e) {
            logger.error("融资服务协议失败", e);
        }
        return file;
    }

    /**
     * 保证借款协议（办理抵押登记）showcarloan_guaranteed_loan_agreement.ftl
     * @param apiContractVehiclePledge
     * @return
     */
    private File getGuaranteedLoanAgreement(ApiContractVehiclePledge apiContractVehiclePledge) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name().equals(apiContractVehiclePledge.getLoanAgreementOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehiclePledge.getLoanAgreementOption())) {
                apiContractVehiclePledge.setProtocolNo(apiContractVehiclePledge.getLoanAgreementProtocolNo());
            } else {
                apiContractVehiclePledge.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehiclePledge, VehiclePledgeContractType.GUARANTEED_LOAN_AGREEMENT);
        } catch (Exception e) {
            logger.error("保证借款协议失败", e);
        }
        return file;
    }

    /**
     * 抵押反担保协议（办理抵押登记） showcarloan_mortgage_no_guarantee.ftl
     * @param apiContractVehiclePledge
     * @return
     */
    private File getPledgeCounterGuaranteeAgreement(ApiContractVehiclePledge apiContractVehiclePledge) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name()
                    .equals(apiContractVehiclePledge.getLoanPledgeNoGuaranteeOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()
                    .equals(apiContractVehiclePledge.getLoanPledgeNoGuaranteeOption())) {
                apiContractVehiclePledge
                        .setProtocolNo(apiContractVehiclePledge.getLoanPledgeNoGuaranteeProtocolNo());
            } else {
                apiContractVehiclePledge.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehiclePledge, VehiclePledgeContractType.PLEDGE_NO_GUARANTEE);
        } catch (Exception e) {
            logger.error("抵押反担保协议失败", e);
        }
        return file;
    }

    /**
     * 客户声明 show_customer_readme.ftl
     * @param apiContractVehiclePledge
     * @return
     */
    private File getClientStatement(ApiContractVehiclePledge apiContractVehiclePledge) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name()
                    .equals(apiContractVehiclePledge.getClientStatementOption())) {
                return file;
            }
            apiContractVehiclePledge.setProtocolNo("");
            file = this.getContractPdf(apiContractVehiclePledge, VehiclePledgeContractType.BASEIC_CLIENT_STATEMENT);
        } catch (Exception e) {
            logger.error("客户声明失败", e);
        }
        return file;
    }

    /**
     * 授权委托书 show_powerofattorney.ftl
     * @param apiContractVehiclePledge
     * @return
     */
    private File getletterAttorney(ApiContractVehiclePledge apiContractVehiclePledge) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name()
                    .equals(apiContractVehiclePledge.getPowerOfattorneyOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()
                    .equals(apiContractVehiclePledge.getPowerOfattorneyOption())) {
                apiContractVehiclePledge.setProtocolNo(apiContractVehiclePledge.getPowerOfattorneyProtocolNo());
            } else {
                apiContractVehiclePledge.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehiclePledge, VehiclePledgeContractType.POWER_OFATTORNEY);
        } catch (Exception e) {
            logger.error("授权委托书失败", e);
        }
        return file;
    }

    /**
     * 收款确认函 show_confirmationofreceipt.ftl
     * @param apiContractVehiclePledge
     * @return
     */
    private File getConfirmationReceipt(ApiContractVehiclePledge apiContractVehiclePledge) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name()
                    .equals(apiContractVehiclePledge.getConfirmationReceiptOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()
                    .equals(apiContractVehiclePledge.getConfirmationReceiptOption())) {
                apiContractVehiclePledge.setProtocolNo(apiContractVehiclePledge.getConfirmationReceiptProtocolNo());
            } else {
                apiContractVehiclePledge.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehiclePledge, VehiclePledgeContractType.CONFIRMATION_RECEIPT);
        } catch (Exception e) {
            logger.error("收款确认函失败", e);
        }
        return file;
    }

    /**
     * 车辆转让协议 showcarloan_carstransfer.ftl
     * @param apiContractVehiclePledge
     * @return
     */
    private File getVehicleTransferAgreement(ApiContractVehiclePledge apiContractVehiclePledge) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name()
                    .equals(apiContractVehiclePledge.getCarloanCarstransferOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()
                    .equals(apiContractVehiclePledge.getCarloanCarstransferOption())) {
                apiContractVehiclePledge.setProtocolNo(apiContractVehiclePledge.getCarloanCarstransferProtocolNo());
            } else {
                apiContractVehiclePledge.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehiclePledge, VehiclePledgeContractType.CAR_STRANSFER);
        } catch (Exception e) {
            logger.error("车辆转让协议失败", e);
        }
        return file;
    }

    /**
     * 借款展期协议（第一次可不签） show_extending_loan_agreement.ftl
     * @param apiContractVehiclePledge
     * @return
     */
    private File getExtendingLoanAgreement(ApiContractVehiclePledge apiContractVehiclePledge) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name().equals(apiContractVehiclePledge.getExtendingLoanAgreementOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehiclePledge.getExtendingLoanAgreementOption())) {
                apiContractVehiclePledge.setProtocolNo(apiContractVehiclePledge.getExtendingLoanAgreementProtocolNo());
            } else {
                apiContractVehiclePledge.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehiclePledge, VehiclePledgeContractType.EXTENDING_LOAN_AGREEMENT);
        } catch (Exception e) {
            logger.error("借款展期协议失败", e);
        }
        return file;
    }

    public Map<String, Object> apiContract2ContractMap(ApiContractVehiclePledge apiContractVehiclePledge) {
        HashMap<String, Object> contractContents = new HashMap<String, Object>();
        contractContents.put("protocolNo", StringUtils.trimToEmpty(apiContractVehiclePledge.getProtocolNo()));
        contractContents.put("auditeeName", StringUtils.trimToEmpty(apiContractVehiclePledge.getAuditeeName()));
        contractContents.put("auditeeIdNumber", StringUtils.trimToEmpty(apiContractVehiclePledge.getAuditeeIdNumber()));
        contractContents.put("fiduciaryName", StringUtils.trimToEmpty(apiContractVehiclePledge.getFiduciaryName()));
        contractContents.put("fiduciaryIdNumber", StringUtils.trimToEmpty(apiContractVehiclePledge.getFiduciaryIdNumber()));

        contractContents.put("guaranteeName", StringUtils.trimToEmpty(apiContractVehiclePledge.getGuaranteeName()));
        contractContents.put("guaranteeIdNumber", StringUtils.trimToEmpty(apiContractVehiclePledge.getFiduciaryIdNumber()));

        contractContents.put("auditeeAmount", StringUtils.trimToEmpty(apiContractVehiclePledge.getAuditeeAmount()));
        contractContents.put("auditeeAmountStr",  StringUtils.trimToEmpty(apiContractVehiclePledge.getAuditeeAmountStr()));
        contractContents.put("feeAmount", StringUtils.trimToEmpty(apiContractVehiclePledge.getFeeAmount()));
        contractContents.put("feeAmountStr", StringUtils.trimToEmpty(apiContractVehiclePledge.getFeeAmountStr()));
        contractContents.put("repaymentType", StringUtils.trimToEmpty(apiContractVehiclePledge.getRepaymentType()));
        contractContents.put("contractUse", StringUtils.trimToEmpty(apiContractVehiclePledge.getContractUse()));
        contractContents.put("periodStr", StringUtils.trimToEmpty(apiContractVehiclePledge.getPeriodStr()));
        contractContents.put("valueDate", DateUtil.dateStringFormart(StringUtils.trimToEmpty(apiContractVehiclePledge.getValueDate())));
        contractContents.put("repaymentDate",  DateUtil.dateStringFormart(StringUtils.trimToEmpty(apiContractVehiclePledge.getRepaymentDate())));
        contractContents.put("apr", StringUtils.trimToEmpty(apiContractVehiclePledge.getApr()));
        
        contractContents.put("carNumber", apiContractVehiclePledge.getCarNumber());
        contractContents.put("engineNumber", apiContractVehiclePledge.getEngineNumber());
        contractContents.put("vehicleIdentificationNumber", apiContractVehiclePledge.getVehicleIdentificationNumber());
        contractContents.put("carBrand", apiContractVehiclePledge.getCarBrand());
        contractContents.put("carBuyDate", DateUtil.dateStringFormart(apiContractVehiclePledge.getCarBuyDate()));
        contractContents.put("vehicleEvaluationValue", apiContractVehiclePledge.getVehicleEvaluationValue());
        contractContents.put("totalValue", apiContractVehiclePledge.getTotalValue());
        contractContents.put("carModel", apiContractVehiclePledge.getCarModel());
        
        return contractContents;
    }
}