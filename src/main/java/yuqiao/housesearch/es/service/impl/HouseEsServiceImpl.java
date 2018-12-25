package yuqiao.housesearch.es.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuqiao.housesearch.common.enums.ErrorCode;
import yuqiao.housesearch.common.execption.BizException;
import yuqiao.housesearch.es.service.HouseEsService;
import yuqiao.housesearch.form.HouseConditionForm;
import yuqiao.housesearch.page.ElasticsearchPage;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author 浦希成
 * 2018-12-24
 */
@Service
public class HouseEsServiceImpl implements HouseEsService {

    @Autowired
    private TransportClient client;

    @Override
    public ElasticsearchPage getHouseEntitiesByCondition(HouseConditionForm form) throws Exception {
        if (form == null) {
            throw new BizException(ErrorCode.OBJECT_NULL_ERROR);
        }
        //构造查询条件
        QueryBuilder queryBuilder = getQueryBuilder(form);

        //执行查询
        SearchResponse searchResponse = client.prepareSearch("house")
                .setTypes("house").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(queryBuilder)
                .setFrom((form.getPageNum() - 1) * form.getPageSize())
                .setSize(form.getPageSize())
                .setExplain(true)
                .execute().actionGet();

        return getEsPage(searchResponse, form.getPageNum(), form.getPageSize());
    }

    private ElasticsearchPage getEsPage(SearchResponse response, int pageNum, int pageSize) {
        SearchHit[] searchHits = response.getHits().getHits();
        if (searchHits == null || searchHits.length == 0) {
            return new ElasticsearchPage();
        }
        int totalHits = (int) response.getHits().getTotalHits();
        List<JSONObject> results = Lists.newArrayList();
        for (SearchHit searchHit : searchHits) {
            Map<String, Object> source = searchHit.getSourceAsMap();
            results.add(new JSONObject(source));
        }
        return new ElasticsearchPage(pageNum, pageSize, totalHits, results);
    }

    private QueryBuilder getQueryBuilder(HouseConditionForm form) throws Exception {
        Map<String, String> map = BeanUtils.describe(form);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue())
                    && !"priceRange".equalsIgnoreCase(entry.getKey())
                    && !"areaRange".equalsIgnoreCase(entry.getKey())
                    && !"pageNum".equalsIgnoreCase(entry.getKey())
                    && !"pageSize".equalsIgnoreCase(entry.getKey())
                    && !"class".equalsIgnoreCase(entry.getKey())) {
                queryBuilder.filter(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
            }
        }
        //范围查询
        if (StringUtils.isNotBlank(form.getPriceRange())) {
            String text = form.getPriceRange();
            addRangeQuery(queryBuilder, text, "price");
        }
        if (StringUtils.isNotBlank(form.getAreaRange())) {
            String text = form.getPriceRange();
            addRangeQuery(queryBuilder, text, "area");
        }

        return queryBuilder;
    }

    private void addRangeQuery(BoolQueryBuilder queryBuilder, String text, String key) {
        int[] rangeNums = getRangeNums(text);
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(key).from(rangeNums[0]).to(rangeNums[1]);

        if ((text.contains("(") || text.contains("（"))) {
            rangeQuery.includeLower(false);
        }
        if((text.contains(")") || text.contains("）"))) {
            rangeQuery.includeUpper(false);
        }
        queryBuilder.filter(rangeQuery);
    }

    private int[] getRangeNums(String priceRange) {
        String[] temps = Pattern.compile("[^0-9，,]").matcher(priceRange).replaceAll("").split("[,，]");
        int[] result = new int[2];
        result[0] = Integer.parseInt(temps[0]);
        result[1] = Integer.parseInt(temps[1]);
        return result;
    }

    public static void main(String[] args) {
        HouseEsServiceImpl esService = new HouseEsServiceImpl();
        System.out.println(esService.getRangeNums("[1800，2000]")[1]);
    }


}
