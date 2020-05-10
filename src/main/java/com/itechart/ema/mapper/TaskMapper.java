package com.itechart.ema.mapper;

import com.itechart.ema.entity.TaskEntity;
import com.itechart.generated.model.RestTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = {
        CommonMapper.class,
        UserMapper.class,
})
public interface TaskMapper {

    TaskMapper TASK_MAPPER = getMapper(TaskMapper.class);

    RestTask toRestTask(TaskEntity source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "devOwner", ignore = true)
    @Mapping(target = "devOwnerId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdById", ignore = true)
    TaskEntity toTaskEntity(RestTask source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "devOwner", ignore = true)
    @Mapping(target = "devOwnerId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdById", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    TaskEntity updateEntity(RestTask source, @MappingTarget TaskEntity target);

}
