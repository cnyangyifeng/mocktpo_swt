package com.mocktpo.widget;

import java.util.ResourceBundle;

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
import org.eclipse.swt.widgets.ProgressBar;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;

public class TestCard extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Composite header;

    /* Properties */

    private UserTest ut;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

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
        setBackground(ResourceManager.getColor(MT.COLOR_WHITE));
        FormLayoutSet.layout(this).marginWidth(10).marginHeight(10);
    }

    private void initHeader() {
        header = new Composite(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        header.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));
        FormLayoutSet.layout(header);

        final Label tl = new Label(header, SWT.NONE);
        FormDataSet.attach(tl).atLeft().atTop(5).atRight();
        tl.setText(ut.getTitle());
        tl.setFont(ResourceManager.getFont(MT.FONT_SUBTITLE));
        tl.setForeground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));

        final ProgressBar bar = new ProgressBar(header, SWT.NONE);
        FormDataSet.attach(bar).atLeft().atTopTo(tl, 15).atRight();
        bar.setMinimum(0);
        bar.setMaximum(100);
        bar.setSelection(ut.getProgress());

        final Label divider = new Label(header, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(bar, 10).atRight().withHeight(1);
        divider.setBackground(ResourceManager.getColor(MT.COLOR_LIGHTER_GRAY));
    }

    private void initActionBar() {
        final Composite c = new Composite(this, SWT.NONE);
        FormDataSet.attach(c).atLeft().atTopTo(header, 10).atRight();
        c.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));
        FormLayoutSet.layout(c).marginWidth(0).marginHeight(0);

        final CLabel rl = new CLabel(c, SWT.NONE);
        FormDataSet.attach(rl).atLeft().atTop().atRight();
        rl.setText(msgs.getString("restart"));
        rl.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));
        rl.setForeground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
        rl.setImage(ResourceManager.getImage(MT.IMAGE_ARROW_RIGHT));
        rl.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));

        final CLabel sl = new CLabel(c, SWT.NONE);
        FormDataSet.attach(sl).atLeft().atTopTo(rl, 10).atRight();
        sl.setText(msgs.getString("score"));
        sl.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));
        sl.setForeground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
        sl.setImage(ResourceManager.getImage(MT.IMAGE_ARROW_RIGHT));
        sl.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));

        final Label bd = new Label(c, SWT.NONE);
        FormDataSet.attach(bd).atLeft().atTopTo(sl, 10).atRight().withHeight(1);
        bd.setBackground(ResourceManager.getColor(MT.COLOR_LIGHTER_GRAY));

        final Button b = new Button(c, SWT.PUSH);
        FormDataSet.attach(b).atLeft().atTopTo(bd, 10).atBottom().withWidth(90).withHeight(LC.DEFAULT_BUTTON_HEIGHT);
        b.setText(msgs.getString("start"));
        b.setForeground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
        b.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
        b.addSelectionListener(new StartSelectionListener());
    }

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
