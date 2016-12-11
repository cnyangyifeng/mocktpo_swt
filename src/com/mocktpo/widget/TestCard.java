package com.mocktpo.widget;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
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

    /* Properties */

    private UserTest ut;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestCard(Composite parent, int style, UserTest ut) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.ut = ut;
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
        CLabelSet.decorate(tl).setFont(MT.FONT_MEDIUM).setText(ut.getTitle());

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

        final CLabel rl = new CLabel(c, SWT.NONE);
        FormDataSet.attach(rl).atLeft().atTop().atRight();
        CLabelSet.decorate(rl).setBackground(MT.COLOR_WHITE).setCursor(MT.CURSOR_HAND).setImage(MT.IMAGE_ARROW_RIGHT).setText(msgs.getString("restart"));

        final CLabel sl = new CLabel(c, SWT.NONE);
        FormDataSet.attach(sl).atLeft().atTopTo(rl, 10).atRight();
        CLabelSet.decorate(sl).setBackground(MT.COLOR_WHITE).setCursor(MT.CURSOR_HAND).setImage(MT.IMAGE_ARROW_RIGHT).setText(msgs.getString("score"));

        final Label bd = new Label(c, SWT.NONE);
        FormDataSet.attach(bd).atLeft().atTopTo(sl, 10).atRight().withHeight(1);
        LabelSet.decorate(bd).setBackground(MT.COLOR_WHITE_SMOKE);

        final Button b = new Button(c, SWT.PUSH);
        FormDataSet.attach(b).atLeft().atTopTo(bd, 10).atBottom().withWidth(LC.BUTTON_WIDTH_HINT).withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(b).setCursor(MT.CURSOR_HAND).setText(msgs.getString("start"));
        b.addSelectionListener(new StartSelectionListener());
    }

    /*
     * ==================================================
     *
     * Reset
     *
     * ==================================================
     */

    public void reset(UserTest ut) {
        this.ut = ut;
        d.asyncExec(new Runnable() {
            @Override
            public void run() {
                pl.setText(getCompletionRate());
            }
        });
    }

    private String getCompletionRate() {
        return (ut.getCompletionRate() + "%");
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public UserTest getUserTest() {
        return ut;
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class StartSelectionListener implements SelectionListener {

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            MyApplication.get().getWindow().toTestPage(ut);
        }
    }
}
