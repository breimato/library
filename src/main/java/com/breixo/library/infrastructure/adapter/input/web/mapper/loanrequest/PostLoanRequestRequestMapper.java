package com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostLoanRequestV1Request;

import org.mapstruct.Mapper;

/** The Interface Post Loan Request Request Mapper. */
@Mapper(componentModel = "spring")
public interface PostLoanRequestRequestMapper {

    /**
     * To create loan request command.
     *
     * @param postLoanRequestV1Request the post loan request V1 request.
     * @return the create loan request command.
     */
    CreateLoanRequestCommand toCreateLoanRequestCommand(PostLoanRequestV1Request postLoanRequestV1Request);
}
