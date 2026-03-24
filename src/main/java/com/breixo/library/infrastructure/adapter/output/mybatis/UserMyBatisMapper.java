package com.breixo.library.infrastructure.adapter.output.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

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
                status,
                created_at,
                updated_at
            from users
            <where>
                <if test="id != null">and id = #{id}</if>
                <if test="email != null">and email = #{email}</if>
            </where>
            </script>
            """)
    List<UserEntity> find(UserSearchCriteriaCommand userSearchCriteriaCommand);

    /**
     * Insert.
     *
     * @param userEntity the user entity.
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into users (name, email, phone, status) values (#{name}, #{email}, #{phone}, #{status}::user_status)")
    void insert(UserEntity userEntity);
}
