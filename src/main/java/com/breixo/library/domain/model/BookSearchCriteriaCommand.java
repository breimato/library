package com.breixo.library.domain.model;

import lombok.Builder;
import lombok.Getter;

/**
 * The Class Book Search Criteria Command.
 */
@Getter
@Builder
public class BookSearchCriteriaCommand {

    /** The id. */
    private final Long id;

    /** The isbn. */
    private final String isbn;

    /** The title. */
    private final String title;

    /** The author. */
    private final String author;

    /** The genre. */
    private final String genre;
}
