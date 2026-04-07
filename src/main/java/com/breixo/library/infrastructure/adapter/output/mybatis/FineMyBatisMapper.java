package com.breixo.library.infrastructure.adapter.output.mybatis;

import java.util.List;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.infrastructure.adapter.output.entities.FineEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
                f.id as id,
                f.loan_id as loan_id,
                f.amount_euros as amount_euros,
                f.status_id as status_id,
                f.paid_at as paid_at
            from fines f
            <if test="userId != null">
                inner join loans l on f.loan_id = l.id
            </if>
            <where>
                <if test="id != null">and f.id = #{id}</if>
                <if test="loanId != null">and f.loan_id = #{loanId}</if>
                <if test="statusId != null">and f.status_id = #{statusId}</if>
                <if test="userId != null">and l.user_id = #{userId}</if>
            </where>
            </script>
            """)
    List<FineEntity> find(FineSearchCriteriaCommand fineSearchCriteriaCommand);

    /**
     * Insert.
     *
     * @param fineEntity the fine entity.
     */
    @Insert("insert into fines (loan_id, amount_euros, status_id) values (#{loanId}, #{amountEuros}, #{statusId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(FineEntity fineEntity);

    /**
     * Update.
     *
     * @param fineEntity the fine entity.
     */
    @Update("""
            <script>
            update fines
            <set>
                <if test="amountEuros != null">amount_euros = #{amountEuros},</if>
                <if test="statusId != null">status_id = #{statusId},</if>
                <if test="paidAt != null">paid_at = #{paidAt},</if>
            </set>
            where id = #{id}
            </script>
            """)
    void update(FineEntity fineEntity);

    /**
     * Delete.
     *
     * @param id the fine identifier.
     */
    @Delete("delete from fines where id = #{id}")
    void delete(Integer id);
}
