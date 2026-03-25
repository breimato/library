package com.breixo.library.infrastructure.adapter.input.web.controller.book;

import com.breixo.library.domain.port.input.book.UpdateBookUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.PatchBookV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.BookV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchBookV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.BookResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.book.PatchBookRequestMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Patch Book Controller. */
@RestController
@RequiredArgsConstructor
public class PatchBookController implements PatchBookV1Api {

    /** The update book use case. */
    private final UpdateBookUseCase updateBookUseCase;

    /** The patch book request mapper. */
    private final PatchBookRequestMapper patchBookRequestMapper;

    /** The book response mapper. */
    private final BookResponseMapper bookResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<BookV1ResponseDto> patchBookV1(final Long id, final PatchBookV1Request patchBookV1Request) {

        final var updateBookCommand = this.patchBookRequestMapper.toUpdateBookCommand(id, patchBookV1Request);

        final var book = this.updateBookUseCase.execute(updateBookCommand);

        final var bookV1ResponseDto = this.bookResponseMapper.toBookV1Response(book);

        return ResponseEntity.ok(bookV1ResponseDto);
    }
}
