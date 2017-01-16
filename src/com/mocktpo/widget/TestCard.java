package com.mocktpo.widget;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.util.ResourceBundle;

public class TestCard extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Composite header;
    private CLabel pl;

    /* Persistence */

    protected SqlSession sqlSession;

    /* Properties */

    private UserTest userTest;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestCard(Composite parent, int style, UserTest userTest) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.sqlSession = MyApplication.get().getSqlSession();
        this.userTest = userTest;
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

        final CLabel tl = new CLabel(header, SWT.NONE);
        FormDataSet.attach(tl).atLeft().atTop(5).atRight();
        CLabelSet.decorate(tl).setFont(MT.FONT_MEDIUM).setText(userTest.getTitle());

        pl = new CLabel(header, SWT.NONE);
        FormDataSet.attach(pl).atLeft().atTopTo(tl, 15).atRight();
        CLabelSet.decorate(pl).setForeground(MT.COLOR_DARK_BLUE).setText(getCompletionRate());

        final Label divider = new Label(header, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(pl, 10).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_WHITE_SMOKE);
    }

    private void initActionBar() {

        final Composite c = new Composite(this, SWT.NONE);
        FormDataSet.attach(c).atLeft().atTopTo(header, 10).atRight();
        CompositeSet.decorate(c).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(c).marginWidth(0).marginHeight(0);

        final CLabel restartLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(restartLabel).atLeft().atTop().atRight();
        CLabelSet.decorate(restartLabel).setBackground(MT.COLOR_WHITE).setCursor(MT.CURSOR_HAND).setImage(MT.IMAGE_ARROW_RIGHT).setText(msgs.getString("restart"));
        restartLabel.addMouseListener(new RestartLabelMouseListener());

        final CLabel reportLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(reportLabel).atLeft().atTopTo(restartLabel, 10).atRight();
        CLabelSet.decorate(reportLabel).setBackground(MT.COLOR_WHITE).setCursor(MT.CURSOR_HAND).setImage(MT.IMAGE_ARROW_RIGHT).setText(msgs.getString("report"));
        reportLabel.addMouseListener(new ReportLabelMouseListener());

        final Label divider = new Label(c, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(reportLabel, 10).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_WHITE_SMOKE);

        final Button enterButton = new Button(c, SWT.PUSH);
        FormDataSet.attach(enterButton).atLeft().atTopTo(divider, 10).atBottom().withWidth(LC.BUTTON_WIDTH_HINT).withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(enterButton).setCursor(MT.CURSOR_HAND).setText(msgs.getString("enter"));
        enterButton.addSelectionListener(new EnterButtonSelectionListener());
    }

    /*
     * ==================================================
     *
     * Reset
     *
     * ==================================================
     */

    public void reset(UserTest userTest) {
        this.userTest = userTest;
        d.asyncExec(new Runnable() {
            @Override
            public void run() {
                pl.setText(getCompletionRate());
            }
        });
    }

    private String getCompletionRate() {
        return Integer.toString(userTest.getStars());
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public UserTest getUserTest() {
        return userTest;
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class RestartLabelMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            userTest.setTimerHidden(false);
            userTest.setReadingTime(MT.TIME_READING_SECTION);
            userTest.setListeningTime1(MT.TIME_LISTENING_PER_SUB_SECTION);
            userTest.setListeningTime2(MT.TIME_LISTENING_PER_SUB_SECTION);
            userTest.setSpeakingReadingTime1(MT.TIME_SPEAKING_READING_PER_TASK);
            userTest.setSpeakingReadingTime2(MT.TIME_SPEAKING_READING_PER_TASK);
            userTest.setWritingReadingTime(MT.TIME_WRITING_READING_PER_TASK);
            userTest.setVolume(1.0);
            userTest.setVolumeControlHidden(true);
            userTest.setStars(0);
            userTest.setLastViewId(1);

            sqlSession.getMapper(UserTestMapper.class).update(userTest);
            sqlSession.commit();

            MyApplication.get().getWindow().toTestPage(userTest);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class ReportLabelMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            MyApplication.get().getWindow().toReportPage(userTest);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class EnterButtonSelectionListener implements SelectionListener {

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            MyApplication.get().getWindow().toTestPage(userTest);
        }
    }
}
