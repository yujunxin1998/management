package com.yjx.admin.service.impl;

import com.yjx.admin.service.EmailService;
import com.yjx.common.redis.util.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.yjx.admin.util.ConstUtil.*;

@Slf4j
@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final RedisUtil redisUtil;

    private final JavaMailSender javaMailSender;

    /**
     * 存储邮箱验证码和过期时间
     * @param email 邮箱
     * @param code 验证码
     */
    public void storeVerificationCode(String email, String code) {
        // 向redis中存储验证码和过期时间
        if(!redisUtil.hasKey(email)){
            // 设置redis的key
            String redisKey = String.format(EMAIL_SEND_REDIS_KEY, email);
            // 设置验证码过期时间
            long expireTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);
            redisUtil.set(redisKey, code, expireTime);
        }else{
            log.error("当前校验码已经存在");
            throw new RuntimeException();
        }
    }

    /**
     * 校验验证码
     * @param email 用户邮箱
     * @param code 验证码
     * @return Boolean 是否成功
     */
    public Boolean verifyCode(String email, String code) {
        // 判断当前redis中是否存在该邮箱对应的验证码和过期时间
        String redisKey = String.format(EMAIL_SEND_REDIS_KEY, email);
        if(!redisUtil.hasKey(redisKey)){
            return false;
        }
        String storedCode = (String) redisUtil.get(redisKey);
        // 校验验证码是否一致
        return storedCode.equals(code);
    }


    /**
     * 生成6位验证码并发送邮件给用户
     * @param toEmail
     * @return Void
     * @throws MessagingException
     */
    public void sendVerificationCode(String toEmail) throws MessagingException {
        // 生成6位随机验证码
        String verificationCode = generateVerificationCode();
        log.info("当前用户邮箱为:{},生成的校验码为:{}", toEmail, verificationCode);

        log.info("开始创建邮件消息");
        // 创建邮件消息
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(EMAIL_FROM);
        helper.setTo(toEmail);
        helper.setSubject(EMAIL_TEMPLATE_NAME);
        helper.setText(EMAIL_TEMPLATE.replace(EMAIL_SUBJECT, verificationCode));

        // 发送邮件
        javaMailSender.send(message);
        // 发送成功后存储验证码
        storeVerificationCode(toEmail, verificationCode);
    }

    /**
     * 生成随机6位验证码
     * @return String
     */
    private String generateVerificationCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
