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
import com.pp100.seal.api.contractautogeneration.ApiContractVehicleMortage;
import com.pp100.seal.api.contractautogeneration.VehicleMortageContractType;
import com.pp100.seal.model.service.template.TemplateManagerService;
import com.pp100.seal.utils.DateUtil;
import com.pp100.seal.utils.DownFileZip;

@Service
public class ContractBuildVehicleMortgageService {
    private static Logger logger = Logger.getLogger(ContractBuildVehicleMortgageService.class);

    private TemplateManagerService templateManagerService;

    @Autowired
    public ContractBuildVehicleMortgageService(TemplateManagerService templateManagerService) {
        this.templateManagerService = templateManagerService;
    }

    /**
     * 压缩合同文件
     * 
     * @param contractContents
     * @return
     */
    public File getContractZipFile(ApiContractVehicleMortage apiContractVehicleMortage) {
        List<File> files = new ArrayList<File>();
        File file = getFinancingServicesAgreement(apiContractVehicleMortage);
        if (file != null) {
            files.add(file);
        }
        file = getGuaranteedLoanAgreement(apiContractVehicleMortage);
        if (file != null) {
            files.add(file);
        }
        file = getCollateralSecurityAgreement(apiContractVehicleMortage);
        if (file != null) {
            files.add(file);
        }
        file = getClientStatement(apiContractVehicleMortage);
        if (file != null) {
            files.add(file);
        }
        file = getletterAttorney(apiContractVehicleMortage);
        if (file != null) {
            files.add(file);
        }
        file = getConfirmationReceipt(apiContractVehicleMortage);
        if (file != null) {
            files.add(file);
        }
        file = getVehicleTransferAgreement(apiContractVehicleMortage);
        if (file != null) {
            files.add(file);
        }
        file = getExtendingLoanAgreement(apiContractVehicleMortage);
        if (file != null) {
            files.add(file);
        }
        return DownFileZip.zipFile("车贷业务-车辆抵押", files);
    }

