package com.breixo.library.infrastructure.adapter.output.mapper.loanrequest;

import java.util.List;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.infrastructure.adapter.output.entities.LoanRequestEntity;
import com.breixo.library.infrastructure.mapper.LoanRequestStatusMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Loan Request Entity Mapper. */
@Mapper(componentModel = "spring", uses = {LoanRequestStatusMapper.class})
public interface LoanRequestEntityMapper {

    /**
     * To loan request.
     *
     * @param loanRequestEntity the loan request entity.
     * @return the loan request.
     */
    @Mapping(source = "statusId", target = "status")
    LoanRequest toLoanRequest(LoanRequestEntity loanRequestEntity);

    /**
     * To loan request list.
     *
     * @param loanRequestEntities the loan request entities.
     * @return the loan request list.
     */
    List<LoanRequest> toLoanRequestList(List<LoanRequestEntity> loanRequestEntities);

    /**
     * To loan request entity.
     *
     * @param createLoanRequestCommand the create loan request command.
     * @return the loan request entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "requestDate", ignore = true)
    @Mapping(target = "approvalDate", ignore = true)
    @Mapping(target = "statusId", ignore = true)
    @Mapping(target = "rejectionReason", ignore = true)
    LoanRequestEntity toLoanRequestEntity(CreateLoanRequestCommand createLoanRequestCommand);

    /**
     * To loan request entity.
     *
     * @param updateLoanRequestCommand the update loan request command.
     * @return the loan request entity.
     */
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "bookId", ignore = true)
    @Mapping(target = "requestDate", ignore = true)
    @Mapping(target = "approvalDate", ignore = true)
    @Mapping(source = "status", target = "statusId")
    LoanRequestEntity toLoanRequestEntity(UpdateLoanRequestCommand updateLoanRequestCommand);
}
