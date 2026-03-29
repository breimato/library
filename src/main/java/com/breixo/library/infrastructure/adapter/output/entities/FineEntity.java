package com.breixo.library.infrastructure.adapter.output.entities;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Data;

/** The Class Fine Entity. */
@Data
public class FineEntity {

    /** The id. */
    private Integer id;

    /** The loan id. */
    private Integer loanId;

    /** The amount euros. */
    private BigDecimal amountEuros;

    /** The status id. */
    private Integer statusId;

    /** The paid at. */
    private OffsetDateTime paidAt;
}
