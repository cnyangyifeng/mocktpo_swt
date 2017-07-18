package com.mocktpo.modules.report;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.report.views.ListeningReportView;
import com.mocktpo.modules.report.views.ReadingReportView;
import com.mocktpo.modules.report.views.SpeakingReportView;
import com.mocktpo.modules.report.views.WritingReportView;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.PDFUtils;
import com.mocktpo.util.TimeUtils;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.vo.TestSchemaVo;
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

public class ReportPage extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Stack */

    private StackLayout stack;

    /* Widgets */

    private Composite toolBar;
    private CLabel readingLabel;
    private CLabel listeningLabel;
    private CLabel speakingLabel;
    private CLabel writingLabel;

    private Composite body;
    private ReadingReportView readingReportView;
    private ListeningReportView listeningReportView;
    private SpeakingReportView speakingReportView;
    private WritingReportView writingReportView;

    /* Properties */

    private TestSchemaVo testSchema;
    private UserTestSession userTestSession;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReportPage(Composite parent, int style, UserTestSession userTestSession) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.userTestSession = userTestSession;
        this.testSchema = ConfigUtils.load(this.userTestSession.getFileAlias(), TestSchemaVo.class);
        init();
    }

    private void init() {
        golbal();
        initToolBar();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this);
    }

    private void initToolBar() {
        toolBar = new Composite(this, SWT.NONE);
        FormDataSet.attach(toolBar).atLeft().atTop().atRight();
        CompositeSet.decorate(toolBar).setBackground(MT.COLOR_WHITE_SMOKE);
        FormLayoutSet.layout(toolBar).marginWidth(10).marginHeight(10).spacing(5);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(toolBar).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        final ImageButton backButton = new ImageButton(toolBar, SWT.NONE, MT.IMAGE_SYSTEM_BACK, MT.IMAGE_SYSTEM_BACK_HOVER);
        FormDataSet.attach(backButton).atLeft().atTop();
        backButton.addMouseListener(new BackButtonMouseAdapter());

        final CLabel titleLabel = new CLabel(toolBar, SWT.NONE);
        FormDataSet.attach(titleLabel).fromLeft(50, -LC.REPORT_TITLE_WIDTH / 2).atTopTo(backButton, 0, SWT.TOP).atBottomTo(backButton, 0, SWT.BOTTOM).withWidth(LC.REPORT_TITLE_WIDTH);
        CLabelSet.decorate(titleLabel).setAlignment(SWT.CENTER).setFont(MT.FONT_LARGE_BOLD).setForeground(MT.COLOR_GRAY20).setText(testSchema.getTitle() + MT.STRING_SPACE + msgs.getString("score_report"));

        final ImageButton exportButton = new ImageButton(toolBar, SWT.NONE, MT.IMAGE_SYSTEM_EXPORT, MT.IMAGE_SYSTEM_EXPORT_HOVER);
        FormDataSet.attach(exportButton).atTop().atRight();
        exportButton.addMouseListener(new ExportButtonMouseAdapter());

        final CLabel startTimeLabel = new CLabel(toolBar, SWT.NONE);
        FormDataSet.attach(startTimeLabel).atTopTo(backButton, 0, SWT.TOP).atRightTo(exportButton, 20).atBottomTo(backButton, 0, SWT.BOTTOM);
        CLabelSet.decorate(startTimeLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(TimeUtils.displayClockTime(userTestSession.getStartTime()));

        final CLabel startTimePreLabel = new CLabel(toolBar, SWT.NONE);
        FormDataSet.attach(startTimePreLabel).atTopTo(backButton, 0, SWT.TOP).atRightTo(startTimeLabel).atBottomTo(backButton, 0, SWT.BOTTOM);
        CLabelSet.decorate(startTimePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("start_time"));

        readingLabel = new CLabel(toolBar, SWT.NONE);
        FormDataSet.attach(readingLabel).fromLeft(50, -LC.REPORT_SECTION_LABEL_WIDTH * 2).atTopTo(titleLabel, 10).withWidth(LC.REPORT_SECTION_LABEL_WIDTH).withHeight(LC.REPORT_SECTION_LABEL_HEIGHT);
        CLabelSet.decorate(readingLabel).setAlignment(SWT.CENTER).setBackground(MT.COLOR_DARK_BLUE).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_WHITE).setText(msgs.getString("reading"));
        readingLabel.addMouseListener(new SectionTabItemMouseAdapter());

        listeningLabel = new CLabel(toolBar, SWT.NONE);
        FormDataSet.attach(listeningLabel).fromLeft(50, -LC.REPORT_SECTION_LABEL_WIDTH).atTopTo(readingLabel, 0, SWT.TOP).withWidth(LC.REPORT_SECTION_LABEL_WIDTH).withHeight(LC.REPORT_SECTION_LABEL_HEIGHT);
        CLabelSet.decorate(listeningLabel).setAlignment(SWT.CENTER).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("listening"));
        listeningLabel.addMouseListener(new SectionTabItemMouseAdapter());

        speakingLabel = new CLabel(toolBar, SWT.NONE);
        FormDataSet.attach(speakingLabel).fromLeft(50).atTopTo(readingLabel, 0, SWT.TOP).withWidth(LC.REPORT_SECTION_LABEL_WIDTH).withHeight(LC.REPORT_SECTION_LABEL_HEIGHT);
        CLabelSet.decorate(speakingLabel).setAlignment(SWT.CENTER).setBackground(MT.COLOR_GRAY60).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("speaking"));
        speakingLabel.addMouseListener(new SectionTabItemMouseAdapter());

        writingLabel = new CLabel(toolBar, SWT.NONE);
        FormDataSet.attach(writingLabel).fromLeft(50, LC.REPORT_SECTION_LABEL_WIDTH).atTopTo(readingLabel, 0, SWT.TOP).withWidth(LC.REPORT_SECTION_LABEL_WIDTH).withHeight(LC.REPORT_SECTION_LABEL_HEIGHT);
        CLabelSet.decorate(writingLabel).setAlignment(SWT.CENTER).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("writing"));
        writingLabel.addMouseListener(new SectionTabItemMouseAdapter());
    }

    private void initBody() {
        body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(toolBar).atRight().atBottom();
        stack = new StackLayout();
        body.setLayout(stack);
        toReadingReportView();
    }


    /*
     * ==================================================
     *
     * Page Controls
     *
     * ==================================================
     */

    public void toReadingReportView() {
        if (readingReportView == null) {
            readingReportView = new ReadingReportView(body, SWT.NONE);
        }
        CLabelSet.decorate(readingLabel).setBackground(MT.COLOR_DARK_BLUE).setForeground(MT.COLOR_WHITE);
        CLabelSet.decorate(listeningLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(speakingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(writingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        readingReportView.refreshRows();
        stack.topControl = readingReportView;
        body.layout();
    }

    public void toListeningReportView() {
        if (listeningReportView == null) {
            listeningReportView = new ListeningReportView(body, SWT.NONE);
        }
        CLabelSet.decorate(readingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(listeningLabel).setBackground(MT.COLOR_DARK_BLUE).setForeground(MT.COLOR_WHITE);
        CLabelSet.decorate(speakingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(writingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        listeningReportView.refreshRows();
        stack.topControl = listeningReportView;
        body.layout();
    }

    public void toSpeakingReportView() {
        if (speakingReportView == null) {
            speakingReportView = new SpeakingReportView(body, SWT.NONE);
        }
        CLabelSet.decorate(readingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(listeningLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(speakingLabel).setBackground(MT.COLOR_DARK_BLUE).setForeground(MT.COLOR_WHITE);
        CLabelSet.decorate(writingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        speakingReportView.refreshRows();
        stack.topControl = speakingReportView;
        body.layout();
    }

    public void toWritingReportView() {
        if (writingReportView == null) {
            writingReportView = new WritingReportView(body, SWT.NONE);
        }
        CLabelSet.decorate(readingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(listeningLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(speakingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(writingLabel).setBackground(MT.COLOR_DARK_BLUE).setForeground(MT.COLOR_WHITE);
        writingReportView.refreshRows();
        stack.topControl = writingReportView;
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
            MyApplication.get().getWindow().toMainPageAndToTestRecordsView();
        }
    }

    private class ExportButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            FileDialog dialog = new FileDialog(MyApplication.get().getWindow().getShell(), SWT.SAVE);
            dialog.setFilterNames(new String[]{"PDF File (*.pdf)"});
            dialog.setFilterExtensions(new String[]{"*.pdf"});
            dialog.setFileName(userTestSession.getFileAlias() + ".pdf");
            boolean done = false;
            while (!done) {
                String absoluteFileName = dialog.open();
                if (!StringUtils.isEmpty(absoluteFileName)) {
                    File file = new File(absoluteFileName);
                    if (file.exists()) {
                        MessageBox box = new MessageBox(dialog.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
                        box.setText(msgs.getString("file_exists"));
                        box.setMessage("\"" + userTestSession.getFileAlias() + ".pdf\" " + msgs.getString("exists_and_replace"));
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

    private class SectionTabItemMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            String text = ((CLabel) e.widget).getText();
            if (msgs.getString("reading").equals(text)) {
                toReadingReportView();
            } else if (msgs.getString("listening").equals(text)) {
                toListeningReportView();
            } else if (msgs.getString("speaking").equals(text)) {
                toSpeakingReportView();
            } else if (msgs.getString("writing").equals(text)) {
                toWritingReportView();
            }
        }
    }
}
