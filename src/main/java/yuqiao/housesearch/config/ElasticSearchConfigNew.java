package yuqiao.housesearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

/**
 * @author 浦希成
 * 2018/11/19
 */
public class ElasticSearchConfigNew {
    public static void main(String[] args) {
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http")).build();
    }
}
