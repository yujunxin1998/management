package com.yjx.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjx.admin.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    SystemUser getOneByUsername(String username);
}