    private String getProtocolNo(VehicleMortageContractType vehicleMortageContractType, ApiContractVehicleMortage apiContractVehicleMortage) {
        String protocolNo = "";
        switch (vehicleMortageContractType) {
        case CARLOAN_FINANCING_SERVICES_3PARTY:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehicleMortage.getCarloanCarstransferOption()) ? apiContractVehicleMortage.getFinancingservices3partyProtocolNo() : "";
            break;
        case GUARANTEED_LOAN_AGREEMENT:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehicleMortage.getLoanAgreementOption()) ? apiContractVehicleMortage.getLoanAgreementProtocolNo() : "";
            break;
        case MORTGAGE_NO_GUARANTEE:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehicleMortage.getLoanMortgageNoGuaranteeOption()) ? apiContractVehicleMortage.getLoanMortgageNoGuaranteeProtocolNo() : "";
            break;
        case BASEIC_CLIENT_STATEMENT:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehicleMortage.getClientStatementOption()) ? apiContractVehicleMortage.getClientStatementProtocolNo() : "";
            break;
        case POWER_OFATTORNEY:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehicleMortage.getPowerOfattorneyOption()) ? apiContractVehicleMortage.getPowerOfattorneyProtocolNo() : "";
            break;
        case CONFIRMATION_RECEIPT:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name() .equals(apiContractVehicleMortage.getConfirmationReceiptOption()) ? apiContractVehicleMortage.getConfirmationReceiptProtocolNo() : "";
            break;
        case CAR_STRANSFER:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()  .equals(apiContractVehicleMortage.getCarloanCarstransferOption()) ? apiContractVehicleMortage.getCarloanCarstransferProtocolNo() : "";
            break;
        case EXTENDING_LOAN_AGREEMENT:
            protocolNo = ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()  .equals(apiContractVehicleMortage.getExtendingLoanAgreementOption()) ? apiContractVehicleMortage.getExtendingLoanAgreementProtocolNo() : "";
            break;
        default:
            throw new RuntimeException(vehicleMortageContractType + "无法获取协议编号");
        }
        return protocolNo;
    }

    public File downContractPDF(VehicleMortageContractType vehicleMortageContractType, ApiContractVehicleMortage apiContractVehicleMortage) {
        File file = null;
        try {
            String templateContent = templateManagerService.getContractApplictionByApplictionName(vehicleMortageContractType.getName()).getTemplate().getTemplateContent();
            apiContractVehicleMortage.setProtocolNo(this.getProtocolNo(vehicleMortageContractType, apiContractVehicleMortage));
            Map<String, Object> contractContents = this.apiContract2ContractMap(apiContractVehicleMortage);
            file = templateManagerService.getAgreementFile(vehicleMortageContractType.getDescribe(), templateContent, new ArrayList(), contractContents, null);
        } catch (Exception e) {
            logger.error(vehicleMortageContractType.getDescribe(), e);
        }
        return file;
    }
    
    private File getContractPdf(ApiContractVehicleMortage apiContractVehicleMortage,
            VehicleMortageContractType vehicleMortageContractType) throws Exception {
        Map<String, Object> contractContents = apiContract2ContractMap(apiContractVehicleMortage);
        String templateContent = templateManagerService.getContractApplictionByApplictionName(vehicleMortageContractType.getName()).getTemplate().getTemplateContent();
        return templateManagerService.getAgreementFile(vehicleMortageContractType.getDescribe(), templateContent, new ArrayList(), contractContents, null);
    }

    /**
     * 融资服务协议（三方版 ）showcarloan_financing_services_3party.ftl
     * @param apiContractVehicleMortage
     * @return
     */
    private File getFinancingServicesAgreement(ApiContractVehicleMortage apiContractVehicleMortage) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name().equals(apiContractVehicleMortage.getFinancingservices3partyOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehicleMortage.getFinancingservices3partyOption())) {
                apiContractVehicleMortage.setProtocolNo(apiContractVehicleMortage.getFinancingservices3partyProtocolNo());
            } else {
                apiContractVehicleMortage.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehicleMortage, VehicleMortageContractType.CARLOAN_FINANCING_SERVICES_3PARTY);
        } catch (Exception e) {
            logger.error("融资服务协议失败", e);
        }
        return file;
    }

    /**
     * 保证借款协议（办理抵押登记）showcarloan_guaranteed_loan_agreement.ftl
     * @param apiContractVehicleMortage
     * @return
     */
    private File getGuaranteedLoanAgreement(ApiContractVehicleMortage apiContractVehicleMortage) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name().equals(apiContractVehicleMortage.getLoanAgreementOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehicleMortage.getLoanAgreementOption())) {
                apiContractVehicleMortage.setProtocolNo(apiContractVehicleMortage.getLoanAgreementProtocolNo());
            } else {
                apiContractVehicleMortage.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehicleMortage, VehicleMortageContractType.GUARANTEED_LOAN_AGREEMENT);
        } catch (Exception e) {
            logger.error("保证借款协议失败", e);
        }
        return file;
    }

    /**
     * 抵押反担保协议（办理抵押登记） showcarloan_mortgage_no_guarantee.ftl
     * @param apiContractVehicleMortage
     * @return
     */
    private File getCollateralSecurityAgreement(ApiContractVehicleMortage apiContractVehicleMortage) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name()
                    .equals(apiContractVehicleMortage.getLoanMortgageNoGuaranteeOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()
                    .equals(apiContractVehicleMortage.getLoanMortgageNoGuaranteeOption())) {
                apiContractVehicleMortage
                        .setProtocolNo(apiContractVehicleMortage.getLoanMortgageNoGuaranteeProtocolNo());
            } else {
                apiContractVehicleMortage.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehicleMortage, VehicleMortageContractType.MORTGAGE_NO_GUARANTEE);
        } catch (Exception e) {
            logger.error("抵押反担保协议失败", e);
        }
        return file;
    }

    /**
     * 客户声明 show_customer_readme.ftl
     * @param apiContractVehicleMortage
     * @return
     */
    private File getClientStatement(ApiContractVehicleMortage apiContractVehicleMortage) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name()
                    .equals(apiContractVehicleMortage.getClientStatementOption())) {
                return file;
            }
            apiContractVehicleMortage.setProtocolNo("");
            file = this.getContractPdf(apiContractVehicleMortage, VehicleMortageContractType.BASEIC_CLIENT_STATEMENT);
        } catch (Exception e) {
            logger.error("客户声明失败", e);
        }
        return file;
    }

    /**
     * 授权委托书 show_powerofattorney.ftl
     * @param apiContractVehicleMortage
     * @return
     */
    private File getletterAttorney(ApiContractVehicleMortage apiContractVehicleMortage) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name()
                    .equals(apiContractVehicleMortage.getPowerOfattorneyOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()
                    .equals(apiContractVehicleMortage.getPowerOfattorneyOption())) {
                apiContractVehicleMortage.setProtocolNo(apiContractVehicleMortage.getPowerOfattorneyProtocolNo());
            } else {
                apiContractVehicleMortage.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehicleMortage, VehicleMortageContractType.POWER_OFATTORNEY);
        } catch (Exception e) {
            logger.error("授权委托书失败", e);
        }
        return file;
    }

    /**
     * 收款确认函 show_confirmationofreceipt.ftl
     * @param apiContractVehicleMortage
     * @return
     */
    private File getConfirmationReceipt(ApiContractVehicleMortage apiContractVehicleMortage) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name()
                    .equals(apiContractVehicleMortage.getConfirmationReceiptOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()
                    .equals(apiContractVehicleMortage.getConfirmationReceiptOption())) {
                apiContractVehicleMortage.setProtocolNo(apiContractVehicleMortage.getConfirmationReceiptProtocolNo());
            } else {
                apiContractVehicleMortage.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehicleMortage, VehicleMortageContractType.CONFIRMATION_RECEIPT);
        } catch (Exception e) {
            logger.error("收款确认函失败", e);
        }
        return file;
    }

    /**
     * 车辆转让协议 showcarloan_carstransfer.ftl
     * @param apiContractVehicleMortage
     * @return
     */
    private File getVehicleTransferAgreement(ApiContractVehicleMortage apiContractVehicleMortage) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name()
                    .equals(apiContractVehicleMortage.getCarloanCarstransferOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name()
                    .equals(apiContractVehicleMortage.getCarloanCarstransferOption())) {
                apiContractVehicleMortage.setProtocolNo(apiContractVehicleMortage.getCarloanCarstransferProtocolNo());
            } else {
                apiContractVehicleMortage.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehicleMortage, VehicleMortageContractType.CONFIRMATION_RECEIPT);
        } catch (Exception e) {
            logger.error("车辆转让协议失败", e);
        }
        return file;
    }

    /**
     * 借款展期协议（第一次可不签） show_extending_loan_agreement.ftl
     * @param apiContractVehicleMortage
     * @return
     */
    private File getExtendingLoanAgreement(ApiContractVehicleMortage apiContractVehicleMortage) {
        File file = null;
        try {
            // 不打印
            if (ApiContractBuildOption.NO_GENERATION.name().equals(apiContractVehicleMortage.getExtendingLoanAgreementOption())) {
                return file;
            }
            // 打印包含合同编号
            if (ApiContractBuildOption.GENERATION_AUTO_NUMBER.name().equals(apiContractVehicleMortage.getExtendingLoanAgreementOption())) {
                apiContractVehicleMortage.setProtocolNo(apiContractVehicleMortage.getExtendingLoanAgreementProtocolNo());
            } else {
                apiContractVehicleMortage.setProtocolNo("");
            }
            file = this.getContractPdf(apiContractVehicleMortage, VehicleMortageContractType.EXTENDING_LOAN_AGREEMENT);
        } catch (Exception e) {
            logger.error("借款展期协议失败", e);
        }
        return file;
    }

    public Map<String, Object> apiContract2ContractMap(ApiContractVehicleMortage apiContractVehicleMortage) {
        HashMap<String, Object> contractContents = new HashMap<String, Object>();
        contractContents.put("protocolNo", StringUtils.trimToEmpty(apiContractVehicleMortage.getProtocolNo()));
        contractContents.put("auditeeName", StringUtils.trimToEmpty(apiContractVehicleMortage.getAuditeeName()));
        contractContents.put("auditeeIdNumber", StringUtils.trimToEmpty(apiContractVehicleMortage.getAuditeeIdNumber()));
        contractContents.put("fiduciaryName", StringUtils.trimToEmpty(apiContractVehicleMortage.getFiduciaryName()));
        contractContents.put("fiduciaryIdNumber", StringUtils.trimToEmpty(apiContractVehicleMortage.getFiduciaryIdNumber()));

        contractContents.put("guaranteeName", StringUtils.trimToEmpty(apiContractVehicleMortage.getGuaranteeName()));
        contractContents.put("guaranteeIdNumber", StringUtils.trimToEmpty(apiContractVehicleMortage.getFiduciaryIdNumber()));

        contractContents.put("auditeeAmount", StringUtils.trimToEmpty(apiContractVehicleMortage.getAuditeeAmount()));
        contractContents.put("auditeeAmountStr",  StringUtils.trimToEmpty(apiContractVehicleMortage.getAuditeeAmountStr()));
        contractContents.put("feeAmount", StringUtils.trimToEmpty(apiContractVehicleMortage.getFeeAmount()));
        contractContents.put("feeAmountStr", StringUtils.trimToEmpty(apiContractVehicleMortage.getFeeAmountStr()));
        contractContents.put("repaymentType", StringUtils.trimToEmpty(apiContractVehicleMortage.getRepaymentType()));
        contractContents.put("contractUse", StringUtils.trimToEmpty(apiContractVehicleMortage.getContractUse()));
        contractContents.put("periodStr", StringUtils.trimToEmpty(apiContractVehicleMortage.getPeriodStr()));
        contractContents.put("valueDate", DateUtil.dateStringFormart(StringUtils.trimToEmpty(apiContractVehicleMortage.getValueDate())));
        contractContents.put("repaymentDate",  DateUtil.dateStringFormart(StringUtils.trimToEmpty(apiContractVehicleMortage.getRepaymentDate())));
        contractContents.put("apr", StringUtils.trimToEmpty(apiContractVehicleMortage.getApr()));
        
        contractContents.put("carNumber", apiContractVehicleMortage.getCarNumber());
        contractContents.put("engineNumber", apiContractVehicleMortage.getEngineNumber());
        contractContents.put("vehicleIdentificationNumber", apiContractVehicleMortage.getVehicleIdentificationNumber());
        contractContents.put("carBrand", apiContractVehicleMortage.getCarBrand());
        contractContents.put("carBuyDate", DateUtil.dateStringFormart(apiContractVehicleMortage.getCarBuyDate()));
        contractContents.put("vehicleEvaluationValue", apiContractVehicleMortage.getVehicleEvaluationValue());
        contractContents.put("totalValue", apiContractVehicleMortage.getTotalValue());
        contractContents.put("carModel", apiContractVehicleMortage.getCarModel());
        
        return contractContents;
    }
}