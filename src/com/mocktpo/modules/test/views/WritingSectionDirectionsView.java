package com.mocktpo.modules.test.views;

import com.mocktpo.events.StyledTextPaintImageListener;
import com.mocktpo.modules.test.TestPage;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.widgets.StyleRangeUtils;
import com.mocktpo.util.widgets.StyledTextSet;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.widgets.ImageButton;
import com.mocktpo.widgets.VolumeControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Scale;

public class WritingSectionDirectionsView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 50;

    /* Widgets */

    private VolumeControl volumeControl;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public WritingSectionDirectionsView(TestPage page, int style) {
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
        final ImageButton nextOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_NEXT_OVAL, MT.IMAGE_NEXT_OVAL_HOVER, MT.IMAGE_NEXT_OVAL_DISABLED);
        FormDataSet.attach(nextOvalButton).atRight(10).atTop(10);
        nextOvalButton.setEnabled(false);

        final ImageButton helpOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(helpOvalButton).atRightTo(nextOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        helpOvalButton.setEnabled(false);

        final ImageButton volumeOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(volumeOvalButton).atRightTo(helpOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        volumeOvalButton.addMouseListener(new VolumeOvalButtonMouseAdapter());

        final ImageButton continueButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER);
        FormDataSet.attach(continueButton).atRightTo(volumeOvalButton, 16).atTopTo(nextOvalButton, 8, SWT.TOP);
        continueButton.addMouseListener(new ContinueButtonMouseAdapter());

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(volumeOvalButton, 0, SWT.BOTTOM).atRightTo(volumeOvalButton, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTestSession().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionAdapter());
    }

    @Override
    public void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);

        final StyledText headingTextWidget = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(headingTextWidget).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(headingTextWidget).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SERIF_HEADING).setForeground(MT.COLOR_DARK_BLUE).setText(vo.getStyledText("heading").getText());

        final StyledText descriptionTextWidget = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(descriptionTextWidget).atLeft().atTopTo(headingTextWidget, 50).atRight();
        StyledTextSet.decorate(descriptionTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("description").getText());
        StyleRangeUtils.decorate(descriptionTextWidget, vo.getStyledText("description").getStyles());
        descriptionTextWidget.addPaintObjectListener(new StyledTextPaintImageListener());
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class VolumeOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            volumeControlVisible = !volumeControlVisible;
            CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
            PersistenceUtils.saveVolumeControlVisibility(WritingSectionDirectionsView.this);
        }
    }

    private class VolumeControlSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            Scale s = (Scale) e.widget;
            double selection = s.getSelection(), maximum = s.getMaximum();
            double volume = selection / maximum;
            PersistenceUtils.saveVolume(WritingSectionDirectionsView.this, volume);
            setAudioVolume(volume);
        }
    }

    private class ContinueButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            PersistenceUtils.saveToNextView(WritingSectionDirectionsView.this);
            page.resume();
        }
    }
}
