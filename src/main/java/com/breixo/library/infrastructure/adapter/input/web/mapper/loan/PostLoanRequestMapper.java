package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostLoanV1Request;

import org.mapstruct.Mapper;

/** The Interface Post Loan Request Mapper. */
@Mapper(componentModel = "spring")
public interface PostLoanRequestMapper {

    /**
     * To create loan command.
     *
     * @param postLoanV1Request the post loan V1 request
     * @return the create loan command
     */
    CreateLoanCommand toCreateLoanCommand(PostLoanV1Request postLoanV1Request);
}
