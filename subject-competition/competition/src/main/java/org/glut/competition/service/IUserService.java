package org.glut.competition.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.glut.competition.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.glut.competition.error.BusinessException;
import org.glut.competition.service.Model.UserModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    UserModel getUser(String userId) throws BusinessException;
    void addEmail(String email,String newPassword,String userId) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;
    void changePassword(String userId,String newPassword) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;
    Page<User> users(int currentPage,int limit);
    User getUserById(long id);
    void save(long id,String userId,String userName,String faculty,String classA,int gender);
    User getUserShow(String userId) throws BusinessException;
    void delete(String userId);
    void userImport(MultipartFile file) throws IOException;
    void register(UserModel userModel) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
