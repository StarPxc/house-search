package yuqiao.housesearch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 房屋信息表
 * </p>
 *
 * @author 浦希成
 * @since 2018-12-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class House implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * house唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    private Float area;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近数据更新时间
     */
    private Date lastUpdateTime;

    /**
     * 最近数据更新时间
     */
    private Date crawTime;

    /**
     * 省份
     */
    private String province;

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
     * 爬取地址
     */
    private String url;

    /**
     * 图片地址
     */
    private String imgUrls;




}
