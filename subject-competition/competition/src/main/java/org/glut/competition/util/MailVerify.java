package org.glut.competition.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MailVerify {


    private static Map<String, Object> resultMap = new HashMap<>();
    public static  String MAILVERIFY;

/*    @GetMapping("/register")
    public String register() {
        return "tourist/register";
    }*/


    public String VerifyCode(int n) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            int ran1 = r.nextInt(10);
            sb.append(String.valueOf(ran1));
        }
//        System.out.println(sb);
        return sb.toString();
    }

    //保存邮箱验证码和时间
    public void saveCode(String code, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        String currentTime = sf.format(c.getTime());// 生成5分钟后时间，用户校验是否过期
        System.out.println(currentTime);
        EncryptionUtil encryptionUtil = new EncryptionUtil();
        String hash = encryptionUtil.enCodeByMd5(code);//生成MD5值
        resultMap.put("hash", hash);
        resultMap.put("tamp", currentTime);
        //将加密的验证码放入session中
        request.getSession().setAttribute("MAILVERIFY",resultMap);

    }


}
