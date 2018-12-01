package yuqiao.housesearch.common.enums;

/**
 * @author 浦希成
 * 2018-12-01
 */
public enum OriginEnun {
    /**
     * 来源枚举
     */
    DANKE(1,"蛋壳")
    ,;

    private Integer code;
    private String msg;

    OriginEnun(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
