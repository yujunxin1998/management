package com.yjx.admin.web;

import com.yjx.admin.pojo.params.VerifyCodeAndUpdatePassword;
import com.yjx.admin.service.EmailService;
import com.yjx.admin.service.UserService;
import com.yjx.admin.util.ResponseCode;
import com.yjx.common.base.response.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@Api(tags = "忘记密码控制器")
@Slf4j
@RequestMapping("/forgetPwd")
@RestController
@AllArgsConstructor
public class ForgetPasswordController {

    private final EmailService emailService;

    private final UserService userService;

    @PostMapping("/submitEmail")
    @ApiOperation("发送邮件")
    public APIResponse<?> submitEmail(@Param("toEmail") String toEmail, @Param("username")String username){
        try {
            // 首先校验用户是否存在
            if(!userService.checkUser(username,toEmail)){
                log.error("用户名和邮箱校验失败");
            }
            // 如果
            emailService.sendVerificationCode(toEmail);
            return new APIResponse<>(ResponseCode.SUCCESS);
        } catch (MessagingException e) {
            log.error("邮件服务发送异常",e);
            return new APIResponse<>(ResponseCode.FAIL);
        }
    }

    @PostMapping("/verifyCodeAndUpdatePassword")
    @ApiOperation("校验邮箱验证码")
    public APIResponse<?> verifyCodeAndUpdatePassword(@RequestBody VerifyCodeAndUpdatePassword params){
        // 校验验证码是否正确
        Boolean result = emailService.verifyCode(params.getToEmail(), params.getCode());
        if(result){
            // 继续执行更新密码操作
            userService.updatePassWordByUsername(params.getUsername(), params.getNewPassword());
            return new APIResponse<>(ResponseCode.SUCCESS);
        }else{
            return new APIResponse<>(ResponseCode.VERIFY_CODE_ERROR,"验证码校验失败");
        }
    }
}
