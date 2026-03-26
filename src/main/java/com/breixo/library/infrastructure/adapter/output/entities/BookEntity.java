package com.breixo.library.infrastructure.adapter.output.entities;

import lombok.Data;

/** The Class Book Entity. */
@Data
public class BookEntity {

    /** The id. */
    private Integer id;

    /** The isbn. */
    private String isbn;

    /** The title. */
    private String title;

    /** The author. */
    private String author;

    /** The genre. */
    private String genre;

    /** The total copies. */
    private Integer totalCopies;

    /** The available copies. */
    private Integer availableCopies;
}
