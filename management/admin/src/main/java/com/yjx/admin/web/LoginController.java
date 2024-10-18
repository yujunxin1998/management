package com.yjx.admin.web;


import com.yjx.admin.entity.SystemUser;
import com.yjx.admin.pojo.params.LoginParams;
import com.yjx.admin.pojo.params.SignUpParams;
import com.yjx.admin.service.UserService;
import com.yjx.common.base.response.APIResponse;
import com.yjx.admin.util.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Api(tags = "用户登录注册控制器")
@Slf4j
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    @ApiOperation("用户登录功能")
    public APIResponse<SystemUser> login(@RequestBody LoginParams params) {
        log.info("==== 执行用户登录 ====");
        SystemUser user = userService.login(params);
        log.info("==== 用户登录成功 ====");
        return new APIResponse<>(ResponseCode.SUCCESS,"登录成功",user);
    }

    @PostMapping("/signUp")
    @ApiOperation("注册接口")
    public APIResponse<?> signUp(@RequestBody SignUpParams params){
        SystemUser user = userService.signUp(params);
        return new APIResponse<>(ResponseCode.SUCCESS,user);
    }

    @GetMapping("/unregister")
    @ApiOperation("账户注销功能")
    public APIResponse<?> unregister(){
        return new APIResponse<>(ResponseCode.SUCCESS);
    }
}
