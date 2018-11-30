package yuqiao.housesearch.common.constants;

/**
 * @author 浦希成
 * @version V1.0
 * @date 2018/2/7 12:16
 */
public class Constants {
    /**
     * 教务系统非vpn登录路径
     */
    public static final String LOGIN_TO_JWGL_NORMAL = "http://jwgl.just.edu.cn:8080/jsxsd/xk/LoginToXk";
    /**
     * 教务系统vpn登录路径
     */
    public static final String LOGIN_TO_JWGL_VPN = "https://vpn.just.edu.cn/jsxsd/xk/,DanaInfo=jwgl.just.edu.cn,Port=8080+LoginToXk";
    /**
     * 学生信息(非vpn)
     */
    public static final String STUDENT_INFO = "http://jwgl.just.edu.cn:8080/jsxsd/grxx/xsxx?Ves632DSdyV=NEW_XSD_XJCJ";
    /**
     * vpn账号登录路径
     */
    public static final String LOGIN_TO_VPN = "https://vpn.just.edu.cn/dana-na/auth/url_default/login.cgi";
    /**
     * 成绩列表(非vpn)
     */
    public static final String SCORE_NORMAL = "http://jwgl.just.edu.cn:8080/jsxsd/kscj/cjcx_list";
}
