package com.mocktpo.modules.editor;

import com.mocktpo.modules.editor.layers.*;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.EditorUtils;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.vo.TestEditorVo;
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

    private TestEditorVo testEditorVo;
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

    public TestEditorPage(Composite parent, int style, TestEditorVo testEditorVo) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.testEditorVo = testEditorVo;
        this.firstRun = false;
        init();
    }

    private void initTestVo() {
        this.testEditorVo = new TestEditorVo();
        testEditorVo.setTid(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        testEditorVo.setTitle("");

        testEditorVo.setStars(3);
        testEditorVo.setCreator("");
        testEditorVo.setCreatedTime(System.currentTimeMillis());
        testEditorVo.setUpdatedTime(System.currentTimeMillis());
        testEditorVo.setVersion(1.0);
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
        if (readingEditorLayer.isRefreshRequired()) {
            toLoadingEditorLayer();
            if (!d.isDisposed()) {
                d.asyncExec(() -> {
                    readingEditorLayer.refreshCards();
                    stack.topControl = readingEditorLayer;
                    TestEditorPage.this.layout();
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
        if (listeningEditorLayer.isRefreshRequired()) {
            toLoadingEditorLayer();
            if (!d.isDisposed()) {
                d.asyncExec(() -> {
                    listeningEditorLayer.refreshCards();
                    stack.topControl = listeningEditorLayer;
                    TestEditorPage.this.layout();
                });
            }
        } else {
            stack.topControl = listeningEditorLayer;
            this.layout();
        }
    }

    public void toSpeakingEditorLayer() {
        if (speakingEditorLayer == null) {
            speakingEditorLayer = new SpeakingEditorLayer(TestEditorPage.this, SWT.NONE);
        }
        if (speakingEditorLayer.isRefreshRequired()) {
            toLoadingEditorLayer();
            if (!d.isDisposed()) {
                d.asyncExec(() -> {
                    speakingEditorLayer.refreshCards();
                    stack.topControl = speakingEditorLayer;
                    TestEditorPage.this.layout();
                });
            }
        } else {
            stack.topControl = speakingEditorLayer;
            this.layout();
        }
    }

    public void toWritingEditorLayer() {
        if (writingEditorLayer == null) {
            writingEditorLayer = new WritingEditorLayer(TestEditorPage.this, SWT.NONE);
        }
        if (writingEditorLayer.isRefreshRequired()) {
            toLoadingEditorLayer();
            if (!d.isDisposed()) {
                d.asyncExec(() -> {
                    writingEditorLayer.refreshCards();
                    stack.topControl = writingEditorLayer;
                    TestEditorPage.this.layout();
                });
            }
        } else {
            stack.topControl = writingEditorLayer;
            this.layout();
        }
    }

    public void toPreviewEditorLayer() {
        if (previewEditorLayer == null) {
            previewEditorLayer = new PreviewEditorLayer(this, SWT.NONE);
        }
        stack.topControl = previewEditorLayer;
        this.layout();
    }

    public void toLoadingEditorLayer() {
        if (loadingEditorLayer == null) {
            loadingEditorLayer = new LoadingEditorLayer(this, SWT.NONE);
        }
        stack.topControl = loadingEditorLayer;
        this.layout();
    }

    /*
     * ==================================================
     *
     * Save and Edit
     *
     * ==================================================
     */

    public void save() {
        EditorUtils.validate(testEditorVo.getTid());
        ConfigUtils.pushToEditorBaseDir(testEditorVo.getTid(), testEditorVo);
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
     * Export
     *
     * ==================================================
     */

    public void export(String fullDestZipFileName) {
        EditorUtils.validate(testEditorVo.getTid());
        EditorUtils.export(testEditorVo.getTid(), fullDestZipFileName);
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public TestEditorVo getTestEditorVo() {
        return testEditorVo;
    }

    public void setTestEditorVo(TestEditorVo testEditorVo) {
        this.testEditorVo = testEditorVo;
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
