package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.paper.TestPaperPage;
import com.mocktpo.modules.system.widgets.TestRecordCard;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.util.List;

public class ReadingPaperView extends SashTestPaperView {

    /* Widgets */

    private ScrolledComposite lsc;
    private Composite lb;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingPaperView(TestPaperPage page, int style) {
        super(page, style);
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    @Override
    public void updateHeader() {
        readingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_READING_CHECKED, MT.IMAGE_SYSTEM_STEP_READING_CHECKED);
    }

    @Override
    public void updateFooter() {

    }

    @Override
    public void updateLeft() {
        lsc = new ScrolledComposite(left, SWT.V_SCROLL);
        FormDataSet.attach(lsc).atLeft().atTop().atRight().atBottom();
        lsc.setExpandHorizontal(true);
        lsc.setExpandVertical(true);

        lb = new Composite(lsc, SWT.NONE);
        CompositeSet.decorate(lb).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        GridLayoutSet.layout(lb).marginWidth(20).marginHeight(20).horizontalSpacing(20).verticalSpacing(20);

        lsc.setContent(lb);
    }

    @Override
    public void updateRight() {
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    private void initTestPaperViewCards() {
        List<UserTestSession> sessions = PersistenceUtils.findSessions();
        for (UserTestSession session : sessions) {
            TestRecordCard card = new TestRecordCard(lb, SWT.NONE, session);
            GridDataSet.attach(card).fillHorizontal();
        }
        lb.layout();
        lsc.setMinSize(lb.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        lsc.setOrigin(0, 0);
    }

    public void refreshTestPaperViewCards() {
        for (Control c : lb.getChildren()) {
            c.dispose();
        }
        initTestPaperViewCards();
    }
}
