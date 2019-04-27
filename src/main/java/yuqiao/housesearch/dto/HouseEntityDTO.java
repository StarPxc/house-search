package yuqiao.housesearch.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author 浦希成
 * 2018-12-24
 */
@Data
public class HouseEntityDTO {

    private Integer id;

    /**
     * 编号
     */
    private String number;

    /**
     * 标题
     */
    private String title;

    /**
     * 价格
     */
    private String price;

    /**
     * 面积
     */
    private String area;

    /**
     * 户型
     */
    private String type;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 总楼层
     */
    private Integer totalFloor;

    /**
     * 被看次数
     */
    private Integer watchTimes;

    /**
     * 建立年限
     */
    private Integer buildYear;

    /**
     * 房屋状态 0-正常 1-下架 2-删除
     */
    private Integer status;


    /**
     * 最近数据更新时间
     */
    private Date crawTime;


    /**
     * 城市
     */
    private String city;

    /**
     * 区域
     */
    private String district;

    /**
     * 街道
     */
    private String street;

    /**
     * 房屋朝向
     */
    private String direction;

    /**
     * 地铁信息
     */
    private String subway;

    /**
     * 所在小区
     */
    private String community;

    /**
     * 标签
     */
    private String tag;

    /**
     * 来源
     */
    private Integer origin;


    /**
     * 图片地址
     */
    private String imgUrls;

    public String getPrice() {
        return price+"元";
    }

    public String getArea() {
        return area+"㎡";
    }
}
