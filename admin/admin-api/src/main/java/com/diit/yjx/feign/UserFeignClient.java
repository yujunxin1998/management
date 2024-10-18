package com.diit.yjx.feign;

import com.diit.yjx.dto.UserAuthDTO;
import com.yjx.common.base.response.APIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author yjxbz
 */
@FeignClient(value = "admin")
public interface UserFeignClient {

    @GetMapping("/user/getUserByUsername")
    APIResponse<UserAuthDTO> getUserByUsername(@PathVariable("username") String username);
}