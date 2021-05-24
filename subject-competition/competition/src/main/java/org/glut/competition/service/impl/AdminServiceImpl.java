package org.glut.competition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.glut.competition.entity.Admin;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.mapper.AdminMapper;
import org.glut.competition.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.glut.competition.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-23
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public void login(String name, String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("name", name));
        if (admin==null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        EncryptionUtil encryptionUtil=new EncryptionUtil();
        String encryptPassword = encryptionUtil.enCodeByMd5(password);
        if (!StringUtils.equals(admin.getEncryptPassword(),encryptPassword)){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
    }
}
