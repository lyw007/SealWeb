package com.pp100.seal.api.contractautogeneration;

public enum VehicleMortageContractType {
    CARLOAN_FINANCING_SERVICES_3PARTY("showcarloan_financing_services_3party.ftl", "融资服务协议三方协议"),
    GUARANTEED_LOAN_AGREEMENT("showcarloan_guaranteed_loan_agreement.ftl", "保证借款协议（办理抵押登记）"),
    MORTGAGE_NO_GUARANTEE("showcarloan_mortgage_no_guarantee.ftl", "抵押反担保协议"),
    BASEIC_CLIENT_STATEMENT("show_customer_readme.ftl", "客户声明"),
    POWER_OFATTORNEY("show_powerofattorney.ftl", "授权委托书"),
    CONFIRMATION_RECEIPT("show_confirmationofreceipt.ftl", "收款确认函"),
    CAR_STRANSFER("showcarloan_carstransfer.ftl", "车辆转让协议"),
    EXTENDING_LOAN_AGREEMENT("show_extending_loan_agreement.ftl", "借款展期协议");

    private String name;
    private String describe;

    private VehicleMortageContractType(String name, String desc) {
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
