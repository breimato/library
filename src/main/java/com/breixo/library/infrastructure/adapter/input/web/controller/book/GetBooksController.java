package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetBooksV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetBooksV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetBooksV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.BookMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.GetBooksV1RequestMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Books Controller. */
@RestController
@RequiredArgsConstructor
public class GetBooksController implements GetBooksV1Api {

    /** The book retrieval persistence port. */
    private final BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The get books V1 request mapper. */
    private final GetBooksV1RequestMapper getBooksV1RequestMapper;

    /** The book mapper. */
    private final BookMapper bookMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetBooksV1ResponseDto> getBooksV1(final GetBooksV1Request getBooksV1Request) {

        final var bookSearchCriteriaCommand = this.getBooksV1RequestMapper.toBookSearchCriteriaCommand(getBooksV1Request);

        final var books = this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand);

        final var bookV1DtoList = this.bookMapper.toBookV1List(books);

        final var getBooksV1ResponseDto = GetBooksV1ResponseDto.builder().books(bookV1DtoList).build();

        return ResponseEntity.ok(getBooksV1ResponseDto);
    }
}
