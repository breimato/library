package com.breixo.library.infrastructure.adapter.output.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.breixo.library.infrastructure.adapter.output.entities.BookEntity;

/** The Interface Book My Batis Mapper. */
@Mapper
public interface BookMyBatisMapper {

    /**
     * Find by id.
     *
     * @param id the id.
     * @return the book entity.
     */
    @Select(
        """
            select
                id,
                isbn,
                title,
                author,
                genre,
                total_copies,
                available_copies,
                created_at,
                updated_at
            from books where id = #{id}
        """)
    BookEntity findById(Long id);
}
