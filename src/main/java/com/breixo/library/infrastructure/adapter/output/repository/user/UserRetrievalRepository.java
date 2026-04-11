package com.breixo.library.infrastructure.adapter.output.repository.user;

import java.util.List;
import java.util.Optional;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.user.UserEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.UserMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class User Retrieval repository. */
@Component
@RequiredArgsConstructor
public class UserRetrievalRepository implements UserRetrievalPersistencePort {

    /** The user my batis mapper. */
    private final UserMyBatisMapper userMyBatisMapper;

    /** The user entity mapper. */
    private final UserEntityMapper userEntityMapper;

    /** {@inheritDoc} */
    @Override
    public Optional<User> findById(@NotNull final Integer id) {

        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(id).build();
        final var userList = this.find(userSearchCriteriaCommand);

        return userList.isEmpty() ? Optional.empty() : Optional.of(userList.getFirst());
    }

    /** {@inheritDoc} */
    @Override
    public List<User> find(@Valid @NotNull final UserSearchCriteriaCommand userSearchCriteriaCommand) {

        final var userEntities = this.userMyBatisMapper.find(userSearchCriteriaCommand);

        return this.userEntityMapper.toUserList(userEntities);
    }
}
