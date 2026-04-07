package com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostLoanRequestV1Request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Post Loan Request Request Mapper. */
@Mapper(componentModel = "spring")
public interface PostLoanRequestRequestMapper {

    /**
     * To create loan request command.
     *
     * @param requesterId              The requester id (from X-Requester-Id header).
     * @param postLoanRequestV1Request the post loan request V1 request.
     * @return the create loan request command.
     */
    @Mapping(source = "requesterId", target = "requesterId")
    @Mapping(source = "postLoanRequestV1Request.userId", target = "userId")
    @Mapping(source = "postLoanRequestV1Request.bookId", target = "bookId")
    CreateLoanRequestCommand toCreateLoanRequestCommand(Integer requesterId, PostLoanRequestV1Request postLoanRequestV1Request);
}
