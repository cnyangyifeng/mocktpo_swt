package com.mocktpo.modules.system.widgets;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.TimeUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import java.util.ResourceBundle;

public class TestReportCard extends Composite {

    /* Constants */

    private static final int TITLE_WIDTH = 400;
    private static final int PROGRESS_BAR_WIDTH = 400;
    private static final int PROGRESS_BAR_HEIGHT = 6;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Properties */

    private UserTestSession userTestSession;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestReportCard(Composite parent, int style, UserTestSession userTestSession) {
        super(parent, style);
        this.userTestSession = userTestSession;
        init();
    }

    private void init() {
        golbal();
        initWidgets();
    }

    private void golbal() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(this).marginWidth(10).marginHeight(10).spacing(0);
        this.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
    }

    private void initWidgets() {
        final Composite header = new Composite(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        FormLayoutSet.layout(header).marginWidth(0).marginHeight(0).spacing(0);

        final CLabel titleLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(titleLabel).atLeft().atTop().withWidth(TITLE_WIDTH);
        CLabelSet.decorate(titleLabel).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_BLUE_GREY_DARKEN_4).setText(userTestSession.getTitle());

        final StarsComposite starsComposite = new StarsComposite(header, SWT.NONE, userTestSession.getStars());
        FormDataSet.attach(starsComposite).atLeftTo(titleLabel).atBottomTo(titleLabel, 0, SWT.BOTTOM);

        final ImageButton deleteButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_DELETE, MT.IMAGE_SYSTEM_DELETE_HOVER);
        FormDataSet.attach(deleteButton).atTop().atRight();
        deleteButton.addMouseListener(new DeleteButtonMouseAdapter());

        final CLabel startTimePreLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(startTimePreLabel).atLeft().atTopTo(titleLabel, 10);
        CLabelSet.decorate(startTimePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY).setText(msgs.getString("start_time"));

        final CLabel startTimeLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(startTimeLabel).atLeftTo(startTimePreLabel, 5).atTopTo(startTimePreLabel, 0, SWT.TOP);
        CLabelSet.decorate(startTimeLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY_DARKEN_2).setText(TimeUtils.displaySocialTime(userTestSession.getStartTime()));

        final CLabel lastVisitedTimePreLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(lastVisitedTimePreLabel).atLeftTo(startTimeLabel, 40).atTopTo(startTimePreLabel, 0, SWT.TOP);
        CLabelSet.decorate(lastVisitedTimePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY).setText(msgs.getString("last_visited_time"));

        final CLabel lastVisitedTimeLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(lastVisitedTimeLabel).atLeftTo(lastVisitedTimePreLabel, 5).atTopTo(startTimePreLabel, 0, SWT.TOP).atRight();
        CLabelSet.decorate(lastVisitedTimeLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY_DARKEN_2).setText(TimeUtils.displaySocialTime(userTestSession.getLastVisitedTime()));

        final Label divider1 = new Label(header, SWT.NONE);
        FormDataSet.attach(divider1).atLeft().atTopTo(startTimePreLabel, 10).atRight().withHeight(1);
        LabelSet.decorate(divider1).setBackground(MT.COLOR_GREY_LIGHTEN_4);

        final Composite body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(header).atRight();
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(0);

        final CLabel progressLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(progressLabel).atLeft().atTop(10).atRight();
        CLabelSet.decorate(progressLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY_DARKEN_2).setText(msgs.getString("progress"));

        int selection = 100 * userTestSession.getVisitedViewCount() / userTestSession.getTotalViewCount();
        if (userTestSession.isTestComplete() || selection > 100) {
            selection = 100;
        }
        final TestReportProgressBar progressBar = new TestReportProgressBar(body, SWT.NONE, PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT, selection);
        FormDataSet.attach(progressBar).atLeft().atTopTo(progressLabel, 10);

        final CLabel selectionLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(selectionLabel).atLeftTo(progressBar, 10).atTopTo(progressBar, -5, SWT.TOP);
        CLabelSet.decorate(selectionLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY_DARKEN_2).setText(Integer.toString(selection) + MT.STRING_PERCENTAGE);

        final CLabel sectionsLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(sectionsLabel).atLeft().atTopTo(progressBar, 20).atRight();
        CLabelSet.decorate(sectionsLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY_DARKEN_2).setText(msgs.getString("selected_sections"));

        final SectionsComposite sectionsComposite = new SectionsComposite(body, SWT.NONE, 4, false, userTestSession.isReadingSelected(), userTestSession.isListeningSelected(), userTestSession.isSpeakingSelected(), userTestSession.isWritingSelected());
        FormDataSet.attach(sectionsComposite).atLeft().atTopTo(sectionsLabel, 10).atRight();

        final Label divider2 = new Label(body, SWT.NONE);
        FormDataSet.attach(divider2).atLeft().atTopTo(sectionsComposite, 15).atRight().withHeight(1);
        LabelSet.decorate(divider2).setBackground(MT.COLOR_GREY_LIGHTEN_4);

        final Composite footer = new Composite(this, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atTopTo(body).atRight();
        FormLayoutSet.layout(footer).marginWidth(0).marginHeight(10).spacing(0);

        if (!userTestSession.isTestComplete()) {
            final ImageButton continueButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_CONTINUE, MT.IMAGE_SYSTEM_CONTINUE_HOVER);
            FormDataSet.attach(continueButton).atLeft().atTop(10);
            continueButton.addMouseListener(new ContinueButtonMouseAdapter());

            final ImageButton reportButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_REPORT, MT.IMAGE_SYSTEM_REPORT_HOVER);
            FormDataSet.attach(reportButton).atLeftTo(continueButton, 10).atTopTo(continueButton, 0, SWT.TOP);
            reportButton.addMouseListener(new ReportButtonMouseAdapter());
        } else {
            final ImageButton reportButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_REPORT, MT.IMAGE_SYSTEM_REPORT_HOVER);
            FormDataSet.attach(reportButton).atLeft().atTop(10);
            reportButton.addMouseListener(new ReportButtonMouseAdapter());
        }
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class ContinueButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            MyApplication.get().getWindow().toTestPage(userTestSession);
        }
    }

    private class ReportButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            MyApplication.get().getWindow().toTestReportPage(userTestSession);
        }
    }

    private class DeleteButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            MessageBox box = new MessageBox(MyApplication.get().getWindow().getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
            box.setText(msgs.getString("delete"));
            box.setMessage(msgs.getString("delete_test_record_or_not"));
            int response = box.open();
            if (response == SWT.YES) {
                PersistenceUtils.deleteSession(userTestSession);
                MyApplication.get().getWindow().toMainPageAndToTestReportNavContent();
            }
        }
    }
}
