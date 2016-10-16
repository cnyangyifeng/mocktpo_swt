package com.mocktpo.util;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.eclipse.swt.widgets.Display;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.ActivationCode;
import com.mocktpo.orm.mapper.ActivationCodeMapper;
import com.mocktpo.window.RegisterWindow;
import com.mocktpo.window.SplashWindow;

public class AppLoader extends Thread {

    protected MyApplication app;
    protected Display d;
    protected SplashWindow splash;

    private boolean licensed;
    private CountDownLatch latch;

    public AppLoader(MyApplication app, SplashWindow splash) {
        this.app = app;
        this.d = app.getDisplay();
        this.splash = splash;
        this.licensed = false;
        this.latch = new CountDownLatch(1);
    }

    @Override
    public void run() {
        ResourceManager.alloc(d);
        splash.proceed(20);

        DbUtils.init();
        app.setSqlSession(DbUtils.getSqlSession());
        splash.proceed(40);

        final ActivationCodeMapper acm = app.getSqlSession().getMapper(ActivationCodeMapper.class);
        acm.schema();
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    List<ActivationCode> lz = acm.find();
                    while (lz.isEmpty()) {
                        splash.setVisible(false);
                        RegisterWindow register = new RegisterWindow(app);
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
        splash.setVisible(true);
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
