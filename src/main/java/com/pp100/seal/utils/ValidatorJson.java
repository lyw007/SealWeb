package com.pp100.seal.utils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ValidatorJson {

    private static Logger logger = Logger.getLogger(ValidatorJson.class);

    private CharacterIterator it;
    private char c;
    private int col;

    public ValidatorJson() {
    }

    /**
     * 验证一个字符串是否是合法的JSON串
     *
     * @param input
     *            要验证的字符串
     * @return true-合法 ，false-非法
     */
    public boolean isValidate(String input) {
        input = input.trim();
        boolean ret = valid(input);
        return ret;
    }

    private boolean valid(String input) {
        if ("".equals(input)) {
            return true;
        }

        boolean ret = true;
        it = new StringCharacterIterator(input);
        c = it.first();
        col = 1;
        if (!value()) {
            ret = error("value", 1);
        } else {
            skipWhiteSpace();
            if (c != CharacterIterator.DONE) {
                ret = error("end", col);
            }
        }

        return ret;
    }

    private boolean value() {
        return literal("true") || literal("false") || literal("null") || string() || number() || object() || array();
    }

    private boolean literal(String text) {
        CharacterIterator ci = new StringCharacterIterator(text);
        char t = ci.first();
        if (c != t) {
            return false;
        }

        int start = col;
        boolean ret = true;
        for (t = ci.next(); t != CharacterIterator.DONE; t = ci.next()) {
            if (t != nextCharacter()) {
                ret = false;
                break;
            }
        }
        nextCharacter();
        if (!ret) {
            error("literal " + text, start);
        }
        return ret;
    }

    private boolean array() {
        return aggregate('[', ']', false);
    }

    private boolean object() {
        return aggregate('{', '}', true);
    }

    private boolean aggregate(char entryCharacter, char exitCharacter, boolean prefix) {
        if (c != entryCharacter) {
            return false;
        }
        nextCharacter();
        skipWhiteSpace();
        if (c == exitCharacter) {
            nextCharacter();
            return true;
        }
        for (;;) {
            if (prefix) {
                int start = col;
                if (!string()) {
                    return error("string", start);
                }
                skipWhiteSpace();
                if (c != ':') {
                    return error("colon", col);
                }
                nextCharacter();
                skipWhiteSpace();
            }
            if (value()) {
                skipWhiteSpace();
                if (c == ',') {
                    nextCharacter();
                } else if (c == exitCharacter) {
                    break;
                } else {
                    return error("comma or " + exitCharacter, col);
                }
            } else {
                return error("value", col);
            }
            skipWhiteSpace();
        }

        nextCharacter();
        return true;
    }

    private boolean number() {
        if (!Character.isDigit(c) && c != '-') {
            return false;
        }

        int start = col;
        if (c == '-') {
            nextCharacter();
        }

        if (c == '0') {
            nextCharacter();
        } else if (Character.isDigit(c)) {
            while (Character.isDigit(c)) {
                nextCharacter();
            }
        } else {
            return error("number", start);
        }
        if (c == '.') {
            nextCharacter();
            if (Character.isDigit(c)) {
                while (Character.isDigit(c)) {
                    nextCharacter();
                }
            } else {
                return error("number", start);
            }
        }
        if (c == 'e' || c == 'E') {
            nextCharacter();
            if (c == '+' || c == '-') {
                nextCharacter();
            }
            if (Character.isDigit(c)) {
                while (Character.isDigit(c))
                    nextCharacter();
            } else {
                return error("number", start);
            }
        }
        return true;
    }

    private boolean string() {
        if (c != '"') {
            return false;
        }

        int start = col;
        boolean escaped = false;
        for (nextCharacter(); c != CharacterIterator.DONE; nextCharacter()) {
            if (!escaped && c == '\\') {
                escaped = true;
            } else if (escaped) {
                if (!escape()) {
                    return false;
                }
                escaped = false;
            } else if (c == '"') {
                nextCharacter();
                return true;
            }
        }
        return error("quoted string", start);
    }

    private boolean escape() {
        int start = col - 1;
        if (" \\\"/bfnrtu".indexOf(c) < 0) {
            return error("escape sequence  \\\",\\\\,\\/,\\b,\\f,\\n,\\r,\\t  or  \\uxxxx ", start);
        }
        if (c == 'u') {
            if (!ishex(nextCharacter()) || !ishex(nextCharacter()) || !ishex(nextCharacter())
                    || !ishex(nextCharacter())) {
                return error("unicode escape sequence  \\uxxxx ", start);
            }
        }
        return true;
    }

    private boolean ishex(char d) {
        return "0123456789abcdefABCDEF".indexOf(c) >= 0;
    }

    private char nextCharacter() {
        c = it.next();
        ++col;
        return c;
    }

    private void skipWhiteSpace() {
        while (Character.isWhitespace(c)) {
            nextCharacter();
        }
    }

    private boolean error(String type, int col) {
        //System.out.printf("type: %s, col: %s%s", type, col, System.getProperty("line.separator"));
        return false;
    }

    public static final String templetJson = "{" + "\"code\": \"0\"," + "\"msg\": \"ok\","
            + "\"time\": \"2016-10-13 16:13:48\"," + "\"data\": {" + "\"count\": 1," + "\"list\": [{"
            + "\"pid\": \"商品id\"," + "\"title\": \"商品名称\"," + "\"price\": \"价格\"," + "\"desc\": \"商品描述\"}],"
            + "\"list2\": [{" + "\"pid\": \"商品id\"," + "\"title\": \"商品名称\"," + "\"price\": \"价格\","
            + "\"desc\": \"商品描述\"}]" + "}}";

    public static final String testJson = "{" + "\"code\": \"0\"," + "\"msg\": \"ok\","
            + "\"time\": \"2016-10-13 16:13:48\"," + "\"data\": {" + "\"count\": \"1\"," + "\"list\": [{"
            + "\"pid\": \"edb1eb3e82964efba7117fa7feed2f53\"," + "\"title\": \"牛B辣条\"," + "\"price\": \"12.00\","
            + "\"desc\": \"炒鸡好吃的辣条，辣条里面的领导者。\"}]," + "\"list2\": [{" + "\"pid\": \"商品id\"," + "\"title\": \"商品名称\","
            + "\"price\": \"价格\"," + "\"desc\": \"商品描述\"}]" + "}}";

    /**
     * 比对json和模板的区别
     * 
     * @param json
     * @param templet
     * @return
     */
    public boolean compare(String json, String templet) {
        ArrayList<String> jsonKeyList = getJsonKeys(null, JSONObject.parseObject(json));
        ArrayList<String> templetKeyList = getJsonKeys(null, JSONObject.parseObject(templet));
        int sizeDiff = jsonKeyList.size() - templetKeyList.size();
        StringBuilder builder = new StringBuilder();
        boolean isContain = true;
        for (String string : jsonKeyList) {
            if (!templetKeyList.contains(string)) {
                isContain = false;
                builder.append(string).append(",");
            }
        }
        if (sizeDiff != 0) {
            logger.info("json结构校验: 长度不一致");
        }
        if (!isContain) {
            logger.info("json结构校验: 不包含参数  " + builder.toString());
        }
        if (sizeDiff == 0 && isContain) {
            logger.info("json结构校验: json format is right");
        }
        return sizeDiff == 0 || isContain;
    }

    /**
     * 获取json所有的key的集合
     * 
     * @param head
     * @param jsonObj
     * @return
     */
    public static ArrayList<String> getJsonKeys(String head, JSONObject jsonObj) {
        ArrayList<String> keys = new ArrayList<>();
        Set<Entry<String, Object>> set = jsonObj.entrySet();
        Iterator<Entry<String, Object>> iter = set.iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            keys.add(head == null ? key : head + key);
            // 判断value类型
            if (value instanceof JSONObject) {
                ArrayList<String> list = getJsonKeys(head == null ? key + "-" : head + key + "-", (JSONObject) value);
                keys.addAll(list);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                if (jsonArray.size() != 0) {
                    Object obj = jsonArray.get(0);
                    // 对array中的类型进行判断
                    if (obj instanceof JSONObject) {
                        ArrayList<String> list = getJsonKeys(head == null ? key + "-" : head + key + "-", (JSONObject) jsonArray.get(0));
                        keys.addAll(list);
                    } else {
                        keys.add(head == null ? key : head + key);
                    }
                }
            }
        }
        return keys;
    }

    public static void main(String[] args) {
        try {
            // 开始对比
            //new ValidatorJson().compare(testJson, templetJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // String jsonStr = "{\"website\":\"open-open.com\"}";
        //String jsonStr = "{\"contactPersons\":[{\"name\":\"String,姓名\"},{\"name\":\"String,姓名\"}],\"contactEnterprise\":[{\"name\":\"String,姓名\"}],\"contactContent\":[{\"name\":\"String,姓名\"}]}";
        String jsonStr = "[{\"cnashu1\":\"1\";\"cnashu2\":\"2\";\"cnashu3\":\"3\";\"cnashu4\":\"4\";\"cnashu5\":\"5\"},{\"cnashu1\":\"6\";\"cnashu2\":\"7\";\"cnashu3\":\"8\";\"cnashu4\":\"9\";\"cnashu5\":\"10\"}]";
        System.out.println(jsonStr + ":" + new ValidatorJson().isValidate(jsonStr));
    }
}