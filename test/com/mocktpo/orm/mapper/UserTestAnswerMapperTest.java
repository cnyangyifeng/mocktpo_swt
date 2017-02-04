package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.UserTestAnswer;
import com.mocktpo.util.DbUtils;
import com.mocktpo.util.constants.ST;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTestAnswerMapperTest {

    private UserTestAnswerMapper mapper;
    private SqlSession sqlSession;

    @Before
    public void setUp() {
        DbUtils.init();
        sqlSession = DbUtils.getSqlSession();
        mapper = sqlSession.getMapper(UserTestAnswerMapper.class);
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
        UserTestAnswer userTestAnswer = new UserTestAnswer();
        userTestAnswer.setSid(0);
        userTestAnswer.setViewId(1);
        userTestAnswer.setSectionType(ST.SECTION_TYPE_WRITING);
        userTestAnswer.setAnswer("Hello, TPO1.");
        mapper.insert(userTestAnswer);
    }

    @Test
    public void testCount() {
        long count = mapper.count();
        System.out.println(count);
    }

    @After
    public void tearDown() {
        sqlSession.commit();
        sqlSession.close();
    }
}
