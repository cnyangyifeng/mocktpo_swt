package com.mocktpo.views.test;

import com.mocktpo.pages.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.*;
import com.mocktpo.widgets.ImageButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Label;

public class TestIntroView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 100;
    private static final int VIEW_PORT_PADDING_WIDTH = 200;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestIntroView(TestPage page, int style) {
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
        final ImageButton continueButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER);
        FormDataSet.attach(continueButton).atRight(10).atTop(10);
        continueButton.addMouseListener(new ContinueButtonMouseAdapter());
    }

    @Override
    public void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        GridLayoutSet.layout(viewPort).marginTop(VIEW_PORT_PADDING_TOP).verticalSpacing(50);

        final Label imageLabel = new Label(viewPort, SWT.NONE);
        GridDataSet.attach(imageLabel).topCenter();
        LabelSet.decorate(imageLabel).setImage(MT.IMAGE_ETS_TOEFL);

        final StyledText descriptionTextWidget = new StyledText(viewPort, SWT.WRAP);
        GridDataSet.attach(descriptionTextWidget).topCenter();
        StyledTextSet.decorate(descriptionTextWidget).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("description").getText());
        StyleRangeUtils.decorate(descriptionTextWidget, vo.getStyledText("description").getStyles());
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
            PersistenceUtils.saveToNextView(TestIntroView.this);
            page.resume();
        }
    }
}
