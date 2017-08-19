package com.mocktpo.modules.editor.widgets;

import com.mocktpo.modules.editor.layers.SashTestEditorLayer;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.util.TestViewTypeUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.vo.TestViewVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import java.util.ResourceBundle;

public class TestEditorCard extends Composite {

    /* Constants */

    private static final int TEST_EDITOR_CARD_WIDTH = 192;
    private static final int TEST_EDITOR_CARD_HEIGHT = 120;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Layer */

    private SashTestEditorLayer layer;

    /* Widgets */

    private Composite inner;

    /* Listeners */

    private BorderedCompositePaintListener borderPaintListener;
    private BorderedCompositePaintListener defaultBorderPaintListener;
    private BorderedCompositePaintListener hoveredBorderPaintListener;
    private BorderedCompositePaintListener checkedBorderPaintListener;

    /* Properties */

    private TestViewVo viewVo;
    private boolean checked;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestEditorCard(SashTestEditorLayer editorLayer, int style, TestViewVo viewVo) {
        super(editorLayer.getLeftBody(), style);
        this.layer = editorLayer;
        initViewVo(viewVo);
        initListeners();
        init();
    }

    /*
     * ==================================================
     *
     * Data Initialization
     *
     * ==================================================
     */

    private void initViewVo(TestViewVo viewVo) {
        this.viewVo = viewVo;
    }

    /*
     * ==================================================
     *
     * Listeners Initialization
     *
     * ==================================================
     */

    private void initListeners() {
        this.defaultBorderPaintListener = new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED);
        this.hoveredBorderPaintListener = new BorderedCompositePaintListener(MT.COLOR_GRAY60);
        this.checkedBorderPaintListener = new BorderedCompositePaintListener(MT.COLOR_DARK_BLUE, 2);
    }

    /*
     * ==================================================
     *
     * UI Initialization
     *
     * ==================================================
     */

    private void init() {
        golbal();
        initWidgets();
    }

    private void golbal() {
        FormLayoutSet.layout(this).marginWidth(0).marginHeight(0).spacing(0);
    }

    private void initWidgets() {
        inner = new Composite(this, SWT.NONE);
        FormDataSet.attach(inner).atLeft().atTop().atRight().withWidth(TEST_EDITOR_CARD_WIDTH).withHeight(TEST_EDITOR_CARD_HEIGHT);
        CompositeSet.decorate(inner).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(inner).marginWidth(0).marginHeight(0).spacing(0);
        borderPaintListener = defaultBorderPaintListener;
        inner.addPaintListener(borderPaintListener);
        inner.addMouseListener(new CardInnerMouseAdapter());
        inner.addMouseTrackListener(new CardInnerMouseTrackAdapter());

        final Label viewTypeNameLabel = new Label(inner, SWT.WRAP);
        FormDataSet.attach(viewTypeNameLabel).atLeft(10).atTop(10).atRight(10);
        LabelSet.decorate(viewTypeNameLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(TestViewTypeUtils.getViewTypeName(viewVo.getViewType()));
        viewTypeNameLabel.addMouseListener(new CardInnerMouseAdapter());
        viewTypeNameLabel.addMouseTrackListener(new CardInnerMouseTrackAdapter());

        final CLabel viewIdLabel = new CLabel(inner, SWT.NONE);
        FormDataSet.attach(viewIdLabel).atLeft(10).atBottom(10);
        CLabelSet.decorate(viewIdLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY60).setText(Integer.toString(viewVo.getViewId()));
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
            layer.checkEditorView(viewVo.getViewId());
        }
    }

    private class CardInnerMouseTrackAdapter extends MouseTrackAdapter {

        @Override
        public void mouseEnter(MouseEvent e) {
            if (!checked) {
                inner.removePaintListener(borderPaintListener);
                borderPaintListener = hoveredBorderPaintListener;
                inner.addPaintListener(borderPaintListener);
                TestEditorCard.this.redraw();
            }
        }

        @Override
        public void mouseExit(MouseEvent e) {
            if (!checked) {
                inner.removePaintListener(borderPaintListener);
                borderPaintListener = defaultBorderPaintListener;
                inner.addPaintListener(borderPaintListener);
                TestEditorCard.this.redraw();
            }
        }
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            inner.removePaintListener(borderPaintListener);
            borderPaintListener = checkedBorderPaintListener;
            inner.addPaintListener(borderPaintListener);
            TestEditorCard.this.redraw();
        } else {
            inner.removePaintListener(borderPaintListener);
            borderPaintListener = defaultBorderPaintListener;
            inner.addPaintListener(borderPaintListener);
            TestEditorCard.this.redraw();
        }
    }
}
