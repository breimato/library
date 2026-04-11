package com.breixo.library.infrastructure.adapter.output.repository.book;

import com.breixo.library.domain.command.book.CreateBookCommand;
import com.breixo.library.domain.vo.Isbn;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Book Creation Repository Integration Test. */
class BookCreationRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The book creation repository. */
    @Autowired
    BookCreationRepository bookCreationRepository;

    /**
     * Test execute when command is valid then persist and return book.
     */
    @Test
    @Sql("classpath:sql/integration/common/reset-data.sql")
    void testExecute_whenCommandIsValid_thenPersistAndReturnBook() {

        // Given
        final var isbn = Instancio.create(Isbn.class);
        final var createBookCommand = CreateBookCommand.builder()
                .requesterId(1)
                .isbn(isbn)
                .title("Integration Created Book")
                .author("Integration Author")
                .genre("Fiction")
                .totalCopies(3)
                .availableCopies(3)
                .build();

        // When
        final var book = this.bookCreationRepository.execute(createBookCommand);

        // Then
        assertEquals(1, book.id());
        assertEquals(createBookCommand.title(), book.title());
        assertEquals(createBookCommand.author(), book.author());
        assertEquals(createBookCommand.genre(), book.genre());
        assertEquals(3, book.totalCopies());
        assertEquals(3, book.availableCopies());
    }
}
