package org.glut.competition.controller;


import org.glut.competition.controller.viewobject.EventVO;
import org.glut.competition.entity.CompetitionEvent;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.service.impl.CompetitionEventServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-12
 */
@Controller
@RequestMapping("/competition-event")
public class CompetitionEventController {

    /*@Autowired
    CompetitionEventServiceImpl competitionEventService;

    //获取赛事内容
    @RequestMapping("/read")
    @ResponseBody
    public EventVO read(@RequestParam(name = "competitionName")String competitionName) throws BusinessException {
        if(competitionName==null){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"竞赛名称不能为空");
        }
        CompetitionEvent competitionEvent = competitionEventService.read(competitionName);
        return convertFrom(competitionEvent);
    }

    //转视图模型
    private EventVO convertFrom(CompetitionEvent competitionEvent){
        EventVO eventVO=new EventVO();
        BeanUtils.copyProperties(competitionEvent,eventVO);
        return eventVO;
    }

    //发布赛事
    @RequestMapping(value = "/release")
    public void release(@RequestParam("competitionName")String competitionName, @RequestParam("competitionContent")String competitionContent,
                        @RequestParam("competitionBegin")LocalDate competitionBegin, @RequestParam("competitionFinish")LocalDate competitionFinish) throws BusinessException {

        CompetitionEvent competitionEvent=new CompetitionEvent();
        competitionEvent.setCompetitionName(competitionName);
        competitionEvent.setCompetitionContent(competitionContent);
        competitionEvent.setCompetitionBegin(competitionBegin);
        competitionEvent.setCompetitionFinish(competitionFinish);

        competitionEventService.release(competitionEvent);

    }*/
}
