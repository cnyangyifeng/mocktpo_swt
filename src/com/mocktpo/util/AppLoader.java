package com.mocktpo.util;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.ActivationCode;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.ActivationCodeMapper;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.util.constants.MT;
import com.mocktpo.window.RegisterWindow;
import com.mocktpo.window.SplashWindow;
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
            final UserTestMapper utm = app.getSqlSession().getMapper(UserTestMapper.class);
            utm.schema();
            if (utm.count() <= 0) {
                splash.proceed(msgs.getString("configuring_data"));
                for (int i = 1; i <= 48; i++) {
                    UserTest ut = new UserTest();
                    ut.setEmail(email);
                    ut.setTid(i);
                    ut.setTitle(msgs.getString("tpo") + MT.STRING_SPACE + i);
                    ut.setAlias(msgs.getString("tpo") + i);
                    ut.setTimerHidden(false);
                    ut.setReadingTime(MT.TIME_READING_SECTION);
                    ut.setListeningTime1(MT.TIME_LISTENING_GROUP);
                    ut.setListeningTime2(MT.TIME_LISTENING_GROUP);
                    ut.setSpeakingReadingTime1(MT.TIME_SPEAKING_READING_GROUP);
                    ut.setSpeakingReadingTime2(MT.TIME_SPEAKING_READING_GROUP);
                    ut.setVolume(1.0);
                    ut.setVolumeControlHidden(true);
                    ut.setCompletionRate(0);
                    ut.setLastViewId(1);
                    utm.insert(ut);
                }
                app.getSqlSession().commit();
            }
            splash.proceed(msgs.getString("welcome"));
            splash.close();
        } else {
            System.exit(0);
        }
    }
}
