package com.breixo.library.infrastructure.adapter.output.mybatis;

import java.util.List;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.infrastructure.adapter.output.entities.FineEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/** The Interface Fine My Batis Mapper. */
@Mapper
public interface FineMyBatisMapper {

    /**
     * Find.
     *
     * @param fineSearchCriteriaCommand the fine search criteria command.
     * @return the list of fine entities.
     */
    @Select("""
            <script>
            select
                id,
                loan_id,
                amount_euros,
                status_id,
                paid_at
            from fines
            <where>
                <if test="id != null">and id = #{id}</if>
                <if test="loanId != null">and loan_id = #{loanId}</if>
                <if test="statusId != null">and status_id = #{statusId}</if>
            </where>
            </script>
            """)
    List<FineEntity> find(FineSearchCriteriaCommand fineSearchCriteriaCommand);

    /**
     * Delete.
     *
     * @param id the fine identifier.
     */
    @Delete("delete from fines where id = #{id}")
    void delete(Integer id);
}
