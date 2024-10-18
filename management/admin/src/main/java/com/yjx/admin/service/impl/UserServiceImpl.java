package com.yjx.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjx.admin.entity.SystemUser;
import com.yjx.admin.exception.PasswordNotCorrectException;
import com.yjx.admin.exception.UserNotFoundException;
import com.yjx.admin.exception.UserNotMatchEmailException;
import com.yjx.admin.mapper.SystemUserMapper;
import com.yjx.admin.pojo.params.LoginParams;
import com.yjx.admin.pojo.params.SignUpParams;
import com.yjx.admin.service.UserService;
import com.yjx.admin.util.Md5Util;
import com.yjx.common.redis.util.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import static com.yjx.admin.util.ConstUtil.USER_LOGIN_REDIS_KEY;
import static com.yjx.admin.util.ResponseCode.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements UserService {

    private final RedisUtil redisUtil;

    private final SystemUserMapper systemUserMapper;

    @Override
    public SystemUser login(LoginParams params) {
        // 获取rediskey

        String redisKey = String.format(USER_LOGIN_REDIS_KEY, params.getUsername());
        // 首先去redis里去查询
        if(redisUtil.hasKey(redisKey)){
            // 如果存在 直接返回存储的token
            String userInfo = redisUtil.get(redisKey).toString();
            SystemUser systemUser = JSONObject.parseObject(userInfo, SystemUser.class);
            // 用户存在 校验密码
            Boolean ifCorrect = checkPassword(params.getPassword(), systemUser);
            if(!ifCorrect){
                log.error("用户密码不正确");
                throw new PasswordNotCorrectException(USERNAME_OR_PASSWORD_ERROR);
            }
            return systemUser;
        }
        // 如果redis里不存在的话 直接从数据库里查询，然后存入redis
        // 根据用户id查询用户密码
        SystemUser user = systemUserMapper.getOneByUsername(params.getUsername());
        if(user == null){
            log.error("用户不存在");
            // 用户不存在抛出异常
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        // 用户存在 校验密码
        Boolean ifCorrect = checkPassword(params.getPassword(), user);
        if(!ifCorrect){
            log.info("用户密码不正确");
            throw new PasswordNotCorrectException(USERNAME_OR_PASSWORD_ERROR);
        }
        // 如果密码正确，存入redis
        redisUtil.set(redisKey, JSONObject.toJSONString(user), 3600);
        return user;
    }

    /**
     * 根据用户名更新用户密码
     * @param username 用户名
     * @param newPassword 新密码
     */
    @Override
    public void updatePassWordByUsername(String username, String newPassword) {
        // 校验用户是否存在
        SystemUser user = systemUserMapper.getOneByUsername(username);
        if(user == null){
            log.error("用户不存在");
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        // 如果用户存在，修改用户密码
        String newPasswordMd5 = Md5Util.encryptWithSalt(newPassword, user.getSalt());
        user.setPassword(newPasswordMd5);
        // 更新数据库
        systemUserMapper.updateById(user);
    }

    /**
     * 检查用户的用户名是否存在 以及用户名和邮箱是否一致
     * @param username 用户名
     * @param toEmail 用户的邮箱
     * @return boolean
     */
    @Override
    public boolean checkUser(String username, String toEmail) {
        // 判断用户是否存在
        SystemUser user = systemUserMapper.getOneByUsername(username);
        if (user == null){
            log.error("用户不存在,当前用户名:{}", username);
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        // 如果存在校验用户的邮箱是否一致
        if(!user.getEmail().equals(toEmail)){
            log.error("用户名与邮箱不匹配,当前用户名:{},邮箱:{}", username, toEmail);
            throw new UserNotMatchEmailException(USERNAME_NOT_MATCH_EMAIL);
        }
        return true;
    }

    /**
     * 用户注册功能
     * @param params 注册参数类
     * @return SystemUser 注册的用户信息
     */
    @Override
    public SystemUser signUp(SignUpParams params) {
        // 第一步校验用户是否存在以及邮箱是否被注册过
        SystemUser user = systemUserMapper.getOneByUsername(params.getUsername());
        if(user != null){
            log.error("用户已存在");
            throw new UserNotFoundException(USER_ALREADY_EXIST);
        }

        QueryWrapper<SystemUser> wrapper = new QueryWrapper<SystemUser>().eq("email", params.getEmail());
        if(systemUserMapper.selectCount(wrapper) > 0){
            log.error("邮箱已被注册");
            throw new UserNotFoundException(EMAIL_ALREADY_EXIST);
        }

        // 如果校验都通过了就创建一条用户记录
        SystemUser systemUser = new SystemUser();
        systemUser.fieldFill(params.getEmail(),params.getUsername(),params.getPassword());
        // 执行用户插入操作
        systemUserMapper.insert(systemUser);
        return systemUser;
    }

    /**
     * 校验密码是否正确
     * @param password 待校验密码
     * @param user 用户实体类
     * @return Boolean
     */
    private Boolean checkPassword(String password, SystemUser user){
        return Md5Util.verifyPasswordWithSalt(password,user.getSalt(),user.getPassword());
    }
}
