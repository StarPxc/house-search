package yuqiao.housesearch.service;

import yuqiao.housesearch.form.HouseConditionForm;
import yuqiao.housesearch.dto.ElasticsearchPageDTO;

/**
 * @author 浦希成
 * 2018-12-24
 */
public interface HouseEsService {
    /**
     * 条件查询
     * @return
     */
    ElasticsearchPageDTO getHouseEntitiesByCondition(HouseConditionForm form) throws Exception;
}
