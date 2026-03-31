package com.breixo.library.infrastructure.adapter.output.mybatis;

import java.util.List;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.infrastructure.adapter.output.entities.ReservationEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/** The Interface Reservation My Batis Mapper. */
@Mapper
public interface ReservationMyBatisMapper {

    /**
     * Find.
     *
     * @param reservationSearchCriteriaCommand the reservation search criteria command.
     * @return the list of reservation entities.
     */
    @Select("""
            <script>
            select
                id,
                user_id,
                book_id,
                loan_id,
                expires_at,
                status_id
            from reservations
            <where>
                <if test="id != null">and id = #{id}</if>
                <if test="userId != null">and user_id = #{userId}</if>
                <if test="bookId != null">and book_id = #{bookId}</if>
                <if test="loanId != null">and loan_id = #{loanId}</if>
                <if test="statusId != null">and status_id = #{statusId}</if>
            </where>
            </script>
            """)
    List<ReservationEntity> find(ReservationSearchCriteriaCommand reservationSearchCriteriaCommand);

    /**
     * Find active by book id.
     *
     * @param bookId    the book id.
     * @param statusIds the active status ids.
     * @return the list of reservation entities.
     */
    @Select("""
            <script>
            select
                id,
                user_id,
                book_id,
                loan_id,
                expires_at,
                status_id
            from reservations
            where book_id = #{bookId}
            and status_id in
            <foreach collection="statusIds" item="statusId" open="(" separator="," close=")">
                #{statusId}
            </foreach>
            </script>
            """)
    List<ReservationEntity> findActiveByBookId(@Param("bookId") Integer bookId,
            @Param("statusIds") List<Integer> statusIds);

    /**
     * Insert.
     *
     * @param reservationEntity the reservation entity.
     */
    @Insert("""
            insert into reservations (user_id, book_id, loan_id, expires_at, status_id)
            values (#{userId}, #{bookId}, #{loanId}, #{expiresAt}, #{statusId})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ReservationEntity reservationEntity);

    /**
     * Update.
     *
     * @param reservationEntity the reservation entity.
     */
    @Update("""
            <script>
            update reservations
            <set>
                <if test="loanId != null">loan_id = #{loanId},</if>
                <if test="expiresAt != null">expires_at = #{expiresAt},</if>
                <if test="statusId != null">status_id = #{statusId},</if>
            </set>
            where id = #{id}
            </script>
            """)
    void update(ReservationEntity reservationEntity);

    /**
     * Mark expired.
     *
     * @return the number of reservations marked as expired.
     */
    @Update("update reservations set status_id = 3 where status_id = 0 and expires_at < current_timestamp")
    int markExpired();

    /**
     * Mark notified by book id.
     *
     * @param bookId the book id.
     * @return the number of reservations marked as notified.
     */
    @Update("update reservations set status_id = 1 where book_id = #{bookId} and status_id = 0")
    int markNotifiedByBookId(Integer bookId);

    /**
     * Delete.
     *
     * @param id the reservation identifier.
     */
    @Delete("delete from reservations where id = #{id}")
    void delete(Integer id);
}
