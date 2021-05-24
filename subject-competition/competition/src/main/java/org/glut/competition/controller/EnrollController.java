package org.glut.competition.controller;


import org.glut.competition.controller.viewobject.EnrollVO;
import org.glut.competition.entity.Contest;
import org.glut.competition.entity.Enroll;
import org.glut.competition.error.BusinessException;
import org.glut.competition.response.CommonReturnType;
import org.glut.competition.service.Model.EnrollModel;
import org.glut.competition.service.Model.UserModel;
import org.glut.competition.service.impl.ContestServiceImpl;
import org.glut.competition.service.impl.EnrollServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/enroll")
public class EnrollController {
    @Autowired
    EnrollServiceImpl enrollService;
    @Autowired
    ContestServiceImpl contestService;

    @Autowired
    HttpServletRequest request;

    @PostMapping(value = "/create")
    @ResponseBody
    public CommonReturnType create(@RequestParam(name = "studentId")String studentId, @RequestParam(name = "studentName")String studentName,
                                   @RequestParam(name = "studentClass")String studentClass, @RequestParam(name = "IDCard")String IDCard,
                                   @RequestParam(name = "instructor",required = false)String instructor, @RequestParam(name = "contestId")Long contestId,
                                   @RequestParam(name = "studentFaculty")String studentFaculty) throws BusinessException {
        Enroll enroll=new Enroll();
        enroll.setStudentId(studentId);
        enroll.setStudentName(studentName);
        enroll.setStudentFaculty(studentFaculty);
        enroll.setStudentClass(studentClass);
        enroll.setIdCard(IDCard);
        if (instructor!=null){
            enroll.setInstructor(instructor);
        }
        enroll.setContestId(contestId);

        enrollService.create(enroll);
        return CommonReturnType.create();
    }

    //点击按钮跳转报名
    @RequestMapping(value = "/create/{contestId}")
    public ModelAndView toCreate(@PathVariable long contestId) throws BusinessException {
        Contest contest= contestService.getContestById(contestId);
        ModelAndView modelAndView=new ModelAndView("enroll");
        modelAndView.addObject("contest",contest);
        return modelAndView;
    }

    @RequestMapping(value = "/count")
    @ResponseBody
    public CommonReturnType getCount(long contestId){
        int count = enrollService.getCount(contestId);
        return CommonReturnType.create(count);
    }

    @RequestMapping(value = "/enrolls")
    @ResponseBody
    public CommonReturnType enrollView(){
        UserModel user = (UserModel)this.request.getSession().getAttribute("LOGIN_USER");
        List<EnrollModel> enrollModels = enrollService.enrollView(user.getUserId());
        Iterator<EnrollModel> it=enrollModels.iterator();
        List<EnrollVO> enrollVOS=new ArrayList<>();
        while (it.hasNext()){
            EnrollVO enrollVO = convertFromModel(it.next());
            enrollVOS.add(enrollVO);
        }
        return CommonReturnType.create(enrollVOS);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestParam(name = "studentId")String studentId,@RequestParam(name = "contestId")long contestId){
        enrollService.delete(studentId,contestId);
        return CommonReturnType.create();
    }

    @RequestMapping("/")
    public String enroll(){
        return "enroll";
    }

    @RequestMapping("/view")
    public String enrolled(){
        return "enrollView";
    }

    private EnrollVO convertFromModel(EnrollModel enrollModel){
        EnrollVO enrollVO=new EnrollVO();
        BeanUtils.copyProperties(enrollModel,enrollVO);
        enrollVO.setContestName(enrollModel.getContest().getContestName());
        return enrollVO;
    }
}
