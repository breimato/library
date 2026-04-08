package com.breixo.library.infrastructure.mapper;

import java.util.Objects;

import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;

import org.mapstruct.Mapper;

/** The Interface Loan Request Status Mapper. */
@Mapper(componentModel = "spring")
public interface LoanRequestStatusMapper {

    /**
     * To status id.
     *
     * @param loanRequestStatus the loan request status.
     * @return the integer value.
     */
    default Integer toStatusId(final LoanRequestStatus loanRequestStatus) {
        if (Objects.isNull(loanRequestStatus)) {
            return null;
        }
        return loanRequestStatus.getId();
    }

    /**
     * To loan request status.
     *
     * @param id the status integer id.
     * @return the loan request status.
     */
    default LoanRequestStatus toLoanRequestStatus(final Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return LoanRequestStatus.values()[id];
    }
}
