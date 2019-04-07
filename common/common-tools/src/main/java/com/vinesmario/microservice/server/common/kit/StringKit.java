package com.vinesmario.microservice.server.common.kit;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringKit {

    /**
     * 下划线转驼峰法
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param camel 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String camel) {
        if (StringUtils.isBlank(camel)) {
            return "";
        }
        camel = String.valueOf(camel.charAt(0)).toUpperCase().concat(camel.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(camel);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == camel.length() ? "" : "_");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String line = "I_HAVE_AN_IPANG3_PIG";
        //String camel = underline2Camel(line, true);
        //System.out.println(camel);
        String  value =  "create_date  ";
        System.out.println(camel2Underline(value).toLowerCase());
    }

}
