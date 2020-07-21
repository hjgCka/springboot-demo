package com.hjg.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;

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

    /**
     * 经过测试发现connectiontimeout到达后，会再重试一次。
     */
    @Test
    public void testHtmlMail() {
        try {
            String from = "computer_cka@163.com";
            String to = "1179214342@qq.com";
            String subject = "springboot邮件2";
            String content = "<h2>springboot email test</h2>";

            //用于mimeMessage.setRecipients
            InternetAddress address = new InternetAddress(to);
            InternetAddress addresses [] = {address};

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(from);
            //重载方法较多，可以用String或String[]
            helper.setTo(addresses);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(LocalTime.now());
        }
    }

    @Test
    public void testHtmlMail2() {
        try {
            String from = "computer_cka@163.com";
            String to = "1179214342@qq.com";
            String subject = "springboot邮件2";
            String content = "<h2>springboot email test</h2>";

            //用于mimeMessage.setRecipients
            InternetAddress address = new InternetAddress(to);
            InternetAddress addresses [] = {address};

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setFrom(from);
            mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
            mimeMessage.setSubject(subject);
            mimeMessage.setText(content, StandardCharsets.UTF_8.toString(), "html");

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(LocalTime.now());
        }
    }
}
