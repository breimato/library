package com.breixo.library.domain.model;

/**
 * The Record Find Book Command.
 *
 * @param id     The id.
 * @param isbn   The isbn.
 * @param title  The title.
 * @param author The author.
 * @param genre  The genre.
 */
public record FindBookCommand(Long id, String isbn, String title, String author, String genre) {

}
