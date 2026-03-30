package com.breixo.library.infrastructure.adapter.output.mybatis;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/** The Interface Reservation My Batis Mapper. */
@Mapper
public interface ReservationMyBatisMapper {

    /**
     * Delete.
     *
     * @param id the reservation identifier.
     */
    @Delete("delete from reservations where id = #{id}")
    void delete(Integer id);
}
