package yuqiao.housesearch.house.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import yuqiao.housesearch.es.entity.HouseEntity;
import yuqiao.housesearch.es.repository.HouseEntityRepository;
import yuqiao.housesearch.house.entity.House;
import yuqiao.housesearch.house.service.IHouseService;

/**
 * <p>
 * 房屋信息表 前端控制器
 * </p>
 *
 * @author 浦希成
 * @since 2018-12-01
 */
@RestController
@RequestMapping("/house")
public class HouseController {

}
