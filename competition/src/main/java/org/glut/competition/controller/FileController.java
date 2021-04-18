package org.glut.competition.controller;

import org.glut.competition.error.BusinessException;
import org.glut.competition.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/file")
public class FileController {

/*    @Autowired
    FileServiceImpl fileService;

    @Autowired
    HttpServletResponse response;

    @RequestMapping(value = "/upload")
    public void upload(@RequestParam(name = "file")MultipartFile file) throws BusinessException {
        fileService.upload(file);
    }

    @GetMapping("/download")
    public void download(String name) throws IOException {
        fileService.download(name,response);
    }*/
}
