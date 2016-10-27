package com.mocktpo.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Label;

import com.mocktpo.MyApplication;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.ImageButton;

public class GeneralTestInfoView extends TestView {

    public GeneralTestInfoView(TestPage page, int style) {
        super(page, style);
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

        final Label et = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(et).atLeft().atTop(50).atRight();
        et.setImage(ResourceManager.getImage(MT.IMAGE_ETS_TOEFL));

        final Label dt = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(dt).atLeft().atTopTo(et, 50).atRight();
        dt.setFont(ResourceManager.getFont(MT.FONT_SUBTITLE));
        String content = "EDUCATIONAL TESTING SERVICE, ETS, the ETS logo, TOEFL and TOEFL iBT are registered trademarks of Educational Testing Service (ETS) in the United States and other countries.\n\nClick on Continue to go on.";
        dt.setText(content);
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
            // TODO Switch to the next view
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
