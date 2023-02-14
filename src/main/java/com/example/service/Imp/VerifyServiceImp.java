package com.example.service.Imp;

import com.example.service.VerifyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Service
public class VerifyServiceImp implements VerifyService {

    @Resource
    JavaMailSender sender;
    @Resource
    StringRedisTemplate template;
    @Value("${spring.mail.username}")
    String from;
    @Override
    public void sendVerifyCode(String mail) {
        System.out.println("发送邮件中...");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("注册验证");
        Random random = new Random();
        int code = random.nextInt(899999)+100000;
        template.opsForValue().set("verify:code"+mail,code+"");
        message.setText("你的注册验证码为："+code+"三分钟内有效。");
        message.setTo(mail);
        message.setFrom("zjp_student@163.com");
        sender.send(message);

    }

    @Override
    public boolean doVerify(String email, String code) {
        String string = template.opsForValue().get("verify:code"+email);
        if(string==null){
            return false;
        }
        return string.equals(code);
    }
}
