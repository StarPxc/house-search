package yuqiao.housesearch.craw.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import yuqiao.housesearch.craw.service.IHouseCrawService;

import static org.junit.Assert.*;

/**
 * @author 浦希成
 * 2018-12-15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DanKeHouseCrawServiceImplTest {
    @Autowired
    @Qualifier("danKeHouseCrawServiceImpl")
    private IHouseCrawService service;

    @Test
    public void testInsert() {
        service.start();
    }
}