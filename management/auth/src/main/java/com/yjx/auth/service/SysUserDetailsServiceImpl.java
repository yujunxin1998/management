package com.yjx.auth.service;


import com.diit.yjx.dto.UserAuthDTO;
import com.diit.yjx.feign.UserFeignClient;
import com.yjx.auth.entity.SystemUserDetails;
import com.yjx.auth.enums.PasswordEncoderTypeEnum;
import com.yjx.common.base.response.APIResponse;
import com.yjx.common.base.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("SysUSerDetailsServiceImpl")
@Slf4j
@RequiredArgsConstructor
public class SysUserDetailsServiceImpl implements UserDetailsService {


    private final UserFeignClient userFeignClient;

    /**
     * 获取用户信息方法
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 后面需要修改成从管理端获取用户信息
        // 后面从管理端获取用户信息
        APIResponse<UserAuthDTO> result = userFeignClient.getUserByUsername(username);
        SystemUserDetails userDetails = null;
        if (ResponseCode.SUCCESS.getCode() == result.getCode()) {
            UserAuthDTO user = (UserAuthDTO) result.getData();
            if (null != user) {
                userDetails = SystemUserDetails.builder()
                        .userId(user.getUserId())
                        .username(user.getUsername())
                        .authorities(handleRoles(user.getRoles()))
                        .enabled(user.getStatus() == 1)
                        .password(PasswordEncoderTypeEnum.BCRYPT.getPrefix() + user.getPassword())
                        .build();
            }
        }
//        SysUserDetails userDetails = loadUser(username);
        if(!userDetails.isEnabled()){
            throw new DisabledException("该用户已被禁用！");
        }else if(!userDetails.isAccountNonExpired()){
            throw new AccountExpiredException("该账户已经过期！");
        }else if(!userDetails.isAccountNonLocked()){
            throw new LockedException("该账户已被锁定！");
        }
        return userDetails;
    }

    /**
     * 这个地方是在没有后台管理端的时候，用来代替管理端提供信息的方法
     * @param username
     * @return
     */
    private SystemUserDetails loadUser(String username){
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("admin"));
        return SystemUserDetails.builder()
                .userId(1L)
                .enabled(true)
                .authorities(authorities)
                .password(PasswordEncoderTypeEnum.BCRYPT.getPrefix() + new BCryptPasswordEncoder().encode("123456")).build();
    }

    private Collection<SimpleGrantedAuthority> handleRoles(List<String> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
