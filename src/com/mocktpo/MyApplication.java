package com.mocktpo;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import com.mocktpo.orm.domain.ActivationCode;
import com.mocktpo.orm.mapper.ActivationCodeMapper;
import com.mocktpo.util.DbUtils;
import com.mocktpo.util.FontUtils;
import com.mocktpo.util.ActivationCodeUtils;
import com.mocktpo.window.AppWindow;
import com.mocktpo.window.RegisterWindow;
import com.mocktpo.window.SplashWindow;

public class MyApplication {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    private Display d;
    private SqlSession sqlSession;

    public void init() {
        Display.setAppName(msgs.getString("app_name"));
        d = Display.getDefault();
        final SplashWindow splash = new SplashWindow(MyApplication.this);
        new AppLoader(splash).start();
        splash.openAndWaitForDisposal();
        final AppWindow main = new AppWindow(MyApplication.this);
        main.openAndWaitForDisposal();
        exitApplication();
    }

    private void exitApplication() {
        if (null != sqlSession) {
            sqlSession.close();
        }
    }

    private class AppLoader extends Thread {

        private SplashWindow splash;
        private boolean licensed;
        private CountDownLatch latch;

        public AppLoader(SplashWindow splash) {
            this.splash = splash;
            this.licensed = false;
            this.latch = new CountDownLatch(1);
        }

        @Override
        public void run() {
            FontUtils.loadFonts(d);
            DbUtils.init();
            setSqlSession(DbUtils.getSqlSession());
            splash.proceed(30);

            final ActivationCodeMapper acm = sqlSession.getMapper(ActivationCodeMapper.class);
            acm.schema();
            if (!d.isDisposed()) {
                d.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        List<ActivationCode> lz = acm.find();
                        while (lz.isEmpty()) {
                            RegisterWindow register = new RegisterWindow(MyApplication.this);
                            register.openAndWaitForDisposal();
                            lz = acm.find();
                        }
                        final String acc = ((ActivationCode) lz.get(0)).getContent();
                        licensed = ActivationCodeUtils.isLicensed(acc);
                        latch.countDown();
                    }
                });
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            splash.proceed(60);

            if (licensed) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                splash.proceed(100);
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                splash.close();
            }
        }
    }

    /**************************************************
     * Getters and Setters
     **************************************************/

    public Display getDisplay() {
        return d;
    }

    public void setDisplay(Display d) {
        this.d = d;
    }

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**************************************************
     * The Application Main Method
     **************************************************/

    public static void main(String[] args) {
        new MyApplication().init();
    }
}
