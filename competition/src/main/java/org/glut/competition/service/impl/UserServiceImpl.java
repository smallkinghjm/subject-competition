package org.glut.competition.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.glut.competition.entity.Password;
import org.glut.competition.entity.User;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.mapper.PasswordMapper;
import org.glut.competition.mapper.UserMapper;
import org.glut.competition.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.glut.competition.service.Model.UserModel;
import org.glut.competition.util.EncryptionUtil;
import org.glut.competition.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordMapper passwordMapper;

    @Autowired
    private ValidatorImpl validator;

    //实现登陆
    @Override
    public UserModel login(String userId, String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        UserModel userModel = getUserById(userId);
        //对比密码是否正确
        EncryptionUtil encryptionUtil=new EncryptionUtil();
        String s = encryptionUtil.enCodeByMd5(password);
        if (!userModel.getEncryptPassword().equals(s)){
            System.out.println("*****************密码错误*******************1");
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    //实现通过userId查找信息
    @Override
    public UserModel getUserById(String userId) throws BusinessException {
        //调用selectOne的两种写法
/*        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        User user = userMapper.selectOne(queryWrapper);*/

        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userId));
        if (user==null){
            System.out.println("****************通过学号或职工号查找的信息为空***************");
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        Password password = passwordMapper.selectOne(new QueryWrapper<Password>().eq("user_id", userId));
        return convertFromDataObject(user,password);
    }

    //实现添加邮箱
    @Override
    @Transactional
    public void addEmail(String email,String newPassword, String userId) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        EncryptionUtil encryptionUtil=new EncryptionUtil();
        String s = encryptionUtil.enCodeByMd5(newPassword);
        int update1 = userMapper.update(null, new UpdateWrapper<User>().eq("user_id", userId).set("email", email));
        int update2 = passwordMapper.update(null, new UpdateWrapper<Password>().eq("user_id", userId).set("encrypt_password", s));

        if (update1==0){
            System.out.println("********************添加邮箱失败**********************");
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"绑定邮箱失败！");
        }
        if (update2==0){
            System.out.println("********************修改初始密码失败**********************");
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"修改初始密码失败！");
        }
    }

    @Override
    @Transactional
    public void forgetPassword(String userId,String email, String newPassword) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userId));
        String userIdIn = user.getUserId();
        String emailIn=user.getEmail();
        if ((!userId.equals(userIdIn))||(!email.equals(emailIn))){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL,"用户名或邮箱错误");
        }
        int update = passwordMapper.update(null, new UpdateWrapper<Password>().eq("user_id", userId).set("encrypt_password", new EncryptionUtil().enCodeByMd5(newPassword)));
        if (update==0){
            System.out.println("***************修改密码失败1*****************");
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"通过邮箱修改密码失败");
        }
    }

    @Override
    @Transactional
    public void changePassword(String userId, String newPassword) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        EncryptionUtil encryptionUtil=new EncryptionUtil();
        String s = encryptionUtil.enCodeByMd5(newPassword);
        int update = passwordMapper.update(null, new UpdateWrapper<Password>().eq("user_id", userId).set("encrypt_password", s));
        if (update==0){
            System.out.println("***************修改密码失败2*****************");
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"修改密码失败");
        }

    }

    //合并用户表和密码表数据
    private UserModel convertFromDataObject(User user,Password password){
        if (user==null){
            return null;
        }
        UserModel userModel=new UserModel();
        BeanUtils.copyProperties(user,userModel);
        if (password!=null){
            userModel.setEncryptPassword(password.getEncryptPassword());
        }
        return userModel;
    }
}
