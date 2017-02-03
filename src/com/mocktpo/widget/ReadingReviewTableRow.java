package com.mocktpo.widget;

import com.mocktpo.util.*;
import com.mocktpo.util.constants.MT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.util.ResourceBundle;

public class ReadingReviewTableRow extends Composite {

    private static final int NUMBER_CELL_WIDTH = 120;
    private static final int STATUS_CELL_WIDTH = 200;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Label numberCell;
    private Label descriptionCell;
    private Label statusCell;

    /* Properties */

    private String numberText;
    private String descriptionText;
    private String statusText;

    private int viewId;

    private boolean tableHeader;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingReviewTableRow(Composite parent, int style, String numberText, String descriptionText, String statusText, int viewId) {
        this(parent, style, numberText, descriptionText, statusText, viewId, false);
    }

    public ReadingReviewTableRow(Composite parent, int style, String numberText, String descriptionText, String statusText, int viewId, boolean tableHeader) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.numberText = numberText;
        this.descriptionText = descriptionText;
        this.statusText = statusText;
        this.viewId = viewId;
        this.tableHeader = tableHeader;
        init();
    }

    private void init() {
        golbal();
        initTableCells();
    }

    private void golbal() {
        if (!isTableHeader()) {
            CompositeSet.decorate(this).setBackground(MT.COLOR_BEIGE);
        } else {
            CompositeSet.decorate(this).setBackground(MT.COLOR_TOUPE);
        }
        addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                GC gc = e.gc;
                Rectangle c = ((Control) e.widget).getBounds();
                gc.setForeground(ResourceManager.getColor(MT.COLOR_GRAY40));
                gc.drawLine(0, 0, 0, c.height); // left
                gc.drawLine(0, 0, c.width, 0); // top
                gc.drawLine(c.width - 1, 0, c.width - 1, c.height); // right
            }
        });
        FormLayoutSet.layout(this);
    }

    private void initTableCells() {

        numberCell = new Label(this, SWT.CENTER);
        FormDataSet.attach(numberCell).atLeft(1).atTop(1).atBottom().withWidth(NUMBER_CELL_WIDTH);
        LabelSet ncs = LabelSet.decorate(numberCell).setFont(MT.FONT_MEDIUM).setText(numberText);
        if (isTableHeader()) {
            ncs.setFont(MT.FONT_MEDIUM_BOLD);
        }

        final Label divider1 = new Label(this, SWT.VERTICAL);
        FormDataSet.attach(divider1).atLeftTo(numberCell).atTop().atBottom().withWidth(1);
        LabelSet.decorate(divider1).setBackground(MT.COLOR_GRAY40);

        statusCell = new Label(this, SWT.CENTER);
        FormDataSet.attach(statusCell).atTop(1).atRight(1).atBottom().withWidth(STATUS_CELL_WIDTH);
        LabelSet scs = LabelSet.decorate(statusCell).setFont(MT.FONT_MEDIUM).setText(statusText);
        if (isTableHeader()) {
            scs.setFont(MT.FONT_MEDIUM_BOLD);
        }

        final Label divider2 = new Label(this, SWT.VERTICAL);
        FormDataSet.attach(divider2).atTop().atRightTo(statusCell).atBottom().withWidth(1);
        LabelSet.decorate(divider2).setBackground(MT.COLOR_GRAY40);

        descriptionCell = new Label(this, SWT.NONE);
        FormDataSet.attach(descriptionCell).atLeftTo(divider1).atTop(1).atRightTo(divider2).atBottom();
        LabelSet dcs = LabelSet.decorate(descriptionCell).setFont(MT.FONT_MEDIUM).setText(descriptionText);
        if (isTableHeader()) {
            dcs.setAlignment(SWT.CENTER).setFont(MT.FONT_MEDIUM_BOLD);
        }
    }

    @Override
    public void addMouseListener(MouseListener listener) {
        numberCell.addMouseListener(listener);
        statusCell.addMouseListener(listener);
        descriptionCell.addMouseListener(listener);
    }

    public void setSelectionBackground() {
        Color c = ResourceManager.getColor(MT.COLOR_ROSY_BROWN);
        LabelSet.decorate(numberCell).setBackground(c);
        LabelSet.decorate(descriptionCell).setBackground(c);
        LabelSet.decorate(statusCell).setBackground(c);
    }

    public void resetBackground() {
        Color c = ResourceManager.getColor(MT.COLOR_BEIGE);
        LabelSet.decorate(numberCell).setBackground(c);
        LabelSet.decorate(descriptionCell).setBackground(c);
        LabelSet.decorate(statusCell).setBackground(c);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (!enabled) {
            Color fc = ResourceManager.getColor(MT.COLOR_GRAY40);
            LabelSet.decorate(numberCell).setForeground(fc);
            LabelSet.decorate(descriptionCell).setForeground(fc);
            LabelSet.decorate(statusCell).setForeground(fc);
        }
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public int getViewId() {
        return viewId;
    }

    public boolean isTableHeader() {
        return tableHeader;
    }
}
