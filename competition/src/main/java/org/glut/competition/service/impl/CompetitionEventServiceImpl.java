package org.glut.competition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.glut.competition.entity.CompetitionEvent;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.mapper.CompetitionEventMapper;
import org.glut.competition.service.ICompetitionEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.glut.competition.validator.ValidationResult;
import org.glut.competition.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-12
 */
@Service
public class CompetitionEventServiceImpl extends ServiceImpl<CompetitionEventMapper, CompetitionEvent> implements ICompetitionEventService {

    /*@Autowired
    CompetitionEventMapper competitionEventMapper;

    @Autowired
    ValidatorImpl validator;

    @Override
    public CompetitionEvent read(String competitionName) throws BusinessException {
        CompetitionEvent competitionEvent = competitionEventMapper.selectOne(new QueryWrapper<CompetitionEvent>().eq("competition_name", competitionName));
        if (competitionEvent==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"该赛事不存在");
        }
        return competitionEvent;


    }

    @Override
    public void release(CompetitionEvent competitionEvent) throws BusinessException {
        if (competitionEvent==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"发布赛事的参数不合法");
        }
        ValidationResult result = validator.validate(competitionEvent);
        if (result.isHasErrors()){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"插入数据有误");
        }
        competitionEventMapper.insert(competitionEvent);
    }
*/

}
