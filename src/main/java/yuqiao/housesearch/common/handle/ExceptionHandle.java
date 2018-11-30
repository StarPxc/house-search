package yuqiao.housesearch.common.handle;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import yuqiao.housesearch.common.enums.ResultEnum;
import yuqiao.housesearch.common.execption.BizException;
import yuqiao.housesearch.common.resp.ApiResult;
import yuqiao.housesearch.util.RestUtil;

/**
 * @author Ethanp
 * @version V1.0
 * @Description: 全局的异常处理
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResult handle(Exception e) {
        //如果是自定义异常
        if (e instanceof BizException) {
            BizException exception = (BizException) e;
            log.error(exception.getMessage());
            return RestUtil.error(exception.getCode(), exception.getMessage());
        } else if (e instanceof MultipartException) {
            return RestUtil.error(ResultEnum.FILE_TOO_BIG.getCode(), ResultEnum.FILE_TOO_BIG.getMsg());
        } else {
            log.error("【系统异常】", e);
            return RestUtil.error(ResultEnum.UNKONW_ERROR.getCode(), ResultEnum.UNKONW_ERROR.getMsg());
        }

    }
}
