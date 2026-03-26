package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetBookIdV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.BookV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.BookResponseMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Book Id Controller. */
@RestController
@RequiredArgsConstructor
public class GetBookIdController implements GetBookIdV1Api {

    /** The book retrieval persistence port. */
    private final BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The book response mapper. */
    private final BookResponseMapper bookResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<BookV1ResponseDto> getBookIdV1(final Integer id) {

        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(id).build();

        final var book = this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)
                .orElseThrow(() -> new BookException(
                        ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR));

        final var bookV1ResponseDto = this.bookResponseMapper.toBookV1Response(book);

        return ResponseEntity.ok(bookV1ResponseDto);
    }
}
