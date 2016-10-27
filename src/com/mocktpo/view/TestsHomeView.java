package com.mocktpo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.GridDataSet;
import com.mocktpo.util.GridLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.TestCard;

public class TestsHomeView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Composite toolBar;
    private Composite body;
    private List<TestCard> cards;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public TestsHomeView(Composite parent, int style) {
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
        toolBar.setBackground(ResourceManager.getColor(MT.COLOR_LIGHTER_GRAY));
        FormLayoutSet.layout(toolBar).marginWidth(10).marginHeight(5).spacing(5);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(toolBar).atRight().withHeight(1);
        divider.setBackground(ResourceManager.getColor(MT.COLOR_GRAY));

        final Button sb = new Button(toolBar, SWT.PUSH);
        FormDataSet.attach(sb).atLeft().atTop().withWidth(100).withHeight(LC.DEFAULT_BUTTON_HEIGHT);
        sb.setText(msgs.getString("sort_by_name"));
        sb.setForeground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
        sb.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
    }

    private void initBody() {
        final ScrolledComposite sc = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(toolBar).atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        sc.setContent(body);

        body.setBackground(ResourceManager.getColor(MT.COLOR_LIGHT_GRAY));
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

    /**************************************************
     * 
     * Reset
     * 
     **************************************************/

    public void reset(UserTest ut) {
        for (TestCard tc : cards) {
            if (ut.getTid() == tc.getUserTest().getTid()) {
                tc.setUserTest(ut);
                tc.reset();
                return;
            }
        }
    }
}
