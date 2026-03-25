package com.breixo.library.infrastructure.adapter.output.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.command.book.UpdateBookCommand;
import com.breixo.library.infrastructure.adapter.output.entities.BookEntity;

/** The Interface Book My Batis Mapper. */
@Mapper
public interface BookMyBatisMapper {

    /**
     * Find.
     *
     * @param bookSearchCriteriaCommand the book search criteria command.
     * @return the list of book entities.
     */
    @Select("""
            <script>
            select
                id,
                isbn,
                title,
                author,
                genre,
                total_copies,
                available_copies
            from books
            <where>
                <if test="id != null">and id = #{id}</if>
                <if test="isbn != null">and isbn = #{isbn}</if>
                <if test="title != null">and title ilike #{title}</if>
                <if test="author != null">and author ilike #{author}</if>
                <if test="genre != null">and genre ilike #{genre}</if>
            </where>
            </script>
            """)
    List<BookEntity> find(BookSearchCriteriaCommand bookSearchCriteriaCommand);

    /**
     * Find all.
     *
     * @return the list of all book entities.
     */
    @Select("select id, isbn, title, author, genre, total_copies, available_copies from books")
    List<BookEntity> findAll();

    /**
     * Insert.
     *
     * @param bookEntity the book entity.
     */
    @Insert("""
            insert into books (isbn, title, author, genre, total_copies, available_copies)
            values (#{isbn}, #{title}, #{author}, #{genre}, #{totalCopies}, #{availableCopies})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(BookEntity bookEntity);

    /**
     * Update.
     *
     * @param updateBookCommand the update book command.
     */
    @Update("""
            <script>
            update books
            <set>
                <if test="title != null">title = #{title},</if>
                <if test="author != null">author = #{author},</if>
                <if test="genre != null">genre = #{genre},</if>
                <if test="totalCopies != null">total_copies = #{totalCopies},</if>
                <if test="availableCopies != null">available_copies = #{availableCopies},</if>
            </set>
            where id = #{id}
            </script>
            """)
    void update(UpdateBookCommand updateBookCommand);

    /**
     * Delete.
     *
     * @param id the book identifier.
     */
    @Delete("delete from books where id = #{id}")
    void delete(Long id);
}
