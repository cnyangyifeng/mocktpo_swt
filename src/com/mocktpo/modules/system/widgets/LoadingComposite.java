package com.mocktpo.modules.system.widgets;

import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;

public class LoadingComposite extends Composite {

    /* Widgets */

    private AnimatedGif loader;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public LoadingComposite(Composite parent, int style) {
        super(parent, style);
        init();
    }

    private void init() {
        golbal();
    }

    private void golbal() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_WHITE);
        GridLayoutSet.layout(this).marginWidth(0).marginHeight(0);

        loader = new AnimatedGif(this, SWT.NONE, "loading");
        GridDataSet.attach(loader).centerBoth().withWidth(128).withHeight(128);
    }

    public void animate() {
        loader.animate();
    }

    public void stop() {
        loader.stop();
    }
}
