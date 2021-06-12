package org.glut.competition.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.glut.competition.controller.viewobject.EventVO;
import org.glut.competition.entity.Event;
import com.baomidou.mybatisplus.extension.service.IService;
import org.glut.competition.error.BusinessException;
import org.glut.competition.service.Model.EventModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-20
 */
public interface IEventService extends IService<Event> {
    void create(String title,int type,String content,String enclosureName);
    EventVO show(String title);
    List<Event> getAll(int type,int start,int limit);
    EventVO getEventContent(Long eventId);
    void delete(long eventId) throws BusinessException;
    Page<Event> getEvents(int type, int currentPage, int limit);
    List<Event> getAll(long eventId);
    void update(long eventId,String title,int type,String content,String fileName) throws BusinessException;
    Event getEventById(long eventId) throws BusinessException;
    EventModel wonderful(long eventId) throws BusinessException;
    void deleteEnclosure(String fileName) throws BusinessException;
    Page<Event> summary(String userId,int currentPage,int limit);
}
