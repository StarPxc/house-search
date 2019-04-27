package yuqiao.housesearch.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import yuqiao.housesearch.entity.House;
import yuqiao.housesearch.form.CommonForm;
import yuqiao.housesearch.form.HouseConditionForm;
import yuqiao.housesearch.mapper.HouseMapper;
import yuqiao.housesearch.service.IHouseService;

import java.util.List;

/**
 * <p>
 * 房屋信息表 服务实现类
 * </p>
 *
 * @author 浦希成
 * @since 2018-12-01
 */
@Service
@Primary
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements IHouseService {
    @Autowired
    private HouseMapper houseMapper;

    @Override
    public List<House> getAll(int start, int size) {
        return houseMapper.selectAll(start, size);
    }

    @Override
    public int countSum() {
        return houseMapper.countSum();
    }

    public IPage<House> getHouseEntitiesByCondition(HouseConditionForm form) {
        IPage<House> page = new Page<>(form.getPageNum(), form.getPageSize());
        House house = new House();
        BeanUtils.copyProperties(form, house);
        Wrapper<House> wrapper = new QueryWrapper<>(house);
        return houseMapper.selectPage(page, wrapper);
    }

    @Override
    public IPage searchValue(CommonForm form) {
        QueryWrapper<House> wrapper = new QueryWrapper<>();
        wrapper.eq(form.getQueryValue()!=null,"city", form.getQueryValue())
                .or()
                .eq(form.getQueryValue()!=null,"title", form.getQueryValue())
                .or()
                .eq(form.getQueryValue()!=null,"number", form.getQueryValue());
        return houseMapper.selectPage(new Page<>(form.getPageNum(), form.getPageSize()), wrapper);
    }


}


