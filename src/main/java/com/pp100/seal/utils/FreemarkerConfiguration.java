package com.pp100.seal.utils;


import freemarker.template.Configuration;

@SuppressWarnings("deprecation")
public class FreemarkerConfiguration {   
       
    private static Configuration config = null;   
       
    /**  
     * Static initialization.  
     *   
     * Initialize the configuration of Freemarker.  
     */  
    static{   
        config = new Configuration();   
        config.setClassForTemplateLoading(FreemarkerConfiguration.class, "/template/protocol/");
        config.setClassicCompatible(true);
        config.setClassicCompatible(true);
        config.setDefaultEncoding("utf-8");
    }   
       
    public static Configuration getConfiguation(){   
        return config;   
    }   
  
}  