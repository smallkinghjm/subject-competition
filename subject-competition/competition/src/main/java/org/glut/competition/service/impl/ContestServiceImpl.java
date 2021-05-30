package org.glut.competition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.glut.competition.entity.Contest;
import org.glut.competition.entity.Event;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.mapper.ContestMapper;
import org.glut.competition.service.IContestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.glut.competition.service.Model.ContestModel;
import org.glut.competition.validator.ValidationResult;
import org.glut.competition.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-23
 */
@Service
public class ContestServiceImpl extends ServiceImpl<ContestMapper, Contest> implements IContestService {

    @Autowired
    ContestMapper contestMapper;

    @Autowired
    ValidatorImpl validator;

    @Override
    @Transactional
    public void create(ContestModel contestModel) throws BusinessException {
        ValidationResult result = validator.validate(contestModel);
        if (result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        Contest contest=new Contest();
        BeanUtils.copyProperties(contestModel,contest);
        contestMapper.insert(contest);
    }

/*    @Override
    public List<ContestModel> getAll() {
        List<Contest> contests = contestMapper.selectList(new QueryWrapper<Contest>());
        List<ContestModel> contestModels=new ArrayList<>();
        for (Contest contest:contests) {
            ContestModel contestModel1 = convertFromDataObject(contest);
            getState(contest.getContestStart(),contest.getContestEnd(),contestModel1);
            contestModels.add(contestModel1);
        }
        return contestModels;
    }*/

/*    @Override
    public Page<ContestModel> contestPage(int currentPage, int limit) {
        Page<Contest> contestPage = contestMapper.selectPage(new Page<>(currentPage, limit), null);
        Page<ContestModel> contestModelPage = convertPageFromDataObject(contestPage);
        return contestModelPage;
    }*/

    //分页显示
    @Override
    public Page<Contest> contestPage(int currentPage, int limit) {
        QueryWrapper<Contest> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("contest_id");
        Page<Contest> contestPage = contestMapper.selectPage(new Page<>(currentPage, limit), wrapper);
        for (Contest contest:contestPage.getRecords()) {
            contest.setState(getState(contest.getContestStart(),contest.getContestEnd(),contest));
        }
        return contestPage;
    }

    @Override
    public Contest getContestById(long contestId) throws BusinessException {
        if (contestId==0){
            throw new BusinessException(EmBusinessError.DATA_ISEMPTY,"该值为0，不合法");
        }
        Contest contest = contestMapper.selectById(contestId);
        if (contest==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        return contest;
    }

    @Override
    public void update(Contest contest) throws BusinessException {
        try {
            contestMapper.update(contest,new UpdateWrapper<Contest>().eq("contest_id",contest.getContestId()));
        }catch (Exception exception){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"更新失败");
        }
    }

    @Override
    public void delete(long contestId) {
        contestMapper.delete(new QueryWrapper<Contest>().eq("contest_id",contestId));
    }

    @Override
    public void deleteEnclosure(String fileName) throws BusinessException {
        UpdateWrapper<Contest> wrapper=new UpdateWrapper<>();
        wrapper.eq("enclosure_name",fileName);
        Contest contest=new Contest();
        contest.setEnclosureName("");
        int update = contestMapper.update(contest, wrapper);
        if (update==0){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"删除失败");
        }
    }


/*    private Page<ContestModel>  convertPageFromDataObject(Page<Contest> contestPage){
        Page<ContestModel> contestModelPage=new Page<>();
        BeanUtils.copyProperties(contestPage,contestModelPage);//在另一个方法中执行完成了selectPage操作,通过工具类复制到model中
        System.out.println(contestModelPage.getTotal());//无错
        List<ContestModel> records = contestModelPage.getRecords();
        System.out.println(records);//无错
        System.out.println(records.get(1));//无错
        System.out.println(records.get(1).getContestId());//出错(contestId是数据库中的一个字段)java.lang.ClassCastException
        for (ContestModel contestModel:records) {//这里出错了，records可以输出，但是不能获取当中的任何属性
            LocalDate contestStart = contestModel.getContestStart();
            LocalDate contestEnd = contestModel.getContestEnd();
            String state = getState(contestStart, contestEnd, contestModel);
            contestModel.setState(state);
        }
        return contestModelPage;
    }*/


/*    private ContestModel convertFromDataObject(Contest contest){
        ContestModel contestModel=new ContestModel();
        BeanUtils.copyProperties(contest,contestModel);
        LocalDate contestStart = contest.getContestStart();
        LocalDate contestEnd = contest.getContestEnd();
        String state = getState(contestStart, contestEnd, contestModel);
        contestModel.setState(state);
        return contestModel;
    }*/
    //根据当时间显示赛事状态
    private String getState(LocalDate dateStart,LocalDate dateEnd, Contest contest){
        LocalDate now = LocalDate.now();
        if (dateStart.isAfter(now)){
            contest.setState("未开始");
        }else if (dateStart.isBefore(now)&&dateEnd.isAfter(now)){
            contest.setState("进行中");
        }else {
            contest.setState("已结束");
        }
        return contest.getState();
    }

}
