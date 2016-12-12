package com.mocktpo.view.test;

import com.mocktpo.dialog.RequiredAnswerDialog;
import com.mocktpo.listener.StyledTextPaintImageListener;
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
import org.eclipse.swt.widgets.Scale;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;

public class ListeningMatchObjectsQuestionView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 150;
    private static final int VIEW_PORT_PADDING_WIDTH = 200;

    private static final int VIEW_STATUS_INITIAL = 0;
    private static final int VIEW_STATUS_NEXT_BUTTON_ENABLED = 1;
    private static final int VIEW_STATUS_OK_BUTTON_ENABLED = 2;

    /* Widgets */

    private ImageButton nob, oob;
    private VolumeControl vc;

    private StyledText tips;

    /* Properties */

    private boolean volumeControlVisible;
    private int viewStatus;
    private int answer1, answer2, answer3, answer4, answer5;

    private PropertyChangeListener listener;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ListeningMatchObjectsQuestionView(TestPage page, int style) {
        super(page, style);
        this.viewStatus = VIEW_STATUS_INITIAL;
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

        nob = new ImageButton(header, SWT.NONE, MT.IMAGE_NEXT_OVAL, MT.IMAGE_NEXT_OVAL_HOVER, MT.IMAGE_NEXT_OVAL_DISABLED);
        FormDataSet.attach(nob).atRight(10).atTop(10);
        nob.setEnabled(false);
        nob.addMouseListener(new NextOvalButtonMouseListener());

        oob = new ImageButton(header, SWT.NONE, MT.IMAGE_OK_OVAL, MT.IMAGE_OK_OVAL_HOVER, MT.IMAGE_OK_OVAL_DISABLED);
        FormDataSet.attach(oob).atRightTo(nob).atTopTo(nob, 0, SWT.TOP);
        oob.setEnabled(false);
        oob.addMouseListener(new OkOvalButtonMouseListener());

        final ImageButton hob = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(hob).atRightTo(oob).atTopTo(nob, 0, SWT.TOP);
        hob.addMouseListener(new HelpOvalButtonMouseListener());

        final ImageButton vob = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(vob).atRightTo(hob).atTopTo(nob, 0, SWT.TOP);
        vob.addMouseListener(new VolumeOvalButtonMouseListener());

        vc = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(vc).atTopTo(vob, 0, SWT.BOTTOM).atRightTo(vob, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(vc).setVisible(volumeControlVisible);
        vc.setSelection(((Double) (page.getUserTest().getVolume() * 10)).intValue());
        vc.addSelectionListener(new VolumeControlSelectionListener());

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

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        FormLayoutSet.layout(viewPort);

        final StyledText dt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(dt).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(dt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("directions").getText());
        StyleRangeUtils.decorate(dt, vo.getStyledText("directions").getStyles());
        dt.addPaintObjectListener(new StyledTextPaintImageListener());

        tips = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(tips).atLeft().atTopTo(dt, 20).atRight();
        StyledTextSet.decorate(tips).setAlignment(SWT.CENTER).setBackground(MT.COLOR_HIGHLIGHTED).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(5).setText(vo.getStyledText("tips").getText()).setVisible(false);
        StyleRangeUtils.decorate(tips, vo.getStyledText("tips").getStyles());
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

        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    timerButton.setEnabled(false);
                }
            });
        }

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
     * Answer Status
     *
     * ==================================================
     */

    public boolean isOk() {
        return 0 != answer1 && 0 != answer2;
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class NextOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            nob.setEnabled(false);
            oob.setEnabled(true);
            viewStatus = VIEW_STATUS_OK_BUTTON_ENABLED;
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class OkOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            if (isOk()) {

                release();

                UserTest ut = page.getUserTest();
                ut.setCompletionRate(100 * vo.getViewId() / page.getTestSchema().getViews().size());
                ut.setLastViewId(vo.getViewId() + 1);

                sqlSession.getMapper(UserTestMapper.class).update(ut);
                sqlSession.commit();

                page.resume(ut);

            } else {

                nob.setEnabled(true);
                oob.setEnabled(false);
                viewStatus = VIEW_STATUS_NEXT_BUTTON_ENABLED;

                new RequiredAnswerDialog().openAndWaitForDisposal();
            }
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class HelpOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

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

    private class ChooseAnswerListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            int answer = (Integer) e.widget.getData(MT.KEY_CHOICE);

            if (VIEW_STATUS_INITIAL == viewStatus) {
                nob.setEnabled(true);
                oob.setEnabled(false);
                viewStatus = VIEW_STATUS_NEXT_BUTTON_ENABLED;
            }
        }

        @Override
        public void mouseUp(MouseEvent e) {
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
                            StyledTextSet.decorate(tips).setVisible(true);
                        }
                    });
                }

                /*
                 * ==================================================
                 *
                 * Schedules a new Timer and a Timer Task here if the
                 * view is marked as timerTaskDelayed, rather than
                 * TestView.startTimer().
                 *
                 * ==================================================
                 */
                if (vo.isTimerTaskDelayed()) {

                    if (!d.isDisposed()) {
                        d.asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                timerButton.setEnabled(true);
                            }
                        });
                    }

                    countDown = page.getUserTest().getRemainingViewTime(vo.getSectionType(), vo.getGroupId());
                    timer = new Timer();
                    timerTask = new TestTimerTask();
                    timer.scheduleAtFixedRate(timerTask, 0, 1000);
                }
            }
        }
    }
}
