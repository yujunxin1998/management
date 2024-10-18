package com.yjx.gateway.util;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NacosServiceUtil {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    public boolean isServiceHealthy(String serviceName) {
        try {
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
            List<Instance> instances = namingService.getAllInstances(serviceName);
            for (Instance instance : instances) {
                if (instance.isHealthy()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}