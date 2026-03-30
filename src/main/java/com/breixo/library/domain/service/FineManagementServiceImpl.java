package com.breixo.library.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.breixo.library.domain.command.fine.CreateFineCommand;
import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.command.fine.UpdateFineCommand;
import com.breixo.library.domain.model.fine.enums.FineStatus;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.output.fine.FineCreationPersistencePort;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.domain.port.output.fine.FineUpdatePersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

/** The Class Fine Management Service Impl. */
@Component
@RequiredArgsConstructor
public class FineManagementServiceImpl implements FineManagementService {

    /** The fine calculation service. */
    private final FineCalculationService fineCalculationService;

    /** The fine retrieval persistence port. */
    private final FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** The fine creation persistence port. */
    private final FineCreationPersistencePort fineCreationPersistencePort;

    /** The fine update persistence port. */
    private final FineUpdatePersistencePort fineUpdatePersistencePort;

    /** {@inheritDoc} */
    @Override
    public void execute(@Valid @NotNull final Loan loan, @NotNull final LocalDate returnDate) {

        final var amountEuros = this.fineCalculationService.execute(loan.dueDate(), returnDate);

        if (amountEuros.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        this.createOrUpdatePendingFine(loan, amountEuros);
    }


    /**
     * Creates or updates pending fine.
     *
     * @param loan        the loan.
     * @param amountEuros the amount euros.
     */
    private void createOrUpdatePendingFine(final Loan loan, final BigDecimal amountEuros) {

        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder().loanId(loan.id()).build();
        final var fines = this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand);

        if (CollectionUtils.isEmpty(fines)) {
            this.createPendingFine(loan, amountEuros);
            return;
        }

        this.updatePendingFineIfExists(fines.getFirst().id(), fines.getFirst().status(), amountEuros);
    }


    /**
     * Creates pending fine.
     *
     * @param loan        the loan.
     * @param amountEuros the amount euros.
     */
    private void createPendingFine(final Loan loan, final BigDecimal amountEuros) {

        final var createFineCommand = CreateFineCommand.builder()
                .loanId(loan.id())
                .amountEuros(amountEuros)
                .statusId(FineStatus.PENDING.getId())
                .build();

        this.fineCreationPersistencePort.execute(createFineCommand);
    }


    /**
     * Update pending fine if exists.
     *
     * @param fineId      the fine id.
     * @param fineStatus  the fine status.
     * @param amountEuros the amount euros.
     */
    private void updatePendingFineIfExists(final Integer fineId, final FineStatus fineStatus,
            final BigDecimal amountEuros) {

        if (FineStatus.PENDING.equals(fineStatus)) {
            final var updateFineCommand = UpdateFineCommand.builder()
                    .id(fineId)
                    .amountEuros(amountEuros)
                    .status(FineStatus.PENDING)
                    .build();
            this.fineUpdatePersistencePort.execute(updateFineCommand);
        }
    }
}
