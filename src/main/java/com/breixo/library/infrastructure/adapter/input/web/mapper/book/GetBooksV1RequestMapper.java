package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetBooksV1Request;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

/** The Interface Get Books V1 Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetBooksV1RequestMapper {

    /**
     * To book search criteria command.
     *
     * @param getBooksV1Request the get books V1 request.
     * @return the book search criteria command.
     */
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    BookSearchCriteriaCommand toBookSearchCriteriaCommand(GetBooksV1Request getBooksV1Request);
}
