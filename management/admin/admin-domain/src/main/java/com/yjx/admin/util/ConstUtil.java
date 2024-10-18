package com.yjx.admin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 常数参数类
 * @Author : yu.
 */
public class ConstUtil {

    /** redis存储用户登录信息key */
    public static final String USER_LOGIN_REDIS_KEY = "management:admin:%s";

    /** redis存储邮件发送信息key */
    public static final String EMAIL_SEND_REDIS_KEY = "management:email:send:%s";

    /** 邮件发送者 */
    public static final String EMAIL_FROM = "yjxbzcl@163.com";

    /** 日期格式化 */
    public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** 邮箱邮件模版占位符 */
    public static final String EMAIL_SUBJECT = "${verificationCode}";

    /** 邮件模版 */
    public static final String EMAIL_TEMPLATE = "尊敬的用户，\n" +
            "\n" +
            "您好！\n" +
            "\n" +
            "您正在尝试进行邮箱验证，本次操作的验证码为：${verificationCode}。\n" +
            "\n" +
            "为了确保您的账号安全，请勿将验证码泄露给他人。\n" +
            "\n" +
            "验证码有效期为10分钟，如未在有效期内完成验证，验证码将失效。\n" +
            "\n" +
            "如果您未进行此操作，请忽略此邮件。\n" +
            "\n" +
            "如有任何疑问，请联系我们的客服。\n" +
            "\n" +
            "祝您生活愉快！\n" +
            "\n" +
            "敬上，\n" +
            "于军昕技术责任有限公司\n" +
            TIMESTAMP_FORMAT.format(new Date()) +"\n";

    /** 邮件模版名 */
    public static final String EMAIL_TEMPLATE_NAME = "于军昕技术公司账号验证";
}
