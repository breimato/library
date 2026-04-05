package com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest;

import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchLoanRequestV1Request;
import com.breixo.library.infrastructure.mapper.LoanRequestStatusMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Patch Loan Request Request Mapper. */
@Mapper(componentModel = "spring", uses = {LoanRequestStatusMapper.class})
public interface PatchLoanRequestRequestMapper {

    /**
     * To update loan request command.
     *
     * @param id                       The loan request identifier.
     * @param patchLoanRequestV1Request The patch loan request V1 request.
     * @return The update loan request command.
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "patchLoanRequestV1Request.status", target = "status")
    @Mapping(source = "patchLoanRequestV1Request.rejectionReason", target = "rejectionReason")
    @Mapping(target = "authenticatedUserId", ignore = true)
    @Mapping(target = "authenticatedUserRole", ignore = true)
    UpdateLoanRequestCommand toUpdateLoanRequestCommand(Integer id, PatchLoanRequestV1Request patchLoanRequestV1Request);
}
