package com.yjx.admin.service;

import javax.mail.MessagingException;

public interface EmailService {

    void sendVerificationCode(String toEmail) throws MessagingException;

    Boolean verifyCode(String email, String code);
}
