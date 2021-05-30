package org.glut.competition.controller;


import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.response.CommonReturnType;
import org.glut.competition.service.impl.ContestServiceImpl;
import org.glut.competition.service.impl.EnclosureServiceImpl;
import org.glut.competition.service.impl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-20
 */
@Controller
@RequestMapping("/enclosure")
public class EnclosureController {

    @Autowired
    EventServiceImpl eventService;

    @Autowired
    ContestServiceImpl contestService;

    @Autowired
    EnclosureServiceImpl enclosureService;


    @PostMapping("/upload")
    @ResponseBody
    public CommonReturnType upload(@RequestParam(name = "file")MultipartFile file) throws BusinessException {
        String fileName = enclosureService.upload(file);
        return CommonReturnType.create(fileName);
    }

    @RequestMapping("/delFile")
    @ResponseBody
    public CommonReturnType delFile(@RequestParam(name = "fileName")String fileName) throws BusinessException {
        eventService.deleteEnclosure(fileName);
        enclosureService.delete(fileName);
        enclosureService.deleteFile(fileName);
        return CommonReturnType.create();
    }
    @RequestMapping("/deleteFile")
    @ResponseBody
    public CommonReturnType deleteFile(@RequestParam(name = "fileName")String fileName) throws BusinessException {
        contestService.deleteEnclosure(fileName);
        enclosureService.delete(fileName);
        enclosureService.deleteFile(fileName);
        return CommonReturnType.create();
    }
    @PostMapping("/cover/upload")
    @ResponseBody
    public CommonReturnType coverUpload(@RequestParam(name = "file")MultipartFile file) throws BusinessException {
        Map<String, Object> map = enclosureService.coverUpload(file);
        return CommonReturnType.create(map);
    }
}
