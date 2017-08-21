package com.mocktpo.modules.editor.layers;

import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.modules.editor.windows.NewReadingQuestionWindow;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.util.TestViewUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.vo.TestViewVo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

import java.util.List;

public class ReadingEditorLayer extends SashTestEditorLayer {

    /* Widgets */

    private ImageButton newReadingPassageButton;
    private ImageButton newReadingQuestionButton;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingEditorLayer(TestEditorPage page, int style) {
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
    protected void updateHeader() {
        readingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_READING_CHECKED, MT.IMAGE_SYSTEM_STEP_READING_CHECKED);
    }

    @Override
    protected void updateFooter() {
        newReadingPassageButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_NEW_READING_PASSAGE, MT.IMAGE_SYSTEM_NEW_READING_PASSAGE_HOVER);
        FormDataSet.attach(newReadingPassageButton).atLeft().atTop();
        newReadingPassageButton.addMouseListener(new NewReadingPassageButtonMouseAdapter());

        newReadingQuestionButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_NEW_READING_QUESTION, MT.IMAGE_SYSTEM_NEW_READING_QUESTION_HOVER);
        FormDataSet.attach(newReadingQuestionButton).atLeftTo(newReadingPassageButton).atTopTo(newReadingPassageButton, 0, SWT.TOP);
        newReadingQuestionButton.addMouseListener(new NewReadingQuestionButtonMouseAdapter());
    }

    @Override
    protected void updateLeft() {
    }

    @Override
    protected void updateRight() {
    }

    public void newReadingMultipleChoiceQuestion() {
        TestViewVo viewVo = TestViewUtils.newReadingMultipleChoiceQuestionViewVo(++currentViewId);
        List<TestViewVo> viewVos = page.getTestVo().getViewVos();
        viewVos.add(viewVo);
        for (int i = viewVos.size() - 1; i > currentViewId; i--) {
            TestViewVo eachAfter = viewVos.get(i - 1);
            eachAfter.setViewId(i);
            viewVos.set(i, eachAfter);
        }
        viewVos.set(currentViewId, viewVo);
        setDirty(true);

        page.toReadingEditorLayer();
        page.save();
    }

    public void newReadingInsertTextQuestion() {

    }

    public void newReadingProseSummaryQuestion() {

    }

    public void newReadingFillInATableQuestion() {

    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class NewReadingPassageButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            TestViewVo viewVo = TestViewUtils.newReadingPassageView(++currentViewId);
            List<TestViewVo> viewVos = page.getTestVo().getViewVos();
            viewVos.add(viewVo);
            for (int i = viewVos.size() - 1; i > currentViewId; i--) {
                TestViewVo eachAfter = viewVos.get(i - 1);
                eachAfter.setViewId(i);
                viewVos.set(i, eachAfter);
            }
            viewVos.set(currentViewId, viewVo);
            setDirty(true);

            page.toReadingEditorLayer();
            page.save();
        }
    }

    private class NewReadingQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            int leftBottomX = newReadingQuestionButton.getLocation().x - 20;
            int leftBottomY = getBounds().height - 54;
            new NewReadingQuestionWindow(ReadingEditorLayer.this, leftBottomX, leftBottomY).openAndWaitForDisposal();
        }
    }
}
