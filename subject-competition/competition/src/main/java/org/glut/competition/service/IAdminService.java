package org.glut.competition.service;

import org.glut.competition.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import org.glut.competition.error.BusinessException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-23
 */
public interface IAdminService extends IService<Admin> {
    void login(String name,String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;
}
