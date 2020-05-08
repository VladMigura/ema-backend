package com.itechart.ema.mapper;

import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;
import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface CommonMapper {

    CommonMapper COMMON_MAPPER = getMapper(CommonMapper.class);

    default OffsetDateTime toOffsetDateTime(final Instant source) {
        return source == null ? null : OffsetDateTime.ofInstant(source, UTC);
    }

}
