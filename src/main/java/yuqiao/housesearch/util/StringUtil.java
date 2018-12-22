package yuqiao.housesearch.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 浦希成
 * 2018-12-21
 */
public class StringUtil {
    /**
     * 获取字符串中的数字
     * @param str 字符串
     * @return 数字字符串
     */
    public static String getNumber(String str){
        Pattern p = Pattern.compile("(\\d+\\.\\d+)");
        Matcher m = p.matcher(str);
        if (m.find()) {
            str = m.group(1) == null ? "" : m.group(1);
        } else {
            p = Pattern.compile("(\\d+)");
            m = p.matcher(str);
            if (m.find()) {
                str = m.group(1) == null ? "" : m.group(1);
            } else {
                str = "";
            }
        }
       return str;
    }

    /**
     * 根据正则表达式提取字符串
     * @param regex 正则表达式
     * @return 字符串
     */
    public static String getMatcher(String regex, String source){
        String result = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }
    public static void main(String[] args) {
        String source = "大三的撒 7/13层";
        String regex = "(\\d{1,3}/\\d{1,3})";
        System.out.println(getMatcher(regex,source));
    }
}
