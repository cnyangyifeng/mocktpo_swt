package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.DbUtils;
import com.mocktpo.util.constants.MT;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserTestSessionMapperTest {

    private UserTestSessionMapper mapper;
    private SqlSession sqlSession;

    @Before
    public void setUp() {
        DbUtils.init();
        sqlSession = DbUtils.getSqlSession();
        mapper = sqlSession.getMapper(UserTestSessionMapper.class);
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
            UserTestSession userTestSession = new UserTestSession();
            userTestSession.setEmail("165239796@qq.com");
            userTestSession.setTid(i);
            userTestSession.setTitle("TPO" + MT.STRING_SPACE + i);
            userTestSession.setAlias("TPO" + i);
            userTestSession.setTimerHidden(false);
            userTestSession.setReadingTime(MT.TIME_READING_SECTION);
            userTestSession.setListeningTime1(MT.TIME_LISTENING_PER_SUB_SECTION);
            userTestSession.setListeningTime2(MT.TIME_LISTENING_PER_SUB_SECTION);
            userTestSession.setSpeakingReadingTime1(MT.TIME_SPEAKING_READING_PER_TASK);
            userTestSession.setSpeakingReadingTime2(MT.TIME_SPEAKING_READING_PER_TASK);
            userTestSession.setWritingReadingTime(MT.TIME_WRITING_READING_PER_TASK);
            userTestSession.setVolume(1.0);
            userTestSession.setVolumeControlHidden(true);
            userTestSession.setStars(0);
            userTestSession.setLastViewId(1);
            userTestSession.setMaxViewId(1);
            mapper.insert(userTestSession);
        }
    }

    @Test
    public void testFind() {
        List<UserTestSession> list = mapper.find();
        for (UserTestSession userTestSession : list) {
            System.out.println(userTestSession);
        }
    }

    @Test
    public void testUpdate() {
        int i = 1;
        UserTestSession userTestSession = new UserTestSession();
        userTestSession.setEmail("165239796@qq.com");
        userTestSession.setTid(i);
        userTestSession.setTitle("TPO" + MT.STRING_SPACE + i);
        userTestSession.setAlias("TPO" + i);
        userTestSession.setTimerHidden(false);
        userTestSession.setReadingTime(MT.TIME_READING_SECTION);
        userTestSession.setListeningTime1(MT.TIME_LISTENING_PER_SUB_SECTION);
        userTestSession.setListeningTime2(MT.TIME_LISTENING_PER_SUB_SECTION);
        userTestSession.setSpeakingReadingTime1(MT.TIME_SPEAKING_READING_PER_TASK);
        userTestSession.setSpeakingReadingTime2(MT.TIME_SPEAKING_READING_PER_TASK);
        userTestSession.setWritingReadingTime(MT.TIME_WRITING_READING_PER_TASK);
        userTestSession.setIntegratedWritingTime(MT.TIME_INTEGRATED_WRITING_TASK);
        userTestSession.setIndependentWritingTime(MT.TIME_INDEPENDENT_WRITING_TASK);
        userTestSession.setVolume(1.0);
        userTestSession.setVolumeControlHidden(true);
        userTestSession.setStars(0);
        userTestSession.setLastViewId(122);
        userTestSession.setMaxViewId(122);
        mapper.update(userTestSession);
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
