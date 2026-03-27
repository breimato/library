package com.breixo.library.infrastructure.mapper;

import com.breixo.library.domain.model.loan.enums.LoanStatus;

import org.mapstruct.Mapper;

/** The Interface Loan Status Mapper. */
@Mapper(componentModel = "spring")
public interface LoanStatusMapper {

    /**
     * To status id.
     *
     * @param loanStatus the loan status
     * @return the integer
     */
    default Integer toStatusId(final LoanStatus loanStatus) {
        return loanStatus.getId();
    }

    /**
     * To loan status.
     *
     * @param id the id
     * @return the loan status
     */
    default LoanStatus toLoanStatus(final Integer id) {
        return LoanStatus.values()[id];
    }
}
