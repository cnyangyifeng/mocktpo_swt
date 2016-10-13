package com.mocktpo.util;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WindowUtilsTest {

    private Display d;

    private Shell s;

    @Before
    public void setUp() {
        d = Display.getDefault();
        s = new Shell(d);
    }

    @Test
    public void testCenter() {
        s.setSize(640, 480);
        WindowUtils.center(s);
    }

    @After
    public void tearDown() {
        s.open();
        while (!s.isDisposed()) {
            if (!d.readAndDispatch()) {
                d.sleep();
            }
        }
        d.dispose();
    }
}
