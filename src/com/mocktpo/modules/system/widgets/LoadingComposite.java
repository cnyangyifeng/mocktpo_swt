package com.mocktpo.modules.system.widgets;

import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.RowLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class LoadingComposite extends Composite {

    /* Properties */

    private int count;

    public LoadingComposite(Composite parent, int style) {
        super(parent, style);
        init();
    }

    private void init() {
        golbal();
        initBody();
    }

    private void golbal() {
        RowLayoutSet.layout(this).marginWidth(0).marginHeight(0);
    }

    private void initBody() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_BLACK);
    }
}
