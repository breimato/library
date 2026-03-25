package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetBooksV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetBooksV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.BookMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Books Controller. */
@RestController
@RequiredArgsConstructor
public class GetBooksController implements GetBooksV1Api {

    /** The book retrieval persistence port. */
    private final BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The book mapper. */
    private final BookMapper bookMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetBooksV1ResponseDto> getBooksV1() {

        final var books = this.bookRetrievalPersistencePort.findAll();

        final var bookV1DtoList = this.bookMapper.toBookV1List(books);

        final var getBooksV1ResponseDto = GetBooksV1ResponseDto.builder().books(bookV1DtoList).build();

        return ResponseEntity.ok(getBooksV1ResponseDto);
    }
}
