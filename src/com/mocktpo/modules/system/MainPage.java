package com.mocktpo.modules.system;

import com.mocktpo.modules.system.views.NewTestView;
import com.mocktpo.modules.system.views.PracticesView;
import com.mocktpo.modules.system.views.TestRecordsView;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import java.util.ResourceBundle;

public class MainPage extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Stack */

    private StackLayout stack;

    /* Widgets */

    private Composite sidebar;
    private CLabel newTestLabel;
    private CLabel testRecordsLabel;
    private CLabel practicesLabel;

    private Composite body;
    private NewTestView newTestView;
    private TestRecordsView testRecordsView;
    private PracticesView practicesView;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public MainPage(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        init();
    }

    private void init() {
        golbal();
        initSidebar();
        initPages();
    }

    private void golbal() {
        FormLayoutSet.layout(this);
        addListener(SWT.Resize, new AppWindowResizeListener());
    }

    private void initSidebar() {
        sidebar = new Composite(this, SWT.NONE);
        FormDataSet.attach(sidebar).atLeft().atTop().atBottom().withWidth(this.getBounds().width / 6);
        CompositeSet.decorate(sidebar).setBackground(MT.COLOR_BLACK);
        FormLayoutSet.layout(sidebar);

        final CLabel brandLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(brandLabel).atLeft().atTop().atRight().withHeight(80);
        CLabelSet.decorate(brandLabel).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_X_LARGE_BOLD).setForeground(MT.COLOR_WHITE).setImage(MT.IMAGE_LOGO).setLeftMargin(10).setRightMargin(20).setText(msgs.getString("app_name_alt"));

        final CLabel testsPreLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(testsPreLabel).atLeft().atTopTo(brandLabel).atRight().withHeight(60);
        CLabelSet.decorate(testsPreLabel).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_GRAY60).setLeftMargin(10).setRightMargin(20).setText(msgs.getString("tests"));

        newTestLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(newTestLabel).atLeft().atTopTo(testsPreLabel).atRight().withHeight(60);
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setLeftMargin(20).setText(msgs.getString("new_test"));
        newTestLabel.addMouseListener(new SidebarItemAdapter());

        testRecordsLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(testRecordsLabel).atLeft().atTopTo(newTestLabel).atRight().withHeight(60);
        CLabelSet.decorate(testRecordsLabel).setBackground(MT.COLOR_GRAY20).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setLeftMargin(20).setText(msgs.getString("test_records"));
        testRecordsLabel.addMouseListener(new SidebarItemAdapter());

        final CLabel practicesPreLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(practicesPreLabel).atLeft().atTopTo(testRecordsLabel).atRight().withHeight(60);
        CLabelSet.decorate(practicesPreLabel).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_GRAY60).setLeftMargin(10).setRightMargin(20).setText(msgs.getString("practices"));

        practicesLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(practicesLabel).atLeft().atTopTo(practicesPreLabel).atRight().withHeight(60);
        CLabelSet.decorate(practicesLabel).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setLeftMargin(20).setText(msgs.getString("practices"));
        practicesLabel.addMouseListener(new SidebarItemAdapter());
    }

    private void initPages() {
        body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeftTo(sidebar).atTop().atRight().atBottom();
        stack = new StackLayout();
        body.setLayout(stack);
        toNewTestView();
    }

    /*
     * ==================================================
     *
     * Page Controls
     *
     * ==================================================
     */

    public void toNewTestView() {
        if (newTestView == null) {
            newTestView = new NewTestView(body, SWT.NONE);
        }
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_GRAY20);
        CLabelSet.decorate(testRecordsLabel).setBackground(MT.COLOR_BLACK);
        CLabelSet.decorate(practicesLabel).setBackground(MT.COLOR_BLACK);
        newTestView.refreshCards();
        stack.topControl = newTestView;
        body.layout();
    }

    public void toTestRecordsView() {
        if (testRecordsView == null) {
            testRecordsView = new TestRecordsView(body, SWT.NONE);
        }
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_BLACK);
        CLabelSet.decorate(testRecordsLabel).setBackground(MT.COLOR_GRAY20);
        CLabelSet.decorate(practicesLabel).setBackground(MT.COLOR_BLACK);
        testRecordsView.refreshRows();
        stack.topControl = testRecordsView;
        body.layout();
    }

    public void toPracticesView() {
        if (practicesView == null) {
            practicesView = new PracticesView(body, SWT.NONE);
        }
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_BLACK);
        CLabelSet.decorate(testRecordsLabel).setBackground(MT.COLOR_BLACK);
        CLabelSet.decorate(practicesLabel).setBackground(MT.COLOR_GRAY20);
        practicesView.refreshCards();
        stack.topControl = practicesView;
        body.layout();
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class AppWindowResizeListener implements Listener {

        @Override
        public void handleEvent(Event e) {
            FormDataSet.attach(sidebar).atLeft().atTop().atBottom().withWidth(((Composite) e.widget).getBounds().width / 6);
        }
    }

    private class SidebarItemAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            String text = ((CLabel) e.widget).getText();
            if (msgs.getString("new_test").equals(text)) {
                toNewTestView();
            } else if (msgs.getString("test_records").equals(text)) {
                toTestRecordsView();
            } else if (msgs.getString("practices").equals(text)) {
                toPracticesView();
            }
        }
    }
}
