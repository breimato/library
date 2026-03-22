package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.port.output.BookCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.PostBookV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostBookV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostBookV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.PostBookRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.PostBookResponseMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Post Book Controller. */
@RestController
@RequiredArgsConstructor
public class PostBookController implements PostBookV1Api {

    /** The book creation persistence port. */
    private final BookCreationPersistencePort bookCreationPersistencePort;

    /** The post book request mapper. */
    private final PostBookRequestMapper postBookRequestMapper;

    /** The post book response mapper. */
    private final PostBookResponseMapper postBookResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<PostBookV1Response> postBookV1(final PostBookV1Request postBookV1Request) {

        final var createBookCommand = this.postBookRequestMapper.toCreateBookCommand(postBookV1Request);

        final var book = this.bookCreationPersistencePort.execute(createBookCommand);

        final var postBookV1Response = this.postBookResponseMapper.toPostBookV1Response(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(postBookV1Response);
    }
}
