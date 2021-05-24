package org.glut.competition.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.glut.competition.controller.viewobject.EventVO;
import org.glut.competition.entity.*;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.response.CommonReturnType;
import org.glut.competition.service.Model.EventModel;
import org.glut.competition.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    ContestServiceImpl contestService;

    @Autowired
    EventServiceImpl eventService;

    @Autowired
    EnclosureServiceImpl enclosureService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    EnrollServiceImpl enrollService;

    @Autowired
    HttpServletRequest request;


    @PostMapping("/login")
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "name")String name,@RequestParam(name = "password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(name)||StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        adminService.login(name,password);
        request.getSession().setAttribute("IS_LOGIN","true");
        return CommonReturnType.create();
    }


    //公告，赛果创建
    @RequestMapping(value = "/event/create-page")
    @ResponseBody
    public CommonReturnType create(@RequestParam(name = "title")String title, @RequestParam(name = "type")Integer type,
                                   @RequestParam(name = "content")String content,@RequestParam(name = "enclosureName",required = false)String enclosureName){
        eventService.create(title,type,content,enclosureName);
        return CommonReturnType.create();
    }

    //
    @RequestMapping(value = "/home")
    public String home(){
        return "admin/home";
    }

    @RequestMapping(value = "/event")
    public ModelAndView event(@RequestParam(name = "type")int type){
        ModelAndView modelAndView=new ModelAndView("admin/event");
        modelAndView.addObject("type",type);
        return modelAndView;
    }

    @GetMapping("/login-page")
    public String toLogin(){
        return "admin/login";
    }

    @RequestMapping("/events")
    @ResponseBody
    public CommonReturnType eventPage(@RequestParam(name = "type")int type,@RequestParam(required = false, defaultValue = "1")int currentPage,
                                      @RequestParam(required = false,defaultValue = "10")int limit){
        Page<Event> eventPage = eventService.getEvents(type, currentPage, limit);
        Map<String,Object> map=new HashMap<>();
        map.put("currentPage",eventPage.getCurrent());
        map.put("total",eventPage.getTotal());
        map.put("pages",eventPage.getPages());
        map.put("events",eventPage.getRecords());
        return CommonReturnType.create(map);
    }

    @RequestMapping(value = "/event/update/{eventId}")
    public ModelAndView show(@PathVariable("eventId") long eventId) throws BusinessException {
        EventVO eventVO = eventService.getEventContent(eventId);
        ModelAndView modelAndView=new ModelAndView("admin/eventUpdate");
        Enclosure enclosure = new Enclosure();
        if (eventVO.getEnclosureName()!=null){
            enclosure = enclosureService.getEnclosure(eventVO.getEnclosureName());
        }else {
            enclosure.setEnclosureName("无附件");
        }
        modelAndView.addObject("enclosure",enclosure);
        modelAndView.addObject("event",eventVO);
        return modelAndView;
    }

    @RequestMapping(value = "/event/saveUpdate")
    @ResponseBody
    public CommonReturnType update(@RequestParam(name = "eventId")long eventId,@RequestParam(name = "title")String title, @RequestParam(name = "type")Integer type,
                                   @RequestParam(name = "content")String content) throws BusinessException {
        eventService.update(eventId,title,type,content);
        return CommonReturnType.create();
    }

    @RequestMapping(value = "/enroll/page")
    @ResponseBody
    public CommonReturnType contestEnroll(@RequestParam(name = "currentPage",required = false,defaultValue = "1")int currentPage,
                                      @RequestParam(name = "limit",required = false,defaultValue = "15")int limit){
        Page<Contest> contestPage = contestService.contestPage(currentPage, limit);
        Map<String,Object> map=new HashMap<>();
        map.put("currentPage",contestPage.getCurrent());
        map.put("total",contestPage.getTotal());
        map.put("pages",contestPage.getPages());
        map.put("contests",contestPage.getRecords());
        return CommonReturnType.create(map);
    }

    @RequestMapping(value = "/enroll/count")
    @ResponseBody
    public CommonReturnType getCount(@RequestParam(name = "contestId") long contestId,@RequestParam(name = "currentPage",defaultValue= "1",required = false)int currentPage,
                                 @RequestParam(name = "limit",defaultValue = "5",required = false)int limit){
        Page<Enroll> enrollPage = enrollService.getEnrollPage(contestId, currentPage, limit);
        Map<String,Object> map=new HashMap<>();
        map.put("currentPage",enrollPage.getCurrent());
        map.put("total",enrollPage.getTotal());
        map.put("pages",enrollPage.getPages());
        map.put("enrolls",enrollPage.getRecords());
        return CommonReturnType.create(map);
    }

    @RequestMapping(value = "/users")
    @ResponseBody
    public CommonReturnType users(@RequestParam(name = "currentPage",required = false,defaultValue = "1")int currentPage,
                                 @RequestParam(name = "limit",required = false,defaultValue = "15")int limit){
        Page<User> userPage = userService.users(currentPage, limit);
        Map<String,Object> map=new HashMap<>();
        map.put("currentPage",userPage.getCurrent());
        map.put("total",userPage.getTotal());
        map.put("pages",userPage.getPages());
        map.put("users",userPage.getRecords());
        return CommonReturnType.create(map);
    }

    @RequestMapping(value = "/user/update/{userId}")
    public ModelAndView userShow(@PathVariable("userId") String userId) throws BusinessException {
        if (userId.equals("")){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        User user = userService.getUserShow(userId);
        ModelAndView modelAndView=new ModelAndView("admin/userUpdate");
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @PostMapping(value = "user/saveUpdate")
    @ResponseBody
    public CommonReturnType updateUser(@RequestParam(name = "userId") String userId,@RequestParam(name = "userName") String userName,
                      @RequestParam(name = "faculty")String faculty,@RequestParam(name = "classA")String classA,
                      @RequestParam(name = "gender")int gender,@RequestParam(name = "id")long id) throws BusinessException {
        User userById = userService.getUserById(id);
        if (userById==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        userService.save(id,userId,userName,faculty,classA,gender);
        return CommonReturnType.create();

    }

    @RequestMapping(value = "/user/delete")
    @ResponseBody
    public CommonReturnType userDelete(@RequestParam(name = "userId")String userId){
        userService.delete(userId);
        return CommonReturnType.create();
    }

    //将学生信息批量导入数据库
    @RequestMapping("/user/import")
    @ResponseBody
    public CommonReturnType imports (@RequestParam(name = "file")MultipartFile file) throws IOException, BusinessException {
        if (file==null){
            throw new BusinessException(EmBusinessError.DATA_ISEMPTY);
        }
        userService.userImport(file);
        return CommonReturnType.create();
    }

    @RequestMapping(value = "/wonderful-page")
    @ResponseBody
    public CommonReturnType wonderful(@RequestParam(name = "eventId")int eventId) throws BusinessException {
        EventModel eventModel = eventService.wonderful(eventId);
        return CommonReturnType.create();
    }


    @RequestMapping(value = "/user")
    public String contest(){
        return "admin/user";
    }

    @RequestMapping(value = "/enroll")
    public String enroll(){
        return "admin/enroll";
    }



    @RequestMapping(value = "/enroll/{contestId}")
    public ModelAndView enrollShow(@PathVariable String contestId){
        ModelAndView modelAndView=new ModelAndView("admin/enrollCount");
        modelAndView.addObject("contestId",contestId);
        return modelAndView;
    }

    @RequestMapping(value = "/user/create")
    public String create(){
        return "admin/userCreate";
    }

    @GetMapping(value = "/wonderful")
    public String wonderfulShow(){
        return "admin/wonderful";
    }

    @GetMapping(value = "/wonderful/create")
    public String wonderful1(){
        return "admin/wonderfulCreate";
    }
}
