package org.glut.competition.service.impl;

/*import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;*/
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    HttpServletRequest request;

    //文件上传
    @Override
    public Map<String, String> upload(MultipartFile file) throws BusinessException {
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
            String filename = file.getOriginalFilename();//获取图片名
            String[] names=filename.split("\\.");//获取后缀格式
            String uploadFileName= UUID.randomUUID().toString()+"."+names[names.length-1];//生成新文件名
            File targetFile = new File (filePath,uploadFileName);//目标文件
            if (!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            //传图片一步到位
            file.transferTo(targetFile);
            Map<String, String> map = new HashMap<String, String>();
            map.put("data",basePath+"contentFile/"+uploadFileName);//项目路径，返回前台url
            return map;
        } catch (Exception e) {
            e.printStackTrace();
           throw new BusinessException(EmBusinessError.DATA_ERROR);
        }
    }

    @Override
    public void download(String name, HttpServletResponse response) throws IOException {

        //String extendName=name.substring(name.lastIndexOf('.')+1);
        /*File file=new File(filePath+name);
        IOUtils.copy(FileUtils.openInputStream(file),response.getOutputStream());*/
    }
}
