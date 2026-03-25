package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import java.util.List;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.infrastructure.adapter.input.web.dto.BookV1Dto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.DateMapper;
import com.breixo.library.infrastructure.mapper.IsbnMapper;

import org.mapstruct.Mapper;

/** The Interface Book Mapper. */
@Mapper(componentModel = "spring", uses = {DateMapper.class, IsbnMapper.class})
public interface BookMapper {

    /**
     * To book v1.
     *
     * @param book The book.
     * @return The book v 1 dto.
     */
    BookV1Dto toBookV1(Book book);

    /**
     * To book v1 list.
     *
     * @param books The books.
     * @return The book v 1 dto list.
     */
    List<BookV1Dto> toBookV1List(List<Book> books);

}
