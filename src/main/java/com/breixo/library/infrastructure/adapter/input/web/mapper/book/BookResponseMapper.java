package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.infrastructure.adapter.input.web.dto.BookV1ResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Book Response Mapper. */
@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface BookResponseMapper {

    /**
     * To book v1 response.
     *
     * @param book the book.
     * @return the book v1 response dto.
     */
    @Mapping(target = "book", source = "book")
    BookV1ResponseDto toBookV1Response(Book book);
}
