package yuqiao.housesearch.service;

/**
 * @author 浦希成
 * 2018-12-22
 */
public interface EsAdminService {
    /**
     * 从mysql数据库同步数据到es
     * @return 数据总数
     */
     Integer synchronousata();
}
