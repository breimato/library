package com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest;

import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanRequestV1Response;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Loan Request Response Mapper. */
@Mapper(componentModel = "spring", uses = LoanRequestMapper.class)
public interface LoanRequestResponseMapper {

    /**
     * To loan request V1 response.
     *
     * @param loanRequest the loan request.
     * @return the loan request V1 response dto.
     */
    @Mapping(target = "loanRequest", source = "loanRequest")
    LoanRequestV1Response toLoanRequestV1Response(LoanRequest loanRequest);
}
