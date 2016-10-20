package com.mocktpo.widgets;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.GridDataSet;
import com.mocktpo.util.GridLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.MT;

public class TestsHomeView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Composite toolBar;
    private Composite body;

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
        FormDataSet.attach(toolBar).atLeft().atTop().atRight().withHeight(50);
        toolBar.setBackground(ResourceManager.getColor(MT.COLOR_LIGHTER_GRAY));
        FormLayoutSet.layout(toolBar).marginWidth(10).marginHeight(5).spacing(5);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(toolBar).atRight().withHeight(1);
        divider.setBackground(ResourceManager.getColor(MT.COLOR_GRAY));

        final Button sb = new Button(toolBar, SWT.PUSH);
        FormDataSet.attach(sb).atLeft().atTop().atBottom();
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
        GridLayoutSet.layout(body).numColumns(5).makeColumnsEqualWidth(true).marginWidth(10).marginHeight(10).horizontalSpacing(10).verticalSpacing(10);

        for (int i = 0; i < 50; i++) {
            CardView cv = new CardView(body, SWT.NONE, "TPO " + i);
            GridDataSet.attach(cv).fillBoth();
        }

        sc.setMinSize(body.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }
}
