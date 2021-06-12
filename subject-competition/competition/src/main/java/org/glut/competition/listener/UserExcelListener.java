package org.glut.competition.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.SneakyThrows;
import org.glut.competition.entity.Password;
import org.glut.competition.entity.User;
import org.glut.competition.mapper.PasswordMapper;
import org.glut.competition.mapper.UserMapper;
import org.glut.competition.service.Model.UserModel;
import org.glut.competition.util.EncryptionUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class UserExcelListener extends AnalysisEventListener<UserModel> {

    //因为SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
    //不能实现数据库操作(通过在controller层调用service时将user的service层传递进来)
    private static final int BATCH_COUNT = 1000;
    List<User> userList=new ArrayList<>();
    List<Password> passwordList=new ArrayList<>();

    private UserMapper userMapper;
    private PasswordMapper passwordMapper;

    public UserExcelListener(UserMapper userMapper,PasswordMapper passwordMapper){
        this.userMapper=userMapper;
        this.passwordMapper=passwordMapper;
    }

    @SneakyThrows
    @Override
    public void invoke(UserModel data, AnalysisContext context) {
        User user=new User();
        Password password=new Password();
/*        BeanUtils.copyProperties(data,user);
        BeanUtils.copyProperties(data,password);*/
        user.setUserId(data.getUserId());
        user.setUserName(data.getUserName());
        user.setFaculty(data.getFaculty());
        user.setClassA(data.getClassA());
        user.setGender(data.getGender());
        password.setUserId(data.getUserId());
        EncryptionUtil encryptionUtil=new EncryptionUtil();
        String s = encryptionUtil.enCodeByMd5(data.getEncryptPassword());
        password.setEncryptPassword(s);

        userList.add(user);
        passwordList.add(password);
        if (userList.size()>=BATCH_COUNT){
            saveData();
            userList.clear();
            passwordList.clear();
        }
    }

    private void saveData() {
        //先插入密码表，防止出错时密码为空
        passwordMapper.passwordSaveBatch(passwordList);
        userMapper.userSaveBatch(userList);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

}

