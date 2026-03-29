package com.breixo.library.infrastructure.adapter.output.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.infrastructure.adapter.output.entities.UserEntity;

/** The Interface User My Batis Mapper. */
@Mapper
public interface UserMyBatisMapper {

    /**
     * Find.
     *
     * @param userSearchCriteriaCommand the user search criteria command.
     * @return the list of user entities.
     */
    @Select("""
            <script>
            select
                id,
                name,
                email,
                phone,
                status_id
            from users
            <where>
                <if test="id != null">and id = #{id}</if>
                <if test="email != null">and email = #{email}</if>
            </where>
            </script>
            """)
    List<UserEntity> find(UserSearchCriteriaCommand userSearchCriteriaCommand);

    /**
     * Find all.
     *
     * @return the list of user entities.
     */
    @Select("select id, name, email, phone, status_id from users")
    List<UserEntity> findAll();

    /**
     * Insert.
     *
     * @param userEntity the user entity.
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into users (name, email, phone, status_id) values (#{name}, #{email}, #{phone}, 0)")
    void insert(UserEntity userEntity);

    /**
     * Update.
     *
     * @param userEntity the user entity.
     */
    @Update("""
            <script>
            update users
            <set>
                <if test="name != null">name = #{name},</if>
                <if test="phone != null">phone = #{phone},</if>
                <if test="statusId != null">status_id = #{statusId},</if>
            </set>
            where id = #{id}
            </script>
            """)
    void update(UserEntity userEntity);

    /**
     * Delete.
     *
     * @param id the user identifier.
     */
    @Delete("delete from users where id = #{id}")
    void delete(Integer id);
}
