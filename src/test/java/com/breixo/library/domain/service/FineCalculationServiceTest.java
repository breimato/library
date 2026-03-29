package com.breixo.library.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Fine Calculation Service Test. */
@ExtendWith(MockitoExtension.class)
class FineCalculationServiceTest {

    /** The fine calculation service. */
    @InjectMocks
    FineCalculationServiceImpl fineCalculationService;

    /**
     * Test execute when returned on time then fine is zero.
     */
    @Test
    void testExecute_whenReturnedOnTime_thenFineIsZero() {
        
        // Given
        final var dueDate = LocalDate.now();
        final var returnDate = LocalDate.now();

        // When
        final var fine = this.fineCalculationService.execute(dueDate, returnDate);

        // Then
        assertEquals(BigDecimal.ZERO, fine);
    }

    /**
     * Test execute when returned early then fine is zero.
     */
    @Test
    void testExecute_whenReturnedEarly_thenFineIsZero() {
        
        // Given
        final var dueDate = LocalDate.now();
        final var returnDate = LocalDate.now().minusDays(3);

        // When
        final var fine = this.fineCalculationService.execute(dueDate, returnDate);

        // Then
        assertEquals(BigDecimal.ZERO, fine);
    }

    /**
     * Test execute when returned late then fine is calculated.
     */
    @Test
    void testExecute_whenReturnedLate_thenFineIsCalculated() {

        // Given
        final var dueDate = LocalDate.now();
        final var returnDate = LocalDate.now().plusDays(2);
        final var expectedFine = BigDecimal.valueOf(1.0);

        // When
        final var fine = this.fineCalculationService.execute(dueDate, returnDate);

        // Then
        assertEquals(expectedFine, fine);
    }
}
