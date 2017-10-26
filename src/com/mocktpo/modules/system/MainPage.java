package com.mocktpo.modules.system;

import com.mocktpo.modules.system.views.NewTestView;
import com.mocktpo.modules.system.views.TestPapersView;
import com.mocktpo.modules.system.views.TestRecordsView;
import com.mocktpo.modules.system.views.TestStoreView;
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
    private CLabel testPapersLabel;
    private CLabel testStoreLabel;

    private Composite body;
    private NewTestView newTestView;
    private TestRecordsView testRecordsView;
    private TestPapersView testPapersView;
    private TestStoreView testStoreView;

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
        initSideBar();
        initViews();
    }

    private void golbal() {
        FormLayoutSet.layout(this).marginWidth(0).marginHeight(0).spacing(0);
        addListener(SWT.Resize, new AppWindowResizeListener());
    }

    /*
     * ==================================================
     *
     * Side Bar Initialization
     *
     * ==================================================
     */

    private void initSideBar() {
        sidebar = new Composite(this, SWT.NONE);
        FormDataSet.attach(sidebar).atLeft().atTop().atBottom().withWidth(this.getBounds().width / 6);
        CompositeSet.decorate(sidebar).setBackground(MT.COLOR_BLACK);
        FormLayoutSet.layout(sidebar).marginWidth(0).marginHeight(0).spacing(0);

        final CLabel brandLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(brandLabel).atLeft().atTop().atRight().withHeight(80);
        CLabelSet.decorate(brandLabel).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_X_LARGE_BOLD).setForeground(MT.COLOR_WHITE).setImage(MT.IMAGE_LOGO).setLeftMargin(10).setRightMargin(20).setText(msgs.getString("app_name_alt"));

        final CLabel testSectionLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(testSectionLabel).atLeft().atTopTo(brandLabel).atRight().withHeight(60);
        CLabelSet.decorate(testSectionLabel).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_GRAY60).setLeftMargin(10).setRightMargin(20).setText(msgs.getString("test"));

        newTestLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(newTestLabel).atLeft().atTopTo(testSectionLabel).atRight().withHeight(60);
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setLeftMargin(20).setText(msgs.getString("new_test"));
        newTestLabel.addMouseListener(new NewTestLabelMouseAdapter());

        testRecordsLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(testRecordsLabel).atLeft().atTopTo(newTestLabel).atRight().withHeight(60);
        CLabelSet.decorate(testRecordsLabel).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setLeftMargin(20).setText(msgs.getString("test_records"));
        testRecordsLabel.addMouseListener(new TestRecordsLabelMouseAdapter());

        final CLabel storeSectionLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(storeSectionLabel).atLeft().atTopTo(testRecordsLabel).atRight().withHeight(60);
        CLabelSet.decorate(storeSectionLabel).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_GRAY60).setLeftMargin(10).setRightMargin(20).setText(msgs.getString("store"));

        testStoreLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(testStoreLabel).atLeft().atTopTo(storeSectionLabel).atRight().withHeight(60);
        CLabelSet.decorate(testStoreLabel).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setLeftMargin(20).setText(msgs.getString("test_store"));
        testStoreLabel.addMouseListener(new TestStoreLabelMouseAdapter());

        testPapersLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(testPapersLabel).atLeft().atTopTo(testStoreLabel).atRight().withHeight(60);
        CLabelSet.decorate(testPapersLabel).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setLeftMargin(20).setText(msgs.getString("test_papers"));
        testPapersLabel.addMouseListener(new TestPapersLabelMouseAdapter());
    }

    /*
     * ==================================================
     *
     * Views Initialization
     *
     * ==================================================
     */

    private void initViews() {
        body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeftTo(sidebar).atTop().atRight().atBottom();
        stack = new StackLayout();
        body.setLayout(stack);
        toNewTestView();
    }

    /*
     * ==================================================
     *
     * View Controls
     *
     * ==================================================
     */

    public void toNewTestView() {
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_GRAY20);
        CLabelSet.decorate(testRecordsLabel).setBackground(MT.COLOR_BLACK);
        CLabelSet.decorate(testPapersLabel).setBackground(MT.COLOR_BLACK);
        CLabelSet.decorate(testStoreLabel).setBackground(MT.COLOR_BLACK);

        if (newTestView == null) {
            newTestView = new NewTestView(body, SWT.NONE);
        }
        newTestView.refreshCards();
        stack.topControl = newTestView;
        body.layout();
    }

    public void toTestRecordsView() {
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_BLACK);
        CLabelSet.decorate(testRecordsLabel).setBackground(MT.COLOR_GRAY20);
        CLabelSet.decorate(testPapersLabel).setBackground(MT.COLOR_BLACK);
        CLabelSet.decorate(testStoreLabel).setBackground(MT.COLOR_BLACK);

        if (testRecordsView == null) {
            testRecordsView = new TestRecordsView(body, SWT.NONE);
        }
        testRecordsView.refreshCards();
        stack.topControl = testRecordsView;
        body.layout();
    }

    public void toTestStoreView() {
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_BLACK);
        CLabelSet.decorate(testRecordsLabel).setBackground(MT.COLOR_BLACK);
        CLabelSet.decorate(testPapersLabel).setBackground(MT.COLOR_BLACK);
        CLabelSet.decorate(testStoreLabel).setBackground(MT.COLOR_GRAY20);

        if (testStoreView == null) {
            testStoreView = new TestStoreView(body, SWT.NONE);
        }
        testStoreView.refreshCards();
        stack.topControl = testStoreView;
        body.layout();
    }

    public void toTestPapersView() {
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_BLACK);
        CLabelSet.decorate(testRecordsLabel).setBackground(MT.COLOR_BLACK);
        CLabelSet.decorate(testPapersLabel).setBackground(MT.COLOR_GRAY20);
        CLabelSet.decorate(testStoreLabel).setBackground(MT.COLOR_BLACK);

        if (testPapersView == null) {
            testPapersView = new TestPapersView(body, SWT.NONE);
        }
        testPapersView.refreshCards();
        stack.topControl = testPapersView;
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

    private class NewTestLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            toNewTestView();
        }
    }

    private class TestRecordsLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            toTestRecordsView();
        }
    }

    private class TestStoreLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            toTestStoreView();
        }
    }

    private class TestPapersLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            toTestPapersView();
        }
    }
}
