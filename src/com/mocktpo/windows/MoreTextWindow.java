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

public class MoreTextWindow {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Shell */

    private Display d;
    private Shell s;

    /* Widgets */

    private CLabel background;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public MoreTextWindow() {
        this.d = MyApplication.get().getDisplay();
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
        WindowUtils.setModalWindowBoundsToTheLeft(s);
        WindowUtils.disableFullscreen(s);
        FormLayoutSet.layout(s).marginWidth(0).marginHeight(0).spacing(0);
    }

    private void initBackground() {
        background = new CLabel(s, SWT.NONE);
        FormDataSet.attach(background).atLeft().atTop().atRight().atBottom();
        CLabelSet.decorate(background).setGradientBackground(MT.COLOR_INDIGO, MT.COLOR_WHITE_SMOKE, true);
        FormLayoutSet.layout(background).marginWidth(0).marginHeight(20).spacing(0);

        background.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                GC gc = e.gc;
                gc.setFont(ResourceManager.getFont(MT.FONT_MEDIUM_BOLD));
                gc.setForeground(ResourceManager.getColor(MT.COLOR_WHITE));
                Point p = gc.textExtent(msgs.getString("more_text"));
                gc.drawString(msgs.getString("more_text"), (s.getBounds().width - p.x) / 2, 20, true);
            }
        });
    }

    private void initWidgets() {
        final ImageButton continueButton = new ImageButton(background, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER);
        FormDataSet.attach(continueButton).fromLeft(50, -LC.CONTINUE_BUTTON_WIDTH / 2).atBottom(20);
        continueButton.addMouseListener(new ContinueButtonMouseAdapter());

        final Composite c = new Composite(background, SWT.NONE);
        FormDataSet.attach(c).atLeft(20).atTop(60).atRight(20).atBottomTo(continueButton, 40, SWT.TOP);
        CompositeSet.decorate(c).setBackground(MT.COLOR_WHITE);
        GridLayoutSet.layout(c).marginWidth(10).marginHeight(10);

        final Label bodyLabel = new Label(c, SWT.WRAP);
        GridDataSet.attach(bodyLabel).centerBoth();
        LabelSet.decorate(bodyLabel).setAlignment(SWT.CENTER).setFont(MT.FONT_MEDIUM_BOLD).setText(msgs.getString("use_scroll_bar"));
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

    private class ContinueButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            close();
        }
    }
}
