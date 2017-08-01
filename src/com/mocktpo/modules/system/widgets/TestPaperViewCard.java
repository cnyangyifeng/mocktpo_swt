package com.mocktpo.modules.system.widgets;

import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;

public class TestPaperViewCard extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    private Display d;


    /* Widgets */

    private Composite inner;

    /* Listeners */

    private BorderedCompositePaintListener borderPaintListener;
    private BorderedCompositePaintListener defaultBorderPaintListener;
    private BorderedCompositePaintListener hoveredBorderPaintListener;
    private BorderedCompositePaintListener checkedBorderPaintListener;

    /* Properties */

    private int viewId;
    private boolean checked;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestPaperViewCard(Composite parent, int style, int viewId) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.viewId = viewId;
        this.defaultBorderPaintListener = new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED);
        this.hoveredBorderPaintListener = new BorderedCompositePaintListener(MT.COLOR_GRAY60);
        this.checkedBorderPaintListener = new BorderedCompositePaintListener(MT.COLOR_DARK_BLUE, 4);
        init();
    }

    private void init() {
        golbal();
        initWidgets();
    }

    private void golbal() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(this).marginWidth(0).marginHeight(0).spacing(0);
    }

    private void initWidgets() {
        inner = new Composite(this, SWT.NONE);
        FormDataSet.attach(inner).atLeft().atTop().atRight().withHeight(150);
        FormLayoutSet.layout(inner).marginWidth(0).marginHeight(0).spacing(0);
        borderPaintListener = defaultBorderPaintListener;
        inner.addPaintListener(borderPaintListener);
        inner.addMouseListener(new CardInnerMouseAdapter());
        inner.addMouseTrackListener(new CardInnerMouseTrackAdapter());

        final CLabel viewIdLabel = new CLabel(inner, SWT.NONE);
        FormDataSet.attach(viewIdLabel).atLeft(10).atBottom(10);
        CLabelSet.decorate(viewIdLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY60).setText(Integer.toString(viewId));
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class CardInnerMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            if (!checked) {
                inner.removePaintListener(borderPaintListener);
                borderPaintListener = checkedBorderPaintListener;
                inner.addPaintListener(borderPaintListener);
                TestPaperViewCard.this.redraw();
            } else {
                inner.removePaintListener(borderPaintListener);
                borderPaintListener = defaultBorderPaintListener;
                inner.addPaintListener(borderPaintListener);
                TestPaperViewCard.this.redraw();
            }
            checked = !checked;
        }
    }

    private class CardInnerMouseTrackAdapter extends MouseTrackAdapter {

        @Override
        public void mouseEnter(MouseEvent e) {
            if (!checked) {
                inner.removePaintListener(borderPaintListener);
                borderPaintListener = hoveredBorderPaintListener;
                inner.addPaintListener(borderPaintListener);
                TestPaperViewCard.this.redraw();
            }
        }

        @Override
        public void mouseExit(MouseEvent e) {
            if (!checked) {
                inner.removePaintListener(borderPaintListener);
                borderPaintListener = defaultBorderPaintListener;
                inner.addPaintListener(borderPaintListener);
                TestPaperViewCard.this.redraw();
            }
        }
    }
}
