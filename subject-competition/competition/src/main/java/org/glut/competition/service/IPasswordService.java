package org.glut.competition.service;

import org.glut.competition.entity.Password;
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
 * @since 2021-04-15
 */
public interface IPasswordService extends IService<Password> {
    void forgetPassword(String emailName,String newPassword) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;
}
