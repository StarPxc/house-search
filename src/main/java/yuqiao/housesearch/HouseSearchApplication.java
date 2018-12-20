package yuqiao.housesearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 浦希成
 */
@SpringBootApplication
@MapperScan("yuqiao.housesearch")
public class HouseSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseSearchApplication.class, args);
    }
}
