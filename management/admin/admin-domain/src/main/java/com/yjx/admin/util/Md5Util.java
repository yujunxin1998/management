package com.yjx.admin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class Md5Util {
    public static void main(String[] args) {
        String salt = UUID.randomUUID().toString().replaceAll("-","").substring(0,6);
        System.out.println(salt);
        System.out.println(encryptWithSalt("12345",salt));
    }

    /**
     * 加密方法
     * @param password 待加密密码
     * @param salt 盐值
     * @return String 加密后的密码
     */
    public static String encryptWithSalt(String password,String salt){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());
            byte[] digest = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("找不到MD5码算法实现类无法进行加密", e);
        }
    }

    /**
     * 解密方法
     * @param password 待校验密码
     * @param salt 盐值
     * @param encryptedPassword 加密后的密码
     * @return Boolean
     */
    public static boolean verifyPasswordWithSalt(String password, String salt, String encryptedPassword) {
        String encrypted = encryptWithSalt(password, salt);
        return encrypted.equals(encryptedPassword);
    }
}
