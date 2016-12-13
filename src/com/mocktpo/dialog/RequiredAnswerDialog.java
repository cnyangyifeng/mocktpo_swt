package com.mocktpo.dialog;

import com.mocktpo.MyApplication;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.ImageButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import java.util.ResourceBundle;

public class RequiredAnswerDialog {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Application */

    protected MyApplication app;

    /* Display and Shell */

    protected Display d;
    protected Shell s;

    /* Widgets */

    private CLabel background;

    /* Properties */

    private int type;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public RequiredAnswerDialog(int type) {
        this.app = MyApplication.get();
        this.d = app.getDisplay();
        this.type = type;
        init();
    }

    private void init() {
        s = new Shell(d, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        golbal();
        initBackground();
        initWidgets();
    }

    private void golbal() {
        s.setBackgroundMode(SWT.INHERIT_FORCE);
        WindowUtils.setCenterDialogBounds(s);
        WindowUtils.disableFullscreen(s);
        FormLayoutSet.layout(s);
    }

    private void initBackground() {
        background = new CLabel(s, SWT.NONE);
        FormDataSet.attach(background).atLeft().atTop().atRight().atBottom();
        CLabelSet.decorate(background).setGradientBackground(MT.COLOR_INDIGO, MT.COLOR_WHITE_SMOKE, true);
        FormLayoutSet.layout(background).marginHeight(20);
    }

    private void initWidgets() {

        final Label tl = new Label(background, SWT.CENTER);
        FormDataSet.attach(tl).atLeft().atTop().atRight();
        LabelSet.decorate(tl).setFont(MT.FONT_MEDIUM).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setText(msgs.getString("required_answer"));

        final ImageButton cb = new ImageButton(background, SWT.NONE, MT.IMAGE_RETURN_TO_QUESTION, MT.IMAGE_RETURN_TO_QUESTION_HOVER);
        FormDataSet.attach(cb).fromLeft(50, -LC.RETURN_TO_QUESTION_BUTTON_WIDTH / 2).atBottom(20);
        cb.addMouseListener(new ReturnToQuestionButtonMouseListener());

        final Composite c = new Composite(background, SWT.NONE);
        FormDataSet.attach(c).atLeft(20).atTopTo(tl, 20).atRight(20).atBottomTo(cb, 40, SWT.TOP);
        CompositeSet.decorate(c).setBackground(MT.COLOR_WHITE);
        GridLayoutSet.layout(c).marginWidth(10).marginHeight(10);

        final Label p = new Label(c, SWT.WRAP);
        GridDataSet.attach(p).centerBoth();
        LabelSet.decorate(p).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD);
        switch (type) {
            case MT.REQUIRED_ANSWER_DIALOG_TYPE_NO_ANSWER_FOR_ONE:
                LabelSet.decorate(p).setText(msgs.getString("no_answer_for_one"));
                break;
            case MT.REQUIRED_ANSWER_DIALOG_TYPE_NO_ANSWER_FOR_MANY:
                LabelSet.decorate(p).setText(msgs.getString("no_answer_for_many"));
                break;
            case MT.REQUIRED_ANSWER_DIALOG_TYPE_INCORRECT_ANSWER_COUNT:
                LabelSet.decorate(p).setText(msgs.getString("incorrect_answer_count"));
                break;
        }
    }

    public void openAndWaitForDisposal() {
        s.open();
        while (!s.isDisposed()) {
            if (!d.readAndDispatch()) {
                d.sleep();
            }
        }
    }

    public void close() {
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    s.dispose();
                }
            });
        }
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class ReturnToQuestionButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            close();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
