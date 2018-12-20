package yuqiao.housesearch.craw.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import yuqiao.housesearch.house.entity.House;
import yuqiao.housesearch.house.mapper.HouseMapper;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 浦希成
 * 2018-12-15
 * 获取租房星期抽象类
 */
@Slf4j
@Component
public abstract class AbstractHouseCrawService implements IHouseCrawService {

    protected HouseMapper houseMapper;

    /**
     * 成功插入的总数
     */
    protected AtomicInteger sum = new AtomicInteger();

    @Override
    public void insertIntoMysql(House house) {
        try {
            if (!isExist(house)) {
                houseMapper.insert(house);
                log.info("mysql插入 {} 条数据：{}", sum.addAndGet(1), house);
            } else {
                log.info("数据已存在：{}", house);
            }
        } catch (Exception e) {
            log.error("insert error ：{}", e);
        }
    }

    /**
     * 启动爬虫
     */
    @Override
    public abstract void start();


    protected void start(String[] cities) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService es = new ThreadPoolExecutor(
                9,
                10,
                0L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                namedThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()

        );
        for (String city : cities) {
            es.submit(new InsertTask(city));
        }
    }

    class InsertTask extends Thread {
        private String city;

        InsertTask(String city) {
            this.city = city;
        }

        @Override
        public void run() {
            crawHousesInfo(city);
        }
    }
}
