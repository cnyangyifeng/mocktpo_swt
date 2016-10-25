package com.mocktpo.widget;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.MT;

public class TestHeader extends CLabel {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public TestHeader(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        init();
    }

    private void init() {
        golbal();
    }

    private void golbal() {
        FormDataSet.attach(this).atLeft().atTop().atRight().atBottom();
        Color start = ResourceManager.getColor(MT.COLOR_DARK_BLUE);
        Color end = ResourceManager.getColor(MT.COLOR_DARK_RED);
        this.setBackground(new Color[] { start, end }, new int[] { 100 });
        FormLayoutSet.layout(this);
    }
}
