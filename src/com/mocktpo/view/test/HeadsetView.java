package com.mocktpo.view.test;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.ImageButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Label;

public class HeadsetView extends ResponsiveTestView {

    /* Constants */

    private static final int TOP_TEXT_Y = 50;
    private static final int VERTICAL_SPACING = 10;

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

        body.setBackground(ResourceManager.getColor(MT.COLOR_BEIGE));

        final StyledText tt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(tt).atLeft().atTop(TOP_TEXT_Y).atRight();
        StyledTextSet.decorate(tt).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("top").getText());

        final Label il = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(il).atLeft().atTopTo(tt, VERTICAL_SPACING).atRight();
        LabelSet.decorate(il).setImage(MT.IMAGE_HEADSET);

        final StyledText bt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(bt).atLeft().atTopTo(il, VERTICAL_SPACING).atRight();
        StyledTextSet.decorate(bt).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("bottom").getText());
        StyleRangeUtils.decorate(bt, vo.getStyledText("bottom").getStyles());
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
