package org.glut.competition.util;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
/*
* 对密码进行加密
* */
public class EncryptionUtil {

    public String enCodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定一个计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        Base64.Encoder base64Encoder = Base64.getEncoder();
        //加密字符串
        String pwd = base64Encoder.encodeToString(md5.digest(str.getBytes("UTF-8")));
        return pwd;
    }
}
