package com.mocktpo.modules.test.views;

import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.modules.test.TestPage;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.ScreenUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.StyledTextSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class ReadingSectionEndView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 200;
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
    protected void updateHeader() {
        final ImageButton continueButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER);
        FormDataSet.attach(continueButton).atRight(10).atTop(10);
        continueButton.addMouseListener(new ContinueButtonMouseAdapter());

        final ImageButton reviewButton = new ImageButton(header, SWT.NONE, MT.IMAGE_REVIEW, MT.IMAGE_REVIEW_HOVER);
        FormDataSet.attach(reviewButton).atRightTo(continueButton, 10).atTop(10);
        reviewButton.addMouseListener(new ReviewButtonMouseAdapter());

        final ImageButton returnButton = new ImageButton(header, SWT.NONE, MT.IMAGE_RETURN, MT.IMAGE_RETURN_HOVER);
        FormDataSet.attach(returnButton).atRightTo(reviewButton, 10).atTop(10);
        returnButton.addMouseListener(new ReturnButtonMouseAdapter());
    }

    @Override
    protected void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        GridLayoutSet.layout(viewPort).marginTop(VIEW_PORT_PADDING_TOP);

        final StyledText descriptionTextWidget = new StyledText(viewPort, SWT.WRAP);
        GridDataSet.attach(descriptionTextWidget).topCenter();
        StyledTextSet.decorate(descriptionTextWidget).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setLineSpacing(5).setText(vo.getStyledTextContent("description"));
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class ContinueButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            PersistenceUtils.saveToNextView(ReadingSectionEndView.this);
            page.resume();
        }
    }

    private class ReviewButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            page.toReadingReview();
        }
    }

    private class ReturnButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            PersistenceUtils.saveToPreviousView(ReadingSectionEndView.this);
            page.resume();
        }
    }
}
