package com.mocktpo.widgets;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.util.ResourceBundle;

public class TestCard extends Composite {

    /* Constants */

    private static final int TITLE_WIDTH = 200;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Composite header;

    /* Persistence */

    protected SqlSession sqlSession;

    /* Properties */

    private UserTestSession userTestSession;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestCard(Composite parent, int style, UserTestSession userTestSession) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.sqlSession = MyApplication.get().getSqlSession();
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
        CLabelSet.decorate(titleLabel).setFont(MT.FONT_MEDIUM).setText(userTestSession.getTitle());

        final StarsComposite starsComposite = new StarsComposite(header, SWT.NONE, getStars());
        FormDataSet.attach(starsComposite).atLeft().atTopTo(titleLabel, 15).atRight();

        final Label divider = new Label(header, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(starsComposite, 10).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_WHITE_SMOKE);
    }

    private void initActionBar() {
        final Composite c = new Composite(this, SWT.NONE);
        FormDataSet.attach(c).atLeft().atTopTo(header, 10).atRight();
        CompositeSet.decorate(c).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(c).marginWidth(0).marginHeight(0);

        final CLabel restartLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(restartLabel).atLeft().atTop().atRight();
        CLabelSet.decorate(restartLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("restart"));
        restartLabel.addMouseListener(new RestartLabelMouseAdapter());

        final CLabel reportLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(reportLabel).atLeft().atTopTo(restartLabel, 10).atRight();
        CLabelSet.decorate(reportLabel).setBackground(MT.COLOR_WHITE).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("report"));
        reportLabel.addMouseListener(new ReportLabelMouseAdapter());

        final Label divider = new Label(c, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(reportLabel, 10).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_WHITE_SMOKE);

        final Button enterButton = new Button(c, SWT.PUSH);
        FormDataSet.attach(enterButton).atLeft().atTopTo(divider, 10).atBottom().withWidth(LC.BUTTON_WIDTH_HINT).withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(enterButton).setText(msgs.getString("enter"));
        enterButton.addSelectionListener(new EnterButtonSelectionAdapter());
    }

    private int getStars() {
        return userTestSession.getStars();
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public UserTestSession getUserTestSession() {
        return userTestSession;
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class RestartLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            UserTestPersistenceUtils.restart(userTestSession);
            MyApplication.get().getWindow().toTestPage(userTestSession);
        }
    }

    private class ReportLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            MyApplication.get().getWindow().toReportPage(userTestSession);
        }
    }

    private class EnterButtonSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            MyApplication.get().getWindow().toTestPage(userTestSession);
        }
    }
}
