package com.pp100.seal.api.contractautogeneration;

public class ApiContractPayLoan {
    private String protocolNo;
    private String auditeeName;
    private String auditeeIdNumber;
    private String fiduciaryName;
    private String fiduciaryIdNumber;
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

    //编号-批量打印编号
    private String financingservices3partyProtocolNo;
    private String paidLoanAgreementProtocolNo;
    private String ClientStatementProtocolNo;
    private String confirmationReceiptProtocolNo;
    private String extendingLoanAgreementProtocolNo;
    
    //编号生成规则选项
    private String financingservices3partyOption;
    private String paidLoanAgreementOption;
    private String clientStatementOption;
    private String confirmationReceiptOption;
    private String extendingLoanAgreementOption;

    public ApiContractPayLoan() {
    }

    public ApiContractPayLoan(String protocolNo, String auditeeName, String auditeeIdNumber, String fiduciaryName,
            String fiduciaryIdNumber, String auditeeAmount, String auditeeAmountStr, String feeAmount,
            String feeAmountStr, String contractUse, String periodStr, String valueDate, String repaymentDate,
            String apr, String repaymentType) {
        this.protocolNo = protocolNo;
        this.auditeeName = auditeeName;
        this.auditeeIdNumber = auditeeIdNumber;
        this.fiduciaryName = fiduciaryName;
        this.fiduciaryIdNumber = fiduciaryIdNumber;
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
    }

    public void setProtocolNo(String protocolNo) {
        this.protocolNo = protocolNo;
    }

    public void setAuditeeName(String auditeeName) {
        this.auditeeName = auditeeName;
    }

    public void setAuditeeIdNumber(String auditeeIdNumber) {
        this.auditeeIdNumber = auditeeIdNumber;
    }

    public void setFiduciaryName(String fiduciaryName) {
        this.fiduciaryName = fiduciaryName;
    }

    public void setFiduciaryIdNumber(String fiduciaryIdNumber) {
        this.fiduciaryIdNumber = fiduciaryIdNumber;
    }

    public void setAuditeeAmount(String auditeeAmount) {
        this.auditeeAmount = auditeeAmount;
    }

    public void setAuditeeAmountStr(String auditeeAmountStr) {
        this.auditeeAmountStr = auditeeAmountStr;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public void setFeeAmountStr(String feeAmountStr) {
        this.feeAmountStr = feeAmountStr;
    }

    public void setContractUse(String contractUse) {
        this.contractUse = contractUse;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getProtocolNo() {
        return protocolNo;
    }

    public String getAuditeeName() {
        return auditeeName;
    }

    public String getAuditeeIdNumber() {
        return auditeeIdNumber;
    }

    public String getFiduciaryName() {
        return fiduciaryName;
    }

    public String getFiduciaryIdNumber() {
        return fiduciaryIdNumber;
    }

    public String getAuditeeAmount() {
        return auditeeAmount;
    }

    public String getAuditeeAmountStr() {
        return auditeeAmountStr;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public String getFeeAmountStr() {
        return feeAmountStr;
    }

    public String getContractUse() {
        return contractUse;
    }

    public String getPeriodStr() {
        return periodStr;
    }

    public String getValueDate() {
        return valueDate;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public String getApr() {
        return apr;
    }

    public String getFinancingservices3partyProtocolNo() {
        return financingservices3partyProtocolNo;
    }

    public void setFinancingservices3partyProtocolNo(String financingservices3partyProtocolNo) {
        this.financingservices3partyProtocolNo = financingservices3partyProtocolNo;
    }

    public String getPaidLoanAgreementProtocolNo() {
        return paidLoanAgreementProtocolNo;
    }

    public void setPaidLoanAgreementProtocolNo(String paidLoanAgreementProtocolNo) {
        this.paidLoanAgreementProtocolNo = paidLoanAgreementProtocolNo;
    }

    public String getClientStatementProtocolNo() {
        return ClientStatementProtocolNo;
    }

    public void setClientStatementProtocolNo(String clientStatementProtocolNo) {
        ClientStatementProtocolNo = clientStatementProtocolNo;
    }

    public String getConfirmationReceiptProtocolNo() {
        return confirmationReceiptProtocolNo;
    }

    public void setConfirmationReceiptProtocolNo(String confirmationReceiptProtocolNo) {
        this.confirmationReceiptProtocolNo = confirmationReceiptProtocolNo;
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

    public String getPaidLoanAgreementOption() {
        return paidLoanAgreementOption;
    }

    public void setPaidLoanAgreementOption(String paidLoanAgreementOption) {
        this.paidLoanAgreementOption = paidLoanAgreementOption;
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

    public String getExtendingLoanAgreementOption() {
        return extendingLoanAgreementOption;
    }

    public void setExtendingLoanAgreementOption(String extendingLoanAgreementOption) {
        this.extendingLoanAgreementOption = extendingLoanAgreementOption;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }
}
