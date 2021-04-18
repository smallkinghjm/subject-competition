package org.glut.competition.service;

import org.glut.competition.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.glut.competition.error.BusinessException;
import org.glut.competition.service.Model.UserModel;

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
public interface IUserService extends IService<User> {
    UserModel login(String userId,String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;
    UserModel getUserById(String userId) throws BusinessException;
    void addEmail(String email,String newPassword,String userId) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;
    void forgetPassword(String userId,String email,String newPassword) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;
    void changePassword(String userId,String newPassword) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;
}
