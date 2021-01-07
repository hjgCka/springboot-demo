package com.hjg.email.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/7
 */
@Service
public class MailSender {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public void sendPlainTextMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo("1179214342@qq.com");
        simpleMailMessage.setSubject("Test");
        simpleMailMessage.setText("Hello world");

        javaMailSender.send(simpleMailMessage);
    }

    public void sendMimeMail() throws MessagingException {
        //邮件主题
        String subject = "测试";

        String[] receivers = {"1179214342@qq.com"};

        InternetAddress[] addresses = new InternetAddress[receivers.length];
        for(int i=0; i<receivers.length; i++) {
            InternetAddress address = new InternetAddress(receivers[i]);
            addresses[i] = address;
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setRecipients(Message.RecipientType.TO, addresses);
        message.setSubject(subject);
        message.setText("Hello world");

        javaMailSender.send(message);
    }

    public void sendMimeMailByHelper() throws MessagingException {
        //邮件主题
        String subject = "测试";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject(subject);
        helper.setTo("1179214342@qq.com");
        helper.setText("Hello world");

        javaMailSender.send(message);
    }


}
