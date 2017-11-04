package com.mocktpo.modules.editor.views;

import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.modules.editor.layers.SashTestEditorLayer;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.vo.TestViewVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;

public abstract class TestViewEditorView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Page and layer */

    protected TestEditorPage page;
    protected SashTestEditorLayer layer;

    /* Properties */

    protected TestViewVo vo;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestViewEditorView(SashTestEditorLayer layer, int style, TestViewVo viewVo) {
        super(layer.getRight(), style);
        this.d = layer.getDisplay();
        this.layer = layer;
        this.page = layer.getTestEditorPage();
        initViewVo(viewVo);
        init();
    }

    /*
     * ==================================================
     *
     * Data Initialization
     *
     * ==================================================
     */

    private void initViewVo(TestViewVo viewVo) {
        this.vo = viewVo;
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
