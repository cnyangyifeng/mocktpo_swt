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
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scale;

public class AdjustingMicrophoneView extends StackTestView {

    /* Constants */

    private static final int SUB_VIEW_RECORDING = 1;
    private static final int SUB_VIEW_RESPONSE = 2;

    private static final int VIEW_PORT_PADDING_TOP = 50;
    private static final int VIEW_PORT_PADDING_WIDTH = 240;
    private static final int FOOTNOTE_TEXT_WIDTH = 360;
    private static final int RESPONSE_TIME_WIDTH = 180;
    private static final int STOP_RECORDING_BUTTON_WIDTH = 74;

    /* Widgets */

    private ImageButton continueButton, recordAgainButton, playbackResponseButton;
    private VolumeControl volumeControl;

    private Composite recordingView;
    private Composite responseTimeContainer;
    private CLabel responseTimeLabel;
    private ImageButton stopRecordingButton;

    private Composite responseView;

    /* Properties */

    private int subViewId;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public AdjustingMicrophoneView(TestPage page, int style) {
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

        continueButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER, MT.IMAGE_CONTINUE_DISABLED);
        FormDataSet.attach(continueButton).atRightTo(vob, 16).atTopTo(vob, 8, SWT.TOP);
        continueButton.setEnabled(false);
        continueButton.addMouseListener(new ContinueButtonMouseListener());

        recordAgainButton = new ImageButton(header, SWT.NONE, MT.IMAGE_RECORD_AGAIN, MT.IMAGE_RECORD_AGAIN_HOVER, MT.IMAGE_RECORD_AGAIN_DISABLED);
        FormDataSet.attach(recordAgainButton).atRightTo(continueButton, 10).atTopTo(continueButton, 0, SWT.TOP);
        recordAgainButton.setEnabled(false);
        recordAgainButton.addMouseListener(new RecordAgainButtonMouseListener());

        playbackResponseButton = new ImageButton(header, SWT.NONE, MT.IMAGE_PLAYBACK_RESPONSE, MT.IMAGE_PLAYBACK_RESPONSE_HOVER, MT.IMAGE_PLAYBACK_RESPONSE_DISABLED);
        FormDataSet.attach(playbackResponseButton).atRightTo(recordAgainButton, 10).atTopTo(continueButton, 0, SWT.TOP);
        playbackResponseButton.setEnabled(false);
        playbackResponseButton.addMouseListener(new PlaybackResponseButtonMouseListener());

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(vob, 0, SWT.BOTTOM).atRightTo(vob, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTest().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionListener());
    }

    @Override
    public void updateBody() {
        subViewId = SUB_VIEW_RECORDING;
        stack.topControl = getSubView(subViewId);
        body.layout();
    }

    private Composite getSubView(int subViewId) {

        switch (subViewId) {
            case SUB_VIEW_RECORDING:
                if (null == recordingView) {
                    recordingView = initRecordingSubView();
                }
                return recordingView;
            case SUB_VIEW_RESPONSE:
                if (null == responseView) {
                    responseView = initResponseSubView();
                }
                return responseView;
        }

        return null;
    }


    /*
     * ==================================================
     *
     * Sub Views
     *
     * ==================================================
     */

    private Composite initRecordingSubView() {

        final Composite c = new Composite(body, SWT.NONE);
        CompositeSet.decorate(c).setBackground(MT.COLOR_BEIGE);
        FormLayoutSet.layout(c);

        final ScrolledComposite sc = new ScrolledComposite(c, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTop().atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        final Composite inner = new Composite(sc, SWT.NONE);
        GridLayoutSet.layout(inner).marginBottom(50);

        final Composite viewPort = new Composite(inner, SWT.NONE);
        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        FormLayoutSet.layout(viewPort);

        final StyledText ht = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(ht).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(ht).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SERIF_HEADING).setForeground(MT.COLOR_DARK_BLUE).setText(vo.getStyledText("heading").getText());

        final StyledText dt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(dt).atLeft().atTopTo(ht, 30).atRight();
        StyledTextSet.decorate(dt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("description").getText());
        StyleRangeUtils.decorate(dt, vo.getStyledText("description").getStyles());

        final StyledText ft = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(ft).fromLeft(50, -FOOTNOTE_TEXT_WIDTH / 2).atTopTo(dt, 20).withWidth(FOOTNOTE_TEXT_WIDTH);
        StyledTextSet.decorate(ft).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SERIF_ITALIC_TEXT).setForeground(MT.COLOR_DARK_BLUE).setLineSpacing(5).setMarginHeight(20).setText(vo.getStyledText("footnote").getText());
        ft.addPaintListener(new BorderedCompositePaintListener());

        responseTimeContainer = new Composite(viewPort, SWT.NONE);
        FormDataSet.attach(responseTimeContainer).fromLeft(50, -RESPONSE_TIME_WIDTH / 2).atTopTo(ft, 20).withWidth(RESPONSE_TIME_WIDTH);
        CompositeSet.decorate(responseTimeContainer);
        FormLayoutSet.layout(responseTimeContainer).marginWidth(1).marginHeight(1);
        responseTimeContainer.addPaintListener(new BorderedCompositePaintListener());

        final CLabel rth = new CLabel(responseTimeContainer, SWT.CENTER);
        FormDataSet.attach(rth).atLeft().atTop().atRight();
        CLabelSet.decorate(rth).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_WHITE).setMargins(5).setText("RESPONSE TIME");

        responseTimeLabel = new CLabel(responseTimeContainer, SWT.CENTER);
        FormDataSet.attach(responseTimeLabel).atLeft().atTopTo(rth).atRight();
        CLabelSet.decorate(responseTimeLabel).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(5).setText("00:00:15");

        stopRecordingButton = new ImageButton(viewPort, SWT.NONE, MT.IMAGE_STOP_RECORDING, MT.IMAGE_STOP_RECORDING_HOVER);
        FormDataSet.attach(stopRecordingButton).fromLeft(50, -STOP_RECORDING_BUTTON_WIDTH / 2).atTopTo(responseTimeContainer, 20);
        stopRecordingButton.addMouseListener(new StopRecordingButtonMouseListener());

        sc.setContent(inner);
        sc.setMinSize(inner.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        return c;
    }

    private Composite initResponseSubView() {

        final Composite c = new Composite(body, SWT.NONE);
        CompositeSet.decorate(c).setBackground(MT.COLOR_BEIGE);
        FormLayoutSet.layout(c);

        final ScrolledComposite sc = new ScrolledComposite(c, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTop().atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        final Composite inner = new Composite(sc, SWT.NONE);
        GridLayoutSet.layout(inner).marginBottom(50);

        final Composite viewPort = new Composite(inner, SWT.NONE);
        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x);
        FormLayoutSet.layout(viewPort);

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        FormLayoutSet.layout(viewPort);

        return c;
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

    private class ContinueButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            release();

            UserTest ut = page.getUserTest();
            ut.setCompletionRate(100 * vo.getViewId() / page.getTestSchema().getViews().size());
            ut.setLastViewId(vo.getViewId() + 1);

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();

            page.resume(ut);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class RecordAgainButtonMouseListener implements MouseListener {

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

    private class PlaybackResponseButtonMouseListener implements MouseListener {

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

    private class StopRecordingButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            if (subViewId == SUB_VIEW_RECORDING) {

                continueButton.setEnabled(true);
                recordAgainButton.setEnabled(true);
                playbackResponseButton.setEnabled(true);

                subViewId = SUB_VIEW_RESPONSE;

                stack.topControl = getSubView(subViewId);
                body.layout();
            }
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
