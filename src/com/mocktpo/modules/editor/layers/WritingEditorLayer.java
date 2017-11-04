package com.mocktpo.modules.editor.layers;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.modules.editor.views.*;
import com.mocktpo.modules.editor.widgets.TestViewEditorCard;
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

public class WritingEditorLayer extends SashTestEditorLayer {

    /* Widgets */

    private ImageButton newIntegratedTaskRLWButton;
    private ImageButton newIndependentTaskWButton;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public WritingEditorLayer(TestEditorPage page, int style) {
        super(page, style);
        if (page.getTestEditorVo().getWritingViewVos().size() > 0) {
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
        writingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_WRITING_CHECKED, MT.IMAGE_SYSTEM_STEP_WRITING_CHECKED);
    }

    @Override
    protected void updateFooter() {
        newIntegratedTaskRLWButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLW, MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLW_HOVER);
        FormDataSet.attach(newIntegratedTaskRLWButton).atLeft().atTop();
        newIntegratedTaskRLWButton.addMouseListener(new NewIntegratedTaskRLWButtonMouseAdapter());

        newIndependentTaskWButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_W, MT.IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_W_HOVER);
        FormDataSet.attach(newIndependentTaskWButton).atLeftTo(newIntegratedTaskRLWButton).atTopTo(newIntegratedTaskRLWButton, 0, SWT.TOP);
        newIndependentTaskWButton.addMouseListener(new NewIndependentTaskWButtonMouseAdapter());
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

    public void newWritingReadingPassage() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getWritingViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawWritingReadingPassageViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toWritingEditorLayer();
        page.save();
    }

    public void newWritingListeningMaterial() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getWritingViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawWritingListeningMaterialViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toWritingEditorLayer();
        page.save();
    }

    public void newIntegratedWritingTask() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getWritingViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawIntegratedWritingTaskViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toWritingEditorLayer();
        page.save();
    }

    public void newIndependentWritingTask() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getWritingViewVos();
        TestViewVo viewVo = TestViewVoUtils.initRawIndependentWritingTaskViewVo(++currentViewId);
        viewVos.add(currentViewId, viewVo);
        for (int i = currentViewId + 1; i < viewVos.size(); i++) {
            TestViewVo eachAfter = viewVos.get(i);
            eachAfter.setViewId(i);
        }
        setRefreshRequired(true);
        page.toWritingEditorLayer();
        page.save();
    }

    @Override
    public void trash() {
        MessageBox box = new MessageBox(MyApplication.get().getWindow().getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
        box.setText(msgs.getString("delete"));
        box.setMessage(msgs.getString("delete_test_view_or_not"));
        int response = box.open();
        if (response == SWT.YES) {
            List<TestViewVo> viewVos = page.getTestEditorVo().getWritingViewVos();
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
            page.toWritingEditorLayer();
            page.save();
        }
    }

    @Override
    public void sendBackward() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getWritingViewVos();
        TestViewVo current = viewVos.get(currentViewId);
        current.setViewId(currentViewId + 1);
        TestViewVo back = viewVos.get(currentViewId + 1);
        back.setViewId(currentViewId);
        viewVos.set(currentViewId, back);
        viewVos.set(currentViewId + 1, current);

        currentViewId++;
        setRefreshRequired(true);

        page.toWritingEditorLayer();
        page.save();
    }

    @Override
    public void bringForward() {
        List<TestViewVo> viewVos = page.getTestEditorVo().getWritingViewVos();
        TestViewVo current = viewVos.get(currentViewId);
        current.setViewId(currentViewId - 1);
        TestViewVo front = viewVos.get(currentViewId - 1);
        front.setViewId(currentViewId);
        viewVos.set(currentViewId, front);
        viewVos.set(currentViewId - 1, current);

        currentViewId--;
        setRefreshRequired(true);

        page.toWritingEditorLayer();
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
        List<TestViewVo> viewVos = page.getTestEditorVo().getWritingViewVos();

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
                case VT.VIEW_TYPE_WRITING_READING_PASSAGE:
                    view = new WritingReadingPassageViewEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_WRITING_LISTENING_MATERIAL:
                    view = new WritingListeningMaterialViewEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_INTEGRATED_WRITING_TASK:
                    view = new IntegratedWritingTaskViewEditorView(this, SWT.NONE, viewVo);
                    break;
                case VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK:
                    view = new IndependentWritingTaskViewEditorView(this, SWT.NONE, viewVo);
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

    private class NewIntegratedTaskRLWButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            newWritingReadingPassage();
            newWritingListeningMaterial();
            newIntegratedWritingTask();
        }
    }

    private class NewIndependentTaskWButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            newIndependentWritingTask();
        }
    }
}
