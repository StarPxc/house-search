package yuqiao.housesearch.house.service;

import yuqiao.housesearch.house.entity.House;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 房屋信息表 服务类
 * </p>
 *
 * @author 浦希成
 * @since 2018-12-01
 */
public interface IHouseService extends IService<House> {

    List<House> getAll(int start,int size);

    int countSum();
}
