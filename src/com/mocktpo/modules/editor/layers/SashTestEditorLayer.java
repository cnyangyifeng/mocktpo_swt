package com.mocktpo.modules.editor.layers;

import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.modules.editor.views.TestEditorView;
import com.mocktpo.modules.editor.widgets.TestEditorCard;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;
import java.util.List;

public abstract class SashTestEditorLayer extends TestEditorLayer {

    /* Constants */

    private static final int LEFT_COMPOSITE_WIDTH = 232;

    /* Widgets */

    protected Composite body;

    protected Composite left;
    protected ScrolledComposite lsc;
    protected Composite leftBody;
    protected List<TestEditorCard> cards;

    protected Composite right;
    protected StackLayout rightViewStack;
    protected List<TestEditorView> views;

    /* Properties */

    protected int currentViewId;
    protected boolean refreshRequired;

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
        this.currentViewId = -1;
        this.refreshRequired = true;
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
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(0);
        initLeft();
        initRight();
    }

    private void initLeft() {
        left = new Composite(body, SWT.NONE);
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
        right = new Composite(body, SWT.NONE);
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
     * Test Editor View Operations
     *
     * ==================================================
     */

    public abstract void trash();

    public abstract void sendBackward();

    public abstract void bringForward();

    /*
     * ==================================================
     *
     * Test Editor Card Operations
     *
     * ==================================================
     */

    public void check(int viewId) {
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

    public boolean isFirstCardChecked() {
        return currentViewId == 0;
    }

    public boolean isLastCardChecked() {
        return currentViewId == cards.size() - 1;
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

    public int getCurrentViewId() {
        return currentViewId;
    }

    public void setCurrentViewId(int currentViewId) {
        this.currentViewId = currentViewId;
    }

    public boolean isRefreshRequired() {
        return refreshRequired;
    }

    public void setRefreshRequired(boolean refreshRequired) {
        this.refreshRequired = refreshRequired;
    }
}
