package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.domain.model.Book;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostBookV1Response;

import org.mapstruct.Mapper;

/** The Interface Post Book Response Mapper. */
@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface PostBookResponseMapper {

    /**
     * To post book v1 response.
     *
     * @param book The book.
     * @return The post book v1 response.
     */
    PostBookV1Response toPostBookV1Response(Book book);

}
