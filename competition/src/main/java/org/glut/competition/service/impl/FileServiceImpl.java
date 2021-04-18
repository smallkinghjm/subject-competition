package org.glut.competition.service.impl;

/*import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;*/
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.service.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
@Service
public class FileServiceImpl implements IFileService {

/*    //保存的目录
    String filePath="D:\\enclosure\\";
    //文件上传
    @Override
    public void upload(MultipartFile file) throws BusinessException {
        if (file==null){
            throw new BusinessException(EmBusinessError.DATA_ISEMPTY,"文件上传为空");
        }
        String originalFilename=file.getOriginalFilename();
        //将上传的文件名修改为当前时间加上后缀的字符串
        String fileName=System.currentTimeMillis()+"."+originalFilename.substring(originalFilename.lastIndexOf(".")+1);

        File dest=new File(filePath+fileName);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessError.DATA_ERROR,"文件上传失败");
        }
    }

    @Override
    public void download(String name, HttpServletResponse response) throws IOException {

        //String extendName=name.substring(name.lastIndexOf('.')+1);
        File file=new File(filePath+name);
        IOUtils.copy(FileUtils.openInputStream(file),response.getOutputStream());
    }*/
}
