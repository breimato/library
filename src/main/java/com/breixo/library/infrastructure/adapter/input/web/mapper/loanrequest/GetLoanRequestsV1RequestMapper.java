package com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetLoanRequestsV1Request;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/** The Interface Get Loan Requests V1 Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetLoanRequestsV1RequestMapper {

    /**
     * To loan request search criteria command.
     *
     * @param getLoanRequestsV1Request the get loan requests V1 request.
     * @return the loan request search criteria command.
     */
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Mapping(source = "status", target = "statusId")
    LoanRequestSearchCriteriaCommand toLoanRequestSearchCriteriaCommand(GetLoanRequestsV1Request getLoanRequestsV1Request);
}
