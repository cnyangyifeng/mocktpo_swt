package com.mocktpo.view.test;

import com.mocktpo.listener.BorderedCompositePaintListener;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.UserTestPersistenceUtils;
import com.mocktpo.widget.ImageButton;
import com.mocktpo.widget.VolumeControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ListeningReplayView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 50;
    private static final int ILLUSTRATION_WIDTH = 600;
    private static final int AUDIO_BAR_CONTAINER_WIDTH = 360;
    private static final int AUDIO_BAR_CONTAINER_HEIGHT = 26;

    /* Widgets */

    private VolumeControl volumeControl;
    private Label illustrationLabel;
    private ProgressBar audioBar;

    /* Properties */

    private Map<Integer, Image> illustrations;

    private PropertyChangeListener listener;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ListeningReplayView(TestPage page, int style) {
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

        final ImageButton okOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_OK_OVAL, MT.IMAGE_OK_OVAL_HOVER, MT.IMAGE_OK_OVAL_DISABLED);
        FormDataSet.attach(okOvalButton).atRightTo(nextOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        okOvalButton.setEnabled(false);

        final ImageButton helpOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(helpOvalButton).atRightTo(okOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        helpOvalButton.setEnabled(false);

        final ImageButton volumeOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(volumeOvalButton).atRightTo(helpOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        volumeOvalButton.addMouseListener(new VolumeOvalButtonMouseListener());

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(volumeOvalButton, 0, SWT.BOTTOM).atRightTo(volumeOvalButton, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTest().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionListener());

        // TODO Removes the continue debug button

        final ImageButton continueDebugButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE_DEBUG, MT.IMAGE_CONTINUE_DEBUG_HOVER);
        FormDataSet.attach(continueDebugButton).atRightTo(volumeOvalButton, 16).atTopTo(nextOvalButton, 8, SWT.TOP);
        continueDebugButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent mouseEvent) {
                release();
                UserTestPersistenceUtils.saveToNextView(ListeningReplayView.this);
                page.resume();
            }
        });
    }

    @Override
    public void updateBody() {

        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE);

        illustrations = IllustrationUtils.load(d, page.getUserTest(), vo.getIllustrations());

        illustrationLabel = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(illustrationLabel).fromLeft(50, -ILLUSTRATION_WIDTH / 2).atTop(VIEW_PORT_PADDING_TOP);
        LabelSet.decorate(illustrationLabel).setImage(illustrations.get(0));

        final Composite pc = new Composite(viewPort, SWT.NONE);
        FormDataSet.attach(pc).fromLeft(50, -AUDIO_BAR_CONTAINER_WIDTH / 2).atTopTo(illustrationLabel, 30).withWidth(AUDIO_BAR_CONTAINER_WIDTH).withHeight(AUDIO_BAR_CONTAINER_HEIGHT);
        CompositeSet.decorate(pc).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        FormLayoutSet.layout(pc).marginWidth(10).marginHeight(8);
        pc.addPaintListener(new BorderedCompositePaintListener());

        audioBar = new ProgressBar(pc, SWT.NONE);
        FormDataSet.attach(audioBar).atLeft().atTop().atRight().atBottom();
        ProgressBarSet.decorate(audioBar).setMaximum(100).setMinimum(0).setSelection(0);
    }

    /*
     * ==================================================
     *
     * Audio Visualization
     *
     * ==================================================
     */

    @Override
    public void startAudioVisualization() {
        listener = new AudioVisualizationListener();
        audioPlayer.addPropertyChangeListener(listener);
    }

    @Override
    public void stopAudioVisualization() {
        audioPlayer.removePropertyChangeListener(listener);
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
            UserTestPersistenceUtils.saveVolumeControlVisibility(ListeningReplayView.this);
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

            UserTestPersistenceUtils.saveVolume(ListeningReplayView.this, volume);
            setAudioVolume(volume);
        }
    }

    private class AudioVisualizationListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent e) {

            long timeElapsed = (Long) e.getNewValue();

            final AtomicReference<Integer> rl = new AtomicReference<Integer>();
            for (Integer location : illustrations.keySet()) {
                if (timeElapsed / 1000 == location) {
                    rl.set(location);
                    if (!d.isDisposed()) {
                        d.asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                LabelSet.decorate(illustrationLabel).setImage(illustrations.get(rl.get()));
                            }
                        });
                    }
                }
            }

            final AtomicReference<Long> rv = new AtomicReference<Long>();
            long duration = vo.getAudioDuration() * 1000;
            long val = 100 * timeElapsed / duration;
            rv.set(val);
            if (!d.isDisposed()) {
                d.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        audioBar.setSelection(rv.get().intValue());
                    }
                });
            }

            if (audioPlayer.isStopped()) {

                if (!d.isDisposed()) {
                    d.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            audioBar.setSelection(100);
                        }
                    });
                }

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                release();

                if (!d.isDisposed()) {
                    d.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            UserTestPersistenceUtils.saveToNextView(ListeningReplayView.this);
                            page.resume();
                        }
                    });
                }
            }
        }
    }
}
