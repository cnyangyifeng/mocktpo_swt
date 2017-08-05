package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.paper.TestPaperPage;
import com.mocktpo.modules.paper.editor.ReadingPassageEditorView;
import com.mocktpo.modules.paper.editor.TestEditorView;
import com.mocktpo.modules.system.widgets.TestPaperViewCard;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.VT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.vo.TestViewVo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.util.ArrayList;
import java.util.List;

public class ReadingPaperView extends SashTestPaperView {

    /* Editor Views */

    protected List<TestEditorView> views = new ArrayList<TestEditorView>();
    protected int checkedViewNumber;

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
        this.checkedViewNumber = 0;
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    @Override
    protected void updateHeader() {
        readingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_READING_CHECKED, MT.IMAGE_SYSTEM_STEP_READING_CHECKED);
    }

    @Override
    protected void updateFooter() {

    }

    @Override
    protected void updateLeft() {
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
    protected void updateRight() {
        FormLayoutSet.layout(right).marginWidth(0).marginHeight(0).spacing(0);
        viewStack = new StackLayout();
        right.setLayout(viewStack);
        viewStack.topControl = getTestPaperView();
        right.layout();
    }

    private TestEditorView getTestPaperView() {
        TestEditorView view = null;
        List<TestViewVo> viewVos = page.getVo().getViewVos();
        int totalViewCount = viewVos.size();
        if (totalViewCount > 0 && checkedViewNumber >= 0 && checkedViewNumber < totalViewCount) {
            TestViewVo viewVo = viewVos.get(checkedViewNumber);
            int viewType = viewVo.getViewType();
            switch (viewType) {
                case VT.VIEW_TYPE_READING_PASSAGE:
                    view = new ReadingPassageEditorView(this, SWT.NONE, viewVo.getViewId());
                    break;
                default:
                    view = new ReadingPassageEditorView(this, SWT.NONE, viewVo.getViewId());
                    break;
            }
        }
        return view;
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    private void initTestPaperViewCards() {
        List<TestViewVo> viewVos = page.getVo().getViewVos();
        for (int i = 0; i < viewVos.size(); i++) {
            TestViewVo viewVo = viewVos.get(i);
            // Check Section Type
            TestPaperViewCard card = new TestPaperViewCard(lb, SWT.NONE, viewVo.getViewType(), i + 1);
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
