package com.mocktpo.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import com.mocktpo.MyApplication;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.ImageButton;

public class GeneralTestInfoView extends TestTemplateView {

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
            MyApplication.get().getWindow().toMainPage();
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
