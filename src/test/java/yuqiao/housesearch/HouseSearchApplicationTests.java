package yuqiao.housesearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import yuqiao.housesearch.craw.DanKeCrawService;
import yuqiao.housesearch.house.entity.House;
import yuqiao.housesearch.house.service.IHouseService;
import yuqiao.housesearch.house.service.impl.HouseServiceImpl;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseSearchApplicationTests {
    @Autowired
    private DanKeCrawService danKeCrawService;
    @Test
    public void contextLoads() {
        List<House> houses=danKeCrawService.getHouseInfo("hz");
        houses.forEach(house -> {
            System.out.println(house);
        });
    }

}
