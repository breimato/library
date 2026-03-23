package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.infrastructure.adapter.input.web.dto.BookV1Dto;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Patch Book Response Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PatchBookResponseMapperTest {

    /** The patch book response mapper. */
    @InjectMocks
    PatchBookResponseMapperImpl patchBookResponseMapper;

    /** The book mapper. */
    @Mock
    BookMapper bookMapper;

    /**
     * Test to patch book v1 response when book is valid then return mapped response.
     */
    @Test
    void testToPatchBookV1Response_whenBookIsValid_thenReturnMappedResponse() {
        // Given
        final var book = Instancio.create(Book.class);
        final var bookV1Dto = Instancio.create(BookV1Dto.class);

        // When
        when(this.bookMapper.toBookV1(book)).thenReturn(bookV1Dto);
        final var patchBookV1Response = this.patchBookResponseMapper.toPatchBookV1Response(book);

        // Then
        verify(this.bookMapper, times(1)).toBookV1(book);
        assertNotNull(patchBookV1Response);
        assertEquals(bookV1Dto, patchBookV1Response.getBook());
    }

    /**
     * Test to patch book v1 response when book is null then return null.
     */
    @Test
    void testToPatchBookV1Response_whenBookIsNull_thenReturnNull() {
        assertNull(this.patchBookResponseMapper.toPatchBookV1Response(null));
    }
}
