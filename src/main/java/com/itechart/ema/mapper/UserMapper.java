package com.itechart.ema.mapper;

import com.itechart.ema.entity.enums.UserRoleEntity;
import com.itechart.ema.security.impl.UserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = {
        CommonMapper.class
})
public interface UserMapper {

    UserMapper USER_MAPPER = getMapper(UserMapper.class);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "role", source = "role")
    UserDetailsImpl toUserDetails(UUID userId, UserRoleEntity role);

}
