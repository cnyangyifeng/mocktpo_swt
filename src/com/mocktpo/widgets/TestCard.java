package com.mocktpo.widgets;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.vo.TestSchemaVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import java.util.ResourceBundle;

public class TestCard extends Composite {

    /* Constants */

    private static final int TITLE_WIDTH = 200;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Composite header;
    private SectionsComposite sectionsComposite;

    /* Properties */

    private String fileAlias;
    private TestSchemaVo testSchema;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestCard(Composite parent, int style, String fileAlias) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.fileAlias = fileAlias;
        this.testSchema = ConfigUtils.load(fileAlias, TestSchemaVo.class);
        init();
    }

    private void init() {
        golbal();
        initHeader();
        initActionBar();
    }

    private void golbal() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(this).marginWidth(10).marginHeight(10);
    }

    private void initHeader() {
        header = new Composite(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        CompositeSet.decorate(header).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(header);

        final CLabel titleLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(titleLabel).atLeft().atTop(5).withWidth(TITLE_WIDTH);
        CLabelSet.decorate(titleLabel).setFont(MT.FONT_MEDIUM_BOLD).setText(testSchema.getTitle());

        final StarsComposite starsComposite = new StarsComposite(header, SWT.NONE, testSchema.getStars());
        FormDataSet.attach(starsComposite).atLeft().atTopTo(titleLabel, 15).atRight();

        final Label divider = new Label(header, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(starsComposite, 10).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_WHITE_SMOKE);
    }

    private void initActionBar() {
        final Composite c = new Composite(this, SWT.NONE);
        FormDataSet.attach(c).atLeft().atTopTo(header, 10).atRight();
        CompositeSet.decorate(c).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(c).marginWidth(0).marginHeight(0);

        final CLabel sectionsLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(sectionsLabel).atLeft().atTop().atRight();
        CLabelSet.decorate(sectionsLabel).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("select_sections"));

        sectionsComposite = new SectionsComposite(c, SWT.NONE, 2, true);
        FormDataSet.attach(sectionsComposite).atLeft().atTopTo(sectionsLabel, 10).atRight();

        final Label divider = new Label(c, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(sectionsComposite, 10).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_WHITE_SMOKE);

        final ImageButton newTestButton = new ImageButton(c, SWT.PUSH, MT.IMAGE_SYSTEM_NEW_TEST, MT.IMAGE_SYSTEM_NEW_TEST_HOVER);
        FormDataSet.attach(newTestButton).atLeft().atTopTo(divider, 10);
        newTestButton.addMouseListener(new NewTestButtonMouseAdapter());
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class NewTestButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            boolean readingSectionEnabled = sectionsComposite.isReadingSectionEnabled();
            boolean listeningSectionEnabled = sectionsComposite.isListeningSectionEnabled();
            boolean speakingSectionEnabled = sectionsComposite.isSpeakingSectionEnabled();
            boolean writingSectionEnabled = sectionsComposite.isWritingSectionEnabled();
            if (!readingSectionEnabled && !listeningSectionEnabled && !speakingSectionEnabled && !writingSectionEnabled) {
                MessageBox box = new MessageBox(MyApplication.get().getWindow().getShell(), SWT.OK);
                box.setText(msgs.getString("select_sections"));
                box.setMessage(msgs.getString("select_sections_to_start"));
                box.open();
            } else {
                UserTestSession userTestSession = PersistenceUtils.newSession(fileAlias, testSchema, readingSectionEnabled, listeningSectionEnabled, speakingSectionEnabled, writingSectionEnabled);
                MyApplication.get().getWindow().toTestPage(userTestSession);
            }
        }
    }
}
