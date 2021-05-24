package org.glut.competition.controller;


import org.glut.competition.controller.viewobject.UserVO;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.response.CommonReturnType;
import org.glut.competition.service.Model.UserModel;
import org.glut.competition.service.impl.UserServiceImpl;
import org.glut.competition.util.EncryptionUtil;
import org.glut.competition.validator.ValidationResult;
import org.glut.competition.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import static org.glut.competition.util.VerifyCodeUtil.RANDOMCODEKEY;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-15
 */
@Controller
@RequestMapping("/user")
public class UserController{

    @Autowired
    UserServiceImpl userService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    ValidatorImpl validator;

    @PostMapping(value = "/checkId")
    @ResponseBody
    public CommonReturnType checked(@RequestParam(name = "userId")String userId) throws BusinessException {
        userService.getUserShow(userId);
        return CommonReturnType.create();
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName,
                                     @RequestParam(name = "password")String password,@RequestParam(name = "faculty")String faculty,
                                     @RequestParam(name = "classA")String classA,@RequestParam(name = "gender")int gender) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        UserModel userModel=new UserModel();
        userModel.setUserId(userId);
        userModel.setUserName(userName);
        userModel.setFaculty(faculty);
        userModel.setClassA(classA);
        userModel.setGender(gender);
        userModel.setEncryptPassword(password);

        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        userService.register(userModel);
        return CommonReturnType.create();
    }
    /*
    * 登陆
    * */
    @PostMapping(value = "/login-page")
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "userId")String userId,
                      @RequestParam(name = "password")String password,
                      @RequestParam(name = "verifyCode")String verifyCode) throws BusinessException, IOException, NoSuchAlgorithmException {
        //检查验证码是否错误
        String code=(String) this.httpServletRequest.getSession().getAttribute(RANDOMCODEKEY);
        if(!StringUtils.equals(code.toLowerCase(),verifyCode.toLowerCase())){
            throw new BusinessException(EmBusinessError.VERIFY_ERROR);
        }
        //入参校验
        if (StringUtils.isEmpty(userId)||StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserModel userModel = userService.login(userId, password);
        //将登录凭证加入到用户登录成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        //检查用户是否首次登陆
        if (userModel.getEmail()==null||userModel.getEmail().equals("")){
            //首次登陆绑定邮箱
            return CommonReturnType.create("您为首次登陆，为了您的信息安全，请绑定邮箱，并修改初始密码!!!","11111");
        }
        return CommonReturnType.create();
    }

    /*
    * 绑定邮箱
    * */
    @PostMapping(value = "first-page")
    @ResponseBody
    public CommonReturnType addEmail(@RequestParam(name = "email")String email, @RequestParam(name = "mailCode") String mailCode,@RequestParam(name = "newPassword")String newPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException, BusinessException {
        Map<String, Object> resultMap =(Map<String, Object>) this.httpServletRequest.getSession().getAttribute("MAILVERIFY");

        if (resultMap ==null) {
            System.out.println("请先获取验证码");
            return CommonReturnType.create("请先获取验证码","50000");
        }
        //判断验证码是否正确
        String requestHash = resultMap.get("hash").toString();
        String tamp = resultMap.get("tamp").toString();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");//当前时间
        Calendar c = Calendar.getInstance();
        String currentTime = sf.format(c.getTime());
        if (tamp.compareTo(currentTime) <= 0) {
            // 超时
            System.out.println("3");
            //attributes.addFlashAttribute("message", "验证码已过期");
            //return "redirect:/password/addEmail";
            return CommonReturnType.create("验证码已过期","50001");
        }
        EncryptionUtil encryptionUtil = new EncryptionUtil();
        String hash = encryptionUtil.enCodeByMd5(mailCode);//生成MD5值
        if (!hash.equalsIgnoreCase(requestHash)){
            //验证码不正确，校验失败
             return CommonReturnType.create("验证码输入不正确","50002");
        }

        //校验成功
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        if (userModel==null){
            return CommonReturnType.create("用户未登陆，请先登陆","20003");
        }
        String userId = userModel.getUserId();
        //绑定邮箱
        userService.addEmail(email,newPassword,userId);
        //更新session中的userModel
        userModel.setEmail(email);
        userModel.setEncryptPassword(new EncryptionUtil().enCodeByMd5(newPassword));
        this.httpServletRequest.getSession().removeAttribute("LOGIN_USER");
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return CommonReturnType.create("success");
    }


    @PostMapping(value = "/changePassword-page")
    @ResponseBody
    public CommonReturnType changerPassword(@RequestParam(name = "password")String password,@RequestParam(name = "newPassword")String newPassword) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        UserModel userModel =(UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        String userId=new String();
        try {
            userId = userModel.getUserId();
        }catch (Exception e) {
            System.out.println(e);
            if (userId.equals("")){
                System.out.println("错误，用户登录状态异常");
                return CommonReturnType.create("错误，用户登录状态异常","20003");
            }
        }
        //对比密码
        EncryptionUtil encryptionUtil=new EncryptionUtil();
        if (userModel.getEncryptPassword()!=encryptionUtil.enCodeByMd5(password)){
            System.out.println("****************密码输入错误*****************");
            CommonReturnType.create("密码输入错误","20002");
        }
        //修改密码
        userService.changePassword(userId,newPassword);
        //重新登录
        httpServletRequest.getSession().removeAttribute("IS_LOGIN");
        return CommonReturnType.create(null);
    }

    //将领域模型转为视图模型
    private UserVO convertFromModel(UserModel userModel){
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

    @RequestMapping("/information")
    public ModelAndView information() throws BusinessException {
        Object is_login =this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (is_login==null){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN_);

        }else {
            UserModel userModel =(UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
            UserVO userVO = convertFromModel(userModel);
            ModelAndView modelAndView=new ModelAndView("information");
            modelAndView.addObject("user",userVO);
            return modelAndView;
        }
    }

    @RequestMapping("exit")
    public void exit(){
        this.httpServletRequest.getSession().removeAttribute("IS_LOGIN");
    }


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/first")
    public String first(){
        return "first";
    }

    @RequestMapping("/home")
    public String login2(){
        return "redirect:/home";
    }

    @RequestMapping("/changePassword")
    public String login3(){
        return "changePassword";
    }
    @RequestMapping("/forgetPassword")
    public String login4(){
        return "resetPassword";
    }


}
