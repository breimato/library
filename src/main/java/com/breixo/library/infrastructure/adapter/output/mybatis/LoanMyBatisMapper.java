package com.breixo.library.infrastructure.adapter.output.mybatis;

import java.util.List;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.UpdateLoanRenewCommand;
import com.breixo.library.domain.command.loan.UpdateLoanReturnCommand;
import com.breixo.library.infrastructure.adapter.output.entities.LoanEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/** The Interface Loan My Batis Mapper. */
@Mapper
public interface LoanMyBatisMapper {

    /**
     * Find.
     *
     * @param loanSearchCriteriaCommand the loan search criteria command
     * @return the list of loan entities
     */
    @Select("""
            <script>
            select
                id,
                user_id,
                book_id,
                due_date,
                return_date,
                status_id,
                created_at,
                updated_at
            from loans
            <where>
                <if test="id != null">and id = #{id}</if>
                <if test="userId != null">and user_id = #{userId}</if>
                <if test="bookId != null">and book_id = #{bookId}</if>
                <if test="statusId != null">and status_id = #{statusId}</if>
            </where>
            </script>
            """)
    List<LoanEntity> find(LoanSearchCriteriaCommand loanSearchCriteriaCommand);

    /**
     * Insert.
     *
     * @param loanEntity the loan entity
     */
    @Insert("""
            insert into loans (user_id, book_id, due_date, status_id)
            values (#{userId}, #{bookId}, #{dueDate}, 0)
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(LoanEntity loanEntity);

    /**
     * Update.
     *
     * @param loanReturnCommand the loan return command
     */
    @Update("""
            update loans
            set return_date = #{returnDate},
                status_id   = 1
            where id = #{id}
            """)
    void update(UpdateLoanReturnCommand loanReturnCommand);

    /**
     * Mark overdue.
     *
     * @return the number of loans marked as overdue.
     */
    @Update("update loans set status_id = 2 where status_id = 0 and due_date < current_date")
    int markOverdue();

    /**
     * Renew.
     *
     * @param updateLoanRenewCommand the update loan renew command
     */
    @Update("""
            update loans
            set due_date  = #{dueDate},
                status_id = 0
            where id = #{id}
            """)
    void renew(UpdateLoanRenewCommand updateLoanRenewCommand);

    /**
     * Delete.
     *
     * @param id the id
     */
    @Delete("delete from loans where id = #{id}")
    void delete(Integer id);
}
