package com.mocktpo;

import com.mocktpo.util.AppLoader;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.window.MainWindow;
import com.mocktpo.window.SplashWindow;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;

public class MyApplication {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    protected MainWindow win;
    protected Display d;

    protected SqlSession sqlSession;

    private void init() {
        Display.setAppName(msgs.getString("app_name"));
        d = Display.getDefault();
        final SplashWindow splash = new SplashWindow();
        new AppLoader(splash).start();
        splash.openAndWaitForDisposal();
        win = new MainWindow();
        win.openAndWaitForDisposal();
        exitApplication();
    }

    public void exitApplication() {
        ResourceManager.dispose();
        if (null != d) {
            d.dispose();
        }
        if (null != sqlSession) {
            sqlSession.close();
        }
        System.exit(0);
    }

    /**************************************************
     * Getters and Setters
     **************************************************/

    public MainWindow getWindow() {
        return win;
    }

    public Display getDisplay() {
        return d;
    }

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**************************************************
     * The Application Singleton
     **************************************************/

    private static MyApplication inst = null;

    private MyApplication() {
    }

    public static MyApplication get() {
        if (null == inst) {
            synchronized (MyApplication.class) {
                if (null == inst) {
                    inst = new MyApplication();
                }
            }
        }
        return inst;
    }

    /**************************************************
     * The Application Main Method
     **************************************************/

    public static void main(String[] args) {
        MyApplication.get().init();
    }
}
