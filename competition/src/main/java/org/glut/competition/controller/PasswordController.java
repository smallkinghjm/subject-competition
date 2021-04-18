package org.glut.competition.controller;


import org.glut.competition.service.impl.PasswordServiceImpl;
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
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        message.setSubject("学科赛事系统");// 标题
        message.setText("【学科赛事系统】你的验证码为：" + code + "，有效时间为5分钟(若不是本人操作，可忽略该条邮件)");// 内容
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


}
