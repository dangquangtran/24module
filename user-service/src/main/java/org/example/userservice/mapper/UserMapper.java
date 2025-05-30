package org.example.userservice.mapper;

import org.example.userservice.dto.user.CreateUserDTO;
import org.example.userservice.dto.user.RegisterRequestDTO;
import org.example.userservice.dto.user.UpdateUserDTO;
import org.example.userservice.dto.user.UserVM;
import org.example.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserVM toUserVM(User user);
    @Mapping(target = "role", source = "role")
    User toUser(CreateUserDTO dto);
    User toUser(RegisterRequestDTO dto);
    void updateUserFromDTO(UpdateUserDTO dto, @MappingTarget User user);
    List<UserVM> toVMList(List<User> users);
    org.example.commonlibrary.dto.UserVM toUserVMDubbo(User user);
}
