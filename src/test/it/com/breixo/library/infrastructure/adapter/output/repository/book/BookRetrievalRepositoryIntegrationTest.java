package com.breixo.library.infrastructure.adapter.output.repository.book;

import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Book Retrieval Repository Integration Test. */
class BookRetrievalRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The book retrieval repository. */
    @Autowired
    BookRetrievalRepository bookRetrievalRepository;

    /**
     * Test find by id when book exists then return book.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/book/insert-book.sql"
    })
    void testFindById_whenBookExists_thenReturnBook() {

        // When
        final var book = this.bookRetrievalRepository.findById(200).orElseThrow();

        // Then
        assertEquals(200, book.id());
        assertEquals("9780134685991", book.isbn().getValue());
        assertEquals("Integration Book", book.title());
        assertEquals("Integration Author", book.author());
        assertEquals("Fiction", book.genre());
        assertEquals(5, book.totalCopies());
        assertEquals(5, book.availableCopies());
    }
}
