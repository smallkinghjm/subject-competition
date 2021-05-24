package org.glut.competition.service;

import org.glut.competition.error.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface IFileService {
    Map<String, String> upload(MultipartFile file) throws BusinessException;
    void download(String name, HttpServletResponse response) throws IOException;
}
