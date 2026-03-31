package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import com.breixo.library.domain.command.loan.UpdateLoanRenewCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchLoanRenewV1Request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Patch Loan Renew Request Mapper. */
@Mapper(componentModel = "spring")
public interface PatchLoanRenewRequestMapper {

    /**
     * To update loan renew command.
     *
     * @param id                      the id
     * @param patchLoanRenewV1Request the patch loan renew V1 request
     * @return the update loan renew command
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "patchLoanRenewV1Request.dueDate", target = "dueDate")
    UpdateLoanRenewCommand toUpdateLoanRenewCommand(Integer id, PatchLoanRenewV1Request patchLoanRenewV1Request);
}
