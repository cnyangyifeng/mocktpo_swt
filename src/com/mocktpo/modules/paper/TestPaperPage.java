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
    private boolean initialized;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestPaperPage(Composite parent, int style, TestPaperVo testPaperVo) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.testPaperVo = testPaperVo;
        init();
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
     * Page Controls
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

    public void save() {
        String fileAlias = StringUtils.deleteWhitespace(StringUtils.lowerCase(testPaperVo.getTitle()));
        ConfigUtils.save(fileAlias, testPaperVo);
        setInitialized(true);
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

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
