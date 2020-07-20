package com.hjg.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest(args = {"--spring.mail.password=19921023abcde"})
public class EmailAppTest {

    @Autowired
    JavaMailSender javaMailSender;

    @Test
    public void testSendMail() {
        String from = "computer_cka@163.com";
        String to = "1179214342@qq.com";
        String subject = "springboot邮件";
        String content = "springboot email test";

        SimpleMailMessage message = new SimpleMailMessage();
        //收件人
        message.setFrom(from);
        message.setTo(to);
        message.setText(content);
        message.setSubject(subject);

        javaMailSender.send(message);
    }
}
