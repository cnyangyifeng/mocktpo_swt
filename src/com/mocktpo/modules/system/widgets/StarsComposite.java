package com.mocktpo.modules.system.widgets;

import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.RowLayoutSet;
import com.mocktpo.util.widgets.LabelSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class StarsComposite extends Composite {

    /* Constants */

    private static final int TOTAL_STARS_COUNT = 5;

    /* Properties */

    private int count;

    public StarsComposite(Composite parent, int style, int count) {
        super(parent, style);
        this.count = count;
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
        for (int i = 0; i < count; i++) {
            final Label starLabel = new Label(this, SWT.NONE);
            LabelSet.decorate(starLabel).setImage(MT.IMAGE_SYSTEM_STAR);
        }
        for (int i = 0; i < TOTAL_STARS_COUNT - count; i++) {
            final Label starLabel = new Label(this, SWT.NONE);
            LabelSet.decorate(starLabel).setImage(MT.IMAGE_SYSTEM_STAR_OUTLINE);
        }
    }
}
