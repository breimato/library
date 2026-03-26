package com.breixo.library.domain.command.book;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The Class Book Search Criteria Command.
 */
@Getter
@Builder
@EqualsAndHashCode
public class BookSearchCriteriaCommand {

    /** The id. */
    private final Integer id;

    /** The isbn. */
    private final String isbn;

    /** The title. */
    private final String title;

    /** The author. */
    private final String author;

    /** The genre. */
    private final String genre;
}
