package com.stpp.movies.services.user;

import com.stpp.movies.dto.user.UserEditRequestDto;
import com.stpp.movies.dto.user.UserRequestDto;
import com.stpp.movies.dto.user.UserResponseDto;
import com.stpp.movies.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Named("userToResponseDto")
  @Mapping(target = "id", source = "id")
  @Mapping(target = "username", source = "user", qualifiedByName = "mapRealUsername")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "dateCreated", source = "dateCreated")
  @Mapping(target = "dateModified", source = "dateModified")
  @Mapping(target = "role", ignore = true)
  UserResponseDto userToResponseDto(User user);

  @Named("userToResponseDtoWithRoles")
  @Mapping(target = "id", source = "id")
  @Mapping(target = "username", source = "user", qualifiedByName = "mapRealUsername")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "dateCreated", source = "dateCreated")
  @Mapping(target = "dateModified", source = "dateModified")
  @Mapping(target = "role", source = "role")
  UserResponseDto userToResponseDtoWithRoles(User user);

  @Named("requestDtoToUser")
  @Mapping(target = "username", source = "username")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "password", source = "password")
  @Mapping(target = "role", expression = "java(com.stpp.movies.enumerators.Role.USER)")
  User requestDtoToUser(UserRequestDto userRequestDto);

  @Named("userEditRequestDtoToUser")
  @Mapping(target = "username", source = "username")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "password", ignore = true)
  void userEditRequestDtoToUserAdmin(UserEditRequestDto userRequestDto, @MappingTarget User user);

  @Named("userEditRequestDtoToUser")
  @Mapping(target = "username", source = "username")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "role", ignore = true)
  void userEditRequestDtoToUserUser(UserEditRequestDto userRequestDto, @MappingTarget User user);

  @Named("mapRealUsername")
  default String mapRealUsername(User user) {
    return user.realUsername();
  }
}
