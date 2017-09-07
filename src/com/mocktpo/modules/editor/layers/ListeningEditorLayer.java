package com.mocktpo.modules.editor.layers;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.modules.editor.views.*;
import com.mocktpo.modules.editor.widgets.TestEditorCard;
import com.mocktpo.modules.editor.windows.NewListeningQuestionWindow;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.util.TestViewVoUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.VT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.vo.TestViewVo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;

import java.util.Iterator;
import java.util.List;

public class ListeningEditorLayer extends SashTestEditorLayer {

    /* Widgets */

    private ImageButton newListeningMaterialButton;
    private ImageButton newListeningQuestionButton;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ListeningEditorLayer(TestEditorPage page, int style) {
        super(page, style);
        if (page.getTestEditorVo().getListeningViewVos().size() > 0) {
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
        listeningButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_LISTENING_CHECKED, MT.IMAGE_SYSTEM_STEP_LISTENING_CHECKED);
    }

    @Override
    protected void updateFooter() {
        newListeningMaterialButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_NEW_LISTENING_MATERIAL, MT.IMAGE_SYSTEM_NEW_LISTENING_MATERIAL_HOVER);
        FormDataSet.attach(newListeningMaterialButton).atLeft().atTop();
        newListeningMaterialButton.addMouseListener(new NewListeningMaterialButtonMouseAdapter());

        newListeningQuestionButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_NEW_LISTENING_QUESTION, MT.IMAGE_SYSTEM_NEW_LISTENING_QUESTION_HOVER);
        FormDataSet.attach(newListeningQuestionButton).atLeftTo(newListeningMaterialButton).atTopTo(newListeningMaterialButton, 0, SWT.TOP);
        newListeningQuestionButton.addMouseListener(new NewListeningQuestionButtonMouseAdapter());
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

    public void newListeningMaterial() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getListeningViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawListeningMaterialViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toListeningEditorLayer();
        page.save();
    }

    public void newListeningMultipleChoiceQuestion() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getListeningViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawListeningMultipleChoiceQuestionViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toListeningEditorLayer();
        page.save();
    }

    public void newListeningMultipleResponseQuestion() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getListeningViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawListeningMultipleResponseQuestionViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toListeningEditorLayer();
        page.save();
    }

    public void newListeningReplayQuestion() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getListeningViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawListeningReplayViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toListeningEditorLayer();
        page.save();
    }

    public void newListeningSortEventsQuestion() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getListeningViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawListeningSortEventsQuestionViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toListeningEditorLayer();
        page.save();
    }

    public void newListeningCategorizeObjectsQuestion() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getListeningViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawListeningCategorizeObjectsQuestionViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toListeningEditorLayer();
        page.save();
    }

    @Override
    public void trash() {
        MessageBox box = new MessageBox(MyApplication.get().getWindow().getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
        box.setText(msgs.getString("delete"));
        box.setMessage(msgs.getString("delete_test_view_or_not"));
        int response = box.open();
        if (response == SWT.YES) {
            List<TestViewVo> viewVos = page.getTestEditorVo().getListeningViewVos();
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
            page.toListeningEditorLayer();
            page.save();
        }
    }

    @Override
    public void sendBackward() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getListeningViewVos();
        TestViewVo current = viewVos.get(currentViewId);
        current.setViewId(currentViewId + 1);
        TestViewVo back = viewVos.get(currentViewId + 1);
        back.setViewId(currentViewId);
        viewVos.set(currentViewId, back);
        viewVos.set(currentViewId + 1, current);

        currentViewId++;
        setRefreshRequired(true);

        page.toListeningEditorLayer();
        page.save();
    }

    @Override
    public void bringForward() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getListeningViewVos();
        TestViewVo current = viewVos.get(currentViewId);
        current.setViewId(currentViewId - 1);
        TestViewVo front = viewVos.get(currentViewId - 1);
        front.setViewId(currentViewId);
        viewVos.set(currentViewId, front);
        viewVos.set(currentViewId - 1, current);

        currentViewId--;
        setRefreshRequired(true);

        page.toListeningEditorLayer();
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
        List<TestViewVo> viewVos = page.getTestEditorVo().getListeningViewVos();

        /*
         * ==================================================
         *
         * Update Left Part
         *
         * ==================================================
         */

        cards.clear();
        for (TestViewVo viewVo : viewVos) {
            TestEditorCard card = new TestEditorCard(this, SWT.NONE, viewVo);
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
                case VT.VIEW_TYPE_LISTENING_MATERIAL:
                    view = new ListeningMaterialEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_LISTENING_MULTIPLE_CHOICE_QUESTION:
                    view = new ListeningMultipleChoiceQuestionEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_LISTENING_MULTIPLE_RESPONSE_QUESTION:
                    view = new ListeningMultipleResponseQuestionEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_LISTENING_REPLAY:
                    view = new ListeningReplayEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_LISTENING_SORT_EVENTS_QUESTION:
                    view = new ListeningSortEventsQuestionEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_LISTENING_CATEGORIZE_OBJECTS_QUESTION:
                    view = new ListeningCategorizeObjectsQuestionEditorView(this, SWT.NONE, viewVo);
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

    private class NewListeningMaterialButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            newListeningMaterial();
        }
    }

    private class NewListeningQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            int leftBottomX = newListeningQuestionButton.getLocation().x - 20;
            int leftBottomY = getBounds().height - 64;
            new NewListeningQuestionWindow(ListeningEditorLayer.this, leftBottomX, leftBottomY).openAndWaitForDisposal();
        }
    }
}
