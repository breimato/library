package com.breixo.library;

import org.junit.jupiter.api.Test;
import com.breixo.library.infrastructure.adapter.output.mybatis.BookMyBatisMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.UserMyBatisMapper;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        FlywayAutoConfiguration.class,
        MybatisAutoConfiguration.class
})
class LibraryApplicationTests {

    @MockBean
    BookMyBatisMapper bookMyBatisMapper;

    @MockBean
    UserMyBatisMapper userMyBatisMapper;

    @Test
    void contextLoads() {
    }
}
