package com.pp100.seal.model.sealutil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ESignSetting {
    private static ESignSetting instance;

    public static ESignSetting getInstance() {
        return instance;
    }

    public ESignSetting() {
        instance = this;
    }

    @Value("#{applicationProperties['eseal.projectid']}")
    private String projectId;
    @Value("#{applicationProperties['eseal.projectsecret']}")
    private String projectSecret;

    public String getProjectId() {
        return projectId;
    }

    public String getProjectSecret() {
        return projectSecret;
    }
}
