package org.glut.competition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.glut.competition.entity.Password;
import org.glut.competition.entity.User;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.mapper.PasswordMapper;
import org.glut.competition.mapper.UserMapper;
import org.glut.competition.service.IPasswordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.glut.competition.util.EncryptionUtil;
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
public class PasswordServiceImpl extends ServiceImpl<PasswordMapper, Password> implements IPasswordService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordMapper passwordMapper;

    @Override
    @Transactional
    public void forgetPassword(String emailName, String newPassword) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("email", emailName));

        String userId = user.getUserId();
        int update = passwordMapper.update(null, new UpdateWrapper<Password>().eq("user_id", userId).set("encrypt_password", new EncryptionUtil().enCodeByMd5(newPassword)));
        if (update==0){
            System.out.println("***************修改密码失败1*****************");
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"通过邮箱修改密码失败");
        }
    }
}
