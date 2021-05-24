package org.glut.competition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.glut.competition.entity.Enclosure;
import org.glut.competition.entity.Event;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.mapper.EnclosureMapper;
import org.glut.competition.mapper.EventMapper;
import org.glut.competition.service.IEnclosureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-20
 */
@Service
public class EnclosureServiceImpl extends ServiceImpl<EnclosureMapper, Enclosure> implements IEnclosureService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    EnclosureMapper enclosureMapper;

    @Autowired
    EventMapper eventMapper;


    @Override
    @Transactional
    public String upload(MultipartFile file) throws BusinessException {
        if (file==null){
            throw new BusinessException(EmBusinessError.DATA_ISEMPTY,"文件上传为空");
        }
        //分隔符
        String separator = System.getProperty("file.separator");
        separator=separator.replaceAll("\\\\","/");
        //获取项目路径+端口号 比如：http://localhost:8080/
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +request.getContextPath()+ separator;

        try {
            //获取源文件
            String filePath="D:/contentFile/" ;//存储地址
            String filename = file.getOriginalFilename();//获取文件名,包含后缀
            String[] names=filename.split("\\.");//获取后缀格式
            String test = filename.substring(0,filename.lastIndexOf("."));//获取文件名，不包含后缀
            String uploadFileName= test+System.currentTimeMillis()+"."+names[names.length-1];//生成新文件名:原文件名+时间戳
            File targetFile = new File (filePath,uploadFileName);//目标文件
            if (!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            //传图片一步到位
            file.transferTo(targetFile);
            insert(uploadFileName,basePath+"contentFile/"+uploadFileName);
            return uploadFileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessError.DATA_ERROR);
        }
    }

    //附件插入数据库
    private void insert(String enclosureName,String enclosureContent){
        Enclosure enclosure=new Enclosure();
        enclosure.setEnclosureName(enclosureName);
        enclosure.setEnclosureContent(enclosureContent);
        enclosureMapper.insert(enclosure);
      /*  Enclosure enclosure1 = enclosureMapper.selectOne(new QueryWrapper<Enclosure>().eq("enclosure_name", enclosureName));
        Long enclosureId = enclosure1.getEnclosureId();
        String title =(String) request.getSession().getAttribute("TITLE");
        eventMapper.update(null,new UpdateWrapper<Event>().eq("title",title).set("enclosure_id",enclosureId));*/
    }

    @Override
    public boolean deleteFile(String fileName) throws BusinessException {
        String path="D:/contentFile/";
        File file=new File(path);
        if (!file.exists()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"文件为空");
        }
        if (file.isFile()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"该文件是目录");
        }else {
            File[] files=file.listFiles();
            for (File f:files){
                if (fileName.equals(f.getName())){
                    f.delete();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Enclosure getEnclosure(String enclosureName) {
        Enclosure enclosure = enclosureMapper.selectOne(new QueryWrapper<Enclosure>().eq("enclosure_name",enclosureName));
        return enclosure;
    }

    @Override
    public void delete(String enclosureName) throws BusinessException {
        if (enclosureName.equals("")){
            throw new BusinessException(EmBusinessError.DATA_ISEMPTY);
        }
        enclosureMapper.delete(new QueryWrapper<Enclosure>().eq("enclosure_name",enclosureName));
    }

    @Override
    public Map<String,Object> coverUpload(MultipartFile file) throws BusinessException {
        if (file==null){
            throw new BusinessException(EmBusinessError.DATA_ISEMPTY,"文件上传为空");
        }
        //分隔符
        String separator = System.getProperty("file.separator");
        separator=separator.replaceAll("\\\\","/");
        //获取项目路径+端口号 比如：http://localhost:8080/
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +request.getContextPath()+ separator;

        try {
            //获取源文件
            String filePath="D:/contentFile/" ;//存储地址
            String filename = file.getOriginalFilename();//获取文件名,包含后缀
            String[] names=filename.split("\\.");//获取后缀格式
            String test = filename.substring(0,filename.lastIndexOf("."));//获取文件名，不包含后缀
            String uploadFileName= test+System.currentTimeMillis()+"."+names[names.length-1];//生成新文件名:原文件名+时间戳
            File targetFile = new File (filePath,uploadFileName);//目标文件
            if (!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            //传图片一步到位
            file.transferTo(targetFile);
            insert(uploadFileName,basePath+"contentFile/"+uploadFileName);
            String url=basePath+"contentFile/"+uploadFileName;
            Map<String,Object> map=new HashMap<>();
            map.put("fileName",uploadFileName);
            map.put("url",url);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessError.DATA_ERROR);
        }
    }
}
