package yuqiao.housesearch.common.util;


import yuqiao.housesearch.common.enums.ErrorCode;
import yuqiao.housesearch.common.resp.ApiResult;

/**
 * @author 浦希成
 */
public class RestUtil {
    public static ApiResult success() {
        return success(null, null);
    }

    public static ApiResult success(String msg, Object data) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(200);
        apiResult.setMsg(msg);
        apiResult.setData(data);
        return apiResult;
    }

    public static ApiResult success(String msg) {
        return success(msg, null);
    }

    public static ApiResult error(Integer code, String msg) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        return apiResult;
    }
    public static ApiResult error(ErrorCode codeEnum) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(codeEnum.getCode());
        apiResult.setMsg(codeEnum.getMsg());
        return apiResult;
    }


}
