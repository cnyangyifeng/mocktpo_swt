package com.mocktpo.util;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.mapper.UserTestAnswerMapper;
import com.mocktpo.orm.mapper.UserTestSessionMapper;
import com.mocktpo.windows.SplashWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;

public class AppLoader extends Thread {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Application */

    private MyApplication app;

    /* Display and Splash Window */

    private Display d;
    private SplashWindow splash;

    public AppLoader(final SplashWindow splash) {
        this.app = MyApplication.get();
        this.d = app.getDisplay();
        this.splash = splash;
    }

    @Override
    public void run() {
        splash.proceed(msgs.getString("loading_resources"));
        ResourceManager.alloc(d);
        splash.proceed(msgs.getString("initializing_database"));
        DbUtils.init();
        app.setSqlSession(DbUtils.getSqlSession());
        final UserTestSessionMapper userTestSessionMapper = app.getSqlSession().getMapper(UserTestSessionMapper.class);
        userTestSessionMapper.schema();
        final UserTestAnswerMapper userTestAnswerMapper = app.getSqlSession().getMapper(UserTestAnswerMapper.class);
        userTestAnswerMapper.schema();
        splash.proceed(msgs.getString("complete"));
        splash.close();
    }
}
