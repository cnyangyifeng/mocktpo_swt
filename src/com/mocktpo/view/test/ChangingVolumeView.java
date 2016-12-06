package com.mocktpo.view.test;

import com.mocktpo.listener.BorderedCompositePaintListener;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.CompositeSet;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.StyleRangeUtils;
import com.mocktpo.util.StyledTextSet;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.ImageButton;
import com.mocktpo.widget.VolumeControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Scale;

public class ChangingVolumeView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 100;
    private static final int DESCRIPTION_TEXT_WIDTH = 720;
    private static final int FOOTNOTE_TEXT_WIDTH = 480;

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

    public ChangingVolumeView(TestPage page, int style) {
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

        final ImageButton cb = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER);
        FormDataSet.attach(cb).atRightTo(vob, 16).atTopTo(vob, 8, SWT.TOP);
        cb.addMouseListener(new ContinueButtonMouseListener());

        vc = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(vc).atTopTo(vob, 0, SWT.BOTTOM).atRightTo(vob, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(vc).setVisible(volumeControlVisible);
        vc.addSelectionListener(new VolumeControlSelectionListener());
    }

    @Override
    public void updateBody() {

        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);

        final StyledText ht = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(ht).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(ht).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SERIF_HEADING).setForeground(MT.COLOR_DARK_BLUE).setText(vo.getStyledText("heading").getText());

        final StyledText dt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(dt).fromLeft(50, -DESCRIPTION_TEXT_WIDTH / 2).atTopTo(ht, 50).withWidth(DESCRIPTION_TEXT_WIDTH);
        StyledTextSet.decorate(dt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("description").getText());
        StyleRangeUtils.decorate(dt, vo.getStyledText("description").getStyles());

        final StyledText ft = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(ft).fromLeft(50, -FOOTNOTE_TEXT_WIDTH / 2).atTopTo(dt, 50).withWidth(FOOTNOTE_TEXT_WIDTH);
        StyledTextSet.decorate(ft).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SERIF_ITALIC_TEXT).setForeground(MT.COLOR_DARK_BLUE).setLineSpacing(5).setMarginHeight(50).setText(vo.getStyledText("footnote").getText());
        ft.addPaintListener(new BorderedCompositePaintListener());
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
            if (null != audioPlayer) {
                double selection = s.getSelection(), maximum = s.getMaximum();
                audioPlayer.setVolume(selection / maximum);
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
            ut.setLastViewId(vo.getViewId() + 1);

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();

            page.resume(ut);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
