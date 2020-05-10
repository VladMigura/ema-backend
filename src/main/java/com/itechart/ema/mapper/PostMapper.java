package com.itechart.ema.mapper;

import com.itechart.ema.entity.PostEntity;
import com.itechart.generated.model.RestPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = {
        CommonMapper.class
})
public interface PostMapper {

    PostMapper POST_MAPPER = getMapper(PostMapper.class);

    RestPost toRestPost(PostEntity source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "authorId", ignore = true)
    PostEntity toPostEntity(RestPost source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "authorId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    PostEntity updateEntity(RestPost source, @MappingTarget PostEntity target);

}
