package com.mocktpo.modules.editor.widgets;

import com.mocktpo.modules.editor.layers.SashTestEditorLayer;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.modules.system.widgets.ImageButton;
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

public class TestViewEditorCard extends Composite {

    /* Constants */

    private static final int TEST_EDITOR_CARD_WIDTH = 192;
    private static final int TEST_EDITOR_CARD_HEIGHT = 168;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Layer */

    private SashTestEditorLayer layer;

    /* Widgets */

    private Composite inner;
    private Label viewDescriptionLabel;
    private ImageButton trashButton;
    private ImageButton sendBackwardButton;
    private ImageButton bringForwardButton;

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

    public TestViewEditorCard(SashTestEditorLayer layer, int style, TestViewVo viewVo) {
        super(layer.getLeftBody(), style);
        this.layer = layer;
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
        this.hoveredBorderPaintListener = new BorderedCompositePaintListener(MT.COLOR_GREY_DARKEN_1);
        this.checkedBorderPaintListener = new BorderedCompositePaintListener(MT.COLOR_INDIGO, 2);
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
        LabelSet.decorate(viewTypeNameLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_4).setText(TestViewTypeUtils.getViewTypeName(viewVo.getViewType()));
        viewTypeNameLabel.addMouseListener(new CardInnerMouseAdapter());
        viewTypeNameLabel.addMouseTrackListener(new CardInnerMouseTrackAdapter());

        final CLabel serialNumberLabel = new CLabel(inner, SWT.NONE);
        FormDataSet.attach(serialNumberLabel).atLeft(10).atBottom(10);
        CLabelSet.decorate(serialNumberLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_1).setText(Integer.toString(viewVo.getViewId() + 1));
        serialNumberLabel.addMouseListener(new CardInnerMouseAdapter());
        serialNumberLabel.addMouseTrackListener(new CardInnerMouseTrackAdapter());

        trashButton = new ImageButton(inner, SWT.NONE, MT.IMAGE_SYSTEM_TRASH, MT.IMAGE_SYSTEM_TRASH_HOVER);
        FormDataSet.attach(trashButton).atRight(10).atBottom(10);
        trashButton.addMouseListener(new TrashButtonMouseAdapter());

        sendBackwardButton = new ImageButton(inner, SWT.NONE, MT.IMAGE_SYSTEM_SEND_BACKWARD, MT.IMAGE_SYSTEM_SEND_BACKWARD_HOVER);
        FormDataSet.attach(sendBackwardButton).atRightTo(trashButton, 10).atBottom(10);
        sendBackwardButton.addMouseListener(new SendBackwardButtonMouseAdapter());

        bringForwardButton = new ImageButton(inner, SWT.NONE, MT.IMAGE_SYSTEM_BRING_FORWARD, MT.IMAGE_SYSTEM_BRING_FORWARD_HOVER);
        FormDataSet.attach(bringForwardButton).atRightTo(sendBackwardButton, 10).atBottom(10);
        bringForwardButton.addMouseListener(new BringForwardButtonMouseAdapter());

        if (!isChecked()) {
            trashButton.setVisible(false);
            sendBackwardButton.setVisible(false);
            bringForwardButton.setVisible(false);
        } else {
            trashButton.setVisible(true);
            if (layer.isLastCardChecked()) {
                sendBackwardButton.setVisible(false);
            } else {
                sendBackwardButton.setVisible(true);
            }
            if (layer.isFirstCardChecked()) {
                bringForwardButton.setVisible(false);
            } else {
                bringForwardButton.setVisible(true);
            }
        }

        viewDescriptionLabel = new Label(inner, SWT.WRAP);
        FormDataSet.attach(viewDescriptionLabel).atLeft(10).atTopTo(viewTypeNameLabel, 5).atRight(10).atBottomTo(trashButton, 5);
        LabelSet.decorate(viewDescriptionLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_1).setText(viewVo.getViewDescription());
        viewDescriptionLabel.addMouseListener(new CardInnerMouseAdapter());
        viewDescriptionLabel.addMouseTrackListener(new CardInnerMouseTrackAdapter());
    }

    public void updateDescription(String text) {
        viewDescriptionLabel.setText(text);
        update();
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
            layer.check(viewVo.getViewId());
        }
    }

    private class CardInnerMouseTrackAdapter extends MouseTrackAdapter {

        @Override
        public void mouseEnter(MouseEvent e) {
            if (!checked) {
                inner.removePaintListener(borderPaintListener);
                borderPaintListener = hoveredBorderPaintListener;
                inner.addPaintListener(borderPaintListener);
                inner.redraw();
            }
        }

        @Override
        public void mouseExit(MouseEvent e) {
            if (!checked) {
                inner.removePaintListener(borderPaintListener);
                borderPaintListener = defaultBorderPaintListener;
                inner.addPaintListener(borderPaintListener);
                inner.redraw();
            }
        }
    }

    private class TrashButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            layer.trash();
        }
    }

    private class SendBackwardButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            layer.sendBackward();
        }
    }

    private class BringForwardButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            layer.bringForward();
        }
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    private boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            inner.removePaintListener(borderPaintListener);
            borderPaintListener = checkedBorderPaintListener;
            inner.addPaintListener(borderPaintListener);

            trashButton.setVisible(true);
            if (layer.isLastCardChecked()) {
                sendBackwardButton.setVisible(false);
            } else {
                sendBackwardButton.setVisible(true);
            }
            if (layer.isFirstCardChecked()) {
                bringForwardButton.setVisible(false);
            } else {
                bringForwardButton.setVisible(true);
            }

            inner.redraw();
        } else {
            inner.removePaintListener(borderPaintListener);
            borderPaintListener = defaultBorderPaintListener;
            inner.addPaintListener(borderPaintListener);

            trashButton.setVisible(false);
            sendBackwardButton.setVisible(false);
            bringForwardButton.setVisible(false);

            inner.redraw();
        }
    }
}
