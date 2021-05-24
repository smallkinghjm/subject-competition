package org.glut.competition.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.glut.competition.entity.Event;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-20
 */
@Mapper
public interface EventMapper extends BaseMapper<Event> {

}
