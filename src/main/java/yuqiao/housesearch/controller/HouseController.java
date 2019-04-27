package yuqiao.housesearch.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yuqiao.housesearch.common.resp.ApiResult;
import yuqiao.housesearch.common.util.RestUtil;
import yuqiao.housesearch.form.CommonForm;
import yuqiao.housesearch.form.HouseConditionForm;
import yuqiao.housesearch.service.IHouseService;


@RestController
@RequestMapping("house")
@Api("房屋搜索")
public class HouseController {
    @Autowired
    private IHouseService houseService;

    @PostMapping("search")
    @ApiImplicitParam
    public ApiResult search(@RequestBody HouseConditionForm form) {
        return RestUtil.success("查询成功", houseService.getHouseEntitiesByCondition(form));
    }

    @PostMapping("search2")
    @ApiImplicitParam
    public ApiResult search2(@RequestBody HouseConditionForm form) {
        return RestUtil.success("查询成功", houseService.getAll(0, 100));
    }
    @PostMapping("searchValue")
    @ApiImplicitParam
    public ApiResult searchValue(@RequestBody CommonForm form) {
        return RestUtil.success("查询成功", houseService.searchValue(form));
    }


}
