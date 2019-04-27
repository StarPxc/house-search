package yuqiao.housesearch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import yuqiao.housesearch.entity.House;

import java.util.List;

/**
 * <p>
 * 房屋信息表 Mapper 接口
 * </p>
 *
 * @author 浦希成
 * @since 2018-12-01
 */
public interface HouseMapper extends BaseMapper<House> {

    List<House> selectAll(@Param("start") int start, @Param("size") int size);

    @Select("select count(*) from house ")
    int countSum();

}
