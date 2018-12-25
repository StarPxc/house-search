package yuqiao.housesearch.es.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yuqiao.housesearch.es.service.EsAdminService;
import yuqiao.housesearch.house.entity.House;
import yuqiao.housesearch.house.service.IHouseService;

import java.util.List;

/**
 * @author 浦希成
 * 2018-12-22
 */
@RequestMapping("es/admin")
@RestController
public class AdminController {
    @Autowired
    private EsAdminService esAdminService;

    @GetMapping("/synchronousata")
    public Integer synchronousData(){

        return esAdminService.synchronousata();
    }
}
