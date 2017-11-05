package com.mocktpo.modules.editor.layers;

import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;

import java.util.ResourceBundle;

public class LoadingEditorLayer extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public LoadingEditorLayer(Composite parent, int style) {
        super(parent, style);
        init();
    }

    private void init() {
        golbal();
    }

    private void golbal() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        GridLayoutSet.layout(this).marginWidth(0).marginHeight(0).spacing(0);

        CLabel label = new CLabel(this, SWT.NONE);
        GridDataSet.attach(label).centerBoth();
        CLabelSet.decorate(label).setForeground(MT.COLOR_BLUE_GREY_DARKEN_4).setFont(MT.FONT_LARGE).setText(msgs.getString("loading_please_wait"));
    }
}
