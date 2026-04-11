package com.breixo.library.infrastructure.adapter.output.repository.book;

import com.breixo.library.domain.command.book.UpdateBookCommand;
import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Book Update Repository Integration Test. */
class BookUpdateRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The book update repository. */
    @Autowired
    BookUpdateRepository bookUpdateRepository;

    /**
     * Test execute when command is valid then update and return book.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/book/insert-book.sql"
    })
    void testExecute_whenCommandIsValid_thenUpdateAndReturnBook() {

        // Given
        final var updateBookCommand = UpdateBookCommand.builder()
                .requesterId(1)
                .id(200)
                .title("Updated Title")
                .author("Integration Author")
                .genre("Fiction")
                .totalCopies(5)
                .availableCopies(5)
                .build();

        // When
        final var book = this.bookUpdateRepository.execute(updateBookCommand);

        // Then
        assertEquals(200, book.id());
        assertEquals("Updated Title", book.title());
    }
}
