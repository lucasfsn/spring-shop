package com.example.demo.mapper.user;

import com.example.demo.dto.user.SearchUserResDto;
import com.example.demo.dto.user.UserResDto;
import com.example.demo.dto.user.UserSignupReqDto;
import com.example.demo.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
  UserResDto toDto(User user);

  SearchUserResDto toSearchDto(User user);

  User toEntity(UserSignupReqDto userSignupReqDto);
}
