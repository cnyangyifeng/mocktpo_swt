package com.mocktpo.view.test;

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
    public void updateHeader() {

        final ImageButton vob = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(vob).atRight(10).atTop(10);
        vob.addMouseListener(new VolumeOvalButtonMouseListener());

        final ImageButton cb = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER, MT.IMAGE_CONTINUE_DISABLED);
        FormDataSet.attach(cb).atRightTo(vob, 16).atTopTo(vob, 8, SWT.TOP);
        cb.setEnabled(false);

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(vob, 0, SWT.BOTTOM).atRightTo(vob, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTest().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionListener());

        // TODO Removes the continue button

        final ImageButton cb2 = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER);
        FormDataSet.attach(cb2).atRightTo(cb, 10).atTopTo(cb, 0, SWT.TOP);
        cb2.addMouseListener(new MouseAdapter() {
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

        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        FormLayoutSet.layout(viewPort);

        final StyledText tt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(tt).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(tt).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("top").getText());

        final Label il = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(il).atLeft().atTopTo(tt, 20).atRight();
        LabelSet.decorate(il).setImage(MT.IMAGE_HEADSET);
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

    private class VolumeOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            volumeControlVisible = !volumeControlVisible;
            CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);

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

    private class AudioAsyncExecutionListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent e) {

            if (audioPlayer.isStopped()) {
                if (!d.isDisposed()) {
                    d.asyncExec(new Runnable() {
                        @Override
                        public void run() {

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
            }
        }
    }
}
