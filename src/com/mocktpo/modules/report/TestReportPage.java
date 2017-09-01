package com.mocktpo.modules.report;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.report.views.ListeningReportView;
import com.mocktpo.modules.report.views.ReadingReportView;
import com.mocktpo.modules.report.views.SpeakingReportView;
import com.mocktpo.modules.report.views.WritingReportView;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.JSONUtils;
import com.mocktpo.util.ExportUtils;
import com.mocktpo.util.TimeUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.vo.TestVo;
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

public class TestReportPage extends Composite {

    /* Constants */

    private static final int TITLE_WIDTH = 300;
    private static final int SECTION_LABEL_WIDTH = 90;
    private static final int SECTION_LABEL_HEIGHT = 30;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Stack */

    private StackLayout stack;

    /* Widgets */

    private Composite header;
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

    private TestVo testVo;
    private UserTestSession userTestSession;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestReportPage(Composite parent, int style, UserTestSession userTestSession) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.userTestSession = userTestSession;
        this.testVo = JSONUtils.pullFromTests(this.userTestSession.getFileAlias(), TestVo.class);
        init();
    }

    private void init() {
        golbal();
        initHeader();
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
        header = new Composite(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        CompositeSet.decorate(header).setBackground(MT.COLOR_WHITE_SMOKE);
        FormLayoutSet.layout(header).marginWidth(10).marginHeight(10).spacing(10);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(header).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        final ImageButton backButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_BACK, MT.IMAGE_SYSTEM_BACK_HOVER);
        FormDataSet.attach(backButton).atLeft().atTop();
        backButton.addMouseListener(new BackButtonMouseAdapter());

        final CLabel titleLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(titleLabel).fromLeft(50, -TITLE_WIDTH / 2).atTopTo(backButton, 0, SWT.TOP).atBottomTo(backButton, 0, SWT.BOTTOM).withWidth(TITLE_WIDTH);
        CLabelSet.decorate(titleLabel).setAlignment(SWT.CENTER).setFont(MT.FONT_LARGE_BOLD).setForeground(MT.COLOR_BLACK).setText(testVo.getTitle() + MT.STRING_SPACE + msgs.getString("score_report"));

        final ImageButton exportAsPdfButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_EXPORT_AS_PDF, MT.IMAGE_SYSTEM_EXPORT_AS_PDF_HOVER);
        FormDataSet.attach(exportAsPdfButton).atTop().atRight();
        exportAsPdfButton.addMouseListener(new ExportAsPdfButtonMouseAdapter());

        final CLabel startTimeLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(startTimeLabel).atTopTo(backButton, 0, SWT.TOP).atRightTo(exportAsPdfButton, 20).atBottomTo(backButton, 0, SWT.BOTTOM);
        CLabelSet.decorate(startTimeLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(TimeUtils.displayClockTime(userTestSession.getStartTime()));

        final CLabel startTimePreLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(startTimePreLabel).atTopTo(backButton, 0, SWT.TOP).atRightTo(startTimeLabel).atBottomTo(backButton, 0, SWT.BOTTOM);
        CLabelSet.decorate(startTimePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("start_time"));

        readingLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(readingLabel).fromLeft(50, -SECTION_LABEL_WIDTH * 2).atTopTo(titleLabel, 10).withWidth(SECTION_LABEL_WIDTH).withHeight(SECTION_LABEL_HEIGHT);
        CLabelSet.decorate(readingLabel).setAlignment(SWT.CENTER).setFont(MT.FONT_SMALL).setText(msgs.getString("reading"));
        readingLabel.addMouseListener(new SectionTabItemMouseAdapter());

        listeningLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(listeningLabel).fromLeft(50, -SECTION_LABEL_WIDTH).atTopTo(readingLabel, 0, SWT.TOP).withWidth(SECTION_LABEL_WIDTH).withHeight(SECTION_LABEL_HEIGHT);
        CLabelSet.decorate(listeningLabel).setAlignment(SWT.CENTER).setFont(MT.FONT_SMALL).setText(msgs.getString("listening"));
        listeningLabel.addMouseListener(new SectionTabItemMouseAdapter());

        speakingLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(speakingLabel).fromLeft(50).atTopTo(readingLabel, 0, SWT.TOP).withWidth(SECTION_LABEL_WIDTH).withHeight(SECTION_LABEL_HEIGHT);
        CLabelSet.decorate(speakingLabel).setAlignment(SWT.CENTER).setFont(MT.FONT_SMALL).setText(msgs.getString("speaking"));
        speakingLabel.addMouseListener(new SectionTabItemMouseAdapter());

        writingLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(writingLabel).fromLeft(50, SECTION_LABEL_WIDTH).atTopTo(readingLabel, 0, SWT.TOP).withWidth(SECTION_LABEL_WIDTH).withHeight(SECTION_LABEL_HEIGHT);
        CLabelSet.decorate(writingLabel).setAlignment(SWT.CENTER).setFont(MT.FONT_SMALL).setText(msgs.getString("writing"));
        writingLabel.addMouseListener(new SectionTabItemMouseAdapter());
    }

    /*
     * ==================================================
     *
     * Body Initialization
     *
     * ==================================================
     */

    private void initBody() {
        body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(header).atRight().atBottom();
        stack = new StackLayout();
        body.setLayout(stack);
        toReadingReportView();
    }


    /*
     * ==================================================
     *
     * View Controls
     *
     * ==================================================
     */

    public void toReadingReportView() {
        CLabelSet.decorate(readingLabel).setBackground(MT.COLOR_DARK_BLUE).setForeground(MT.COLOR_WHITE);
        CLabelSet.decorate(listeningLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(speakingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(writingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);

        if (readingReportView == null) {
            readingReportView = new ReadingReportView(body, SWT.NONE);
        }
        readingReportView.refreshRows();
        stack.topControl = readingReportView;
        body.layout();
    }

    public void toListeningReportView() {
        CLabelSet.decorate(readingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(listeningLabel).setBackground(MT.COLOR_DARK_BLUE).setForeground(MT.COLOR_WHITE);
        CLabelSet.decorate(speakingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(writingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);

        if (listeningReportView == null) {
            listeningReportView = new ListeningReportView(body, SWT.NONE);
        }
        listeningReportView.refreshRows();
        stack.topControl = listeningReportView;
        body.layout();
    }

    public void toSpeakingReportView() {
        CLabelSet.decorate(readingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(listeningLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(speakingLabel).setBackground(MT.COLOR_DARK_BLUE).setForeground(MT.COLOR_WHITE);
        CLabelSet.decorate(writingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);

        if (speakingReportView == null) {
            speakingReportView = new SpeakingReportView(body, SWT.NONE);
        }
        speakingReportView.refreshRows();
        stack.topControl = speakingReportView;
        body.layout();
    }

    public void toWritingReportView() {
        CLabelSet.decorate(readingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(listeningLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(speakingLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40);
        CLabelSet.decorate(writingLabel).setBackground(MT.COLOR_DARK_BLUE).setForeground(MT.COLOR_WHITE);

        if (writingReportView == null) {
            writingReportView = new WritingReportView(body, SWT.NONE);
        }
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

    private class ExportAsPdfButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            FileDialog dialog = new FileDialog(MyApplication.get().getWindow().getShell(), SWT.SAVE);
            dialog.setText(msgs.getString("export"));
            dialog.setFilterNames(new String[]{"PDF File (*.pdf)"});
            dialog.setFilterExtensions(new String[]{"*.pdf"});
            dialog.setFileName(userTestSession.getFileAlias() + ".pdf");
            boolean done = false;
            while (!done) {
                String fullDestFileName = dialog.open();
                if (!StringUtils.isEmpty(fullDestFileName)) {
                    File file = new File(fullDestFileName);
                    if (file.exists()) {
                        MessageBox box = new MessageBox(dialog.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
                        box.setText(msgs.getString("file_exists"));
                        box.setMessage("\"" + userTestSession.getFileAlias() + ".pdf\" " + msgs.getString("replace_or_not"));
                        int response = box.open();
                        if (response == SWT.YES) {
                            ExportUtils.exportTestRecordAsPdf(fullDestFileName);
                            done = true;
                        }
                    } else {
                        ExportUtils.exportTestRecordAsPdf(fullDestFileName);
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
