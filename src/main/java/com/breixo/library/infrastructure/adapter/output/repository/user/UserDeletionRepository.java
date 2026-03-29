package com.breixo.library.infrastructure.adapter.output.repository.user;

import com.breixo.library.domain.port.output.user.UserDeletionPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mybatis.UserMyBatisMapper;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class User Deletion repository. */
@Component
@RequiredArgsConstructor
public class UserDeletionRepository implements UserDeletionPersistencePort {

    /** The user my batis mapper. */
    private final UserMyBatisMapper userMyBatisMapper;

    /** {@inheritDoc} */
    @Override
    public void execute(@NotNull final Integer id) {
        this.userMyBatisMapper.delete(id);
    }
}
