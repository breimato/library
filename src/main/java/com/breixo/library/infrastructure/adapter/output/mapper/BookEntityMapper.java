package com.breixo.library.infrastructure.adapter.output.mapper;

import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.CreateBookCommand;
import com.breixo.library.infrastructure.adapter.output.entities.BookEntity;

import org.mapstruct.Mapper;

/** The Interface Book Entity Mapper. */
@Mapper(componentModel = "spring")
public interface BookEntityMapper {

    /**
     * To book.
     *
     * @param bookEntity the book entity.
     * @return the book.
     */
    Book toBook(BookEntity bookEntity);

    /**
     * To book entity.
     *
     * @param command the create book command.
     * @return the book entity.
     */
    BookEntity toBookEntity(CreateBookCommand command);
}
