package com.mocktpo.view.home;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.TestCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TestEditorHomeView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Composite toolBar;
    private Composite body;
    private List<TestCard> cards;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestEditorHomeView(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
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
        toolBar.setBackground(ResourceManager.getColor(MT.COLOR_WHITE_SMOKE));
        FormLayoutSet.layout(toolBar).marginWidth(10).marginHeight(5).spacing(5);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(toolBar).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_BORDER);

        final Button sb = new Button(toolBar, SWT.PUSH);
        FormDataSet.attach(sb).atLeft().atTop().withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(sb).setCursor(MT.CURSOR_HAND).setText(msgs.getString("sort_by_name"));
        sb.addMouseListener(new SortButtonMouseListener());
    }

    private void initBody() {

        final ScrolledComposite sc = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(toolBar).atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        sc.setContent(body);

        body.setBackground(ResourceManager.getColor(MT.COLOR_MAJOR_BACKGROUND));
        GridLayoutSet.layout(body).numColumns(4).makeColumnsEqualWidth(true).marginWidth(20).marginHeight(20).horizontalSpacing(20).verticalSpacing(20);

        UserTestMapper utm = MyApplication.get().getSqlSession().getMapper(UserTestMapper.class);
        List<UserTest> list = utm.find();
        cards = new ArrayList<TestCard>();
        for (UserTest ut : list) {
            TestCard tc = new TestCard(body, SWT.NONE, ut);
            GridDataSet.attach(tc).fillBoth();
            cards.add(tc);
        }

        sc.setMinSize(body.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    /*
     * ==================================================
     *
     * Reset
     *
     * ==================================================
     */

    public void reset(UserTest ut) {
        for (TestCard tc : cards) {
            if (ut.getTid() == tc.getUserTest().getTid()) {
                tc.reset(ut);
                return;
            }
        }
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class SortButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            logger.debug("Test cards sorted by name successfully.");
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
