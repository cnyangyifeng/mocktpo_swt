package com.mocktpo.modules.editor;

import com.mocktpo.modules.editor.layers.*;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.vo.TestVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;
import java.util.UUID;

public class TestEditorPage extends Composite {


    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Stack */

    private StackLayout stack;

    private GeneralEditorLayer generalEditorLayer;
    private ReadingEditorLayer readingEditorLayer;
    private ListeningEditorLayer listeningEditorLayer;
    private SpeakingEditorLayer speakingEditorLayer;
    private WritingEditorLayer writingEditorLayer;
    private PreviewEditorLayer previewEditorLayer;

    private LoadingEditorLayer loadingEditorLayer;

    /* Properties */

    private TestVo testVo;
    private boolean firstRun;
    private boolean unsaved;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestEditorPage(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        initTestVo();
        this.firstRun = true;
        init();
    }

    public TestEditorPage(Composite parent, int style, TestVo testVo) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.testVo = testVo;
        this.firstRun = false;
        init();
    }

    private void initTestVo() {
        this.testVo = new TestVo();
        testVo.setTid(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        testVo.setTitle("");
        testVo.setStars(3);
        testVo.setAuthor("");
        testVo.setCreatedTime(System.currentTimeMillis());
    }

    private void init() {
        golbal();
    }

    private void golbal() {
        FormLayoutSet.layout(this).marginWidth(0).marginHeight(0).spacing(0);
        stack = new StackLayout();
        this.setLayout(stack);
        toGeneralEditorLayer();
    }

    /*
     * ==================================================
     *
     * View Controls
     *
     * ==================================================
     */

    public void toGeneralEditorLayer() {
        if (generalEditorLayer == null) {
            generalEditorLayer = new GeneralEditorLayer(this, SWT.NONE);
        }
        stack.topControl = generalEditorLayer;
        this.layout();
    }

    public void toReadingEditorLayer() {
        if (readingEditorLayer == null) {
            readingEditorLayer = new ReadingEditorLayer(TestEditorPage.this, SWT.NONE);
        }
        if (readingEditorLayer.isDirty()) {
            beforeLoading();
            if (!d.isDisposed()) {
                d.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        readingEditorLayer.refreshCards();
                        afterLoading();
                    }
                });
            }
        } else {
            stack.topControl = readingEditorLayer;
            this.layout();
        }
    }

    public void toListeningEditorLayer() {
        if (listeningEditorLayer == null) {
            listeningEditorLayer = new ListeningEditorLayer(TestEditorPage.this, SWT.NONE);
        }
        stack.topControl = listeningEditorLayer;
        this.layout();
    }

    public void toSpeakingEditorLayer() {
        if (speakingEditorLayer == null) {
            speakingEditorLayer = new SpeakingEditorLayer(TestEditorPage.this, SWT.NONE);
        }
        stack.topControl = speakingEditorLayer;
        this.layout();
    }

    public void toWritingEditorLayer() {
        if (writingEditorLayer == null) {
            writingEditorLayer = new WritingEditorLayer(TestEditorPage.this, SWT.NONE);
        }
        stack.topControl = writingEditorLayer;
        this.layout();
    }

    public void toPreviewEditorLayer() {
        if (previewEditorLayer == null) {
            previewEditorLayer = new PreviewEditorLayer(this, SWT.NONE);
        }
        stack.topControl = previewEditorLayer;
        this.layout();
    }

    public void beforeLoading() {
        if (loadingEditorLayer == null) {
            loadingEditorLayer = new LoadingEditorLayer(this, SWT.NONE);
        }
        stack.topControl = loadingEditorLayer;
        this.layout();
    }

    public void afterLoading() {
        stack.topControl = readingEditorLayer;
        TestEditorPage.this.layout();
    }

    /*
     * ==================================================
     *
     * Save
     *
     * ==================================================
     */

    public void save() {
        ConfigUtils.push(testVo.getTid(), testVo);
        setFirstRun(false);
        enterSavedMode();
    }

    public void edit() {
        enterUnsavedMode();
    }

    public void enterUnsavedMode() {
        setUnsaved(true);
        updateTitleLabels();
    }

    public void enterSavedMode() {
        setUnsaved(false);
        updateTitleLabels();
    }

    public void updateTitleLabels() {
        if (generalEditorLayer != null) {
            generalEditorLayer.updateTitleLabel();
        }
        if (readingEditorLayer != null) {
            readingEditorLayer.updateTitleLabel();
        }
        if (listeningEditorLayer != null) {
            listeningEditorLayer.updateTitleLabel();
        }
        if (speakingEditorLayer != null) {
            speakingEditorLayer.updateTitleLabel();
        }
        if (writingEditorLayer != null) {
            writingEditorLayer.updateTitleLabel();
        }
        if (previewEditorLayer != null) {
            previewEditorLayer.updateTitleLabel();
        }
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public TestVo getTestVo() {
        return testVo;
    }

    public void setTestVo(TestVo testVo) {
        this.testVo = testVo;
    }

    public boolean isFirstRun() {
        return firstRun;
    }

    public void setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
    }

    public boolean isUnsaved() {
        return unsaved;
    }

    public void setUnsaved(boolean unsaved) {
        this.unsaved = unsaved;
    }
}
