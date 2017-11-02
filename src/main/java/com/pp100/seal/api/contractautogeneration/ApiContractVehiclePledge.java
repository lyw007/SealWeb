package com.pp100.seal.api.contractautogeneration;

public class ApiContractVehiclePledge {
    private String protocolNo;
    private String auditeeName;
    private String auditeeIdNumber;
    private String fiduciaryName;
    private String fiduciaryIdNumber;
    private String guaranteeName;
    private String guaranteeIdNumber;

    private String auditeeAmount;
    private String auditeeAmountStr;
    private String feeAmount;
    private String feeAmountStr;
    private String contractUse;
    private String periodStr;
    private String valueDate;
    private String repaymentDate;
    private String apr;
    private String repaymentType;

    private String carNumber;
    private String engineNumber;
    private String vehicleIdentificationNumber;
    private String carBrand;
    private String carBuyDate;// 抵押车辆购置日期：
    private String vehicleEvaluationValue;// 抵押车辆评估值：
    private String totalValue;// 抵押价值总额：
    private String carModel;

    // 编号-批量打印编号
    private String financingservices3partyProtocolNo;
    private String loanAgreementProtocolNo;
    private String loanPledgeNoGuaranteeProtocolNo;
    private String clientStatementProtocolNo;
    private String confirmationReceiptProtocolNo;
    private String powerOfattorneyProtocolNo;
    private String carloanCarstransferProtocolNo;
    private String extendingLoanAgreementProtocolNo;

    // 编号生成规则选项
    private String financingservices3partyOption;
    private String loanAgreementOption;
    private String loanPledgeNoGuaranteeOption;
    private String clientStatementOption;
    private String confirmationReceiptOption;
    private String powerOfattorneyOption;
    private String carloanCarstransferOption;
    private String extendingLoanAgreementOption;

    public ApiContractVehiclePledge() {
    }

    public ApiContractVehiclePledge(String protocolNo, String auditeeName, String auditeeIdNumber, String fiduciaryName,
            String fiduciaryIdNumber, String guaranteeName, String guaranteeIdNumber, String auditeeAmount,
            String auditeeAmountStr, String feeAmount, String feeAmountStr, String contractUse, String periodStr,
            String valueDate, String repaymentDate, String apr, String repaymentType, String carNumber,
            String engineNumber, String vehicleIdentificationNumber, String carBrand, String carBuyDate,
            String vehicleEvaluationValue, String totalValue, String carModel, String financingservices3partyProtocolNo,
            String loanAgreementProtocolNo, String loanPledgeNoGuaranteeProtocolNo, String clientStatementProtocolNo,
            String confirmationReceiptProtocolNo, String powerOfattorneyProtocolNo,
            String carloanCarstransferProtocolNo, String extendingLoanAgreementProtocolNo,
            String financingservices3partyOption, String loanAgreementOption, String loanPledgeNoGuaranteeOption,
            String clientStatementOption, String confirmationReceiptOption, String powerOfattorneyOption,
            String carloanCarstransferOption, String extendingLoanAgreementOption) {
        super();
        this.protocolNo = protocolNo;
        this.auditeeName = auditeeName;
        this.auditeeIdNumber = auditeeIdNumber;
        this.fiduciaryName = fiduciaryName;
        this.fiduciaryIdNumber = fiduciaryIdNumber;
        this.guaranteeName = guaranteeName;
        this.guaranteeIdNumber = guaranteeIdNumber;
        this.auditeeAmount = auditeeAmount;
        this.auditeeAmountStr = auditeeAmountStr;
        this.feeAmount = feeAmount;
        this.feeAmountStr = feeAmountStr;
        this.contractUse = contractUse;
        this.periodStr = periodStr;
        this.valueDate = valueDate;
        this.repaymentDate = repaymentDate;
        this.apr = apr;
        this.repaymentType = repaymentType;
        this.carNumber = carNumber;
        this.engineNumber = engineNumber;
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.carBrand = carBrand;
        this.carBuyDate = carBuyDate;
        this.vehicleEvaluationValue = vehicleEvaluationValue;
        this.totalValue = totalValue;
        this.carModel = carModel;
        this.financingservices3partyProtocolNo = financingservices3partyProtocolNo;
        this.loanAgreementProtocolNo = loanAgreementProtocolNo;
        this.loanPledgeNoGuaranteeProtocolNo = loanPledgeNoGuaranteeProtocolNo;
        this.clientStatementProtocolNo = clientStatementProtocolNo;
        this.confirmationReceiptProtocolNo = confirmationReceiptProtocolNo;
        this.powerOfattorneyProtocolNo = powerOfattorneyProtocolNo;
        this.carloanCarstransferProtocolNo = carloanCarstransferProtocolNo;
        this.extendingLoanAgreementProtocolNo = extendingLoanAgreementProtocolNo;
        this.financingservices3partyOption = financingservices3partyOption;
        this.loanAgreementOption = loanAgreementOption;
        this.loanPledgeNoGuaranteeOption = loanPledgeNoGuaranteeOption;
        this.clientStatementOption = clientStatementOption;
        this.confirmationReceiptOption = confirmationReceiptOption;
        this.powerOfattorneyOption = powerOfattorneyOption;
        this.carloanCarstransferOption = carloanCarstransferOption;
        this.extendingLoanAgreementOption = extendingLoanAgreementOption;
    }

