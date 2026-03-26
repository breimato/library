package com.breixo.library.infrastructure.adapter.output.mybatis;

import java.util.List;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.ReturnLoanCommand;
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
     * Find all.
     *
     * @return the list of loan entities
     */
    @Select("""
            select id, user_id, book_id, due_date, return_date, status_id, created_at, updated_at
            from loans
            """)
    List<LoanEntity> findAll();

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
     * @param returnLoanCommand the return loan command
     */
    @Update("""
            update loans
            set return_date = #{returnDate},
                status_id   = 1
            where id = #{id}
            """)
    void update(ReturnLoanCommand returnLoanCommand);

    /**
     * Delete.
     *
     * @param id the id
     */
    @Delete("delete from loans where id = #{id}")
    void delete(Integer id);
}
