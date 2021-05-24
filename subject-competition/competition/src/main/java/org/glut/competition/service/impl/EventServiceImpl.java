package org.glut.competition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.glut.competition.controller.viewobject.EventVO;
import org.glut.competition.entity.Enclosure;
import org.glut.competition.entity.Event;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.mapper.EnclosureMapper;
import org.glut.competition.mapper.EventMapper;
import org.glut.competition.response.CommonReturnType;
import org.glut.competition.service.IEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.glut.competition.service.Model.EventModel;
import org.glut.competition.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-20
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements IEventService {

    @Autowired
    EventMapper eventMapper;

    @Autowired
    ValidatorImpl validator;

    @Autowired
    HttpServletRequest request;

    @Autowired
    EnclosureMapper enclosureMapper;


    //创建
    @Override
    public void create(String title, int type, String content,String enclosureName) {
        Event event=new Event();
        event.setTitle(title);
        event.setType(type);
        if (!enclosureName.equals("")){
            event.setEnclosureName(enclosureName);
        }
        event.setContent(content);
        event.setCreateTime(LocalDateTime.now());
        event.setUpdateTime(LocalDateTime.now());

        eventMapper.insert(event);
    }

    @Override
    public EventVO show(String title) {
        Event event = eventMapper.selectOne(new QueryWrapper<Event>().eq("title", title));
        EventVO eventVO=new EventVO();
        BeanUtils.copyProperties(event,eventVO);
        return eventVO;
    }

    @Override
    public List<Event> getAll(int type,int start,int limit) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        String lastSql = " limit ";
        lastSql = lastSql + String.valueOf(limit) + " offset " + String.valueOf(start);
        queryWrapper.eq("type",type).last(lastSql);
        return eventMapper.selectList(queryWrapper);
    }

    @Override
    public EventVO getEventContent(Long eventId) {
        Event event = eventMapper.selectOne(new QueryWrapper<Event>().eq("event_id", eventId));
        EventVO eventVO=new EventVO();
        BeanUtils.copyProperties(event,eventVO);
        return eventVO;
    }

/*    @Override
    public List<Event> eventPage(int type,Integer currentPage, Integer limit) {
        Page<Event> events=eventMapper.selectPage(new Page<Event>(currentPage,limit),new QueryWrapper<Event>().eq("type",type));
        return events.getRecords();

    }*/

    @Override
    @Transactional
    public void delete(long eventId) throws BusinessException {
        try {
            eventMapper.deleteById(eventId);
        }catch (Exception exception){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"删除失败");
        }
    }

    //根据修改时间排序，分页
    @Override
    public Page<Event> getEvents(int type, int currentPage, int limit) {
        QueryWrapper<Event> wrapper=new QueryWrapper<>();
        wrapper.eq("type",type);
        wrapper.orderByDesc("update_time");
        eventMapper.selectCount(wrapper);
        Page<Event> page=new Page<>(currentPage,limit);
        Page<Event> eventPage = eventMapper.selectPage(page, wrapper);
        return eventPage;

    }
    //根据
    @Override
    public List<Event> getAll(long eventId) {
        QueryWrapper<Event> wrapper=new QueryWrapper<>();
        wrapper.eq("event_id",eventId);
        List<Event> events=eventMapper.selectList(wrapper);
        return events;
    }
    //内容更新
    @Override
    @Transactional
    public void update(long eventId, String title, int type, String content) throws BusinessException {
        System.out.println("eventId"+eventId);
        System.out.println(type);
        UpdateWrapper<Event> wrapper=new UpdateWrapper<>();
        wrapper.eq("event_id",eventId);
        Event event=new Event();
        event.setTitle(title);
        event.setType(type);
        event.setContent(content);
        event.setUpdateTime(LocalDateTime.now());
        int update = eventMapper.update(event, wrapper);
        if (update==0){
            throw new BusinessException(EmBusinessError.DATA_ERROR,"更新失败");
        }
    }

    @Override
    public Event getEventById(long eventId) throws BusinessException {
        Event event = eventMapper.selectById(eventId);
        if (event==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"该信息查找不存在");
        }
        return event;
    }

    @Override
    public EventModel wonderful(long eventId) throws BusinessException {
        Event event = eventMapper.selectOne(new QueryWrapper<Event>().eq("event_id", eventId));
        if (event==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"查找的数据为空");
        }
        String enclosureName = event.getEnclosureName();
        Enclosure enclosure = enclosureMapper.selectOne(new QueryWrapper<Enclosure>().eq("enclosure_name", enclosureName));
        return convertFromDataObject(event,enclosure);
    }
    private EventModel convertFromDataObject(Event event,Enclosure enclosure){
        EventModel eventModel=new EventModel();
        BeanUtils.copyProperties(event,eventModel);
        if (enclosure!=null){
            eventModel.setEnclosureContent(enclosure.getEnclosureContent());
        }
        return eventModel;
    }
}
