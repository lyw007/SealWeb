package com.pp100.seal.api.contractautogeneration;

import java.util.TreeMap;

public enum ApiContractBuildOption {
    GENERATION_AUTO_NUMBER("生成且自动编号"), GENERATION_NO_NUMBER("生成但不编号"), NO_GENERATION("不生成此合同");

    private String descript;

    private ApiContractBuildOption(String descript) {
        this.descript = descript;
    }

    public String getDescript() {
        return descript;
    }

    public static TreeMap getCodeMap() {
        TreeMap map = new TreeMap();
        map.put("GENERATION_AUTO_NUMBER", GENERATION_AUTO_NUMBER.getDescript());
        map.put("GENERATION_NO_NUMBER", GENERATION_NO_NUMBER.getDescript());
        map.put("NO_GENERATION", NO_GENERATION.getDescript());
        return map;
    }
}
