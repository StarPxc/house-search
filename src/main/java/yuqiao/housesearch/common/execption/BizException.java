package yuqiao.housesearch.common.execption;


import yuqiao.housesearch.common.enums.ErrorCode;

/**
 * @author Ethanp
 * @date 2018/1/29 10:20
 * 自定义异常类
 */
public class BizException extends RuntimeException {
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }
}
