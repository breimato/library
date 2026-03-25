package com.breixo.library.infrastructure.adapter.output.mapper;

import java.util.List;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.command.book.CreateBookCommand;
import com.breixo.library.infrastructure.adapter.output.entities.BookEntity;
import com.breixo.library.infrastructure.mapper.IsbnMapper;

import org.mapstruct.Mapper;

/** The Interface Book Entity Mapper. */
@Mapper(componentModel = "spring", uses = IsbnMapper.class)
public interface BookEntityMapper {

    /**
     * To book.
     *
     * @param bookEntity the book entity.
     * @return the book.
     */
    Book toBook(BookEntity bookEntity);

    /**
     * To book list.
     *
     * @param bookEntities the book entities.
     * @return the book list.
     */
    List<Book> toBookList(List<BookEntity> bookEntities);

    /**
     * To book entity.
     *
     * @param createBookCommand the create book command.
     * @return the book entity.
     */
    BookEntity toBookEntity(CreateBookCommand createBookCommand);

}
