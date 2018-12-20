package yuqiao.housesearch.craw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yuqiao.housesearch.craw.service.IHouseCrawService;

/**
 * @author 浦希成
 * 2018-12-15
 */
@RestController
@RequestMapping("/craw")
public class CrawController {
    @Autowired
    @Qualifier("danKeHouseCrawServiceImpl")
    private IHouseCrawService houseCrawService;
    @RequestMapping("/test")
    public String test(){
        houseCrawService.start();
        return "";
    }
}
