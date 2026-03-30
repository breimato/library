package com.breixo.library.infrastructure.adapter.output.mybatis;

import java.util.List;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.infrastructure.adapter.output.entities.ReservationEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
     * Delete.
     *
     * @param id the reservation identifier.
     */
    @Delete("delete from reservations where id = #{id}")
    void delete(Integer id);
}
