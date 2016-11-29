package com.mocktpo.view.test;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.ImageButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Label;

public class HeadsetView extends ResponsiveTestView {

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public HeadsetView(TestPage page, int style) {
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
        final ImageButton cb = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_CONTINUE), ResourceManager.getImage(MT.IMAGE_CONTINUE_HOVER));
        FormDataSet.attach(cb).atRight(10).atTop(10);
        cb.addMouseListener(new ContinueButtonMouseListener());
    }

    @Override
    public void updateBody() {

        body.setBackground(ResourceManager.getColor(MT.COLOR_KHAKI));

        final CLabel t = new CLabel(viewPort, SWT.NONE);
        FormDataSet.attach(t).atLeft().atTop(50).atRight();
        CLabelSet.decorate(t).setFont(MT.FONT_MEDIUM).setText("Now put on your headset.");

        final Label i = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(i).atLeft().atTopTo(t, 10).atRight();
        LabelSet.decorate(i).setImage(MT.IMAGE_ETS_TOEFL);

        final StyledText b = new StyledText(viewPort, SWT.NONE);
        FormDataSet.attach(b).atLeft().atTopTo(i).atRight();
        StyledTextSet.decorate(b).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setText("Click on Continue to go on.");
        b.setStyleRange(new StyleRange(9, 8, null, null, SWT.BOLD));
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

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
