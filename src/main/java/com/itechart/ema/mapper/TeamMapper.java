package com.itechart.ema.mapper;

import com.itechart.ema.entity.TeamEntity;
import com.itechart.generated.model.RestTeam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = {
        CommonMapper.class,
        UserMapper.class,
})
public interface TeamMapper {

    TeamMapper TEAM_MAPPER = getMapper(TeamMapper.class);

    RestTeam toRestTeam(TeamEntity source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "managerId", ignore = true)
    TeamEntity toTeamEntity(RestTeam source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "managerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    TeamEntity updateEntity(RestTeam source, @MappingTarget TeamEntity target);

}
