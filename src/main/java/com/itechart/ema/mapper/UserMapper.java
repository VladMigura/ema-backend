package com.itechart.ema.mapper;

import com.itechart.ema.entity.UserEntity;
import com.itechart.ema.entity.enums.UserRoleEntity;
import com.itechart.ema.security.impl.UserDetailsImpl;
import com.itechart.generated.model.RestSignUpRequest;
import com.itechart.generated.model.RestUser;
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
    @Mapping(target = "authorities", ignore = true)
    UserDetailsImpl toUserDetails(UUID userId, UserRoleEntity role);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "authorities", ignore = true)
    UserDetailsImpl toUserDetails(UserEntity userEntity);

    RestUser toRestUser(UserEntity source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    UserEntity toUserEntity(RestSignUpRequest source);

}
