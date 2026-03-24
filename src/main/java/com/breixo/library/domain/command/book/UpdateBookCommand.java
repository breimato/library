package com.breixo.library.domain.command.book;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Update Book Command.
 *
 * @param id              The id.
 * @param title           The title.
 * @param author          The author.
 * @param genre           The genre.
 * @param totalCopies     The total copies.
 * @param availableCopies The available copies.
 */
@Builder
public record UpdateBookCommand(@NotNull Long id, String title, String author, String genre,
                                Integer totalCopies, Integer availableCopies) {

}
