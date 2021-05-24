package org.glut.competition.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.glut.competition.controller.viewobject.EventVO;
import org.glut.competition.entity.Contest;
import org.glut.competition.entity.Enclosure;
import org.glut.competition.entity.Event;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.response.CommonReturnType;
import org.glut.competition.service.Model.UserModel;
import org.glut.competition.service.impl.ContestServiceImpl;
import org.glut.competition.service.impl.EnclosureServiceImpl;
import org.glut.competition.service.impl.EventServiceImpl;
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
 * @since 2021-04-20
 */
@Controller
public class EventController {

    @Autowired
    EventServiceImpl eventService;

    @Autowired
    EnclosureServiceImpl enclosureService;

    @Autowired
    ContestServiceImpl contestService;


    //用户主页
    @RequestMapping(value = "/home")
    public ModelAndView home(){
        Page<Event> eventPage0 = eventService.getEvents(0, 1, 10);
        List<Contest> contests=contestService.contestPage(1,10).getRecords();
        Page<Event> eventPage2 = eventService.getEvents(2, 1, 10);
        Page<Event> carousels = eventService.getEvents(3, 1, 5);
        List<EventVO> list=new ArrayList<>();
        Iterator<Event> it = carousels.getRecords().iterator();
        while (it.hasNext()){
            Event event = it.next();
            EventVO eventVO=new EventVO();
            BeanUtils.copyProperties(event,eventVO);
            Enclosure enclosure = enclosureService.getEnclosure(event.getEnclosureName());
            eventVO.setEnclosureContent(enclosure.getEnclosureContent());
            list.add(eventVO);
        }
        ModelAndView modelAndView=new ModelAndView("home");
        modelAndView.addObject("contests",contests);
        modelAndView.addObject("notices",eventPage0.getRecords());
        modelAndView.addObject("achievements",eventPage2.getRecords());
        modelAndView.addObject("wonderfuls",list);
        return modelAndView;
    }

    @GetMapping(value = "/events")
    @ResponseBody
    public CommonReturnType getEventPage(@RequestParam(name = "type")int type,
                                     @RequestParam(name = "currentPage",defaultValue = "1",required = false)int currentPage,
                                     @RequestParam(name = "limit",defaultValue = "20",required = false)int limit){
        Page<Event> eventPage = eventService.getEvents(type, currentPage, limit);
        Map<String,Object> map=new HashMap<>();
        map.put("currentPage",eventPage.getCurrent());
        map.put("total",eventPage.getTotal());
        map.put("pages",eventPage.getPages());
        map.put("events",eventPage.getRecords());
        return CommonReturnType.create(map);
    }

    @GetMapping(value = "/event/{eventId}")
    public ModelAndView getEventContent(@PathVariable long eventId){
        EventVO eventVO=eventService.getEventContent(eventId);
        String enclosureName = eventVO.getEnclosureName();
        ModelAndView modelAndView=new ModelAndView("eventView");
        modelAndView.addObject("eventVO",eventVO);
        if (enclosureName!=null){
            Enclosure enclosure = enclosureService.getEnclosure(enclosureName);
            modelAndView.addObject("enclosure",enclosure);
        }else {
            modelAndView.addObject("enclosure","无附件");
        }
        return modelAndView;
    }

    @GetMapping(value = "/wonderfuls")
    public ModelAndView wonderfuls(@RequestParam(name = "type")int type,
                                   @RequestParam(name = "currentPage",defaultValue = "1",required = false)int currentPage,
                                   @RequestParam(name = "limit",defaultValue = "12",required = false)int limit){
        Page<Event> events = eventService.getEvents(3, currentPage, limit);
        List<EventVO> list=new ArrayList<>();
        Iterator<Event> it = events.getRecords().iterator();
        while (it.hasNext()){
            Event event = it.next();
            EventVO eventVO=new EventVO();
            BeanUtils.copyProperties(event,eventVO);
            Enclosure enclosure = enclosureService.getEnclosure(event.getEnclosureName());
            eventVO.setEnclosureContent(enclosure.getEnclosureContent());
            list.add(eventVO);
        }
        ModelAndView modelAndView=new ModelAndView("wonderful");
        modelAndView.addObject("wonderfuls",list);
        return modelAndView;
    }

    @GetMapping(value = "/wonderful/{eventId}")
    public ModelAndView wonderfulView(@PathVariable long eventId){
        EventVO eventVO=eventService.getEventContent(eventId);
        ModelAndView modelAndView=new ModelAndView("wonderfulView");
        modelAndView.addObject("wonderful",eventVO);
        return modelAndView;
    }

    @GetMapping("/admin/event/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestParam(name = "eventId")long eventId) throws BusinessException {
        Event event = eventService.getEventById(eventId);
        try {
            if (event.getEnclosureName()!=null&&!event.getEnclosureName().equals("")){
                Enclosure enclosure = enclosureService.getEnclosure(event.getEnclosureName());
                enclosureService.deleteFile(enclosure.getEnclosureName());
                enclosureService.delete(enclosure.getEnclosureName());
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"删除错误，请检查字段是否正确");
        }
        eventService.delete(eventId);
        return CommonReturnType.create();
    }
    @RequestMapping("/admin/event/create")
    public String toCreate(){
        return "/admin/eventCreate";
    }

    @RequestMapping(value = "/notice")
    public String notice(){
        return "notice";
    }
    @RequestMapping(value = "/achievement")
    public String achievement(){
        return "achievement";
    }
    @RequestMapping(value = "/summary")
    public String summary(){
        return "summary";
    }
}
