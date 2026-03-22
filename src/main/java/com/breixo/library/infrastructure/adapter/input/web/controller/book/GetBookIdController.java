package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.FindBookCommand;
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

        final var findBookCommand = new FindBookCommand(id, null, null, null, null);

        final var books = this.bookRetrievalPersistencePort.execute(findBookCommand);

        if (books.isEmpty()) {
            throw new BookException(
                    ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR);
        }

        final var bookV1Dto = this.bookMapper.toBookV1(books.get(0));

        final var getBookIdV1Response = GetBookIdV1Response.builder().book(bookV1Dto).build();

        return ResponseEntity.ok(getBookIdV1Response);
    }
}
