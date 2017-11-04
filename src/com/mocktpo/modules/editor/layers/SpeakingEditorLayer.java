package com.mocktpo.modules.editor.layers;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.modules.editor.views.*;
import com.mocktpo.modules.editor.widgets.TestViewEditorCard;
import com.mocktpo.modules.editor.windows.NewIntegratedSpeakingTaskWindow;
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

public class SpeakingEditorLayer extends SashTestEditorLayer {

    /* Widgets */

    private ImageButton newIndependentTaskSButton;
    private ImageButton newIntegratedTaskSButton;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public SpeakingEditorLayer(TestEditorPage page, int style) {
        super(page, style);
        if (page.getTestEditorVo().getSpeakingViewVos().size() > 0) {
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
        speakingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_SPEAKING_CHECKED, MT.IMAGE_SYSTEM_STEP_SPEAKING_CHECKED);
    }

    @Override
    protected void updateFooter() {
        newIndependentTaskSButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_S, MT.IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_S_HOVER);
        FormDataSet.attach(newIndependentTaskSButton).atLeft().atTop();
        newIndependentTaskSButton.addMouseListener(new NewIndependentTaskSButtonMouseAdapter());

        newIntegratedTaskSButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_S, MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_S_HOVER);
        FormDataSet.attach(newIntegratedTaskSButton).atLeftTo(newIndependentTaskSButton).atTopTo(newIndependentTaskSButton, 0, SWT.TOP);
        newIntegratedTaskSButton.addMouseListener(new NewIntegratedTaskSButtonMouseAdapter());
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

    public void newSpeakingTaskDirections() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getSpeakingViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawSpeakingTaskDirectionsViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toSpeakingEditorLayer();
        page.save();
    }

    public void newSpeakingTask() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getSpeakingViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawSpeakingTaskViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toSpeakingEditorLayer();
        page.save();
    }

    public void newSpeakingReadingPassage() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getSpeakingViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawSpeakingReadingPassageViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toSpeakingEditorLayer();
        page.save();
    }

    public void newSpeakingListeningMaterial() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getSpeakingViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawSpeakingListeningMaterialViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toSpeakingEditorLayer();
        page.save();
    }

    @Override
    public void trash() {
        MessageBox box = new MessageBox(MyApplication.get().getWindow().getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
        box.setText(msgs.getString("delete"));
        box.setMessage(msgs.getString("delete_test_view_or_not"));
        int response = box.open();
        if (response == SWT.YES) {
            List<TestViewVo> viewVos = page.getTestEditorVo().getSpeakingViewVos();
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
            page.toSpeakingEditorLayer();
            page.save();
        }
    }

    @Override
    public void sendBackward() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getSpeakingViewVos();
        TestViewVo current = viewVos.get(currentViewId);
        current.setViewId(currentViewId + 1);
        TestViewVo back = viewVos.get(currentViewId + 1);
        back.setViewId(currentViewId);
        viewVos.set(currentViewId, back);
        viewVos.set(currentViewId + 1, current);

        currentViewId++;
        setRefreshRequired(true);

        page.toSpeakingEditorLayer();
        page.save();
    }

    @Override
    public void bringForward() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getSpeakingViewVos();
        TestViewVo current = viewVos.get(currentViewId);
        current.setViewId(currentViewId - 1);
        TestViewVo front = viewVos.get(currentViewId - 1);
        front.setViewId(currentViewId);
        viewVos.set(currentViewId, front);
        viewVos.set(currentViewId - 1, current);

        currentViewId--;
        setRefreshRequired(true);

        page.toSpeakingEditorLayer();
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
        List<TestViewVo> viewVos = page.getTestEditorVo().getSpeakingViewVos();

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
            TestViewEditorView view = null;
            switch (viewVo.getViewType()) {
                case VT.VIEW_TYPE_SPEAKING_TASK_DIRECTIONS:
                    view = new SpeakingTaskDirectionsViewEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_SPEAKING_TASK:
                    view = new SpeakingTaskViewEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_SPEAKING_READING_PASSAGE:
                    view = new SpeakingReadingPassageViewEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_SPEAKING_LISTENING_MATERIAL:
                    view = new SpeakingListeningMaterialViewEditorView(this, SWT.NONE, viewVo);
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

    private class NewIndependentTaskSButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            newSpeakingTaskDirections();
            newSpeakingTask();
        }
    }

    private class NewIntegratedTaskSButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            int leftBottomX = newIntegratedTaskSButton.getLocation().x - 20;
            int leftBottomY = getBounds().height - 64;
            new NewIntegratedSpeakingTaskWindow(SpeakingEditorLayer.this, leftBottomX, leftBottomY).openAndWaitForDisposal();
        }
    }
}