    public String getProtocolNo() {
        return protocolNo;
    }

    public void setProtocolNo(String protocolNo) {
        this.protocolNo = protocolNo;
    }

    public String getAuditeeName() {
        return auditeeName;
    }

    public void setAuditeeName(String auditeeName) {
        this.auditeeName = auditeeName;
    }

    public String getAuditeeIdNumber() {
        return auditeeIdNumber;
    }

    public void setAuditeeIdNumber(String auditeeIdNumber) {
        this.auditeeIdNumber = auditeeIdNumber;
    }

    public String getFiduciaryName() {
        return fiduciaryName;
    }

    public void setFiduciaryName(String fiduciaryName) {
        this.fiduciaryName = fiduciaryName;
    }

    public String getFiduciaryIdNumber() {
        return fiduciaryIdNumber;
    }

    public void setFiduciaryIdNumber(String fiduciaryIdNumber) {
        this.fiduciaryIdNumber = fiduciaryIdNumber;
    }

    public String getGuaranteeName() {
        return guaranteeName;
    }

    public void setGuaranteeName(String guaranteeName) {
        this.guaranteeName = guaranteeName;
    }

    public String getGuaranteeIdNumber() {
        return guaranteeIdNumber;
    }

    public void setGuaranteeIdNumber(String guaranteeIdNumber) {
        this.guaranteeIdNumber = guaranteeIdNumber;
    }

    public String getAuditeeAmount() {
        return auditeeAmount;
    }

    public void setAuditeeAmount(String auditeeAmount) {
        this.auditeeAmount = auditeeAmount;
    }

    public String getAuditeeAmountStr() {
        return auditeeAmountStr;
    }

