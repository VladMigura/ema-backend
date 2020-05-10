package com.itechart.ema.mapper;

import com.itechart.ema.entity.ProjectUserEntity;
import com.itechart.generated.model.RestProjectUser;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = {
        CommonMapper.class,
})
public interface ProjectUserMapper {

    ProjectUserMapper PROJECT_USER_MAPPER = getMapper(ProjectUserMapper.class);

    RestProjectUser toRestProjectUser(ProjectUserEntity source);

}
