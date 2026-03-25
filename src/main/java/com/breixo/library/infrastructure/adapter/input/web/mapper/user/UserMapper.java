package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import java.util.List;

import com.breixo.library.domain.model.user.User;
import com.breixo.library.infrastructure.adapter.input.web.dto.UserV1Dto;
import com.breixo.library.infrastructure.mapper.UserStatusMapper;

import org.mapstruct.Mapper;

/** The Interface User Mapper. */
@Mapper(componentModel = "spring", uses = {UserStatusMapper.class})
public interface UserMapper {

    /**
     * To user v1.
     *
     * @param user The user.
     * @return The user v1 dto.
     */
    UserV1Dto toUserV1(User user);

    /**
     * To user v1 list.
     *
     * @param users The users.
     * @return The user v1 dto list.
     */
    List<UserV1Dto> toUserV1List(List<User> users);
}
