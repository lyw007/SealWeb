package com.pp100.seal.api.contractautogeneration;

public enum PayLoanContractType {
    PAYLOAN_FINANCING_SERVICES_3PARTY("showpayloan_financing_services_3party.ftl", "融资服务协议三方协议"),
    SMALLBAIPAID_LOAN_AGREEMENT("showofflineSmallBaipaidloan.ftl", "小佰-借款协议"),
    BASEIC_CLIENT_STATEMENT("show_customer_readme.ftl", "客户声明"),
    CONFIRMATION_RECEIPT("show_confirmationofreceipt.ftl", "收款确认函"),
    EXTENDING_LOAN_AGREEMENT("show_extending_loan_agreement.ftl", "借款展期协议");
       
    private String name;
    private String describe;

    private PayLoanContractType(String name, String desc) {
        this.name = name;
        this.describe = desc;
    }

    public String getName() {
        return name;
    }

    public String getDescribe() {
        return describe;
    }

}
