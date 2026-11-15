package com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/** The Interface Get LoanRequests V1 Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetLoanRequestsV1RequestMapper {

    /** To LoanRequest search criteria command. */
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Mapping(source = "status", target = "statusId")
    LoanRequestSearchCriteriaCommand toLoanRequestSearchCriteriaCommand(Integer id, Integer userId, Integer bookId, Integer status);
}
