package com.itechart.ema.mapper;

import com.itechart.ema.entity.ProjectEntity;
import com.itechart.generated.model.RestProject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = {
        CommonMapper.class,
        UserMapper.class,
})
public interface ProjectMapper {

    ProjectMapper PROJECT_MAPPER = getMapper(ProjectMapper.class);

    RestProject toRestProject(ProjectEntity source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "managerId", ignore = true)
    @Mapping(target = "scrumMaster", ignore = true)
    @Mapping(target = "scrumMasterId", ignore = true)
    ProjectEntity toProjectEntity(RestProject source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "managerId", ignore = true)
    @Mapping(target = "scrumMaster", ignore = true)
    @Mapping(target = "scrumMasterId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    ProjectEntity updateEntity(RestProject source, @MappingTarget ProjectEntity target);

}
