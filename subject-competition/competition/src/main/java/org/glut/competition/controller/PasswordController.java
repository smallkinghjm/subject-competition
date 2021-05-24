package org.glut.competition.controller;


import org.glut.competition.entity.User;
import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.response.CommonReturnType;
import org.glut.competition.service.impl.PasswordServiceImpl;
import org.glut.competition.service.impl.UserServiceImpl;
import org.glut.competition.util.EncryptionUtil;
import org.glut.competition.util.MailVerify;
import org.glut.competition.util.VerifyCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import static org.glut.competition.util.MailVerify.MAILVERIFY;
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
@RequestMapping("/password")
public class PasswordController{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${mail.fromMail.sender}")
    private String sender;// 邮件发送者

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    HttpServletRequest request;

    @Autowired
    PasswordServiceImpl passwordService;

    @Autowired
    UserServiceImpl userService;

    /**
     * 获取图片验证码
     */
    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            VerifyCodeUtil randomValidateCode = new VerifyCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 发送邮箱验证码
    * */
    @RequestMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email) {
        MailVerify mailVerify=new MailVerify();
        SimpleMailMessage message = new SimpleMailMessage();
        String code = mailVerify.VerifyCode(6);    //随机数生成6位验证码
        message.setFrom(sender);
        message.setTo(email);
        message.setSubject("学科赛事管理系统");// 标题
        message.setText("【学科赛事管理系统】你的验证码为：" + code + "，有效时间为5分钟(若不是本人操作，可忽略该条邮件)");// 内容
        try {
            javaMailSender.send(message);
            logger.info("邮件发送成功！");
            mailVerify.saveCode(code,request);
            request.getSession().setAttribute("EMAILNAME",email);
            return "success";
        } catch (MailSendException e) {
            logger.error("目标邮箱不存在");
            return "false";
        } catch (Exception e) {
            logger.error("邮件发送异常！", e);
            return "failure";
        }
    }

    /*
     * 忘记密码时通过邮箱修改密码
     * */
    @RequestMapping("/check")
    @ResponseBody
    public CommonReturnType check( @RequestParam(name = "verifyCode")String verifyCode) throws BusinessException {
        String code=(String) this.request.getSession().getAttribute(RANDOMCODEKEY);
        if(!StringUtils.equals(code.toLowerCase(),verifyCode.toLowerCase())){
            throw new BusinessException(EmBusinessError.VERIFY_ERROR);
        }else
            return CommonReturnType.create();
    }

    @PostMapping(value = "/newForget-page")
    @ResponseBody
    public CommonReturnType getEmail(@RequestParam(name = "str")String str) throws BusinessException {
        String expr="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";
        String email=null;
        if (str.matches(expr)){
             email=str;
        }else {
            User user = userService.getUserShow(str);
             email=user.getEmail();
        }

        MailVerify mailVerify=new MailVerify();
        SimpleMailMessage message = new SimpleMailMessage();
        String emailCode = mailVerify.VerifyCode(6);    //随机数生成6位验证码
        message.setFrom(sender);
        message.setTo(email);
        message.setSubject("学科赛事管理系统");// 标题
        message.setText("【学科赛事管理系统】您现在的操作是重置密码，验证码为：" + emailCode + "，有效时间为5分钟(若不是本人操作，可忽略该条邮件)");// 内容
        try {
            javaMailSender.send(message);
            logger.info("邮件发送成功！");
            mailVerify.saveCode(emailCode,request);
            request.getSession().setAttribute("EMAILNAME",email);
            return CommonReturnType.create();
        } catch (MailSendException e) {
            logger.error("目标邮箱不存在");
            return CommonReturnType.create("目标邮箱不存在","50000");
        } catch (Exception e) {
            logger.error("邮件发送异常！", e);
            return CommonReturnType.create("邮件发送异常！","50000");
        }
    }

    @PostMapping(value = "/resetPassword-page")
    @ResponseBody
    public CommonReturnType forgetPassword(@RequestParam(name = "newPassword")String newPassword,@RequestParam(name = "mailCode")String mailCode) throws UnsupportedEncodingException, NoSuchAlgorithmException, BusinessException {
        Map<String, Object> resultMap =(Map<String, Object>) request.getSession().getAttribute("MAILVERIFY");
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
            return CommonReturnType.create("验证码已过期","50001");
        }
        EncryptionUtil encryptionUtil = new EncryptionUtil();
        String hash = encryptionUtil.enCodeByMd5(mailCode);
        if (!hash.equals(requestHash)){
            //验证码不正确，校验失败
            return CommonReturnType.create("验证码输入不正确","50002");
        }
        //校验成功
        if (newPassword==null||newPassword.equals("")){
            System.out.println("****************输入的新密码为空********************");
            throw new BusinessException(EmBusinessError.DATA_ISEMPTY);
        }
        String emailName = (String)request.getSession().getAttribute("EMAILNAME");
        passwordService.forgetPassword(emailName,newPassword);
        return CommonReturnType.create();
    }

    @RequestMapping(value = "/newforget")
    public String forget(){
        return "newForget";
    }

    @RequestMapping(value = "/resetpassword")
    public String reset(){
        return "resetPassword";
    }
}
