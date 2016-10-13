package com.mocktpo.widgets;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.ImageUtils;
import com.mocktpo.util.constants.ResourceConstants;

public class TestsHomeView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    private Display d;

    /* Widgets */

    private Composite tb;

    /* Resources */

    private Color gray;
    private Color lightGray;
    private Color lighterGray;
    private Cursor hand;
    private Image si;

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
        alloc();
        golbal();
        initToolBar();
    }

    private void golbal() {
        setBackground(lightGray);
        FormLayoutSet.layout(this);
    }

    private void initToolBar() {
        tb = new Composite(this, SWT.NONE);
        FormDataSet.attach(tb).atLeft().atTop().atRight().withHeight(50);
        tb.setBackground(lighterGray);
        FormLayoutSet.layout(tb).marginWidth(10).marginHeight(5).spacing(5);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(tb).atRight().withHeight(1);
        divider.setBackground(gray);

        final Button sb = new Button(tb, SWT.PUSH);
        FormDataSet.attach(sb).atLeft().atTop().atBottom();
        sb.setText(msgs.getString("sync"));
        // sb.setImage(si);
        sb.setCursor(hand);
    }

    @Override
    public void dispose() {
        release();
        super.dispose();
    }

    /**************************************************
     * 
     * Native Resource Operations
     * 
     **************************************************/

    private void alloc() {
        gray = new Color(d, 220, 220, 220);
        lightGray = new Color(d, 239, 239, 239);
        lighterGray = new Color(d, 245, 245, 245);
        hand = new Cursor(d, SWT.CURSOR_HAND);
        si = ImageUtils.load(d, ResourceConstants.SYNC_IMAGE_FILE);
    }

    private void release() {
        gray.dispose();
        lightGray.dispose();
        lighterGray.dispose();
        hand.dispose();
        si.dispose();
    }
}
