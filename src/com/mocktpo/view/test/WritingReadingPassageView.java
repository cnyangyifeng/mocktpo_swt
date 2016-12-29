package com.mocktpo.view.test;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.ImageButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;

public class WritingReadingPassageView extends SashTestView {

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public WritingReadingPassageView(TestPage page, int style) {
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

        final ImageButton cob = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE_OVAL, MT.IMAGE_CONTINUE_OVAL_HOVER, MT.IMAGE_CONTINUE_OVAL_DISABLED);
        FormDataSet.attach(cob).atRight(10).atTop(10);
        cob.addMouseListener(new ContinueOvalButtonMouseListener());
    }

    @Override
    public void updateRight() {
        initIndicator();
        initRightBody();
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

            release();

            UserTest ut = page.getUserTest();
            ut.setCompletionRate(100 * vo.getViewId() / page.getTestSchema().getViews().size());
            ut.setLastViewId(vo.getViewId() + 1);

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();

            page.resume(ut);
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
