package org.glut.competition.controller;

import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.response.CommonReturnType;
import org.glut.competition.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    HttpServletResponse response;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/upload")
    @ResponseBody
    public Map<String, String> upload(@RequestParam(name = "file")MultipartFile file) throws BusinessException {

        Map<String, String> filePath = fileService.upload(file);
        return filePath;
    }

    @GetMapping("/download")
    public void download(String name) throws IOException {
        fileService.download(name,response);
    }


    @RequestMapping("/test1")
    public String test1(){
        return "create";
    }
}
