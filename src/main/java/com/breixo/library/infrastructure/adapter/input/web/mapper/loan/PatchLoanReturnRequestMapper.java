package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import com.breixo.library.domain.command.loan.ReturnLoanCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchLoanReturnV1Request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Patch Loan Return Request Mapper. */
@Mapper(componentModel = "spring")
public interface PatchLoanReturnRequestMapper {

    /**
     * To return loan command.
     *
     * @param id                    the id
     * @param patchLoanReturnV1Request the patch loan return V1 request
     * @return the return loan command
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "patchLoanReturnV1Request.returnDate", target = "returnDate")
    ReturnLoanCommand toReturnLoanCommand(Integer id, PatchLoanReturnV1Request patchLoanReturnV1Request);
}
