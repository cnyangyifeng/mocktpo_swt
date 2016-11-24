package com.mocktpo.view.test;

import com.mocktpo.dialog.MoreTextDialog;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.ImageButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;

public class ReadingTextView extends SashTestView {

    /* Widgets */

    private CLabel indicator;

    /* Properties */

    protected boolean goneThrough;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingTextView(TestPage page, int style) {
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
        final ImageButton cob = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_CONTINUE_OVAL), ResourceManager.getImage(MT.IMAGE_CONTINUE_OVAL_HOVER), ResourceManager.getImage(MT.IMAGE_CONTINUE_OVAL_DISABLED));
        FormDataSet.attach(cob).atRight(10).atTop(10);
        cob.addMouseListener(new ContinueOvalButtonMouseListener());
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

        final ScrolledComposite sc = new ScrolledComposite(right, SWT.H_SCROLL | SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(indicator).atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);
        sc.getVerticalBar().addSelectionListener(new TextScrollBarSelectionListener());

        final Composite c = new Composite(sc, SWT.NONE);
        FormLayoutSet.layout(c).marginWidth(10).marginTop(20).marginBottom(100).spacing(20);

        final StyledText ht = new StyledText(c, SWT.SINGLE);
        FormDataSet.attach(ht).atLeft().atTop().atRight();
        StyledTextSet.decorate(ht).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setText(vo.getStyledText("heading").getText());

        final StyledText pt = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(pt).atLeft().atTopTo(ht).atBottom().withWidth(ScreenUtils.getHalfClientWidth(d));
        StyledTextSet.decorate(pt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("passage").getText());
        StyleRangeUtils.decorate(pt, vo.getStyledText("passage").getStyles());

        sc.setContent(c);
        sc.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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

    private class ContinueOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            if (goneThrough) {

                if (vo.isTimed()) {
                    stopTimer();
                }

                UserTest ut = page.getUserTest();
                ut.setLastViewId(vo.getViewId() + 1);

                sqlSession.getMapper(UserTestMapper.class).update(ut);
                sqlSession.commit();

                page.resume(ut);

            } else {
                new MoreTextDialog().openAndWaitForDisposal();
            }

        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class TextScrollBarSelectionListener implements SelectionListener {

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }

        @Override
        public void widgetSelected(SelectionEvent e) {

            ScrollBar bar = (ScrollBar) e.widget;
            int selection = bar.getSelection(), maximum = bar.getMaximum(), increment = bar.getIncrement(), thumb = bar.getThumb();
            int reserved = increment * 2;

            if (selection <= 0) {
                indicator.setText("");
            } else if (selection > 0 && selection <= reserved) {
                indicator.setText(msgs.getString("beginning"));
            } else if (selection >= maximum - thumb - reserved) {
                indicator.setText(msgs.getString("more_available"));
                goneThrough = true;
            } else {
                indicator.setText(msgs.getString("more_available"));
            }
        }
    }
}
