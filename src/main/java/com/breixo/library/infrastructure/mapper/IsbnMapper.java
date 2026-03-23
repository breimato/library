package com.breixo.library.infrastructure.mapper;

import com.breixo.library.domain.model.vo.Isbn;

import org.mapstruct.Mapper;

/** The Interface Isbn Mapper. */
@Mapper(componentModel = "spring")
public interface IsbnMapper {

    /**
     * To isbn.
     *
     * @param value the isbn string value.
     * @return the isbn.
     */
    default Isbn toIsbn(final String value) {
        return new Isbn(value);
    }

    /**
     * From isbn.
     *
     * @param isbn the isbn.
     * @return the isbn string value.
     */
    default String fromIsbn(final Isbn isbn) {
        return isbn.getValue();
    }

}
