package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/** The Interface Get Loans V1 Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetLoansV1RequestMapper {

    /** To Loan search criteria command. */
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Mapping(source = "status", target = "statusId")
    LoanSearchCriteriaCommand toLoanSearchCriteriaCommand(Integer id, Integer userId, Integer bookId, Integer status);
}
