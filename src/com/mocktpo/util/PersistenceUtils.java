package com.mocktpo.util;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.test.TestPage;
import com.mocktpo.modules.test.views.TestView;
import com.mocktpo.orm.domain.UserTestAnswer;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.orm.mapper.UserTestAnswerMapper;
import com.mocktpo.orm.mapper.UserTestSessionMapper;
import com.mocktpo.util.constants.MT;
import com.mocktpo.vo.TestVo;
import com.mocktpo.vo.TestViewVo;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class PersistenceUtils {

    public static UserTestSession newSession(String fileAlias, TestVo testVo, boolean readingSelected, boolean listeningSelected, boolean speakingSelected, boolean writingSelected) {
        UserTestSession userTestSession = new UserTestSession();
        userTestSession.setTid(testVo.getTid());
        userTestSession.setTitle(testVo.getTitle());
        userTestSession.setFileAlias(fileAlias);
        userTestSession.setStars(testVo.getStars());
        userTestSession.setStartTime(System.currentTimeMillis());
        userTestSession.setLastVisitedTime(System.currentTimeMillis());
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
        userTestSession.setReadingSelected(readingSelected);
        userTestSession.setListeningSelected(listeningSelected);
        userTestSession.setSpeakingSelected(speakingSelected);
        userTestSession.setWritingSelected(writingSelected);
        userTestSession.setLastViewId(1);
        userTestSession.setMaxViewId(1);
        userTestSession.setVisitedViewCount(1);
        userTestSession.setTotalViewCount(testVo.findTotalViewCount(readingSelected, listeningSelected, speakingSelected, writingSelected));
        userTestSession.setTestComplete(false);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).create(userTestSession);
        sqlSession.commit();

        return userTestSession;
    }

    public static List<UserTestSession> findSessions() {
        return MyApplication.get().getSqlSession().getMapper(UserTestSessionMapper.class).find();
    }

    public static void deleteSession(UserTestSession userTestSession) {
        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).delete(userTestSession);
        sqlSession.getMapper(UserTestAnswerMapper.class).delete(userTestSession);
        sqlSession.commit();
    }

    public static void saveToView(UserTestSession userTestSession, int viewId) {
        userTestSession.setLastVisitedTime(System.currentTimeMillis());
        userTestSession.setLastViewId(viewId);
        userTestSession.setMaxViewId(viewId);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveToView(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();

        userTestSession.setLastVisitedTime(System.currentTimeMillis());
        userTestSession.setLastViewId(testView.getVo().getViewId());
        userTestSession.setMaxViewId(testView.getVo().getViewId());

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveToNextView(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();

        userTestSession.setLastVisitedTime(System.currentTimeMillis());
        userTestSession.setLastViewId(testView.getVo().getViewId() + 1);
        userTestSession.setMaxViewId(testView.getVo().getViewId() + 1);
        int visitedViewCount = userTestSession.getVisitedViewCount();
        userTestSession.setVisitedViewCount(visitedViewCount + 1);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveToTestEnd(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();

        userTestSession.setTestComplete(true);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveToPreviousView(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();

        userTestSession.setLastVisitedTime(System.currentTimeMillis());
        userTestSession.setLastViewId(testView.getVo().getViewId() - 1);
        userTestSession.setMaxViewId(testView.getVo().getViewId() - 1);

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

    public static void saveRemainingViewTime(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();

        userTestSession.setRemainingViewTime(testView.getVo(), testView.getCountDown());

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

    public static void saveTimerHidden(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();

        userTestSession.setTimerHidden(testView.isTimerHidden());

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

    public static void saveVolume(TestView testView, double volume) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();

        userTestSession.setVolume(volume);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static void saveSpeakingReadingTime(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();

        switch (testView.getVo().getSpeakingReadingId()) {
            case 1:
                userTestSession.setSpeakingReadingTime1(testView.getVo().getSpeakingReadingTime());
                break;
            case 2:
                userTestSession.setSpeakingReadingTime2(testView.getVo().getSpeakingReadingTime());
                break;
        }

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestSessionMapper.class).update(userTestSession);
        sqlSession.commit();
    }

    public static UserTestAnswer newAnswer(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();

        UserTestAnswer userTestAnswer = new UserTestAnswer();
        userTestAnswer.setSid(userTestSession.getSid());
        userTestAnswer.setViewId(userTestSession.getLastViewId());
        userTestAnswer.setSectionType(page.getTestVo().getViewVo(userTestSession.getLastViewId()).getSectionType());
        userTestAnswer.setAnswer("");

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestAnswerMapper.class).create(userTestAnswer);
        sqlSession.commit();

        return userTestAnswer;
    }

    public static UserTestAnswer findAnswer(UserTestSession userTestSession, int viewId) {
        SqlSession sqlSession = MyApplication.get().getSqlSession();
        UserTestAnswerMapper mapper = sqlSession.getMapper(UserTestAnswerMapper.class);
        return mapper.findByViewId(userTestSession, viewId);
    }

    public static UserTestAnswer findAnswer(TestView testView) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        UserTestAnswerMapper mapper = sqlSession.getMapper(UserTestAnswerMapper.class);
        return mapper.find(userTestSession);
    }

    public static void saveAnswer(TestView testView, String answerText) {
        TestPage page = testView.getPage();
        UserTestSession userTestSession = page.getUserTestSession();

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestAnswerMapper.class).update(userTestSession, answerText);
        sqlSession.commit();
    }
}
