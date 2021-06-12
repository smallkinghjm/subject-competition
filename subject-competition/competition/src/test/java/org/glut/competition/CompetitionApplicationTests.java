package org.glut.competition;


import org.glut.competition.util.EncryptionUtil;


import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class CompetitionApplicationTests {


	@Test
	public void contextLoads() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		EncryptionUtil encryptionUtil=new EncryptionUtil();
		String s = encryptionUtil.enCodeByMd5("admin");
		System.out.println(s);
	}

}
