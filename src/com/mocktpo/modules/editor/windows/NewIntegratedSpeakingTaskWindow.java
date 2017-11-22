package com.mocktpo.modules.editor.windows;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.editor.layers.SpeakingEditorLayer;
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

public class NewIntegratedSpeakingTaskWindow {

    /* Constants */

    private static final int WINDOW_WIDTH = 340;
    private static final int WINDOW_HEIGHT = 202;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Speaking Editor Layer */

    private SpeakingEditorLayer layer;

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

    public NewIntegratedSpeakingTaskWindow(SpeakingEditorLayer layer, int leftBottomX, int leftBottomY) {
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
        CompositeSet.decorate(body).setBackground(MT.COLOR_GREY_LIGHTEN_4);
        body.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_GREY_DARKEN_4, 2));
        FormLayoutSet.layout(body).marginWidth(20).marginHeight(20).spacing(20);

        final ImageButton newIntegratedSpeakingTaskRLSButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLS, MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLS_HOVER);
        FormDataSet.attach(newIntegratedSpeakingTaskRLSButton).atLeft().atTop();
        newIntegratedSpeakingTaskRLSButton.addMouseListener(new NewIntegratedSpeakingTaskRLSButtonMouseAdapter());

        final ImageButton newIntegratedSpeakingTaskLSButton = new ImageButton(body, SWT.NONE, MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_LS, MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_LS_HOVER);
        FormDataSet.attach(newIntegratedSpeakingTaskLSButton).atLeft().atTopTo(newIntegratedSpeakingTaskRLSButton);
        newIntegratedSpeakingTaskLSButton.addMouseListener(new NewIntegratedSpeakingTaskLSButtonMouseAdapter());

        final Label divider = new Label(body, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(newIntegratedSpeakingTaskLSButton).atRight().withHeight(1);
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

    private class NewIntegratedSpeakingTaskRLSButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
            layer.newSpeakingTaskDirections();
            layer.newSpeakingReadingPassage();
            layer.newSpeakingListeningMaterial();
            layer.newSpeakingTask();
        }
    }

    private class NewIntegratedSpeakingTaskLSButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
            layer.newSpeakingTaskDirections();
            layer.newSpeakingListeningMaterial();
            layer.newSpeakingTask();
        }
    }

    private class CancelButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
        }
    }
}