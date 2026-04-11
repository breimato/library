package com.breixo.library.infrastructure.adapter.output.repository.book;

import com.breixo.library.support.integration.AbstractPostgresRepositoryIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** The Class Book Deletion Repository Integration Test. */
class BookDeletionRepositoryIntegrationTest extends AbstractPostgresRepositoryIntegrationTest {

    /** The book deletion repository. */
    @Autowired
    BookDeletionRepository bookDeletionRepository;

    /** The book retrieval repository. */
    @Autowired
    BookRetrievalRepository bookRetrievalRepository;

    /**
     * Test execute when id exists then delete book.
     */
    @Test
    @Sql(scripts = {
            "classpath:sql/integration/common/reset-data.sql",
            "classpath:sql/integration/book/insert-book.sql"
    })
    void testExecute_whenIdExists_thenDeleteBook() {

        // When
        this.bookDeletionRepository.execute(200);

        // Then
        assertTrue(this.bookRetrievalRepository.findById(200).isEmpty());
    }
}
