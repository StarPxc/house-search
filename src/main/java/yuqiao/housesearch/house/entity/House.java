package yuqiao.housesearch.house.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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

    private String title;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 面积
     */
    private Integer area;

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
    private LocalDateTime createTime;

    /**
     * 最近数据更新时间
     */
    private LocalDateTime lastUpdateTime;

    /**
     * 省份
     */
    private Integer province;

    /**
     * 城市
     */
    private Integer city;

    /**
     * 区域
     */
    private Integer district;

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
     * 客厅数量
     */
    private Integer parlour;

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


}
