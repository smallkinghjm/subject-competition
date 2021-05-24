package org.glut.competition.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.glut.competition.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int userSaveBatch(List<User> users);
}
