package yuqiao.housesearch.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yuqiao.housesearch.service.HouseEsService;
import yuqiao.housesearch.form.HouseConditionForm;
import yuqiao.housesearch.dto.ElasticsearchPageDTO;

/**
 * @author 浦希成
 * 2018-12-24
 */
@RestController
@RequestMapping("es/house")
@Api("es房屋搜索")
public class HouseEsController {
    @Autowired
    private HouseEsService houseEsService;
    @PostMapping("search")
    @ApiImplicitParam
    public ElasticsearchPageDTO search(@RequestBody HouseConditionForm form) throws Exception {
        return houseEsService.getHouseEntitiesByCondition(form);
    }

}
