package yuqiao.housesearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import yuqiao.housesearch.house.mapper.HouseMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseSearchApplicationTests {
    @Autowired
    private HouseMapper mapper;

    @Test
    public void contextLoads() {
        System.out.println(mapper.selectById(15));
    }

}
