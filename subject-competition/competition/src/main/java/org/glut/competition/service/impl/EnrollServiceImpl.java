package org.glut.competition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.glut.competition.entity.Enroll;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.mapper.EnrollMapper;
import org.glut.competition.service.IEnrollService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.glut.competition.service.Model.EnrollModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-23
 */
@Service
public class EnrollServiceImpl extends ServiceImpl<EnrollMapper, Enroll> implements IEnrollService {

    @Autowired
    EnrollMapper enrollMapper;
    @Override
    public void create(Enroll enroll) throws BusinessException {
        //通过学号和项目编号唯一性验证防止重复报名
        Enroll enroll1 = getEnroll(enroll.getStudentId());
        if(enroll1!=null&&enroll1.getContestId()==enroll.getContestId()){
            throw new BusinessException(EmBusinessError.DATA_ERROR,"该项目您已经报名");
        }
        enrollMapper.insert(enroll);
    }

    @Override
    public Enroll getEnroll(String studentId) throws BusinessException {
        if (studentId.equals("")){
            throw new BusinessException(EmBusinessError.DATA_ISEMPTY);
        }
        Enroll enroll = enrollMapper.selectOne(new QueryWrapper<Enroll>().eq("student_id", studentId));
        return enroll;
    }

    @Override
    public Page<Enroll> getEnrollPage(long contestId,int currentPage, int limit) {
        QueryWrapper<Enroll> wrapper=new QueryWrapper<>();
        wrapper.eq("contest_id",contestId);
        Page<Enroll> enrollPage = enrollMapper.selectPage(new Page<>(currentPage, limit), wrapper);
        return enrollPage;
    }

    @Override
    public int getCount(long contestId) {
        Integer count = enrollMapper.selectCount(new QueryWrapper<Enroll>().eq("contest_id", contestId));
        return count;
    }

    @Override
    public List<EnrollModel> enrollView(String studentId) {
        List<EnrollModel> enrollModels = enrollMapper.enrollView(studentId);
        return enrollModels;
    }

    @Override
    public void delete(String studentId, long contestId) {
        QueryWrapper<Enroll> wrapper=new QueryWrapper<>();
        wrapper.eq("student_id",studentId);
        wrapper.eq("contest_id",contestId);
        enrollMapper.delete(wrapper);
    }

}
