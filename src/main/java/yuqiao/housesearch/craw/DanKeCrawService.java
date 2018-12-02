package yuqiao.housesearch.craw;

import yuqiao.housesearch.house.entity.House;

import java.util.List;

/**
 * @author 浦希成
 * 2018-11-30
 */
public interface DanKeCrawService {
    /**
     * 获取房屋信息
     * @param city
     * @return
     */
     List<House> getHouseInfo(String city) ;

}
