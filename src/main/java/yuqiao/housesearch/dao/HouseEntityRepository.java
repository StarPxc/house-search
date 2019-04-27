package yuqiao.housesearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;
import yuqiao.housesearch.entity.HouseEntity;

/**
 * @author 浦希成
 * 2018-12-21
 */
@Repository
public interface HouseEntityRepository extends ElasticsearchCrudRepository<HouseEntity,Integer> {
}
