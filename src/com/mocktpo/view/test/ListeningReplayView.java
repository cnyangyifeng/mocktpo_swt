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
import org.eclipse.swt.custom.StyledText;
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
    private static final int AUDIO_PROGRESS_INDICATOR_WIDTH = 360;
    private static final int AUDIO_PROGRESS_INDICATOR_HEIGHT = 26;

    /* Widgets */

    private VolumeControl vc;
    private Label il;
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
        vc.setSelection(((Double) (page.getUserTest().getVolume() * 10)).intValue());
        vc.addSelectionListener(new VolumeControlSelectionListener());


        /*
         * ==================================================
         *
         * Caption
         *
         * ==================================================
         */

        final StyledText caption = new StyledText(header, SWT.SINGLE);
        FormDataSet.attach(caption).fromLeft(50, -LC.CAPTION_WIDTH / 2).atBottomTo(pauseTestButton, 0, SWT.BOTTOM).withWidth(LC.CAPTION_WIDTH);
        StyledTextSet.decorate(caption).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setText(MT.STRING_QUESTION + MT.STRING_SPACE + vo.getQuestionNumberInSection() + MT.STRING_SPACE + MT.STRING_OF + MT.STRING_SPACE + TestSchemaUtils.getTotalQuestionCountInSectionAndGroup(page.getTestSchema(), vo.getSectionType(), vo.getGroupId()));

        // TODO Removes the continue button

        final ImageButton cb = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER);
        FormDataSet.attach(cb).atRightTo(vob, 16).atTopTo(nob, 8, SWT.TOP);
        cb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent mouseEvent) {

                release();

                UserTest ut = page.getUserTest();
                ut.setCompletionRate(100 * vo.getViewId() / page.getTestSchema().getViews().size());
                ut.setLastViewId(vo.getViewId() + 1);

                sqlSession.getMapper(UserTestMapper.class).update(ut);
                sqlSession.commit();

                page.resume(ut);
            }
        });
    }

    @Override
    public void updateBody() {

        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE);

        illustrations = IllustrationUtils.load(d, page.getUserTest(), vo.getIllustrations());

        il = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(il).fromLeft(50, -ILLUSTRATION_WIDTH / 2).atTop(VIEW_PORT_PADDING_TOP);
        LabelSet.decorate(il).setImage(illustrations.get(0));

        final Composite pc = new Composite(viewPort, SWT.NONE);
        FormDataSet.attach(pc).fromLeft(50, -AUDIO_PROGRESS_INDICATOR_WIDTH / 2).atTopTo(il, 30).withWidth(AUDIO_PROGRESS_INDICATOR_WIDTH).withHeight(AUDIO_PROGRESS_INDICATOR_HEIGHT);
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
            CompositeSet.decorate(vc).setVisible(volumeControlVisible);

            UserTest ut = page.getUserTest();
            ut.setVolumeControlHidden(!volumeControlVisible);

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();
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

            if (null != audioPlayer) {

                double selection = s.getSelection(), maximum = s.getMaximum();
                double volume = selection / maximum;

                UserTest ut = page.getUserTest();
                ut.setVolume(volume);

                sqlSession.getMapper(UserTestMapper.class).update(ut);
                sqlSession.commit();

                audioPlayer.setVolume(volume);
            }
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
                                LabelSet.decorate(il).setImage(illustrations.get(rl.get()));
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

                release();

                if (!d.isDisposed()) {
                    d.asyncExec(new Runnable() {
                        @Override
                        public void run() {

                            UserTest ut = page.getUserTest();
                            ut.setCompletionRate(100 * vo.getViewId() / page.getTestSchema().getViews().size());
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
