package com.breixo.library.infrastructure.adapter.output.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.infrastructure.adapter.output.entities.LoanRequestEntity;

/** The Interface Loan Request My Batis Mapper. */
@Mapper
public interface LoanRequestMyBatisMapper {

    /**
     * Find.
     *
     * @param loanRequestSearchCriteriaCommand the loan request search criteria command.
     * @return the list of loan request entities.
     */
    @Select("""
            <script>
            select
                id,
                user_id,
                book_id,
                request_date,
                approval_date,
                status_id,
                rejection_reason
            from loan_requests
            <where>
                <if test="id != null">and id = #{id}</if>
                <if test="userId != null">and user_id = #{userId}</if>
                <if test="bookId != null">and book_id = #{bookId}</if>
                <if test="statusId != null">and status_id = #{statusId}</if>
            </where>
            order by request_date desc
            </script>
            """)
    List<LoanRequestEntity> find(LoanRequestSearchCriteriaCommand loanRequestSearchCriteriaCommand);

    /**
     * Insert.
     *
     * @param loanRequestEntity the loan request entity.
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into loan_requests (user_id, book_id, request_date, status_id) values (#{userId}, #{bookId}, current_date, 0)")
    void insert(LoanRequestEntity loanRequestEntity);

    /**
     * Update.
     *
     * @param loanRequestEntity the loan request entity.
     */
    @Update("""
            <script>
            update loan_requests
            <set>
                <if test="statusId != null">status_id = #{statusId},</if>
                <if test="statusId == 1">approval_date = current_date,</if>
                <if test="rejectionReason != null">rejection_reason = #{rejectionReason},</if>
            </set>
            where id = #{id}
            </script>
            """)
    void update(LoanRequestEntity loanRequestEntity);

    /**
     * Delete.
     *
     * @param id the loan request identifier.
     */
    @Delete("delete from loan_requests where id = #{id}")
    void delete(Integer id);
}
