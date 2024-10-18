package com.yjx.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjx.admin.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> getRoleNameByUserId(Long userId);
}
