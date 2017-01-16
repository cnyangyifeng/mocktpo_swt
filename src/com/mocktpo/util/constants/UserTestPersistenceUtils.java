package com.mocktpo.util.constants;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestAnswerMapper;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.view.test.TestView;
import org.apache.ibatis.session.SqlSession;

public class UserTestPersistenceUtils {

    public static void saveToNextView(TestView testView) {

        TestPage page = testView.getPage();
        UserTest userTest = page.getUserTest();
        userTest.setLastViewId(testView.getVo().getViewId() + 1);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestMapper.class).update(userTest);
        sqlSession.commit();
    }

    public static void saveToCurrentView(TestView testView) {

        TestPage page = testView.getPage();
        UserTest userTest = page.getUserTest();
        userTest.setLastViewId(testView.getVo().getViewId());

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestMapper.class).update(userTest);
        sqlSession.commit();
    }

    public static void saveToCurrentView(UserTest userTest, int viewId) {

        userTest.setLastViewId(viewId);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestMapper.class).update(userTest);
        sqlSession.commit();
    }

    public static void saveToPreviousView(TestView testView) {

        TestPage page = testView.getPage();
        UserTest userTest = page.getUserTest();
        userTest.setLastViewId(testView.getVo().getViewId() - 1);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestMapper.class).update(userTest);
        sqlSession.commit();
    }

    public static void saveVolumeControlVisibility(TestView testView) {

        TestPage page = testView.getPage();
        UserTest userTest = page.getUserTest();
        userTest.setVolumeControlHidden(!testView.isVolumeControlVisible());

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestMapper.class).update(userTest);
        sqlSession.commit();
    }

    public static void saveVolume(TestView testView, double volume) {

        TestPage page = testView.getPage();
        UserTest userTest = page.getUserTest();
        userTest.setVolume(volume);

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestMapper.class).update(userTest);
        sqlSession.commit();
    }

    public static void saveAnswers(TestView testView, String answerText) {

        TestPage page = testView.getPage();
        UserTest userTest = page.getUserTest();

        SqlSession sqlSession = MyApplication.get().getSqlSession();
        sqlSession.getMapper(UserTestAnswerMapper.class).update(userTest, answerText);
        sqlSession.commit();
    }
}
