package com.mocktpo.view.test;

import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.UserTestPersistenceUtils;
import com.mocktpo.widget.ImageButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

public class ReadingSectionEndView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 150;
    private static final int VIEW_PORT_PADDING_WIDTH = 240;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingSectionEndView(TestPage page, int style) {
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

        final ImageButton cb = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER);
        FormDataSet.attach(cb).atRight(10).atTop(10);
        cb.addMouseListener(new ContinueButtonMouseListener());

        final ImageButton rvb = new ImageButton(header, SWT.NONE, MT.IMAGE_REVIEW, MT.IMAGE_REVIEW_HOVER);
        FormDataSet.attach(rvb).atRightTo(cb, 10).atTop(10);
        rvb.addMouseListener(new ReviewButtonMouseListener());

        final ImageButton rtb = new ImageButton(header, SWT.NONE, MT.IMAGE_RETURN, MT.IMAGE_RETURN_HOVER);
        FormDataSet.attach(rtb).atRightTo(rvb, 10).atTop(10);
        rtb.addMouseListener(new ReturnButtonMouseListener());
    }

    @Override
    public void updateBody() {

        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        FormLayoutSet.layout(viewPort);

        final StyledText dt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(dt).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(dt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setLineSpacing(5).setText(vo.getStyledText("description").getText());
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class ContinueButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            UserTestPersistenceUtils.saveToNextView(ReadingSectionEndView.this);
            page.resume();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class ReviewButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            page.toReadingReview();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class ReturnButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            UserTestPersistenceUtils.saveToPreviousView(ReadingSectionEndView.this);
            page.resume();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
