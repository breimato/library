package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.port.output.book.BookDeletionPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.DeleteBookV1Api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

/** The Class Delete Book Controller. */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class DeleteBookController implements DeleteBookV1Api {

    /** The book deletion persistence port. */
    private final BookDeletionPersistencePort bookDeletionPersistencePort;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<Void> deleteBookV1(final Integer id) {

        this.bookDeletionPersistencePort.execute(id);

        return ResponseEntity.noContent().build();
    }
}
