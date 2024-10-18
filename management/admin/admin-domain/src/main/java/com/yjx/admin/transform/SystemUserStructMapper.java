package com.yjx.admin.transform;

import com.diit.yjx.dto.UserAuthDTO;
import com.yjx.admin.entity.SystemUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SystemUserStructMapper {
    SystemUserStructMapper INSTANCE = Mappers.getMapper(SystemUserStructMapper.class);

    @Mappings({
        @Mapping(source = "id", target = "userId"),
        @Mapping(source = "password", target = "password"),
        @Mapping(source = "username", target = "username")
    })
    UserAuthDTO toUserAuthDTO(SystemUser systemUser);
}
