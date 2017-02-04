package com.mocktpo.util;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.orm.mapper.UserTestAnswerMapper;
import com.mocktpo.orm.mapper.UserTestSessionMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.constants.MT;
import com.mocktpo.view.test.TestView;
import com.mocktpo.vo.TestViewVo;
import org.apache.ibatis.session.SqlSession;

public class UserTestPersistenceUtils {

    public static void saveToNextView(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();
        userTestSession.setLastViewId(testView.getVo().getViewId() + 1);
        userTestSession.setMaxViewId(testView.getVo().getViewId() + 1);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveToCurrentView(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();
        userTestSession.setLastViewId(testView.getVo().getViewId());
        userTestSession.setMaxViewId(testView.getVo().getViewId());

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveToCurrentView(UserTestSession userTestSession, int viewId) {
        userTestSession.setLastViewId(viewId);
        userTestSession.setMaxViewId(viewId);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveToPreviousView(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();
        userTestSession.setLastViewId(testView.getVo().getViewId() - 1);
        userTestSession.setMaxViewId(testView.getVo().getViewId() - 1);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveVolumeControlVisibility(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();
        userTestSession.setVolumeControlHidden(!testView.isVolumeControlVisible());

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveRemainingViewTime(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();
        userTestSession.setRemainingViewTime(testView.getVo(), testView.getCountDown());

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveRemainingViewTime(UserTestSession userTestSession, TestViewVo vo, int countDown) {
        userTestSession.setRemainingViewTime(vo, countDown);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveTimerHidden(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();
        userTestSession.setTimerHidden(testView.isTimerHidden());

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveTimerHidden(UserTestSession userTestSession, boolean timerHidden) {
        userTestSession.setTimerHidden(timerHidden);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveVolume(TestView testView, double volume) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();
        userTestSession.setVolume(volume);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveAnswers(TestView testView, String answerText) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestAnswerMapper.class).update(userTestSession, answerText);
        sqlSession.commit();
    }

    public static void restart(UserTestSession userTestSession) {
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
        userTestSession.setLastViewId(1);
        userTestSession.setMaxViewId(1);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();

        sqlSession.getMapper(UserTestAnswerMapper.class).delete(userTestSession);
        sqlSession.commit();
    }
}
