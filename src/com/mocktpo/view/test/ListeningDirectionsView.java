package com.mocktpo.view.test;

import com.mocktpo.listener.StyledTextPaintImageListener;
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

public class ListeningDirectionsView extends ResponsiveTestView {

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ListeningDirectionsView(TestPage page, int style) {
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

        final ImageButton nob = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_NEXT_OVAL), ResourceManager.getImage(MT.IMAGE_NEXT_OVAL_HOVER), ResourceManager.getImage(MT.IMAGE_NEXT_OVAL_DISABLED));
        FormDataSet.attach(nob).atRight(10).atTop(10);
        nob.setEnabled(false);
        nob.addMouseListener(new NextOvalButtonMouseListener());

        final ImageButton oob = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_OK_OVAL), ResourceManager.getImage(MT.IMAGE_OK_OVAL_HOVER), ResourceManager.getImage(MT.IMAGE_OK_OVAL_DISABLED));
        FormDataSet.attach(oob).atRightTo(nob).atTopTo(nob, 0, SWT.TOP);
        oob.setEnabled(false);
        oob.addMouseListener(new OkOvalButtonMouseListener());

        final ImageButton hob = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_HELP_OVAL), ResourceManager.getImage(MT.IMAGE_HELP_OVAL_HOVER), ResourceManager.getImage(MT.IMAGE_HELP_OVAL_DISABLED));
        FormDataSet.attach(hob).atRightTo(oob).atTopTo(nob, 0, SWT.TOP);
        hob.setEnabled(false);
        hob.addMouseListener(new HelpOvalButtonMouseListener());

        final ImageButton vob = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_VOLUME_OVAL), ResourceManager.getImage(MT.IMAGE_VOLUME_OVAL_HOVER));
        FormDataSet.attach(vob).atRightTo(hob).atTopTo(nob, 0, SWT.TOP);
        vob.addMouseListener(new VolumeOvalButtonMouseListener());

        final ImageButton cb = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_CONTINUE), ResourceManager.getImage(MT.IMAGE_CONTINUE_HOVER));
        FormDataSet.attach(cb).atRightTo(vob, 16).atTopTo(nob, 8, SWT.TOP);
        cb.addMouseListener(new ContinueButtonMouseListener());
    }

    @Override
    public void updateBody() {

        body.setBackground(ResourceManager.getColor(MT.COLOR_KHAKI));

        final StyledText ht = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(ht).atLeft().atTop(50).atRight();
        StyledTextSet.decorate(ht).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SERIF_HEADING).setForeground(MT.COLOR_DARK_BLUE).setText(vo.getStyledText("heading").getText());

        final StyledText dt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(dt).atLeft().atTopTo(ht, 50).atRight();
        StyledTextSet.decorate(dt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("description").getText());
        StyleRangeUtils.decorate(dt, vo.getStyledText("description").getStyles());
        dt.addPaintObjectListener(new StyledTextPaintImageListener());
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
