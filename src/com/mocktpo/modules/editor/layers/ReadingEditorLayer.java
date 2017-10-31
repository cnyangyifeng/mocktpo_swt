package com.mocktpo.modules.editor.layers;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.modules.editor.views.*;
import com.mocktpo.modules.editor.widgets.TestViewEditorCard;
import com.mocktpo.modules.editor.windows.NewReadingQuestionWindow;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.util.TestViewVoUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.VT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.vo.StyledTextVo;
import com.mocktpo.vo.TestViewVo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;

import java.util.Iterator;
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
        if (page.getTestEditorVo().getReadingViewVos().size() > 0) {
            this.currentViewId = 0;
        } else {
            this.currentViewId = -1;
        }
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

    /*
     * ==================================================
     *
     * Test Editor View Operations
     *
     * ==================================================
     */

    public void newReadingPassage() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getReadingViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawReadingPassageViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toReadingEditorLayer();
        page.save();
    }

    public void newReadingMultipleChoiceQuestion() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getReadingViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawReadingMultipleChoiceQuestionViewVo(++currentViewId);
        updateReadingPassageForQuestionView(viewVo, viewVos);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toReadingEditorLayer();
        page.save();
    }

    public void newReadingInsertTextQuestion() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getReadingViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawReadingInsertTextQuestionViewVo(++currentViewId);
        updateReadingPassageForQuestionView(viewVo, viewVos);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toReadingEditorLayer();
        page.save();
    }

    public void newReadingProseSummaryQuestion() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getReadingViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawReadingProseSummaryQuestionViewVo(++currentViewId);
        updateReadingPassageForQuestionView(viewVo, viewVos);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toReadingEditorLayer();
        page.save();
    }

    public void newReadingFillInATableQuestion() {

    }

    private void updateReadingPassageForQuestionView(TestViewVo viewVo, List<TestViewVo> readingViewVos) {
        if (viewVo.getViewType() != VT.VIEW_TYPE_READING_MULTIPLE_CHOICE_QUESTION &&
                viewVo.getViewType() != VT.VIEW_TYPE_READING_INSERT_TEXT_QUESTION &&
                viewVo.getViewType() != VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION &&
                viewVo.getViewType() != VT.VIEW_TYPE_READING_FILL_IN_A_TABLE_QUESTION) {
            return;
        }
        for (int i = viewVo.getViewId() - 1; i >= 0; i--) {
            TestViewVo tmp = readingViewVos.get(i);
            if (tmp.getViewType() == VT.VIEW_TYPE_READING_PASSAGE) {
                /* Sets headingTextVo */
                StyledTextVo headingTextVo = new StyledTextVo();
                headingTextVo.setText(tmp.getStyledTextContent("heading"));
                viewVo.setStyledTextVo("heading", headingTextVo);
                /* Sets passageTextVo */
                StyledTextVo passageTextVo = new StyledTextVo();
                passageTextVo.setText(tmp.getStyledTextContent("passage"));
                viewVo.setStyledTextVo("passage", passageTextVo);
                break;
            }
        }
    }

    @Override
    public void trash() {
        MessageBox box = new MessageBox(MyApplication.get().getWindow().getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
        box.setText(msgs.getString("delete"));
        box.setMessage(msgs.getString("delete_test_view_or_not"));
        int response = box.open();
        if (response == SWT.YES) {
            List<TestViewVo> viewVos = page.getTestEditorVo().getReadingViewVos();
            Iterator<TestViewVo> it = viewVos.iterator();
            while (it.hasNext()) {
                TestViewVo viewVo = it.next();
                if (viewVo.getViewId() == currentViewId) {
                    it.remove();
                    break;
                }
            }
            for (int i = currentViewId; i < viewVos.size(); i++) {
                TestViewVo eachAfter = viewVos.get(i);
                eachAfter.setViewId(i);
            }
            if (currentViewId == viewVos.size()) {
                currentViewId--;
            }
            setRefreshRequired(true);
            page.toReadingEditorLayer();
            page.save();
        }
    }

    @Override
    public void sendBackward() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getReadingViewVos();
        TestViewVo current = viewVos.get(currentViewId);
        current.setViewId(currentViewId + 1);
        TestViewVo back = viewVos.get(currentViewId + 1);
        back.setViewId(currentViewId);
        viewVos.set(currentViewId, back);
        viewVos.set(currentViewId + 1, current);

        currentViewId++;
        setRefreshRequired(true);

        page.toReadingEditorLayer();
        page.save();
    }

    @Override
    public void bringForward() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getReadingViewVos();
        TestViewVo current = viewVos.get(currentViewId);
        current.setViewId(currentViewId - 1);
        TestViewVo front = viewVos.get(currentViewId - 1);
        front.setViewId(currentViewId);
        viewVos.set(currentViewId, front);
        viewVos.set(currentViewId - 1, current);

        currentViewId--;
        setRefreshRequired(true);

        page.toReadingEditorLayer();
        page.save();
    }

    /*
     * ==================================================
     *
     * Test Editor Card Operations
     *
     * ==================================================
     */

    public void refreshCards() {
        for (Control c : leftBody.getChildren()) {
            c.dispose();
        }
        initCards();
    }

    private void initCards() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getReadingViewVos();

        /*
         * ==================================================
         *
         * Update Left Part
         *
         * ==================================================
         */

        cards.clear();
        for (TestViewVo viewVo : viewVos) {
            TestViewEditorCard card = new TestViewEditorCard(this, SWT.NONE, viewVo);
            GridDataSet.attach(card).fillHorizontal();
            cards.add(card);
        }
        leftBody.layout();
        lsc.setMinSize(leftBody.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        if (currentViewId >= 0) {
            lsc.setOrigin(0, cards.get(currentViewId).getLocation().y - 208); // 20+120+20
        }

        /*
         * ==================================================
         *
         * Update Right Part
         *
         * ==================================================
         */

        views.clear();
        for (TestViewVo viewVo : viewVos) {
            TestEditorView view = null;
            switch (viewVo.getViewType()) {
                case VT.VIEW_TYPE_READING_PASSAGE:
                    view = new ReadingPassageEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_READING_MULTIPLE_CHOICE_QUESTION:
                    view = new ReadingMultipleChoiceQuestionEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_READING_INSERT_TEXT_QUESTION:
                    view = new ReadingInsertTextQuestionEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION:
                    view = new ReadingProseSummaryQuestionEditorView(this, SWT.NONE, viewVo);
                    break;
            }
            views.add(view);
        }

        check(currentViewId);
        setRefreshRequired(false);
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
            newReadingPassage();
        }
    }

    private class NewReadingQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            int leftBottomX = newReadingQuestionButton.getLocation().x - 20;
            int leftBottomY = getBounds().height - 64;
            new NewReadingQuestionWindow(ReadingEditorLayer.this, leftBottomX, leftBottomY).openAndWaitForDisposal();
        }
    }
}
