package yuqiao.housesearch.common.enums;

/**
 * @author 浦希成
 * 2018-12-21
 */
public enum  HouseStatusEnum {
    /**
     * 房屋状态枚举
     */
    NORMAL(1,"正常"),
    LOWER(2,"下架"),
    DELETE(3,"删除")
    ,;

    private Integer code;
    private String msg;

    HouseStatusEnum(Integer code, String msg) {
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
