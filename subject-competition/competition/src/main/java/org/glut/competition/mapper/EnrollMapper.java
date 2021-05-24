package org.glut.competition.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.glut.competition.entity.Enroll;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.glut.competition.service.Model.EnrollModel;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-23
 */
@Mapper
public interface EnrollMapper extends BaseMapper<Enroll> {
    List<EnrollModel> enrollView(String studentId);
}
