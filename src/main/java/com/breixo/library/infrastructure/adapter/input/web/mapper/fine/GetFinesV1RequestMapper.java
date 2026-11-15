package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/** The Interface Get Fines V1 Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetFinesV1RequestMapper {

    /** To Fine search criteria command. */
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Mapping(source = "status", target = "statusId")
    FineSearchCriteriaCommand toFineSearchCriteriaCommand(Integer id, Integer loanId, Integer status);
}
