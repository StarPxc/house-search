package yuqiao.housesearch.security.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 浦希成
 * @since 2018-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String nickname;

    private String password;

    private String originalPassword;

    private String email;

    private String phone;

    private Integer roleId;

    private String authors;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
