package yuqiao.housesearch.common.execption;


import yuqiao.housesearch.common.enums.ResultEnum;

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

    public BizException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
