package com.mocktpo.modules.editor;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.util.PDFUtils;
import com.mocktpo.util.constants.LC;
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

public class TestEditorPage extends Composite {

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

    private Composite footer;

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
        FormLayoutSet.layout(header).marginWidth(10).marginHeight(10).spacing(5);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(header).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        final ImageButton backButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_BACK, MT.IMAGE_SYSTEM_BACK_HOVER);
        FormDataSet.attach(backButton).atLeft().atTop();
        backButton.addMouseListener(new BackButtonMouseAdapter());

        final CLabel titleLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(titleLabel).fromLeft(50, -LC.REPORT_TITLE_WIDTH / 2).atTopTo(backButton, 0, SWT.TOP).atBottomTo(backButton, 0, SWT.BOTTOM).withWidth(LC.REPORT_TITLE_WIDTH);
        CLabelSet.decorate(titleLabel).setAlignment(SWT.CENTER).setFont(MT.FONT_LARGE_BOLD).setForeground(MT.COLOR_GRAY20).setText(msgs.getString("test_papers"));

        final CLabel endTimeLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(endTimeLabel).atTopTo(backButton, 0, SWT.TOP).atRight().atBottomTo(backButton, 0, SWT.BOTTOM);
        CLabelSet.decorate(endTimeLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText("0000-00-00 00:00:00");

        final CLabel endTimePreLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(endTimePreLabel).atTopTo(backButton, 0, SWT.TOP).atRightTo(endTimeLabel).atBottomTo(backButton, 0, SWT.BOTTOM);
        CLabelSet.decorate(endTimePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("end_time"));

        final CLabel startTimeLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(startTimeLabel).atTopTo(backButton, 0, SWT.TOP).atRightTo(endTimePreLabel, 20).atBottomTo(backButton, 0, SWT.BOTTOM);
        CLabelSet.decorate(startTimeLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText("0000-00-00 00:00:00");

        final CLabel startTimePreLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(startTimePreLabel).atTopTo(backButton, 0, SWT.TOP).atRightTo(startTimeLabel).atBottomTo(backButton, 0, SWT.BOTTOM);
        CLabelSet.decorate(startTimePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("start_time"));

        generalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_GENERAL, MT.IMAGE_SYSTEM_STEP_GENERAL_HOVER);
        FormDataSet.attach(generalButton).fromLeft(50, -LC.REPORT_STEP_BUTTON_WIDTH * 3).atTopTo(titleLabel, 10);
        generalButton.addMouseListener(new StepButtonMouseAdapter());

        readingButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_READING, MT.IMAGE_SYSTEM_STEP_READING_HOVER);
        FormDataSet.attach(readingButton).fromLeft(50, -LC.REPORT_STEP_BUTTON_WIDTH * 2).atTopTo(generalButton, 0, SWT.TOP);
        readingButton.addMouseListener(new StepButtonMouseAdapter());

        listeningButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_LISTENING, MT.IMAGE_SYSTEM_STEP_LISTENING_HOVER);
        FormDataSet.attach(listeningButton).fromLeft(50, -LC.REPORT_STEP_BUTTON_WIDTH).atTopTo(generalButton, 0, SWT.TOP);
        listeningButton.addMouseListener(new StepButtonMouseAdapter());

        speakingButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_SPEAKING, MT.IMAGE_SYSTEM_STEP_SPEAKING_HOVER);
        FormDataSet.attach(speakingButton).fromLeft(50).atTopTo(generalButton, 0, SWT.TOP);
        speakingButton.addMouseListener(new StepButtonMouseAdapter());

        writingButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_WRITING, MT.IMAGE_SYSTEM_STEP_WRITING_HOVER);
        FormDataSet.attach(writingButton).fromLeft(50, LC.REPORT_STEP_BUTTON_WIDTH).atTopTo(generalButton, 0, SWT.TOP);
        writingButton.addMouseListener(new StepButtonMouseAdapter());

        previewButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_STEP_PREVIEW, MT.IMAGE_SYSTEM_STEP_PREVIEW_HOVER);
        FormDataSet.attach(previewButton).fromLeft(50, LC.REPORT_STEP_BUTTON_WIDTH * 2).atTopTo(generalButton, 0, SWT.TOP);
        previewButton.addMouseListener(new StepButtonMouseAdapter());
    }

    private void initFooter() {
        footer = new Composite(this, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atRight().atBottom();
        CompositeSet.decorate(footer).setBackground(MT.COLOR_WHITE_SMOKE);
        FormLayoutSet.layout(footer).marginWidth(10).marginHeight(10).spacing(5);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atRight().atBottomTo(footer, 0, SWT.TOP).withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        final ImageButton backButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_BACK, MT.IMAGE_SYSTEM_BACK_HOVER);
        FormDataSet.attach(backButton).atLeft().atTop();
        backButton.addMouseListener(new BackButtonMouseAdapter());

        final CLabel titleLabel = new CLabel(footer, SWT.NONE);
        FormDataSet.attach(titleLabel).fromLeft(50, -LC.REPORT_TITLE_WIDTH / 2).atTopTo(backButton, 0, SWT.TOP).atBottomTo(backButton, 0, SWT.BOTTOM).withWidth(LC.REPORT_TITLE_WIDTH);
        CLabelSet.decorate(titleLabel).setAlignment(SWT.CENTER).setFont(MT.FONT_LARGE_BOLD).setForeground(MT.COLOR_GRAY20).setText(msgs.getString("test_papers"));

        final ImageButton exportAsZipButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_EXPORT_AS_ZIP, MT.IMAGE_SYSTEM_EXPORT_AS_ZIP_HOVER);
        FormDataSet.attach(exportAsZipButton).atTop().atRight();
        exportAsZipButton.addMouseListener(new ExportAsZipButtonMouseAdapter());
    }

    private void initBody() {
        body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(header).atRight().atBottomTo(footer);
        stack = new StackLayout();
        body.setLayout(stack);
        toGeneralReportView();
    }

    /*
     * ==================================================
     *
     * Page Controls
     *
     * ==================================================
     */

    public void toGeneralReportView() {
    }

    public void toReadingReportView() {
    }

    public void toListeningReportView() {
    }

    public void toSpeakingReportView() {
    }

    public void toWritingReportView() {
    }

    public void toPreviewReportView() {
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

    private class StepButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
        }
    }
}
