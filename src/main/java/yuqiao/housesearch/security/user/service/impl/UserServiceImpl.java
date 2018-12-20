package yuqiao.housesearch.security.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yuqiao.housesearch.security.user.entity.User;
import yuqiao.housesearch.security.user.mapper.UserMapper;
import yuqiao.housesearch.security.user.service.IUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 浦希成
 * @since 2018-12-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
