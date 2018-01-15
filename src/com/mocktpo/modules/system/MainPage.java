package com.mocktpo.modules.system;

import com.mocktpo.modules.system.views.NewTestNavContent;
import com.mocktpo.modules.system.views.TestEditorNavContent;
import com.mocktpo.modules.system.views.TestReportNavContent;
import com.mocktpo.modules.system.views.TestStoreNavContent;
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
    private CLabel testReportLabel;
    private CLabel testEditorLabel;
    private CLabel testStoreLabel;

    private Composite body;
    private NewTestNavContent newTestView;
    private TestReportNavContent testReportView;
    private TestEditorNavContent testEditorView;
    private TestStoreNavContent testStoreView;

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
        CompositeSet.decorate(sidebar).setBackground(MT.COLOR_INDIGO_DARKEN_2);
        FormLayoutSet.layout(sidebar).marginWidth(0).marginHeight(0).spacing(0);

        final CLabel brandLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(brandLabel).atLeft().atTop().atRight().withHeight(80);
        CLabelSet.decorate(brandLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2).setFont(MT.FONT_X_LARGE_BOLD).setForeground(MT.COLOR_WHITE).setImage(MT.IMAGE_LOGO).setLeftMargin(10).setRightMargin(20).setText(msgs.getString("app_name_alt"));

        final CLabel testSectionLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(testSectionLabel).atLeft().atTopTo(brandLabel).atRight().withHeight(60);
        CLabelSet.decorate(testSectionLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_INDIGO_LIGHTEN_4).setLeftMargin(10).setRightMargin(20).setText(msgs.getString("test"));

        newTestLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(newTestLabel).atLeft().atTopTo(testSectionLabel).atRight().withHeight(60);
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setLeftMargin(20).setText(msgs.getString("new_test"));
        newTestLabel.addMouseListener(new NewTestLabelMouseAdapter());

        testReportLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(testReportLabel).atLeft().atTopTo(newTestLabel).atRight().withHeight(60);
        CLabelSet.decorate(testReportLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setLeftMargin(20).setText(msgs.getString("test_reports"));
        testReportLabel.addMouseListener(new TestReportLabelMouseAdapter());

        final CLabel storeSectionLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(storeSectionLabel).atLeft().atTopTo(testReportLabel).atRight().withHeight(60);
        CLabelSet.decorate(storeSectionLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_INDIGO_LIGHTEN_4).setLeftMargin(10).setRightMargin(20).setText(msgs.getString("store"));

        testStoreLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(testStoreLabel).atLeft().atTopTo(storeSectionLabel).atRight().withHeight(60);
        CLabelSet.decorate(testStoreLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setLeftMargin(20).setText(msgs.getString("test_store"));
        testStoreLabel.addMouseListener(new TestStoreLabelMouseAdapter());

        testEditorLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(testEditorLabel).atLeft().atTopTo(testStoreLabel).atRight().withHeight(60);
        CLabelSet.decorate(testEditorLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setLeftMargin(20).setText(msgs.getString("test_editor"));
        testEditorLabel.addMouseListener(new TestEditorLabelMouseAdapter());
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
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_INDIGO);
        CLabelSet.decorate(testReportLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2);
        CLabelSet.decorate(testEditorLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2);
        CLabelSet.decorate(testStoreLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2);

        if (newTestView == null) {
            newTestView = new NewTestNavContent(body, SWT.NONE);
        }
        newTestView.refresh();
        stack.topControl = newTestView;
        body.layout();
    }

    public void toTestReportView() {
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2);
        CLabelSet.decorate(testReportLabel).setBackground(MT.COLOR_INDIGO);
        CLabelSet.decorate(testEditorLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2);
        CLabelSet.decorate(testStoreLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2);

        if (testReportView == null) {
            testReportView = new TestReportNavContent(body, SWT.NONE);
        }
        testReportView.refresh();
        stack.topControl = testReportView;
        body.layout();
    }

    public void toTestStoreView() {
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2);
        CLabelSet.decorate(testReportLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2);
        CLabelSet.decorate(testEditorLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2);
        CLabelSet.decorate(testStoreLabel).setBackground(MT.COLOR_INDIGO);

        if (testStoreView == null) {
            testStoreView = new TestStoreNavContent(body, SWT.NONE);
        }
        testStoreView.refresh();
        stack.topControl = testStoreView;
        body.layout();
    }

    public void toTestEditorView() {
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2);
        CLabelSet.decorate(testReportLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2);
        CLabelSet.decorate(testEditorLabel).setBackground(MT.COLOR_INDIGO);
        CLabelSet.decorate(testStoreLabel).setBackground(MT.COLOR_INDIGO_DARKEN_2);

        if (testEditorView == null) {
            testEditorView = new TestEditorNavContent(body, SWT.NONE);
        }
        testEditorView.refresh();
        stack.topControl = testEditorView;
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

    private class TestReportLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            toTestReportView();
        }
    }

    private class TestStoreLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            toTestStoreView();
        }
    }

    private class TestEditorLabelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            toTestEditorView();
        }
    }
}
