package yuqiao.housesearch.es.service;

import yuqiao.housesearch.form.HouseConditionForm;
import yuqiao.housesearch.page.ElasticsearchPage;

/**
 * @author 浦希成
 * 2018-12-24
 */
public interface HouseEsService {
    /**
     * 条件查询
     * @return
     */
    ElasticsearchPage getHouseEntitiesByCondition(HouseConditionForm form) throws Exception;
}
