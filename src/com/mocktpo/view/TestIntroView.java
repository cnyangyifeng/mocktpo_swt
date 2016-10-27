package com.mocktpo.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Label;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.TV;
import com.mocktpo.widget.ImageButton;

public class TestIntroView extends TestView {

    public TestIntroView(TestPage page, int style) {
        super(page, style);
        nextViewId = TV.VIEW_GENERAL_TEST_INFO;
    }

    @Override
    public void updateHeader() {
        final ImageButton ptb = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_PAUSE_TEST), ResourceManager.getImage(MT.IMAGE_PAUSE_TEST_HOVER));
        FormDataSet.attach(ptb).atLeft(10).atBottom(10);
        ptb.addMouseListener(new PauseTestButtonMouseListener());

        final ImageButton cb = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_CONTINUE), ResourceManager.getImage(MT.IMAGE_CONTINUE_HOVER));
        FormDataSet.attach(cb).atRight(10).atTop(10);
        cb.addMouseListener(new ContinueButtonMouseListener());
    }

    @Override
    public void updateBody() {
        body.setBackground(ResourceManager.getColor(MT.COLOR_DUST_RED));

        final Label et = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(et).atLeft().atTop(50).atRight();
        et.setImage(ResourceManager.getImage(MT.IMAGE_ETS_TOEFL));

        final StyledText dt = new StyledText(viewPort, SWT.WRAP | SWT.CENTER);
        FormDataSet.attach(dt).atLeft().atTopTo(et, 50).atRight();
        dt.setFont(ResourceManager.getFont(MT.FONT_SUBTITLE));
        dt.setEditable(false);
        String content = "EDUCATIONAL TESTING SERVICE, ETS, the ETS logo, TOEFL and TOEFL iBT are registered trademarks of Educational Testing Service (ETS) in the United States and other countries.\n\nClick on Continue to go on.";
        dt.setText(content);
        StyleRange style = new StyleRange(0, 4, null, null, SWT.BOLD);
        dt.setStyleRanges(new StyleRange[] { style });
    }

    /**************************************************
     * 
     * Listeners
     * 
     **************************************************/

    private class PauseTestButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            MyApplication.get().getWindow().resetToMainPage(page.getUserTest());
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
            ut.setLastViewId(nextViewId);
            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();
            page.resume(ut);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
