package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1ResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Loan Response Mapper. */
@Mapper(componentModel = "spring", uses = LoanMapper.class)
public interface LoanResponseMapper {

    /**
     * To loan V1 response.
     *
     * @param loan the loan
     * @return the loan V1 response dto
     */
    @Mapping(target = "loan", source = "loan")
    LoanV1ResponseDto toLoanV1Response(Loan loan);
}
