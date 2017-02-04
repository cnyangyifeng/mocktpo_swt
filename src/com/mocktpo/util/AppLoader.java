package com.mocktpo.util;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.ActivationCode;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.orm.mapper.ActivationCodeMapper;
import com.mocktpo.orm.mapper.UserTestAnswerMapper;
import com.mocktpo.orm.mapper.UserTestSessionMapper;
import com.mocktpo.util.constants.MT;
import com.mocktpo.window.RegisterWindow;
import com.mocktpo.window.SplashWindow;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

public class AppLoader extends Thread {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Application */

    protected MyApplication app;

    /* Display and Splash Window */

    protected Display d;
    protected SplashWindow splash;

    private String email;
    private boolean licensed;
    private CountDownLatch latch;

    public AppLoader(final SplashWindow splash) {
        this.app = MyApplication.get();
        this.d = app.getDisplay();
        this.splash = splash;
        this.licensed = false;
        this.latch = new CountDownLatch(1);
    }

    @Override
    public void run() {
        splash.proceed(msgs.getString("loading_resources"));
        ResourceManager.alloc(d);
        splash.proceed(msgs.getString("initializing_database"));
        DbUtils.init();
        app.setSqlSession(DbUtils.getSqlSession());
        splash.proceed(msgs.getString("validating"));
        final ActivationCodeMapper acm = app.getSqlSession().getMapper(ActivationCodeMapper.class);
        acm.schema();
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    List<ActivationCode> lz = acm.find();
                    while (lz.isEmpty()) {
                        splash.setVisible(false);
                        RegisterWindow register = new RegisterWindow();
                        register.openAndWaitForDisposal();
                        lz = acm.find();
                    }
                    email = lz.get(0).getEmail();
                    licensed = ActivationCodeUtils.isLicensed(email, lz.get(0).getContent());
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        splash.setVisible(true);
        if (licensed) {
            splash.proceed(msgs.getString("configuring_data"));
            SqlSession sqlSession = app.getSqlSession();
            final UserTestSessionMapper userTestSessionMapper = sqlSession.getMapper(UserTestSessionMapper.class);
            userTestSessionMapper.schema();
            // TODO Initialize user test sessions, wrong biz logic here!
            if (userTestSessionMapper.count() <= 0) {
                for (int i = 1; i <= 48; i++) {
                    UserTestSession userTestSession = new UserTestSession();
                    userTestSession.setEmail(email);
                    userTestSession.setTid(i);
                    userTestSession.setTitle(msgs.getString("tpo") + MT.STRING_SPACE + i);
                    userTestSession.setAlias(msgs.getString("tpo") + i);
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
                    userTestSessionMapper.insert(userTestSession);
                }
            }
            sqlSession.getMapper(UserTestAnswerMapper.class).schema();
            sqlSession.commit();
            splash.proceed(msgs.getString("welcome"));
            splash.close();
        } else {
            System.exit(0);
        }
    }
}
