package com.mocktpo.modules.editor.layers;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.modules.system.widgets.ImageButton;
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
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.util.ResourceBundle;

public abstract class TestEditorLayer extends Composite {

    /* Constants */

    private static final int STEP_BUTTON_WIDTH = 120;
    private static final int TITLE_WIDTH = 360;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Page */

    protected TestEditorPage page;

    /* Widgets */

    protected Composite header;
    protected ImageButton backButton;
    protected ImageButton generalButton;
    protected ImageButton readingButton;
    protected ImageButton listeningButton;
    protected ImageButton speakingButton;
    protected ImageButton writingButton;
    protected ImageButton previewButton;

    protected Composite footer;
    protected ImageButton exportAsZipButton;
    protected ImageButton saveButton;
    protected CLabel titleLabel;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestEditorLayer(TestEditorPage page, int style) {
        super(page, style);
        this.d = page.getDisplay();
        this.page = page;
        init();
    }

    private void init() {
        golbal();
        initHeader();
        initFooter();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this).marginWidth(0).marginHeight(0).spacing(0);
    }

    /*
     * ==================================================
     *
     * Header Initialization
     *
     * ==================================================
     */

    private void initHeader() {

        /*
         * ==================================================
         *
         * Header Composite
         *
         * ==================================================
         */

        header = new Composite(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        CompositeSet.decorate(header).setBackground(MT.COLOR_WHITE_SMOKE);
        FormLayoutSet.layout(header).marginWidth(10).marginHeight(10).spacing(0);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(header).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        /*
         * ==================================================
         *
         * Back Button
         *
         * ==================================================
         */

        backButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_BACK, MT.IMAGE_SYSTEM_BACK_HOVER, MT.IMAGE_SYSTEM_BACK_DISABLED);
        FormDataSet.attach(backButton).atLeft().atTop();
        backButton.addMouseListener(new BackButtonMouseAdapter());

        /*
         * ==================================================
         *
         * Button Group
         *
         * ==================================================
         */

        generalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_GENERAL, MT.IMAGE_SYSTEM_STEP_GENERAL_HOVER, MT.IMAGE_SYSTEM_STEP_GENERAL_DISABLED);
        FormDataSet.attach(generalButton).fromLeft(50, -STEP_BUTTON_WIDTH * 3).atTop(2);
        generalButton.addMouseListener(new GeneralButtonMouseAdapter());

        readingButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_READING, MT.IMAGE_SYSTEM_STEP_READING_HOVER, MT.IMAGE_SYSTEM_STEP_READING_DISABLED);
        FormDataSet.attach(readingButton).fromLeft(50, -STEP_BUTTON_WIDTH * 2).atTopTo(generalButton, 0, SWT.TOP);
        readingButton.addMouseListener(new ReadingButtonMouseAdapter());

        listeningButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_LISTENING, MT.IMAGE_SYSTEM_STEP_LISTENING_HOVER, MT.IMAGE_SYSTEM_STEP_LISTENING_DISABLED);
        FormDataSet.attach(listeningButton).fromLeft(50, -STEP_BUTTON_WIDTH).atTopTo(generalButton, 0, SWT.TOP);
        listeningButton.addMouseListener(new ListeningButtonMouseAdapter());

        speakingButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_SPEAKING, MT.IMAGE_SYSTEM_STEP_SPEAKING_HOVER, MT.IMAGE_SYSTEM_STEP_SPEAKING_DISABLED);
        FormDataSet.attach(speakingButton).fromLeft(50).atTopTo(generalButton, 0, SWT.TOP);
        speakingButton.addMouseListener(new SpeakingButtonMouseAdapter());

        writingButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_WRITING, MT.IMAGE_SYSTEM_STEP_WRITING_HOVER, MT.IMAGE_SYSTEM_STEP_WRITING_DISABLED);
        FormDataSet.attach(writingButton).fromLeft(50, STEP_BUTTON_WIDTH).atTopTo(generalButton, 0, SWT.TOP);
        writingButton.addMouseListener(new WritingButtonMouseAdapter());

        previewButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_PREVIEW, MT.IMAGE_SYSTEM_STEP_PREVIEW_HOVER, MT.IMAGE_SYSTEM_STEP_PREVIEW_DISABLED);
        FormDataSet.attach(previewButton).fromLeft(50, STEP_BUTTON_WIDTH * 2).atTopTo(generalButton, 0, SWT.TOP);
        previewButton.addMouseListener(new PreviewButtonMouseAdapter());

        /*
         * ==================================================
         *
         * Header Updates
         *
         * ==================================================
         */

        updateHeader();
    }

    /*
     * ==================================================
     *
     * Footer Initialization
     *
     * ==================================================
     */

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
        exportAsZipButton.addMouseListener(new ExportAsZipButtonMouseAdapter());

        saveButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_SAVE, MT.IMAGE_SYSTEM_SAVE_HOVER, MT.IMAGE_SYSTEM_SAVE_DISABLED);
        FormDataSet.attach(saveButton).atTopTo(exportAsZipButton, 0, SWT.TOP).atRightTo(exportAsZipButton);
        saveButton.addMouseListener(new SaveButtonMouseAdapter());

        titleLabel = new CLabel(footer, SWT.NONE);
        FormDataSet.attach(titleLabel).atTopTo(exportAsZipButton, 0, SWT.TOP).atRightTo(saveButton, 10).atBottomTo(exportAsZipButton, 0, SWT.BOTTOM).withWidth(TITLE_WIDTH);
        CLabelSet.decorate(titleLabel).setAlignment(SWT.RIGHT).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_GRAY40);
        updateTitleLabel();

        /*
         * ==================================================
         *
         * Footer Updates
         *
         * ==================================================
         */

        updateFooter();
    }

    public void updateTitleLabel() {
        String title = page.getTestEditorVo().getTitle();
        if (StringUtils.isEmpty(title)) {
            title = msgs.getString("untitled");
        }
        if (page.isUnsaved()) {
            title = title + MT.STRING_SPACE + MT.STRING_STAR;
            CLabelSet.decorate(titleLabel).setForeground(MT.COLOR_PINK).setText(title);
        } else {
            CLabelSet.decorate(titleLabel).setForeground(MT.COLOR_GRAY40).setText(title);
        }
    }

    /*
     * ==================================================
     *
     * Body Initialization
     *
     * ==================================================
     */

    protected abstract void initBody();

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    protected abstract void updateHeader();

    protected abstract void updateFooter();

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
            if (page.isUnsaved()) {
                MessageBox box = new MessageBox(MyApplication.get().getWindow().getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
                box.setText(msgs.getString("save"));
                box.setMessage(msgs.getString("save_or_not_before_leaving"));
                int response = box.open();
                if (response == SWT.YES) {
                    page.save();
                    MyApplication.get().getWindow().toMainPageAndToTestPapersView();
                }
            } else {
                MyApplication.get().getWindow().toMainPageAndToTestPapersView();
            }
        }
    }

    private class GeneralButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            page.toGeneralEditorLayer();
        }
    }

    private class ReadingButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            page.toReadingEditorLayer();
        }
    }

    private class ListeningButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            page.toListeningEditorLayer();
        }
    }

    private class SpeakingButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            page.toSpeakingEditorLayer();
        }
    }

    private class WritingButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            page.toWritingEditorLayer();
        }
    }

    private class PreviewButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            page.toPreviewEditorLayer();
        }
    }

    private class ExportAsZipButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            if (page.isUnsaved()) {
                MessageBox box = new MessageBox(MyApplication.get().getWindow().getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
                box.setText(msgs.getString("save"));
                box.setMessage(msgs.getString("save_or_not_before_exporting"));
                int response = box.open();
                if (response == SWT.YES) {
                    page.save();
                } else {
                    return;
                }
            }
            FileDialog dialog = new FileDialog(MyApplication.get().getWindow().getShell(), SWT.SAVE);
            dialog.setText(msgs.getString("export"));
            dialog.setFilterNames(new String[]{"Zip Archive (*.zip)"});
            dialog.setFilterExtensions(new String[]{"*.zip"});
            dialog.setFileName(titleLabel.getText() + ".zip");
            boolean done = false;
            while (!done) {
                String fullDestZipFileName = dialog.open();
                if (!StringUtils.isEmpty(fullDestZipFileName)) {
                    File file = new File(fullDestZipFileName);
                    if (file.exists()) {
                        MessageBox box = new MessageBox(dialog.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
                        box.setText(msgs.getString("file_exists"));
                        box.setMessage("\"" + titleLabel.getText() + ".zip\" " + msgs.getString("replace_or_not"));
                        int response = box.open();
                        if (response == SWT.YES) {
                            page.export(fullDestZipFileName);
                            done = true;
                        }
                    } else {
                        page.export(fullDestZipFileName);
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
            page.save();
            enableControlButtons();
        }
    }

    public void enableControlButtons() {
        if (!page.isFirstRun()) {
            readingButton.setEnabled(true);
            listeningButton.setEnabled(true);
            speakingButton.setEnabled(true);
            writingButton.setEnabled(true);
            previewButton.setEnabled(true);
            exportAsZipButton.setEnabled(true);
        }
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public TestEditorPage getTestEditorPage() {
        return page;
    }

    public void setTestEditorPage(TestEditorPage page) {
        this.page = page;
    }
}
