package com.mocktpo;

import java.util.ResourceBundle;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import com.mocktpo.util.AppLoader;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.window.MainWindow;
import com.mocktpo.window.SplashWindow;

public class MyApplication {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    protected Display d;

    private SqlSession sqlSession;

    public void init() {
        Display.setAppName(msgs.getString("app_name"));
        d = Display.getDefault();
        final SplashWindow splash = new SplashWindow(this);
        new AppLoader(this, splash).start();
        splash.openAndWaitForDisposal();
        final MainWindow main = new MainWindow(this);
        main.openAndWaitForDisposal();
        exitApplication();
    }

    private void exitApplication() {
        ResourceManager.dispose();
        if (null != d) {
            d.dispose();
        }
        if (null != sqlSession) {
            sqlSession.close();
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
