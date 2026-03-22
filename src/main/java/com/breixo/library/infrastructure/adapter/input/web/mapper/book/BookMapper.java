package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.CreateBookCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.BookV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostBookV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.DateMapper;

import org.mapstruct.Mapper;

/** The Interface Book Mapper. */
@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface BookMapper {

    /**
     * To book v1.
     *
     * @param book The book.
     * @return The book v 1 dto.
     */
    BookV1Dto toBookV1(Book book);

    /**
     * To create book command.
     *
     * @param request The post book v1 request.
     * @return The create book command.
     */
    CreateBookCommand toCreateBookCommand(PostBookV1Request request);

}