package com.breixo.library.infrastructure.adapter.output.mybatis;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/** The Interface Fine My Batis Mapper. */
@Mapper
public interface FineMyBatisMapper {

    /**
     * Delete.
     *
     * @param id the fine identifier.
     */
    @Delete("delete from fines where id = #{id}")
    void delete(Integer id);
}
