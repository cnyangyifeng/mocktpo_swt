package com.mocktpo.modules.test.views;

import com.mocktpo.modules.test.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.*;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.modules.test.widgets.VolumeControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SpeakingTaskDirectionsView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 100;
    private static final int VIEW_PORT_PADDING_WIDTH = 100;

    /* Widgets */

    private VolumeControl volumeControl;

    /* Properties */

    private PropertyChangeListener listener;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public SpeakingTaskDirectionsView(TestPage page, int style) {
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
        final ImageButton volumeOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(volumeOvalButton).atRight(10).atTop(10);
        volumeOvalButton.addMouseListener(new VolumeOvalButtonMouseAdapter());

        final ImageButton continueButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER, MT.IMAGE_CONTINUE_DISABLED);
        FormDataSet.attach(continueButton).atRightTo(volumeOvalButton, 16).atTopTo(volumeOvalButton, 8, SWT.TOP);
        continueButton.setEnabled(false);

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(volumeOvalButton, 0, SWT.BOTTOM).atRightTo(volumeOvalButton, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTestSession().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionAdapter());

        final ImageButton skipButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SKIP, MT.IMAGE_SKIP_HOVER);
        FormDataSet.attach(skipButton).atRightTo(continueButton, 10).atTopTo(continueButton, 0, SWT.TOP);
        skipButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent mouseEvent) {
                release();
                PersistenceUtils.saveToNextView(SpeakingTaskDirectionsView.this);
                page.resume();
            }
        });
    }

    @Override
    protected void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        GridLayoutSet.layout(viewPort).marginTop(VIEW_PORT_PADDING_TOP).verticalSpacing(20);

        final StyledText topTextWidget = new StyledText(viewPort, SWT.WRAP);
        GridDataSet.attach(topTextWidget).topCenter();
        StyledTextSet.decorate(topTextWidget).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledTextContent("top"));

        final Label imageLabel = new Label(viewPort, SWT.NONE);
        GridDataSet.attach(imageLabel).topCenter();
        LabelSet.decorate(imageLabel).setImage(MT.IMAGE_HEADSET);
    }

    /*
     * ==================================================
     *
     * Audio Async Execution
     *
     * ==================================================
     */

    @Override
    public void startAudioAsyncExecution() {
        listener = new AudioAsyncExecutionListener();
        audioPlayer.addPropertyChangeListener(listener);
    }

    @Override
    public void stopAudioAsyncExecution() {
        audioPlayer.removePropertyChangeListener(listener);
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
            PersistenceUtils.saveVolumeControlVisibility(SpeakingTaskDirectionsView.this);
        }
    }

    private class VolumeControlSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            Scale s = (Scale) e.widget;
            double selection = s.getSelection(), maximum = s.getMaximum();
            double volume = selection / maximum;
            PersistenceUtils.saveVolume(SpeakingTaskDirectionsView.this, volume);
            setAudioVolume(volume);
        }
    }

    private class AudioAsyncExecutionListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent e) {
            if (audioPlayer.isStopped()) {
                if (!d.isDisposed()) {
                    d.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            release();
                            PersistenceUtils.saveToNextView(SpeakingTaskDirectionsView.this);
                            page.resume();
                        }
                    });
                }
            }
        }
    }
}
