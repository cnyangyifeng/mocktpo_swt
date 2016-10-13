package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.UserModel;
import com.mocktpo.util.DbUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserMapperTest {

    private UserMapper mapper;
    private SqlSession session;

    @Before
    public void setUp() {
        DbUtils.init();
        session = DbUtils.getSqlSession();
        mapper = session.getMapper(UserMapper.class);
    }

    @Test
    public void testSchema() {
        mapper.schema();
    }

    @Test
    public void testInsert() {
        UserModel user = new UserModel();
        user.setName("eric");
        user.setEmail("account@qq.com");
        user.setLicense("1234-1234-1234-1234");

        mapper.insert(user);
    }

    @Test
    public void testCount() {
        long count = mapper.count();
        System.out.println(count);
    }

    @After
    public void tearDown() {
        session.commit();
        session.close();
    }
}
