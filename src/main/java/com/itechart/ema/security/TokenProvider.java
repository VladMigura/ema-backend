package com.itechart.ema.security;

import com.itechart.ema.entity.UserEntity;
import com.itechart.ema.entity.enums.UserRoleEntity;
import com.itechart.generated.model.RestToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface TokenProvider {

    RestToken createToken(UserEntity userEntity);

    RestToken createToken(UserDetails userDetails);

    RestToken refreshToken(String refreshToken);

    UserDetails buildUserDetailsByToken(String token);

    UUID getUserIdByToken(String token);

    UserRoleEntity getUserRoleByToken(String token);

}
