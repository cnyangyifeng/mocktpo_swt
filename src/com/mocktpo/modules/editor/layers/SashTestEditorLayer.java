package com.mocktpo.modules.editor.layers;

import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.modules.editor.views.ReadingMultipleChoiceQuestionEditorView;
import com.mocktpo.modules.editor.views.ReadingPassageEditorView;
import com.mocktpo.modules.editor.views.TestEditorView;
import com.mocktpo.modules.editor.widgets.TestEditorCard;
import com.mocktpo.modules.system.widgets.LoadingComposite;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.VT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.vo.TestViewVo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;
import java.util.List;

public abstract class SashTestEditorLayer extends TestEditorLayer {

    /* Constants */

    private static final int LEFT_COMPOSITE_WIDTH = 232;

    /* Stack */

    private StackLayout stack;

    /* Widgets */

    protected Composite body;
    protected LoadingComposite loadingComposite;
    protected Composite mainComposite;
    protected Composite left;
    protected ScrolledComposite lsc;
    protected Composite leftBody;
    protected Composite right;
    protected StackLayout rightViewStack;

    /* Properties */

    protected List<TestEditorCard> cards;
    protected List<TestEditorView> views;
    protected int currentViewId;
    protected boolean dirty;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public SashTestEditorLayer(TestEditorPage page, int style) {
        super(page, style);
        this.cards = new ArrayList<TestEditorCard>();
        this.views = new ArrayList<TestEditorView>();
        if (page.getTestVo().getViewVos().size() > 0) {
            this.currentViewId = 0;
        } else {
            this.currentViewId = -1;
        }
        this.dirty = true;
    }

    /*
     * ==================================================
     *
     * Body Initialization
     *
     * ==================================================
     */

    @Override
    protected void initBody() {
        body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(header).atRight().atBottomTo(footer);
        stack = new StackLayout();
        body.setLayout(stack);

        loadingComposite = new LoadingComposite(body, SWT.NONE);
        mainComposite = new Composite(body, SWT.NONE);
        FormLayoutSet.layout(mainComposite).marginWidth(0).marginHeight(0).spacing(0);
        initLeft();
        initRight();
    }

    private void initLeft() {
        left = new Composite(mainComposite, SWT.NONE);
        FormDataSet.attach(left).atLeft().atTop().atBottom().withWidth(LEFT_COMPOSITE_WIDTH);
        FormLayoutSet.layout(left).marginWidth(0).marginHeight(0).spacing(0);

        final Label divider = new Label(left, SWT.VERTICAL);
        FormDataSet.attach(divider).atTop().atRight().atBottom().withWidth(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        lsc = new ScrolledComposite(left, SWT.V_SCROLL);
        FormDataSet.attach(lsc).atLeft().atTop().atRight().atBottom();
        lsc.setExpandHorizontal(true);
        lsc.setExpandVertical(true);

        leftBody = new Composite(lsc, SWT.NONE);
        CompositeSet.decorate(leftBody).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        GridLayoutSet.layout(leftBody).marginWidth(20).marginHeight(20).horizontalSpacing(20).verticalSpacing(20);

        lsc.setContent(leftBody);

        updateLeft();
    }

    private void initRight() {
        right = new Composite(mainComposite, SWT.NONE);
        FormDataSet.attach(right).atLeftTo(left).atTop().atRight().atBottom();
        FormLayoutSet.layout(right).marginWidth(0).marginHeight(0).spacing(0);

        FormLayoutSet.layout(right).marginWidth(0).marginHeight(0).spacing(0);
        rightViewStack = new StackLayout();
        right.setLayout(rightViewStack);

        updateRight();
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    protected abstract void updateLeft();

    protected abstract void updateRight();

    /*
     * ==================================================
     *
     * Test Editor Card Operations
     *
     * ==================================================
     */

    public void refreshCards() {
        if (!isDirty()) {
            return;
        }
        toLoadingComposite();
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    for (Control c : leftBody.getChildren()) {
                        c.dispose();
                    }
                    initCards();
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    toMainComposite();
                }
            });
        }
    }

    public void toLoadingComposite() {
        loadingComposite.load();

        stack.topControl = loadingComposite;
        body.layout();
    }

    public void toMainComposite() {
        loadingComposite.stop();

        stack.topControl = mainComposite;
        body.layout();
    }

    private void initCards() {
        List<TestViewVo> viewVos = page.getTestVo().getViewVos();

        /*
         * ==================================================
         *
         * Update Left Part
         *
         * ==================================================
         */

        cards.clear();
        for (TestViewVo viewVo : viewVos) {
            TestEditorCard card = new TestEditorCard(this, SWT.NONE, viewVo);
            GridDataSet.attach(card).fillHorizontal();
            cards.add(card);
        }
        leftBody.layout();
        lsc.setMinSize(leftBody.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        if (currentViewId >= 0) {
            lsc.setOrigin(0, cards.get(currentViewId).getLocation().y - 20);
        }

        /*
         * ==================================================
         *
         * Update Right Part
         *
         * ==================================================
         */

        views.clear();
        for (TestViewVo viewVo : viewVos) {
            TestEditorView view = null;
            switch (viewVo.getViewType()) {
                case VT.VIEW_TYPE_READING_PASSAGE:
                    view = new ReadingPassageEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_READING_MULTIPLE_CHOICE_QUESTION:
                    view = new ReadingMultipleChoiceQuestionEditorView(this, SWT.NONE, viewVo);
                    break;
            }
            views.add(view);
        }

        checkEditorView(currentViewId);
        setDirty(false);
    }

    public void checkEditorView(int viewId) {
        if (currentViewId < 0) {
            return;
        }

        TestEditorCard previousCard = cards.get(currentViewId);
        previousCard.setChecked(false);
        currentViewId = viewId;
        TestEditorCard currentCard = cards.get(currentViewId);
        currentCard.setChecked(true);

        rightViewStack.topControl = views.get(currentViewId);
        right.layout();
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public Composite getLeftBody() {
        return leftBody;
    }

    public Composite getRight() {
        return right;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
