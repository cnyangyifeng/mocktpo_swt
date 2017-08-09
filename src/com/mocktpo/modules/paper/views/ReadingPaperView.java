package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.paper.TestPaperPage;
import com.mocktpo.modules.paper.editor.ReadingPassageEditorView;
import com.mocktpo.modules.paper.editor.TestEditorView;
import com.mocktpo.modules.system.widgets.ImageButton;
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
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import java.util.ArrayList;
import java.util.List;

public class ReadingPaperView extends SashTestPaperView {

    /* Editor Views */

    private List<TestPaperViewCard> cards;
    private List<TestEditorView> editorViews;
    private int checkedId;

    /* Stack */

    private StackLayout viewStack;

    /* Widgets */

    private ScrolledComposite lsc;
    private Composite lb;

    private ImageButton newReadingPassageButton;
    private ImageButton newReadingQuestionButton;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingPaperView(TestPaperPage page, int style) {
        super(page, style);
        this.cards = new ArrayList<TestPaperViewCard>();
        this.editorViews = new ArrayList<TestEditorView>();
        this.checkedId = 0;
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
        newReadingPassageButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_NEW_READING_PASSAGE, MT.IMAGE_SYSTEM_NEW_READING_PASSAGE_HOVER);
        FormDataSet.attach(newReadingPassageButton).atLeft().atTop();
        newReadingPassageButton.addMouseListener(new NewReadingPassageButtonMouseAdapter());

        newReadingQuestionButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_NEW_READING_QUESTION, MT.IMAGE_SYSTEM_NEW_READING_QUESTION_HOVER);
        FormDataSet.attach(newReadingQuestionButton).atLeftTo(newReadingPassageButton).atTopTo(newReadingPassageButton, 0, SWT.TOP);
        newReadingQuestionButton.addMouseTrackListener(new NewReadingQuestionButtonMouseTrackAdapter());
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
        List<TestViewVo> viewVos = page.getTestPaperVo().getViewVos();
        int totalViewCount = viewVos.size();
        if (totalViewCount > 0 && checkedId >= 0 && checkedId < totalViewCount) {
            TestViewVo viewVo = viewVos.get(checkedId);
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

    public void refreshTestPaperViewCards() {
        for (Control c : lb.getChildren()) {
            c.dispose();
        }
        initTestPaperViewCards();
    }

    private void initTestPaperViewCards() {
        List<TestViewVo> viewVos = page.getTestPaperVo().getViewVos();
        for (int i = 0; i < viewVos.size(); i++) {
            TestViewVo viewVo = viewVos.get(i);
            TestPaperViewCard card = new TestPaperViewCard(lb, SWT.NONE, viewVo.getViewType());
            GridDataSet.attach(card).fillHorizontal();
            cards.add(card);
        }
        lb.layout();
        lsc.setMinSize(lb.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        lsc.setOrigin(0, 0);
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class NewReadingPassageButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            // TODO Add a new reading passage view to the current list
            TestPaperViewCard card = new TestPaperViewCard(lb, SWT.NONE, VT.VIEW_TYPE_READING_PASSAGE);
            GridDataSet.attach(card).fillHorizontal();
            lb.layout();
            lsc.setMinSize(lb.computeSize(SWT.DEFAULT, SWT.DEFAULT));
            lsc.setOrigin(0, 0);
        }
    }

    private class NewReadingQuestionButtonMouseTrackAdapter extends MouseTrackAdapter {

        public void mouseEnter(MouseEvent var1) {
        }

        public void mouseExit(MouseEvent var1) {
        }
    }
}
