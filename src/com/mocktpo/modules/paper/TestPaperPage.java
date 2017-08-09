package com.mocktpo.modules.paper;

import com.mocktpo.modules.paper.views.*;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.vo.TestPaperVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;
import java.util.UUID;

public class TestPaperPage extends Composite {


    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Stack */

    private StackLayout stack;

    private GeneralPaperView generalPaperView;
    private ReadingPaperView readingPaperView;
    private ListeningPaperView listeningPaperView;
    private SpeakingPaperView speakingPaperView;
    private WritingPaperView writingPaperView;
    private PreviewPaperView previewPaperView;

    /* Properties */

    private TestPaperVo testPaperVo;
    private boolean firstRun;
    private boolean unsaved;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestPaperPage(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        initTestPaperVo();
        this.firstRun = true;
        init();
    }

    public TestPaperPage(Composite parent, int style, TestPaperVo testPaperVo) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.testPaperVo = testPaperVo;
        this.firstRun = false;
        init();
    }

    private void initTestPaperVo() {
        this.testPaperVo = new TestPaperVo();
        testPaperVo.setTid(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        testPaperVo.setTitle("");
        testPaperVo.setStars(0);
        testPaperVo.setAuthor("");
        testPaperVo.setCreatedTime(System.currentTimeMillis());
    }

    private void init() {
        golbal();
    }

    private void golbal() {
        FormLayoutSet.layout(this).marginWidth(0).marginHeight(0).spacing(0);
        stack = new StackLayout();
        this.setLayout(stack);
        toGeneralPaperView();
    }

    /*
     * ==================================================
     *
     * View Controls
     *
     * ==================================================
     */

    public void toGeneralPaperView() {
        if (generalPaperView == null) {
            generalPaperView = new GeneralPaperView(this, SWT.NONE);
        }
        stack.topControl = generalPaperView;
        this.layout();
    }

    public void toReadingPaperView() {
        if (readingPaperView == null) {
            readingPaperView = new ReadingPaperView(this, SWT.NONE);
        }
        readingPaperView.refreshTestPaperViewCards();
        stack.topControl = readingPaperView;
        this.layout();
    }

    public void toListeningPaperView() {
        if (listeningPaperView == null) {
            listeningPaperView = new ListeningPaperView(this, SWT.NONE);
        }
        stack.topControl = listeningPaperView;
        this.layout();
    }

    public void toSpeakingPaperView() {
        if (speakingPaperView == null) {
            speakingPaperView = new SpeakingPaperView(this, SWT.NONE);
        }
        stack.topControl = speakingPaperView;
        this.layout();
    }

    public void toWritingPaperView() {
        if (writingPaperView == null) {
            writingPaperView = new WritingPaperView(this, SWT.NONE);
        }
        stack.topControl = writingPaperView;
        this.layout();
    }

    public void toPreviewPaperView() {
        if (previewPaperView == null) {
            previewPaperView = new PreviewPaperView(this, SWT.NONE);
        }
        stack.topControl = previewPaperView;
        this.layout();
    }

    /*
     * ==================================================
     *
     * Save
     *
     * ==================================================
     */

    public void save() {
        ConfigUtils.push(testPaperVo.getTid(), testPaperVo);
        setFirstRun(false);
        enterSavedMode();
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
        if (generalPaperView != null) {
            generalPaperView.updateTitleLabel();
        }
        if (readingPaperView != null) {
            readingPaperView.updateTitleLabel();
        }
        if (listeningPaperView != null) {
            listeningPaperView.updateTitleLabel();
        }
        if (speakingPaperView != null) {
            speakingPaperView.updateTitleLabel();
        }
        if (writingPaperView != null) {
            writingPaperView.updateTitleLabel();
        }
        if (previewPaperView != null) {
            previewPaperView.updateTitleLabel();
        }
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public TestPaperVo getTestPaperVo() {
        return testPaperVo;
    }

    public void setTestPaperVo(TestPaperVo testPaperVo) {
        this.testPaperVo = testPaperVo;
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
