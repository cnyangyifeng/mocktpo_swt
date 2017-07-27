package com.mocktpo.modules.paper;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.paper.views.*;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.util.PDFUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.util.ResourceBundle;

public class TestPaperPage extends Composite {

    /* Constants */

    private static final int TITLE_WIDTH = 300;
    private static final int STEP_BUTTON_WIDTH = 120;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Stack */

    private StackLayout stack;

    /* Widgets */

    private Composite header;
    private ImageButton generalButton;
    private ImageButton readingButton;
    private ImageButton listeningButton;
    private ImageButton speakingButton;
    private ImageButton writingButton;
    private ImageButton previewButton;

    private Composite body;
    private GeneralPaperView generalPaperView;
    private ReadingPaperView readingPaperView;
    private ListeningPaperView listeningPaperView;
    private SpeakingPaperView speakingPaperView;
    private WritingPaperView writingPaperView;
    private PreviewPaperView previewPaperView;

    private Composite footer;
    private ImageButton exportAsZipButton;
    private ImageButton saveButton;
    private CLabel titleLabel;

    /* Properties */

    private boolean initialized;


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
        init();
    }

    private void init() {
        golbal();
        initHeader();
        initFooter();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this);
    }

    private void initHeader() {
        header = new Composite(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        CompositeSet.decorate(header).setBackground(MT.COLOR_WHITE_SMOKE);
        FormLayoutSet.layout(header).marginWidth(10).marginHeight(10).spacing(0);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(header).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        final ImageButton backButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_BACK, MT.IMAGE_SYSTEM_BACK_HOVER);
        FormDataSet.attach(backButton).atLeft().atTop();
        backButton.addMouseListener(new BackButtonMouseAdapter());

        generalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_GENERAL, MT.IMAGE_SYSTEM_STEP_GENERAL_HOVER, MT.IMAGE_SYSTEM_STEP_GENERAL_DISABLED);
        FormDataSet.attach(generalButton).fromLeft(50, -STEP_BUTTON_WIDTH * 3).atTopTo(backButton, 2, SWT.TOP);
        generalButton.addMouseListener(new GeneralButtonMouseAdapter());

        readingButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_READING, MT.IMAGE_SYSTEM_STEP_READING_HOVER, MT.IMAGE_SYSTEM_STEP_READING_DISABLED);
        FormDataSet.attach(readingButton).fromLeft(50, -STEP_BUTTON_WIDTH * 2).atTopTo(generalButton, 0, SWT.TOP);
        readingButton.setEnabled(false);
        readingButton.addMouseListener(new ReadingButtonMouseAdapter());

        listeningButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_LISTENING, MT.IMAGE_SYSTEM_STEP_LISTENING_HOVER, MT.IMAGE_SYSTEM_STEP_LISTENING_DISABLED);
        FormDataSet.attach(listeningButton).fromLeft(50, -STEP_BUTTON_WIDTH).atTopTo(generalButton, 0, SWT.TOP);
        listeningButton.setEnabled(false);
        listeningButton.addMouseListener(new ListeningButtonMouseAdapter());

        speakingButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_SPEAKING, MT.IMAGE_SYSTEM_STEP_SPEAKING_HOVER, MT.IMAGE_SYSTEM_STEP_SPEAKING_DISABLED);
        FormDataSet.attach(speakingButton).fromLeft(50).atTopTo(generalButton, 0, SWT.TOP);
        speakingButton.setEnabled(false);
        speakingButton.addMouseListener(new SpeakingButtonMouseAdapter());

        writingButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_WRITING, MT.IMAGE_SYSTEM_STEP_WRITING_HOVER, MT.IMAGE_SYSTEM_STEP_WRITING_DISABLED);
        FormDataSet.attach(writingButton).fromLeft(50, STEP_BUTTON_WIDTH).atTopTo(generalButton, 0, SWT.TOP);
        writingButton.setEnabled(false);
        writingButton.addMouseListener(new WritingButtonMouseAdapter());

        previewButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_PREVIEW, MT.IMAGE_SYSTEM_STEP_PREVIEW_HOVER, MT.IMAGE_SYSTEM_STEP_PREVIEW_DISABLED);
        FormDataSet.attach(previewButton).fromLeft(50, STEP_BUTTON_WIDTH * 2).atTopTo(generalButton, 0, SWT.TOP);
        previewButton.setEnabled(false);
        previewButton.addMouseListener(new PreviewButtonMouseAdapter());
    }

    private void initFooter() {
        footer = new Composite(this, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atRight().atBottom();
        CompositeSet.decorate(footer).setBackground(MT.COLOR_WHITE_SMOKE);
        FormLayoutSet.layout(footer).marginWidth(10).marginHeight(10).spacing(10);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atRight().atBottomTo(footer, 0, SWT.TOP).withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        exportAsZipButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_EXPORT_AS_ZIP, MT.IMAGE_SYSTEM_EXPORT_AS_ZIP_HOVER, MT.IMAGE_SYSTEM_EXPORT_AS_ZIP_DISABLED);
        FormDataSet.attach(exportAsZipButton).atTop().atRight();
        exportAsZipButton.setEnabled(false);
        exportAsZipButton.addMouseListener(new ExportAsZipButtonMouseAdapter());

        saveButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_SAVE, MT.IMAGE_SYSTEM_SAVE_HOVER);
        FormDataSet.attach(saveButton).atTopTo(exportAsZipButton, 0, SWT.TOP).atRightTo(exportAsZipButton);
        saveButton.addMouseListener(new SaveButtonMouseAdapter());

        titleLabel = new CLabel(footer, SWT.NONE);
        FormDataSet.attach(titleLabel).atTopTo(exportAsZipButton, 0, SWT.TOP).atRightTo(saveButton, 10).atBottomTo(exportAsZipButton, 0, SWT.BOTTOM);
        CLabelSet.decorate(titleLabel).setAlignment(SWT.CENTER).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("untitled") + MT.STRING_SPACE + MT.STRING_STAR);
    }

    private void initBody() {
        body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(header).atRight().atBottomTo(footer);
        stack = new StackLayout();
        body.setLayout(stack);
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
        generalButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_GENERAL_CHECKED, MT.IMAGE_SYSTEM_STEP_GENERAL_CHECKED);
        if (initialized) {
            readingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_READING, MT.IMAGE_SYSTEM_STEP_READING_HOVER);
            listeningButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_LISTENING, MT.IMAGE_SYSTEM_STEP_LISTENING_HOVER);
            speakingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_SPEAKING, MT.IMAGE_SYSTEM_STEP_SPEAKING_HOVER);
            writingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_WRITING, MT.IMAGE_SYSTEM_STEP_WRITING_HOVER);
            previewButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_PREVIEW, MT.IMAGE_SYSTEM_STEP_PREVIEW_HOVER);
        }

        if (generalPaperView == null) {
            generalPaperView = new GeneralPaperView(body, SWT.NONE);
        }
        stack.topControl = generalPaperView;
        body.layout();
    }

    public void toReadingPaperView() {
        generalButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_GENERAL, MT.IMAGE_SYSTEM_STEP_GENERAL_HOVER);
        readingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_READING_CHECKED, MT.IMAGE_SYSTEM_STEP_READING_CHECKED);
        listeningButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_LISTENING, MT.IMAGE_SYSTEM_STEP_LISTENING_HOVER);
        speakingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_SPEAKING, MT.IMAGE_SYSTEM_STEP_SPEAKING_HOVER);
        writingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_WRITING, MT.IMAGE_SYSTEM_STEP_WRITING_HOVER);
        previewButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_PREVIEW, MT.IMAGE_SYSTEM_STEP_PREVIEW_HOVER);

        if (readingPaperView == null) {
            readingPaperView = new ReadingPaperView(body, SWT.NONE);
        }
        stack.topControl = readingPaperView;
        body.layout();
    }

    public void toListeningPaperView() {
        generalButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_GENERAL, MT.IMAGE_SYSTEM_STEP_GENERAL_HOVER);
        readingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_READING, MT.IMAGE_SYSTEM_STEP_READING_HOVER);
        listeningButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_LISTENING_CHECKED, MT.IMAGE_SYSTEM_STEP_LISTENING_CHECKED);
        speakingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_SPEAKING, MT.IMAGE_SYSTEM_STEP_SPEAKING_HOVER);
        writingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_WRITING, MT.IMAGE_SYSTEM_STEP_WRITING_HOVER);
        previewButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_PREVIEW, MT.IMAGE_SYSTEM_STEP_PREVIEW_HOVER);

        if (listeningPaperView == null) {
            listeningPaperView = new ListeningPaperView(body, SWT.NONE);
        }
        stack.topControl = listeningPaperView;
        body.layout();
    }

    public void toSpeakingPaperView() {
        generalButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_GENERAL, MT.IMAGE_SYSTEM_STEP_GENERAL_HOVER);
        readingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_READING, MT.IMAGE_SYSTEM_STEP_READING_HOVER);
        listeningButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_LISTENING, MT.IMAGE_SYSTEM_STEP_LISTENING_HOVER);
        speakingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_SPEAKING_CHECKED, MT.IMAGE_SYSTEM_STEP_SPEAKING_CHECKED);
        writingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_WRITING, MT.IMAGE_SYSTEM_STEP_WRITING_HOVER);
        previewButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_PREVIEW, MT.IMAGE_SYSTEM_STEP_PREVIEW_HOVER);

        if (speakingPaperView == null) {
            speakingPaperView = new SpeakingPaperView(body, SWT.NONE);
        }
        stack.topControl = speakingPaperView;
        body.layout();
    }

    public void toWritingPaperView() {
        generalButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_GENERAL, MT.IMAGE_SYSTEM_STEP_GENERAL_HOVER);
        readingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_READING, MT.IMAGE_SYSTEM_STEP_READING_HOVER);
        listeningButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_LISTENING, MT.IMAGE_SYSTEM_STEP_LISTENING_HOVER);
        speakingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_SPEAKING, MT.IMAGE_SYSTEM_STEP_SPEAKING_HOVER);
        writingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_WRITING_CHECKED, MT.IMAGE_SYSTEM_STEP_WRITING_CHECKED);
        previewButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_PREVIEW, MT.IMAGE_SYSTEM_STEP_PREVIEW_HOVER);

        if (writingPaperView == null) {
            writingPaperView = new WritingPaperView(body, SWT.NONE);
        }
        stack.topControl = writingPaperView;
        body.layout();
    }

    public void toPreviewPaperView() {
        generalButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_GENERAL, MT.IMAGE_SYSTEM_STEP_GENERAL_HOVER);
        readingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_READING, MT.IMAGE_SYSTEM_STEP_READING_HOVER);
        listeningButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_LISTENING, MT.IMAGE_SYSTEM_STEP_LISTENING_HOVER);
        speakingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_SPEAKING, MT.IMAGE_SYSTEM_STEP_SPEAKING_HOVER);
        writingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_WRITING, MT.IMAGE_SYSTEM_STEP_WRITING_HOVER);
        previewButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_PREVIEW_CHECKED, MT.IMAGE_SYSTEM_STEP_PREVIEW_CHECKED);

        if (previewPaperView == null) {
            previewPaperView = new PreviewPaperView(body, SWT.NONE);
        }
        stack.topControl = previewPaperView;
        body.layout();
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class BackButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            MyApplication.get().getWindow().toMainPageAndToTestPapersView();
        }
    }

    private class ExportAsZipButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            FileDialog dialog = new FileDialog(MyApplication.get().getWindow().getShell(), SWT.SAVE);
            dialog.setFilterNames(new String[]{"PDF File (*.pdf)"});
            dialog.setFilterExtensions(new String[]{"*.pdf"});
            dialog.setFileName("xx.pdf");
            boolean done = false;
            while (!done) {
                String absoluteFileName = dialog.open();
                if (!StringUtils.isEmpty(absoluteFileName)) {
                    File file = new File(absoluteFileName);
                    if (file.exists()) {
                        MessageBox box = new MessageBox(dialog.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
                        box.setText(msgs.getString("file_exists"));
                        box.setMessage("\"xx.pdf\" " + msgs.getString("exists_and_replace"));
                        int response = box.open();
                        if (response == SWT.YES) {
                            PDFUtils.save(absoluteFileName);
                            done = true;
                        }
                    } else {
                        PDFUtils.save(absoluteFileName);
                        done = true;
                    }
                } else {
                    done = true;
                }
            }
        }
    }

    private class SaveButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            initialized = true;
            readingButton.setEnabled(true);
            listeningButton.setEnabled(true);
            speakingButton.setEnabled(true);
            writingButton.setEnabled(true);
            previewButton.setEnabled(true);
            exportAsZipButton.setEnabled(true);
        }
    }

    private class GeneralButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            toGeneralPaperView();
        }
    }

    private class ReadingButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            toReadingPaperView();
        }
    }

    private class ListeningButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            toListeningPaperView();
        }
    }

    private class SpeakingButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            toSpeakingPaperView();
        }
    }

    private class WritingButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            toWritingPaperView();
        }
    }

    private class PreviewButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            toPreviewPaperView();
        }
    }
}
