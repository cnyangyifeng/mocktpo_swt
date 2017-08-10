package com.mocktpo.modules.editor.layers;

import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.modules.editor.views.ReadingMultipleChoiceQuestionEditorView;
import com.mocktpo.modules.editor.views.ReadingPassageEditorView;
import com.mocktpo.modules.editor.views.TestEditorView;
import com.mocktpo.modules.editor.widgets.TestEditorCard;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.VT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.vo.StyledTextVo;
import com.mocktpo.vo.TestViewVo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.widgets.Control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadingEditorLayer extends SashTestEditorLayer {

    /* Properties */

    private List<TestEditorCard> cards;
    private List<TestEditorView> views;
    private int currentIndex;

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
        this.cards = new ArrayList<TestEditorCard>();
        this.views = new ArrayList<TestEditorView>();
        this.currentIndex = 0;
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
        newReadingQuestionButton.addMouseTrackListener(new NewReadingQuestionButtonMouseTrackAdapter());
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
     * Widget Updates
     *
     * ==================================================
     */

    public void refreshEditor() {
        for (Control c : leftBody.getChildren()) {
            c.dispose();
        }
        initEditor();
    }

    private void initEditor() {
        List<TestViewVo> viewVos = page.getTestVo().getViewVos();
        for (TestViewVo viewVo : viewVos) {
            TestEditorCard card = new TestEditorCard(this, SWT.NONE, viewVo);
            GridDataSet.attach(card).fillHorizontal();
            cards.add(card);
        }
        leftBody.layout();
        lsc.setMinSize(leftBody.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        lsc.setOrigin(0, 0);

        for (TestViewVo viewVo : viewVos) {
            TestEditorView view = null;
            switch (viewVo.getViewType()) {
                case VT.VIEW_TYPE_READING_PASSAGE:
                    view = new ReadingPassageEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_READING_MULTIPLE_CHOICE_QUESTION:
                    view = new ReadingMultipleChoiceQuestionEditorView(this, SWT.NONE, viewVo);
                    break;
            }
            views.add(view);
        }
        if (views.size() > 0) {
            rightViewStack.topControl = views.get(currentIndex);
            right.layout();
        }
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
            int viewId = ++currentIndex;
            TestViewVo viewVo = new TestViewVo();
            viewVo.setViewId(viewId);
            viewVo.setViewType(VT.VIEW_TYPE_READING_PASSAGE);
            Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>();
            viewVo.setBody(body);
            page.getTestVo().addViewVo(viewVo);

            TestEditorCard card = new TestEditorCard(ReadingEditorLayer.this, SWT.NONE, viewVo);
            GridDataSet.attach(card).fillHorizontal();
            cards.add(card);
            leftBody.layout();
            lsc.setMinSize(leftBody.computeSize(SWT.DEFAULT, SWT.DEFAULT));
            lsc.setOrigin(0, 0);

            TestEditorView view = new ReadingPassageEditorView(ReadingEditorLayer.this, SWT.NONE, viewVo);
            views.add(view);
            rightViewStack.topControl = view;
            right.layout();

            page.enterUnsavedMode();
        }
    }

    private class NewReadingQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            int viewId = ++currentIndex;
            TestViewVo viewVo = new TestViewVo();
            viewVo.setViewId(viewId);
            viewVo.setViewType(VT.VIEW_TYPE_READING_MULTIPLE_CHOICE_QUESTION);
            Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>();
            viewVo.setBody(body);
            page.getTestVo().addViewVo(viewVo);
logger.info(page.getTestVo());
            TestEditorCard card = new TestEditorCard(ReadingEditorLayer.this, SWT.NONE, viewVo);
            GridDataSet.attach(card).fillHorizontal();
            cards.add(card);
            leftBody.layout();
            lsc.setMinSize(leftBody.computeSize(SWT.DEFAULT, SWT.DEFAULT));
            lsc.setOrigin(0, 0);

            TestEditorView view = new ReadingMultipleChoiceQuestionEditorView(ReadingEditorLayer.this, SWT.NONE, viewVo);
            views.add(view);
            rightViewStack.topControl = view;
            right.layout();

            page.enterUnsavedMode();
        }
    }

    private class NewReadingQuestionButtonMouseTrackAdapter extends MouseTrackAdapter {

        public void mouseEnter(MouseEvent e) {
        }

        public void mouseExit(MouseEvent e) {
        }
    }
}
