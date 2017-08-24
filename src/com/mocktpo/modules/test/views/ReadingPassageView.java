package com.mocktpo.modules.test.views;

import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.modules.test.TestPage;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.ScreenUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.StyleRangeUtils;
import com.mocktpo.util.widgets.StyledTextSet;
import com.mocktpo.modules.test.windows.MoreTextWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;

public class ReadingPassageView extends SashTestView {

    /* Widgets */

    private CLabel indicator;

    /* Properties */

    private boolean goneThrough;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingPassageView(TestPage page, int style) {
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
    protected void updateHeader() {
        final ImageButton continueOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE_OVAL, MT.IMAGE_CONTINUE_OVAL_HOVER, MT.IMAGE_CONTINUE_OVAL_DISABLED);
        FormDataSet.attach(continueOvalButton).atRight(10).atTop(10);
        continueOvalButton.addMouseListener(new ContinueOvalButtonMouseAdapter());
        if (!vo.isFirstPassage()) {
            final ImageButton backOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_BACK_OVAL, MT.IMAGE_BACK_OVAL_HOVER, MT.IMAGE_BACK_OVAL_DISABLED);
            FormDataSet.attach(backOvalButton).atRightTo(continueOvalButton).atTop(10);
            backOvalButton.addMouseListener(new BackOvalButtonMouseAdapter());
        }
    }

    @Override
    public void updateRight() {
        initIndicator();
        initRightBody();
    }

    private void initIndicator() {
        indicator = new CLabel(right, SWT.RIGHT);
        FormDataSet.attach(indicator).atLeft().atTop().atRight();
        CLabelSet.decorate(indicator).setBackground(MT.COLOR_INDIGO).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_WHITE);
    }

    private void initRightBody() {
        final ScrolledComposite rsc = new ScrolledComposite(right, SWT.V_SCROLL);
        FormDataSet.attach(rsc).atLeft().atTopTo(indicator).atRight().atBottom();
        rsc.setExpandHorizontal(true);
        rsc.setExpandVertical(true);
        rsc.getVerticalBar().addSelectionListener(new TextScrollBarSelectionAdapter());

        final Composite c = new Composite(rsc, SWT.NONE);
        FormLayoutSet.layout(c).marginWidth(20).marginTop(20).marginBottom(0).spacing(20);

        final StyledText headingTextWidget = new StyledText(c, SWT.SINGLE);
        FormDataSet.attach(headingTextWidget).atLeft().atTop().atRight();
        StyledTextSet.decorate(headingTextWidget).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setText(vo.getStyledTextContent("heading"));

        final StyledText passageTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(headingTextWidget).atRight();
        StyledTextSet.decorate(passageTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledTextContent("passage"));
        StyleRangeUtils.decorate(passageTextWidget, vo.getStyledTextStyles("passage"));

        rsc.setContent(c);
        rsc.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                int wh = rsc.getBounds().width;
                int hh = passageTextWidget.getBounds().y + passageTextWidget.getBounds().height + 100;
                rsc.setMinSize(c.computeSize(wh, hh));
            }
        });
    }

    @Override
    public void updateLeft() {
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class ContinueOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            if (goneThrough) {
                release();
                PersistenceUtils.saveToNextView(ReadingPassageView.this);
                page.resume();
            } else {
                new MoreTextWindow().openAndWaitForDisposal();
            }
        }
    }

    private class BackOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            PersistenceUtils.saveToPreviousView(ReadingPassageView.this);
            page.resume();
        }
    }

    private class TextScrollBarSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            ScrollBar bar = (ScrollBar) e.widget;
            int selection = bar.getSelection(), maximum = bar.getMaximum(), increment = bar.getIncrement(), thumb = bar.getThumb();
            int reserved = increment * 2;
            if (selection <= 0) {
                indicator.setText("");
            } else if (selection > 0 && selection <= reserved) {
                indicator.setText(msgs.getString("beginning"));
            } else /* if (selection >= maximum - thumb - reserved) */ {
                indicator.setText(msgs.getString("more_available"));
                goneThrough = true;
            } /* else {
                indicator.setText(msgs.getString("more_available"));
            } */
        }
    }
}
