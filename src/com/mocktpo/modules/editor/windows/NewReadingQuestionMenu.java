package com.mocktpo.modules.editor.windows;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.WindowUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.util.ResourceBundle;

public class NewReadingQuestionMenu {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Shell */

    private Display d;
    private Shell s;

    /* Properties */

    private int width;
    private int height;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public NewReadingQuestionMenu() {
        this.d = MyApplication.get().getDisplay();
        this.width = 340;
        this.height = 240;
        init();
    }

    private void init() {
        s = new Shell(d, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        golbal();
        initWidgets();
    }

    private void golbal() {
        s.setImage(ResourceManager.getImage(MT.IMAGE_APP_ICON));
        s.setBackgroundMode(SWT.INHERIT_FORCE);
        WindowUtils.setModalWindowBoundsToCenter(s, width, height);
        WindowUtils.disableFullscreen(s);
        GridLayoutSet.layout(s).marginWidth(0).marginHeight(0).spacing(0);
    }

    private void initWidgets() {
        Composite body = new Composite(s, SWT.NONE);
        GridDataSet.attach(body).fillBoth();
        CompositeSet.decorate(body).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        GridLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(0);

        ImageButton newMulitipleChoiceQuestionButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION, MT.IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION_HOVER);
        GridDataSet.attach(newMulitipleChoiceQuestionButton).topCenter();
        newMulitipleChoiceQuestionButton.addMouseListener(new NewMultipleChoiceQuestionButtonMouseAdapter());

        ImageButton newInsertTextQuestionButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_INSERT_TEXT_QUESTION, MT.IMAGE_SYSTEM_NEW_INSERT_TEXT_QUESTION_HOVER);
        GridDataSet.attach(newInsertTextQuestionButton).topCenter();
        newInsertTextQuestionButton.addMouseListener(new NewInsertTextQuestionButtonMouseAdapter());

        ImageButton newProseSummaryQuestionButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_PROSE_SUMMARY_QUESTION, MT.IMAGE_SYSTEM_NEW_PROSE_SUMMARY_QUESTION_HOVER);
        GridDataSet.attach(newProseSummaryQuestionButton).topCenter();
        newProseSummaryQuestionButton.addMouseListener(new NewProseSummaryQuestionButtonMouseAdapter());

        ImageButton newFillInATableQuestionButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_FILL_IN_A_TABLE_QUESTION, MT.IMAGE_SYSTEM_NEW_FILL_IN_A_TABLE_QUESTION_HOVER);
        GridDataSet.attach(newFillInATableQuestionButton).topCenter();
        newFillInATableQuestionButton.addMouseListener(new NewFillInATableQuestionButtonMouseAdapter());
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

    private class NewReadingPassageButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
        }
    }

    private class NewMultipleChoiceQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
        }
    }

    private class NewInsertTextQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
        }
    }

    private class NewProseSummaryQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
        }
    }

    private class NewFillInATableQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
        }
    }
}