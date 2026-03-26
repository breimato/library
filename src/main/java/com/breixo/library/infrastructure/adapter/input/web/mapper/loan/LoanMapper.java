package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import java.util.List;

import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.DateMapper;
import com.breixo.library.infrastructure.mapper.LoanStatusMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Loan Mapper. */
@Mapper(componentModel = "spring", uses = {DateMapper.class, LoanStatusMapper.class})
public interface LoanMapper {

    /**
     * To loan V1.
     *
     * @param loan the loan
     * @return the loan V1 dto
     */
    @Mapping(source = "status", target = "status")
    LoanV1Dto toLoanV1(Loan loan);

    /**
     * To loan V1 list.
     *
     * @param loans the loans
     * @return the list of loan V1 dtos
     */
    List<LoanV1Dto> toLoanV1List(List<Loan> loans);
}
