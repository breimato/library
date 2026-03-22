package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.domain.model.Book;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostBookV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.DateMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/** The Class Book Mapper Test. */
@ExtendWith(MockitoExtension.class)
class BookMapperTest {

    /** The book mapper. */
    @InjectMocks BookMapperImpl bookMapper;

    /** The date mapper. */
    @Mock
    DateMapper dateMapper;

    /**
     * Test to create book command when request is valid then return mapped command.
     */
    @Test
    void testToCreateBookCommand_whenRequestIsValid_thenReturnMappedCommand() {
        // Given
        final var postBookV1Request = Instancio.create(PostBookV1Request.class);

        // When
        final var createBookCommand = this.bookMapper.toCreateBookCommand(postBookV1Request);

        // Then
        assertNotNull(createBookCommand);
        assertEquals(postBookV1Request.getIsbn(), createBookCommand.isbn());
        assertEquals(postBookV1Request.getTitle(), createBookCommand.title());
        assertEquals(postBookV1Request.getAuthor(), createBookCommand.author());
        assertEquals(postBookV1Request.getGenre(), createBookCommand.genre());
        assertEquals(postBookV1Request.getTotalCopies(), createBookCommand.totalCopies());
        assertEquals(postBookV1Request.getAvailableCopies(), createBookCommand.availableCopies());
    }

    /**
     * Test to book v 1 when book is valid then return mapped dto.
     */
    @Test
    void testToBookV1_whenBookIsValid_thenReturnMappedDto() {

        // Given
        final var book = Instancio.create(Book.class);

        // When
        when(this.dateMapper.toOffsetDateTime(book.createdAt())).thenReturn(book.createdAt().atOffset(ZoneOffset.UTC));
        when(this.dateMapper.toOffsetDateTime(book.updatedAt())).thenReturn(book.updatedAt().atOffset(ZoneOffset.UTC));
        final var bookV1Dto = this.bookMapper.toBookV1(book);

        // Then
        verify(this.dateMapper, times(1)).toOffsetDateTime(book.createdAt());
        verify(this.dateMapper, times(1)).toOffsetDateTime(book.updatedAt());
        assertNotNull(bookV1Dto);
        assertEquals(book.id(), bookV1Dto.getId());
        assertEquals(book.isbn(), bookV1Dto.getIsbn());
        assertEquals(book.title(), bookV1Dto.getTitle());
        assertEquals(book.author(), bookV1Dto.getAuthor());
        assertEquals(book.genre(), bookV1Dto.getGenre());
        assertEquals(book.totalCopies(), bookV1Dto.getTotalCopies());
        assertEquals(book.availableCopies(), bookV1Dto.getAvailableCopies());
        assertEquals(book.createdAt().atOffset(ZoneOffset.UTC), bookV1Dto.getCreatedAt());
        assertEquals(book.updatedAt().atOffset(ZoneOffset.UTC), bookV1Dto.getUpdatedAt());
    }
}
