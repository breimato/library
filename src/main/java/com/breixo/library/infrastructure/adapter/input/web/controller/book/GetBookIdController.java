package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.port.output.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetBookIdV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetBookIdV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.GetBookResponseMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Book Id Controller. */
@RestController
@RequiredArgsConstructor
public class GetBookIdController implements GetBookIdV1Api {

    /** The book retrieval persistence port. */
    private final BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The get book response mapper. */
    private final GetBookResponseMapper getBookResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetBookIdV1Response> getBookIdV1(final Long id) {

        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(id).build();

        final var book = this.bookRetrievalPersistencePort.execute(bookSearchCriteriaCommand);

        final var getBookIdV1Response = this.getBookResponseMapper.toGetBookIdV1Response(book);

        return ResponseEntity.ok(getBookIdV1Response);
    }
}
