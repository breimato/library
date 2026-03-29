package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetLoansV1Request;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/** The Interface Get Loans V1 Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetLoansV1RequestMapper {

    /**
     * To loan search criteria command.
     *
     * @param getLoansV1Request the get loans V1 request.
     * @return the loan search criteria command.
     */
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Mapping(source = "status", target = "statusId")
    LoanSearchCriteriaCommand toLoanSearchCriteriaCommand(GetLoansV1Request getLoansV1Request);
}
