package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.port.output.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetBookIdV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetBookIdV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.BookMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Book Id Controller. */
@RestController
@RequiredArgsConstructor
public class GetBookIdController implements GetBookIdV1Api {

    /** The book retrieval persistence port. */
    private final BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The book mapper. */
    private final BookMapper bookMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetBookIdV1Response> getBookIdV1(final Long id) {

        final var book = this.bookRetrievalPersistencePort.findById(id);

        final var bookDto = this.bookMapper.toBookV1(book);

        final var getBookIdV1Response = GetBookIdV1Response.builder().book(bookDto).build();

        return ResponseEntity.ok(getBookIdV1Response);
    }
}
