package yuqiao.housesearch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import yuqiao.housesearch.entity.House;
import yuqiao.housesearch.form.CommonForm;
import yuqiao.housesearch.form.HouseConditionForm;

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

    /**
     * genuine条件获取房屋信息
     * @param form form
     * @return list
     */
    IPage getHouseEntitiesByCondition(HouseConditionForm form);

    IPage  searchValue(CommonForm queryValue);
}
