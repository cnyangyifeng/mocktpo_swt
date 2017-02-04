package com.mocktpo.view.home;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.orm.mapper.UserTestSessionMapper;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.TestCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StoreHomeView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

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

    public StoreHomeView(Composite parent, int style) {
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
        CompositeSet.decorate(toolBar).setBackground(MT.COLOR_WHITE_SMOKE);
        FormLayoutSet.layout(toolBar).marginWidth(10).marginHeight(5).spacing(5);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(toolBar).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        final Button sb = new Button(toolBar, SWT.PUSH);
        FormDataSet.attach(sb).atLeft().atTop().withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(sb).setCursor(MT.CURSOR_HAND).setText(msgs.getString("sort_by_name"));
        sb.addSelectionListener(new SortButtonSelectionAdapter());
    }

    private void initBody() {
        final ScrolledComposite sc = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(toolBar).atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        CompositeSet.decorate(body).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        GridLayoutSet.layout(body).numColumns(4).makeColumnsEqualWidth(true).marginWidth(20).marginHeight(20).horizontalSpacing(20).verticalSpacing(20);

        UserTestSessionMapper userTestSessionMapper = MyApplication.get().getSqlSession().getMapper(UserTestSessionMapper.class);
        List<UserTestSession> list = userTestSessionMapper.find();
        cards = new ArrayList<TestCard>();
        for (UserTestSession userTestSession : list) {
            TestCard card = new TestCard(body, SWT.NONE, userTestSession);
            GridDataSet.attach(card).fillBoth();
            cards.add(card);
        }

        sc.setContent(body);
        sc.setMinSize(body.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    /*
     * ==================================================
     *
     * Reset
     *
     * ==================================================
     */

    public void reset(UserTestSession getUserTestSession) {
        for (TestCard card : cards) {
            if (getUserTestSession.getTid() == card.getUserTestSession().getTid()) {
                card.reset(getUserTestSession);
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

    private class SortButtonSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            logger.debug("Test cards sorted by name successfully.");
        }
    }
}