    public void setAuditeeAmountStr(String auditeeAmountStr) {
        this.auditeeAmountStr = auditeeAmountStr;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getFeeAmountStr() {
        return feeAmountStr;
    }

    public void setFeeAmountStr(String feeAmountStr) {
        this.feeAmountStr = feeAmountStr;
    }

    public String getContractUse() {
        return contractUse;
    }

    public void setContractUse(String contractUse) {
        this.contractUse = contractUse;
    }

    public String getPeriodStr() {
        return periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getVehicleIdentificationNumber() {
        return vehicleIdentificationNumber;
    }

    public void setVehicleIdentificationNumber(String vehicleIdentificationNumber) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarBuyDate() {
        return carBuyDate;
    }

    public void setCarBuyDate(String carBuyDate) {
        this.carBuyDate = carBuyDate;
    }

    public String getVehicleEvaluationValue() {
        return vehicleEvaluationValue;
    }

    public void setVehicleEvaluationValue(String vehicleEvaluationValue) {
        this.vehicleEvaluationValue = vehicleEvaluationValue;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getFinancingservices3partyProtocolNo() {
        return financingservices3partyProtocolNo;
    }

    public void setFinancingservices3partyProtocolNo(String financingservices3partyProtocolNo) {
        this.financingservices3partyProtocolNo = financingservices3partyProtocolNo;
    }

    public String getLoanAgreementProtocolNo() {
        return loanAgreementProtocolNo;
    }

    public void setLoanAgreementProtocolNo(String loanAgreementProtocolNo) {
        this.loanAgreementProtocolNo = loanAgreementProtocolNo;
    }

    public String getLoanPledgeNoGuaranteeProtocolNo() {
        return loanPledgeNoGuaranteeProtocolNo;
    }

    public void setLoanPledgeNoGuaranteeProtocolNo(String loanPledgeNoGuaranteeProtocolNo) {
        this.loanPledgeNoGuaranteeProtocolNo = loanPledgeNoGuaranteeProtocolNo;
    }

    public String getClientStatementProtocolNo() {
        return clientStatementProtocolNo;
    }

    public void setClientStatementProtocolNo(String clientStatementProtocolNo) {
        this.clientStatementProtocolNo = clientStatementProtocolNo;
    }

    public String getConfirmationReceiptProtocolNo() {
        return confirmationReceiptProtocolNo;
    }

    public void setConfirmationReceiptProtocolNo(String confirmationReceiptProtocolNo) {
        this.confirmationReceiptProtocolNo = confirmationReceiptProtocolNo;
    }

    public String getPowerOfattorneyProtocolNo() {
        return powerOfattorneyProtocolNo;
    }

    public void setPowerOfattorneyProtocolNo(String powerOfattorneyProtocolNo) {
        this.powerOfattorneyProtocolNo = powerOfattorneyProtocolNo;
    }

    public String getCarloanCarstransferProtocolNo() {
        return carloanCarstransferProtocolNo;
    }

    public void setCarloanCarstransferProtocolNo(String carloanCarstransferProtocolNo) {
        this.carloanCarstransferProtocolNo = carloanCarstransferProtocolNo;
    }

    public String getExtendingLoanAgreementProtocolNo() {
        return extendingLoanAgreementProtocolNo;
    }

    public void setExtendingLoanAgreementProtocolNo(String extendingLoanAgreementProtocolNo) {
        this.extendingLoanAgreementProtocolNo = extendingLoanAgreementProtocolNo;
    }

    public String getFinancingservices3partyOption() {
        return financingservices3partyOption;
    }

    public void setFinancingservices3partyOption(String financingservices3partyOption) {
        this.financingservices3partyOption = financingservices3partyOption;
    }

    public String getLoanAgreementOption() {
        return loanAgreementOption;
    }

    public void setLoanAgreementOption(String loanAgreementOption) {
        this.loanAgreementOption = loanAgreementOption;
    }

    public String getLoanPledgeNoGuaranteeOption() {
        return loanPledgeNoGuaranteeOption;
    }

    public void setLoanPledgeNoGuaranteeOption(String loanPledgeNoGuaranteeOption) {
        this.loanPledgeNoGuaranteeOption = loanPledgeNoGuaranteeOption;
    }

    public String getClientStatementOption() {
        return clientStatementOption;
    }

    public void setClientStatementOption(String clientStatementOption) {
        this.clientStatementOption = clientStatementOption;
    }

    public String getConfirmationReceiptOption() {
        return confirmationReceiptOption;
    }

    public void setConfirmationReceiptOption(String confirmationReceiptOption) {
        this.confirmationReceiptOption = confirmationReceiptOption;
    }

    public String getPowerOfattorneyOption() {
        return powerOfattorneyOption;
    }

    public void setPowerOfattorneyOption(String powerOfattorneyOption) {
        this.powerOfattorneyOption = powerOfattorneyOption;
    }

    public String getCarloanCarstransferOption() {
        return carloanCarstransferOption;
    }

    public void setCarloanCarstransferOption(String carloanCarstransferOption) {
        this.carloanCarstransferOption = carloanCarstransferOption;
    }

    public String getExtendingLoanAgreementOption() {
        return extendingLoanAgreementOption;
    }

    public void setExtendingLoanAgreementOption(String extendingLoanAgreementOption) {
        this.extendingLoanAgreementOption = extendingLoanAgreementOption;
    }
}
