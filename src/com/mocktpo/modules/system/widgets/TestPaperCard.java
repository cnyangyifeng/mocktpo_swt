package com.mocktpo.modules.system.widgets;

import com.mocktpo.MyApplication;
import com.mocktpo.util.JSONSUtils;
import com.mocktpo.util.TimeUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.vo.TestEditorVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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

import java.io.File;
import java.net.URLDecoder;
import java.util.ResourceBundle;

public class TestPaperCard extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    private Display d;

    /* Properties */

    private String fileAlias;
    private TestEditorVo testVo;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestPaperCard(Composite parent, int style, String fileAlias) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.fileAlias = fileAlias;
        this.testVo = JSONSUtils.pullFromProject(fileAlias, TestEditorVo.class);
        init();
    }

    private void init() {
        golbal();
        initWidgets();
    }

    private void golbal() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(this).marginWidth(10).marginHeight(10).spacing(0);
        // this.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
    }

    private void initWidgets() {
        final Composite header = new Composite(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        FormLayoutSet.layout(header).marginWidth(0).marginHeight(0).spacing(0);

        final ImageButton trashButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_TRASH, MT.IMAGE_SYSTEM_TRASH_HOVER);
        FormDataSet.attach(trashButton).atTop().atRight();
        trashButton.addMouseListener(new TrashButtonMouseAdapter());

        final CLabel titleLabel = new CLabel(header, SWT.MULTI);
        FormDataSet.attach(titleLabel).atLeft().atTop().atRightTo(trashButton);
        CLabelSet.decorate(titleLabel).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_BLACK).setText(getTitle());

        final StarsComposite starsComposite = new StarsComposite(header, SWT.NONE, testVo.getStars());
        FormDataSet.attach(starsComposite).atLeft().atTopTo(titleLabel, 10).atRight();

        final Label divider1 = new Label(header, SWT.NONE);
        FormDataSet.attach(divider1).atLeft().atTopTo(starsComposite, 20).atRight().withHeight(1);
        LabelSet.decorate(divider1).setBackground(MT.COLOR_WINDOW_BACKGROUND);

        final Composite body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(header).atRight();
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(0);

        final CLabel authorPreLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(authorPreLabel).atLeft().atTop(10);
        CLabelSet.decorate(authorPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("author"));

        final CLabel authorLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(authorLabel).atLeftTo(authorPreLabel, 10).atTopTo(authorPreLabel, 0, SWT.TOP).atRight();
        CLabelSet.decorate(authorLabel).setAlignment(SWT.RIGHT).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(getAuthor());

        final CLabel createdTimePreLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(createdTimePreLabel).atLeft().atTopTo(authorPreLabel, 10);
        CLabelSet.decorate(createdTimePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("created"));

        final CLabel createTimeLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(createTimeLabel).atLeftTo(createdTimePreLabel, 10).atTopTo(createdTimePreLabel, 0, SWT.TOP).atRight();
        CLabelSet.decorate(createTimeLabel).setAlignment(SWT.RIGHT).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(getCreatedTime());

        final Label divider2 = new Label(body, SWT.NONE);
        FormDataSet.attach(divider2).atLeft().atTopTo(createdTimePreLabel, 10).atRight().withHeight(1);
        LabelSet.decorate(divider2).setBackground(MT.COLOR_WHITE_SMOKE);

        final Composite footer = new Composite(this, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atTopTo(body).atRight();
        FormLayoutSet.layout(footer).marginWidth(0).marginHeight(10).spacing(0);

        final ImageButton editButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_EDIT, MT.IMAGE_SYSTEM_EDIT_HOVER);
        FormDataSet.attach(editButton).atLeft().atTop(10);
        editButton.addMouseListener(new EditButtonMouseAdapter());
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class TrashButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            MessageBox box = new MessageBox(MyApplication.get().getWindow().getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
            box.setText(msgs.getString("delete"));
            box.setMessage(msgs.getString("delete_test_paper_or_not"));
            int response = box.open();
            if (response == SWT.YES) {
                try {
                    File dir = new File(this.getClass().getResource(URLDecoder.decode(RC.PROJECTS_DATA_DIR + fileAlias, "utf-8")).toURI());
                    FileUtils.deleteDirectory(dir);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                MyApplication.get().getWindow().toMainPageAndToTestPapersView();
            }
        }
    }

    private class EditButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            MyApplication.get().getWindow().toTestEditorPage(testVo);
        }
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public String getFileAlias() {
        return fileAlias;
    }

    public void setFileAlias(String fileAlias) {
        this.fileAlias = fileAlias;
    }

    private String getTitle() {
        String title = testVo.getTitle();
        if (StringUtils.isEmpty(title)) {
            title = msgs.getString("untitled");
        }
        return title;
    }

    public String getAuthor() {
        String author = testVo.getAuthor();
        if (StringUtils.isEmpty(author)) {
            author = msgs.getString("anonymous");
        }
        return author;
    }

    public String getCreatedTime() {
        long createdTime = testVo.getCreatedTime();
        if (createdTime == 0) {
            return msgs.getString("unknown");
        } else {
            return TimeUtils.displayClockTime(createdTime);
        }
    }
}
