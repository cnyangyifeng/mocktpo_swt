package com.mocktpo.widgets;

import com.mocktpo.MyApplication;
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
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.util.ResourceBundle;

public class TestRow extends Composite {

    /* Constants */

    private static final int TITLE_WIDTH = 400;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Composite header;
    private CLabel deleteLabel;

    /* Properties */

    private UserTestSession userTestSession;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestRow(Composite parent, int style, UserTestSession userTestSession) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.userTestSession = userTestSession;
        init();
    }

    private void init() {
        golbal();
        initHeader();
        initActionBar();
    }

    private void golbal() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(this).marginWidth(10).marginHeight(10);
    }

    private void initHeader() {
        header = new Composite(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        CompositeSet.decorate(header).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(header);

        final CLabel titleLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(titleLabel).atLeft().atTop(5).withWidth(TITLE_WIDTH);
        CLabelSet.decorate(titleLabel).setFont(MT.FONT_MEDIUM_BOLD).setText(userTestSession.getTitle());

        final StarsComposite starsComposite = new StarsComposite(header, SWT.NONE, userTestSession.getStars());
        FormDataSet.attach(starsComposite).atLeftTo(titleLabel).atBottomTo(titleLabel, 0, SWT.BOTTOM);

        deleteLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(deleteLabel).atBottomTo(titleLabel, 0, SWT.BOTTOM).atRight();
        CLabelSet.decorate(deleteLabel).setForeground(MT.COLOR_GRAY40).setFont(MT.FONT_SMALL).setImage(MT.IMAGE_SYSTEM_DELETE);
        deleteLabel.addMouseListener(new DeleteLabelMouseAdapter());
        deleteLabel.addMouseTrackListener(new DeleteLabelMouseTrackAdapter());

        final CLabel lastVisitTimeLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(lastVisitTimeLabel).atLeft().atTopTo(titleLabel, 10);
        CLabelSet.decorate(lastVisitTimeLabel).setForeground(MT.COLOR_GRAY40).setText(TimeUtils.displaySocialTime(userTestSession.getLastVisitTime()) + MT.STRING_SPACE + msgs.getString("visited"));

        final CLabel startTimeLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(startTimeLabel).atLeftTo(lastVisitTimeLabel, 20).atTopTo(lastVisitTimeLabel, 0, SWT.TOP).atRight();
        CLabelSet.decorate(startTimeLabel).setForeground(MT.COLOR_GRAY40).setText(TimeUtils.displaySocialTime(userTestSession.getStartTime()) + MT.STRING_SPACE + msgs.getString("initialized"));

        final Label divider = new Label(header, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(lastVisitTimeLabel, 10).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_WHITE_SMOKE);
    }

    private void initActionBar() {
        final Composite c = new Composite(this, SWT.NONE);
        FormDataSet.attach(c).atLeft().atTopTo(header, 10).atRight();
        CompositeSet.decorate(c).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(c).marginWidth(0).marginHeight(0);

        final CLabel sectionsLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(sectionsLabel).atLeft().atTop().atRight();
        CLabelSet.decorate(sectionsLabel).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("selected_sections"));

        final SectionsComposite sectionsComposite = new SectionsComposite(c, SWT.NONE, 4, false);
        FormDataSet.attach(sectionsComposite).atLeft().atTopTo(sectionsLabel, 10).atRight();

        final Label divider = new Label(c, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(sectionsComposite, 10).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_WHITE_SMOKE);

        final ImageButton continueButton = new ImageButton(c, SWT.NONE, MT.IMAGE_SYSTEM_CONTINUE, MT.IMAGE_SYSTEM_CONTINUE_HOVER);
        FormDataSet.attach(continueButton).atLeft().atTopTo(divider, 10);
        continueButton.addMouseListener(new ContinueButtonMouseAdapter());

        final ImageButton reportButton = new ImageButton(c, SWT.PUSH, MT.IMAGE_SYSTEM_REPORT, MT.IMAGE_SYSTEM_REPORT_HOVER);
        FormDataSet.attach(reportButton).atLeftTo(continueButton, 10).atTopTo(continueButton, 0, SWT.TOP);
        reportButton.addMouseListener(new ReportButtonMouseAdapter());
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
            MyApplication.get().getWindow().toReportPage(userTestSession);
        }
    }

    private class DeleteLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            PersistenceUtils.deleteSession(userTestSession);
            MyApplication.get().getWindow().toMainPage();
        }
    }

    public class DeleteLabelMouseTrackAdapter extends MouseTrackAdapter {

        public void mouseEnter(MouseEvent e) {
            CLabelSet.decorate(deleteLabel).setImage(MT.IMAGE_SYSTEM_DELETE_HOVER);
        }

        public void mouseExit(MouseEvent e) {
            CLabelSet.decorate(deleteLabel).setImage(MT.IMAGE_SYSTEM_DELETE);
        }
    }
}
