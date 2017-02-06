package com.mocktpo.util;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.ActivationCode;
import com.mocktpo.orm.mapper.ActivationCodeMapper;
import com.mocktpo.orm.mapper.UserTestAnswerMapper;
import com.mocktpo.orm.mapper.UserTestSessionMapper;
import com.mocktpo.windows.RegisterWindow;
import com.mocktpo.windows.SplashWindow;
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
        final ActivationCodeMapper activationCodeMapper = app.getSqlSession().getMapper(ActivationCodeMapper.class);
        activationCodeMapper.schema();
        final UserTestSessionMapper userTestSessionMapper = app.getSqlSession().getMapper(UserTestSessionMapper.class);
        userTestSessionMapper.schema();
        final UserTestAnswerMapper userTestAnswerMapper = app.getSqlSession().getMapper(UserTestAnswerMapper.class);
        userTestAnswerMapper.schema();
        splash.proceed(msgs.getString("validating"));
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    List<ActivationCode> lz = activationCodeMapper.find();
                    while (lz.isEmpty()) {
                        splash.setVisible(false);
                        RegisterWindow register = new RegisterWindow();
                        register.openAndWaitForDisposal();
                        lz = activationCodeMapper.find();
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
            // splash.proceed(msgs.getString("configuring_data"));
            splash.proceed(msgs.getString("welcome"));
            splash.close();
        } else {
            System.exit(0);
        }
    }
}
