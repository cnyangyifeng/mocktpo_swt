package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.paper.TestPaperPage;
import com.mocktpo.modules.test.TestPage;
import com.mocktpo.orm.domain.TestPaper;
import com.mocktpo.util.constants.VT;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.vo.StyledTextVo;
import com.mocktpo.vo.TestPaperVo;
import com.mocktpo.vo.TestViewVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class TestEditorView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Page */

    protected TestPaperPage page;

    /* Paper View */

    protected TestViewVo viewVo;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestEditorView(SashTestPaperView paperView, int style, int viewId) {
        super(paperView.getRight(), style);
        this.d = paperView.getDisplay();
        this.page = paperView.getTestPaperPage();
        initTestViewVo(viewId);
        init();
    }

    /*
     * ==================================================
     *
     * Data Initialization
     *
     * ==================================================
     */

    private void initTestViewVo(int viewId) {
        this.viewVo = page.getTestPaperVo().getViewVo(viewId);
        if (viewVo == null) {
            viewVo = new TestViewVo();
            viewVo.setViewId(viewId);
            Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>();
            viewVo.setBody(body);
            page.getTestPaperVo().addViewVo(viewVo);
        }
        updateTestViewVo();
    }

    protected abstract void updateTestViewVo();

    /*
     * ==================================================
     *
     * UI Initialization
     *
     * ==================================================
     */

    private void init() {
        golbal();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this).marginWidth(0).marginHeight(0).spacing(0);
    }

    protected abstract void initBody();
}
