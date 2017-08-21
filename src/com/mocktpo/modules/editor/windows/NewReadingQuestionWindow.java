package com.mocktpo.modules.editor.windows;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.WindowUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
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

public class NewReadingQuestionWindow {

    /* Constants */

    private static final int WINDOW_WIDTH = 340;
    private static final int WINDOW_HEIGHT = 236;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Shell */

    private Display d;
    private Shell s;

    /* Properties */

    private int x;
    private int y;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public NewReadingQuestionWindow(int leftBottomX, int leftBottomY) {
        this.d = MyApplication.get().getDisplay();
        this.x = leftBottomX;
        this.y = leftBottomY - WINDOW_HEIGHT;
        init();
    }

    private void init() {
        s = new Shell(d, SWT.NO_TRIM | SWT.APPLICATION_MODAL);
        golbal();
        initBody();
    }

    private void golbal() {
        s.setImage(ResourceManager.getImage(MT.IMAGE_APP_ICON));
        s.setBackgroundMode(SWT.INHERIT_FORCE);
        s.setBounds(x, y, WINDOW_WIDTH, WINDOW_HEIGHT);
        WindowUtils.disableFullscreen(s);
        FormLayoutSet.layout(s).marginWidth(0).marginHeight(0).spacing(0);
    }

    private void initBody() {
        Composite body = new Composite(s, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTop().atRight().atBottom();
        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE_SMOKE);
        body.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        FormLayoutSet.layout(body).marginWidth(20).marginHeight(20).spacing(20);

        ImageButton newReadingMulitipleChoiceQuestionButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION, MT.IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION_HOVER);
        FormDataSet.attach(newReadingMulitipleChoiceQuestionButton).atLeft().atTop();
        newReadingMulitipleChoiceQuestionButton.addMouseListener(new NewReadingMultipleChoiceQuestionButtonMouseAdapter());

        ImageButton newReadingInsertTextQuestionButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_INSERT_TEXT_QUESTION, MT.IMAGE_SYSTEM_NEW_INSERT_TEXT_QUESTION_HOVER);
        FormDataSet.attach(newReadingInsertTextQuestionButton).atLeft().atTopTo(newReadingMulitipleChoiceQuestionButton);
        newReadingInsertTextQuestionButton.addMouseListener(new NewReadingInsertTextQuestionButtonMouseAdapter());

        ImageButton newReadingProseSummaryQuestionButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_PROSE_SUMMARY_QUESTION, MT.IMAGE_SYSTEM_NEW_PROSE_SUMMARY_QUESTION_HOVER);
        FormDataSet.attach(newReadingProseSummaryQuestionButton).atLeft().atTopTo(newReadingInsertTextQuestionButton);
        newReadingProseSummaryQuestionButton.addMouseListener(new NewReadingProseSummaryQuestionButtonMouseAdapter());

        ImageButton newReadingFillInATableQuestionButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_FILL_IN_A_TABLE_QUESTION, MT.IMAGE_SYSTEM_NEW_FILL_IN_A_TABLE_QUESTION_HOVER);
        FormDataSet.attach(newReadingFillInATableQuestionButton).atLeft().atTopTo(newReadingProseSummaryQuestionButton);
        newReadingFillInATableQuestionButton.addMouseListener(new NewReadingFillInATableQuestionButtonMouseAdapter());
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

    private class NewReadingMultipleChoiceQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();

        }
    }

    private class NewReadingInsertTextQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
        }
    }

    private class NewReadingProseSummaryQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
        }
    }

    private class NewReadingFillInATableQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
        }
    }
}