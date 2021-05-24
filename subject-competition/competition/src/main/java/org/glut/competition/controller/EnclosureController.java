package org.glut.competition.controller;


import org.glut.competition.error.BusinessException;
import org.glut.competition.response.CommonReturnType;
import org.glut.competition.service.impl.EnclosureServiceImpl;
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
    EnclosureServiceImpl enclosureService;
    @PostMapping("/upload")
    @ResponseBody
    public CommonReturnType upload(@RequestParam(name = "file")MultipartFile file) throws BusinessException {
        String fileName = enclosureService.upload(file);
        return CommonReturnType.create(fileName);
    }

    @GetMapping("delFile")
    @ResponseBody
    public CommonReturnType deleteFile(@RequestParam(name = "fileName")String fileName) throws BusinessException {
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
