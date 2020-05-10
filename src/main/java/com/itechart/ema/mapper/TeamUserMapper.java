package com.itechart.ema.mapper;

import com.itechart.ema.entity.TeamUserEntity;
import com.itechart.generated.model.RestTeamUser;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = {
        CommonMapper.class,
})
public interface TeamUserMapper {

    TeamUserMapper TEAM_USER_MAPPER = getMapper(TeamUserMapper.class);

    RestTeamUser toRestTeamUser(TeamUserEntity source);

}
