package yuqiao.housesearch.form;


import lombok.Data;

/**
 * @author 浦希成
 * 2018-12-24
 */
@Data
public class HouseConditionForm extends BaseForm{

    /**
     * 标题
     */
    private String title;

    /**
     * 价格区间
     * [800,1200]
     */
    private String priceRange;

    /**
     * 面积区间
     * [9,12]
     */
    private String areaRange;

    /**
     * 户型
     */
    private String type;

    /**
     * 楼层
     */
    private String floor;


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
     * 来源
     */
    private String origin;



}
