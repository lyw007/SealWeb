package com.pp100.seal.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class HtmlGenerator {

    /**
     * Generate html string.
     * 
     * @param template
     *            the name of freemarker teamlate.
     * @param variables
     *            the data of teamlate.
     * @return htmlStr
     * @throws Exception
     */
    public static String generate(String template, Map<String, Object> variables) throws Exception {

        Configuration config = FreemarkerConfiguration.getConfiguation();
        config.setTemplateLoader(new StringTemplateLoader(template));
        Template tp = config.getTemplate("");
        //Template tp = config.getTemplate(template, "UTF-8");
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        tp.process(freemarkerTranslation(variables), writer);
        String htmlStr = stringWriter.toString();
        writer.flush();
        writer.close();
        return htmlStr;
    }

    /**
     * 字符转换, 英文&是 freemarker 模板引擎的关键内容, 转英文为中文, 否则会有点报错
     * 
     * @param map
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String, Object> freemarkerTranslation(Map<String, Object> map) {
        if (map == null) {
            return map;
        }
        Set<String> keys = map.keySet();
        for (String key : keys) {
            Object obj = map.get(key);
            if (obj instanceof String) {
                String temp = (String) obj;
                map.put(key, temp.replace("&", "＆").replace(">", "＞").replace("<", "＜"));
            }
            if (obj instanceof Map) {
                freemarkerTranslation((Map) obj);
            }
        }
        return map;
    }

}