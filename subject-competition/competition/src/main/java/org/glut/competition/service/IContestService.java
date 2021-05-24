package org.glut.competition.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.glut.competition.entity.Contest;
import com.baomidou.mybatisplus.extension.service.IService;
import org.glut.competition.error.BusinessException;
import org.glut.competition.service.Model.ContestModel;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-23
 */
public interface IContestService extends IService<Contest> {
    void create(ContestModel contest) throws BusinessException;
    Page<Contest> contestPage(int currentPage,int limit);
   /* Page<ContestModel>  contestPage(int currentPage,int limit);*/
    Contest getContestById(long contestId) throws BusinessException;
    void update(Contest contest) throws BusinessException;
    void delete(long contestId);
}
