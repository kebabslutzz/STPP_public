package com.stpp.movies.services.file;

import com.stpp.movies.dto.file.FileResponseDto;
import com.stpp.movies.entities.Poster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileMapper {
  FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);

  @Mapping(source = "poster", target = "poster", ignore = true)
  @Mapping(source = "id", target = "id")
  FileResponseDto posterToResponseDtoForCreate(Poster poster);

  @Mapping(source = "poster", target = "poster")
  @Mapping(source = "id", target = "id", ignore = true)
  FileResponseDto posterToResponseDto(Poster poster);
}
