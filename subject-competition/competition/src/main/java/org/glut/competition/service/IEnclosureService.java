package org.glut.competition.service;

import org.glut.competition.entity.Enclosure;
import com.baomidou.mybatisplus.extension.service.IService;
import org.glut.competition.error.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-20
 */
public interface IEnclosureService extends IService<Enclosure> {
    String upload(MultipartFile file) throws BusinessException;
    boolean deleteFile(String fileName) throws BusinessException;
    Enclosure getEnclosure(String enclosureName);
    void delete(String enclosureName) throws BusinessException;
    Map<String,Object> coverUpload(MultipartFile file) throws BusinessException;
}
