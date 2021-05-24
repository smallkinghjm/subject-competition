package org.glut.competition.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.glut.competition.entity.Password;
import org.glut.competition.entity.User;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.listener.UserExcelListener;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
        UserModel userModel = getUser(userId);
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
    public UserModel getUser(String userId) throws BusinessException {
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
    public void changePassword(String userId, String newPassword) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        EncryptionUtil encryptionUtil=new EncryptionUtil();
        String s = encryptionUtil.enCodeByMd5(newPassword);
        int update = passwordMapper.update(null, new UpdateWrapper<Password>().eq("user_id", userId).set("encrypt_password", s));
        if (update==0){
            System.out.println("***************修改密码失败2*****************");
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"修改密码失败");
        }

    }

    @Override
    public Page<User> users(int currentPage, int limit) {
        Page<User> userPage = userMapper.selectPage(new Page<User>(currentPage, limit), null);
        return userPage;
    }

    @Override
    public User getUserById(long id) {
        return userMapper.selectById(id);
    }

    @Override
    public void save(long id,String userId, String userName, String faculty, String classA, int gender) {
        User user=new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setFaculty(faculty);
        user.setClassA(classA);
        user.setGender(gender);
        userMapper.update(user,new UpdateWrapper<User>().eq("id",id));
    }

    @Override
    public User getUserShow(String userId) throws BusinessException {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userId));
        if (user==null){
            System.out.println("****************通过学号查找的信息为空***************");
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        return user;
    }

    @Override
    @Transactional
    public void delete(String userId) {
        userMapper.delete(new QueryWrapper<User>().eq("user_id",userId));
        passwordMapper.delete(new QueryWrapper<Password>().eq("user_id",userId));
    }

    @Override
    public void userImport(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        UserExcelListener listener = new UserExcelListener(userMapper,passwordMapper);
        EasyExcel.read(inputStream,UserModel.class,listener).sheet().doRead();
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        EncryptionUtil encryptionUtil=new EncryptionUtil();
        String s = encryptionUtil.enCodeByMd5(userModel.getEncryptPassword());
        Password password=new Password();
        password.setUserId(userModel.getUserId());
        password.setEncryptPassword(s);
        passwordMapper.insert(password);
        User user=new User();
        BeanUtils.copyProperties(userModel,user);
        userMapper.insert(user);
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
