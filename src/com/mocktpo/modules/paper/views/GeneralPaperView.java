package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.util.KeyBindingSet;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.StyledTextSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;

public class GeneralPaperView extends Composite {

    /* Constants */

    private static final int VIEW_PORT_WIDTH = 720;
    private static final int TEXT_HEIGHT = 22;
    private static final int LABEL_WIDTH = 120;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Widgets */

    private ScrolledComposite sc;
    private Composite body;
    private Composite viewPort;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public GeneralPaperView(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        init();
    }

    private void init() {
        golbal();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this);
    }

    private void initBody() {
        sc = new ScrolledComposite(this, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTop().atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        CompositeSet.decorate(body).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        GridLayoutSet.layout(body).marginBottom(50);

        viewPort = new Composite(body, SWT.NONE);
        GridDataSet.attach(viewPort).topCenter().withWidth(VIEW_PORT_WIDTH);
        FormLayoutSet.layout(viewPort).marginWidth(0).marginHeight(80).spacing(10);

        /*
         * ==================================================
         *
         * Body Updates
         *
         * ==================================================
         */

        updateBody();

        sc.setContent(body);
        sc.setMinSize(body.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    private void updateBody() {
        final StyledText titleText = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(titleText).atLeft(LABEL_WIDTH).atTop().atRight().withHeight(TEXT_HEIGHT);
        StyledTextSet.decorate(titleText).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10);
        KeyBindingSet.bind(titleText).traverse().selectAll();
        titleText.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel titleLabel = new CLabel(viewPort, SWT.NONE);
        FormDataSet.attach(titleLabel).atLeft().atTopTo(titleText, 0, SWT.TOP).atRightTo(titleText, 0, SWT.LEFT).atBottomTo(titleText, 0, SWT.BOTTOM);
        CLabelSet.decorate(titleLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("title") + MT.STRING_TAB + MT.STRING_STAR);

        final StyledText starsText = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(starsText).atLeft(LABEL_WIDTH).atTopTo(titleText).withWidth(40).withHeight(TEXT_HEIGHT);
        StyledTextSet.decorate(starsText).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText("3");
        KeyBindingSet.bind(starsText).traverse().selectAll();
        starsText.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel starsLabel = new CLabel(viewPort, SWT.NONE);
        FormDataSet.attach(starsLabel).atLeft().atTopTo(starsText, 0, SWT.TOP).atRightTo(starsText, 0, SWT.LEFT).atBottomTo(starsText, 0, SWT.BOTTOM);
        CLabelSet.decorate(starsLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("stars") + MT.STRING_TAB + MT.STRING_STAR);

        final StyledText authorText = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(authorText).atLeft(LABEL_WIDTH).atTopTo(starsText).withWidth(240).withHeight(TEXT_HEIGHT);
        StyledTextSet.decorate(authorText).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10);
        KeyBindingSet.bind(authorText).traverse().selectAll();
        authorText.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel authorLabel = new CLabel(viewPort, SWT.NONE);
        FormDataSet.attach(authorLabel).atLeft().atTopTo(authorText, 0, SWT.TOP).atRightTo(authorText, 0, SWT.LEFT).atBottomTo(authorText, 0, SWT.BOTTOM);
        CLabelSet.decorate(authorLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("author") + MT.STRING_TAB + MT.STRING_STAR);
    }
}
