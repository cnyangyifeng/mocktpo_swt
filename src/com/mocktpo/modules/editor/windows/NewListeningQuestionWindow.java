package com.mocktpo.modules.editor.windows;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.editor.layers.ListeningEditorLayer;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.WindowUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import java.util.ResourceBundle;

public class NewListeningQuestionWindow {

    /* Constants */

    private static final int WINDOW_WIDTH = 340;
    private static final int WINDOW_HEIGHT = 364;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Listening Editor Layer */

    private ListeningEditorLayer layer;

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

    public NewListeningQuestionWindow(ListeningEditorLayer layer, int leftBottomX, int leftBottomY) {
        this.d = MyApplication.get().getDisplay();
        this.layer = layer;
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
        final Composite body = new Composite(s, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTop().atRight().atBottom();
        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE_SMOKE);
        body.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_GRAY20, 2));
        FormLayoutSet.layout(body).marginWidth(20).marginHeight(20).spacing(20);

        final ImageButton newListeningMulitipleChoiceQuestionButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION, MT.IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION_HOVER);
        FormDataSet.attach(newListeningMulitipleChoiceQuestionButton).atLeft().atTop();
        newListeningMulitipleChoiceQuestionButton.addMouseListener(new NewListeningMultipleChoiceQuestionButtonMouseAdapter());

        final ImageButton newListeningMultipleResponseQuestionButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_MULTIPLE_RESPONSE_QUESTION, MT.IMAGE_SYSTEM_NEW_MULTIPLE_RESPONSE_QUESTION_HOVER);
        FormDataSet.attach(newListeningMultipleResponseQuestionButton).atLeft().atTopTo(newListeningMulitipleChoiceQuestionButton);
        newListeningMultipleResponseQuestionButton.addMouseListener(new NewListeningMultipleResponseQuestionButtonMouseAdapter());

        final ImageButton newListeningReplayQuestionButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_LISTENING_REPLAY_QUESTION, MT.IMAGE_SYSTEM_NEW_LISTENING_REPLAY_QUESTION_HOVER);
        FormDataSet.attach(newListeningReplayQuestionButton).atLeft().atTopTo(newListeningMultipleResponseQuestionButton);
        newListeningReplayQuestionButton.addMouseListener(new NewListeningReplayQuestionButtonMouseAdapter());

        final ImageButton newListeningSortEventsQuestionButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_SORT_EVENTS_QUESTION, MT.IMAGE_SYSTEM_NEW_SORT_EVENTS_QUESTION_HOVER);
        FormDataSet.attach(newListeningSortEventsQuestionButton).atLeft().atTopTo(newListeningReplayQuestionButton);
        newListeningSortEventsQuestionButton.addMouseListener(new NewListeningSortEventsQuestionButtonMouseAdapter());

        final ImageButton newListeningCategorizeObjectsQuestionButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_CATEGORIZE_OBJECTS_QUESTION, MT.IMAGE_SYSTEM_NEW_CATEGORIZE_OBJECTS_QUESTION_HOVER);
        FormDataSet.attach(newListeningCategorizeObjectsQuestionButton).atLeft().atTopTo(newListeningSortEventsQuestionButton);
        newListeningCategorizeObjectsQuestionButton.addMouseListener(new NewListeningCategorizeObjectsQuestionButtonMouseAdapter());

        final Label divider = new Label(body, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(newListeningCategorizeObjectsQuestionButton).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        final ImageButton cancelButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_CANCEL_WIDE, MT.IMAGE_SYSTEM_CANCEL_WIDE_HOVER);
        FormDataSet.attach(cancelButton).atLeft().atTopTo(divider);
        cancelButton.addMouseListener(new CancelButtonMouseAdapter());
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
            d.asyncExec(s::dispose);
        }
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class NewListeningMultipleChoiceQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
            layer.newListeningMultipleChoiceQuestion();
        }
    }

    private class NewListeningMultipleResponseQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
            layer.newListeningMultipleResponseQuestion();
        }
    }

    private class NewListeningReplayQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
            layer.newListeningReplayQuestion();
            layer.newListeningMultipleChoiceQuestion();
        }
    }

    private class NewListeningSortEventsQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
            layer.newListeningSortEventsQuestion();
        }
    }

    private class NewListeningCategorizeObjectsQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
            layer.newListeningCategorizeObjectsQuestion();
        }
    }

    private class CancelButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
        }
    }
}