package yuqiao.housesearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import yuqiao.housesearch.house.mapper.HouseMapper;
import yuqiao.housesearch.security.user.mapper.UserMapper;

/**
 * @author 浦希成
 */
@SpringBootApplication
@MapperScan(basePackageClasses = {HouseMapper.class, UserMapper.class})
public class HouseSearchApplication {

    public static void main(String[] args)
    {
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(HouseSearchApplication.class, args);
    }
}
