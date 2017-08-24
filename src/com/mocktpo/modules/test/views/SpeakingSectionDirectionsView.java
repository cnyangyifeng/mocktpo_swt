package com.mocktpo.modules.test.views;

import com.mocktpo.modules.test.TestPage;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.widgets.StyleRangeUtils;
import com.mocktpo.util.widgets.StyledTextSet;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.modules.system.widgets.ImageButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class SpeakingSectionDirectionsView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 50;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public SpeakingSectionDirectionsView(TestPage page, int style) {
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

    }

    @Override
    protected void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);

        final StyledText headingTextWidget = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(headingTextWidget).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(headingTextWidget).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SERIF_HEADING).setForeground(MT.COLOR_DARK_BLUE).setText(vo.getStyledTextContent("heading"));

        final StyledText descriptionTextWidget = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(descriptionTextWidget).atLeft().atTopTo(headingTextWidget, 50).atRight();
        StyledTextSet.decorate(descriptionTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledTextContent("description"));
        StyleRangeUtils.decorate(descriptionTextWidget, vo.getStyledTextStyles("description"));
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
            PersistenceUtils.saveToNextView(SpeakingSectionDirectionsView.this);
            page.resume();
        }
    }
}
