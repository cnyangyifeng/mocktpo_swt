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
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import java.util.ResourceBundle;

public class MoreTextDialog {

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

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public MoreTextDialog() {
        this.app = MyApplication.get();
        this.d = app.getDisplay();
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
        WindowUtils.setDialogBounds(s);
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
        LabelSet.decorate(tl).setFont(MT.FONT_MEDIUM).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setText(msgs.getString("more_text"));

        final ImageButton cb = new ImageButton(background, SWT.NONE, ResourceManager.getImage(MT.IMAGE_CONTINUE), ResourceManager.getImage(MT.IMAGE_CONTINUE_HOVER));
        FormDataSet.attach(cb).fromLeft(50, -LC.CONTINUE_BUTTON_WIDTH / 2).atBottom();
        cb.addMouseListener(new ContinueButtonMouseListener());

        final StyledText p = new StyledText(background, SWT.WRAP);
        FormDataSet.attach(p).atLeft(20).atTopTo(tl, 20).atRight(20).atBottomTo(cb, 20, SWT.TOP);
        StyledTextSet.decorate(p).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setMargins(10).setText(msgs.getString("use_scroll_bar"));

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

    /**************************************************
     * 
     * Listeners
     * 
     **************************************************/

    private class ContinueButtonMouseListener implements MouseListener {

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
