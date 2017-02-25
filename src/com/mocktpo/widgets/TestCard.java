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
        initWidgets();
    }

    private void golbal() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(this).marginWidth(10).marginHeight(10).spacing(0);
    }

    private void initWidgets() {
        final Composite header = new Composite(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        CompositeSet.decorate(header).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(header).marginWidth(0).marginHeight(0).spacing(0);

        final CLabel titleLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(titleLabel).atLeft().atTop().withWidth(TITLE_WIDTH);
        CLabelSet.decorate(titleLabel).setFont(MT.FONT_MEDIUM_BOLD).setText(testSchema.getTitle());

        final StarsComposite starsComposite = new StarsComposite(header, SWT.NONE, testSchema.getStars());
        FormDataSet.attach(starsComposite).atLeft().atTopTo(titleLabel, 10).atRight();

        final Label divider1 = new Label(header, SWT.NONE);
        FormDataSet.attach(divider1).atLeft().atTopTo(starsComposite, 10).atRight().withHeight(1);
        LabelSet.decorate(divider1).setBackground(MT.COLOR_WHITE_SMOKE);

        final Composite body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(header).atRight();
        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(0);

        final CLabel sectionsLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(sectionsLabel).atLeft().atTop(10).atRight();
        CLabelSet.decorate(sectionsLabel).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("select_sections"));

        sectionsComposite = new SectionsComposite(body, SWT.NONE, 2, true, false, false, false, false);
        FormDataSet.attach(sectionsComposite).atLeft().atTopTo(sectionsLabel, 10).atRight();

        final Label divider2 = new Label(body, SWT.NONE);
        FormDataSet.attach(divider2).atLeft().atTopTo(sectionsComposite, 10).atRight().withHeight(1);
        LabelSet.decorate(divider2).setBackground(MT.COLOR_WHITE_SMOKE);

        final Composite footer = new Composite(this, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atTopTo(body).atRight();
        CompositeSet.decorate(footer).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(footer).marginWidth(0).marginHeight(10).spacing(0);

        final ImageButton newTestButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_NEW_TEST, MT.IMAGE_SYSTEM_NEW_TEST_HOVER);
        FormDataSet.attach(newTestButton).atLeft().atTop(10);
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
            boolean readingSelected = sectionsComposite.isReadingSelected();
            boolean listeningSelected = sectionsComposite.isListeningSelected();
            boolean speakingSelected = sectionsComposite.isSpeakingSelected();
            boolean writingSelected = sectionsComposite.isWritingSelected();
            if (!readingSelected && !listeningSelected && !speakingSelected && !writingSelected) {
                MessageBox box = new MessageBox(MyApplication.get().getWindow().getShell(), SWT.OK);
                box.setText(msgs.getString("select_sections"));
                box.setMessage(msgs.getString("select_sections_to_start"));
                box.open();
            } else {
                UserTestSession userTestSession = PersistenceUtils.newSession(fileAlias, testSchema, readingSelected, listeningSelected, speakingSelected, writingSelected);
                MyApplication.get().getWindow().toTestPage(userTestSession);
            }
        }
    }
}
