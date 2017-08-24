package com.mocktpo.modules.test.views;

import com.mocktpo.modules.test.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.widgets.*;
import com.mocktpo.modules.system.widgets.ImageButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class IndependentWritingTaskEndView extends ResponsiveTestView {

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

    public IndependentWritingTaskEndView(TestPage page, int style) {
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

        final ImageButton returnButton = new ImageButton(header, SWT.NONE, MT.IMAGE_RETURN, MT.IMAGE_RETURN_HOVER);
        FormDataSet.attach(returnButton).atRightTo(continueButton, 10).atTop(10);
        returnButton.addMouseListener(new ReturnButtonMouseAdapter());
    }

    @Override
    protected void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        FormLayoutSet.layout(viewPort).marginWidth(0).marginHeight(0).spacing(0);

        final StyledText descriptionTextWidget = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(descriptionTextWidget).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(descriptionTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setLineSpacing(5).setText(vo.getStyledTextContent("description"));
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
            PersistenceUtils.saveToNextView(IndependentWritingTaskEndView.this);
            page.resume();
        }
    }

    private class ReturnButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            PersistenceUtils.saveToPreviousView(IndependentWritingTaskEndView.this);
            page.resume();
        }
    }
}
