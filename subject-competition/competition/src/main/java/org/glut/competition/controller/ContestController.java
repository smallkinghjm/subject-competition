package org.glut.competition.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.glut.competition.controller.viewobject.ContestVO;
import org.glut.competition.entity.Contest;
import org.glut.competition.entity.Enclosure;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.response.CommonReturnType;
import org.glut.competition.service.Model.ContestModel;
import org.glut.competition.service.impl.ContestServiceImpl;
import org.glut.competition.service.impl.EnclosureServiceImpl;
import org.glut.competition.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-23
 */
@Controller
//@RequestMapping("/contest")
public class ContestController {
    @Autowired
    ContestServiceImpl contestService;

    @Autowired
    EnclosureServiceImpl enclosureService;

    DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");//日期格式

    @RequestMapping("/admin/contest/create-page")
    @ResponseBody
    public CommonReturnType create(@RequestParam(name = "contestName")String contestName, @RequestParam(name = "startTime")String startTime,
                                   @RequestParam(name = "endTime")String endTime, @RequestParam(name = "contestContent")String contestContent,
                                   @RequestParam(name = "leader")String leader,@RequestParam(name = "enclosureName")String enclosureName) throws BusinessException {
        //日期格式转换
        LocalDate contestStart=LocalDate.parse(startTime,dateTimeFormatter);
        LocalDate contestEnd=LocalDate.parse(endTime,dateTimeFormatter);
        if (contestStart.isAfter(contestEnd)||contestEnd.isBefore(LocalDate.now())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"赛事开始时间与结束时间不符合实际");
        }

        ContestModel contestModel=new ContestModel();
        contestModel.setContestName(contestName);
        contestModel.setContestStart(contestStart);
        contestModel.setContestEnd(contestEnd);
        contestModel.setContestContent(contestContent);
        contestModel.setLeader(leader);
        if (!enclosureName.equals("")){
            contestModel.setEnclosureName(enclosureName);
        }
        contestService.create(contestModel);
        return CommonReturnType.create();
    }

    @RequestMapping(value = "admin/contest/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestParam(name = "contestId")long contestId) throws BusinessException {
        Contest contest = contestService.getContestById(contestId);
        try {
            if (contest.getEnclosureName()!=null&&!contest.getEnclosureName().equals("")){
                Enclosure enclosure = enclosureService.getEnclosure(contest.getEnclosureName());
                enclosureService.delete(enclosure.getEnclosureName());
                enclosureService.deleteFile(enclosure.getEnclosureContent());
            }
        } catch (BusinessException e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"删除错误，请检查字段是否正确");
        }
        contestService.delete(contestId);
        return CommonReturnType.create();
    }

    @RequestMapping(value = "/admin/contests")
    @ResponseBody
    public CommonReturnType contestPage(@RequestParam(value = "currentPage" ,required = false, defaultValue = "1")int currentPage,
                                        @RequestParam(value = "limit" ,required = false,defaultValue = "10")int limit){
        Page<Contest> contestPage = contestService.contestPage(currentPage, limit);
        return CommonReturnType.create(contestPage);

    }

    private Page<ContestVO> convertFromModelPage(Page<ContestModel> contestModelPage){
        Page<ContestVO> contestVOPage=new Page<>();
        BeanUtils.copyProperties(contestModelPage,contestVOPage);
        return contestVOPage;
    }

    private ContestVO convertFormModel(ContestModel contestModel){
        ContestVO contestVO=new ContestVO();
        BeanUtils.copyProperties(contestModel,contestVO);
        return contestVO;
    }

    @RequestMapping(value = "/admin/contest/update/{contestId}")
    public ModelAndView show(@PathVariable(value = "contestId")long contestId) throws BusinessException {
        Contest contest = contestService.getContestById(contestId);
        ModelAndView modelAndView=new ModelAndView("admin/contestUpdate");
        Enclosure enclosure = new Enclosure();
        if (contest.getEnclosureName()!=null){
            enclosure = enclosureService.getEnclosure(contest.getEnclosureName());
        }else {
            enclosure.setEnclosureName("无附件");
        }
        modelAndView.addObject("enclosure",enclosure);
        modelAndView.addObject("contest",contest);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/contest/saveUpdate")
    public CommonReturnType update(@RequestParam(name = "contestId")long contestId,@RequestParam(name = "contestName")String contestName, @RequestParam(name = "startTime")String startTime,
                                   @RequestParam(name = "endTime")String endTime, @RequestParam(name = "contestContent")String contestContent,
                                   @RequestParam(name = "leader")String leader,@RequestParam(name = "enclosureName",required = false)String enclosureName) throws BusinessException {


        LocalDate contestStart=LocalDate.parse(startTime,dateTimeFormatter);
        LocalDate contestEnd=LocalDate.parse(endTime,dateTimeFormatter);
        if (contestStart.isAfter(contestEnd)||contestEnd.isBefore(LocalDate.now())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"赛事开始时间与结束时间不符合实际");
        }
        Contest contest = contestService.getContestById(contestId);
        contest.setContestName(contestName);
        contest.setContestContent(contestContent);
        contest.setContestStart(contestStart);
        contest.setContestEnd(contestEnd);
        if (!enclosureName.equals("")){
            contest.setEnclosureName(enclosureName);
        }
        contestService.update(contest);
        return CommonReturnType.create();
    }

    @RequestMapping(value = "/contest/page")
    @ResponseBody
    public CommonReturnType contests(@RequestParam(name = "currentPage",required = false,defaultValue = "1")int currentPage,
                                 @RequestParam(name = "limit",required = false,defaultValue = "20")int limit){
        Page<Contest> contestPage = contestService.contestPage(currentPage, limit);
        Map<String,Object> map=new HashMap<>();
        map.put("currentPage",contestPage.getCurrent());
        map.put("total",contestPage.getTotal());
        map.put("pages",contestPage.getPages());
        map.put("contests",contestPage.getRecords());
        return CommonReturnType.create(map);
    }

    @RequestMapping(value = "/contest/view/{contestId}")
    public ModelAndView view(@PathVariable long contestId) throws BusinessException {
        Contest contest = contestService.getContestById(contestId);
        ModelAndView modelAndView=new ModelAndView("contestView");
        modelAndView.addObject("contest",contest);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/contest/create")
    public String toCreate(){
        return "admin/contestCreate";
    }

    @RequestMapping(value = "/admin/contest")
    public String adminContest(){
        return "admin/contest";
    }

    @RequestMapping(value = "/contest")
    public String contest(){
        return "contest";
    }

}
