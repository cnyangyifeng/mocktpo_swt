package com.mocktpo.modules.system.widgets;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.util.ConfigUtils;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import java.io.File;
import java.net.URLDecoder;
import java.util.ResourceBundle;

public class TestEditorCard extends Composite {

    /* Constants */

    private static final int SUPERSCRIPT_WIDTH = 120;
    private static final int SUPERSCRIPT_HEIGHT = 24;
    private static final int TITLE_WIDTH = 200;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Properties */

    private String fileAlias;
    private TestEditorVo testEditorVo;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestEditorCard(Composite parent, int style, String fileAlias) {
        super(parent, style);
        this.fileAlias = fileAlias;
        this.testEditorVo = ConfigUtils.pullFromEditorBaseDir(fileAlias, TestEditorVo.class);
        init();
    }

    private void init() {
        golbal();
        initWidgets();
    }

    private void golbal() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(this).marginWidth(10).marginHeight(10).spacing(0);
        this.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
    }

    private void initWidgets() {
        final Composite header = new Composite(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight();
        FormLayoutSet.layout(header).marginWidth(0).marginHeight(0).spacing(0);

        final CLabel superscriptLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(superscriptLabel).atLeft().atTop().withWidth(SUPERSCRIPT_WIDTH).withHeight(SUPERSCRIPT_HEIGHT);
        CLabelSet.decorate(superscriptLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_1).setText(msgs.getString("test_paper"));

        final ImageButton deleteButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SYSTEM_DELETE, MT.IMAGE_SYSTEM_DELETE_HOVER);
        FormDataSet.attach(deleteButton).atTop().atRight();
        deleteButton.addMouseListener(new DeleteButtonMouseAdapter());

        final CLabel titleLabel = new CLabel(header, SWT.NONE);
        FormDataSet.attach(titleLabel).atLeft().atTopTo(superscriptLabel, 10).withWidth(TITLE_WIDTH);
        CLabelSet.decorate(titleLabel).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_GREY_DARKEN_4).setText(getTitle());

        final StarsComposite starsComposite = new StarsComposite(header, SWT.NONE, testEditorVo.getStars());
        FormDataSet.attach(starsComposite).atLeft().atTopTo(titleLabel, 10).atRight();

        final Label divider1 = new Label(header, SWT.NONE);
        FormDataSet.attach(divider1).atLeft().atTopTo(starsComposite, 20).atRight().withHeight(1);
        LabelSet.decorate(divider1).setBackground(MT.COLOR_WINDOW_BACKGROUND);

        final Composite body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(header).atRight();
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(0);

        final CLabel creatorPreLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(creatorPreLabel).atLeft().atTop(10);
        CLabelSet.decorate(creatorPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_1).setText(msgs.getString("creator"));

        final CLabel creatorLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(creatorLabel).atLeftTo(creatorPreLabel, 10).atTopTo(creatorPreLabel, 0, SWT.TOP).atRight();
        CLabelSet.decorate(creatorLabel).setAlignment(SWT.RIGHT).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_4).setText(getCreator());

        final CLabel createdTimePreLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(createdTimePreLabel).atLeft().atTopTo(creatorPreLabel, 10);
        CLabelSet.decorate(createdTimePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_1).setText(msgs.getString("created_at"));

        final CLabel createdTimeLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(createdTimeLabel).atLeftTo(createdTimePreLabel, 10).atTopTo(createdTimePreLabel, 0, SWT.TOP).atRight();
        CLabelSet.decorate(createdTimeLabel).setAlignment(SWT.RIGHT).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_4).setText(getCreatedTime());

        final CLabel updatedTimePreLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(updatedTimePreLabel).atLeft().atTopTo(createdTimePreLabel, 10);
        CLabelSet.decorate(updatedTimePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_1).setText(msgs.getString("updated_at"));

        final CLabel updatedTimeLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(updatedTimeLabel).atLeftTo(updatedTimePreLabel, 10).atTopTo(updatedTimePreLabel, 0, SWT.TOP).atRight();
        CLabelSet.decorate(updatedTimeLabel).setAlignment(SWT.RIGHT).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_4).setText(getUpdatedTime());

        final CLabel versionPreLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(versionPreLabel).atLeft().atTopTo(updatedTimePreLabel, 10);
        CLabelSet.decorate(versionPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_1).setText(msgs.getString("version"));

        final CLabel versionLabel = new CLabel(body, SWT.NONE);
        FormDataSet.attach(versionLabel).atLeftTo(versionPreLabel, 10).atTopTo(versionPreLabel, 0, SWT.TOP).atRight();
        CLabelSet.decorate(versionLabel).setAlignment(SWT.RIGHT).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_4).setText(Double.toString(getVersion()));

        final Label divider2 = new Label(body, SWT.NONE);
        FormDataSet.attach(divider2).atLeft().atTopTo(versionPreLabel, 10).atRight().withHeight(1);
        LabelSet.decorate(divider2).setBackground(MT.COLOR_GREY_LIGHTEN_4);

        final Composite footer = new Composite(this, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atTopTo(body).atRight();
        FormLayoutSet.layout(footer).marginWidth(0).marginHeight(10).spacing(0);

        final ImageButton editButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_EDIT, MT.IMAGE_SYSTEM_EDIT_HOVER);
        FormDataSet.attach(editButton).atLeft().atTop(10);
        editButton.addMouseListener(new EditButtonMouseAdapter());

        final ImageButton exportButton = new ImageButton(footer, SWT.NONE, MT.IMAGE_SYSTEM_EXPORT, MT.IMAGE_SYSTEM_EXPORT_HOVER);
        FormDataSet.attach(exportButton).atLeftTo(editButton, 10).atTop(10);
        exportButton.addMouseListener(new ExportButtonMouseAdapter());
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class DeleteButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            MessageBox box = new MessageBox(MyApplication.get().getWindow().getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
            box.setText(msgs.getString("delete"));
            box.setMessage(msgs.getString("delete_test_paper_or_not"));
            int response = box.open();
            if (response == SWT.YES) {
                try {
                    File dir = new File(this.getClass().getResource(URLDecoder.decode(RC.EDITOR_BASE_DIR + fileAlias, "utf-8")).toURI());
                    FileUtils.deleteDirectory(dir);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                MyApplication.get().getWindow().toMainPageAndToTestEditorNavContent();
            }
        }
    }

    private class EditButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            MyApplication.get().getWindow().toTestEditorPage(testEditorVo);
        }
    }

    private class ExportButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            MyApplication.get().getWindow().toTestEditorPage(testEditorVo);
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
        String title = testEditorVo.getTitle();
        if (StringUtils.isEmpty(title)) {
            title = msgs.getString("untitled");
        }
        return title;
    }

    public String getCreator() {
        String creator = testEditorVo.getCreator();
        if (StringUtils.isEmpty(creator)) {
            creator = msgs.getString("unknown");
        }
        return creator;
    }

    public String getCreatedTime() {
        long createdTime = testEditorVo.getCreatedTime();
        if (createdTime == 0) {
            return msgs.getString("unknown");
        } else {
            return TimeUtils.displayClockTime(createdTime);
        }
    }

    public String getUpdatedTime() {
        long updatedTime = testEditorVo.getUpdatedTime();
        if (updatedTime == 0) {
            return msgs.getString("unknown");
        } else {
            return TimeUtils.displayClockTime(updatedTime);
        }
    }

    public double getVersion() {
        double version = testEditorVo.getVersion();
        if (version == 0) {
            return 1.0;
        } else {
            return version;
        }
    }
}
