package com.mocktpo.view.test;

import com.mocktpo.listener.BorderedCompositePaintListener;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.StyleRangeUtils;
import com.mocktpo.util.StyledTextSet;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.ImageButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

public class ChangingVolumeView extends ResponsiveTestView {

    /* Constants */

    private static final int HEADING_TEXT_Y = 100;
    private static final int DESCRIPTION_TEXT_WIDTH = 720;
    private static final int FOOTNOTE_TEXT_WIDTH = 480;
    private static final int VERTICAL_SPACING = 50;

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

        final ImageButton vob = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_VOLUME_OVAL), ResourceManager.getImage(MT.IMAGE_VOLUME_OVAL_HOVER));
        FormDataSet.attach(vob).atRight(10).atTop(10);
        vob.addMouseListener(new VolumeOvalButtonMouseListener());

        final ImageButton cb = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_CONTINUE), ResourceManager.getImage(MT.IMAGE_CONTINUE_HOVER));
        FormDataSet.attach(cb).atRightTo(vob, 16).atTopTo(vob, 8, SWT.TOP);
        cb.addMouseListener(new ContinueButtonMouseListener());
    }

    @Override
    public void updateBody() {

        body.setBackground(ResourceManager.getColor(MT.COLOR_BEIGE));

        final StyledText ht = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(ht).atLeft().atTop(HEADING_TEXT_Y).atRight();
        StyledTextSet.decorate(ht).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SERIF_HEADING).setForeground(MT.COLOR_DARK_BLUE).setText(vo.getStyledText("heading").getText());

        final StyledText dt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(dt).fromLeft(50, -DESCRIPTION_TEXT_WIDTH / 2).atTopTo(ht, VERTICAL_SPACING).withWidth(DESCRIPTION_TEXT_WIDTH);
        StyledTextSet.decorate(dt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("description").getText());
        StyleRangeUtils.decorate(dt, vo.getStyledText("description").getStyles());

        final StyledText ft = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(ft).fromLeft(50, -FOOTNOTE_TEXT_WIDTH / 2).atTopTo(dt, VERTICAL_SPACING).withWidth(FOOTNOTE_TEXT_WIDTH);
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

        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class ContinueButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

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
