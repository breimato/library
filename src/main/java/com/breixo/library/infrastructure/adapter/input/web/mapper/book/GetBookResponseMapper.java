package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetBookIdV1Response;

import org.mapstruct.Mapper;

/** The Interface Get Book Response Mapper. */
@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface GetBookResponseMapper {

    /**
     * To get book id v1 response.
     *
     * @param book The book.
     * @return The get book id v1 response.
     */
    GetBookIdV1Response toGetBookIdV1Response(Book book);

}
