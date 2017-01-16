package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.domain.UserTestAnswer;
import com.mocktpo.util.DbUtils;
import com.mocktpo.util.constants.ST;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTestAnswerMapperTest {

    private UserTestAnswerMapper mapper;
    private SqlSession session;

    @Before
    public void setUp() {
        DbUtils.init();
        session = DbUtils.getSqlSession();
        mapper = session.getMapper(UserTestAnswerMapper.class);
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

        UserTestAnswer uta1 = new UserTestAnswer();
        uta1.setEmail("165239796@qq.com");
        uta1.setTid(1);
        uta1.setViewId(129);
        uta1.setSectionType(ST.SECTION_TYPE_WRITING);
        uta1.setAnswer("Hello, TPO1.");
        mapper.insert(uta1);

        UserTestAnswer uta24 = new UserTestAnswer();
        uta24.setEmail("165239796@qq.com");
        uta24.setTid(24);
        uta24.setViewId(128);
        uta24.setSectionType(ST.SECTION_TYPE_WRITING);
        uta24.setAnswer("Hello, TPO24.");
        mapper.insert(uta24);
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
