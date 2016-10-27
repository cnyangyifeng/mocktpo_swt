package com.mocktpo.orm.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.util.DbUtils;
import com.mocktpo.util.constants.TV;

public class UserTestMapperTest {

    private UserTestMapper mapper;
    private SqlSession session;

    @Before
    public void setUp() {
        DbUtils.init();
        session = DbUtils.getSqlSession();
        mapper = session.getMapper(UserTestMapper.class);
    }

    @Test
    public void testSchema() {
        mapper.schema();
    }

    @Test
    public void testDrop() {
        mapper.drop();
    }

    @Test
    public void testInsert() {
        for (int i = 1; i <= 48; i++) {
            UserTest ut = new UserTest();
            ut.setTid(i);
            ut.setTitle("TPO " + i);
            ut.setUserName("165239796@qq.com");
            ut.setLastViewId(TV.VIEW_TEST_INTRO);
            mapper.insert(ut);
        }
    }

    @Test
    public void testFind() {
        List<UserTest> list = mapper.find();
        for (UserTest ut : list) {
            System.out.println(ut);
        }
    }

    @Test
    public void testUpdate() {
        UserTest ut = new UserTest();
        ut.setTid(1);
        ut.setTitle("TPO 01");
        ut.setUserName("165239796@qq.com");
        ut.setLastViewId(3);
        mapper.update(ut);
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
