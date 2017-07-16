package com.mocktpo.windows;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.WindowUtils;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import java.util.ResourceBundle;

public class RequiredAnswerWindow {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Application */

    private MyApplication app;

    /* Display and Shell */

    private Display d;
    private Shell s;

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

    public RequiredAnswerWindow(int type) {
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
        s.setImage(ResourceManager.getImage(MT.IMAGE_APP_ICON));
        s.setBackgroundMode(SWT.INHERIT_FORCE);
        WindowUtils.setModalWindowBoundsToTheCenter(s);
        WindowUtils.disableFullscreen(s);
        FormLayoutSet.layout(s);
    }

    private void initBackground() {
        background = new CLabel(s, SWT.NONE);
        FormDataSet.attach(background).atLeft().atTop().atRight().atBottom();
        CLabelSet.decorate(background).setGradientBackground(MT.COLOR_INDIGO, MT.COLOR_WHITE_SMOKE, true);
        FormLayoutSet.layout(background).marginHeight(20);

        background.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                GC gc = e.gc;
                gc.setFont(ResourceManager.getFont(MT.FONT_MEDIUM_BOLD));
                gc.setForeground(ResourceManager.getColor(MT.COLOR_WHITE));
                Point p = gc.textExtent(msgs.getString("required_answer"));
                gc.drawString(msgs.getString("required_answer"), (s.getBounds().width - p.x) / 2, 20, true);
            }
        });

    }

    private void initWidgets() {
        final ImageButton continueButton = new ImageButton(background, SWT.NONE, MT.IMAGE_RETURN_TO_QUESTION, MT.IMAGE_RETURN_TO_QUESTION_HOVER);
        FormDataSet.attach(continueButton).fromLeft(50, -LC.RETURN_TO_QUESTION_BUTTON_WIDTH / 2).atBottom(20);
        continueButton.addMouseListener(new ReturnToQuestionButtonMouseAdapter());

        final Composite c = new Composite(background, SWT.NONE);
        FormDataSet.attach(c).atLeft(20).atTop(60).atRight(20).atBottomTo(continueButton, 40, SWT.TOP);
        CompositeSet.decorate(c).setBackground(MT.COLOR_WHITE);
        GridLayoutSet.layout(c).marginWidth(10).marginHeight(10);

        final Label bodyLabel = new Label(c, SWT.WRAP);
        GridDataSet.attach(bodyLabel).centerBoth();
        LabelSet.decorate(bodyLabel).setAlignment(SWT.CENTER).setFont(MT.FONT_MEDIUM_BOLD);
        switch (type) {
            case MT.REQUIRED_ANSWER_WINDOW_TYPE_NO_ANSWER_FOR_ONE:
                LabelSet.decorate(bodyLabel).setText(msgs.getString("no_answer_for_one"));
                break;
            case MT.REQUIRED_ANSWER_WINDOW_TYPE_NO_ANSWER_FOR_MANY:
                LabelSet.decorate(bodyLabel).setText(msgs.getString("no_answer_for_many"));
                break;
            case MT.REQUIRED_ANSWER_WINDOW_TYPE_INCORRECT_ANSWER_COUNT:
                LabelSet.decorate(bodyLabel).setText(msgs.getString("incorrect_answer_count"));
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

    private class ReturnToQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
        }
    }
}
