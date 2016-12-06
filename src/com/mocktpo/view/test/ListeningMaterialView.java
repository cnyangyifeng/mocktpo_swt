package com.mocktpo.view.test;

import com.mocktpo.listener.BorderedCompositePaintListener;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.ImageButton;
import com.mocktpo.widget.VolumeControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicReference;

public class ListeningMaterialView extends ResponsiveTestView {

    /* Constants */

    private static final int ILLUSTRATIONS_MARGIN_TOP = 40;
    private static final int IMAGE_WIDTH = 600;
    private static final int AUDIO_PROGRESS_INDICATOR_WIDTH = 360;
    private static final int AUDIO_PROGRESS_INDICATOR_HEIGHT = 20;

    /* Widgets */

    private VolumeControl vc;

    /* Properties */

    private boolean volumeControlVisible;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ListeningMaterialView(TestPage page, int style) {
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

        final ImageButton nob = new ImageButton(header, SWT.NONE, MT.IMAGE_NEXT_OVAL, MT.IMAGE_NEXT_OVAL_HOVER, MT.IMAGE_NEXT_OVAL_DISABLED);
        FormDataSet.attach(nob).atRight(10).atTop(10);
        nob.setEnabled(false);

        final ImageButton oob = new ImageButton(header, SWT.NONE, MT.IMAGE_OK_OVAL, MT.IMAGE_OK_OVAL_HOVER, MT.IMAGE_OK_OVAL_DISABLED);
        FormDataSet.attach(oob).atRightTo(nob).atTopTo(nob, 0, SWT.TOP);
        oob.setEnabled(false);

        final ImageButton hob = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(hob).atRightTo(oob).atTopTo(nob, 0, SWT.TOP);
        hob.setEnabled(false);

        final ImageButton vob = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(vob).atRightTo(hob).atTopTo(nob, 0, SWT.TOP);
        vob.addMouseListener(new VolumeOvalButtonMouseListener());

        vc = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(vc).atTopTo(vob, 0, SWT.BOTTOM).atRightTo(vob, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(vc).setVisible(volumeControlVisible);
        vc.addSelectionListener(new VolumeControlSelectionListener());
    }

    @Override
    public void updateBody() {

        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE);


        /*
         * ==================================================
         *
         * Audio Visualization
         *
         * ==================================================
         */

        if (vo.isAudioVisualized()) {

            illustrations = IllustrationUtils.load(d, page.getUserTest(), vo.getIllustrations());

            final Label il = new Label(viewPort, SWT.NONE);
            FormDataSet.attach(il).fromLeft(50, -IMAGE_WIDTH / 2).atTop(ILLUSTRATIONS_MARGIN_TOP);
            LabelSet.decorate(il).setImage(illustrations.get(0));
            il.addPaintListener(new BorderedCompositePaintListener());

            final Composite pc = new Composite(viewPort, SWT.NONE);
            FormDataSet.attach(pc).fromLeft(50, -AUDIO_PROGRESS_INDICATOR_WIDTH / 2).atTopTo(il, 30).withWidth(AUDIO_PROGRESS_INDICATOR_WIDTH).withHeight(AUDIO_PROGRESS_INDICATOR_HEIGHT);
            CompositeSet.decorate(pc).setBackground(MT.COLOR_WINDOW_BACKGROUND);
            FormLayoutSet.layout(pc).marginWidth(5).marginHeight(2);
            pc.addPaintListener(new BorderedCompositePaintListener());

            audioBar = new ProgressBar(pc, SWT.NONE);
            FormDataSet.attach(audioBar).atLeft().atTop().atRight().atBottom();
            ProgressBarSet.decorate(audioBar).setMaximum(100).setMinimum(0).setSelection(0);
        }
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
        if (vo.isAudioVisualized()) {
            audioPlayer.addPropertyChangeListener(new AudioVisualizationListener());
        }
    }

    @Override
    public void stopAudioVisualization() {
        if (vo.isAudioVisualized() && null != illustrations) {
        }
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
            CompositeSet.decorate(vc).setVisible(volumeControlVisible);
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
            setAudioVolume(selection / maximum);
        }
    }

    private class AudioVisualizationListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent e) {

            long duration = vo.getAudioDuration() * 1000;
            long timeElapsed = (Long) e.getNewValue();

            final AtomicReference<Long> ref = new AtomicReference<Long>();
            long val = 100 * timeElapsed / duration;
            ref.set(val);
            if (!d.isDisposed()) {
                d.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        audioBar.setSelection(ref.get().intValue());
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

                release();

                if (!d.isDisposed()) {
                    d.asyncExec(new Runnable() {
                        @Override
                        public void run() {

                            UserTest ut = page.getUserTest();
                            ut.setLastViewId(vo.getViewId() + 1);

                            sqlSession.getMapper(UserTestMapper.class).update(ut);
                            sqlSession.commit();

                            page.resume(ut);
                        }
                    });
                }
            }
        }
    }
}
