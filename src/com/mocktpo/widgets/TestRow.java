package com.mocktpo.widgets;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.orm.mapper.UserTestSessionMapper;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.ButtonSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.Button;
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
        CLabelSet.decorate(titleLabel).setFont(MT.FONT_MEDIUM).setText(userTestSession.getTitle());

        final StarsComposite starsComposite = new StarsComposite(header, SWT.NONE, userTestSession.getStars());
        FormDataSet.attach(starsComposite).atLeftTo(titleLabel).atBottomTo(titleLabel, 0, SWT.BOTTOM);

        deleteLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(deleteLabel).atBottomTo(titleLabel, 0, SWT.BOTTOM).atRight();
        CLabelSet.decorate(deleteLabel).setForeground(MT.COLOR_GRAY40).setFont(MT.FONT_SMALL).setText(msgs.getString("delete"));
        deleteLabel.addMouseListener(new DeleteLabelMouseAdapter());
        deleteLabel.addMouseTrackListener(new DeleteLabelMouseTrackAdapter());

        final Label divider = new Label(header, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(titleLabel, 10).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_WHITE_SMOKE);
    }

    private void initActionBar() {
        final Composite c = new Composite(this, SWT.NONE);
        FormDataSet.attach(c).atLeft().atTopTo(header, 10).atRight();
        CompositeSet.decorate(c).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(c).marginWidth(0).marginHeight(0);

        final SectionsComposite sectionsComposite = new SectionsComposite(c, SWT.NONE);
        FormDataSet.attach(sectionsComposite).atLeft().atTop().atRight();

        final Label divider = new Label(c, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(sectionsComposite, 10).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_WHITE_SMOKE);

        final Button loadTestButton = new Button(c, SWT.PUSH);
        FormDataSet.attach(loadTestButton).atLeft().atTopTo(divider, 10).atBottom().withWidth(LC.BUTTON_WIDTH_HINT).withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(loadTestButton).setText(msgs.getString("load_test_alt"));
        loadTestButton.addSelectionListener(new LoadTestButtonSelectionAdapter());
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class LoadTestButtonSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            MyApplication.get().getWindow().toTestPage(userTestSession);
        }
    }

    private class DeleteLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            SqlSession sqlSession = MyApplication.get().getSqlSession();
            UserTestSessionMapper userTestSessionMapper = sqlSession.getMapper(UserTestSessionMapper.class);
            userTestSessionMapper.delete(userTestSession);
            sqlSession.commit();
            MyApplication.get().getWindow().toMainPage();
        }
    }

    public class DeleteLabelMouseTrackAdapter extends MouseTrackAdapter {

        public void mouseEnter(MouseEvent e) {
            CLabelSet.decorate(deleteLabel).setForeground(MT.COLOR_DARK_BLUE);
        }

        public void mouseExit(MouseEvent e) {
            CLabelSet.decorate(deleteLabel).setForeground(MT.COLOR_GRAY40);
        }
    }
}
