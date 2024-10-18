package com.yjx.auth;

import com.diit.yjx.feign.OAuthClientFeignClient;
import com.diit.yjx.feign.UserFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {UserFeignClient.class, OAuthClientFeignClient.class})
public class OAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class, args);
    }
}
