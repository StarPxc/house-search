package yuqiao.housesearch.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuqiao.housesearch.entity.HouseEntity;
import yuqiao.housesearch.dao.HouseEntityRepository;
import yuqiao.housesearch.service.EsAdminService;
import yuqiao.housesearch.entity.House;
import yuqiao.housesearch.service.IHouseService;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 浦希成
 * 2018-12-22
 */
@Service
@Slf4j
public class EsAdminServiceImpl implements EsAdminService {
    @Autowired
    private IHouseService houseService;
    @Autowired
    private HouseEntityRepository houseEntityRepository;
    private AtomicInteger sum = new AtomicInteger(0);

    @Override
    public Integer synchronousata() {
        log.info("开始同步");
        long startTime=System.currentTimeMillis();
        int count=houseService.countSum();
        int size=100;
        int corePoolSize=count%size==0?count/size:count/size+1;
        CountDownLatch latch=new CountDownLatch(corePoolSize);
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService es = new ThreadPoolExecutor(
                corePoolSize,
                corePoolSize,
                0L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                namedThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()

        );
        for (int i = 0; i < corePoolSize; i++) {
            int start=i;
            es.submit(() -> {
                List<House> houses = houseService.getAll(start*size,size);
                execute(houses);
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("共耗时 {} 毫秒，同步 {} 条数据",System.currentTimeMillis()-startTime,sum.get());
        return sum.get();
    }

    private void execute(List<House> houses) {
        for (House house : houses) {
            HouseEntity entity = new HouseEntity();
            BeanUtils.copyProperties(house, entity);
            houseEntityRepository.save(entity);
            log.info("同步 {} 条数据", sum.addAndGet(1));
        }
    }
}
