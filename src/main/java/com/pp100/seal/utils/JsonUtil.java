package com.pp100.seal.utils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class JsonUtil {

    /**
     * 传入任意一个 object对象生成一个指定规格的字符串
     * 
     * @param object
     *            任意对象
     * @return String
     */
    public static String objectToJson(Object object) {
        StringBuilder json = new StringBuilder();
        if (object == null) {
            json.append("\"\"");
        } else if (object instanceof String || object instanceof Integer || object instanceof Double
                || object instanceof Long) {
            json.append("\"").append(object.toString()).append("\"");
        } else {
            json.append(beanToJson(object));
        }
        return json.toString();
    }

    /**
     * 传入任意一个 Javabean对象生成一个指定规格的字符串
     * 
     * @param bean
     *            bean对象
     * @return String "{}"
     */
    public static String beanToJson(Object bean) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        PropertyDescriptor[] props = null;
        try {
            props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (props != null) {
            for (int i = 0; i < props.length; i++) {
                try {
                    if (!(props[i].getPropertyType()).isInterface()) {
                        String name = objectToJson(props[i].getName());
                        String value = objectToJson(props[i].getReadMethod().invoke(bean));
                        json.append(name);
                        json.append(":");
                        json.append(value);
                        json.append(",");
                    }
                } catch (Exception e) {
                }
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    /**
     * 通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串
     * 
     * @param list
     *            列表对象
     * @return String "[{},{}]"
     */
    public static String listToJson(List<?> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append(objectToJson(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    public static List<?> jsonToList(String json,Object obj) {

        if (StringUtils.trimToEmpty(json).equals(""))
            return null;
        try {
            List<Object> list = JSONArray.toList(JSONArray.fromObject(json), obj, new JsonConfig());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}