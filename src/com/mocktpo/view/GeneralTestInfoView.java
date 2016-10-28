package com.mocktpo.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Label;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.StyleRangeUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import com.mocktpo.util.constants.TV;
import com.mocktpo.vo.StyledTextVo;
import com.mocktpo.widget.ImageButton;

public class GeneralTestInfoView extends TestView {

    public GeneralTestInfoView(TestPage page, int style) {
        super(page, style);
        nextViewId = TV.VIEW_READING_DIRECTIONS;
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
        body.setBackground(ResourceManager.getColor(MT.COLOR_LIGHT_YELLOW));

        final Label tl = new Label(viewPort, SWT.CENTER);
        FormDataSet.attach(tl).atLeft().atTop(50).atRight();
        tl.setText(msgs.getString("general_test_info"));
        tl.setFont(ResourceManager.getFont(MT.FONT_SERIF_TITLE));
        tl.setForeground(ResourceManager.getColor(MT.COLOR_BLUE_PURPLE));

        final StyledText dt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(dt).atLeft().atTopTo(tl, 50).atRight();
        dt.setFont(ResourceManager.getFont(MT.FONT_SUBTITLE));
        dt.setForeground(ResourceManager.getColor(MT.COLOR_DARK_TEXT_GRAY));
        dt.setEditable(false);

        StyledTextVo vo = ConfigUtils.load(RC.GENERAL_TEST_INFO_FILE, StyledTextVo.class);
        dt.setText(vo.getText());
        StyleRangeUtils.decorate(dt, vo.getStyles());
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
