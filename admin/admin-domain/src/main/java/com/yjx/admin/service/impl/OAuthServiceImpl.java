package com.yjx.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjx.admin.entity.OauthClient;
import com.yjx.admin.mapper.OauthClientMapper;
import com.yjx.admin.service.OAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yjxbz
 */
@Service
@Slf4j
@AllArgsConstructor
public class OAuthServiceImpl extends ServiceImpl<OauthClientMapper, OauthClient> implements OAuthService {

    private final OauthClientMapper oauthClientMapper;


}
