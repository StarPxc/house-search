package yuqiao.housesearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 浦希成
 */
@SpringBootApplication
@MapperScan("yuqiao.housesearch.mapper")
public class HouseSearchApplication {

    public static void main(String[] args)
    {
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(HouseSearchApplication.class, args);
    }
}
