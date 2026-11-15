package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/** The Interface Get Books V1 Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetBooksV1RequestMapper {

    /** To Book search criteria command. */
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    BookSearchCriteriaCommand toBookSearchCriteriaCommand(Integer id, String isbn, String title, String author, String genre);
}
