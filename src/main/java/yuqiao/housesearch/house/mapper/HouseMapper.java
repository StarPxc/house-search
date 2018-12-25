package yuqiao.housesearch.house.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import yuqiao.housesearch.house.entity.House;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 房屋信息表 Mapper 接口
 * </p>
 *
 * @author 浦希成
 * @since 2018-12-01
 */
@Repository
public interface HouseMapper extends BaseMapper<House> {
    @Select("select * from house LIMIT #{start},#{size}")
    List<House> selectAll(@Param("start") int start, @Param("size") int size);
    @Select("select count(*) from house ")
    int countSum();
}
