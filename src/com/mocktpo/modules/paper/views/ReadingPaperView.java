package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.paper.TestPaperPage;
import com.mocktpo.modules.system.widgets.TestPaperViewCard;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ReadingPaperView extends SashTestPaperView {

    /* Stack */

    private StackLayout viewStack;

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
        FormLayoutSet.layout(right).marginWidth(0).marginHeight(0).spacing(0);
        viewStack = new StackLayout();
        right.setLayout(viewStack);
        viewStack.topControl = getTestPaperView();
        right.layout();
    }

    private TestEditorView getTestPaperView() {
        TestEditorView tpv;
        tpv = new ReadingPassageEditorView(this, SWT.NONE, 1);
        return tpv;
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    private void initTestPaperViewCards() {
        for (int i = 1; i <= 10; i++) {
            TestPaperViewCard card = new TestPaperViewCard(lb, SWT.NONE, i);
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
