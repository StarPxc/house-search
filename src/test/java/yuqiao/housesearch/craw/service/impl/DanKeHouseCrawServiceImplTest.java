package yuqiao.housesearch.craw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import yuqiao.housesearch.entity.House;
import yuqiao.housesearch.form.HouseConditionForm;
import yuqiao.housesearch.mapper.HouseMapper;

import java.util.List;

/**
 * @author 浦希成
 * 2018-12-15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DanKeHouseCrawServiceImplTest {

    @Autowired
    private HouseMapper houseMapper;

    @Test
    public void pxc() {
        HouseConditionForm form = new HouseConditionForm();
        form.setCity("杭州");
        form.setPageNum(2);
        form.setPageSize(20);
        House house = new House();
        BeanUtils.copyProperties(form, house);
        List<House> houses = houseMapper.selectList(new QueryWrapper<>(house));

        IPage page = new Page(form.getPageNum(), form.getPageSize());
        IPage page1 = houseMapper.selectPage(page, new QueryWrapper<>(house));
        System.out.println(page1);

    }
}