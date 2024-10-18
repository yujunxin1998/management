package com.yjx.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diit.yjx.dto.UserAuthDTO;
import com.yjx.admin.entity.SystemUser;
import com.yjx.admin.pojo.params.LoginParams;
import com.yjx.admin.pojo.params.SignUpParams;

public interface UserService extends IService<SystemUser> {
    SystemUser login(LoginParams params);

    void updatePassWordByUsername(String username, String newPassword);

    boolean checkUser(String username, String toEmail);

    SystemUser signUp(SignUpParams params);

    UserAuthDTO getUserByUsername(String username);
}
