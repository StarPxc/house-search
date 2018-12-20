package yuqiao.housesearch.craw.service;


import yuqiao.housesearch.house.entity.House;

import java.util.List;

/**
 * @author 浦希成
 * 2018-11-30
 */
public interface IHouseCrawService {
    /**
     * 爬取房屋信息
     *
     * @param city 城市名
     * @return 爬取总数
     */
    Integer crawHousesInfo(String city);

    /**
     * 获取租房详情
     * @param url 详情链接
     * @return House
     */
    House getDetailInfo(String url);

    /**
     * 判断信息是否已经存在
     * @param house House
     * @return true or false
     */
    boolean isExist(House house);

    /**
     * 启动爬虫
     */
    void start();

    /**
     * 插入数据库
     * @param house House
     */
    void insertIntoMysql(House house);


}
