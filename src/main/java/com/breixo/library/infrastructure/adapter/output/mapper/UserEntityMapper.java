package com.breixo.library.infrastructure.adapter.output.mapper;

import com.breixo.library.domain.model.user.User;
import com.breixo.library.infrastructure.adapter.input.web.mapper.DateMapper;
import com.breixo.library.infrastructure.adapter.output.entities.UserEntity;

import com.breixo.library.infrastructure.mapper.UserStatusMapper;
import org.mapstruct.Mapper;

/** The Interface User Entity Mapper. */
@Mapper(componentModel = "spring", uses = {UserStatusMapper.class})
public interface UserEntityMapper {

    /**
     * To user.
     *
     * @param userEntity the user entity.
     * @return the user.
     */
    User toUser(UserEntity userEntity);
}
