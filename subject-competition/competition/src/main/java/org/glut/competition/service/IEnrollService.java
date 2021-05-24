package org.glut.competition.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.glut.competition.entity.Enroll;
import com.baomidou.mybatisplus.extension.service.IService;
import org.glut.competition.error.BusinessException;
import org.glut.competition.service.Model.EnrollModel;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-23
 */
public interface IEnrollService extends IService<Enroll> {
    void create(Enroll enroll) throws BusinessException;
    Enroll getEnroll(String studentId) throws BusinessException;
    Page<Enroll> getEnrollPage(long contestId,int currentPage,int limit);
    int getCount(long contestId);
    List<EnrollModel> enrollView(String studentId);
    void delete(String studentId,long contestId);
}
