package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.util.DbUtils;
import com.mocktpo.util.constants.MT;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
            ut.setEmail("165239796@qq.com");
            ut.setTid(i);
            ut.setTitle("TPO" + MT.STRING_SPACE + i);
            ut.setAlias("TPO" + i);
            ut.setTimerHidden(false);
            ut.setReadingTime(MT.TIME_READING_SECTION);
            ut.setListeningTime1(MT.TIME_LISTENING_GROUP);
            ut.setListeningTime2(MT.TIME_LISTENING_GROUP);
            ut.setVolume(1.0);
            ut.setLastViewId(1);
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
        ut.setTitle("Title X");
        ut.setAlias("Alias X");
        ut.setEmail("Email X");
        ut.setLastViewId(1);
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
