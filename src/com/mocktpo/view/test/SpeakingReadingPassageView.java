package com.mocktpo.view.test;

import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.UserTestPersistenceUtils;
import com.mocktpo.widget.ImageButton;
import com.mocktpo.widget.VolumeControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.Scale;

public class SpeakingReadingPassageView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 100;

    /* Widgets */

    private VolumeControl volumeControl;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public SpeakingReadingPassageView(TestPage page, int style) {
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

        updateTime();

        final ImageButton vob = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(vob).atRight(10).atTop(10);
        vob.addMouseListener(new VolumeOvalButtonMouseListener());

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(vob, 0, SWT.BOTTOM).atRightTo(vob, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTestSession().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionListener());

        // TODO Removes the continue debug button

        final ImageButton continueDebugButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE_DEBUG, MT.IMAGE_CONTINUE_DEBUG_HOVER);
        FormDataSet.attach(continueDebugButton).atRightTo(vob, 16).atTopTo(vob, 8, SWT.TOP);
        continueDebugButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent mouseEvent) {
                release();
                UserTestPersistenceUtils.saveToNextView(SpeakingReadingPassageView.this);
                page.resume();
            }
        });
    }

    private void updateTime() {
        UserTestSession ut = page.getUserTestSession();
        switch (vo.getGroupId()) {
            case 1:
                ut.setSpeakingReadingTime1(vo.getSpeakingReadingTime());
                break;
            case 2:
                ut.setSpeakingReadingTime2(vo.getSpeakingReadingTime());
                break;
        }
    }

    @Override
    public void updateBody() {

        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);

        StyledText rt = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(rt).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(rt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_ITALIC).setText("Reading Time: " + vo.getSpeakingReadingTime() + " seconds");

        final StyledText ht = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(ht).atLeft().atTopTo(rt, 20).atRight();
        StyledTextSet.decorate(ht).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setText(vo.getStyledText("heading").getText());

        final StyledText pt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(pt).atLeft().atTopTo(ht, 20).atRight().atBottom().withWidth(ScreenUtils.getHalfClientWidth(d));
        StyledTextSet.decorate(pt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("passage").getText());
        StyleRangeUtils.decorate(pt, vo.getStyledText("passage").getStyles());
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class VolumeOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            volumeControlVisible = !volumeControlVisible;
            CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
            UserTestPersistenceUtils.saveVolumeControlVisibility(SpeakingReadingPassageView.this);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class VolumeControlSelectionListener implements SelectionListener {

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }

        @Override
        public void widgetSelected(SelectionEvent e) {

            Scale s = (Scale) e.widget;
            double selection = s.getSelection(), maximum = s.getMaximum();
            double volume = selection / maximum;

            UserTestPersistenceUtils.saveVolume(SpeakingReadingPassageView.this, volume);
            setAudioVolume(volume);
        }
    }
}
