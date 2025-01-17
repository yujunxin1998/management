package com.yjx.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjx.admin.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    SystemUser getOneByUsername(String username);
}
