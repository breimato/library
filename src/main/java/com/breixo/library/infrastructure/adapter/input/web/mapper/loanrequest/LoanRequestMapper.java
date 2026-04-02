package com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest;

import java.util.List;

import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanRequestV1;
import com.breixo.library.infrastructure.mapper.LoanRequestStatusMapper;

import org.mapstruct.Mapper;

/** The Interface Loan Request Mapper. */
@Mapper(componentModel = "spring", uses = {LoanRequestStatusMapper.class})
public interface LoanRequestMapper {

    /**
     * To loan request V1.
     *
     * @param loanRequest The loan request.
     * @return The loan request V1 dto.
     */
    LoanRequestV1 toLoanRequestV1(LoanRequest loanRequest);

    /**
     * To loan request V1 list.
     *
     * @param loanRequests The loan requests.
     * @return The loan request V1 dto list.
     */
    List<LoanRequestV1> toLoanRequestV1List(List<LoanRequest> loanRequests);
}
