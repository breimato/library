package com.breixo.library.infrastructure.adapter.output.mapper;

import java.util.List;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.infrastructure.adapter.output.entities.LoanEntity;
import com.breixo.library.infrastructure.mapper.LoanStatusMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Loan Entity Mapper. */
@Mapper(componentModel = "spring", uses = LoanStatusMapper.class)
public interface LoanEntityMapper {

    /**
     * To loan.
     *
     * @param loanEntity the loan entity
     * @return the loan
     */
    @Mapping(source = "statusId", target = "status")
    Loan toLoan(LoanEntity loanEntity);

    /**
     * To loan list.
     *
     * @param loanEntities the loan entities
     * @return the list of loans
     */
    List<Loan> toLoanList(List<LoanEntity> loanEntities);

    /**
     * To loan entity.
     *
     * @param createLoanCommand the create loan command
     * @return the loan entity
     */
    LoanEntity toLoanEntity(CreateLoanCommand createLoanCommand);
}
